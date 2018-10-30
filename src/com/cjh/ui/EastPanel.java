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
 * �����
 * 
 * @author cjh
 * 
 */
public class EastPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	
	/**
	 * ����
	 */
	private JLabel musicListTitle = null;
	
	/**
	 * �����б�
	 */
	private Vector<MusicInfo> musicVector = null;
	
	/**
	 * �����б����
	 */
	private JList musicList = null;
	
	/**
	 * ��������
	 */
	private JScrollPane musicScrollPane = null;
	
	/**
	 * �����б�����
	 */
	private MuiscListPopupMenu muiscListPopupMenu= MuiscListPopupMenu.getInstance();
	
	/**
	 * �������
	 */
	private MusicButton addMusicButton = null;
	
	/**
	 * ���Ŀ¼����
	 */
	private MusicButton addFolderButton = null;
	
	/**
	 * ����ģʽ
	 */
	private MusicButton playModelButton = null;
	
	/**
	 * �������
	 */
	private MusicButton clearMusicButton = null;
	
	/**
	 * ��������
	 */
	private MusicButton queryMusicButton = null;
	
	/**
	 * ��ͼ
	 */
	private MusicButton snapShotButton = null;
	
	
	/**
	 * �Ƿ�����ظ�����
	 */
	private static int isRepeat = MusicConstant.NO_REPEAT;
	
	/**
	 * ����ģʽ
	 */
	private static int playModel = MusicConstant.MODE1;
	
	/**
	 * ����
	 */
	private static MusicFrame  musicFrame=null;
	
	
	
	

	/**
	 * ����
	 */
	private static EastPanel eastPanel = new EastPanel();

	/**
	 * �޲ι��췽��
	 */
	private EastPanel() {
		init();
	}

	/**
	 * ������ʵ��
	 * 
	 * @return
	 */
	public static EastPanel getInstance() {
		if (null == eastPanel) {
			System.out.println("������ʼ������");
		}

		return eastPanel;
	}

	/**
	 * ��ʼ�����
	 */
	private void init() {
		this.setLayout(null);
		this.setOpaque(false);
		
		//�������ļ��л�ò���ģʽ
		String model = MusicPlayerConfig.getInstance().getPopString("playModel");
		if(null!=model && !"".equals(model)){
			playModel =Integer.valueOf(model);
		}
		
		//�������ļ��л���Ƿ�����ظ�����
		String str = MusicPlayerConfig.getInstance().getPopString("isRepeat");
		if(null!=str && !"".equals(str)){
			isRepeat =Integer.valueOf(str);
		}
		
		//�߿�
		//this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(245,245,245)));

		// ���ô�С
		this.setPreferredSize(new Dimension(201, 400));
		
		//��ʼ�������б�
		this.initList();
		
		//��ʼ����ť
		this.initButton();
		
		//�¼�
		this.event();

	}
	
	/**
	 * ��ʼ�������б�
	 */
	private void initList(){
		musicListTitle = new JLabel("�����б�");
		musicListTitle.setBounds(0, 3, 100, 20);
		musicListTitle.setFont(new Font("����",Font.BOLD,13));
		musicListTitle.setForeground(new Color(10,130,220));
		this.add(musicListTitle);
		
		//����
		musicVector = new Vector<MusicInfo>();
		
		//�б�
		musicList = new JList(musicVector);
		musicList.setCursor(new Cursor(Cursor.HAND_CURSOR));
		musicList.setCellRenderer(new MusicInfoCellRenderer(musicVector));
		musicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		musicList.setOpaque(false);
		musicList.setVisible(false);
		
		//��������
		musicScrollPane = new JScrollPane(musicList);
		musicScrollPane.setUI(new MusicListScrollPanelUI());
		musicScrollPane.setOpaque(false);
		musicScrollPane.getViewport().setOpaque(false);
		musicScrollPane.setBounds(0,25,200,325);
		musicScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(245,245,245)));
		this.add(musicScrollPane);
		
	}
	
	
	/**
	 * ��ʼ����ť
	 */
	private void initButton(){
		
		 //�������
		 addMusicButton =  new MusicButton(new ImageIcon("images/buttonIcon/addMusic.png"));
		 addMusicButton.setIconPath("images/buttonIcon/addMusic");
		 addMusicButton.setToolTipText("�������");
		 addMusicButton.setBounds(15,355, 20, 20);
		 this.add(addMusicButton);
		
		 
		 //���Ŀ¼����
		 addFolderButton =  new MusicButton(new ImageIcon("images/buttonIcon/addFolder.png"));
		 addFolderButton.setIconPath("images/buttonIcon/addFolder");
		 addFolderButton.setToolTipText("���Ŀ¼����");
		 addFolderButton.setBounds(45,355, 20, 20);
		 this.add(addFolderButton);
	
		 
		 //����ģʽ
		 String modelIconPath = "";
		 String tipText = "";
		 //˳�򲥷�
         if (MusicConstant.MODE1==EastPanel.getplayModel()) {
        	 modelIconPath="images/buttonIcon/orderModel";
        	 tipText="˳�򲥷�";
         
         //�б�ѭ��
         }else if(MusicConstant.MODE2 == EastPanel.getplayModel()){
        	 modelIconPath="images/buttonIcon/loopModel";
        	 tipText="�б�ѭ��";
         
         //����ѭ��
         } else if (MusicConstant.MODE3 == EastPanel.getplayModel()) {
        	 modelIconPath="images/buttonIcon/singleModel";
        	 tipText="����ѭ��";
         //�������
         } else if (MusicConstant.MODE4 == EastPanel.getplayModel()) {
        	 modelIconPath="images/buttonIcon/randomModel";
        	 tipText="�������";
         //���򲥷�
         } else if(MusicConstant.MODE5 == EastPanel.getplayModel()){
        	 modelIconPath="images/buttonIcon/invertedOrderModel";
        	 tipText="���򲥷�";
         }
		 
		 playModelButton =  new MusicButton(new ImageIcon(modelIconPath+".png"));
		 playModelButton.setIconPath(modelIconPath);
		 playModelButton.setToolTipText(tipText);
		 playModelButton.setBounds(75,355, 20, 20);
		 this.add(playModelButton);
		 
		 
		 //����б�
		 clearMusicButton =  new MusicButton(new ImageIcon("images/buttonIcon/clearMusic.png"));
		 clearMusicButton.setIconPath("images/buttonIcon/clearMusic");
		 clearMusicButton.setToolTipText("����б�");
		 clearMusicButton.setBounds(105,355, 20, 20);
		 this.add(clearMusicButton);
		
		 //��������
		 queryMusicButton =  new MusicButton(new ImageIcon("images/buttonIcon/queryMusic.png"));
		 queryMusicButton.setIconPath("images/buttonIcon/queryMusic");
		 queryMusicButton.setToolTipText("��������");
		 queryMusicButton.setBounds(135,355, 20, 20);
		 this.add(queryMusicButton);
		 
		 //��ͼ
		 snapShotButton=new MusicButton(new ImageIcon("images/buttonIcon/snapShot.png"));
		 snapShotButton.setIconPath("images/buttonIcon/snapShot");
		 snapShotButton.setToolTipText("��ͼ");
		 snapShotButton.setBounds(165,355, 20, 20);
		 this.add(snapShotButton);
		
	}
	
	/**
	 * �¼�
	 */
	private void event(){
		//�����б����
		musicList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//˫����ʾ
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
		
		//�������
		addMusicButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//����
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					new  AddMusicFileChooser();
				}
			}
		});
		
		//���Ŀ¼����
		addFolderButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//����
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					new  AddMuiscPathChooser();
				}
			}
		});
		
		//����ģʽ
		playModelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//����
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					
					switch(EastPanel.getplayModel()){
					 case MusicConstant.MODE1:
						 EastPanel.setplayModel(MusicConstant.MODE2);
						 playModelButton.setIcon(new ImageIcon("images/buttonIcon/loopModel.png"));
						 playModelButton.setIconPath("images/buttonIcon/loopModel");
						 playModelButton.setToolTipText("�б�ѭ��");
					     break;
					     
					 case MusicConstant.MODE2:
						 EastPanel.setplayModel(MusicConstant.MODE3);
						 playModelButton.setIcon(new ImageIcon("images/buttonIcon/singleModel.png"));
						 playModelButton.setIconPath("images/buttonIcon/singleModel");
						 playModelButton.setToolTipText("����ѭ��");
					     break;
					     
					 case MusicConstant.MODE3:
						 EastPanel.setplayModel(MusicConstant.MODE4);
						 playModelButton.setIcon(new ImageIcon("images/buttonIcon/randomModel.png"));
						 playModelButton.setIconPath("images/buttonIcon/randomModel");
						 playModelButton.setToolTipText("�������");
					     break;
					     
					 case MusicConstant.MODE4:
						 EastPanel.setplayModel(MusicConstant.MODE5);
						 playModelButton.setIcon(new ImageIcon("images/buttonIcon/invertedOrderModel.png"));
						 playModelButton.setIconPath("images/buttonIcon/invertedOrderModel");
						 playModelButton.setToolTipText("���򲥷�");
					     break;
					     
					 case MusicConstant.MODE5:
						 EastPanel.setplayModel(MusicConstant.MODE1);
						 playModelButton.setIcon(new ImageIcon("images/buttonIcon/orderModel.png"));
						 playModelButton.setIconPath("images/buttonIcon/orderModel");
						 playModelButton.setToolTipText("˳�򲥷�");
					     break;
					}
					
					//��������
					MusicPlayerConfig.getInstance().setPopString("playModel", String.valueOf(EastPanel.getplayModel()));
					
				}
			}
		});
		
		
		//����б�
		clearMusicButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//����
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					MusicPlayer.clearMusic();
				}
			}
		});
		
		
		//��������
		queryMusicButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//����
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					QueryMusicFrame.getInstance().setVisible(true);
				}
			}
		});
		
		
		//��ͼ
		snapShotButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//����
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
						JOptionPane.showMessageDialog(null, "��ͼ��"+fileName+"."+imageFormat+"�����ѱ��浽"+path+"Ŀ¼�У�");
					}else{
						JOptionPane.showMessageDialog(null, "��ͼʧ�ܣ������½�ͼ��");
					}
				}
			}
		});
		
		
		
	}
	
	/**
	 * �����Ƿ�����ظ�����
	 * @param isRepeat
	 */
	public static void setIsRepeat(int isRepeat){
		EastPanel.isRepeat=isRepeat;
	}
	
	/**
	 * ��ò���ģʽ
	 * @return
	 */
	public static int getplayModel(){
		return EastPanel.playModel;
	}
	
	/**
	 * ���ò���ģʽ
	 * @return
	 */
	public static void  setplayModel(int state){
		EastPanel.playModel=state;
	}
	
	/**
	 * ���������б�
	 */
	public void updateMusicListUI(){
		//���������б�
		musicList.updateUI();
	}
	
	/**
	 * ��������б�
	 * @return
	 */
	public Vector<MusicInfo> getMusicVector(){
		return  musicVector;
	}
	
	/**
	 * ��������б����
	 * @return
	 */
	public JList getMusicList(){
		return musicList;
	}
	
	/**
	 * ������ֵ��б�
	 */
	public  void addMusic(MusicInfo musicInfo){
		musicList.setVisible(true);
		
		//���ظ��������
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
	 * ���ô���
	 * @param musicFrame
	 */
	public static void setMusicFrame(MusicFrame musicFrame){
	    EastPanel.musicFrame = musicFrame;
	}
	
	
	
	
	

}
