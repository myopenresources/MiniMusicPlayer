package com.cjh.ui;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * 音乐列表滚动面板按钮
 * 
 * @author cjh
 * 
 */
public class MusicListScrollPaneButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	public MusicListScrollPaneButton() {
		init();
	}

	/**
	 * 初始化按钮
	 */
	public void init() {
		setUI(new BasicButtonUI());
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setOpaque(false);
	}

}
