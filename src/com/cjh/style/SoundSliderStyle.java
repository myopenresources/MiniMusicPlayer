package com.cjh.style;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * 声音划块样式
 * 
 * @author cjh
 * 
 */
public class SoundSliderStyle extends BasicSliderUI {

	/**
	 * 颜色
	 */
	private static Color color = new Color(45, 155, 255);

	public SoundSliderStyle(JSlider b) {
		super(b);
		b.setFocusable(false);
	}

	/**
	 * 设置颜色
	 * 
	 * @param color
	 */
	public static void setSliderColor(Color color) {
		SoundSliderStyle.color = color;
	}

	@Override
	public void paintThumb(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(color.darker());
		g2d.fillOval(thumbRect.x, thumbRect.y + 3, thumbRect.width,
				thumbRect.height - 9);
	}

	@Override
	public void paintTrack(Graphics g) {
		int cy, cw;
		Rectangle trackBounds = trackRect;
		if (slider.getOrientation() == JSlider.HORIZONTAL) {
			Graphics2D g2 = (Graphics2D) g;
			cy = (trackBounds.height / 2) - 3;
			cw = trackBounds.width;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.translate(trackBounds.x, trackBounds.y + cy);

			// 背景颜色
			g2.setPaint(Color.lightGray);
			g2.fillRoundRect(0, -cy + 5, cw, cy, 8, 8);

			int trackLeft = 0;
			int trackRight = 0;
			trackRight = trackRect.width - 1;

			int middleOfThumb = 0;
			int fillLeft = 0;
			int fillRight = 0;

			// 坐标换算
			middleOfThumb = thumbRect.x + (thumbRect.width / 2);
			middleOfThumb -= trackRect.x;

			if (!drawInverted()) {
				fillLeft = !slider.isEnabled() ? trackLeft : trackLeft + 1;
				fillRight = middleOfThumb;
			} else {
				fillLeft = middleOfThumb;
				fillRight = !slider.isEnabled() ? trackRight - 1
						: trackRight - 2;
			}
			// 设定渐变 -滑过区域的颜色设置
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
