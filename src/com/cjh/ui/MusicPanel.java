package com.cjh.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


/**
 * 音乐面板
 * @author cjh
 *
 */
public class MusicPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	
	/**
	 * 音乐面板
	 */
	private static MusicPanel musicPanel = new MusicPanel();
	
	/**
	 * 北面板
	 */
	private NorthPanel northPanel = NorthPanel.getInstance();
	
	/**
	 * 南面板
	 */
	private SouthPanel southPanel = SouthPanel.getInstance();
	
	/**
	 * 西面板
	 */
	private WestPanel  westPanel = WestPanel.genInstance();
	
	/**
	 * 东面板
	 */
	private EastPanel  eastPanel= EastPanel.getInstance();
	
	/**
	 * 无参构造方法
	 */
	private MusicPanel(){
		init();
	}
	
	/**
	 * 初始化面板
	 */
	private void init(){
		//this.setBorder(new SubtleSquareBorder(true));
		//设置布局方式
		this.setLayout(new BorderLayout());
		//透明
		this.setOpaque(false);
		//初始化设置边框
		this.setPanelBorder(new Color(166,166,166));
	
		//添加面板
		this.add("North",northPanel);
		this.add("South",southPanel);
		this.add("West",westPanel);
		this.add("East",eastPanel);
	}

	
	/**
	 * 获得面板实例 
	 * @return
	 */
	
	public static MusicPanel getInstance(){
		if(null==musicPanel){
			System.out.println("音乐面板初始化出错！");
			return null;
		}
		
		return musicPanel;
	}
	
	/**
	 * 设置面板边框
	 * @param color
	 */
	public void setPanelBorder(Color color){
		this.setBorder(BorderFactory.createLineBorder(color, 1));
	}
	
	/**
	 * 设置背景颜色
	 * @param color
	 */
	public void setPanelBackground(Color color){
		this.setBackground(color);
	}
	
	
	
}
