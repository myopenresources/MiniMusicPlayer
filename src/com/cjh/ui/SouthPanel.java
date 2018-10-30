package com.cjh.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.media.Player;
import javax.media.Time;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.cjh.config.MusicPlayerConfig;
import com.cjh.music.MusicPlayer;
import com.cjh.style.ProgressSliderStyle;
import com.cjh.style.SoundSliderStyle;
import com.cjh.thread.SliderUpdateThread;
import com.cjh.util.FormatUtils;
import com.cjh.util.MusicConstant;
import com.cjh.util.PlayerState;

/**
 * 南面板
 * 
 * @author cjh
 * 
 */
public class SouthPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * 背景图片
	 */
	private Image backgroudImg = null;

	/**
	 * 播放按钮
	 */
	private MusicButton playButton = null;
	
	
	/**
	 * 上一曲
	 */
	private MusicButton prevButton = null;
	
	/**
	 * 下一曲
	 */
	private MusicButton nextButton = null;
	
	/**
	 * 静音
	 */
	private MusicButton soundButton = null;
	
	
	/**
	 * 声音划块
	 */
	private JSlider soundSlider = null;
	
	
	/**
	 * 进度划块
	 */
	private JSlider progressSlider = null;
	
	/**
	 * 播放时间
	 */
	private JLabel timeLabel = null;
	

	/**
	 * 单例
	 */
	private static SouthPanel southPanel = new SouthPanel();

	/**
	 * 私有构造方法
	 */
	private SouthPanel() {
		init();
	}
	
	/**
	 * 获得面板实例
	 * 
	 * @return
	 */
	public static SouthPanel getInstance() {
		if (null == southPanel) {
			System.out.println("南面板初始化出错！");
			return null;
		}

		return southPanel;
	}

	/**
	 * 初始化面板
	 */
	private void init() {
		//this.setBackground(Color.red);
		// 初始化背景与标题颜色
		//this.setPanelBackgroundImg("images/theme/001/NorthBG.jpg");
		
		//边框
		//this.setBorder(BorderFactory.createLineBorder(new Color(245,245,245), 1));
		this.setOpaque(false);
		this.setLayout(null);

		// 设置大小
		this.setPreferredSize(new Dimension(this.getWidth(), 60));

		// 初始化音乐按钮
		this.initButton();
		
		//初始化划块
		this.initSlider();
		
		//事件
		this.event();

	}

	/**
	 * 初始化按钮
	 */
	private void initButton() {

		// 播放按钮
		playButton = new MusicButton(new ImageIcon("images/buttonIcon/play.png"));
		playButton.setIconPath("images/buttonIcon/play");
		playButton.setToolTipText("播放");
		playButton.setBounds(65, 5, 50, 50);
		this.add(playButton);
		
		
		//上一曲
		prevButton = new MusicButton(new ImageIcon("images/buttonIcon/prev.png"));
		prevButton.setIconPath("images/buttonIcon/prev");
		prevButton.setToolTipText("上一曲");
		prevButton.setBounds(20, 10, 40, 40);
		this.add(prevButton);
		
		//下一曲
		nextButton = new MusicButton(new ImageIcon("images/buttonIcon/next.png"));
		nextButton.setIconPath("images/buttonIcon/next");
		nextButton.setToolTipText("下一曲");
		nextButton.setBounds(120, 10, 40, 40);
		this.add(nextButton);
		
		//静音
		String imgPath ="images/buttonIcon/sound"; 
		String text = "未静音";
		String str = MusicPlayerConfig.getInstance().getPopString("volume");
		if(null!=str && !"".equals(str)){
			if("0".equals(str)){
				imgPath="images/buttonIcon/nosound";
				text="静音";
			}
		}
		soundButton = new MusicButton(new ImageIcon(imgPath+".png"));
		soundButton.setIconPath(imgPath);
		soundButton.setToolTipText(text);
		soundButton.setBounds(590, 15, 32, 32);
		this.add(soundButton);

	}
	
	/**
	 * 初始化划块
	 */
	private void initSlider(){
		//初始化声音进度条
		soundSlider = new JSlider(0,100,50);
		soundSlider.setToolTipText("音量50%");
		soundSlider.setOpaque(false);
		soundSlider.setCursor(new Cursor(Cursor.HAND_CURSOR));
		soundSlider.setBounds(630, 23, 100, 16);
		soundSlider.setUI(new SoundSliderStyle(soundSlider));
		
		//从配置文件中获得音量
		String volume = MusicPlayerConfig.getInstance().getPopString("volume");
		if(null!=volume && !"".equals(volume)){
		  soundSlider.setValue(Integer.valueOf(volume));
		  soundSlider.setToolTipText("音量"+volume+"%");
		}
		
		this.add(soundSlider);
		
		
		//初始化音乐进度条
		progressSlider = new JSlider(0,1000,0);
		progressSlider.setOpaque(false);
		progressSlider.setCursor(new Cursor(Cursor.HAND_CURSOR));
		progressSlider.setBounds(185, 25, 300, 16);
		progressSlider.setUI(new ProgressSliderStyle(progressSlider));
		this.add(progressSlider);
		
		
		//播放时间
		timeLabel = new JLabel("00:00/00:00");
		timeLabel.setBounds(495, 23, 100, 16);
		timeLabel.setFont(new Font("宋体",Font.BOLD,13));
		timeLabel.setForeground(new Color(10,130,220));
		this.add(timeLabel);
		
	}

	/**
	 * 事件
	 */
    private void event(){
    	
    	//播放
    	playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				if(MusicPlayer.getPlayerState() == PlayerState.PLAY){
					MusicPlayer.pauseMusic();
					playButton.setToolTipText("暂停");
					
				}else if(MusicPlayer.getPlayerState() == PlayerState.PAUSE){
					MusicPlayer.playMusic();
					playButton.setToolTipText("播放");
				}else if(MusicPlayer.getPlayerState() == PlayerState.UNREALIZED){
					MusicPlayer.initPlayer(null);
					MusicPlayer.playMusic();
					
				}
			}
		});


    	
    	//上一曲
    	prevButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MusicPlayer.prevMusic();
			}
		});
    	
    	//下一曲
    	nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MusicPlayer.nextMusic();
				
			}
		});
    	
    	//静音
    	soundButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if( !MusicPlayer.getSoundState()){
					MusicPlayer.setSoundState(MusicConstant.SOUND);
				}else if(MusicPlayer.getSoundState()){
					MusicPlayer.setSoundState(MusicConstant.NO_SOUND);
				}
				
			}
		});
    	
    	//声音划块
    	soundSlider.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
    			 Player  player = MusicPlayer.getPlayer();
    		        if (player != null) {
    		            player.getGainControl().setLevel(soundSlider.getValue() / 100.0F);
    		            
    		            if(soundSlider.getValue()==0){
    		            	MusicPlayer.setSoundState(MusicConstant.SOUND);
    		            }else{
    		            	MusicPlayer.setSoundState(MusicConstant.NO_SOUND);
    		            }
    		        }
    		        
    		         //保存配置文件
    		        MusicPlayerConfig.getInstance().setPopString("volume", String.valueOf(soundSlider.getValue()));
    		        soundSlider.setToolTipText("音量: " + soundSlider.getValue() + "%");
    		    }
    	});
    	
    	//音乐进度划块
    	progressSlider.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				 Player  player = MusicPlayer.getPlayer();
			        if (player != null) {
			            double totalTime = player.getDuration().getSeconds();
			            double rate = progressSlider.getValue() * 1.0 / progressSlider.getMaximum();
			            //歌词
			            LRCLabel.getInstance().updateLRC(FormatUtils.roundDouble(totalTime * rate));
			            progressSlider.setToolTipText(FormatUtils.formatTime(totalTime * rate));
			        }
			}
			
    	});
    	
    	//音乐进度划块
    	progressSlider.addMouseListener(new MouseAdapter(){
    		
    		    @Override
    		    public void mousePressed(MouseEvent e) {
    			  Player  player = MusicPlayer.getPlayer();
    		        if (player != null) {
    		            MusicPlayer.pauseMusic();
    		            SliderUpdateThread.pause();
    		        }
    		    }

    		    @Override
    		    public void mouseReleased(MouseEvent e) {
    		      Player  player = MusicPlayer.getPlayer();
    		        if (player != null) {
    		            double totalTime = player.getDuration().getSeconds();
    		            double rate = progressSlider.getValue() * 1.0 / progressSlider.getMaximum();
    		            player.setMediaTime(new Time(totalTime * rate));
    		            MusicPlayer.playMusic();
    		            SliderUpdateThread.resume();
    		        } else {
    		        	progressSlider.setValue(0);
    		        	progressSlider.setToolTipText("00:00");
    		        }
    		    }
    		
    	} );
    	
    	
    }
	
	
	/**
	 * 设置背景图片
	 * 
	 * @param imgPath
	 */
	public void setPanelBackgroundImg(String imgPath) {
		this.backgroudImg = new ImageIcon(imgPath).getImage();
		repaint();
	}

	/**
	 * 获得声音划块
	 * @return
	 */
	public JSlider getSoundSlider() {
		return soundSlider;
	}

	/**
	 * 获得进度条划块
	 * @return
	 */
	public JSlider getProgressSlider() {
		return progressSlider;
	}
	
	
    /**
     * 获得声音按钮
     * @return
     */
	public MusicButton getSoundButton() {
		return soundButton;
	}
	
	

	 /**
     * 获得播放按钮
     * @return
     */
    public MusicButton getPlayButton() {
		return playButton;
	}

	/**
     * 获得播放时间
     * @return
     */
	public JLabel getTimeLabel() {
		return timeLabel;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//sGraphics2D g2d = (Graphics2D) g;
		//sg2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);// 反锯齿

		// 背景图片
		if (null != backgroudImg) {
			g.drawImage(backgroudImg, 0, 0, this.getWidth(), this.getHeight(),this);
		}

	}

}
