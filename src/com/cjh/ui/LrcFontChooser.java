package com.cjh.ui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import com.cjh.config.MusicPlayerConfig;


/**
 * �������ѡ����
 * @author cjh
 *
 */
public class LrcFontChooser extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * ��ǰ����
	 */
	private String  currentFontName ="����";
	
	/**
	 * ��ǰ�ֺ�
	 */
	private String currentFontSize ="13";
	
	
	/**
	 * ����
	 */
	private String [] fontNames =null;
	
	/**
	 * �����С
	 */
	private String [] fontSizes = null;
	
	/**
	 * �����б�
	 */
	private JList  fontNameList = null;      
	
	/**
	 * �����С�б�
	 */
	private JList  fontSizeList = null; 
	
	/**
	 * �����б��������
	 */
	private JScrollPane fontNameListScrollPane = null;
	
	/**
	 * �����С�б��������
	 */
	private JScrollPane fontSizeListScrollPane = null;
	
	
	/**
	 * �ر�
	 */
	private JButton exitButton = null;
	
	private static LrcFontChooser lineFontChooser = new LrcFontChooser();
	
	private   LrcFontChooser(){
		init();
	}
	
	private void init(){
		
		//��ȡ�����ļ�
		currentFontName = MusicPlayerConfig.getInstance().getPopString("lrcFontName");
		currentFontSize = MusicPlayerConfig.getInstance().getPopString("lrcFontSize");
		
		JLabel fontNameTitle = new JLabel("���壺");
		fontNameTitle.setBounds(10, 10, 100, 20);
		this.add(fontNameTitle);
		
		JLabel fontSizeTitle = new JLabel("�ֺţ�");
		fontSizeTitle.setBounds(200, 10, 100, 20);
		this.add(fontSizeTitle);
		
		//�����б��������
		//ȡ�õ�ǰ������������.  
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();  
        fontNames = ge.getAvailableFontFamilyNames();  
        
        fontNameList = new JList(fontNames);  
        int index = this.indexOf(fontNames, currentFontName);
        if(index!=-1){
        	fontNameList.setSelectedIndex(index);
        }else{
        	fontNameList.setSelectedIndex(0);
        }
        
		fontNameListScrollPane = new JScrollPane(fontNameList);
		fontNameListScrollPane.setBounds(10, 30, 150, 200);
        this.add(fontNameListScrollPane);
        
        
		
		//�����С�б��������
        fontSizes = new String[] {"10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","28","29","30"};
        fontSizeList = new JList(fontSizes);
        index = this.indexOf(fontSizes, currentFontSize);
        if(index!=-1){
        	fontSizeList.setSelectedIndex(index);
        }else{
        	fontSizeList.setSelectedIndex(0);
        }
        
		fontSizeListScrollPane = new JScrollPane(fontSizeList);
		fontSizeListScrollPane.setBounds(200, 30, 60, 200);
        this.add(fontSizeListScrollPane);
        
        
        exitButton = new JButton("�ر�");
        exitButton.setBounds(100, 250, 70, 25);
        this.add(exitButton);
        
        
        
        
        this.setLayout(null); 
        this.setResizable(false);
		this.setTitle("����ѡ��");

		// ���ڴ�С
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		int width = toolKit.getScreenSize().width;
		int height = toolKit.getScreenSize().height;

		this.setBounds((width - 340) / 2, (height - 465) / 2, 277, 320);
		this.setVisible(false);
		
		this.event();
        
        
	}
	
	public static LrcFontChooser getInstance(){
		if(null==lineFontChooser){
			System.out.println("������ѡ�񴰿ڳ�ʼ������");
			return null;
		}
		return lineFontChooser;
	}
	
	/**
	 * �¼�
	 */
	private void event(){
		//����
		fontNameList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//˫����ʾ
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					currentFontName = fontNames[fontNameList.getSelectedIndex()];
					MusicSetFrame.getInstance().setLrcFontStateLabel(currentFontName,currentFontSize);
					
					//�޸ĸ����ʾ
					LRCLabel.getInstance().setLrcFont(new Font(currentFontName,Font.PLAIN,Integer.valueOf(currentFontSize)));
					//��������
					MusicPlayerConfig.getInstance().setPopString("lrcFontName", currentFontName);
				}
			}
		});
		
		//�ֺ�
		fontSizeList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//˫����ʾ
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					currentFontSize = fontSizes[fontSizeList.getSelectedIndex()];
					MusicSetFrame.getInstance().setLrcFontStateLabel(currentFontName,currentFontSize);
					//�޸ĸ����ʾ
					LRCLabel.getInstance().setLrcFont(new Font(currentFontName,Font.PLAIN,Integer.valueOf(currentFontSize)));
					//��������
					MusicPlayerConfig.getInstance().setPopString("lrcFontSize", currentFontSize);
				}
			}
		});
		
		//�ر�
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//˫����ʾ
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					dispose();
				}
			}
		});
	}
	
	
	/**
	 * ��õ�ǰ����
	 * @return
	 */
	public String getCurrentFontName(){
		return currentFontName;
	}
	
	/**
	 * ��õ�ǰ�ֺ�
	 * @return
	 */
	public String getCurrentFontSize(){
		return currentFontSize;
	}
	
	/**
	 * �б��в����ַ���
	 * @param list
	 * @param str
	 * @return
	 */
	private int indexOf(String[] list , String str){
		for(int i=0;i<list.length;i++){
			if(list[i].equals(str)){
				return i;
			}
		}
	    return -1;
	}
	
	
}
