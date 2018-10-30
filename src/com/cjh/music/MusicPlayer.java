package com.cjh.music;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.media.Manager;
import javax.media.Player;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

import com.cjh.listener.LRCLabelListener;
import com.cjh.listener.MusicControllerListener;
import com.cjh.thread.SliderUpdateThread;
import com.cjh.ui.EastPanel;
import com.cjh.ui.LRCLabel;
import com.cjh.ui.MusicButton;
import com.cjh.ui.MusicInfoPanel;
import com.cjh.ui.NorthPanel;
import com.cjh.ui.SouthPanel;
import com.cjh.util.FormatUtils;
import com.cjh.util.LRCParse;
import com.cjh.util.MusicConstant;
import com.cjh.util.PlayerState;

/**
 * 音乐播放类
 * 
 * @author cjh
 * 
 */
public class MusicPlayer {

	/**
	 * 播放器
	 */
	private  static Player player = null;
	
	/**
	 * 北面板
	 */
	private static NorthPanel northPanel = NorthPanel.getInstance();
	
	/**
	 * 东面板
	 */
	private static  EastPanel eastPanel = EastPanel.getInstance();
	
	/**
	 * 南面板
	 */
	private static  SouthPanel southPanel = SouthPanel.getInstance();
	
	
	/**
	 * 音乐列表
	 */
	private static  Vector<MusicInfo> musicVector = eastPanel.getMusicVector();
	
	/**
	 * 音乐列表组件
	 */
	private static JList musicList = eastPanel.getMusicList();
	
	/**
	 * 声音划块
	 */
	private static JSlider soundSlider = southPanel.getSoundSlider();
	
	/**
	 * 音乐进度划块
	 */
	private static JSlider progressSlider = southPanel.getProgressSlider();
	
	/**
	 * 播放时间
	 */
	private static JLabel titleLabel = southPanel.getTimeLabel();
	
	
	/**
	 * 声音按钮
	 */
	private static MusicButton soundButton = southPanel.getSoundButton();
	
	/**
	 * 播放按钮
	 */
	private static MusicButton playButton = southPanel.getPlayButton();
	
	/**
	 * 当前播放音乐
	 */
	private static MusicInfo currentMusic= null;
	
	/**
	 * 声音状态
	 */
	private static boolean soundState=MusicConstant.NO_SOUND;
	
	/**
	 * 播放状态
	 */
	private static PlayerState playerState = PlayerState.UNREALIZED;
	
	/**
	 * 音乐信息面板
	 */
	private static MusicInfoPanel musicInfoPanel= MusicInfoPanel.getInstance();

	
	
	  /**
     * 初始化Player
     * @param song
     */
    public static void initPlayer(MusicInfo musicInfo) {
        try {
        	//保持只有一首歌在播放
            if (player != null ) {
            	player.stop();
                player.close();
            }
            if (musicInfo == null) {
                if (currentMusic == null) {
                    int index = musicList.getSelectedIndex();
                    if (index == -1) {
                        return;
                    }
                    musicInfo = musicVector.elementAt(index);
                } else {
                	musicInfo = currentMusic;
                }
            }
            
            currentMusic = musicInfo;
            if(currentMusic.isCanPlay()){
            	 player = Manager.createRealizedPlayer(getURL(musicInfo.getMusicPath()));
            }else{
            	//如果是倒序模式，上一曲，否则下一曲
                if(EastPanel.getplayModel()==MusicConstant.MODE5){
                	prevMusic();
                }else{
                	//下一曲
                    nextMusic();
                }
            }
            
          
        } catch (Exception ex) {
            System.out.println("无法创建Player: " + musicInfo.getMusicName() + "不能播放");
            int index = musicVector.indexOf(currentMusic);
            //设置标记为不能播放
            musicVector.elementAt(index).setCanPlay(false);
            
            //如果是倒序模式，上一曲，否则下一曲
            if(EastPanel.getplayModel()==MusicConstant.MODE5){
            	prevMusic();
            }else{
            	//下一曲
                nextMusic();
            }
            
        }
    }
	
