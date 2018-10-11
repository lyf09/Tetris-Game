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

   
    private JLabel label = new JLabel ("��Ϸ���Ƽ��趨��");

    private JPanel panel1 = new JPanel ();

    private JLabel labelup = new JLabel ("       ���ϣ�");

    private JTextField textup = new JTextField ("");

    private JLabel labeldown = new JLabel ("       ���£�");

    private JTextField textdown = new JTextField ("");

    private JLabel labelleft = new JLabel ("       ����");

    private JTextField textleft = new JTextField ("");

    private JLabel labelright = new JLabel ("       ���ң�");;

    private JTextField textright = new JTextField ("");

    private JPanel panel2 = new JPanel ();

    private JButton button = new JButton ("���");

    private int up ;

    private int down;

    private int left;

    private int right ;
    private GameMenu menu;

    public GameControlkeySet (final GameMenu menu) {
       super (menu, "��Ϸ���Ƽ�����", true);
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
      
       
        //�򿪶Ի������ʾĿǰ��ֵ
        textup.setText(KeyEvent.getKeyText (up));
        textdown.setText(KeyEvent.getKeyText (down));
        textleft.setText(KeyEvent.getKeyText (left));
        textright.setText(KeyEvent.getKeyText (right));
       
        
        //������exit��ť�رմ��ڣ�ɶҲ������ֱ�����ضԻ���
        /*this.addWindowListener (new WindowAdapter() {
        	
        	@Override
        	public void windowClosing(WindowEvent e) {
        		// TODO Auto-generated method stub
        		super.windowClosing(e);       		
        		JOptionPane.showMessageDialog(null, "������");
        	}
        	
		});*/
        
        //Ϊ��ɰ�ťע��������������������ɺ�Ҫ���µ�����ֵ������������
        button.addActionListener (new Click ());
        
        //Ϊ�ĸ��ı���ע�ᰴ����������ʾ���ݴ��µļ�λ���Լ����ĳ����λ�Ѿ��������ˣ����޷����øü�λ
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
