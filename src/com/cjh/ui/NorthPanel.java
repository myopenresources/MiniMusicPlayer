package com.cjh.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * �����
 * 
 * @author cjh
 * 
 */
public class NorthPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * ����ͼƬ
	 */
	private Image  backgroudImg= null;
	
	/**
	 * ����
	 */
	private JLabel title = null;
	
	/**
	 * ��С����������
	 */
	private JLabel miniMusicLabel  = null;
	
	/**
	 * ���ⰴť
	 */
	private MusicButton titleButton= null;
	
	/**
	 * ��С����ť
	 */
	private MusicButton minButton = null;
	
	/**
	 * �رհ�ť
	 */
	private MusicButton closeButton = null;
	
	/**
	 * ����
	 */
	private static MusicFrame  musicFrame=null;
	
	

	/**
	 * ����
	 */
	private static NorthPanel northPanel = new NorthPanel();

	/**
	 * ˽�й��췽��
	 */
	private NorthPanel() {
		init();
	}

	/**
	 * ������ʵ�� 
	 * @return
	 */
	public static NorthPanel getInstance() {
		if (null == northPanel) {
			System.out.println("������ʼ������");
			return null;
		}
		return northPanel;
	}
	
	/**
	 * ��ʼ�����
	 */
	private  void init() {
		
		this.setOpaque(false);
		this.setLayout(null);
		//�߿�
		//this.setBorder(BorderFactory.createLineBorder(new Color(245,245,245), 1));
		
		//���ô�С
		this.setPreferredSize(new Dimension(this.getWidth(), 60));
		
		//��ʼ����ť
		this.initButton();
				
		//�¼�
		this.event();
		
		//��ʼ�������������ɫ
		//this.setPanelBackgroundImg("images/theme/001/NorthBG.jpg");

	}
	
	/**
	 * ��ʼ����ť
	 */
	private void initButton() {
		
		//���ⰴť
		titleButton = new MusicButton(new ImageIcon("images/buttonIcon/titleIcon.png"));
		titleButton.setIconPath("images/buttonIcon/titleIcon");
		titleButton.setToolTipText("��������");
		titleButton.setBounds(15, 15, 35, 35);
		this.add(titleButton);
		
		//����
		title = new JLabel("��������");
		title.setForeground(new Color(10,130,220));
		title.setFont(new Font("����",Font.BOLD,12));
		title.setBounds(45, 33, 100, 15);
		this.add(title);
		
		//��С����������
		miniMusicLabel = new JLabel();
		miniMusicLabel.setFont(new Font("����",Font.PLAIN,12));
		miniMusicLabel.setBounds(200,25, 350, 15);
		miniMusicLabel.setVisible(false);
		this.add(miniMusicLabel);
		
		
		//��С��
		minButton = new MusicButton(new ImageIcon("images/buttonIcon/minIcon.png"));
		minButton.setIconPath("images/buttonIcon/minIcon");
		minButton.setToolTipText("��С��");
		minButton.setBounds(705,3, 19, 19);
		this.add(minButton);
		
		//�ر�
		closeButton = new MusicButton(new ImageIcon("images/buttonIcon/closeIcon.png"));
		closeButton.setIconPath("images/buttonIcon/closeIcon");
		closeButton.setToolTipText("�ر�");
		closeButton.setBounds(725,3, 19, 19);
		this.add(closeButton);
		
	}
	

	
	/**
	 * �¼�
	 */
	private void event(){
		
		//���ⰴť
		titleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//�����ʾ
				if(e.getButton()==MouseEvent.BUTTON1){
				 TitlePopupMenu p = TitlePopupMenu.getInstance();
				 p.show(musicFrame,titleButton.getWidth()-20,titleButton.getHeight()+25);
				}
			}
		});
		
		
		// ��С���¼�
		minButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				musicFrame.dispose();
			}
		});
		
		
		// �ر��¼�
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫ�˳�����������(�ǣ��˳�������С����ȡ��������)","��ʾ",JOptionPane.YES_NO_CANCEL_OPTION);
				if(result==0){
					musicFrame.exitMusic();
				}else if(result==1){
				  musicFrame.dispose();
				}
			}
		});
		
		
		
	}

	
	/**
	 * ���ô���
	 * @param musicFrame
	 */
	public static void setMusicFrame(MusicFrame musicFrame){
	    NorthPanel.musicFrame = musicFrame;
	}
	
	
	/**
	 * ���ñ���ͼƬ
	 */
	public  void setTitleIcon(String imgPath){
		this.titleButton.setIcon(new ImageIcon(imgPath));
	}
	
	/**
	 * ���ñ���ͼƬ
	 * @param imgPath
	 */
	public void setPanelBackgroundImg(String imgPath){
		this.backgroudImg = new ImageIcon(imgPath).getImage();
		repaint();
	}
	
	/**
	 * ������С������
	 * @param musicName
	 */
	public void setMiniMusicLabel(String musicStr){
	     this.miniMusicLabel.setText(musicStr);
	}
	
	/**
	 * ������ʾ״̬
	 * @param sign
	 */
	public void setMiniMusicLabelState(boolean sign){
		this.miniMusicLabel.setVisible(sign);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Graphics2D g2d = (Graphics2D) g;
        // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);//�����
        
        
        //����ͼƬ
        if(null!=backgroudImg){
        	g.drawImage(backgroudImg,0,0,this.getWidth(),this.getHeight(),this);
        }
        
        
    }
	
	

}
