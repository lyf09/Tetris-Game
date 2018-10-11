package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
 

public class ElsblockProcessor extends Thread {
	// ���һ������˹���鴦��������ڲ�������ʾ����˹����
	// ����Ҫ���������˹������ƶ��ͱ���
	public final static int BLOCK_ROWS = 4;
	public final static int BLOCK_COLS = 4;
	private GameCanvas gamecanvas;
	protected boolean controlLeftRight=true;
	private int col = 4;
	private int row = -1;
	private final static int BLOCK_KIND_NUMBER = 7;
	private int style;
	private ObjectInputStream scin;
	private ObjectOutputStream scout;
	
	private Data  outdata; 
	
	private ControlPanel ctrlPanel;
	private int numlose=0;
	
	//ÿ����һ�мƶ��ٷ�
			public final static int PER_LINE_SCORE = 100;
	// �����ٷ��Ժ�������
			public final static int PER_LEVEL_SCORE = PER_LINE_SCORE * 20;
	//		�������9��
			public final static int MAX_LEVEL = 9;
	// Ĭ�ϼ�����5
			public static int DEFAULT_LEVEL = 5; //!!!!!!!!!!!!!!!!!!
	//��Ϸ�ܻ���
			private  static int score = 0;
	//������¼Ӧ��������Ҫ�Ļ���
			private  static int scoreForLevelUpdata = 0;
	
	
	/**
	 * ÿһ����ʽ�ķ���ķ�ת״̬����Ϊ4
	 */
	private final static int BLOCK_STATUS_NUMBER = 4;

	private ElsBox[][] block = new ElsBox[BLOCK_ROWS][BLOCK_COLS]; // ���巽������ã�����ָ�����ķ���

	public final static int[][] STYLES = {// ��28��״̬
	{ 0x0f00, 0x4444, 0x0f00, 0x4444 }, // �����͵�����״̬
			{ 0x04e0, 0x0464, 0x00e4, 0x04c4 }, // 'T'�͵�����״̬
			{ 0x4620, 0x6c00, 0x4620, 0x6c00 }, // ��'Z'�͵�����״̬
			{ 0x2640, 0xc600, 0x2640, 0xc600 }, // 'Z'�͵�����״̬
			{ 0x6220, 0x1700, 0x2230, 0x0740 }, // '7'�͵�����״̬
			{ 0x6440, 0x0e20, 0x44c0, 0x8e00 }, // ��'7'�͵�����״̬
			{ 0x0660, 0x0660, 0x0660, 0x0660 }, // ���������״̬
	};

