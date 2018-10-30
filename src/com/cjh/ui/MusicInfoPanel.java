package com.cjh.ui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.cjh.music.MusicInfo;

/**
 * 音乐信息面板
 * @author cjh
 *
 */
public class MusicInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 当前播放音乐
	 */
	private JLabel currentMusicLabel = null;
	
	/**
	 * 歌手
	 */
	private JLabel musicArtistLabel = null;
	
	/**
	 * 专辑
	 */
	private JLabel musicAlbumLabel = null;
	
	/**
	 * 年
	 */
	//private JLabel yearLabel = null;
	
	/**
	 * 播放状态
	 */
	private JLabel playStateLabel =null;
	
	/**
	 * 单例
	 */
	private static MusicInfoPanel musicInfoPanel = new MusicInfoPanel();
	
	
	/**
	 * 私有构造方法
	 */
	private MusicInfoPanel(){
		init();
	}
	
	/**
	 * 初始化方法
	 */
	private void init(){
		
		this.setOpaque(false);
		
		this.setLayout(null);
		
		// 设置大小
		this.setPreferredSize(new Dimension(545, 70));
		
		this.setBounds(0, 345, 545, 70);
		
		currentMusicLabel = new JLabel("");
		//currentMusicLabel.setForeground(new Color(10,130,220));
		currentMusicLabel.setFont(new Font("宋体",Font.PLAIN,12));
		currentMusicLabel.setBounds(20, 0, 300, 15);
		this.add(currentMusicLabel);
		
		playStateLabel = new JLabel("");
		//playStateLabel.setForeground(new Color(10,130,220));
		playStateLabel.setFont(new Font("宋体",Font.PLAIN,12));
		playStateLabel.setBounds(350,0, 300, 15);
		this.add(playStateLabel);
		
		musicAlbumLabel = new JLabel("");
		musicAlbumLabel.setBounds(20, 15, 300, 15);
		//musicAlbumLabel.setForeground(new Color(10,130,220));
		musicAlbumLabel.setFont(new Font("宋体",Font.PLAIN,12));
		this.add(musicAlbumLabel);
		
		musicArtistLabel = new JLabel("");
		musicArtistLabel.setBounds(350, 15, 300, 15);
		//musicArtistLabel.setForeground(new Color(10,130,220));
		musicArtistLabel.setFont(new Font("宋体",Font.PLAIN,12));
		this.add(musicArtistLabel);
		
		
		
		/*yearLabel = new JLabel("年：aaaaaaaa");
		yearLabel.setBounds(350, 15, 300, 15);
		this.add(yearLabel);*/
		
	}
	
	/**
	 * 获得实例
	 * @return
	 */
	public static MusicInfoPanel getInstance(){
		if(null==musicInfoPanel){
			System.out.println("音乐信息面板初始化出错！");
			return null;
		}
		return musicInfoPanel;
	}
	
	/**
	 * 设置音乐信息
	 * @param musicInfo
	 */
	public void setMusicInfoToPanel(MusicInfo musicInfo){
		if(null!=musicInfo){
		  currentMusicLabel.setText("正在播放："+musicInfo.getMusicName());
		  musicAlbumLabel.setText("专辑："+musicInfo.getMusicAlbum());
		  musicArtistLabel.setText("歌手："+musicInfo.getMusicArtist());
		}else{
		  currentMusicLabel.setText("");
	      musicAlbumLabel.setText("");
		  musicArtistLabel.setText("");
		}
	}
	
	/**
	 * 设置音乐播放状态
	 * @param state
	 */
	public void setMusicState(String state){
		if(null!=state){
		   playStateLabel.setText("播放状态："+state);
		}else{
		   playStateLabel.setText("");
		}
	}
	



}
