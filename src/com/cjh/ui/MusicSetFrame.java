package com.cjh.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.cjh.config.MusicPlayerConfig;
import com.cjh.config.StyleConfig;
import com.cjh.util.MusicConstant;

/**
 * 音乐播放器设置
 * @author cjh
 *
 */
public class MusicSetFrame extends JFrame {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 空格面板
	 */
	private JTabbedPane setTabedPane = null;
	
	/**
	 * 基础配置
	 */
	private JPanel basePanel = null;
	
	/**
	 * 歌词配置
	 */
	private JPanel lrcPanel = null;
	
	/**
	 * 样式配置
	 */
	private JPanel stylePanel = null;
	
	/**
	 * 是否添加重复音乐状态
	 */
	private JLabel isRepeatStateLabel = null;
	
	/**
	 * 添加重复音乐
	 */
	private JButton  repeatButton = null;
	
	/**
	 * 不添加重复音乐
	 */
	private JButton  noRepeatButton = null;
	
	/**
	 * 线字体状态
	 */
	private JLabel lineFontStateLabel = null;
	
	/**
	 * 线字体设置
	 */
	private  JButton lineFontButton =null;
	
	
	/**
	 * 歌词字体状态
	 */
	private JLabel lrcFontStateLabel = null;
	
	/**
	 * 歌词体设置
	 */
	private  JButton lrcFontButton =null;
	
	/**
	 * 歌词线状态
	 */
	private JLabel  lineFontColorState = null;
	
	/**
	 * 线字体颜色设置
	 */
	private  JButton lineFontColorButton =null;
	
	/**
	 * 歌词状态
	 */
	private JLabel  lrcFontColorState = null;
	
	/**
	 * 歌词字体颜色设置
	 */
	private  JButton lrcFontColorButton =null;
	
	
	/**
	 * 歌词突显状态
	 */
	private JLabel  lrcMarkFontColorState = null;
	
	/**
	 * 歌词突显字体颜色设置
	 */
	private  JButton lrcMarkFontColorButton =null;
	
	
	/**
	 * 音乐列表选项颜色状态
	 */
	private JLabel  listSelectColorState = null;
	
	/**
	 * 音乐列表选项颜色设置
	 */
	private  JButton listSelectColorButton =null;
	
	
	
	
	/**
	 * 单例
	 */
	private static MusicSetFrame musicSetFrame = new MusicSetFrame();
	


	/**
	 * 无参构造方法
	 */
	private MusicSetFrame(){
		init();
	}
	
