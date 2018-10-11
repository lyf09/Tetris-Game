package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ReadObjectThread extends Thread {

	private Data inputdata;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;

	private GameCanvas canvas = null;
	private ControlPanel panel = null;

	public ReadObjectThread(ObjectInputStream ois, ObjectOutputStream oos,
			GameCanvas canvas, ControlPanel panel) {
		// TODO Auto-generated constructor stub

		this.canvas = canvas;
		this.panel = panel;
		this.ois = ois;
		this.oos = oos;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (Elsgame.selffail != 1 && Elsgame.otherfail != 1) {

			try {
				inputdata = (Data) ois.readObject();
				panel.setCanvasDate(inputdata.getA());
				int konghang = this.canvas.getCurrentRow();
				if (inputdata.getNumLose() < konghang) {

					this.canvas.addRow(inputdata.getNumLose());

				} else {
					// 进到这里来的话，说明自己已经失败，该通知对方获胜了
					Elsgame.selffail = 1;
					oos.writeObject(new Data(canvas.getCanvasData(), 1, 0));
					break;

				}				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (inputdata.getFail() == 1) {
				Elsgame.otherfail = 1;
				break;

			}
		}

	}

}
