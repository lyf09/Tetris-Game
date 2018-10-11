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
		// TODO Auto-generated constructor stub

		this.canvas = new GameCanvas();
		this.ctrlPanel = new ControlPanel(this);
		this.ctrlPanel.setEnablePlayButton(false);

		this.setTitle("����˹�����ս��Ϸ");
		this.setSize(398, 498);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();

		// ����Ϸ����������Ļ����
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
			// TODO Auto-generated method stub
			
			JDialog serverSuc=serverSuc();
			JDialog socketSuc=socketSuc();
			
			boolean flag = true;
			Socket scsocket = null;
			ObjectInputStream scin = null;
			ObjectOutputStream scout = null;

			if (Elsgame.this.getGameclienset() != null) {// ˵�������ｨ�����ǿͻ���

					
				while (flag) {try {

						scsocket = new Socket(Elsgame.this.getGameclienset()
								.getIpaddress(), Elsgame.this.getGameclienset()
								.getPortNum());// ��new��ʱ������������
						flag=false;
					

						// Thread.sleep(3000);//����2����ͬ������Ҷ��������룬Ϊʲô��
						// ���������׼�����������û���κ�ǰ�������ģ�ֱ���ã��������ݲ���Ҫ�����κζ�����������ֱ�������OK
						// �����������Ҫ�ã�׼������������һ����ҪĿ�귽����һ��������������ڻ�ȡ��������ʱ���������
						// һֱ�ȴ����Է�׼���������֮����������������ܽ����ɹ����Ż�������
						// ���ۣ�1.����˿ͻ���ֻҪ�෴��ok û����2���Ƚ�����������룬Ҳok 3����������������������

						scout = new ObjectOutputStream(
								scsocket.getOutputStream()); // �ӹܵ����õ��ֽ���������������װ���������
						scin = new ObjectInputStream(scsocket.getInputStream());// �ӹܵ����õ��ֽ����������������װ����������
						// Thread.sleep(3000);//����1,����ͬ����ʼ����������
						socketSuc.setVisible(true);
						Thread.sleep(3000);
						socketSuc.setVisible(false);
				
				} catch (Exception e) {
						//System.out.println(e.toString());	
				
						if(	JOptionPane.OK_OPTION==JOptionPane.showConfirmDialog(Elsgame.this, "��������δ�����ɹ�����Ҫ��������������", "���ӶԻ���", JOptionPane.OK_CANCEL_OPTION)){
							continue;
						}else{
							return;
						}

					}
				}

			} else {
				if (Elsgame.this.getGameserverset() != null) {// ˵�������ｨ�����Ƿ����
					try {
						serverSuc.setVisible(true);
						scsocket = Elsgame.this.getGameserverset().getSs()
								.accept();// ���÷����socket����������ע�����ӳɹ�֮�󣬷��ص�Ҳ��һ���ͻ���socket
						serverSuc.setVisible(false);
						scin = new ObjectInputStream(scsocket.getInputStream());
						scout = new ObjectOutputStream(
								scsocket.getOutputStream());
						
						socketSuc.setVisible(true);
						Thread.sleep(3000);
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
					// �ڲ���һ���·��鴦�������֮ǰ���ж�һ���ϵ��Ƿ���������Ҫע������ǲ�����һ����ʱ����û���ϡ�
					// �Ķ���˹���鴦����ģ����Իᱨ��ָ���쳣,����Ҫ�������if����������

					if (elsblockprocessor.isAlive()) {// ֻҪrun��������ִ�У�alive�ͻ᷵��true
						continue;
					}
					numlose=elsblockprocessor.checkFullLine();
				}
				elsblockprocessor = new ElsblockProcessor(canvas, style,scin,scout,ctrlPanel,numlose);
				elsblockprocessor.moveDown();
					if (!elsblockprocessor.controlLeftRight) {
						// ����һ������ʽ����������ȷ���������ִ��
						//JOptionPane.showMessageDialog(Elsgame.this, "��Ϸ������������");						
						selffail=1;//�������Ϣ����Ϊ�˺������жϷ���
						try {
							scout.writeObject(new Data(canvas.getCanvasData(), selffail, 0));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						break;
					}
				
				elsblockprocessor.start();
				style = ElsblockProcessor.STYLES[(int) (Math.random() * 7)][(int) (Math
						.random() * 4)];
				ctrlPanel.setTipPanelStyle(style);
			}
			///�͸�������������Ϣ�����������ʤ���ж�����ʵ
			if (otherfail == 1) {				
				JOptionPane.showMessageDialog(Elsgame.this, "��Ӯ��");
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
				JOptionPane.showMessageDialog(Elsgame.this, "������");
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
	}

	// �ж�ʤ��Ӧ������֮����һ�����鴦�������֮ǰ
/*	private boolean isGameOver() {
		for (int j = 0; j < GameCanvas.CANVAS_COLS; j++) {
			ElsBox box = canvas.getBox(0, j);
			if (box.isColor())
				return true;
		}

		return false;
	}*/
	private boolean isGameOver() {
	System.out.println(elsblockprocessor.controlLeftRight);
	return elsblockprocessor.controlLeftRight;
	}
	private JDialog serverSuc() {
		JDialog dialog = new JDialog(this, "��ʾ��Ϣ", false);
		JLabel label = new JLabel("            �˿���Ч���ȴ����������......");
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
		JDialog dialog = new JDialog(this, "��ʾ��Ϣ", false);
		JLabel label = new JLabel("��������ӽ����ɹ���10s��ʼ��Ϸ��");
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
