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
 * ��ѯ�����б�˵�
 * 
 * @author cjh
 * 
 */
public class QueryMuiscListPopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	
	
	/**
	 * ɾ������
	 */
	private JMenuItem deleteMusic = null;
	
	/**
	 * ����б�
	 */
	private JMenuItem clearList = null;
	
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
	private static QueryMuiscListPopupMenu muiscListPopupMenu = new QueryMuiscListPopupMenu();

	/**
	 * ˽�й��췽��
	 */
	private QueryMuiscListPopupMenu() {
		init();
	}
	
	/**
	 * ���ʵ�� 
	 * @return
	 */
	public static QueryMuiscListPopupMenu getInstance() {
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
		 clearList = new JMenuItem("����б�",new  ImageIcon("images/buttonIcon/chearMusicIcon.gif"));
		 uploadLrc = new JMenuItem("�ϴ����",new  ImageIcon("images/buttonIcon/uploadLrcIcon.gif"));
		 queryMusicDetail  = new JMenuItem("��������",new  ImageIcon("images/buttonIcon/musicDetailIcon.gif"));
		
		 this.add(deleteMusic);
		 this.add(clearList);
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
					  
					  //��ѯ����
					  JList queryMusicList = QueryMusicFrame.getInstance().getMusicList();
					  Vector<MusicInfo> queryMusicVector = QueryMusicFrame.getInstance().getMusicVector();
					  
					  JList musicList = EastPanel.getInstance().getMusicList();
					  Vector<MusicInfo> musicVector = EastPanel.getInstance().getMusicVector();
					   
					  int index = queryMusicList.getSelectedIndex();
					  
					  int result = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫɾ�� ��"+queryMusicVector.get(index).getMusicName()+"�� ��","��ʾ",JOptionPane.YES_NO_OPTION);
					  if(result==0){
						  if(null!=MusicPlayer.getPlayer()){
							  MusicPlayer.deleteMusic();
						  }
						  
						  int i= musicVector.indexOf(queryMusicVector.get(index));
						  musicVector.removeElementAt(i);
						  musicList.updateUI();
						  
						  queryMusicVector.removeElementAt(index);
						  queryMusicList.updateUI();
					  }
					  
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ�������֣�");
					}
					
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
					   //��ѯ����
					   JList queryMusicList = QueryMusicFrame.getInstance().getMusicList();
				       Vector<MusicInfo> queryMusicVector = QueryMusicFrame.getInstance().getMusicVector();
				       
					   int index = queryMusicList.getSelectedIndex();
					   MusicInfo m = queryMusicVector.get(index);
					   new AddLrcFileChooser(m);
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�ϴ��ĸ�����");
					}
				}  
			}
		});
		
		
		//��������б�
		clearList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//�����ʾ
				if(e.getButton()==MouseEvent.BUTTON1){
					//��ѯ����
					JList queryMusicList = QueryMusicFrame.getInstance().getMusicList();
				    Vector<MusicInfo> queryMusicVector = QueryMusicFrame.getInstance().getMusicVector();
					if(queryMusicVector.size()>0){
					    queryMusicVector.removeAllElements();
					    queryMusicList.updateUI();
					    QueryMusicFrame.getInstance().getQueryTextField().setText("");
					    queryMusicList.setVisible(false);
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
					  //��ѯ����
					  JList queryMusicList = QueryMusicFrame.getInstance().getMusicList();
					  Vector<MusicInfo> queryMusicVector = QueryMusicFrame.getInstance().getMusicVector();
					  int index = queryMusicList.getSelectedIndex();
					  MusicInfo m = queryMusicVector.get(index);
					  JOptionPane.showMessageDialog(null, "������"+m.getMusicName()+"\nר����"+m.getMusicAlbum()+"\n���֣�"+m.getMusicArtist()+"\n����ʱ�䣺"+m.getYear()+"\n������Ϣ��"+m.getComment()+"\n����·����"+m.getMusicPath());
					  
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�鿴�ĸ�����");
					}
					
				}
			}
		});
	}
}
