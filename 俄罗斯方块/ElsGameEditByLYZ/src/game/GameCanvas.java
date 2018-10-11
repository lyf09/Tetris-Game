package game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GameCanvas extends JPanel {

	public final static int CANVAS_ROWS = 22;
	public final static int CANVAS_COLS = 12;

	private Color boxColor = Color.lightGray;
	private Color blockColor = Color.orange;

	private ElsBox[][] elsboxes;
	private int boxWidth, boxHeight;
	//private int [][] canvasData;

	public GameCanvas() {
		// TODO Auto-generated constructor stub		
		
		elsboxes = new ElsBox[CANVAS_ROWS][CANVAS_COLS];
		//canvasData=new int[CANVAS_ROWS][CANVAS_COLS];
		for(int i=0;i<CANVAS_ROWS;i++){			
			for(int j=0;j<CANVAS_COLS;j++){				
				elsboxes[i][j]=new ElsBox(false);
			}
		}		


	}
	
	
	public void fanning() {
		boxWidth = getSize().width / CANVAS_COLS;
		boxHeight = getSize().height / CANVAS_ROWS;
	}


	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		this.fanning();

		g.setColor(boxColor);

		for (int i = 0; i < elsboxes.length; i++) {
			for (int j = 0; j < elsboxes[i].length; j++) {			
				
				
				
				if(elsboxes[i][j].isColor()){
					g.setColor(blockColor);
				}else{
					g.setColor(boxColor);
				}
				
				// ������õ��Ǵ������ҵĻ��������ȳ˵���j��Ȼ����i
				g.fill3DRect(j*boxWidth,i*boxHeight,boxWidth,boxHeight,
						true);

			}
		}
		
		


	}
	
	public ElsBox getBox(int rows, int cols) {
		
		if (rows < 0 || rows > elsboxes.length - 1 || cols < 0
				|| cols > elsboxes[0].length - 1)
			return null;
		
		return (elsboxes[rows][cols]);
	}
	//����һ�еķ�������ǣ���������������ϵ�ѭ����������޷����������
	public  void removeLine(int row) {//�÷���ֻ������ô����һ��
		for (int i = row; i > 0; i--) {//�����ܴ���-1������0�п�¡���������������ֻҪ�жϴ���0����
			for (int j = 0; j < GameCanvas.CANVAS_COLS; j++)
				elsboxes[i][j] = (ElsBox) elsboxes[i - 1][j].clone();
		}
 		repaint();//������֮��Ҫ���ô˷���������ػ������Ը÷���д�ڻ����������
	}
	
	
	public int[][] getCanvasData() {
		
		int [][] canvasData=new int[ elsboxes.length][elsboxes[0].length];
 
		for (int i = 0; i < elsboxes.length; i++) {
			for (int j = 0; j < elsboxes[i].length; j++) {
				if (elsboxes[i][j].isColor()) {
					canvasData[i][j] = 1;
				} else {
					canvasData[i][j] = 0;
				}
			}
		}
		return canvasData;
	}

	
	
	
	public ElsBox autoElsbox(){
		ElsBox elsbox=null;
		
		if(Math.random()>0.5){
			elsbox=new ElsBox(true);
		}else{
			elsbox=new ElsBox(false);
		}		
		return elsbox;	
		
		
	}
	
	
	public int getCurrentRow(){//�������µĵ�һ���ǿ��У��ҵ�˼·���������ң�����Ч��
		int currenRow=0;
		boolean flag=true;;
		int i;
		for(i=21;i>=0;i--){	
			flag=true;
			for(int j=0;j<12;j++){				
				if(elsboxes[i][j].isColor()){
					flag=false;
					break;
				}				
			}			
			if(flag){
				currenRow=i+1;
				break;
			}
		}		
		return currenRow;//�������Ҳ��ʾ����һ����˼����ʾ���ж��ٿ���
	}
	
	//��ʱ�����ǲ�����ӵ��������Ϊ������Ӿ�ʤ���Ѷ������ǰ������������ʤ���ж���ȥ��������ֻ���ǳɹ������
	public void addRow(int rowCount){		
		//�����ƶ�
		int currentRow=getCurrentRow();		
		for (int i=currentRow;i<22 ;i++){
			
			for(int j=0;j<12;j++){				
				elsboxes[i-rowCount][j]	=(ElsBox)elsboxes[i][j].clone();		
			}			
		}		
		//���		
		for(int i=21;i>21-rowCount;i--){
			
			for(int j=0;j<12;j++){
				elsboxes[i][j]=autoElsbox();
			}			
		}		
		repaint();			
	}

	

}
