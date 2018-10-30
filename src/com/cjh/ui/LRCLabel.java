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
 * 歌词显示的类
 * @author 
 */
public class LRCLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	
	private static LRCLabel lrcLabel = new LRCLabel();
    private ArrayList<LRCSentence> lrcStringList = new ArrayList<LRCSentence>();
    private int standOutSign = 0;//突出显示的那一行
    private boolean appearLine = false;
    private int nowTime = 0;
    private MusicInfo lastMusic = null;
    private boolean adjustLrc = false;
    
    //没有歌词时显示
    private static String str = "没找到歌词...";
    
    /**
     * 虚线颜色
     */
    private Color lineColor = new Color(10,130,220);
    
    /**
     * 默认歌词颜色
     */
    private Color generalColor = new Color(10,130,220);
    
    /**
     * 突显歌词颜色
     */
    private Color markColor =  new Color(255,0,0);
    
    /**
     * 线字体
     */
    private Font lineFont = new Font("宋体",Font.PLAIN,13);
    
    
	/**
     * 歌曲字体
     */
    private Font lrcFont = new Font("宋体",Font.PLAIN,13);
    
    /**
     * 突显歌曲字体
     */
    private Font lrcMarkFont = new Font("宋体",Font.PLAIN,13);
    

    private LRCLabel() {
    	this.setBounds(10, 10, 525, 319);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        //歌词虚线颜色
        String str = MusicPlayerConfig.getInstance().getPopString("lineColor");
		if(null!=str && !"".equals(str)){
			String [] color = str.split(",");
			lineColor = new Color(Integer.valueOf(color[0]),Integer.valueOf(color[1]),Integer.valueOf(color[2]));
		}
		
		//默认歌词颜色
		str = MusicPlayerConfig.getInstance().getPopString("generalColor");
		if(null!=str && !"".equals(str)){
			String [] color = str.split(",");
			generalColor = new Color(Integer.valueOf(color[0]),Integer.valueOf(color[1]),Integer.valueOf(color[2]));
		}
		
		//突显歌词颜色
		str = MusicPlayerConfig.getInstance().getPopString("markColor");
		if(null!=str && !"".equals(str)){
			String [] color = str.split(",");
			markColor = new Color(Integer.valueOf(color[0]),Integer.valueOf(color[1]),Integer.valueOf(color[2]));
		}
		
		
		//线字体名
		str = MusicPlayerConfig.getInstance().getPopString("lineFontName");
		String size = MusicPlayerConfig.getInstance().getPopString("lineFontSize"); 
		if(null!=str && !"".equals(str) && null!=size && !"".equals(size)){
			lineFont = new Font(str,Font.PLAIN,Integer.valueOf(size));
		}
		
		//歌曲字体名
		str = MusicPlayerConfig.getInstance().getPopString("lrcFontName");
		size = MusicPlayerConfig.getInstance().getPopString("lrcFontSize"); 
		if(null!=str && !"".equals(str) && null!=size && !"".equals(size)){
			lrcFont = new Font(str,Font.PLAIN,Integer.valueOf(size));
			lrcMarkFont= new Font(str,Font.PLAIN,Integer.valueOf(size)+2);
		}
		
		
		
		
    }

    
   
    /**
     * 获得实例 
     * @return
     */
    public static LRCLabel getInstance() {
        return lrcLabel;
    }
    
    /**
     * 设置线字体
     * @param lineFont
     */
    public void setLineFont(Font lineFont) {
		this.lineFont = lineFont;
	}
    

    /**
     * 设置歌词字体
     * @param lineFont
     */
    public void setLrcFont(Font lrcFont) {
		this.lrcFont = lrcFont;
	}
    

    /**
     * 线颜色
     * @param lineColor
     */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}
	
	

    /**
     * 歌词默认颜色
     * @param generalColor
     */
	public void setGeneralColor(Color generalColor) {
		this.generalColor = generalColor;
	}

	

    /**
     * 突显歌词颜色
     * @param markColor
     */
	public void setMarkColor(Color markColor) {
		this.markColor = markColor;
	}



	/**
     * 刷新歌词显示区域
     */
    public void refresh() {
        MusicInfo currentMusic = MusicPlayer.getCurrentMusic();
        if (lastMusic == null || lastMusic != currentMusic) {
            lrcStringList.clear();
            standOutSign = 0;
            Set<LRCSentence> lrcStringSet = LRCParse.getInstance().getLrcStringSet();
            //将Set增加到ArrayList中便于后面处理
            lrcStringList.addAll(lrcStringSet);
            lastMusic = currentMusic;
            repaint();
        }
    }

    /**
     * 调整歌词进度
     * @param decrease是移动的个数
     * @param media为true是调整音乐进度，false时只调整歌词进度
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
     * 更新歌词
     * @param time时歌词播放的时间
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
     * 是否显示虚线
     * @return
     */
    public boolean isAppearLine() {
        return appearLine;
    }

    /**
     * 设置虚线显示状态
     * @param appearLine
     */
    public void setAppearLine(boolean appearLine) {
        this.appearLine = appearLine;
        repaint();
    }

    /**
     * 是否在调整歌词
     * @return
     */
    public boolean isAdjustLrc() {
        return adjustLrc;
    }

    /**
     * 设置歌词是否在调整的标记
     * @param adjustLrc
     */
    public void setAdjustLrc(boolean adjustLrc) {
        this.adjustLrc = adjustLrc;
    }

    /**
     * 设置突出显示的那一行
     * @param standOutSign为下标
     */
    public void setStandOutSign(int standOutSign) {
        this.standOutSign = standOutSign;
        repaint();
    }
    
    /**
     * 设置无歌词时的提示
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
                //RenderingHints.VALUE_ANTIALIAS_ON);//反锯齿
        
        //线字体
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
                	//突显歌词颜色
                	g2d.setColor(markColor);
                	//字体
                	g2d.setFont(lrcMarkFont);
                } else {
                	//默认歌词颜色
                	g2d.setColor(generalColor);
                	//字体
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
                //默认歌词颜色
                g2d.setColor(generalColor);
                g2d.drawString(str, begin, centerLine);
            }
        }
    }
    
}

