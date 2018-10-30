package com.cjh.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


/**
 * �������
 * @author cjh
 *
 */
public class MusicPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	
	/**
	 * �������
	 */
	private static MusicPanel musicPanel = new MusicPanel();
	
	/**
	 * �����
	 */
	private NorthPanel northPanel = NorthPanel.getInstance();
	
	/**
	 * �����
	 */
	private SouthPanel southPanel = SouthPanel.getInstance();
	
	/**
	 * �����
	 */
	private WestPanel  westPanel = WestPanel.genInstance();
	
	/**
	 * �����
	 */
	private EastPanel  eastPanel= EastPanel.getInstance();
	
	/**
	 * �޲ι��췽��
	 */
	private MusicPanel(){
		init();
	}
	
	/**
	 * ��ʼ�����
	 */
	private void init(){
		//this.setBorder(new SubtleSquareBorder(true));
		//���ò��ַ�ʽ
		this.setLayout(new BorderLayout());
		//͸��
		this.setOpaque(false);
		//��ʼ�����ñ߿�
		this.setPanelBorder(new Color(166,166,166));
	
		//������
		this.add("North",northPanel);
		this.add("South",southPanel);
		this.add("West",westPanel);
		this.add("East",eastPanel);
	}

	
	/**
	 * ������ʵ�� 
	 * @return
	 */
	
	public static MusicPanel getInstance(){
		if(null==musicPanel){
			System.out.println("��������ʼ������");
			return null;
		}
		
		return musicPanel;
	}
	
	/**
	 * �������߿�
	 * @param color
	 */
	public void setPanelBorder(Color color){
		this.setBorder(BorderFactory.createLineBorder(color, 1));
	}
	
	/**
	 * ���ñ�����ɫ
	 * @param color
	 */
	public void setPanelBackground(Color color){
		this.setBackground(color);
	}
	
	
	
}
