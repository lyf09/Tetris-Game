package game.config;
import game.Elsgame;
import game.GameMenu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameControlkeySet extends JDialog {

   
    private JLabel label = new JLabel ("游戏控制键设定：");

    private JPanel panel1 = new JPanel ();

    private JLabel labelup = new JLabel ("       向上：");

    private JTextField textup = new JTextField ("");

    private JLabel labeldown = new JLabel ("       向下：");

    private JTextField textdown = new JTextField ("");

    private JLabel labelleft = new JLabel ("       向左：");

    private JTextField textleft = new JTextField ("");

    private JLabel labelright = new JLabel ("       向右：");;

    private JTextField textright = new JTextField ("");

    private JPanel panel2 = new JPanel ();

    private JButton button = new JButton ("完成");

    private int up ;

    private int down;

    private int left;

    private int right ;
    private GameMenu menu;

    public GameControlkeySet (final GameMenu menu) {
       super (menu, "游戏控制键设置", true);
       this.menu = menu;
       
       up=((Elsgame)menu).getCtrlPanel().getUp();
       down=((Elsgame)menu).getCtrlPanel().getDown();
       left=((Elsgame)menu).getCtrlPanel().getLeft();
       right=((Elsgame)menu).getCtrlPanel().getRight();
       setSize (300, 150);
        Dimension scrSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        setLocation ( (scrSize.width - getSize ().width) / 2, (scrSize.height - getSize ().height) / 2);
        setResizable (false);
        
        getContentPane ().setLayout (new BorderLayout ());

        getContentPane ().add (BorderLayout.NORTH, label);

        panel1.setLayout (new GridLayout (2, 4, 5, 5));
        panel1.add (labelup);
        panel1.add (textup);
        panel1.add (labeldown);
        panel1.add (textdown);
        panel1.add (labelleft);
        panel1.add (textleft);
        panel1.add (labelright);
        panel1.add (textright);
        getContentPane ().add (BorderLayout.CENTER, panel1);

        panel2.add (button);
        getContentPane ().add (BorderLayout.SOUTH, panel2);        
      
       
        //打开对话框后，显示目前的值
        textup.setText(KeyEvent.getKeyText (up));
        textdown.setText(KeyEvent.getKeyText (down));
        textleft.setText(KeyEvent.getKeyText (left));
        textright.setText(KeyEvent.getKeyText (right));
       
        
        //如果点击exit按钮关闭窗口，啥也不做，直接隐藏对话框
        /*this.addWindowListener (new WindowAdapter() {
        	
        	@Override
        	public void windowClosing(WindowEvent e) {
        		// TODO Auto-generated method stub
        		super.windowClosing(e);       		
        		JOptionPane.showMessageDialog(null, "哈哈哈");
        	}
        	
		});*/
        
        //为完成按钮注册监听器，如果点击点击完成后，要将新的设置值保存至属性中
        button.addActionListener (new Click ());
        
        //为四个文本框注册按键监听，显示和暂存新的键位，以及如果某个键位已经被设置了，就无法设置该键位
        textup.addKeyListener (new UPkey ());
        textdown.addKeyListener (new DOWNkey ());
        textleft.addKeyListener (new LEFTkey ());
        textright.addKeyListener (new RIGHTkey ());
        this.setVisible(true);
    }

    private class Click implements ActionListener {
        public void actionPerformed (ActionEvent e) {     
        	((Elsgame)menu).getCtrlPanel().setUp(up);
        	((Elsgame)menu).getCtrlPanel().setDown(down);
        	((Elsgame)menu).getCtrlPanel().setLeft(left);
        	((Elsgame)menu).getCtrlPanel().setRight(right);
           setVisible(false);
        }
    }    
    
    private class UPkey extends KeyAdapter {
       
        public void keyReleased (KeyEvent e) {
            if (e.getKeyCode () != down && e.getKeyCode () != left && e.getKeyCode () != right) {
                up = e.getKeyCode ();
                textup.setText (KeyEvent.getKeyText (up));               
            }else{
                textup.setText (KeyEvent.getKeyText (up));
            }
        }
    }

    private class DOWNkey extends KeyAdapter {
        public void keyReleased (KeyEvent e) {
            if (e.getKeyCode () != up && e.getKeyCode () != left && e.getKeyCode () != right) {
                down = e.getKeyCode ();
                textdown.setText (KeyEvent.getKeyText (down));
            }else{
                textdown.setText (KeyEvent.getKeyText (down));
            }

        }

    }

    private class LEFTkey extends KeyAdapter {
        public void keyReleased (KeyEvent e) {
            if (e.getKeyCode () != up && e.getKeyCode () != down && e.getKeyCode () != right) {
                left = e.getKeyCode ();
                textleft.setText (KeyEvent.getKeyText (left));
            }else{
                textleft.setText (KeyEvent.getKeyText (left));
            }

        }
    }

    private class RIGHTkey extends KeyAdapter {
        public void keyReleased (KeyEvent e) {
            if (e.getKeyCode () != up && e.getKeyCode () != down && e.getKeyCode () != left) {
                right = e.getKeyCode ();
                textright.setText (KeyEvent.getKeyText (right));
            }else{
                textright.setText (KeyEvent.getKeyText (right));
            }

        }
    }

  
}
