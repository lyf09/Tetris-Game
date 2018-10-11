package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
 

public class ElsblockProcessor extends Thread {
	// 这个一个俄罗斯方块处理对象，用于产生和显示俄罗斯方块
	// 它还要负责处理俄罗斯方块的移动和变形
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
	
	//每填满一行计多少分
			public final static int PER_LINE_SCORE = 100;
	// 积多少分以后能升级
			public final static int PER_LEVEL_SCORE = PER_LINE_SCORE * 20;
	//		最大级数是9级
			public final static int MAX_LEVEL = 9;
	// 默认级数是5
			public static int DEFAULT_LEVEL = 5; //!!!!!!!!!!!!!!!!!!
	//游戏总积分
			private  static int score = 0;
	//用来记录应该升级所要的积分
			private  static int scoreForLevelUpdata = 0;
	
	
	/**
	 * 每一个样式的方块的反转状态种类为4
	 */
	private final static int BLOCK_STATUS_NUMBER = 4;

	private ElsBox[][] block = new ElsBox[BLOCK_ROWS][BLOCK_COLS]; // 定义方块的引用，将来指向具体的方块

	public final static int[][] STYLES = {// 共28种状态
	{ 0x0f00, 0x4444, 0x0f00, 0x4444 }, // 长条型的四种状态
			{ 0x04e0, 0x0464, 0x00e4, 0x04c4 }, // 'T'型的四种状态
			{ 0x4620, 0x6c00, 0x4620, 0x6c00 }, // 反'Z'型的四种状态
			{ 0x2640, 0xc600, 0x2640, 0xc600 }, // 'Z'型的四种状态
			{ 0x6220, 0x1700, 0x2230, 0x0740 }, // '7'型的四种状态
			{ 0x6440, 0x0e20, 0x44c0, 0x8e00 }, // 反'7'型的四种状态
			{ 0x0660, 0x0660, 0x0660, 0x0660 }, // 方块的四种状态
	};

