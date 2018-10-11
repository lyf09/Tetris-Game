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
		this.color=color;
	}
	public ElsBox() {
	}
	
	@Override
	protected Object clone()  {
		Object cloned = null;
		try {
			cloned = super.clone();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return cloned;
	}
}