	public ElsblockProcessor(GameCanvas gamecanvas,int style,
			ObjectInputStream scin,ObjectOutputStream scout,ControlPanel ctrlPanel,int numlose) {
		// TODO Auto-generated constructor stub
		this.gamecanvas = gamecanvas;
		this.scin=scin;
		this.scout=scout;
		this.ctrlPanel=ctrlPanel;
		this.numlose=numlose;
	//	this.outdata=new Data(gamecanvas.getCanvasData(), 0, 0);//д�����ֻ���õ�һ������
		
		this.style=style;
		int key = 0x8000;
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				boolean isColor = ((style & key) != 0);
				block[i][j] = new ElsBox(isColor);// ��������ģ�ͣ�Ϊ�����е�ÿ��������г�ʼ����Ϊ0��ʹ��Ĭ����ɫ��ɫ��Ϊ1������Ϊ�ۻ�ɫ
				key >>= 1;

			}
		}
		display();	

	}

	private void display() {
		// TODO Auto-generated method stub
		// �÷������Ǹ���row��colλ�ý�����˹������Ƴ���������ı���col��row���Ƿ���ͻ����µ�λ�û���
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				if (block[i][j].isColor()) {
					ElsBox box = gamecanvas.getBox(row + i, col + j);// ���ݷ����еķ������꣬ӳ�䵽�����ϵķ������꣬ע�⣬ֻ�����꣬�������ƺ����Զ�����ݸ��������������λ��
					if(box==null){
						return;
					}
					box.setColor(true);

				}
			}
		}

	}

	/**
	 * �������ƶ�һ��
	 */
	public void moveLeft() {
		if(controlLeftRight){
		moveTo(row, col - 1);
		}
	}

	/**
	 * �������ƶ�һ��
	 */
	public void moveRight() {
		if(controlLeftRight){
		moveTo(row, col + 1);
		}
	}

	/**
	 * ��������һ��
	 */
	public void moveDown() {
		if(!moveTo(row + 1, col)){			
			this.controlLeftRight=false;			
		}
	}

	private boolean moveTo(int newRow, int newCol) {
		
		if(!isMoveAble(newRow,  newCol)) 
			return false;
		// ���µ�λ���ػ�
		erase();

		row = newRow;
		col = newCol;
		display();
		gamecanvas.repaint();
		return true;

	}

	private void erase() {
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				if (block[i][j].isColor()) {
					ElsBox box = gamecanvas.getBox(i + row, j + col);// �ӷ���ķ���ӳ�䵽�����ķ���
					if(box==null)
						return;
					box.setColor(false);
				}
			}
		}
	}

	public void turnNext() {

		for (int i = 0; i < BLOCK_KIND_NUMBER; i++) {
			for (int j = 0; j < BLOCK_STATUS_NUMBER; j++) {
				// �ȶԵ�ǰ�������ʽ���б�������������һ�֣��ҵ��󣬽�i,j+1����ʽȡ�ü��ɵõ���һ�ֱ�����ʽ
				if (STYLES[i][j] == style) {
					int newStyle = STYLES[i][(j + 1) % BLOCK_STATUS_NUMBER];
					// �ٷ���moveto����дһ������ԭ�ȵķ��飬���Ƶ�ǰ�ķ���ķ���
					turnTo(newStyle);
					return;
				}
			}
		}
	}

	
	private void turnTo(int newStyle) {
		
		if(!isTurnAble(newStyle)){
			return;
		}
		
		erase();
		int key = 0x8000;
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {// ��������ʽ���������÷����ڸ����������ɫ���ٵ���display��ʾ
				boolean isColor = ((newStyle & key) != 0);
				block[i][j].setColor(isColor);
				key >>= 1;
			}
		}
		// ����Ҫ������ʽ���Ǳ�����ʽ��style���ԣ������´α��β���������ʽ�����ϵı���
		style = newStyle;//����һ��Ͷ�䵽������ȥ֮ǰ��������ʽ����һ�£�
		display();
		gamecanvas.repaint();

	}
	
	protected boolean isMoveAble(int newRow, int newCol) {
		//ע�⣬��Ҫ������ԭ���ģ���������ƶ�ʱ������Ϊ�ж�Ŀ��λ�����л�ɫ��������Ҫģ���ƶ������޷������ƶ���
		erase();
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				//ֻ��Ҫ�Ի�ɫ��������жϣ���ɫ�����ǿ����Ƴ�����֮���
				if (block[i][j].isColor()) {
					ElsBox box = gamecanvas.getBox(newRow + i, newCol + j);
					// ���������ȡ�õĶ�Ӧ�����Ƿ�����Ѿ��ǻ�ɫ���������ƶ�
					if (box == null || (box.isColor())) {
						//��Ϊ�����ƶ�����������û�иĶ���ʽ������ϵͳ������display�Ὣԭ�������ķ������»���һ��
						display();//���û�����������꣨�����ڻ����е����꣩����ô�൱����������ԭλ���ػ�һ��
						return false;
					}
				}
			}
		}
		//���������жϣ��������л�ɫ������ƶ������ᷢ����ͻʱ������true
	//	display();
		return true;
	}

	private boolean isTurnAble(int newStyle) {
		erase();
		int key = 0x8000;
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				// ���4X4�ĸ����ڣ���ģʽ�ĸ����Ƿ�Ϊ����
				if ((newStyle & key) != 0) {
					// ��鵱ǰ�����Ƿ�Ϊ������ڲ��Ҳ�������������ת��
					ElsBox box = gamecanvas.getBox(row + i, col + j);//Ͷ�䵽�����ϵ�Ŀ��λ�õĶ���˹����
					if (box == null || box.isColor()) {//���Ŀ��λ�õķ�������Ч���Ѿ���һ����ɫ����
						display();
						return false;
					}
				}
				key >>= 1;
			}
		}		
		return true;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//��run�����еĴ���ִ����ϣ��߳�ִ����ϣ���������״̬
		while(this.controlLeftRight){
			
			//�����������������ݵ����ʱ��Ӧ��������
			
			this.outdata=new Data(gamecanvas.getCanvasData(), 0, numlose);
			this.numlose=0;
			//this.outdata.setA(gamecanvas.getCanvasData());
			
			try {
				//����и��ؼ����⣬��Ϊ����˺Ϳͻ��˶���ִ��ͬһ���߼����������Ҫ�ȳ����룬��׼���������׼������
				
				scout.writeObject(outdata);			
				
				///inputdata.getNumLose()�ڶ�ȡ���Է������������ݶ�������ȡ�Է������˶����У����������ϾͼӶ�����
				//1.Ҫֱ����ǰ�������ж��ٿ��У����ʣ��Ŀ���С��Ҫ��������У��Ǿ��Ѿ�ʧ���ˣ����������ˣ�
				//2.
			
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				//Thread.sleep(3000);				
				sleep(100* (9 - this.ctrlPanel.getTfLevel() )+ 100);				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.moveDown();			
			
		}
		
		System.out.println("�����������");
		
	}
	//�������µ��ж�+����==����������------ͨ�������ķ�ʽ������������⣬������ͨ��removelineȥ�����������
	public int  checkFullLine() {
		
		int num=0;
		for (int i =0; i<GameCanvas.CANVAS_ROWS ; i++) {//����Ҫע�⣬Ҫ���������жϣ�����������ϣ����޷�����		
			boolean fullLine = true;
			for (int j = 0; j < GameCanvas.CANVAS_COLS; j++) {
				// ����i�У���j���Ƿ�Ϊ��ɫ����
				if (!gamecanvas.getBox(i, j).isColor()) {
					// �ǲ�ɫ���飬
					fullLine = false;
					break;
					// �˳���ѭ���������һ��
				}
			}
			if (fullLine) {				
				gamecanvas.removeLine(i); // ��������������ȥ���ٽ�����һ�е��жϣ�ֱ��ѭ������
				this.score=this.score+PER_LINE_SCORE;
				this.scoreForLevelUpdata=this.scoreForLevelUpdata+PER_LINE_SCORE;
				if(this.scoreForLevelUpdata>=PER_LEVEL_SCORE){
					if(this.ctrlPanel.getTfLevel()<9){
						int level=this.ctrlPanel.getTfLevel()+1;
						this.ctrlPanel.setTfLevel(level);						
					}				
					
				}		
				this.ctrlPanel.setScore(this.score);;
				num++;
			}
		}
		
		return num;
		
		
	}
	

	
	

}
