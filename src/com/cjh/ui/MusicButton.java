package com.cjh.ui;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * 音乐按钮
 * 
 * @author cjh
 * 
 */
public class MusicButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	private String iconPath = "";

	
	/**
     * 无参构造方法
     */
    public MusicButton() {
        init();
    }
    
	/**
	 * 有参构造方法
	 */
	MusicButton(Icon icon) {
		this.setIcon(icon);
		init();
		addMouseListener();
	}
	
	
	/**
	 * 初始化按钮
	 */
	public void init() {
		setUI(new BasicButtonUI());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
		setOpaque(false);
	}
	
	public void addMouseListener() {
	    	addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                 setIcon(new ImageIcon(iconPath + "_.png"));
	            }


				@Override
	            public void mouseExited(MouseEvent e) {
	                 setIcon(new ImageIcon(iconPath + ".png"));
	            }
	    	});
	}

	
	/**
     * 设置按钮的图标路径
     * @param iconPath为Icon的路径
     */
    public void setIconPath(String iconPath) {
        if (iconPath.length() < 4) {
            System.out.println("图标路径出错！");
            return;
        }
        this.iconPath = iconPath;
    }
	

}
