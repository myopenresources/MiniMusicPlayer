package com.cjh.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.media.Player;

import com.cjh.music.MusicPlayer;
import com.cjh.ui.LRCLabel;

/**
 * �����ʾ����ļ���
 * @author cjh
 *
 */
public class LRCLabelListener extends MouseAdapter {

	/**
	 * �����ʾlabel
	 */
    private LRCLabel lrcLabel = LRCLabel.getInstance();
    
    /**
     * ������
     */
    private Player player = null;
    
    /**
     * Y��
     */
    private int beginY = 0;
    
    
    private int count = 0;
    
    /**
     * ����
     */
    private static  LRCLabelListener lRCLabelListener = new LRCLabelListener();
    
    
    /**
     * ˽�й��췽��
     */
    private  LRCLabelListener() {
        lrcLabel.addMouseListener(this);
        lrcLabel.addMouseMotionListener(this);
    }
    
    /**
     * ���ʵ��
     * @return
     */
    public static LRCLabelListener getInstance(){
    	return lRCLabelListener;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        player = MusicPlayer.getPlayer();
        if (player != null) {
            lrcLabel.setAppearLine(true);
            beginY = e.getPoint().y;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (player != null) {
            lrcLabel.setAppearLine(false);
            if (lrcLabel.isAdjustLrc()) {
                lrcLabel.adjustLRC(0, true);
                lrcLabel.setAdjustLrc(false);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (player != null) {
            lrcLabel.setAdjustLrc(true);
            int endY = e.getPoint().y;
            if (Math.abs(endY - beginY) > 28) {
                count = (beginY - endY) / 28;
                lrcLabel.adjustLRC(count, false);
                beginY = endY;
            }
        }
    }
}
