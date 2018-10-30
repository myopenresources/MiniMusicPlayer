package com.cjh.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.cjh.config.MusicPlayerConfig;
import com.cjh.music.MusicInfo;
import com.cjh.music.MusicPlayer;
import com.cjh.util.MusicConstant;
import com.cjh.util.SnapShot;

/**
 * 东面板
 * 
 * @author cjh
 * 
 */
public class EastPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	
	/**
	 * 标题
	 */
	private JLabel musicListTitle = null;
	
	/**
	 * 音乐列表
	 */
	private Vector<MusicInfo> musicVector = null;
	
	/**
	 * 音乐列表组件
	 */
	private JList musicList = null;
	
	/**
	 * 滚动窗格
	 */
	private JScrollPane musicScrollPane = null;
	
	/**
	 * 音乐列表弹出框
	 */
	private MuiscListPopupMenu muiscListPopupMenu= MuiscListPopupMenu.getInstance();
	
	/**
	 * 添加音乐
	 */
	private MusicButton addMusicButton = null;
	
	/**
	 * 添加目录音乐
	 */
	private MusicButton addFolderButton = null;
	
	/**
	 * 播放模式
	 */
	private MusicButton playModelButton = null;
	
	/**
	 * 添加音乐
	 */
	private MusicButton clearMusicButton = null;
	
	/**
	 * 搜索音乐
	 */
	private MusicButton queryMusicButton = null;
	
	/**
	 * 截图
	 */
	private MusicButton snapShotButton = null;
	
	
	/**
	 * 是否添加重复音乐
	 */
	private static int isRepeat = MusicConstant.NO_REPEAT;
	
	/**
	 * 播放模式
	 */
	private static int playModel = MusicConstant.MODE1;
	
	/**
	 * 窗体
	 */
	private static MusicFrame  musicFrame=null;
	
	
	
	

	/**
	 * 单例
	 */
	private static EastPanel eastPanel = new EastPanel();

	/**
	 * 无参构造方法
	 */
	private EastPanel() {
		init();
	}

	/**
	 * 获得面板实例
	 * 
	 * @return
	 */
	public static EastPanel getInstance() {
		if (null == eastPanel) {
			System.out.println("东面板初始化出错！");
		}

		return eastPanel;
	}

	/**
	 * 初始化面板
	 */
	private void init() {
		this.setLayout(null);
		this.setOpaque(false);
		
		//从配置文件中获得播放模式
		String model = MusicPlayerConfig.getInstance().getPopString("playModel");
		if(null!=model && !"".equals(model)){
			playModel =Integer.valueOf(model);
		}
		
		//从配置文件中获得是否添加重复音乐
		String str = MusicPlayerConfig.getInstance().getPopString("isRepeat");
		if(null!=str && !"".equals(str)){
			isRepeat =Integer.valueOf(str);
		}
		
		//边框
		//this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(245,245,245)));

		// 设置大小
		this.setPreferredSize(new Dimension(201, 400));
		
		//初始化音乐列表
		this.initList();
		
		//初始化按钮
		this.initButton();
		
		//事件
		this.event();

	}
	
	/**
	 * 初始化音乐列表
	 */
	private void initList(){
		musicListTitle = new JLabel("音乐列表");
		musicListTitle.setBounds(0, 3, 100, 20);
		musicListTitle.setFont(new Font("宋体",Font.BOLD,13));
		musicListTitle.setForeground(new Color(10,130,220));
		this.add(musicListTitle);
		
		//向量
		musicVector = new Vector<MusicInfo>();
		
		//列表
		musicList = new JList(musicVector);
		musicList.setCursor(new Cursor(Cursor.HAND_CURSOR));
		musicList.setCellRenderer(new MusicInfoCellRenderer(musicVector));
		musicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		musicList.setOpaque(false);
		musicList.setVisible(false);
		
		//滚动窗格
		musicScrollPane = new JScrollPane(musicList);
		musicScrollPane.setUI(new MusicListScrollPanelUI());
		musicScrollPane.setOpaque(false);
		musicScrollPane.getViewport().setOpaque(false);
		musicScrollPane.setBounds(0,25,200,325);
		musicScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(245,245,245)));
		this.add(musicScrollPane);
		
	}
	
	
	/**
	 * 初始化按钮
	 */
	private void initButton(){
		
		 //添加音乐
		 addMusicButton =  new MusicButton(new ImageIcon("images/buttonIcon/addMusic.png"));
		 addMusicButton.setIconPath("images/buttonIcon/addMusic");
		 addMusicButton.setToolTipText("添加音乐");
		 addMusicButton.setBounds(15,355, 20, 20);
		 this.add(addMusicButton);
		
		 
		 //添加目录音乐
		 addFolderButton =  new MusicButton(new ImageIcon("images/buttonIcon/addFolder.png"));
		 addFolderButton.setIconPath("images/buttonIcon/addFolder");
		 addFolderButton.setToolTipText("添加目录音乐");
		 addFolderButton.setBounds(45,355, 20, 20);
		 this.add(addFolderButton);
	
		 
		 //播放模式
		 String modelIconPath = "";
		 String tipText = "";
		 //顺序播放
         if (MusicConstant.MODE1==EastPanel.getplayModel()) {
        	 modelIconPath="images/buttonIcon/orderModel";
        	 tipText="顺序播放";
         
         //列表循环
         }else if(MusicConstant.MODE2 == EastPanel.getplayModel()){
        	 modelIconPath="images/buttonIcon/loopModel";
        	 tipText="列表循环";
         
         //单曲循环
         } else if (MusicConstant.MODE3 == EastPanel.getplayModel()) {
        	 modelIconPath="images/buttonIcon/singleModel";
        	 tipText="单曲循环";
         //随机播放
         } else if (MusicConstant.MODE4 == EastPanel.getplayModel()) {
        	 modelIconPath="images/buttonIcon/randomModel";
        	 tipText="随机播放";
         //倒序播放
         } else if(MusicConstant.MODE5 == EastPanel.getplayModel()){
        	 modelIconPath="images/buttonIcon/invertedOrderModel";
        	 tipText="倒序播放";
         }
		 
		 playModelButton =  new MusicButton(new ImageIcon(modelIconPath+".png"));
		 playModelButton.setIconPath(modelIconPath);
		 playModelButton.setToolTipText(tipText);
		 playModelButton.setBounds(75,355, 20, 20);
		 this.add(playModelButton);
		 
		 
		 //清空列表
		 clearMusicButton =  new MusicButton(new ImageIcon("images/buttonIcon/clearMusic.png"));
		 clearMusicButton.setIconPath("images/buttonIcon/clearMusic");
		 clearMusicButton.setToolTipText("清空列表");
		 clearMusicButton.setBounds(105,355, 20, 20);
		 this.add(clearMusicButton);
		
		 //搜索音乐
		 queryMusicButton =  new MusicButton(new ImageIcon("images/buttonIcon/queryMusic.png"));
		 queryMusicButton.setIconPath("images/buttonIcon/queryMusic");
		 queryMusicButton.setToolTipText("搜索音乐");
		 queryMusicButton.setBounds(135,355, 20, 20);
		 this.add(queryMusicButton);
		 
		 //截图
		 snapShotButton=new MusicButton(new ImageIcon("images/buttonIcon/snapShot.png"));
		 snapShotButton.setIconPath("images/buttonIcon/snapShot");
		 snapShotButton.setToolTipText("截图");
		 snapShotButton.setBounds(165,355, 20, 20);
		 this.add(snapShotButton);
		
	}
	
	/**
	 * 事件
	 */
	private void event(){
		//音乐列表组件
		musicList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//双击显示
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==2){
					if(null!=musicVector && musicVector.size()>0){
					 int index = musicList.locationToIndex(e.getPoint());
					 if(-1!=index){
					   MusicInfo musicInfo = musicVector.get(index);
					   MusicPlayer.initPlayer(musicInfo);
					   MusicPlayer.playMusic();
					 }
					}
				}else if(e.getButton() == MouseEvent.BUTTON3){
					muiscListPopupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
		//添加音乐
		addMusicButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					new  AddMusicFileChooser();
				}
			}
		});
		
		//添加目录音乐
		addFolderButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					new  AddMuiscPathChooser();
				}
			}
		});
		
		//播放模式
		playModelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					
					switch(EastPanel.getplayModel()){
					 case MusicConstant.MODE1:
						 EastPanel.setplayModel(MusicConstant.MODE2);
						 playModelButton.setIcon(new ImageIcon("images/buttonIcon/loopModel.png"));
						 playModelButton.setIconPath("images/buttonIcon/loopModel");
						 playModelButton.setToolTipText("列表循环");
					     break;
					     
					 case MusicConstant.MODE2:
						 EastPanel.setplayModel(MusicConstant.MODE3);
						 playModelButton.setIcon(new ImageIcon("images/buttonIcon/singleModel.png"));
						 playModelButton.setIconPath("images/buttonIcon/singleModel");
						 playModelButton.setToolTipText("单曲循环");
					     break;
					     
					 case MusicConstant.MODE3:
						 EastPanel.setplayModel(MusicConstant.MODE4);
						 playModelButton.setIcon(new ImageIcon("images/buttonIcon/randomModel.png"));
						 playModelButton.setIconPath("images/buttonIcon/randomModel");
						 playModelButton.setToolTipText("随机播放");
					     break;
					     
					 case MusicConstant.MODE4:
						 EastPanel.setplayModel(MusicConstant.MODE5);
						 playModelButton.setIcon(new ImageIcon("images/buttonIcon/invertedOrderModel.png"));
						 playModelButton.setIconPath("images/buttonIcon/invertedOrderModel");
						 playModelButton.setToolTipText("倒序播放");
					     break;
					     
					 case MusicConstant.MODE5:
						 EastPanel.setplayModel(MusicConstant.MODE1);
						 playModelButton.setIcon(new ImageIcon("images/buttonIcon/orderModel.png"));
						 playModelButton.setIconPath("images/buttonIcon/orderModel");
						 playModelButton.setToolTipText("顺序播放");
					     break;
					}
					
					//保存配置
					MusicPlayerConfig.getInstance().setPopString("playModel", String.valueOf(EastPanel.getplayModel()));
					
				}
			}
		});
		
		
		//清空列表
		clearMusicButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					MusicPlayer.clearMusic();
				}
			}
		});
		
		
		//搜索音乐
		queryMusicButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					QueryMusicFrame.getInstance().setVisible(true);
				}
			}
		});
		
		
		//截图
		snapShotButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					Rectangle dimension =musicFrame.getBounds();
					String path = "snapShot";
					File pathFile = new File(path);
					if(!pathFile.exists()){
						pathFile.mkdir();
					}
					
					String fileName  = String.valueOf(new Date().getTime());
					String imageFormat = "png";
					boolean result = SnapShot.createSnapShot(dimension,path+"/",fileName,imageFormat);
					
					if(result){
						JOptionPane.showMessageDialog(null, "截图“"+fileName+"."+imageFormat+"”，已保存到"+path+"目录中！");
					}else{
						JOptionPane.showMessageDialog(null, "截图失败，请重新截图！");
					}
				}
			}
		});
		
		
		
	}
	
	/**
	 * 设置是否添加重复音乐
	 * @param isRepeat
	 */
	public static void setIsRepeat(int isRepeat){
		EastPanel.isRepeat=isRepeat;
	}
	
	/**
	 * 获得播放模式
	 * @return
	 */
	public static int getplayModel(){
		return EastPanel.playModel;
	}
	
	/**
	 * 设置播放模式
	 * @return
	 */
	public static void  setplayModel(int state){
		EastPanel.playModel=state;
	}
	
	/**
	 * 更新音乐列表
	 */
	public void updateMusicListUI(){
		//更新音乐列表
		musicList.updateUI();
	}
	
	/**
	 * 获得音乐列表
	 * @return
	 */
	public Vector<MusicInfo> getMusicVector(){
		return  musicVector;
	}
	
	/**
	 * 获得音乐列表组件
	 * @return
	 */
	public JList getMusicList(){
		return musicList;
	}
	
	/**
	 * 添加音乐到列表
	 */
	public  void addMusic(MusicInfo musicInfo){
		musicList.setVisible(true);
		
		//不重复添加音乐
		if(isRepeat == MusicConstant.NO_REPEAT){
			
			for(MusicInfo m:  musicVector){
				if(musicInfo.getMusicName().equals(m.getMusicName())){
					return ;
				}
			}
		
			musicVector.add(musicInfo);
		}else{
			musicVector.add(musicInfo);
		}
		
	}
	
	/**
	 * 设置窗体
	 * @param musicFrame
	 */
	public static void setMusicFrame(MusicFrame musicFrame){
	    EastPanel.musicFrame = musicFrame;
	}
	
	
	
	
	

}
