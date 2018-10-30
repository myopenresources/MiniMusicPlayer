package com.cjh.main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.cjh.config.MusicListConfig;
import com.cjh.ui.EastPanel;
import com.cjh.ui.MusicFrame;
import com.cjh.ui.NorthPanel;

/**
 * ����
 * 
 * @author cjh
 * 
 */
public class MiniMusicPlayer {
	public static void main(String[] args) {
		Runnable doCreateAndShowGUI = new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					//UIManager.put("MenuUI","javax.swing.plaf.basic.BasicMenuUI");
					//UIManager.put("MenuItemUI","javax.swing.plaf.basic.BasicMenuItemUI");
					//UIManager.put("ListUI","javax.swing.plaf.basic.BasicListUI");
					//UIManager.put("MenuItem.arrowIcon","javax.swing.plaf.metal.MetalIconFactory$MenuItemArrowIcon@7ae3c6");
					//UIManager.put("ButtonUI","com.sun.java.swing.plaf.windows.WindowsButtonUI");
					//UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);
					//System.setProperty("java.awt.im.style", "on-the-spot");
				} catch (Exception e) {
					System.out.println("error" + e.getMessage());
				}
				
				//����
				MusicFrame	musicFrame = new MusicFrame();
				
				//����崫���� 
				NorthPanel.setMusicFrame(musicFrame);
				
				//����崫����
				EastPanel.setMusicFrame(musicFrame);
				
				//��ʼ�������б�
				MusicListConfig.getInstance().readMusicToList();
				

			}
		};
		SwingUtilities.invokeLater(doCreateAndShowGUI);

	}
}
