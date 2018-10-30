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
 * 音乐列表
 * @author cjh
 *
 */
public class MusicListTabPane extends JTabbedPane{

	
	private static final long serialVersionUID = 1L;

	/**
	 * 音乐列表
	 */
	private Vector<String> musicVector = null;
	
	/**
	 * 音乐列表组件
	 */
	private JList musicList = null;
	
	/**
	 * 滚动窗格
	 */
	private JScrollPane musicScrollPane = null;
	
	/**
	 * 单例
	 */
	private static MusicListTabPane  musicListTabPane = new MusicListTabPane();
	
	/**
	 * 无参构造方法
	 */
	private MusicListTabPane(){
		init();
	}
	
	/**
	 * 初始化列表
	 */
	private  void init(){
		this.setOpaque(false);
		this.setUI(new TabPaneStyle());
		this.setFont(new Font("宋体", 12, 12));
		this.setFocusable(false);
		this.setBounds(1, -1, 200, 350);
		
		//向量
		musicVector = new Vector<String>();
		
		//列表
		musicList = new JList(musicVector);
		musicList.setFont(new Font("宋体", 12, 12));
		musicList.setCursor(new Cursor(Cursor.HAND_CURSOR));
		musicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		musicList.setOpaque(false);
		
		musicScrollPane = new JScrollPane(musicList);
		musicScrollPane.setOpaque(false);
		this.add(musicScrollPane,"播放列表");
		
		
	}
	
	/**
	 * 获得音乐列表实例 
	 * @return
	 */
	public static MusicListTabPane getInstance(){
		if(null==musicListTabPane){
			System.out.println("音乐列表初始化出错！");
			return null;
		}
		return musicListTabPane;
	}
	
	/**
	 * 设置背景颜色
	 * @param color
	 */
	public void setBackgroundColor(Color color){
		if(null!=musicList){
		    musicList.setBackground(color);
		}
		
	}
	
	
}
