package game.config;

import game.Elsgame;
import game.GameMenu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameServerSet extends JDialog implements ActionListener{

	private JLabel tipTitle = new JLabel("������˿ںţ�ע�⣺�˿ں���ѡ�����1024�����֣�");	
	private JLabel port = new JLabel("             �˿ں�:");
	private int portNum = 8081;// �洢�˿ں�
	private JTextField text = new JTextField(""+portNum);
	private JButton ok = new JButton("���");
	private JPanel panel = new JPanel();

	private ServerSocket ss;
	
	private GameMenu gm;


	public int getPortNum() {
		return portNum;
	}
	
	

	public ServerSocket getSs() {
		return ss;
	}



	public GameServerSet(GameMenu gamemenu, String title, boolean model) {

		super(gamemenu, title, model);	
		this.gm=gamemenu;
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		ok.addActionListener(this);
		getContentPane().setLayout(new BorderLayout());
		setSize(350, 90);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((scrSize.width - getSize().width) / 2,
				(scrSize.height - getSize().height) / 2);
		getContentPane().add("North", tipTitle);
		panel.setLayout(new GridLayout(1, 3, 10, 10));
		panel.add(port);
		panel.add(text);
		panel.add(ok);
		getContentPane().add("South", panel);
		setResizable(false);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.portNum = Integer.parseInt(text.getText());		
		
		((Elsgame)gm).getCtrlPanel().setEnablePlayButton(true);
		this.dispose();//���ٿ��ӻ���Դ�����󻹴���			
		//System.out.println("�һ���˶˿ں�" + this.portNum);
		
		try {
			ss=new ServerSocket(portNum);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "�����ö˿ڲ��Ϸ���Ӧ��1-65536֮�� ���߷�������ʧ��");
		}

	}

}
