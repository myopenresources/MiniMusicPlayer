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
 * @author ��ѯ����
 *
 */
public class QueryMusicFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * ��ѯ�ı���
	 */
	private JTextField queryTextField = null;
	
	/**
	 * ��ѯ��ť
	 */
	private JButton queryButton = null;
	
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
	private QueryMuiscListPopupMenu queryMuiscListPopupMenu= QueryMuiscListPopupMenu.getInstance();

	/**
	 * ����
	 */
	private static QueryMusicFrame  queryMusicFrame = new QueryMusicFrame();

	/**
	 * ˽�й��췽��
	 */
	private  QueryMusicFrame() {
		init();

	}

	/**
	 * ���ʵ�� 
	 * @return
	 */
	public static QueryMusicFrame getInstance(){
		if(null==queryMusicFrame){
			System.out.println("��ѯ���ڳ�ʼ������");
			return null;
		}
		return queryMusicFrame;
	}
	/**
	 * ��ʼ������
	 */
	private void init() {
        this.setLayout(null);
         
		JLabel queyLabel = new JLabel("��������");
		queyLabel.setBounds(10, 10, 55, 25);
		this.add(queyLabel);
        
		queryTextField = new JTextField();
		queryTextField.setBounds(65, 10, 150, 25);
		this.add(queryTextField);
		
		queryButton = new JButton("��ѯ");
		queryButton.setBounds(220, 10, 100, 25);
		this.add(queryButton);
		

		//����
		musicVector = new Vector<MusicInfo>();
		
		
		//�б�
		musicList = new JList(musicVector);
		musicList.setFont(new Font("����", 12, 12));
		musicList.setCursor(new Cursor(Cursor.HAND_CURSOR));
		musicList.setCellRenderer(new MusicInfoCellRenderer(musicVector));
		musicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		musicList.setOpaque(false);
		musicList.setVisible(false);
		
		
		//��������
		musicScrollPane = new JScrollPane(musicList);
		musicScrollPane.setOpaque(false);
		musicScrollPane.setBounds(10,50,310,220);
		
		//musicScrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(245,245,245)));
		this.add(musicScrollPane);
		
		
		this.setResizable(false);
		this.setTitle("��ѯ����");

		// ���ڴ�С
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		int width = toolKit.getScreenSize().width;
		int height = toolKit.getScreenSize().height;

		this.setBounds((width - 340) / 2, (height - 465) / 2, 340, 330);
		this.setVisible(true);
		
		this.event();
	}

	/**
	 * �¼�
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
		
		//��ѯ
		queryButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//����
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
					queryMuiscListPopupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
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
	 * ����ı���
	 * @return
	 */
	public JTextField  getQueryTextField(){
		return queryTextField;
	}


}