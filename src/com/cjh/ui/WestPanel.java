package com.cjh.ui;

import java.awt.Dimension;

import javax.swing.JPanel;


/**
 * 西面板
 * 
 * @author cjh
 * 
 */
public class WestPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * 音乐信息面板
	 */
	private MusicInfoPanel musicInfoPanel = MusicInfoPanel.getInstance();
	
	/**
	 * 歌词
	 */
	private LRCLabel lrcLabel = LRCLabel.getInstance();
	
	
	/**
	 * 单例
	 */
	private static WestPanel westPanel = new WestPanel();
	
	/**
	 * 私有构造方法
	 */
	private WestPanel(){
		init();
	}
	
	
	/**
	 * 初始化面板
	 */
	public void init(){
		//this.setBackground(Color.blue);
		this.setOpaque(false);
		
		this.setLayout(null);
		
		//边框
		//this.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(245,245,245)));
		
		// 设置大小
		this.setPreferredSize(new Dimension(546, 380));
	
		this.add(lrcLabel);
		
		this.add(musicInfoPanel);
		
		
	}
	
	/**
	 * 获得面板实例 
	 * @return
	 */
	public static WestPanel  genInstance(){
		if(null==westPanel){
			System.out.println("西面板初始化出错！");
			return null;
		}
		
		return westPanel;
	}
	
	
	
}
