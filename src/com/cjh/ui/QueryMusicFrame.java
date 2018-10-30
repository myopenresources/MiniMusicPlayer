package com.cjh.ui;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.cjh.music.MusicInfo;
import com.cjh.music.MusicPlayer;
/**
 * 
 * @author 查询音乐
 *
 */
public class QueryMusicFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 查询文本框
	 */
	private JTextField queryTextField = null;
	
	/**
	 * 查询按钮
	 */
	private JButton queryButton = null;
	
	/**
	 * 音乐列表
	 */
	private Vector<MusicInfo> musicVector = null;
	
	/**
	 * 音乐列表组件
	 */
	private JList musicList = null;
	
	/**
	 * 滚动窗格
	 */
	private JScrollPane musicScrollPane = null;
	
	/**
	 * 音乐列表弹出框
	 */
	private QueryMuiscListPopupMenu queryMuiscListPopupMenu= QueryMuiscListPopupMenu.getInstance();

	/**
	 * 单例
	 */
	private static QueryMusicFrame  queryMusicFrame = new QueryMusicFrame();

	/**
	 * 私有构造方法
	 */
	private  QueryMusicFrame() {
		init();

	}

	/**
	 * 获得实例 
	 * @return
	 */
	public static QueryMusicFrame getInstance(){
		if(null==queryMusicFrame){
			System.out.println("查询窗口初始化出错！");
			return null;
		}
		return queryMusicFrame;
	}
	/**
	 * 初始化方法
	 */
	private void init() {
        this.setLayout(null);
         
		JLabel queyLabel = new JLabel("音乐名：");
		queyLabel.setBounds(10, 10, 55, 25);
		this.add(queyLabel);
        
		queryTextField = new JTextField();
		queryTextField.setBounds(65, 10, 150, 25);
		this.add(queryTextField);
		
		queryButton = new JButton("查询");
		queryButton.setBounds(220, 10, 100, 25);
		this.add(queryButton);
		

		//向量
		musicVector = new Vector<MusicInfo>();
		
		
		//列表
		musicList = new JList(musicVector);
		musicList.setFont(new Font("宋体", 12, 12));
		musicList.setCursor(new Cursor(Cursor.HAND_CURSOR));
		musicList.setCellRenderer(new MusicInfoCellRenderer(musicVector));
		musicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		musicList.setOpaque(false);
		musicList.setVisible(false);
		
		
		//滚动窗格
		musicScrollPane = new JScrollPane(musicList);
		musicScrollPane.setOpaque(false);
		musicScrollPane.setBounds(10,50,310,220);
		
		//musicScrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(245,245,245)));
		this.add(musicScrollPane);
		
		
		this.setResizable(false);
		this.setTitle("查询音乐");

		// 窗口大小
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		int width = toolKit.getScreenSize().width;
		int height = toolKit.getScreenSize().height;

		this.setBounds((width - 340) / 2, (height - 465) / 2, 340, 330);
		this.setVisible(true);
		
		this.event();
	}

	/**
	 * 事件
	 */
	private void event() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				queryTextField.setText("");
				musicVector.removeAllElements();
				musicList.updateUI();
				musicList.setVisible(false);
				dispose();
			}
		});
		
		//查询
		queryButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//单击
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					if(musicVector.size()>0){
						musicVector.removeAllElements();
						musicList.updateUI();
					}
					String str = queryTextField.getText();
					Vector<MusicInfo> list = EastPanel.getInstance().getMusicVector();
				
					for(MusicInfo m : list){
						if(-1!=m.getMusicName().toLowerCase().indexOf(str)){
							musicVector.addElement(m);
						}
					}
					
					if(musicVector.size()>0){
						musicList.setSelectedIndex(0);
						musicList.setVisible(true);
						musicList.updateUI();
					}else{
						musicList.setVisible(false);
					}
				}
			}
		});
		
		//音乐列表组件
		musicList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//双击显示
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
					queryMuiscListPopupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}
	
	
	/**
	 * 更新音乐列表
	 */
	public void updateMusicListUI(){
		//更新音乐列表
		musicList.updateUI();
	}
	
	/**
	 * 获得音乐列表
	 * @return
	 */
	public Vector<MusicInfo> getMusicVector(){
		return  musicVector;
	}
	
	/**
	 * 获得音乐列表组件
	 * @return
	 */
	public JList getMusicList(){
		return musicList;
	}
	
	/**
	 * 获得文本框
	 * @return
	 */
	public JTextField  getQueryTextField(){
		return queryTextField;
	}


}