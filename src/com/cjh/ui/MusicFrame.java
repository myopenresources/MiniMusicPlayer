package com.cjh.ui;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.event.MouseInputListener;

import com.cjh.config.MusicListConfig;
import com.cjh.config.MusicPlayerConfig;
import com.cjh.music.MusicPlayer;



/**
 *  ����������
 * @author cjh
 *
 */
public class MusicFrame extends JFrame implements MouseInputListener  {

	private static final long serialVersionUID = 1L;
	
	/**
	 * �����
	 */
	private static NorthPanel northPanel = NorthPanel.getInstance();
	

	/**
	 * �������
	 */
	private MusicPanel  musicPanel = MusicPanel.getInstance();
	
	/**
	 * ����ͼƬ��
	 */
	private static JLabel backImage;
	
	/**
	 * ����ͼƬ
	 */
	private ImageIcon background;
	
	/**
	 *  ����ͼ��
	 */
	private TrayIcon trayIcon;

	/**
	 *  ϵͳ����
	 */
	private SystemTray systemTray;

	/**
	 * ϵͳ�˵�
	 */
	private PopupMenu systemMenu;

	/**
	 * ϵͳ�˵���
	 */
	private MenuItem systemOption, systemExit;
	
	
	private static Point preLocation = new Point();
    private static Point preMouse = new Point();
    private static Rectangle preRectangle = new Rectangle();
    private static Point curMouse = new Point();
    protected JLayeredPane pnl = new JLayeredPane();
    
    //��С���ڸ��ô�С
    protected Rectangle minWindowSize = null;
    
    //ԭʼ����λ�ô�С
    protected Rectangle beforeWindow = new Rectangle();
	
	
	public MusicFrame(){
		init();
	}
	
	public void init(){
		
		//�������ļ��л�ñ���ͼƬ
		String skin = MusicPlayerConfig.getInstance().getPopString("skin");
		if(null==skin || "".equals(skin)){
			skin = "images/theme/skin01.png";
		}
		
		background = new ImageIcon(skin);
		backImage = new JLabel(background);
		backImage.setBounds(0, 0,750,500);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(backImage, new Integer(Integer.MIN_VALUE));
		
		
		
		//��ʼ��ϵͳ����
		this.initSysteTray();
		//�¼�
		this.event();
		
		//���ڴ�С
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		int width = toolKit.getScreenSize().width;
		int height = toolKit.getScreenSize().height;
		
		minWindowSize =	new Rectangle((width - 750) / 2, 0, 750, 120);
		
		this.setBounds((width - 750) / 2, (height - 500) / 2, 750, 500);
		this.setContentPane(musicPanel);
		this.addMouseListener(this);
        this.addMouseMotionListener(this);
		this.setUndecorated(true);
		this.setVisible(true);
		
	
	}
	
