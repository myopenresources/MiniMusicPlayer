package com.cjh.style;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

/**
 * Ô²½Ç±ß¿ò
 * 
 * @author cjh
 * 
 */
public class SubtleSquareBorder implements Border {

	protected int m_w = 3;
	protected int m_h = 3;
	protected Color m_bottomColor = Color.gray;
	protected boolean roundc = false;

	public SubtleSquareBorder(boolean round_corners) {
		roundc = round_corners;
	}

	public Insets getBorderInsets(Component c) {
		return new Insets(m_h, m_w, m_h, m_w);
	}

	public boolean isBorderOpaque() {
		return true;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
		w = w - 3;
		h = h - 3;
		x++;
		y++;

		if (roundc) {
			g.setColor(m_bottomColor);
			g.drawLine(x, y + 2, x, y + h - 2);
			g.drawLine(x + 2, y, x + w - 2, y);
			g.drawLine(x, y + 2, x + 2, y);
			g.drawLine(x, y + h - 2, x + 2, y + h);
			g.drawLine(x + w, y + 2, x + w, y + h - 2);
			g.drawLine(x + 2, y + h, x + w - 2, y + h);
			g.drawLine(x + w - 2, y, x + w, y + 2);
			g.drawLine(x + w, y + h - 2, x + w - 2, y + h);

		}else {
			g.setColor(m_bottomColor);
			g.drawLine(x, y, x, y + h);
			g.drawLine(x, y, x + w, y);
			g.drawLine(x + w, y, x + w, y + h);
			g.drawLine(x, y + h, x + w, y + h);
		}
	}
}