package com.cjh.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;

import com.cjh.style.TabPaneStyle;
/**
 * �����б�
 * @author cjh
 *
 */
public class MusicListTabPane extends JTabbedPane{

	
	private static final long serialVersionUID = 1L;

	/**
	 * �����б�
	 */
	private Vector<String> musicVector = null;
	
	/**
	 * �����б����
	 */
	private JList musicList = null;
	
	/**
	 * ��������
	 */
	private JScrollPane musicScrollPane = null;
	
	/**
	 * ����
	 */
	private static MusicListTabPane  musicListTabPane = new MusicListTabPane();
	
	/**
	 * �޲ι��췽��
	 */
	private MusicListTabPane(){
		init();
	}
	
	/**
	 * ��ʼ���б�
	 */
	private  void init(){
		this.setOpaque(false);
		this.setUI(new TabPaneStyle());
		this.setFont(new Font("����", 12, 12));
		this.setFocusable(false);
		this.setBounds(1, -1, 200, 350);
		
		//����
		musicVector = new Vector<String>();
		
		//�б�
		musicList = new JList(musicVector);
		musicList.setFont(new Font("����", 12, 12));
		musicList.setCursor(new Cursor(Cursor.HAND_CURSOR));
		musicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		musicList.setOpaque(false);
		
		musicScrollPane = new JScrollPane(musicList);
		musicScrollPane.setOpaque(false);
		this.add(musicScrollPane,"�����б�");
		
		
	}
	
	/**
	 * ��������б�ʵ�� 
	 * @return
	 */
	public static MusicListTabPane getInstance(){
		if(null==musicListTabPane){
			System.out.println("�����б��ʼ������");
			return null;
		}
		return musicListTabPane;
	}
	
	/**
	 * ���ñ�����ɫ
	 * @param color
	 */
	public void setBackgroundColor(Color color){
		if(null!=musicList){
		    musicList.setBackground(color);
		}
		
	}
	
	
}