	/**
	 * ��ʼ��ϵͳ����
	 */
	private void initSysteTray(){
	   try {
		systemMenu = new PopupMenu();
		systemOption = new MenuItem("��");
		systemExit = new MenuItem("�˳�");
		systemMenu.add(systemOption);
		systemMenu.add(systemExit);

		trayIcon = new TrayIcon(new ImageIcon("images/buttonIcon/icon.png").getImage(),
				"������˫����,�һ��򿪡��˳���\n�汾����������v2.0\n���ߣ��ٱ�С��\n�޸�ʱ�䣺2015-08-02", systemMenu);
		systemTray = SystemTray.getSystemTray();
		
		systemTray.add(trayIcon);
		
	   } catch (AWTException e) {
			e.printStackTrace();
	   }
	}
	
	
	/**
	 * �¼�
	 */
	private void event(){
		// ˫�����̴����ֲ�����
		trayIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					setExtendedState(JFrame.NORMAL);
					setVisible(true);
				}
			}
		});

		// ��������˵��еĴ�
		systemOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.NORMAL);
				setVisible(true);
			}
		});

		// ��������˵��е��˳�
		systemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitMusic();
			}
		});
	}
	
	/**
	 * �˳�����
	 */
	public  void exitMusic(){
		if(null!=MusicPlayer.getPlayer()){
			MusicPlayer.getPlayer().stop();
			MusicPlayer.getPlayer().close();
		}
		
		MusicListConfig.getInstance().saveMusicToText();
		System.exit(0);
	}
	
	/**
	 * ���ñ���ͼƬ
	 * @param imgPath
	 */
	public static void setMusicFrameBackgroundImg(ImageIcon imgIcon){
		backImage.setIcon(imgIcon);
		MusicPlayerConfig.getInstance().setPopString("skin", imgIcon.toString());
	}
	

	/**
	 * ˫���л�С����
	 */
    public void mouseClicked(MouseEvent e) {
		
        if (e.getClickCount() == 2 && e.getY() >= 0 && e.getY() <= 62
                && pnl.getCursor().getType() == Cursor.DEFAULT_CURSOR && !getBounds().equals(minWindowSize)) {
            beforeWindow = getBounds();
            setBounds(minWindowSize);
            //������С��״̬
            northPanel.setMiniMusicLabelState(true);
            repaint();
        } else if (e.getClickCount() == 2 && e.getY() >= 0 && e.getY() <= 62
                && pnl.getCursor().getType() == Cursor.DEFAULT_CURSOR && getBounds().equals(minWindowSize)) {
            setBounds(beforeWindow);
            //������С��״̬
            northPanel.setMiniMusicLabelState(false);
            repaint();
        }
    }

    public void mousePressed(MouseEvent e) {
        preLocation = this.getLocationOnScreen();
        preMouse = e.getLocationOnScreen();
        preRectangle = this.getBounds();
    }

    public void mouseDragged(MouseEvent e) {
        if (!getBounds().equals(minWindowSize)) {
            curMouse = e.getLocationOnScreen();
            int disX = curMouse.x - preMouse.x;
            int disY = curMouse.y - preMouse.y;
            if (pnl.getCursor().getType() == Cursor.DEFAULT_CURSOR) {
                this.setLocation(preLocation.x + disX, preLocation.y + disY);
            } else if (pnl.getCursor().getType() == Cursor.E_RESIZE_CURSOR) {
                this.setSize(preRectangle.width + disX, preRectangle.height);
            } else if (pnl.getCursor().getType() == Cursor.N_RESIZE_CURSOR) {
                this.setBounds(preRectangle.x, preRectangle.y + disY, preRectangle.width, preRectangle.height - disY);
            } else if (pnl.getCursor().getType() == Cursor.W_RESIZE_CURSOR) {
                this.setBounds(preRectangle.x + disX, preRectangle.y, preRectangle.width - disX, preRectangle.height);
            } else if (pnl.getCursor().getType() == Cursor.S_RESIZE_CURSOR) {
                this.setSize(preRectangle.width, preRectangle.height + disY);
            } else if (pnl.getCursor().getType() == Cursor.NE_RESIZE_CURSOR) {
                this.setBounds(preRectangle.x, preRectangle.y + disY, preRectangle.width + disX, preRectangle.height - disY);
            } else if (pnl.getCursor().getType() == Cursor.NW_RESIZE_CURSOR) {
                this.setBounds(preRectangle.x + disX, preRectangle.y + disY, preRectangle.width - disX, preRectangle.height - disY);
            } else if (pnl.getCursor().getType() == Cursor.SW_RESIZE_CURSOR) {
                this.setBounds(preRectangle.x + disX, preRectangle.y, preRectangle.width - disX, preRectangle.height + disY);
            } else if (pnl.getCursor().getType() == Cursor.SE_RESIZE_CURSOR) {
                this.setBounds(preRectangle.x, preRectangle.y, preRectangle.width + disX, preRectangle.height + disY);
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    
    public void mouseMoved(MouseEvent mouseevent) {
        
    }

    public void mouseReleased(MouseEvent e) {
    	
    }
   
	
	

}
