package com.cjh.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;


/**
 * 标题按钮菜单
 * 
 * @author cjh
 * 
 */
public class TitlePopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 添加音乐
	 */
	private JMenuItem addMusic = null;
	
	/**
	 * 添加音乐路径
	 */
	private JMenuItem addMusicPath = null;
	
	/**
	 * 皮肤上传
	 */
	private JMenuItem skinUpload = null;
	
	/**
	 * 皮肤
	 */
	private JMenuItem skin = null;
	
	/**
	 * 设置
	 */
	private JMenuItem setting = null;
	
	
	/**
	 * 关于音乐播放器
	 */
	private JMenuItem aboutMusicPlayer = null;
	
	
	/**
	 * 单例
	 */
	private static TitlePopupMenu titlePopupMenu = new TitlePopupMenu();

	/**
	 * 私有构造方法
	 */
	private TitlePopupMenu() {
		init();
	}
	
	/**
	 * 获得实例 
	 * @return
	 */
	public static TitlePopupMenu getInstance() {
		if (null == titlePopupMenu) {
			System.out.println("标题弹出菜单初始化出错！");
			return null;
		}
		return titlePopupMenu;
	}

	/**
	 * 初始化方法
	 */
	private void init() {
		 addMusic = new JMenuItem("添加音乐",new  ImageIcon("images/buttonIcon/addFileIcon.gif"));
		 addMusicPath = new JMenuItem("添加目录音乐",new  ImageIcon("images/buttonIcon/addFolderIcon.gif"));
		 skinUpload = new JMenuItem("皮肤上传",new  ImageIcon("images/buttonIcon/skinUploadIcon.gif"));
		 skin = new JMenuItem("皮肤更换",new  ImageIcon("images/buttonIcon/skinIcon.gif"));
		 setting = new JMenuItem("播放器设置",new  ImageIcon("images/buttonIcon/settingIcon.gif"));
		 aboutMusicPlayer = new JMenuItem("关于爱听音乐",new  ImageIcon("images/buttonIcon/aboutIcon.gif"));
		
		 this.add(addMusic);
		 this.add(addMusicPath);
		 this.add(skinUpload);
		 this.add(skin);
		 this.add(setting);
		 this.add(aboutMusicPlayer);
		
		 //事件
		 this.event();
	}

	
	/**
	 * 事件
	 */
	private void event(){
		
		//添加音乐
		addMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//左击显示
				if(e.getButton()==MouseEvent.BUTTON1){
					new  AddMusicFileChooser();
				}
			}
		});
		
		//添加目录音乐
		addMusicPath.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//左击显示
				if(e.getButton()==MouseEvent.BUTTON1){
					new  AddMuiscPathChooser();
				}
			}
		});
		
	    //皮肤上传
		skinUpload.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//左击显示
				if(e.getButton()==MouseEvent.BUTTON1){
					new AddSkinFileChooser();
				}
			}
		});
		
		//皮肤
		skin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//左击显示
				if(e.getButton()==MouseEvent.BUTTON1){
					new  SkinFrame().setVisible(true);
					
				}
			}
		});
		
		//播放器设置
		setting.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//左击显示
				if(e.getButton()==MouseEvent.BUTTON1){
					MusicSetFrame.getInstance().setVisible(true);
					
				}
			}
		});
		
		
		//关于爱听音乐
		aboutMusicPlayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//左击显示
				if(e.getButton()==MouseEvent.BUTTON1){
					JOptionPane.showMessageDialog(null, "爱听音乐\n版本：v2.0\n作者：百变小咖\n修改时间：2015-08-02");
				}
			}
		});
		
		
		
		
		
		
	}
}
