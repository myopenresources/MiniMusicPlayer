package com.cjh.thread;


import javax.media.Player;
import javax.swing.JLabel;
import javax.swing.JSlider;

import com.cjh.music.MusicPlayer;
import com.cjh.ui.SouthPanel;
import com.cjh.util.FormatUtils;

/**
 * ���������µ�Runnable
 * @author 
 */
public class SliderUpdateRunnable implements Runnable {

	/**
	 * �����
	 */
    private SouthPanel southPanel = SouthPanel.getInstance();
    
    /**
     * ���Ȼ���
     */
    private JSlider progressSlider = southPanel.getProgressSlider();
    
    /**
     * ����ʱ��
     */
    private JLabel timeLabel = southPanel.getTimeLabel();
    
    /**
     * ������
     */
    private Player player = MusicPlayer.getPlayer();
    
    /**
     * ��ʱ��
     */
    private double totalTime = 0;
    
    /**
     * ��ǰ����ʱ��
     */
    private double nowTime = 0;
    
    /**
     * ��ǰʱ���ַ���
     */
    private String totalTimeString = "";
    
    /**
     * �߳�״̬
     */
    private boolean threadState = true;//���������߳�

    
    public SliderUpdateRunnable() {
    	init();
    }

    /**
     * ˢ��ʱ���������Ϣ
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
     * ����ʱ���������ֵ
     * @param value
     */
    private void setSliderValue(double value) {
        double temp = value / totalTime;
        progressSlider.setValue(FormatUtils.roundDouble(temp * progressSlider.getMaximum()));
        timeLabel.setText(FormatUtils.formatTime(value) + "/" + totalTimeString);
    }

    /**
     * run����
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
