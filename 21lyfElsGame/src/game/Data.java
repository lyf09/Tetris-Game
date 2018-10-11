package game;

import java.io.Serializable;

public class Data implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	
	private int[][] a;
	private int fail;
	private int numLose = 0;

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
