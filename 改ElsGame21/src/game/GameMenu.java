package game;

import game.config.GameClientSet;
import game.config.GameControlkeySet;
import game.config.GameGradeSet;
import game.config.GameServerSet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameMenu extends JFrame implements ActionListener{	
	
	private  JMenuItem jmicreatgame=null;
	private  JMenuItem jmijoingame=null;
	private  JMenuItem jmiexitgame=null;
	
	private JMenuItem jmihardset=null;
	private JMenuItem jmikeyboardset=null;
	
	private JMenuItem jmiabout=null;
	private JMenuItem jmiversion=null;
	
	private JMenu jmgame=null;
	private JMenu jmcontrol=null;
	private JMenu jmhelp=null;	
	private JMenuBar jmbar=null;
	private  GameServerSet gameserverset;
	private GameClientSet gameclienset;
	
	
	
	
	
	public GameServerSet getGameserverset() {
		return gameserverset;
	}
	public GameClientSet getGameclienset() {
		return gameclienset;
	}
	
	
	public GameMenu() {
		// TODO Auto-generated constructor stub
		jmicreatgame=new JMenuItem("������Ϸ");
		jmicreatgame.addActionListener(this);
		
		jmijoingame=new JMenuItem("������Ϸ");
		jmijoingame.addActionListener(this);
		jmiexitgame=new JMenuItem("�˳���Ϸ");
		jmiexitgame.addActionListener(this);
		
		
		jmihardset=new JMenuItem("�Ѷȵȼ�����");
		jmihardset.addActionListener(this);
		
		jmikeyboardset=new JMenuItem("��������");
		jmikeyboardset.addActionListener(this);
		
		jmiabout=new JMenuItem("���ߣ�java��ϷС��");
		jmiversion=new JMenuItem("�汾1.0");
		
		jmgame=new JMenu("��Ϸ");
		jmgame.add(jmicreatgame);
		jmgame.addSeparator();
		jmgame.add(jmijoingame);
		jmgame.addSeparator();
		jmgame.add(jmiexitgame);
		
		jmcontrol=new JMenu("����");
		jmcontrol.add(jmihardset);
		jmcontrol.addSeparator();
		jmcontrol.add(jmikeyboardset);
		
		
		jmhelp=new JMenu("����");
		jmhelp.add(jmiabout);
		jmhelp.addSeparator();
		jmhelp.add(jmiversion);
		
		jmbar=new JMenuBar();
		jmbar.add(jmgame);
		jmbar.add(jmcontrol);
		jmbar.add(jmhelp);		
		this.setJMenuBar(jmbar);	
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jmicreatgame){
			
			jmijoingame.setEnabled(false);
			
			if(gameserverset==null){
				gameserverset = new GameServerSet(this,"����˶˿�����",true);//��������������ô���Ļ��Ǹ�������ָ�������ʵ����Ҳ������Ϸ����
			}else{
				gameserverset.setVisible(true);//���¹�����ӻ���Դ
			}			
			
			System.out.println("��������õĶ˿ڣ�"+gameserverset.getPortNum());
			
			
		}else if (e.getSource()==jmijoingame){
			jmicreatgame.setEnabled(false);
			
			if(gameclienset==null){
				gameclienset = new GameClientSet(this,"�ͻ��˶˿�����",true);//��������������ô���Ļ��Ǹ�������ָ�������ʵ����Ҳ������Ϸ����
			}else{
				gameclienset.setVisible(true);//���¹�����ӻ���Դ
			}	
		}else if (e.getSource()==jmiexitgame){
			System.exit(1);
		}else if (e.getSource() == jmikeyboardset) {
			GameControlkeySet keycontrol = new GameControlkeySet(this);
			
		}
		else if (e.getSource() == jmihardset) {
			GameGradeSet keycontrol = new GameGradeSet(this);
			
		}

		
	}
	

}
