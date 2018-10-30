package com.cjh.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.cjh.config.SkinListConfig;
/**
 * Ƥ��
 * @author cjh
 *
 */
public class SkinFrame extends JFrame {

	private static final long serialVersionUID = 1L;


	/**
	 * ˽�й��췽��
	 */
	public SkinFrame() {
		init();

	}

	/**
	 * ��ʼ������
	 */
	private void init() {

		this.event();

		// Ƥ��ͼƬ����
		int count = 0;

		// Ƥ�����
		JPanel skinPanel = new JPanel();

		// Ƥ���������
		JScrollPane skinScollPane = null;

		// Ƥ����ǩ��
		JLabel label = null;

		ArrayList<String> skinList =  SkinListConfig.getInstance().readSkinToList();
		
		for (String path : skinList) {
			final ImageIcon icon = new ImageIcon(path);
			
			label = new JLabel(icon );
			label.setPreferredSize(new Dimension(300, 200));
			label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			label.setBounds(0, 0, 200, 133);
			label.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					MusicFrame.setMusicFrameBackgroundImg(icon);
				}
			});
			
			skinPanel.add(label);
			count++;
		}

		skinScollPane = new JScrollPane(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		skinScollPane.setViewportView(skinPanel);
		skinPanel.setPreferredSize(new Dimension(300, (count * 205) + 5));

		skinPanel.revalidate();

		this.getContentPane().add(skinScollPane);
		
		this.setResizable(false);
		this.setTitle("Ƥ������");

		// ���ڴ�С
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		int width = toolKit.getScreenSize().width;
		int height = toolKit.getScreenSize().height;

		this.setBounds((width - 340) / 2, (height - 465) / 2, 340, 465);
		this.setVisible(true);
	}

	/**
	 * �¼�
	 */
	private void event() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}

}