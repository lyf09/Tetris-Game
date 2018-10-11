package game.config;

import game.Elsgame;
import game.GameMenu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameClientSet extends JDialog  implements ActionListener{
	
	
	private JLabel address = new JLabel("        ������ַ:");
	private JTextField addressText = new JTextField("127.0.0.1");
	private JLabel port = new JLabel("           �˿ں�:");
	private JTextField text = new JTextField("8081");
	private JButton ok = new JButton("���");
	private JPanel panel = new JPanel();
	private JPanel panel1 = new JPanel();	
	private String ipaddress;
	private int portNum;
	private GameMenu gm;
	
	public String getIpaddress() {
		return ipaddress;
	}

	public int getPortNum() {
		return portNum;
	}

	public GameClientSet(GameMenu gamemenu, String title, boolean model) {
		
		super(gamemenu, title, model);
		this.gm = gamemenu;
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ok.addActionListener(this);
		
		getContentPane().setLayout(new BorderLayout());
		
		panel.setLayout(new GridLayout(1, 4));
		panel.add(address);
		panel.add(addressText);
		panel.add(port);
		panel.add(text);
 
		panel1.setLayout(new FlowLayout());
		panel1.add(ok);

		getContentPane().add("North", panel);
		getContentPane().add("South", panel1);			
		setSize(350, 90);	

		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((scrSize.width - getSize().width) / 2,
				(scrSize.height - getSize().height) / 2);
		setResizable (false);
		this.setVisible(true);
		
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		this.portNum = Integer.parseInt(text.getText());
		this.ipaddress=addressText.getText().trim();
		this.dispose();//���ٿ��ӻ���Դ�����󻹴���		
		((Elsgame)gm).getCtrlPanel().setbtPlayenable(true);
	}

	

}
