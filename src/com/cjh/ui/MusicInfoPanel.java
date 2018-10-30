package com.cjh.ui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.cjh.music.MusicInfo;

/**
 * ������Ϣ���
 * @author cjh
 *
 */
public class MusicInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * ��ǰ��������
	 */
	private JLabel currentMusicLabel = null;
	
	/**
	 * ����
	 */
	private JLabel musicArtistLabel = null;
	
	/**
	 * ר��
	 */
	private JLabel musicAlbumLabel = null;
	
	/**
	 * ��
	 */
	//private JLabel yearLabel = null;
	
	/**
	 * ����״̬
	 */
	private JLabel playStateLabel =null;
	
	/**
	 * ����
	 */
	private static MusicInfoPanel musicInfoPanel = new MusicInfoPanel();
	
	
	/**
	 * ˽�й��췽��
	 */
	private MusicInfoPanel(){
		init();
	}
	
	/**
	 * ��ʼ������
	 */
	private void init(){
		
		this.setOpaque(false);
		
		this.setLayout(null);
		
		// ���ô�С
		this.setPreferredSize(new Dimension(545, 70));
		
		this.setBounds(0, 345, 545, 70);
		
		currentMusicLabel = new JLabel("");
		//currentMusicLabel.setForeground(new Color(10,130,220));
		currentMusicLabel.setFont(new Font("����",Font.PLAIN,12));
		currentMusicLabel.setBounds(20, 0, 300, 15);
		this.add(currentMusicLabel);
		
		playStateLabel = new JLabel("");
		//playStateLabel.setForeground(new Color(10,130,220));
		playStateLabel.setFont(new Font("����",Font.PLAIN,12));
		playStateLabel.setBounds(350,0, 300, 15);
		this.add(playStateLabel);
		
		musicAlbumLabel = new JLabel("");
		musicAlbumLabel.setBounds(20, 15, 300, 15);
		//musicAlbumLabel.setForeground(new Color(10,130,220));
		musicAlbumLabel.setFont(new Font("����",Font.PLAIN,12));
		this.add(musicAlbumLabel);
		
		musicArtistLabel = new JLabel("");
		musicArtistLabel.setBounds(350, 15, 300, 15);
		//musicArtistLabel.setForeground(new Color(10,130,220));
		musicArtistLabel.setFont(new Font("����",Font.PLAIN,12));
		this.add(musicArtistLabel);
		
		
		
		/*yearLabel = new JLabel("�꣺aaaaaaaa");
		yearLabel.setBounds(350, 15, 300, 15);
		this.add(yearLabel);*/
		
	}
	
	/**
	 * ���ʵ��
	 * @return
	 */
	public static MusicInfoPanel getInstance(){
		if(null==musicInfoPanel){
			System.out.println("������Ϣ����ʼ������");
			return null;
		}
		return musicInfoPanel;
	}
	
	/**
	 * ����������Ϣ
	 * @param musicInfo
	 */
	public void setMusicInfoToPanel(MusicInfo musicInfo){
		if(null!=musicInfo){
		  currentMusicLabel.setText("���ڲ��ţ�"+musicInfo.getMusicName());
		  musicAlbumLabel.setText("ר����"+musicInfo.getMusicAlbum());
		  musicArtistLabel.setText("���֣�"+musicInfo.getMusicArtist());
		}else{
		  currentMusicLabel.setText("");
	      musicAlbumLabel.setText("");
		  musicArtistLabel.setText("");
		}
	}
	
	/**
	 * �������ֲ���״̬
	 * @param state
	 */
	public void setMusicState(String state){
		if(null!=state){
		   playStateLabel.setText("����״̬��"+state);
		}else{
		   playStateLabel.setText("");
		}
	}
	



}
