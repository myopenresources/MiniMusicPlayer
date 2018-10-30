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
 * 音乐列表菜单
 * 
 * @author cjh
 * 
 */
public class MuiscListPopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 删除音乐
	 */
	private JMenuItem deleteMusic = null;
	
	/**
	 * 清空音乐
	 */
	private JMenuItem clearMusic = null;
	
	/**
	 * 上传歌词
	 */
	private JMenuItem uploadLrc = null;
	
	/**
	 * 查看音乐详情
	 */
	private JMenuItem queryMusicDetail = null;
	
	
	
	/**
	 * 单例
	 */
	private static MuiscListPopupMenu muiscListPopupMenu = new MuiscListPopupMenu();

	/**
	 * 私有构造方法
	 */
	private MuiscListPopupMenu() {
		init();
	}
	
	/**
	 * 获得实例 
	 * @return
	 */
	public static MuiscListPopupMenu getInstance() {
		if (null == muiscListPopupMenu) {
			System.out.println("音乐列表弹出菜单初始化出错！");
			return null;
		}
		return muiscListPopupMenu;
	}

	/**
	 * 初始化方法
	 */
	private void init() {
		 deleteMusic = new JMenuItem("删除音乐",new  ImageIcon("images/buttonIcon/deleteMusicIcon.gif"));
		 clearMusic = new JMenuItem("清空列表",new  ImageIcon("images/buttonIcon/chearMusicIcon.gif"));
		 uploadLrc = new JMenuItem("上传歌词",new  ImageIcon("images/buttonIcon/uploadLrcIcon.gif"));
		 queryMusicDetail  = new JMenuItem("歌曲详情",new  ImageIcon("images/buttonIcon/musicDetailIcon.gif"));
		
		 this.add(deleteMusic);
		 this.add(clearMusic);
		 this.add(uploadLrc);
		 this.add(queryMusicDetail);
		
		 //事件
		 this.event();
	}

	
	/**
	 * 事件
	 */
	private void event(){
		
		//删除音乐
		deleteMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//左击显示
				if(e.getButton()==MouseEvent.BUTTON1){
					
					try{
					  JList musicList = EastPanel.getInstance().getMusicList();
					  Vector<MusicInfo> musicVector = EastPanel.getInstance().getMusicVector();
					  int index = musicList.getSelectedIndex();
					  
					  int result = JOptionPane.showConfirmDialog(null, "您确定要删除 “"+musicVector.get(index).getMusicName()+"” 吗？","提示",JOptionPane.YES_NO_OPTION);
					  if(result==0){
						  if(null!=MusicPlayer.getPlayer()){
							  MusicPlayer.deleteMusic();
						  }
						  musicVector.removeElementAt(index);
						  musicList.updateUI();
					  }
					  
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, "请选择要删除的音乐！");
					}
					
				}
			}
		});
		
		//清空音乐列表
		clearMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//左击显示
				if(e.getButton()==MouseEvent.BUTTON1){
					MusicPlayer.clearMusic();
				}
			}
		});
		
		//上传歌词
		uploadLrc.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//左击显示
				if(e.getButton()==MouseEvent.BUTTON1){
					try{
					   JList musicList = EastPanel.getInstance().getMusicList();
					   Vector<MusicInfo> musicVector = EastPanel.getInstance().getMusicVector();
					   int index = musicList.getSelectedIndex();
					   MusicInfo m = musicVector.get(index);
					   new AddLrcFileChooser(m);
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, "请选择要上传的歌曲！");
					}
				}  
			}
		});
		
		
		//查看音乐详情
		queryMusicDetail.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//左击显示
				if(e.getButton()==MouseEvent.BUTTON1){
					
					try{
					  JList musicList = EastPanel.getInstance().getMusicList();
					  Vector<MusicInfo> musicVector = EastPanel.getInstance().getMusicVector();
					  int index = musicList.getSelectedIndex();
					  MusicInfo m = musicVector.get(index);
					  JOptionPane.showMessageDialog(null, "歌曲："+m.getMusicName()+"\n专辑："+m.getMusicAlbum()+"\n歌手："+m.getMusicArtist()+"\n发行时间："+m.getYear()+"\n其它信息："+m.getComment()+"\n音乐路径："+m.getMusicPath());
					  
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, "请选择要查看的歌曲！");
					}
					
				}
			}
		});
	}
}
