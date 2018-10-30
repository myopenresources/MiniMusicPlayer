package com.cjh.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;


/**
 * ���ⰴť�˵�
 * 
 * @author cjh
 * 
 */
public class TitlePopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	
	/**
	 * �������
	 */
	private JMenuItem addMusic = null;
	
	/**
	 * �������·��
	 */
	private JMenuItem addMusicPath = null;
	
	/**
	 * Ƥ���ϴ�
	 */
	private JMenuItem skinUpload = null;
	
	/**
	 * Ƥ��
	 */
	private JMenuItem skin = null;
	
	/**
	 * ����
	 */
	private JMenuItem setting = null;
	
	
	/**
	 * �������ֲ�����
	 */
	private JMenuItem aboutMusicPlayer = null;
	
	
	/**
	 * ����
	 */
	private static TitlePopupMenu titlePopupMenu = new TitlePopupMenu();

	/**
	 * ˽�й��췽��
	 */
	private TitlePopupMenu() {
		init();
	}
	
	/**
	 * ���ʵ�� 
	 * @return
	 */
	public static TitlePopupMenu getInstance() {
		if (null == titlePopupMenu) {
			System.out.println("���ⵯ���˵���ʼ������");
			return null;
		}
		return titlePopupMenu;
	}

	/**
	 * ��ʼ������
	 */
	private void init() {
		 addMusic = new JMenuItem("�������",new  ImageIcon("images/buttonIcon/addFileIcon.gif"));
		 addMusicPath = new JMenuItem("���Ŀ¼����",new  ImageIcon("images/buttonIcon/addFolderIcon.gif"));
		 skinUpload = new JMenuItem("Ƥ���ϴ�",new  ImageIcon("images/buttonIcon/skinUploadIcon.gif"));
		 skin = new JMenuItem("Ƥ������",new  ImageIcon("images/buttonIcon/skinIcon.gif"));
		 setting = new JMenuItem("����������",new  ImageIcon("images/buttonIcon/settingIcon.gif"));
		 aboutMusicPlayer = new JMenuItem("���ڰ�������",new  ImageIcon("images/buttonIcon/aboutIcon.gif"));
		
		 this.add(addMusic);
		 this.add(addMusicPath);
		 this.add(skinUpload);
		 this.add(skin);
		 this.add(setting);
		 this.add(aboutMusicPlayer);
		
		 //�¼�
		 this.event();
	}

	
	/**
	 * �¼�
	 */
	private void event(){
		
		//�������
		addMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//�����ʾ
				if(e.getButton()==MouseEvent.BUTTON1){
					new  AddMusicFileChooser();
				}
			}
		});
		
		//���Ŀ¼����
		addMusicPath.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//�����ʾ
				if(e.getButton()==MouseEvent.BUTTON1){
					new  AddMuiscPathChooser();
				}
			}
		});
		
	    //Ƥ���ϴ�
		skinUpload.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//�����ʾ
				if(e.getButton()==MouseEvent.BUTTON1){
					new AddSkinFileChooser();
				}
			}
		});
		
		//Ƥ��
		skin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//�����ʾ
				if(e.getButton()==MouseEvent.BUTTON1){
					new  SkinFrame().setVisible(true);
					
				}
			}
		});
		
		//����������
		setting.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//�����ʾ
				if(e.getButton()==MouseEvent.BUTTON1){
					MusicSetFrame.getInstance().setVisible(true);
					
				}
			}
		});
		
		
		//���ڰ�������
		aboutMusicPlayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//�����ʾ
				if(e.getButton()==MouseEvent.BUTTON1){
					JOptionPane.showMessageDialog(null, "��������\n�汾��v2.0\n���ߣ��ٱ�С��\n�޸�ʱ�䣺2015-08-02");
				}
			}
		});
		
		
		
		
		
		
	}
}
