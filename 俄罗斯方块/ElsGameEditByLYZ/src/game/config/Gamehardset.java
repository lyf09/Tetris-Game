package game.config;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.ControlPanel;
import game.ElsblockProcessor;
import game.Elsgame;
import game.GameMenu;

public class Gamehardset extends JDialog {
	private JLabel label_title = new JLabel("游戏难度设置", JLabel.CENTER);

	private JPanel panel_main = new JPanel();

	private JPanel panel_hard = new JPanel();

	private JLabel label_hard = new JLabel("当前默认难度:   ");

	private JTextField jTextField = new JTextField(10);

	private JPanel panel_button = new JPanel();

	private JButton button_confirm = new JButton("确定");

	private JButton button_back = new JButton("返回");

	private GameMenu menu = null;

	public Gamehardset(GameMenu menu) {
		this.menu = menu;
		setSize(300, 150);
		setTitle("游戏难度设置");
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((scrSize.width - getSize().width) / 2, (scrSize.height - getSize().height) / 2);
		setResizable(false);

		jTextField.setText(ElsblockProcessor.DEFAULT_LEVEL + "");
		panel_hard.setLayout(new GridLayout(1, 2, 10, 10));
		panel_hard.add(label_hard);
		panel_hard.add(jTextField);
		panel_button.setLayout(new GridLayout(1, 2, 10, 10));
		panel_button.add(button_back);
		panel_button.add(button_confirm);
		panel_main.setLayout(new GridLayout(3, 1, 5, 5));
		panel_main.add(label_title);
		panel_main.add(panel_hard);
		panel_main.add(panel_button);
		getContentPane().add(panel_main);
		this.setVisible(true);
		registerListener();
	}

	private void registerListener() {
		button_confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ElsblockProcessor.DEFAULT_LEVEL=Integer.parseInt(jTextField.getText());
				ControlPanel controlPanel =((Elsgame)menu).getCtrlPanel();
				controlPanel.setTfLevel(Integer.parseInt(jTextField.getText()));
				JOptionPane.showMessageDialog(null, "修改完成");
			}
		});
		button_back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Gamehardset.this.dispose();
				
			}
		});
		
	}

}
