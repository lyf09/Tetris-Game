package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class ControlPanel extends JPanel {

	// 容器面板,用于容纳标题和具体下一个方块信息的面板
	private JPanel plTip = new JPanel(new BorderLayout());
	// 用于提示下一个方块信息的面板
	private TipPanel plTipBlock = new TipPanel();

	// 容器面板，用于容纳标题和对方游戏玩家信息的面板
	private JPanel useInfo = new JPanel(new BorderLayout());
	// 用于显示对方玩家信息的面板
	private ShowPanel infoBlock = new ShowPanel();

	// 显示游戏当前信息的面板，4行1列
	private JPanel plInfo = new JPanel(new GridLayout(4, 1));

	// 设置突出的EtchedBorder类型的边框
	private Border border = new EtchedBorder(EtchedBorder.RAISED, Color.white,
			new Color(148, 145, 140));

	private JButton btPlay = new JButton("开始");
	// 用一个文本域显示难度级别
	private JTextField tfLevel = new JTextField("" + 5);
	// 用一个文本域显示玩家得分
	private JTextField tfScore = new JTextField("0");

	private Elsgame elsgame;
	
	private int up=KeyEvent.VK_UP;
	private int down=KeyEvent.VK_DOWN;
	private int left=KeyEvent.VK_LEFT;
	private int right=KeyEvent.VK_RIGHT;
	

	
	
	
	public void setTfLevel(int level) {
		tfLevel.setText(level+"");
	}
	public int getTfLevel() {
		return Integer.parseInt(tfLevel.getText());
	}
	public void setScore(int score) {
		tfScore.setText(score+"");;
	}

	public int getUp() {
		return up;
	}

	public int getDown() {
		return down;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public void setUp(int up) {
		this.up = up;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public void setbtPlayenable(boolean enable){
		btPlay.setEnabled(enable);
	}

	public void setTipPanelStyle(int style) {
		plTipBlock.setStyle(style);
	}
	public void setCanvasDate(int[][] canvasdata) {
		infoBlock.setCanvasdata(canvasdata);
	}
	

	public ControlPanel(final Elsgame elsgame) {
		this.elsgame = elsgame;
		// 绑定监听的语句可能会造成焦点的丢失，所以我们在形成界面之前进行绑定
		this.addKeyListener(new KeyControlListener());
		btPlay.addActionListener(new ActionListener() {//匿名对象，一个的实现了actionlistener接口的匿名对象
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				elsgame.btnPlay();
				elsgame.getCtrlPanel().requestFocus();
				 
			}
		});
		// 整个控制面板有3个子面板，摆放在1列，
		// 每行的间距为4
		this.setLayout(new GridLayout(3, 1, 0, 4));

		// 预提示面板的两个构件及边界
		plTip.add(new JLabel("      下一个方块         "), BorderLayout.NORTH);
		// plTip.add(plTipBlock);和下面语句效果相同
		plTip.add(plTipBlock, BorderLayout.CENTER);
		plTip.setBorder(border);

		// 提示对方玩家面板的两个构件及边界
		useInfo.add(new JLabel("   对方玩家  "), BorderLayout.NORTH);
		useInfo.add(infoBlock);
		useInfo.setBorder(border);

		// 游戏信息显示面板的两个标签和两个文本域及边界
		btPlay.setFocusable(false);
		plInfo.add(btPlay);
		plInfo.add(tfLevel);

		plInfo.add(new JLabel("     得分      "));
		plInfo.add(tfScore);
		plInfo.setBorder(border);

		// 两个文本域都是不可编辑的，只用于显示信息

		tfScore.setEditable(false);
		tfLevel.setEditable(false);

		// 将3个合成面板加入到主面板中
		this.add(plTip);
		this.add(plInfo);
		this.add(useInfo);

	}

	private class TipPanel extends JPanel {
		private int style;
		private int boxWidth, boxHeight;
		private Color boxColor = Color.lightGray;
		private Color blockColor = Color.orange;

		public void setStyle(int style) {
			this.style = style;
			repaint();
		}

		public void fanning() {
			boxWidth = getSize().width / 4;
			boxHeight = getSize().height / 4;
		}

		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			this.fanning();
			int key = 0x8000;
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					Color color = ((style & key) != 0?blockColor:boxColor);					
					g.setColor(color);
					// 这里采用的是从左至右的画，所以先乘的是j，然后是i
					g.fill3DRect(j * boxWidth, i * boxHeight, boxWidth, boxHeight, true);					
					key >>= 1;

				}
			}

			

		}

	}

	private class ShowPanel extends JPanel {
		
	
		private int boxWidth, boxHeight;
		private Color boxColor = Color.lightGray;
		private Color blockColor = Color.orange;	
		private boolean isfanning = false;
		private int [][] canvasdata;	
		private Color color=boxColor ;
		
		public void setCanvasdata(int[][] canvasdata) {
			this.canvasdata = canvasdata;
			repaint();
		}

		public void fanning() {
			boxWidth = getSize().width / 12;
			boxHeight = getSize().height / 22;
			this.isfanning=true;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(!isfanning)
			this.fanning();
			
			for (int i = 0; i < 22; i++) {
				for (int j = 0; j <12; j++) {
					if(canvasdata!=null){
					color =(canvasdata[i][j]==1?blockColor:boxColor);
					}
					g.setColor(color);
					// 这里采用的是从左至右的画，所以先乘的是j，然后是i
					g.fill3DRect(j * boxWidth, i * boxHeight, boxWidth, boxHeight, true);				
					

				}
			}	

		}

	}

	private class KeyControlListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent ke) {
			// 首先要获得游戏界面画布中的当前活动方块，为游戏类增加一个函数getCurBlock

			ElsblockProcessor blockpro = elsgame.getElsblockprocessor();
			if (ke.getKeyCode() == up) {

				blockpro.turnNext();
				return;
			}
			if (ke.getKeyCode() == down) {

				blockpro.moveDown();
				return;
			}
			if (ke.getKeyCode() == left) {
				blockpro.moveLeft();
				return;
			}
			if (ke.getKeyCode() ==right) {
				blockpro.moveRight();
				return;
			}

		}

	}

}