	/**
	 * 初始化方法
	 */
	private void init(){
		setTabedPane = new JTabbedPane();
		setTabedPane.setBounds(0, 0, 340, 530);
		
		
		//基础配置
		basePanel = new JPanel();
		basePanel.setLayout(null);
		//basePanel.setOpaque(false);
		
		JLabel isRepeatLabel = new JLabel("是否添加重复音乐");
		isRepeatLabel.setFont(new Font("宋体",Font.BOLD,12));
		isRepeatLabel.setBounds(30, 20, 150, 15);
		
		//读取配置文件
		String isRepeat = MusicPlayerConfig.getInstance().getPopString("isRepeat");
		String isRepeatText = "当前状态：不添加重复音乐";
		if(null!=isRepeat && !"".equals(isRepeat)){
			if("1".equals(isRepeat)){
				isRepeatText = "当前状态：添加重复音乐";
			}
		}
		
		isRepeatStateLabel = new JLabel(isRepeatText);
		isRepeatStateLabel.setBounds(30, 45,200, 15);
		
		repeatButton = new JButton("添加重复音乐");
		repeatButton.setBounds(30, 70, 130, 25);
		
		noRepeatButton = new JButton("不添加重复音乐");
		noRepeatButton.setBounds(30, 105, 130, 25);
		
		
		basePanel.add(isRepeatLabel);
		basePanel.add(isRepeatStateLabel);
		basePanel.add(repeatButton);
		basePanel.add(noRepeatButton);
		
		
		//列表颜色配置
		//选项颜色
		JLabel listLabel = new JLabel("设置音乐列表选项颜色");
		listLabel.setFont(new Font("宋体",Font.BOLD,12));
		listLabel.setBounds(30, 145, 150, 15);
		basePanel.add(listLabel);
		
		//读取配置文件
		String musicListSelectColorText = "当前音乐列表选项颜色：10,130,220";
		String musicListSelectbgColor = MusicPlayerConfig.getInstance().getPopString("musicListSelectbgColor");
		if(null!=musicListSelectbgColor && !"".equals(musicListSelectbgColor)){
			musicListSelectColorText="当前音乐列表选项颜色："+musicListSelectbgColor;
		}
				
		
		listSelectColorState =  new JLabel(musicListSelectColorText);
		listSelectColorState.setBounds(30, 170, 350, 15);
		basePanel.add(listSelectColorState);
		
		listSelectColorButton = new JButton("选择颜色");
		listSelectColorButton.setBounds(30, 195, 130, 25);
		basePanel.add(listSelectColorButton);
		
		setTabedPane.add(basePanel,"基础设置");
		
		
		//歌词配置
		lrcPanel = new JPanel();
		lrcPanel.setLayout(null);
		//lrcPanel.setOpaque(false);
		
		//===========================================
		//线配置
		JLabel fontLabel = new JLabel("设置歌词虚线字体");
		fontLabel.setFont(new Font("宋体",Font.BOLD,12));
		fontLabel.setBounds(30, 20, 150, 15);
		lrcPanel.add(fontLabel);
		
		//读取配置文件
		String lineFontName = MusicPlayerConfig.getInstance().getPopString("lineFontName");
		String lineFontSize = MusicPlayerConfig.getInstance().getPopString("lineFontSize");
		String fontText = "当前字体：宋体，字号：13";
		if(null!=lineFontName && !"".equals(lineFontName) && null!=lineFontSize && !"".equals(lineFontSize)){
				fontText = "当前字体："+lineFontName+"，字号："+lineFontSize;
		}
		
		lineFontStateLabel = new JLabel(fontText);
		lineFontStateLabel.setBounds(30, 45,350, 15);
		lrcPanel.add(lineFontStateLabel);
		
		lineFontButton = new JButton("选择字体");
		lineFontButton.setBounds(30, 70, 130, 25);
		lrcPanel.add(lineFontButton);
		
		//===========================================
		
		//歌词配置
		fontLabel = new JLabel("设置歌词字体");
		fontLabel.setFont(new Font("宋体",Font.BOLD,12));
		fontLabel.setBounds(30, 110, 150, 15);
		lrcPanel.add(fontLabel);
		
		//读取配置文件
		String lrcFontName = MusicPlayerConfig.getInstance().getPopString("lrcFontName");
		String lrcFontSize = MusicPlayerConfig.getInstance().getPopString("lrcFontSize");
		String lrcfontText = "当前字体：宋体，字号：13";
		if(null!=lrcFontName && !"".equals(lrcFontName) && null!=lrcFontSize && !"".equals(lrcFontSize)){
			lrcfontText = "当前字体："+lrcFontName+"，字号："+lrcFontSize;
		}
		
		lrcFontStateLabel = new JLabel(lrcfontText);
		lrcFontStateLabel.setBounds(30, 135,350, 15);
		lrcPanel.add(lrcFontStateLabel);
		
		lrcFontButton = new JButton("选择字体");
		lrcFontButton.setBounds(30, 160, 130, 25);
		lrcPanel.add(lrcFontButton);
		
		
		//===========================================
		
		//线颜色
		fontLabel = new JLabel("设置歌词虚线颜色");
		fontLabel.setFont(new Font("宋体",Font.BOLD,12));
		fontLabel.setBounds(30, 200, 150, 15);
		lrcPanel.add(fontLabel);
		
		//读取配置文件
		String lineFontColorText = "当前歌词虚线颜色：10,130,220";
		String lineColor = MusicPlayerConfig.getInstance().getPopString("lineColor");
		if(null!=lineColor && !"".equals(lineColor)){
			lineFontColorText="当前歌词虚线颜色："+lineColor;
		}
				
		
		lineFontColorState =  new JLabel(lineFontColorText);
		lineFontColorState.setBounds(30, 225, 350, 15);
		lrcPanel.add(lineFontColorState);
		
		lineFontColorButton = new JButton("选择颜色");
		lineFontColorButton.setBounds(30, 250, 130, 25);
		lrcPanel.add(lineFontColorButton);
		
		//===========================================
		//歌词颜色
		fontLabel = new JLabel("设置歌词颜色");
		fontLabel.setFont(new Font("宋体",Font.BOLD,12));
		fontLabel.setBounds(30, 290, 150, 15);
		lrcPanel.add(fontLabel);
		
		//读取配置文件
		String lrcFontColorText = "当前歌词颜色：10,130,220";
		String lrcColor = MusicPlayerConfig.getInstance().getPopString("generalColor");
		if(null!=lrcColor && !"".equals(lrcColor)){
			lrcFontColorText="当前歌词颜色："+lrcColor;
		}
				
		
		lrcFontColorState =  new JLabel(lrcFontColorText);
		lrcFontColorState.setBounds(30, 315, 350, 15);
		lrcPanel.add(lrcFontColorState);
		
		lrcFontColorButton = new JButton("选择颜色");
		lrcFontColorButton.setBounds(30, 340, 130, 25);
		lrcPanel.add(lrcFontColorButton);
		
		
		//===========================================
		
		//歌词突显颜色
		fontLabel = new JLabel("设置歌词突显颜色");
		fontLabel.setFont(new Font("宋体",Font.BOLD,12));
		fontLabel.setBounds(30, 380, 150, 15);
		lrcPanel.add(fontLabel);
		
		//读取配置文件
		String lrcMarkFontColorText = "当前歌词突显颜色：10,130,220";
		String markColor = MusicPlayerConfig.getInstance().getPopString("markColor");
		if(null!=markColor && !"".equals(markColor)){
			lrcMarkFontColorText="当前歌词突显颜色："+markColor;
		}
				
		
		lrcMarkFontColorState =  new JLabel(lrcMarkFontColorText);
		lrcMarkFontColorState.setBounds(30, 405, 350, 15);
		lrcPanel.add(lrcMarkFontColorState);
		
		lrcMarkFontColorButton = new JButton("选择颜色");
		lrcMarkFontColorButton.setBounds(30, 430, 130, 25);
		lrcPanel.add(lrcMarkFontColorButton);
		
		setTabedPane.add(lrcPanel,"歌词设置");
		
		
		//========================================
		
		//样式配置
		stylePanel = new JPanel();
		stylePanel.setLayout(null);
		
		JLabel styleLabel = new JLabel("设置播放器样式");
		styleLabel.setFont(new Font("宋体",Font.BOLD,12));
		styleLabel.setBounds(30, 20, 150, 15);
		stylePanel.add(styleLabel);
		
		JButton[] jbuttons = new JButton[10];
		StyleConfig styleConfig = StyleConfig.getInstance();
		List<Properties> listPop = styleConfig.getListPop();
		
		for(int i=0; i<listPop.size(); i++){
			final Properties pop = listPop.get(i);
			String title = pop.getProperty("title");
			
			if(null==title || "".equals(title)){
				title = "样式"+i;
			}
			
			jbuttons[i] = new JButton(title);
			jbuttons[i].setBounds(30, (43*(i+1)), 130, 25);
			jbuttons[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					//单击显示
					if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
						setStyle(pop);
					}
				}
			});
			
			stylePanel.add(jbuttons[i]);
			
		}
		
		setTabedPane.add(stylePanel,"样式配置");
		
		
		this.getContentPane().add(setTabedPane);
		this.setLayout(null);
		this.setResizable(false);
		this.setTitle("播放器设置");

		// 窗口大小
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		int width = toolKit.getScreenSize().width;
		int height = toolKit.getScreenSize().height;

		this.setBounds((width - 340) / 2, (height - 465) / 2, 340, 530);
		this.setVisible(false);
		
		
		event();
	}
	
	
	
	
	
	/**
	 * 事件
	 */
	private void event(){
		
		//添加重复音乐
		repeatButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击显示
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					isRepeatStateLabel.setText("当前状态：添加重复音乐");
					EastPanel.setIsRepeat(MusicConstant.REPEAT);
					//保存配置
					MusicPlayerConfig.getInstance().setPopString("isRepeat", String.valueOf(MusicConstant.REPEAT));
				}
			}
		});
		
		//添加重复音乐
		noRepeatButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击显示
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					isRepeatStateLabel.setText("当前状态：不添加重复音乐");
					EastPanel.setIsRepeat(MusicConstant.NO_REPEAT);
					//保存配置
					MusicPlayerConfig.getInstance().setPopString("isRepeat", String.valueOf(MusicConstant.NO_REPEAT));
				}
			}
		});
		
		
		//选择线字体
		lineFontButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击显示
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					LineFontChooser.getInstance().setVisible(true);
				}
			}
		});
		
		//歌词字体
		lrcFontButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击显示
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					LrcFontChooser.getInstance().setVisible(true);
				}
			}
		});
		
		//线颜色
		lineFontColorButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击显示
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					Color color = chooseColor(lineFontColorButton);
					if(null!=color){
					 String strColor = color.getRed()+","+color.getGreen()+","+color.getBlue();
					 lineFontColorState.setText("当前歌词虚线颜色："+strColor);
					 //设置线颜色
					 LRCLabel.getInstance().setLineColor(color);
					 //保存配置
					 MusicPlayerConfig.getInstance().setPopString("lineColor", strColor);
					}
				}
			}
		});
		
		//歌词颜色
		lrcFontColorButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击显示
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					Color color = chooseColor(lrcFontColorButton);
					if(null!=color){
					 String strColor = color.getRed()+","+color.getGreen()+","+color.getBlue();
					 lrcFontColorState.setText("当前歌词颜色："+strColor);
					 //设置歌词颜色
					 LRCLabel.getInstance().setGeneralColor(color);
					 //保存配置
					 MusicPlayerConfig.getInstance().setPopString("generalColor", strColor);
					}
				}
			}
		});
		
		//歌词突显颜色
		lrcMarkFontColorButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击显示
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					Color color = chooseColor(lrcMarkFontColorButton);
					if(null!=color){
					 String strColor = color.getRed()+","+color.getGreen()+","+color.getBlue();
					 lrcMarkFontColorState.setText("当前歌词突显颜色："+strColor);
					 //设置歌词颜色
					 LRCLabel.getInstance().setMarkColor(color);
					 //保存配置
					 MusicPlayerConfig.getInstance().setPopString("markColor", strColor);
					}
				}
			}
		});
		
		
		//音乐列表选项颜色
		listSelectColorButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击显示
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					Color color = chooseColor(listSelectColorButton);
					if(null!=color){
					 String strColor = color.getRed()+","+color.getGreen()+","+color.getBlue();
					 listSelectColorState.setText("当前音乐列表选项颜色："+strColor);
					 //设置选项颜色
					 MusicInfoCellRenderer.setMusicListSelectbgColor(color);
					 EastPanel.getInstance().getMusicList().updateUI();
					 //保存配置
					 MusicPlayerConfig.getInstance().setPopString("musicListSelectbgColor", strColor);
					}
				}
			}
		});
	}
	
	/**
	 * 设置样式
	 * @param styleConfig
	 */
	private void setStyle(Properties pop){
		
		//皮肤
		String skin = pop.getProperty("skin");
		if(null!=skin && !"".equals(skin)){
			ImageIcon imgIcon = new ImageIcon(skin);
			MusicFrame.setMusicFrameBackgroundImg(imgIcon);
		}
		
		//列表选项颜色
		String musicListSelectbgColor = pop.getProperty("musicListSelectbgColor");
		if(null!=musicListSelectbgColor && !"".equals(musicListSelectbgColor)){
			 String[] str = musicListSelectbgColor.split(",");
			 Color color = new Color(Integer.valueOf(str[0]),Integer.valueOf(str[1]),Integer.valueOf(str[2]));
			 listSelectColorState.setText("当前音乐列表选项颜色："+musicListSelectbgColor);
			 //设置选项颜色
			 MusicInfoCellRenderer.setMusicListSelectbgColor(color);
			 EastPanel.getInstance().getMusicList().updateUI();
			 //保存配置
			 MusicPlayerConfig.getInstance().setPopString("musicListSelectbgColor", musicListSelectbgColor);
		}
		
		//歌词颜色
		String generalColor = pop.getProperty("generalColor");
		if(null!=generalColor && !"".equals(generalColor)){
			String[] str = generalColor.split(",");
			Color color = new Color(Integer.valueOf(str[0]),Integer.valueOf(str[1]),Integer.valueOf(str[2]));
			lrcFontColorState.setText("当前歌词颜色："+generalColor);
			//设置歌词颜色
			LRCLabel.getInstance().setGeneralColor(color);
			//保存配置
			MusicPlayerConfig.getInstance().setPopString("generalColor", generalColor);
		}
		
		//歌词突显颜色
		String markColor = pop.getProperty("markColor");
		if(null!=markColor && !"".equals(markColor)){
			String[] str = markColor.split(",");
		    Color color = new Color(Integer.valueOf(str[0]),Integer.valueOf(str[1]),Integer.valueOf(str[2]));
		    lrcMarkFontColorState.setText("当前歌词突显颜色："+markColor);
		    //设置歌词颜色
			LRCLabel.getInstance().setMarkColor(color);
			//保存配置
			MusicPlayerConfig.getInstance().setPopString("markColor", markColor);
		}
		
		//歌词线颜色
		String lineColor = pop.getProperty("lineColor");
		if(null!=lineColor && !"".equals(lineColor)){
			String[] str = lineColor.split(",");
			Color color = new Color(Integer.valueOf(str[0]),Integer.valueOf(str[1]),Integer.valueOf(str[2]));
			 lineFontColorState.setText("当前歌词虚线颜色："+lineColor);
			//设置线颜色
			LRCLabel.getInstance().setLineColor(color);
			//保存配置
			MusicPlayerConfig.getInstance().setPopString("lineColor", lineColor);
		}
		
	}
	
	
	/**
	 * 设置线字体状态
	 * @param str
	 */
	public void setLineFontStateLabel(String str,String str2){
		this.lineFontStateLabel.setText("当前字体："+str+"，字号："+str2);
	}
	
	/**
	 * 设置歌词字体状态
	 * @param str
	 */
	public void setLrcFontStateLabel(String str,String str2){
		this.lrcFontStateLabel.setText("当前字体："+str+"，字号："+str2);
	}
	
	
	/**
	 * 颜色选择器
	 * @param comp
	 * @return
	 */
	private Color chooseColor(Component comp){
		   Color rsltColor =JColorChooser.showDialog(this,
		     "颜色选择",
		     comp.getBackground());
		   return rsltColor;
	}
	
	
	/**
	 * 获得实例 
	 * @return
	 */
	public static MusicSetFrame getInstance(){
		if(null==musicSetFrame){
			System.out.println("音乐播放器设置窗口初始化出错！");
			return null;
		}
		return musicSetFrame;
	}
	

}
