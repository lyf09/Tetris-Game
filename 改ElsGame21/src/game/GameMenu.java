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
		jmicreatgame=new JMenuItem("建立游戏");
		jmicreatgame.addActionListener(this);
		
		jmijoingame=new JMenuItem("加入游戏");
		jmijoingame.addActionListener(this);
		jmiexitgame=new JMenuItem("退出游戏");
		jmiexitgame.addActionListener(this);
		
		
		jmihardset=new JMenuItem("难度等级设置");
		jmihardset.addActionListener(this);
		
		jmikeyboardset=new JMenuItem("键盘设置");
		jmikeyboardset.addActionListener(this);
		
		jmiabout=new JMenuItem("作者：java游戏小组");
		jmiversion=new JMenuItem("版本1.0");
		
		jmgame=new JMenu("游戏");
		jmgame.add(jmicreatgame);
		jmgame.addSeparator();
		jmgame.add(jmijoingame);
		jmgame.addSeparator();
		jmgame.add(jmiexitgame);
		
		jmcontrol=new JMenu("控制");
		jmcontrol.add(jmihardset);
		jmcontrol.addSeparator();
		jmcontrol.add(jmikeyboardset);
		
		
		jmhelp=new JMenu("帮助");
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
				gameserverset = new GameServerSet(this,"服务端端口设置",true);//这个方法真正调用传入的还是父类引用指向的子类实例，也就是游戏主类
			}else{
				gameserverset.setVisible(true);//重新构造可视化资源
			}			
			
			System.out.println("服务端设置的端口："+gameserverset.getPortNum());
			
			
		}else if (e.getSource()==jmijoingame){
			jmicreatgame.setEnabled(false);
			
			if(gameclienset==null){
				gameclienset = new GameClientSet(this,"客户端端口设置",true);//这个方法真正调用传入的还是父类引用指向的子类实例，也就是游戏主类
			}else{
				gameclienset.setVisible(true);//重新构造可视化资源
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
