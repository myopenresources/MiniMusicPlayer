package com.cjh.style;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * ������������ʽ
 * @author cjh
 *
 */
public class ProgressSliderStyle  extends BasicSliderUI {

	 /**
	  * ��ɫ
	  */
	 private static Color color = new Color(45,155,255);

	    public ProgressSliderStyle(JSlider b) {
	        super(b);
	        b.setFocusable(false);
	    }

	    /**
	     * ������ɫ
	     * @param color
	     */
	    public static void setSliderColor(Color color) {
	    	ProgressSliderStyle.color = color;
	    }

	    @Override
	    public void paintThumb(Graphics g) {
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setColor(color.darker());
	        g2d.fillOval(thumbRect.x, thumbRect.y + 2, thumbRect.width, thumbRect.height - 9);
	    }

	    @Override
	    public void paintTrack(Graphics g) {
	        int cy, cw;
	        Rectangle trackBounds = trackRect;
	        if (slider.getOrientation() == JSlider.HORIZONTAL) {
	            Graphics2D g2 = (Graphics2D) g;
	            cy = (trackBounds.height / 2) - 5;
	            cw = trackBounds.width;

	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                    RenderingHints.VALUE_ANTIALIAS_ON);
	            g2.translate(trackBounds.x, trackBounds.y + cy);

	            //������ɫ
	            g2.setPaint(Color.lightGray);
	            g2.fillRoundRect(0, -cy + 5, cw, cy, 8, 8);

	            int trackLeft = 0;
	            int trackRight = 0;
	            trackRight = trackRect.width - 1;

	            int middleOfThumb = 0;
	            int fillLeft = 0;
	            int fillRight = 0;

	            // ���껻��
	            middleOfThumb = thumbRect.x + (thumbRect.width / 2);
	            middleOfThumb -= trackRect.x;

	            if (!drawInverted()) {
	                fillLeft = !slider.isEnabled() ? trackLeft : trackLeft + 1;
	                fillRight = middleOfThumb;
	            } else {
	                fillLeft = middleOfThumb;
	                fillRight = !slider.isEnabled() ? trackRight - 1 : trackRight - 2;
	            }
	            // �趨���� -�����������ɫ����
	            g2.setColor(color);
	            g2.fillRoundRect(0, -cy + 5, fillRight - fillLeft, cy, 8, 8);

	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                    RenderingHints.VALUE_ANTIALIAS_OFF);
	            g2.translate(-trackBounds.x, -(trackBounds.y + cy));
	        } else {
	            super.paintTrack(g);
	        }
	    }
}
