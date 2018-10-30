package com.cjh.ui;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * �����б������尴ť
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
	 * ��ʼ����ť
	 */
	public void init() {
		setUI(new BasicButtonUI());
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setOpaque(false);
	}

}
