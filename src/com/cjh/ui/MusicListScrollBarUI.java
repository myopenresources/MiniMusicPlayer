package com.cjh.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;


/**
 * �����б������UI
 * 
 * @author cjh
 * 
 */
public class MusicListScrollBarUI extends BasicScrollBarUI {

	/**
	 * ��������ɫ
	 */
	private final Color thumbColor = new Color(45, 155, 255);

	public static ComponentUI createUI(JComponent c) {

		return new MusicListScrollBarUI();

	}

	/**
	 * ��ô�С
	 */
	public Dimension getPreferredSize(JComponent c) {
		Insets insets = c.getInsets();
		int dx = insets.left + insets.right;
		int dy = insets.top + insets.bottom;
		return (scrollbar.getOrientation() == JScrollBar.VERTICAL) ? new Dimension(
				dx + 8, dy + 33)
				: new Dimension(dx + 33, dy + 11);

	}

	/**
	 * ���������水ť
	 */
	protected JButton createDecreaseButton(int orientation) {
		return new MusicListScrollPaneButton();

	}

	/**
	 * ���������水ť
	 */
	protected JButton createIncreaseButton(int orientation) {
		return new MusicListScrollPaneButton();

	}

	/**
	 * ���ƹ���������
	 */
	public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {

		// Graphics2D g2 = (Graphics2D) g;
		// g2.setColor(bgColor);
		// g.fillRect(trackBounds.x, trackBounds.y,
		// trackBounds.width,trackBounds.height);
		// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		// g2.fillRoundRect(trackBounds.x, trackBounds.y,
		// trackBounds.width,trackBounds.height, 8, 8);

	}

	/**
	 * ���ƹ�����
	 */
	public void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {

		if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {

			return;

		}

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(thumbColor);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fillRoundRect(thumbBounds.x+1, thumbBounds.y, thumbBounds.width-1,thumbBounds.height, 8, 8);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		g2.translate(-thumbBounds.x, -thumbBounds.y);

	}

}
