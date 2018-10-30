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
 * ���ְ�ť
 * 
 * @author cjh
 * 
 */
public class MusicButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	private String iconPath = "";

	
	/**
     * �޲ι��췽��
     */
    public MusicButton() {
        init();
    }
    
	/**
	 * �вι��췽��
	 */
	MusicButton(Icon icon) {
		this.setIcon(icon);
		init();
		addMouseListener();
	}
	
	
	/**
	 * ��ʼ����ť
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
     * ���ð�ť��ͼ��·��
     * @param iconPathΪIcon��·��
     */
    public void setIconPath(String iconPath) {
        if (iconPath.length() < 4) {
            System.out.println("ͼ��·������");
            return;
        }
        this.iconPath = iconPath;
    }
	

}
