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
 * ���ֲ�����
 * 
 * @author cjh
 * 
 */
public class MusicPlayer {

	/**
	 * ������
	 */
	private  static Player player = null;
	
	/**
	 * �����
	 */
	private static NorthPanel northPanel = NorthPanel.getInstance();
	
	/**
	 * �����
	 */
	private static  EastPanel eastPanel = EastPanel.getInstance();
	
	/**
	 * �����
	 */
	private static  SouthPanel southPanel = SouthPanel.getInstance();
	
	
	/**
	 * �����б�
	 */
	private static  Vector<MusicInfo> musicVector = eastPanel.getMusicVector();
	
	/**
	 * �����б����
	 */
	private static JList musicList = eastPanel.getMusicList();
	
	/**
	 * ��������
	 */
	private static JSlider soundSlider = southPanel.getSoundSlider();
	
	/**
	 * ���ֽ��Ȼ���
	 */
	private static JSlider progressSlider = southPanel.getProgressSlider();
	
	/**
	 * ����ʱ��
	 */
	private static JLabel titleLabel = southPanel.getTimeLabel();
	
	
	/**
	 * ������ť
	 */
	private static MusicButton soundButton = southPanel.getSoundButton();
	
	/**
	 * ���Ű�ť
	 */
	private static MusicButton playButton = southPanel.getPlayButton();
	
	/**
	 * ��ǰ��������
	 */
	private static MusicInfo currentMusic= null;
	
	/**
	 * ����״̬
	 */
	private static boolean soundState=MusicConstant.NO_SOUND;
	
	/**
	 * ����״̬
	 */
	private static PlayerState playerState = PlayerState.UNREALIZED;
	
	/**
	 * ������Ϣ���
	 */
	private static MusicInfoPanel musicInfoPanel= MusicInfoPanel.getInstance();

	
	
