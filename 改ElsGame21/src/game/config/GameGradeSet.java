package game.config;
import game.ElsblockProcessor;
import game.Elsgame;
import game.GameMenu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class GameGradeSet extends JDialog{
	private JLabel tipTitle = new JLabel ("请输入游戏难度等级(难度等级大于0)：");
    private JLabel grade = new JLabel("        游戏难度等级:");
	private int gradeNum = 5;
    private JTextField textright = new JTextField (""+gradeNum);
    private JPanel panel1=new JPanel();
    private JPanel panel2 = new JPanel ();
    private JButton button = new JButton ("完成");
	private GameMenu menu;
	public  GameGradeSet(final GameMenu menu) {
	
    super (menu, "游戏难度等级设置", true);
    this.menu = menu;
     
    setSize (300, 120);
	Dimension scrSize = Toolkit.getDefaultToolkit ().getScreenSize ();
	setLocation ( (scrSize.width - getSize ().width) / 2, (scrSize.height - getSize ().height) / 2);
	setResizable (false);
	        
	getContentPane ().setLayout (new BorderLayout ());
	getContentPane ().add (BorderLayout.NORTH, tipTitle);
	
	panel1.setLayout (new GridLayout (1,2,1,2));
	panel1.add(grade);
	panel1.add(textright);
	getContentPane ().add (BorderLayout.CENTER, panel1);
	
	panel2.add (button);
    getContentPane ().add (BorderLayout.SOUTH, panel2);        
   
	
	button.addActionListener (new Click ());  
	this.setVisible(true);
     
	 }
 public class Click implements ActionListener {
	public void actionPerformed (ActionEvent e) {
		ElsblockProcessor.DEFAULT_LEVEL=Integer.parseInt(textright.getText());
		((Elsgame)menu).getCtrlPanel().setTfLevel(ElsblockProcessor.DEFAULT_LEVEL);
		 setVisible(false);
		}
 	}    
}

    