	/**
	 * 播放音乐
	 */
	public static void playMusic() {
		 if (player != null && !noCanPlaySong()) {
	            player.getGainControl().setLevel(soundSlider.getValue() / 100.0F);
	            //setSoundState(getSoundState());
	            player.start();
	            //进度条更新
	            SliderUpdateThread.initThread();
	            SliderUpdateThread.getThread().start();
	            SliderUpdateThread.resume();
	            
	            MusicPlayer.playerState = PlayerState.PLAY;
	            
	            //实现监听器
	            player.addControllerListener(MusicControllerListener.getInstance());
	            
	            //音乐列表组件选中
	            int index = musicVector.indexOf(currentMusic);
	            musicList.setSelectedIndex(index);
	            musicList.updateUI();
	            
	            
	            //按钮图片
	            playButton.setIcon(new ImageIcon("images/buttonIcon/pause.png"));
	            playButton.setIconPath("images/buttonIcon/pause");
	            
	            //音乐信息
	            musicInfoPanel.setMusicInfoToPanel(currentMusic);
	            musicInfoPanel.setMusicState("正在播放");
	            
	            //最小化音乐
	            northPanel.setMiniMusicLabel("正在播放："+currentMusic.getMusicName());
	            
	            //如果是静音状态，保持静音状态
	            if(MusicPlayer.getSoundState()==MusicConstant.SOUND){
	            	MusicPlayer.setSoundState(MusicConstant.SOUND);
	            }else{
	            	MusicPlayer.setSoundState(MusicConstant.NO_SOUND);
	            }
	                  
	            
	            LRCParse.getInstance().LoadLRC();
	            LRCLabel.getInstance().refresh();
	            LRCLabelListener.getInstance();
	            LRCLabel.setStr("没找到歌词...");
	            
	            
	        }

	}
	

	/**
	 * 暂停音乐
	 */
	public static void pauseMusic() {
		if (null != player) {
			player.stop();
            playerState = PlayerState.PAUSE;
            
            //按钮图片
            playButton.setIcon(new ImageIcon("images/buttonIcon/play.png"));
            playButton.setIconPath("images/buttonIcon/play");
            
            //音乐信息
            musicInfoPanel.setMusicState("暂停播放");
            
            //最小化音乐
            northPanel.setMiniMusicLabel("暂停播放："+currentMusic.getMusicName());
		}

	}

	/**
	 * 停止音乐
	 */
	public static void stopMusic() {
		if (null != player) {
			 playerState = PlayerState.UNREALIZED;
			 //按钮图片
	         playButton.setIcon(new ImageIcon("images/buttonIcon/play.png"));
	         playButton.setIconPath("images/buttonIcon/play");
	         
	         //停止线程
	         SliderUpdateThread.pause();
	         
	         progressSlider.setValue(0);
	         titleLabel.setText("00:00/" + FormatUtils.formatTime(player.getDuration().getSeconds()));
	         player.close();
	         
	         //音乐信息
	         musicInfoPanel.setMusicState("停止播放");
	         
	         //最小化音乐
	         northPanel.setMiniMusicLabel("停止播放："+currentMusic.getMusicName());
	         
		}
	}
	
	/**
	 * 删除音乐
	 */
	public static void deleteMusic(){
		playerState = PlayerState.UNREALIZED;
        //按钮图片
        playButton.setIcon(new ImageIcon("images/buttonIcon/play.png"));
        playButton.setIconPath("images/buttonIcon/play");
        SliderUpdateThread.pause();
        progressSlider.setValue(0);
        player.close();
        
        titleLabel.setText("00:00/00:00");
        LRCLabel.getInstance().setStandOutSign(0);
        
        //音乐信息
        musicInfoPanel.setMusicInfoToPanel(null);
        musicInfoPanel.setMusicState(null);
        
        //设置歌词提示
        LRCLabel.setStr("");
        LRCLabel.getInstance().getLrcStringList().clear();
        
        currentMusic=null;
	}
	
	/**
     * 清空音乐
     */
    public static void clearMusic() {
    	
    	int result = JOptionPane.showConfirmDialog(null, "您确定要清空列表中的音乐吗？","提示",JOptionPane.YES_NO_OPTION);
    	if(result==0){
	        if (player != null) {
	            playerState = PlayerState.UNREALIZED;
	            //按钮图片
		        playButton.setIcon(new ImageIcon("images/buttonIcon/play.png"));
		        playButton.setIconPath("images/buttonIcon/play");
	            SliderUpdateThread.pause();
	            progressSlider.setValue(0);
	            player.close();
	        }
	        
	        titleLabel.setText("00:00/00:00");
	        LRCLabel.getInstance().setStandOutSign(0);
	        
	        
	        //清空音乐列表
	        if(null!=musicVector && musicVector.size()>0){
	        	musicVector.removeAllElements();
	            musicList.removeAll();
	            musicList.updateUI();
	            musicList.setSelectedIndex(-1);
	            musicList.setVisible(false);
	            
	            //音乐信息
	            musicInfoPanel.setMusicInfoToPanel(null);
	            musicInfoPanel.setMusicState(null);
	            
	            //设置歌词提示
	            LRCLabel.setStr("");
	            LRCLabel.getInstance().getLrcStringList().clear();
	            
	            currentMusic=null;
	        }
	        
    	}
        
    }
	