	  /**
     * ��ʼ��Player
     * @param song
     */
    public static void initPlayer(MusicInfo musicInfo) {
        try {
        	//����ֻ��һ�׸��ڲ���
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
            	//����ǵ���ģʽ����һ����������һ��
                if(EastPanel.getplayModel()==MusicConstant.MODE5){
                	prevMusic();
                }else{
                	//��һ��
                    nextMusic();
                }
            }
            
          
        } catch (Exception ex) {
            System.out.println("�޷�����Player: " + musicInfo.getMusicName() + "���ܲ���");
            int index = musicVector.indexOf(currentMusic);
            //���ñ��Ϊ���ܲ���
            musicVector.elementAt(index).setCanPlay(false);
            
            //����ǵ���ģʽ����һ����������һ��
            if(EastPanel.getplayModel()==MusicConstant.MODE5){
            	prevMusic();
            }else{
            	//��һ��
                nextMusic();
            }
            
        }
    }
	
	/**
	 * ��������
	 */
	public static void playMusic() {
		 if (player != null && !noCanPlaySong()) {
	            player.getGainControl().setLevel(soundSlider.getValue() / 100.0F);
	            //setSoundState(getSoundState());
	            player.start();
	            //����������
	            SliderUpdateThread.initThread();
	            SliderUpdateThread.getThread().start();
	            SliderUpdateThread.resume();
	            
	            MusicPlayer.playerState = PlayerState.PLAY;
	            
	            //ʵ�ּ�����
	            player.addControllerListener(MusicControllerListener.getInstance());
	            
	            //�����б����ѡ��
	            int index = musicVector.indexOf(currentMusic);
	            musicList.setSelectedIndex(index);
	            musicList.updateUI();
	            
	            
	            //��ťͼƬ
	            playButton.setIcon(new ImageIcon("images/buttonIcon/pause.png"));
	            playButton.setIconPath("images/buttonIcon/pause");
	            
	            //������Ϣ
	            musicInfoPanel.setMusicInfoToPanel(currentMusic);
	            musicInfoPanel.setMusicState("���ڲ���");
	            
	            //��С������
	            northPanel.setMiniMusicLabel("���ڲ��ţ�"+currentMusic.getMusicName());
	            
	            //����Ǿ���״̬�����־���״̬
	            if(MusicPlayer.getSoundState()==MusicConstant.SOUND){
	            	MusicPlayer.setSoundState(MusicConstant.SOUND);
	            }else{
	            	MusicPlayer.setSoundState(MusicConstant.NO_SOUND);
	            }
	                  
	            
	            LRCParse.getInstance().LoadLRC();
	            LRCLabel.getInstance().refresh();
	            LRCLabelListener.getInstance();
	            LRCLabel.setStr("û�ҵ����...");
	            
	            
	        }

	}
	

	/**
	 * ��ͣ����
	 */
	public static void pauseMusic() {
		if (null != player) {
			player.stop();
            playerState = PlayerState.PAUSE;
            
            //��ťͼƬ
            playButton.setIcon(new ImageIcon("images/buttonIcon/play.png"));
            playButton.setIconPath("images/buttonIcon/play");
            
            //������Ϣ
            musicInfoPanel.setMusicState("��ͣ����");
            
            //��С������
            northPanel.setMiniMusicLabel("��ͣ���ţ�"+currentMusic.getMusicName());
		}

	}

	/**
	 * ֹͣ����
	 */
	public static void stopMusic() {
		if (null != player) {
			 playerState = PlayerState.UNREALIZED;
			 //��ťͼƬ
	         playButton.setIcon(new ImageIcon("images/buttonIcon/play.png"));
	         playButton.setIconPath("images/buttonIcon/play");
	         
	         //ֹͣ�߳�
	         SliderUpdateThread.pause();
	         
	         progressSlider.setValue(0);
	         titleLabel.setText("00:00/" + FormatUtils.formatTime(player.getDuration().getSeconds()));
	         player.close();
	         
	         //������Ϣ
	         musicInfoPanel.setMusicState("ֹͣ����");
	         
	         //��С������
	         northPanel.setMiniMusicLabel("ֹͣ���ţ�"+currentMusic.getMusicName());
	         
		}
	}
	
	/**
	 * ɾ������
	 */
	public static void deleteMusic(){
		playerState = PlayerState.UNREALIZED;
        //��ťͼƬ
        playButton.setIcon(new ImageIcon("images/buttonIcon/play.png"));
        playButton.setIconPath("images/buttonIcon/play");
        SliderUpdateThread.pause();
        progressSlider.setValue(0);
        player.close();
        
        titleLabel.setText("00:00/00:00");
        LRCLabel.getInstance().setStandOutSign(0);
        
        //������Ϣ
        musicInfoPanel.setMusicInfoToPanel(null);
        musicInfoPanel.setMusicState(null);
        
        //���ø����ʾ
        LRCLabel.setStr("");
        LRCLabel.getInstance().getLrcStringList().clear();
        
        currentMusic=null;
	}
	
	/**
     * �������
     */
    public static void clearMusic() {
    	
    	int result = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫ����б��е�������","��ʾ",JOptionPane.YES_NO_OPTION);
    	if(result==0){
	        if (player != null) {
	            playerState = PlayerState.UNREALIZED;
	            //��ťͼƬ
		        playButton.setIcon(new ImageIcon("images/buttonIcon/play.png"));
		        playButton.setIconPath("images/buttonIcon/play");
	            SliderUpdateThread.pause();
	            progressSlider.setValue(0);
	            player.close();
	        }
	        
	        titleLabel.setText("00:00/00:00");
	        LRCLabel.getInstance().setStandOutSign(0);
	        
	        
	        //��������б�
	        if(null!=musicVector && musicVector.size()>0){
	        	musicVector.removeAllElements();
	            musicList.removeAll();
	            musicList.updateUI();
	            musicList.setSelectedIndex(-1);
	            musicList.setVisible(false);
	            
	            //������Ϣ
	            musicInfoPanel.setMusicInfoToPanel(null);
	            musicInfoPanel.setMusicState(null);
	            
	            //���ø����ʾ
	            LRCLabel.setStr("");
	            LRCLabel.getInstance().getLrcStringList().clear();
	            
	            currentMusic=null;
	        }
	        
    	}
        
    }
	
	/**
     * ��һ��
     */
    public static void prevMusic() {
        if (noCanPlaySong()) {
            return;
        }
        
        //������������ģʽ����һ����Ҫ�������
        if(EastPanel.getplayModel()==MusicConstant.MODE4){
        	randomMusic();	
        }else{
	        //�Ƿ񲥷ŵ���һ��������Ƿ��ص����һ��
	        int index = musicVector.indexOf(currentMusic);
	        if (index == 0) {
	            index = musicVector.size() - 1;
	        } else {
	            index--;
	        }
	        
	        //�жϸ����Ƿ���Բ��ţ����ܲ��ţ�������һ��
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
     * ��һ��
     */
    public static void nextMusic() {
    	//���ܲ��ŷ���
        if (noCanPlaySong()) {
            return;
        }
        
        //������������ģʽ����һ����Ҫ�������
        if(EastPanel.getplayModel()==MusicConstant.MODE4){
        	randomMusic();	
        }else{
        	//�Ƿ񲥷ŵ����һ��������Ƿ��ص�һ��
            int index = musicVector.indexOf(currentMusic);
            if (index == musicVector.size() - 1) {
                index = 0;
            } else {
                index++;
            }
            
            //�жϸ����Ƿ���Բ��ţ����ܲ��ţ�������һ��
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
     * ˳�򲥷�
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
     * ���򲥷�
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
     * �������
     */
    public   static void randomMusic() {
        int randNumber = (int) (Math.random() * (musicVector.size() - 1));
        initPlayer(musicVector.elementAt(randNumber));
        playMusic();
        //musicList.setSelectedIndex(randNumber);
    }
    
    /**
     * ��������
     */
    public static void  singleMuisc(){
    	if(null!=currentMusic){
    		initPlayer(currentMusic);	
    		playMusic();
    	}
    	
    }
    
    /**
     * ���þ���
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
	 * ���URL
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
            System.out.println("��ʼ��URLʧ��");
        }
        return mUrl;
    }
	
	
	/**
	 * �����Ƿ�ɲ���
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
	 * ���player
	 * 
	 * @return
	 */
	public static Player getPlayer() {
		return player;
	}
	
	/**
	 * ����״̬
	 * @return
	 */
	public static boolean getSoundState() {
        return soundState;
    }
	
	/**
	 * ��ò���״̬
	 * @return
	 */
	public static PlayerState getPlayerState(){
		return playerState;
	}
	
	/**
	 * ��õ�ǰ��������
	 * @return
	 */
	public static MusicInfo getCurrentMusic(){
		return currentMusic;
	}
	
	/**
	 * ��õ�ǰ��������
	 * @return
	 */
	public static void setCurrentMusic(MusicInfo musicInfo){
		MusicPlayer.currentMusic=musicInfo;
	}
}


