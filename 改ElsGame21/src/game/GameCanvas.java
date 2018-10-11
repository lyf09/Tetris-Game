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
				
				// 这里采用的是从左至右的画，所以先乘的是j，然后是i
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
	//消除一行的方法，不牵涉连消，从下往上的循环，会出现无法连消的情况
	public  void removeLine(int row) {//该方法只考虑怎么消除一行
		for (int i = row; i > 0; i--) {//不可能存在-1行往第0行克隆的情况，所以这里只要判断大于0即可
			for (int j = 0; j < GameCanvas.CANVAS_COLS; j++)
				elsboxes[i][j] = (ElsBox) elsboxes[i - 1][j].clone();
		}
 		repaint();//消完行之后要调用此方法激活画布重画，所以该方法写在画布类中最佳
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
	
	
	public int getCurrentRow(){//从上往下的第一个非空行，找的思路从下往上找，更有效率
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
		return currenRow;//这个数据也表示另外一个意思，表示还有多少空行
	}
	
	//暂时不考虑不能添加的情况，因为不能添加就胜负已定，我们把这种情况留在胜负判断里去做，这里只考虑成功的情况
	public void addRow(int rowCount){		
		//往上移动
		int currentRow=getCurrentRow();		
		for (int i=currentRow;i<22 ;i++){
			
			for(int j=0;j<12;j++){				
				elsboxes[i-rowCount][j]	=(ElsBox)elsboxes[i][j].clone();		
			}			
		}		
		//填充		
		for(int i=21;i>21-rowCount;i--){
			
			for(int j=0;j<12;j++){
				elsboxes[i][j]=autoElsbox();
			}			
		}		
		repaint();			
	}

	

}
