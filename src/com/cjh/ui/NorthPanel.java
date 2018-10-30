package com.cjh.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * 北面板
 * 
 * @author cjh
 * 
 */
public class NorthPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 背景图片
	 */
	private Image  backgroudImg= null;
	
	/**
	 * 标题
	 */
	private JLabel title = null;
	
	/**
	 * 最小化播放音乐
	 */
	private JLabel miniMusicLabel  = null;
	
	/**
	 * 标题按钮
	 */
	private MusicButton titleButton= null;
	
	/**
	 * 最小化按钮
	 */
	private MusicButton minButton = null;
	
	/**
	 * 关闭按钮
	 */
	private MusicButton closeButton = null;
	
	/**
	 * 窗体
	 */
	private static MusicFrame  musicFrame=null;
	
	

	/**
	 * 单例
	 */
	private static NorthPanel northPanel = new NorthPanel();

	/**
	 * 私有构造方法
	 */
	private NorthPanel() {
		init();
	}

	/**
	 * 获得面板实例 
	 * @return
	 */
	public static NorthPanel getInstance() {
		if (null == northPanel) {
			System.out.println("北面板初始化出错！");
			return null;
		}
		return northPanel;
	}
	
	/**
	 * 初始化面板
	 */
	private  void init() {
		
		this.setOpaque(false);
		this.setLayout(null);
		//边框
		//this.setBorder(BorderFactory.createLineBorder(new Color(245,245,245), 1));
		
		//设置大小
		this.setPreferredSize(new Dimension(this.getWidth(), 60));
		
		//初始化按钮
		this.initButton();
				
		//事件
		this.event();
		
		//初始化背景与标题颜色
		//this.setPanelBackgroundImg("images/theme/001/NorthBG.jpg");

	}
	
	/**
	 * 初始化按钮
	 */
	private void initButton() {
		
		//标题按钮
		titleButton = new MusicButton(new ImageIcon("images/buttonIcon/titleIcon.png"));
		titleButton.setIconPath("images/buttonIcon/titleIcon");
		titleButton.setToolTipText("爱听音乐");
		titleButton.setBounds(15, 15, 35, 35);
		this.add(titleButton);
		
		//标题
		title = new JLabel("爱听音乐");
		title.setForeground(new Color(10,130,220));
		title.setFont(new Font("宋体",Font.BOLD,12));
		title.setBounds(45, 33, 100, 15);
		this.add(title);
		
		//最小化播放音乐
		miniMusicLabel = new JLabel();
		miniMusicLabel.setFont(new Font("宋体",Font.PLAIN,12));
		miniMusicLabel.setBounds(200,25, 350, 15);
		miniMusicLabel.setVisible(false);
		this.add(miniMusicLabel);
		
		
		//最小化
		minButton = new MusicButton(new ImageIcon("images/buttonIcon/minIcon.png"));
		minButton.setIconPath("images/buttonIcon/minIcon");
		minButton.setToolTipText("最小化");
		minButton.setBounds(705,3, 19, 19);
		this.add(minButton);
		
		//关闭
		closeButton = new MusicButton(new ImageIcon("images/buttonIcon/closeIcon.png"));
		closeButton.setIconPath("images/buttonIcon/closeIcon");
		closeButton.setToolTipText("关闭");
		closeButton.setBounds(725,3, 19, 19);
		this.add(closeButton);
		
	}
	

	
	/**
	 * 事件
	 */
	private void event(){
		
		//标题按钮
		titleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//左击显示
				if(e.getButton()==MouseEvent.BUTTON1){
				 TitlePopupMenu p = TitlePopupMenu.getInstance();
				 p.show(musicFrame,titleButton.getWidth()-20,titleButton.getHeight()+25);
				}
			}
		});
		
		
		// 最小化事件
		minButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				musicFrame.dispose();
			}
		});
		
		
		// 关闭事件
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "您确定要退出爱听音乐吗？(是：退出，否：最小化，取消：保持)","提示",JOptionPane.YES_NO_CANCEL_OPTION);
				if(result==0){
					musicFrame.exitMusic();
				}else if(result==1){
				  musicFrame.dispose();
				}
			}
		});
		
		
		
	}

	
	/**
	 * 设置窗体
	 * @param musicFrame
	 */
	public static void setMusicFrame(MusicFrame musicFrame){
	    NorthPanel.musicFrame = musicFrame;
	}
	
	
	/**
	 * 设置标题图片
	 */
	public  void setTitleIcon(String imgPath){
		this.titleButton.setIcon(new ImageIcon(imgPath));
	}
	
	/**
	 * 设置背景图片
	 * @param imgPath
	 */
	public void setPanelBackgroundImg(String imgPath){
		this.backgroudImg = new ImageIcon(imgPath).getImage();
		repaint();
	}
	
	/**
	 * 设置最小化音乐
	 * @param musicName
	 */
	public void setMiniMusicLabel(String musicStr){
	     this.miniMusicLabel.setText(musicStr);
	}
	
	/**
	 * 设置显示状态
	 * @param sign
	 */
	public void setMiniMusicLabelState(boolean sign){
		this.miniMusicLabel.setVisible(sign);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Graphics2D g2d = (Graphics2D) g;
        // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);//反锯齿
        
        
        //背景图片
        if(null!=backgroudImg){
        	g.drawImage(backgroudImg,0,0,this.getWidth(),this.getHeight(),this);
        }
        
        
    }
	
	

}
