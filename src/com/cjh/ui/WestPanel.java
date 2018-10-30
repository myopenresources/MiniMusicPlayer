package com.cjh.ui;

import java.awt.Dimension;

import javax.swing.JPanel;


/**
 * �����
 * 
 * @author cjh
 * 
 */
public class WestPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * ������Ϣ���
	 */
	private MusicInfoPanel musicInfoPanel = MusicInfoPanel.getInstance();
	
	/**
	 * ���
	 */
	private LRCLabel lrcLabel = LRCLabel.getInstance();
	
	
	/**
	 * ����
	 */
	private static WestPanel westPanel = new WestPanel();
	
	/**
	 * ˽�й��췽��
	 */
	private WestPanel(){
		init();
	}
	
	
	/**
	 * ��ʼ�����
	 */
	public void init(){
		//this.setBackground(Color.blue);
		this.setOpaque(false);
		
		this.setLayout(null);
		
		//�߿�
		//this.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(245,245,245)));
		
		// ���ô�С
		this.setPreferredSize(new Dimension(546, 380));
	
		this.add(lrcLabel);
		
		this.add(musicInfoPanel);
		
		
	}
	
	/**
	 * ������ʵ�� 
	 * @return
	 */
	public static WestPanel  genInstance(){
		if(null==westPanel){
			System.out.println("������ʼ������");
			return null;
		}
		
		return westPanel;
	}
	
	
	
}
