package com.cjh.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import com.cjh.music.MusicInfo;
import com.cjh.music.MusicPlayer;

/**
 * �����б�˵�
 * 
 * @author cjh
 * 
 */
public class MuiscListPopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	
	
	/**
	 * ɾ������
	 */
	private JMenuItem deleteMusic = null;
	
	/**
	 * �������
	 */
	private JMenuItem clearMusic = null;
	
	/**
	 * �ϴ����
	 */
	private JMenuItem uploadLrc = null;
	
	/**
	 * �鿴��������
	 */
	private JMenuItem queryMusicDetail = null;
	
	
	
	/**
	 * ����
	 */
	private static MuiscListPopupMenu muiscListPopupMenu = new MuiscListPopupMenu();

	/**
	 * ˽�й��췽��
	 */
	private MuiscListPopupMenu() {
		init();
	}
	
	/**
	 * ���ʵ�� 
	 * @return
	 */
	public static MuiscListPopupMenu getInstance() {
		if (null == muiscListPopupMenu) {
			System.out.println("�����б����˵���ʼ������");
			return null;
		}
		return muiscListPopupMenu;
	}

	/**
	 * ��ʼ������
	 */
	private void init() {
		 deleteMusic = new JMenuItem("ɾ������",new  ImageIcon("images/buttonIcon/deleteMusicIcon.gif"));
		 clearMusic = new JMenuItem("����б�",new  ImageIcon("images/buttonIcon/chearMusicIcon.gif"));
		 uploadLrc = new JMenuItem("�ϴ����",new  ImageIcon("images/buttonIcon/uploadLrcIcon.gif"));
		 queryMusicDetail  = new JMenuItem("��������",new  ImageIcon("images/buttonIcon/musicDetailIcon.gif"));
		
		 this.add(deleteMusic);
		 this.add(clearMusic);
		 this.add(uploadLrc);
		 this.add(queryMusicDetail);
		
		 //�¼�
		 this.event();
	}

	
	/**
	 * �¼�
	 */
	private void event(){
		
		//ɾ������
		deleteMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//�����ʾ
				if(e.getButton()==MouseEvent.BUTTON1){
					
					try{
					  JList musicList = EastPanel.getInstance().getMusicList();
					  Vector<MusicInfo> musicVector = EastPanel.getInstance().getMusicVector();
					  int index = musicList.getSelectedIndex();
					  
					  int result = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫɾ�� ��"+musicVector.get(index).getMusicName()+"�� ��","��ʾ",JOptionPane.YES_NO_OPTION);
					  if(result==0){
						  if(null!=MusicPlayer.getPlayer()){
							  MusicPlayer.deleteMusic();
						  }
						  musicVector.removeElementAt(index);
						  musicList.updateUI();
					  }
					  
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ�������֣�");
					}
					
				}
			}
		});
		
		//��������б�
		clearMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//�����ʾ
				if(e.getButton()==MouseEvent.BUTTON1){
					MusicPlayer.clearMusic();
				}
			}
		});
		
		//�ϴ����
		uploadLrc.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//�����ʾ
				if(e.getButton()==MouseEvent.BUTTON1){
					try{
					   JList musicList = EastPanel.getInstance().getMusicList();
					   Vector<MusicInfo> musicVector = EastPanel.getInstance().getMusicVector();
					   int index = musicList.getSelectedIndex();
					   MusicInfo m = musicVector.get(index);
					   new AddLrcFileChooser(m);
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�ϴ��ĸ�����");
					}
				}  
			}
		});
		
		
		//�鿴��������
		queryMusicDetail.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//�����ʾ
				if(e.getButton()==MouseEvent.BUTTON1){
					
					try{
					  JList musicList = EastPanel.getInstance().getMusicList();
					  Vector<MusicInfo> musicVector = EastPanel.getInstance().getMusicVector();
					  int index = musicList.getSelectedIndex();
					  MusicInfo m = musicVector.get(index);
					  JOptionPane.showMessageDialog(null, "������"+m.getMusicName()+"\nר����"+m.getMusicAlbum()+"\n���֣�"+m.getMusicArtist()+"\n����ʱ�䣺"+m.getYear()+"\n������Ϣ��"+m.getComment()+"\n����·����"+m.getMusicPath());
					  
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�鿴�ĸ�����");
					}
					
				}
			}
		});
	}
}