	public ElsblockProcessor(GameCanvas gamecanvas,int style,
			ObjectInputStream scin,ObjectOutputStream scout,ControlPanel ctrlPanel,int numlose) {
		// TODO Auto-generated constructor stub
		this.gamecanvas = gamecanvas;
		this.scin=scin;
		this.scout=scout;
		this.ctrlPanel=ctrlPanel;
		this.numlose=numlose;
	//	this.outdata=new Data(gamecanvas.getCanvasData(), 0, 0);//写在这里，只能拿到一次数据
		
		this.style=style;
		int key = 0x8000;
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				boolean isColor = ((style & key) != 0);
				block[i][j] = new ElsBox(isColor);// 根据数据模型，为方块中的每个方格进行初始化，为0的使用默认颜色灰色，为1的设置为桔黄色
				key >>= 1;

			}
		}
		display();	

	}

	private void display() {
		// TODO Auto-generated method stub
		// 该方法就是根据row和col位置将俄罗斯方块绘制出来，如果改变了col和row，那方块就会在新的位置绘制
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				if (block[i][j].isColor()) {
					ElsBox box = gamecanvas.getBox(row + i, col + j);// 根据方块中的方格坐标，映射到画布上的方格坐标，注意，只是坐标，画布绘制函数自动会根据该坐标计算出具体的位置
					if(box==null){
						return;
					}
					box.setColor(true);

				}
			}
		}

	}

	/**
	 * 块向左移动一格
	 */
	public void moveLeft() {
		if(controlLeftRight){
		moveTo(row, col - 1);
		}
	}

	/**
	 * 块向右移动一格
	 */
	public void moveRight() {
		if(controlLeftRight){
		moveTo(row, col + 1);
		}
	}

	/**
	 * 块向下落一格
	 */
	public void moveDown() {
		if(!moveTo(row + 1, col)){			
			this.controlLeftRight=false;			
		}
	}

	private boolean moveTo(int newRow, int newCol) {
		
		if(!isMoveAble(newRow,  newCol)) 
			return false;
		// 在新的位置重画
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
					ElsBox box = gamecanvas.getBox(i + row, j + col);// 从方块的方格映射到画布的方格
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
				// 先对当前方块的样式进行遍历，看看是哪一种，找到后，将i,j+1中样式取得即可得到下一种变形样式
				if (STYLES[i][j] == style) {
					int newStyle = STYLES[i][(j + 1) % BLOCK_STATUS_NUMBER];
					// 再仿照moveto方法写一个擦除原先的方块，绘制当前的方块的方法
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
			for (int j = 0; j < block[i].length; j++) {// 根据新样式，重新设置方块内各个方格的颜色，再调用display显示
				boolean isColor = ((newStyle & key) != 0);
				block[i][j].setColor(isColor);
				key >>= 1;
			}
		}
		// 并且要把新样式覆盖保存样式的style属性，这样下次变形才是在新样式基础上的变形
		style = newStyle;//在下一句投射到画布上去之前，把新样式保留一下，
		display();
		gamecanvas.repaint();

	}
	
	protected boolean isMoveAble(int newRow, int newCol) {
		//注意，先要擦除掉原来的，否则进行移动时，会因为判断目标位置已有黄色方格而造成要模拟移动方格无法进行移动。
		erase();
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				//只需要对黄色方块进行判断，灰色格子是可以移出窗口之外的
				if (block[i][j].isColor()) {
					ElsBox box = gamecanvas.getBox(newRow + i, newCol + j);
					// 如果画布中取得的对应方格不是方块或已经是黄色方格，则不能移动
					if (box == null || (box.isColor())) {
						//因为不能移动，所以这里没有改动样式及坐标系统，调用display会将原来擦除的方块重新绘制一遍
						display();//如果没有设置新坐标（方块在画布中的坐标），那么相当于在老坐标原位置重画一遍
						return false;
					}
				}
			}
		}
		//经过上述判断，发现所有黄色方块的移动均不会发生冲突时，返回true
	//	display();
		return true;
	}

	private boolean isTurnAble(int newStyle) {
		erase();
		int key = 0x8000;
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				// 检查4X4的格子内，新模式的格子是否为填充块
				if ((newStyle & key) != 0) {
					// 检查当前格子是否为在面板内并且不是填充块是则不能转动
					ElsBox box = gamecanvas.getBox(row + i, col + j);//投射到画布上的目标位置的俄罗斯方格
					if (box == null || box.isColor()) {//如果目标位置的方格是无效或已经是一个黄色方格
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
		//当run方法中的代码执行完毕，线程执行完毕，进入死亡状态
		while(this.controlLeftRight){
			
			//经过分析，发送数据的最佳时间应该在这里
			
			this.outdata=new Data(gamecanvas.getCanvasData(), 0, numlose);
			this.numlose=0;
			//this.outdata.setA(gamecanvas.getCanvasData());
			
			try {
				//这个有个关键问题，因为服务端和客户端都是执行同一个逻辑，这里必须要先出后入，先准备输出，在准备接收
				
				scout.writeObject(outdata);			
				
				///inputdata.getNumLose()在读取到对方发过来的数据对象中提取对方消除了多少行，我这里马上就加多少行
				//1.要直到当前画布还有多少空行（如果剩余的空行小于要增加随机行，那就已经失败了，无需增加了）
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
		
		System.out.println("这个方块死了");
		
	}
	//从上往下的判断+消行==从上往下消------通过这样的方式解决连消的问题，而不是通过removeline去解决连消问题
	public int  checkFullLine() {
		
		int num=0;
		for (int i =0; i<GameCanvas.CANVAS_ROWS ; i++) {//这里要注意，要从上往下判断，如果从下往上，就无法连消		
			boolean fullLine = true;
			for (int j = 0; j < GameCanvas.CANVAS_COLS; j++) {
				// 检查第i行，第j列是否为彩色方块
				if (!gamecanvas.getBox(i, j).isColor()) {
					// 非彩色方块，
					fullLine = false;
					break;
					// 退出内循环，检查下一行
				}
			}
			if (fullLine) {				
				gamecanvas.removeLine(i); // 该行已填满，移去，再进行上一行的判断，直到循环结束
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
