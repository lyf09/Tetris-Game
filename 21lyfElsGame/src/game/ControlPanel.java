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

	// �������,�������ɱ���;�����һ��������Ϣ�����
	private JPanel plTip = new JPanel(new BorderLayout());
	// ������ʾ��һ��������Ϣ�����
	private TipPanel plTipBlock = new TipPanel();

	// ������壬�������ɱ���ͶԷ���Ϸ�����Ϣ�����
	private JPanel useInfo = new JPanel(new BorderLayout());
	// ������ʾ�Է������Ϣ�����
	private ShowPanel infoBlock = new ShowPanel();

	// ��ʾ��Ϸ��ǰ��Ϣ����壬4��1��
	private JPanel plInfo = new JPanel(new GridLayout(4, 1));

	// ����ͻ����EtchedBorder���͵ı߿�
	private Border border = new EtchedBorder(EtchedBorder.RAISED, Color.white,
			new Color(148, 145, 140));

	private JButton btPlay = new JButton("��ʼ");
	// ��һ���ı�����ʾ�Ѷȼ���
	private JTextField tfLevel = new JTextField("" + 5);
	// ��һ���ı�����ʾ��ҵ÷�
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
		// �󶨼����������ܻ���ɽ���Ķ�ʧ�������������γɽ���֮ǰ���а�
		this.addKeyListener(new KeyControlListener());
		btPlay.addActionListener(new ActionListener() {//��������һ����ʵ����actionlistener�ӿڵ���������
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				elsgame.btnPlay();
				elsgame.getCtrlPanel().requestFocus();
				 
			}
		});
		// �������������3������壬�ڷ���1�У�
		// ÿ�еļ��Ϊ4
		this.setLayout(new GridLayout(3, 1, 0, 4));

		// Ԥ��ʾ���������������߽�
		plTip.add(new JLabel("      ��һ������         "), BorderLayout.NORTH);
		// plTip.add(plTipBlock);���������Ч����ͬ
		plTip.add(plTipBlock, BorderLayout.CENTER);
		plTip.setBorder(border);

		// ��ʾ�Է�������������������߽�
		useInfo.add(new JLabel("   �Է����  "), BorderLayout.NORTH);
		useInfo.add(infoBlock);
		useInfo.setBorder(border);

		// ��Ϸ��Ϣ��ʾ����������ǩ�������ı��򼰱߽�
		btPlay.setFocusable(false);
		plInfo.add(btPlay);
		plInfo.add(tfLevel);

		plInfo.add(new JLabel("     �÷�      "));
		plInfo.add(tfScore);
		plInfo.setBorder(border);

		// �����ı����ǲ��ɱ༭�ģ�ֻ������ʾ��Ϣ

		tfScore.setEditable(false);
		tfLevel.setEditable(false);

		// ��3���ϳ������뵽�������
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
					// ������õ��Ǵ������ҵĻ��������ȳ˵���j��Ȼ����i
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
					// ������õ��Ǵ������ҵĻ��������ȳ˵���j��Ȼ����i
					g.fill3DRect(j * boxWidth, i * boxHeight, boxWidth, boxHeight, true);				
					

				}
			}	

		}

	}

	private class KeyControlListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent ke) {
			// ����Ҫ�����Ϸ���滭���еĵ�ǰ����飬Ϊ��Ϸ������һ������getCurBlock

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
