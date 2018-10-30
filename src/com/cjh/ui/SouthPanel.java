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
 * �����
 * 
 * @author cjh
 * 
 */
public class SouthPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * ����ͼƬ
	 */
	private Image backgroudImg = null;

	/**
	 * ���Ű�ť
	 */
	private MusicButton playButton = null;
	
	
	/**
	 * ��һ��
	 */
	private MusicButton prevButton = null;
	
	/**
	 * ��һ��
	 */
	private MusicButton nextButton = null;
	
	/**
	 * ����
	 */
	private MusicButton soundButton = null;
	
	
	/**
	 * ��������
	 */
	private JSlider soundSlider = null;
	
	
	/**
	 * ���Ȼ���
	 */
	private JSlider progressSlider = null;
	
	/**
	 * ����ʱ��
	 */
	private JLabel timeLabel = null;
	

	/**
	 * ����
	 */
	private static SouthPanel southPanel = new SouthPanel();

	/**
	 * ˽�й��췽��
	 */
	private SouthPanel() {
		init();
	}
	
	/**
	 * ������ʵ��
	 * 
	 * @return
	 */
	public static SouthPanel getInstance() {
		if (null == southPanel) {
			System.out.println("������ʼ������");
			return null;
		}

		return southPanel;
	}

	/**
	 * ��ʼ�����
	 */
	private void init() {
		//this.setBackground(Color.red);
		// ��ʼ�������������ɫ
		//this.setPanelBackgroundImg("images/theme/001/NorthBG.jpg");
		
		//�߿�
		//this.setBorder(BorderFactory.createLineBorder(new Color(245,245,245), 1));
		this.setOpaque(false);
		this.setLayout(null);

		// ���ô�С
		this.setPreferredSize(new Dimension(this.getWidth(), 60));

		// ��ʼ�����ְ�ť
		this.initButton();
		
		//��ʼ������
		this.initSlider();
		
		//�¼�
		this.event();

	}

	/**
	 * ��ʼ����ť
	 */
	private void initButton() {

		// ���Ű�ť
		playButton = new MusicButton(new ImageIcon("images/buttonIcon/play.png"));
		playButton.setIconPath("images/buttonIcon/play");
		playButton.setToolTipText("����");
		playButton.setBounds(65, 5, 50, 50);
		this.add(playButton);
		
		
		//��һ��
		prevButton = new MusicButton(new ImageIcon("images/buttonIcon/prev.png"));
		prevButton.setIconPath("images/buttonIcon/prev");
		prevButton.setToolTipText("��һ��");
		prevButton.setBounds(20, 10, 40, 40);
		this.add(prevButton);
		
		//��һ��
		nextButton = new MusicButton(new ImageIcon("images/buttonIcon/next.png"));
		nextButton.setIconPath("images/buttonIcon/next");
		nextButton.setToolTipText("��һ��");
		nextButton.setBounds(120, 10, 40, 40);
		this.add(nextButton);
		
		//����
		String imgPath ="images/buttonIcon/sound"; 
		String text = "δ����";
		String str = MusicPlayerConfig.getInstance().getPopString("volume");
		if(null!=str && !"".equals(str)){
			if("0".equals(str)){
				imgPath="images/buttonIcon/nosound";
				text="����";
			}
		}
		soundButton = new MusicButton(new ImageIcon(imgPath+".png"));
		soundButton.setIconPath(imgPath);
		soundButton.setToolTipText(text);
		soundButton.setBounds(590, 15, 32, 32);
		this.add(soundButton);

	}
	
	/**
	 * ��ʼ������
	 */
	private void initSlider(){
		//��ʼ������������
		soundSlider = new JSlider(0,100,50);
		soundSlider.setToolTipText("����50%");
		soundSlider.setOpaque(false);
		soundSlider.setCursor(new Cursor(Cursor.HAND_CURSOR));
		soundSlider.setBounds(630, 23, 100, 16);
		soundSlider.setUI(new SoundSliderStyle(soundSlider));
		
		//�������ļ��л������
		String volume = MusicPlayerConfig.getInstance().getPopString("volume");
		if(null!=volume && !"".equals(volume)){
		  soundSlider.setValue(Integer.valueOf(volume));
		  soundSlider.setToolTipText("����"+volume+"%");
		}
		
		this.add(soundSlider);
		
		
		//��ʼ�����ֽ�����
		progressSlider = new JSlider(0,1000,0);
		progressSlider.setOpaque(false);
		progressSlider.setCursor(new Cursor(Cursor.HAND_CURSOR));
		progressSlider.setBounds(185, 25, 300, 16);
		progressSlider.setUI(new ProgressSliderStyle(progressSlider));
		this.add(progressSlider);
		
		
		//����ʱ��
		timeLabel = new JLabel("00:00/00:00");
		timeLabel.setBounds(495, 23, 100, 16);
		timeLabel.setFont(new Font("����",Font.BOLD,13));
		timeLabel.setForeground(new Color(10,130,220));
		this.add(timeLabel);
		
	}

	/**
	 * �¼�
	 */
    private void event(){
    	
    	//����
    	playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				if(MusicPlayer.getPlayerState() == PlayerState.PLAY){
					MusicPlayer.pauseMusic();
					playButton.setToolTipText("��ͣ");
					
				}else if(MusicPlayer.getPlayerState() == PlayerState.PAUSE){
					MusicPlayer.playMusic();
					playButton.setToolTipText("����");
				}else if(MusicPlayer.getPlayerState() == PlayerState.UNREALIZED){
					MusicPlayer.initPlayer(null);
					MusicPlayer.playMusic();
					
				}
			}
		});


    	
    	//��һ��
    	prevButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MusicPlayer.prevMusic();
			}
		});
    	
    	//��һ��
    	nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MusicPlayer.nextMusic();
				
			}
		});
    	
    	//����
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
    	
    	//��������
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
    		        
    		         //���������ļ�
    		        MusicPlayerConfig.getInstance().setPopString("volume", String.valueOf(soundSlider.getValue()));
    		        soundSlider.setToolTipText("����: " + soundSlider.getValue() + "%");
    		    }
    	});
    	
    	//���ֽ��Ȼ���
    	progressSlider.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				 Player  player = MusicPlayer.getPlayer();
			        if (player != null) {
			            double totalTime = player.getDuration().getSeconds();
			            double rate = progressSlider.getValue() * 1.0 / progressSlider.getMaximum();
			            //���
			            LRCLabel.getInstance().updateLRC(FormatUtils.roundDouble(totalTime * rate));
			            progressSlider.setToolTipText(FormatUtils.formatTime(totalTime * rate));
			        }
			}
			
    	});
    	
    	//���ֽ��Ȼ���
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
	 * ���ñ���ͼƬ
	 * 
	 * @param imgPath
	 */
	public void setPanelBackgroundImg(String imgPath) {
		this.backgroudImg = new ImageIcon(imgPath).getImage();
		repaint();
	}

	/**
	 * �����������
	 * @return
	 */
	public JSlider getSoundSlider() {
		return soundSlider;
	}

	/**
	 * ��ý���������
	 * @return
	 */
	public JSlider getProgressSlider() {
		return progressSlider;
	}
	
	
    /**
     * ���������ť
     * @return
     */
	public MusicButton getSoundButton() {
		return soundButton;
	}
	
	

	 /**
     * ��ò��Ű�ť
     * @return
     */
    public MusicButton getPlayButton() {
		return playButton;
	}

	/**
     * ��ò���ʱ��
     * @return
     */
	public JLabel getTimeLabel() {
		return timeLabel;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//sGraphics2D g2d = (Graphics2D) g;
		//sg2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);// �����

		// ����ͼƬ
		if (null != backgroudImg) {
			g.drawImage(backgroudImg, 0, 0, this.getWidth(), this.getHeight(),this);
		}

	}

}
