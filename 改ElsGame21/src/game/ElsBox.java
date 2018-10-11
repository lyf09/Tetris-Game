package game;

public class ElsBox implements Cloneable {
	
	
	private boolean color;

	public boolean isColor() {
		return color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}
	
	public ElsBox(boolean color) {
		// TODO Auto-generated constructor stub
		this.color=color;
	}
	public ElsBox() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected Object clone()  {
		// TODO Auto-generated method stub
		Object cloned = null;
		try {
			cloned = super.clone();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return cloned;
	}
	

}
