package com.cjh.ui;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Set;

import javax.media.Time;
import javax.swing.JLabel;


import com.cjh.config.MusicPlayerConfig;
import com.cjh.music.MusicInfo;
import com.cjh.music.MusicPlayer;
import com.cjh.thread.SliderUpdateThread;
import com.cjh.util.FormatUtils;
import com.cjh.util.LRCParse;
import com.cjh.util.LRCSentence;


/**
 * �����ʾ����
 * @author 
 */
public class LRCLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	
	private static LRCLabel lrcLabel = new LRCLabel();
    private ArrayList<LRCSentence> lrcStringList = new ArrayList<LRCSentence>();
    private int standOutSign = 0;//ͻ����ʾ����һ��
    private boolean appearLine = false;
    private int nowTime = 0;
    private MusicInfo lastMusic = null;
    private boolean adjustLrc = false;
    
    //û�и��ʱ��ʾ
    private static String str = "û�ҵ����...";
    
    /**
     * ������ɫ
     */
    private Color lineColor = new Color(10,130,220);
    
    /**
     * Ĭ�ϸ����ɫ
     */
    private Color generalColor = new Color(10,130,220);
    
    /**
     * ͻ�Ը����ɫ
     */
    private Color markColor =  new Color(255,0,0);
    
    /**
     * ������
     */
    private Font lineFont = new Font("����",Font.PLAIN,13);
    
    
	/**
     * ��������
     */
    private Font lrcFont = new Font("����",Font.PLAIN,13);
    
    /**
     * ͻ�Ը�������
     */
    private Font lrcMarkFont = new Font("����",Font.PLAIN,13);
    

    private LRCLabel() {
    	this.setBounds(10, 10, 525, 319);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        //���������ɫ
        String str = MusicPlayerConfig.getInstance().getPopString("lineColor");
		if(null!=str && !"".equals(str)){
			String [] color = str.split(",");
			lineColor = new Color(Integer.valueOf(color[0]),Integer.valueOf(color[1]),Integer.valueOf(color[2]));
		}
		
		//Ĭ�ϸ����ɫ
		str = MusicPlayerConfig.getInstance().getPopString("generalColor");
		if(null!=str && !"".equals(str)){
			String [] color = str.split(",");
			generalColor = new Color(Integer.valueOf(color[0]),Integer.valueOf(color[1]),Integer.valueOf(color[2]));
		}
		
		//ͻ�Ը����ɫ
		str = MusicPlayerConfig.getInstance().getPopString("markColor");
		if(null!=str && !"".equals(str)){
			String [] color = str.split(",");
			markColor = new Color(Integer.valueOf(color[0]),Integer.valueOf(color[1]),Integer.valueOf(color[2]));
		}
		
		
		//��������
		str = MusicPlayerConfig.getInstance().getPopString("lineFontName");
		String size = MusicPlayerConfig.getInstance().getPopString("lineFontSize"); 
		if(null!=str && !"".equals(str) && null!=size && !"".equals(size)){
			lineFont = new Font(str,Font.PLAIN,Integer.valueOf(size));
		}
		
		//����������
		str = MusicPlayerConfig.getInstance().getPopString("lrcFontName");
		size = MusicPlayerConfig.getInstance().getPopString("lrcFontSize"); 
		if(null!=str && !"".equals(str) && null!=size && !"".equals(size)){
			lrcFont = new Font(str,Font.PLAIN,Integer.valueOf(size));
			lrcMarkFont= new Font(str,Font.PLAIN,Integer.valueOf(size)+2);
		}
		
		
		
		
    }

    
   
    /**
     * ���ʵ�� 
     * @return
     */
    public static LRCLabel getInstance() {
        return lrcLabel;
    }
    
    /**
     * ����������
     * @param lineFont
     */
    public void setLineFont(Font lineFont) {
		this.lineFont = lineFont;
	}
    

    /**
     * ���ø������
     * @param lineFont
     */
    public void setLrcFont(Font lrcFont) {
		this.lrcFont = lrcFont;
	}
    

    /**
     * ����ɫ
     * @param lineColor
     */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}
	
	

    /**
     * ���Ĭ����ɫ
     * @param generalColor
     */
	public void setGeneralColor(Color generalColor) {
		this.generalColor = generalColor;
	}

	

    /**
     * ͻ�Ը����ɫ
     * @param markColor
     */
	public void setMarkColor(Color markColor) {
		this.markColor = markColor;
	}



	/**
     * ˢ�¸����ʾ����
     */
    public void refresh() {
        MusicInfo currentMusic = MusicPlayer.getCurrentMusic();
        if (lastMusic == null || lastMusic != currentMusic) {
            lrcStringList.clear();
            standOutSign = 0;
            Set<LRCSentence> lrcStringSet = LRCParse.getInstance().getLrcStringSet();
            //��Set���ӵ�ArrayList�б��ں��洦��
            lrcStringList.addAll(lrcStringSet);
            lastMusic = currentMusic;
            repaint();
        }
    }

    /**
     * ������ʽ���
     * @param decrease���ƶ��ĸ���
     * @param mediaΪtrue�ǵ������ֽ��ȣ�falseʱֻ������ʽ���
     */
    public void adjustLRC(int decrease, boolean media) {
        if (lrcStringList.size() != 0 && (standOutSign + decrease) < lrcStringList.size() - 1
                && (standOutSign + decrease) >= 0) {
            this.standOutSign = standOutSign + decrease;
            nowTime = lrcStringList.get(this.standOutSign).getTime();
            if (media) {
                MusicPlayer.pauseMusic();
                SliderUpdateThread.pause();
                MusicPlayer.getPlayer().setMediaTime(new Time(lrcStringList.get(this.standOutSign).getTime() * 1.0));
                MusicPlayer.playMusic();
                SliderUpdateThread.resume();
            }
            repaint();
        }
    }

    /**
     * ���¸��
     * @param timeʱ��ʲ��ŵ�ʱ��
     */
    public void updateLRC(int time) {
        if (!isAdjustLrc()) {
            this.nowTime = time;
            int size = lrcStringList.size();
            for (int i = 0; i < size; i++) {
                if (time >= lrcStringList.get(size - 1).getTime()) {
                    standOutSign = size - 1;
                } else if (time >= lrcStringList.get(i).getTime() && time <= lrcStringList.get(i + 1).getTime()) {
                    standOutSign = i;
                }
            }
            repaint();
        }
    }

    /**
     * �Ƿ���ʾ����
     * @return
     */
    public boolean isAppearLine() {
        return appearLine;
    }

    /**
     * ����������ʾ״̬
     * @param appearLine
     */
    public void setAppearLine(boolean appearLine) {
        this.appearLine = appearLine;
        repaint();
    }

    /**
     * �Ƿ��ڵ������
     * @return
     */
    public boolean isAdjustLrc() {
        return adjustLrc;
    }

    /**
     * ���ø���Ƿ��ڵ����ı��
     * @param adjustLrc
     */
    public void setAdjustLrc(boolean adjustLrc) {
        this.adjustLrc = adjustLrc;
    }

    /**
     * ����ͻ����ʾ����һ��
     * @param standOutSignΪ�±�
     */
    public void setStandOutSign(int standOutSign) {
        this.standOutSign = standOutSign;
        repaint();
    }
    
    /**
     * �����޸��ʱ����ʾ
     * @param str
     */
    public static void setStr(String str){
    	LRCLabel.str=str;
    }
    
    
    public ArrayList<LRCSentence> getLrcStringList(){
    	return  lrcStringList;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                //RenderingHints.VALUE_ANTIALIAS_ON);//�����
        
        //������
        g2d.setFont(lineFont);
        int size = lrcStringList.size();
        int centerLine = getHeight() / 2;
        if (appearLine && size != 0) {
            g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{6.0f}, 0.0f));
            g2d.setColor(lineColor);
            g2d.drawLine(0, centerLine, getWidth(), centerLine);
            g2d.drawString(FormatUtils.formatTime(nowTime), 5, centerLine - 1);
        }
        
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (i == standOutSign) {
                	//ͻ�Ը����ɫ
                	g2d.setColor(markColor);
                	//����
                	g2d.setFont(lrcMarkFont);
                } else {
                	//Ĭ�ϸ����ɫ
                	g2d.setColor(generalColor);
                	//����
                	g2d.setFont(lrcFont);
                }
                String str = lrcStringList.get(i).getLrcString();
                int strWidth = g2d.getFontMetrics().charsWidth(str.toCharArray(), 0, str.length());
                int begin = (getWidth() - strWidth) / 2;
                g2d.drawString(str, begin, centerLine + 8 + 28 * (i - standOutSign));
            }
        } else {
            if (MusicPlayer.getPlayer() != null) {
                int strWidth = g2d.getFontMetrics().charsWidth(str.toCharArray(), 0, str.length());
                int begin = (getWidth() - strWidth) / 2;
                //Ĭ�ϸ����ɫ
                g2d.setColor(generalColor);
                g2d.drawString(str, begin, centerLine);
            }
        }
    }
    
}

