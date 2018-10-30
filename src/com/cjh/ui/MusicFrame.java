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
 *  播放器窗体
 * @author cjh
 *
 */
public class MusicFrame extends JFrame implements MouseInputListener  {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 北面板
	 */
	private static NorthPanel northPanel = NorthPanel.getInstance();
	

	/**
	 * 音乐面板
	 */
	private MusicPanel  musicPanel = MusicPanel.getInstance();
	
	/**
	 * 背景图片框
	 */
	private static JLabel backImage;
	
	/**
	 * 背景图片
	 */
	private ImageIcon background;
	
	/**
	 *  托盘图标
	 */
	private TrayIcon trayIcon;

	/**
	 *  系统托盘
	 */
	private SystemTray systemTray;

	/**
	 * 系统菜单
	 */
	private PopupMenu systemMenu;

	/**
	 * 系统菜单项
	 */
	private MenuItem systemOption, systemExit;
	
	
	private static Point preLocation = new Point();
    private static Point preMouse = new Point();
    private static Rectangle preRectangle = new Rectangle();
    private static Point curMouse = new Point();
    protected JLayeredPane pnl = new JLayeredPane();
    
    //最小窗口搁置大小
    protected Rectangle minWindowSize = null;
    
    //原始窗口位置大小
    protected Rectangle beforeWindow = new Rectangle();
	
	
	public MusicFrame(){
		init();
	}
	
	public void init(){
		
		//从配置文件中获得背景图片
		String skin = MusicPlayerConfig.getInstance().getPopString("skin");
		if(null==skin || "".equals(skin)){
			skin = "images/theme/skin01.png";
		}
		
		background = new ImageIcon(skin);
		backImage = new JLabel(background);
		backImage.setBounds(0, 0,750,500);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(backImage, new Integer(Integer.MIN_VALUE));
		
		
		
		//初始化系统托盘
		this.initSysteTray();
		//事件
		this.event();
		
		//窗口大小
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
	 * 初始化系统托盘
	 */
	private void initSysteTray(){
	   try {
		systemMenu = new PopupMenu();
		systemOption = new MenuItem("打开");
		systemExit = new MenuItem("退出");
		systemMenu.add(systemOption);
		systemMenu.add(systemExit);

		trayIcon = new TrayIcon(new ImageIcon("images/buttonIcon/icon.png").getImage(),
				"操作：双击打开,右击打开、退出。\n版本：爱听音乐v2.0\n作者：百变小咖\n修改时间：2015-08-02", systemMenu);
		systemTray = SystemTray.getSystemTray();
		
		systemTray.add(trayIcon);
		
	   } catch (AWTException e) {
			e.printStackTrace();
	   }
	}
	
	
	/**
	 * 事件
	 */
	private void event(){
		// 双击托盘打开音乐播放器
		trayIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					setExtendedState(JFrame.NORMAL);
					setVisible(true);
				}
			}
		});

		// 点击弹出菜单中的打开
		systemOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.NORMAL);
				setVisible(true);
			}
		});

		// 点击弹出菜单中的退出
		systemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitMusic();
			}
		});
	}
	
	/**
	 * 退出音乐
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
	 * 设置背景图片
	 * @param imgPath
	 */
	public static void setMusicFrameBackgroundImg(ImageIcon imgIcon){
		backImage.setIcon(imgIcon);
		MusicPlayerConfig.getInstance().setPopString("skin", imgIcon.toString());
	}
	

	/**
	 * 双击切换小窗口
	 */
    public void mouseClicked(MouseEvent e) {
		
        if (e.getClickCount() == 2 && e.getY() >= 0 && e.getY() <= 62
                && pnl.getCursor().getType() == Cursor.DEFAULT_CURSOR && !getBounds().equals(minWindowSize)) {
            beforeWindow = getBounds();
            setBounds(minWindowSize);
            //设置最小化状态
            northPanel.setMiniMusicLabelState(true);
            repaint();
        } else if (e.getClickCount() == 2 && e.getY() >= 0 && e.getY() <= 62
                && pnl.getCursor().getType() == Cursor.DEFAULT_CURSOR && getBounds().equals(minWindowSize)) {
            setBounds(beforeWindow);
            //设置最小化状态
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
