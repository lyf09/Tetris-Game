package game;

import java.io.Serializable;

public class Data implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	
	private int[][] a;//用以存储22X12个方格状态的二维数组
	private int fail;//用以存储自身是否失败的变量，如果自身游戏已经失败，则对方获胜，1表示失败，0表示仍未失败
	private int numLose = 0;//存储自身所消除的行，因为自己消除一行，对方就要增加一行
	
	
	
	
	public Data(int[][] a, int fail, int numLose) {		
		this.a = a;
		this.fail = fail;
		this.numLose = numLose;
	}

	public int[][] getA() {	
		
		return a;
	}

	public void setA(int[][] a) {
		this.a = a;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public int getNumLose() {
		return numLose;
	}

	public void setNumLose(int numLose) {
		this.numLose = numLose;
	}


	
	
	

}
