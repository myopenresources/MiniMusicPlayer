package com.cjh.ui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import com.cjh.config.MusicPlayerConfig;


/**
 * 歌词字体选择器
 * @author cjh
 *
 */
public class LrcFontChooser extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * 当前字体
	 */
	private String  currentFontName ="宋体";
	
	/**
	 * 当前字号
	 */
	private String currentFontSize ="13";
	
	
	/**
	 * 字体
	 */
	private String [] fontNames =null;
	
	/**
	 * 字体大小
	 */
	private String [] fontSizes = null;
	
	/**
	 * 字体列表
	 */
	private JList  fontNameList = null;      
	
	/**
	 * 字体大小列表
	 */
	private JList  fontSizeList = null; 
	
	/**
	 * 字体列表滚动窗格
	 */
	private JScrollPane fontNameListScrollPane = null;
	
	/**
	 * 字体大小列表滚动窗格
	 */
	private JScrollPane fontSizeListScrollPane = null;
	
	
	/**
	 * 关闭
	 */
	private JButton exitButton = null;
	
	private static LrcFontChooser lineFontChooser = new LrcFontChooser();
	
	private   LrcFontChooser(){
		init();
	}
	
	private void init(){
		
		//读取配置文件
		currentFontName = MusicPlayerConfig.getInstance().getPopString("lrcFontName");
		currentFontSize = MusicPlayerConfig.getInstance().getPopString("lrcFontSize");
		
		JLabel fontNameTitle = new JLabel("字体：");
		fontNameTitle.setBounds(10, 10, 100, 20);
		this.add(fontNameTitle);
		
		JLabel fontSizeTitle = new JLabel("字号：");
		fontSizeTitle.setBounds(200, 10, 100, 20);
		this.add(fontSizeTitle);
		
		//字体列表滚动窗格
		//取得当前环境可用字体.  
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();  
        fontNames = ge.getAvailableFontFamilyNames();  
        
        fontNameList = new JList(fontNames);  
        int index = this.indexOf(fontNames, currentFontName);
        if(index!=-1){
        	fontNameList.setSelectedIndex(index);
        }else{
        	fontNameList.setSelectedIndex(0);
        }
        
		fontNameListScrollPane = new JScrollPane(fontNameList);
		fontNameListScrollPane.setBounds(10, 30, 150, 200);
        this.add(fontNameListScrollPane);
        
        
		
		//字体大小列表滚动窗格
        fontSizes = new String[] {"10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","28","29","30"};
        fontSizeList = new JList(fontSizes);
        index = this.indexOf(fontSizes, currentFontSize);
        if(index!=-1){
        	fontSizeList.setSelectedIndex(index);
        }else{
        	fontSizeList.setSelectedIndex(0);
        }
        
		fontSizeListScrollPane = new JScrollPane(fontSizeList);
		fontSizeListScrollPane.setBounds(200, 30, 60, 200);
        this.add(fontSizeListScrollPane);
        
        
        exitButton = new JButton("关闭");
        exitButton.setBounds(100, 250, 70, 25);
        this.add(exitButton);
        
        
        
        
        this.setLayout(null); 
        this.setResizable(false);
		this.setTitle("字体选择");

		// 窗口大小
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		int width = toolKit.getScreenSize().width;
		int height = toolKit.getScreenSize().height;

		this.setBounds((width - 340) / 2, (height - 465) / 2, 277, 320);
		this.setVisible(false);
		
		this.event();
        
        
	}
	
	public static LrcFontChooser getInstance(){
		if(null==lineFontChooser){
			System.out.println("线字体选择窗口初始化出错！");
			return null;
		}
		return lineFontChooser;
	}
	
	/**
	 * 事件
	 */
	private void event(){
		//字体
		fontNameList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//双击显示
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					currentFontName = fontNames[fontNameList.getSelectedIndex()];
					MusicSetFrame.getInstance().setLrcFontStateLabel(currentFontName,currentFontSize);
					
					//修改歌词显示
					LRCLabel.getInstance().setLrcFont(new Font(currentFontName,Font.PLAIN,Integer.valueOf(currentFontSize)));
					//保存配置
					MusicPlayerConfig.getInstance().setPopString("lrcFontName", currentFontName);
				}
			}
		});
		
		//字号
		fontSizeList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//双击显示
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					currentFontSize = fontSizes[fontSizeList.getSelectedIndex()];
					MusicSetFrame.getInstance().setLrcFontStateLabel(currentFontName,currentFontSize);
					//修改歌词显示
					LRCLabel.getInstance().setLrcFont(new Font(currentFontName,Font.PLAIN,Integer.valueOf(currentFontSize)));
					//保存配置
					MusicPlayerConfig.getInstance().setPopString("lrcFontSize", currentFontSize);
				}
			}
		});
		
		//关闭
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//双击显示
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					dispose();
				}
			}
		});
	}
	
	
	/**
	 * 获得当前字体
	 * @return
	 */
	public String getCurrentFontName(){
		return currentFontName;
	}
	
	/**
	 * 获得当前字号
	 * @return
	 */
	public String getCurrentFontSize(){
		return currentFontSize;
	}
	
	/**
	 * 列表中查找字符串
	 * @param list
	 * @param str
	 * @return
	 */
	private int indexOf(String[] list , String str){
		for(int i=0;i<list.length;i++){
			if(list[i].equals(str)){
				return i;
			}
		}
	    return -1;
	}
	
	
}
