package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Elsgame extends GameMenu {

	private GameCanvas canvas;

	private ControlPanel ctrlPanel;
	private boolean playing = false;
	
	
	public static int selffail=0;
	public static int otherfail=0;

	private ElsblockProcessor elsblockprocessor;

	public ElsblockProcessor getElsblockprocessor() {
		return elsblockprocessor;
	}	

	public Elsgame() {
		this.canvas = new GameCanvas();
		this.ctrlPanel = new ControlPanel(this);
		this.ctrlPanel.setbtPlayenable(false);

		this.setTitle("俄罗斯方块对战游戏");
		this.setSize(398, 498);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();

		// 将游戏窗口置于屏幕中央
		this.setLocation((scrSize.width - this.getSize().width) / 2,
				(scrSize.height - this.getSize().height) / 2);

		this.getContentPane().setLayout(new BorderLayout());

		this.getContentPane().add(canvas, BorderLayout.CENTER);
		this.getContentPane().add(ctrlPanel, BorderLayout.EAST);

		this.setVisible(true);

		this.ctrlPanel.requestFocus();
		// this.ctrlPanel.requestFocusInWindow();

	}

	public ControlPanel getCtrlPanel() {
		return ctrlPanel;
	}

	public static void main(String[] args) {

		new Elsgame();
		// Thread thread = new Thread(new Elsgame());
		// thread.start();
	}

	private class Game implements Runnable {

		@Override
		public void run() {
			
			JDialog serverSuc=serverSuc();
			JDialog socketSuc=socketSuc();
			
			boolean flag = true;
			Socket scsocket = null;
			ObjectInputStream scin = null;
			ObjectOutputStream scout = null;

			if (Elsgame.this.getGameclienset() != null) {// 说明我这里建立的是客户端

					
				while (flag) {try {

						scsocket = new Socket(Elsgame.this.getGameclienset()
								.getIpaddress(), Elsgame.this.getGameclienset()
								.getPortNum());// 在new的时候发起连接请求
						flag=false;
					
						scout = new ObjectOutputStream(
								scsocket.getOutputStream()); // 从管道中拿到字节输出流，并将其包装对象输出流
						scin = new ObjectInputStream(scsocket.getInputStream());// 从管道中拿到字节输入流，并将其包装对象输入流
						// Thread.sleep(3000);//测试1,不能同步开始，晚了三秒
						socketSuc.setVisible(true);
						Thread.sleep(3000);
						socketSuc.setVisible(false);
				
				} catch (Exception e) {
				
						if(	JOptionPane.OK_OPTION==JOptionPane.showConfirmDialog(Elsgame.this, "服务器尚未启动成功，你要继续尝试连接吗", "连接对话框", JOptionPane.OK_CANCEL_OPTION)){
							continue;
						}else{
							return;
						}

					}
				}

			} else {
				if (Elsgame.this.getGameserverset() != null) {// 说明我这里建立的是服务端
					try {
						serverSuc.setVisible(true);
						scsocket = Elsgame.this.getGameserverset().getSs()
								.accept();// 调用服务端socket启动侦听，注意连接成功之后，返回的也是一个客户端socket
						serverSuc.setVisible(false);
						scin = new ObjectInputStream(scsocket.getInputStream());
						scout = new ObjectOutputStream(
								scsocket.getOutputStream());
						
						socketSuc.setVisible(true);
						Thread.sleep(5000);
						socketSuc.setVisible(false);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			int style = ElsblockProcessor.STYLES[(int) (Math.random() * 7)][(int) (Math
					.random() * 4)];
			int numlose=0;
			
			new ReadObjectThread(scin, scout, canvas, ctrlPanel).start();
			
			while (selffail!=1 && otherfail!=1) {
				
				if (elsblockprocessor != null) {
					if (elsblockprocessor.isAlive()) {// 只要run方法还在执行，alive就会返回true
						continue;
					}
					numlose=elsblockprocessor.checkFullLine();

					if (isGameOver()) {
						selffail=1;//改这个消息树是为了后续的判断方便
						try {
							scout.writeObject(new Data(canvas.getCanvasData(), selffail, 0));
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						
						break;
					}
				}
				elsblockprocessor = new ElsblockProcessor(canvas, style,scin,scout,ctrlPanel,numlose);
				elsblockprocessor.start();
				style = ElsblockProcessor.STYLES[(int) (Math.random() * 7)][(int) (Math
						.random() * 4)];
				ctrlPanel.setTipPanelStyle(style);
			}
			///就根据上面两个消息树的情况进行胜负判定的现实
			if (otherfail == 1) {				
				JOptionPane.showMessageDialog(Elsgame.this, "你赢了");
				try {
					if (scin != null)
						scin.close();
					if (scout != null)
						scout.close();
					if(scsocket!=null)
						scsocket.close();

						if (Elsgame.this.getGameserverset().getSs() != null) {
							Elsgame.this.getGameserverset().getSs() .close();
							
						}

				} catch (Exception e) {
					
				}

				return;
			}
			if (selffail == 1) {				
				JOptionPane.showMessageDialog(Elsgame.this, "你输了");
				try {
					if (scin != null)
						scin.close();
					if (scout != null)
						scout.close();
					if(scsocket!=null)
						scsocket.close();

						if (Elsgame.this.getGameserverset().getSs() != null) {
							Elsgame.this.getGameserverset().getSs() .close();
							
						}					
					
				} catch (Exception e) {
					
				}

				return;
			}		
			
		}	

	}

	public void btnPlay() {
		Thread thread = new Thread(new Game());
		thread.start();
		this.ctrlPanel.requestFocusInWindow();
	}

	// 判断胜负应在消行之后，下一个方块处理类产生之前
	private boolean isGameOver() {
		for (int j = 0; j < GameCanvas.CANVAS_COLS; j++) {
			ElsBox box = canvas.getBox(0, j);
			if (box.isColor())
				return true;
		}

		return false;
	}

	private JDialog serverSuc() {
		JDialog dialog = new JDialog(this, "提示信息", false);
		JLabel label = new JLabel("            端口有效，等待其他玩家中......");
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.setSize(300, 90);
		dialog.setResizable(false);
		dialog.getContentPane().add(label);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((scrSize.width - getSize().width) / 2,
				(scrSize.height - getSize().height) / 2);
		return dialog;
	}

	private JDialog socketSuc() {
		JDialog dialog = new JDialog(this, "提示信息", false);
		JLabel label = new JLabel("与玩家连接建立成功，10s后开始游戏！");
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.setSize(300, 90);
		dialog.setResizable(false);
		dialog.getContentPane().add(label);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((scrSize.width - getSize().width) / 2,
				(scrSize.height - getSize().height) / 2);
		return dialog;
	}

}