	/**
     * 上一曲
     */
    public static void prevMusic() {
        if (noCanPlaySong()) {
            return;
        }
        
        //如果是随机播放模式，下一曲需要是随机的
        if(EastPanel.getplayModel()==MusicConstant.MODE4){
        	randomMusic();	
        }else{
	        //是否播放到第一曲，如果是返回到最后一曲
	        int index = musicVector.indexOf(currentMusic);
	        if (index == 0) {
	            index = musicVector.size() - 1;
	        } else {
	            index--;
	        }
	        
	        //判断歌曲是否可以播放，不能播放，播放上一曲
	        if (musicVector.elementAt(index).isCanPlay()) {
	        	musicList.setSelectedIndex(index);
	            initPlayer(musicVector.elementAt(index));
	            playMusic();
	        } else {
	        	currentMusic = musicVector.elementAt(index);
	        	prevMusic();
	        }
        }
    }
    
	 /**
     * 下一曲
     */
    public static void nextMusic() {
    	//不能播放返回
        if (noCanPlaySong()) {
            return;
        }
        
        //如果是随机播放模式，下一曲需要是随机的
        if(EastPanel.getplayModel()==MusicConstant.MODE4){
        	randomMusic();	
        }else{
        	//是否播放到最后一曲，如果是返回第一曲
            int index = musicVector.indexOf(currentMusic);
            if (index == musicVector.size() - 1) {
                index = 0;
            } else {
                index++;
            }
            
            //判断歌曲是否可以播放，不能播放，播放下一曲
            if (musicVector.elementAt(index).isCanPlay()) {
                initPlayer(musicVector.elementAt(index));
                playMusic();
            } else {
            	currentMusic = musicVector.elementAt(index);
            	nextMusic();
            }
        }
        
    }

    /**
     * 顺序播放
     */
    public  static void orderMusic(){
    	 int index = musicVector.indexOf(currentMusic);
    	 if(index==musicVector.size() - 1){
    		 stopMusic();
    	 }else{
    		 nextMusic(); 
    	 }
    }
    
    /**
     * 倒序播放
     */
    public static void invertedOrderMusic(){
    	int index = musicVector.indexOf(currentMusic);
    	if(index==0){
    		stopMusic();
    	}else{
    		prevMusic();
    	}
    }
    
    /**
     * 随机播放
     */
    public   static void randomMusic() {
        int randNumber = (int) (Math.random() * (musicVector.size() - 1));
        initPlayer(musicVector.elementAt(randNumber));
        playMusic();
        //musicList.setSelectedIndex(randNumber);
    }
    
    /**
     * 单曲播放
     */
    public static void  singleMuisc(){
    	if(null!=currentMusic){
    		initPlayer(currentMusic);	
    		playMusic();
    	}
    	
    }
    
    /**
     * 设置静音
     * @param soundState
     */
    public static void setSoundState(boolean soundState) {
    	
        if (player != null) {
            player.getGainControl().setMute(soundState);
            if (soundState == false) {
                soundButton.setIcon(new ImageIcon("images/buttonIcon/sound.png"));
                soundButton.setIconPath("images/buttonIcon/sound");
            } else {
                soundButton.setIcon(new ImageIcon("images/buttonIcon/nosound.png"));
                soundButton.setIconPath("images/buttonIcon/nosound");
            }
            MusicPlayer.soundState = soundState;
        }
    }
    
    /**
	 * 获得URL
	 * @param 
	 * @return
	 */
	private static URL getURL(String musicPath) {
        URL mUrl = null;
        try {
            if (musicPath.startsWith("http://")) {
            	mUrl = new URL(musicPath);
            } else {
            	mUrl = new File(musicPath).toURI().toURL();
            }
        } catch (MalformedURLException ex) {
            System.out.println("初始化URL失败");
        }
        return mUrl;
    }
	
	
	/**
	 * 音乐是否可播放
	 * 
	 * @return
	 */
	public static boolean noCanPlaySong() {
		int count = 0;
		if (musicVector.size() == 0) {
			return true;
		}
		for (int i = musicVector.size() - 1; i >= 0; i--) {
			if (!musicVector.elementAt(i).isCanPlay()) {
				count++;
			}
		}
		if (count == musicVector.size()) {
			return true;
		}
		return false;
	}

	/**
	 * 获得player
	 * 
	 * @return
	 */
	public static Player getPlayer() {
		return player;
	}
	
	/**
	 * 声音状态
	 * @return
	 */
	public static boolean getSoundState() {
        return soundState;
    }
	
	/**
	 * 获得播放状态
	 * @return
	 */
	public static PlayerState getPlayerState(){
		return playerState;
	}
	
	/**
	 * 获得当前播放音乐
	 * @return
	 */
	public static MusicInfo getCurrentMusic(){
		return currentMusic;
	}
	
	/**
	 * 获得当前播放音乐
	 * @return
	 */
	public static void setCurrentMusic(MusicInfo musicInfo){
		MusicPlayer.currentMusic=musicInfo;
	}
}


