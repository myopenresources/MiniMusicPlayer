package com.cjh.thread;


import javax.media.Player;
import javax.swing.JLabel;
import javax.swing.JSlider;

import com.cjh.music.MusicPlayer;
import com.cjh.ui.SouthPanel;
import com.cjh.util.FormatUtils;

/**
 * 滑动条更新的Runnable
 * @author 
 */
public class SliderUpdateRunnable implements Runnable {

	/**
	 * 南面板
	 */
    private SouthPanel southPanel = SouthPanel.getInstance();
    
    /**
     * 进度划块
     */
    private JSlider progressSlider = southPanel.getProgressSlider();
    
    /**
     * 播放时间
     */
    private JLabel timeLabel = southPanel.getTimeLabel();
    
    /**
     * 播放器
     */
    private Player player = MusicPlayer.getPlayer();
    
    /**
     * 总时间
     */
    private double totalTime = 0;
    
    /**
     * 当前播放时间
     */
    private double nowTime = 0;
    
    /**
     * 当前时间字符串
     */
    private String totalTimeString = "";
    
    /**
     * 线程状态
     */
    private boolean threadState = true;//用来控制线程

    
    public SliderUpdateRunnable() {
    	init();
    }

    /**
     * 刷新时间进度条信息
     */
    public void init() {
        player = MusicPlayer.getPlayer();
        if (player != null) {
            totalTime = player.getDuration().getSeconds();
            totalTimeString = FormatUtils.formatTime(totalTime);
            timeLabel.setText("00:00/" + totalTimeString);
        }
    }

    /**
     * 设置时间进度条的值
     * @param value
     */
    private void setSliderValue(double value) {
        double temp = value / totalTime;
        progressSlider.setValue(FormatUtils.roundDouble(temp * progressSlider.getMaximum()));
        timeLabel.setText(FormatUtils.formatTime(value) + "/" + totalTimeString);
    }

    /**
     * run方法
     */
    public void run() {
        while (threadState) {
            try {
                nowTime = player.getMediaTime().getSeconds();
                setSliderValue(nowTime);
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            } catch (Exception e) {
            }
        }
    }

    public void pause() {
        threadState = false;
    }

    public void resume() {
        threadState = true;
    }
}
