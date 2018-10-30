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
 * 查询音乐列表菜单
 * 
 * @author cjh
 * 
 */
public class QueryMuiscListPopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 删除音乐
	 */
	private JMenuItem deleteMusic = null;
	
	/**
	 * 清空列表
	 */
	private JMenuItem clearList = null;
	
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
	private static QueryMuiscListPopupMenu muiscListPopupMenu = new QueryMuiscListPopupMenu();

	/**
	 * 私有构造方法
	 */
	private QueryMuiscListPopupMenu() {
		init();
	}
	
	/**
	 * 获得实例 
	 * @return
	 */
	public static QueryMuiscListPopupMenu getInstance() {
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
		 clearList = new JMenuItem("清空列表",new  ImageIcon("images/buttonIcon/chearMusicIcon.gif"));
		 uploadLrc = new JMenuItem("上传歌词",new  ImageIcon("images/buttonIcon/uploadLrcIcon.gif"));
		 queryMusicDetail  = new JMenuItem("歌曲详情",new  ImageIcon("images/buttonIcon/musicDetailIcon.gif"));
		
		 this.add(deleteMusic);
		 this.add(clearList);
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
					  
					  //查询窗口
					  JList queryMusicList = QueryMusicFrame.getInstance().getMusicList();
					  Vector<MusicInfo> queryMusicVector = QueryMusicFrame.getInstance().getMusicVector();
					  
					  JList musicList = EastPanel.getInstance().getMusicList();
					  Vector<MusicInfo> musicVector = EastPanel.getInstance().getMusicVector();
					   
					  int index = queryMusicList.getSelectedIndex();
					  
					  int result = JOptionPane.showConfirmDialog(null, "您确定要删除 “"+queryMusicVector.get(index).getMusicName()+"” 吗？","提示",JOptionPane.YES_NO_OPTION);
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
						JOptionPane.showMessageDialog(null, "请选择要删除的音乐！");
					}
					
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
					   //查询窗口
					   JList queryMusicList = QueryMusicFrame.getInstance().getMusicList();
				       Vector<MusicInfo> queryMusicVector = QueryMusicFrame.getInstance().getMusicVector();
				       
					   int index = queryMusicList.getSelectedIndex();
					   MusicInfo m = queryMusicVector.get(index);
					   new AddLrcFileChooser(m);
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, "请选择要上传的歌曲！");
					}
				}  
			}
		});
		
		
		//清空音乐列表
		clearList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//左击显示
				if(e.getButton()==MouseEvent.BUTTON1){
					//查询窗口
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
		
		//查看音乐详情
		queryMusicDetail.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//左击显示
				if(e.getButton()==MouseEvent.BUTTON1){
					
					try{
					  //查询窗口
					  JList queryMusicList = QueryMusicFrame.getInstance().getMusicList();
					  Vector<MusicInfo> queryMusicVector = QueryMusicFrame.getInstance().getMusicVector();
					  int index = queryMusicList.getSelectedIndex();
					  MusicInfo m = queryMusicVector.get(index);
					  JOptionPane.showMessageDialog(null, "歌曲："+m.getMusicName()+"\n专辑："+m.getMusicAlbum()+"\n歌手："+m.getMusicArtist()+"\n发行时间："+m.getYear()+"\n其它信息："+m.getComment()+"\n音乐路径："+m.getMusicPath());
					  
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, "请选择要查看的歌曲！");
					}
					
				}
			}
		});
	}
}
