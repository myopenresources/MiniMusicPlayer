package com.cjh.ui;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollPaneUI;

/**
 * 音乐列表滚动面板UI
 * 
 * @author cjh
 * 
 */
public class MusicListScrollPanelUI extends BasicScrollPaneUI {

	/**
	 * 默认安装
	 */
	protected void installDefaults(JScrollPane scrollpane) {
		super.installDefaults(scrollpane);
		JScrollBar vsb = scrollpane.getVerticalScrollBar();
		vsb.setOpaque(false);
		//设置滚动条UI
		vsb.setUI(new MusicListScrollBarUI());
	}

	/**
	 * 创建UI
	 * @param x
	 * @return
	 */
	public static ComponentUI createUI(JComponent x) {
		return new MusicListScrollPanelUI();

	}
}