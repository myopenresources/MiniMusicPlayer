package com.cjh.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.cjh.config.MusicPlayerConfig;
import com.cjh.music.MusicInfo;

/**
 * JList�е�CellRenderer
 * @author cjh
 *
 */
public class MusicInfoCellRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * �����б�
	 */
	private Vector<MusicInfo> musicInfo = new Vector<MusicInfo>();
	
	/**
	 * Ĭ�ϱ�����ɫ
	 */
	private static Color musicListbgColor = new Color(240, 240, 240);
	
	/**
	 * ѡ�����ɫ
	 */
	private static Color musicListSelectbgColor = new Color(175,208,253);
	
	
	
    /**
     * ���췽��
     * @param musicInfo
     */
    public MusicInfoCellRenderer(Vector<MusicInfo> musicInfo) {
        this.musicInfo = musicInfo;
        this. setOpaque(false);
        this.setLayout(null);
        this.setFont(new Font("����", 12, 12));
        
		// ���ô�С
		this.setPreferredSize(new Dimension(182, 22));
		
        //�����б�����ɫ
        String str = MusicPlayerConfig.getInstance().getPopString("musicListbgColor");
		if(null!=str && !"".equals(str)){
			String [] color = str.split(",");
			musicListbgColor = new Color(Integer.valueOf(color[0]),Integer.valueOf(color[1]),Integer.valueOf(color[2]));
		}
		
		//�����б�ѡ�����ɫ
        str = MusicPlayerConfig.getInstance().getPopString("musicListSelectbgColor");
		if(null!=str && !"".equals(str)){
			String [] color = str.split(",");
			musicListSelectbgColor= new Color(Integer.valueOf(color[0]),Integer.valueOf(color[1]),Integer.valueOf(color[2]));
		}
    }

    /**
     * ���������б�ѡ�����ɫ
     * @param bgColor
     */
    public static void setMusicListSelectbgColor(Color bgColor) {
    	MusicInfoCellRenderer.musicListSelectbgColor = bgColor;
    }

    /**
     * ���������б�����ɫ
     * @param defaultColor
     */
    public static void setMusicListbgColor(Color defaultColor){
    	MusicInfoCellRenderer.musicListbgColor=defaultColor;
    }
    
    /**
     * ʵ��ListCellRenderer�ӿ�
     */
    public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
    	 
         this.setText(" "+(index+1)+"."+ musicInfo.elementAt(index).getMusicName());
    	
         Color background;

         JList.DropLocation dropLocation = list.getDropLocation();
         
        
         
         if (dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index) {
             background = musicListbgColor;
             this.setOpaque(false);
         } else if (isSelected) {
             background = musicListSelectbgColor;
             this.setOpaque(true);
         } else {
             background = musicListbgColor;
             this.setOpaque(false);
         }
         this.setBackground(background);
    	 
    	 
         return this;
    }
}
