package com.cjh.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.cjh.config.MusicPlayerConfig;
import com.cjh.config.StyleConfig;
import com.cjh.util.MusicConstant;

/**
 * ���ֲ���������
 * @author cjh
 *
 */
public class MusicSetFrame extends JFrame {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * �ո����
	 */
	private JTabbedPane setTabedPane = null;
	
	/**
	 * ��������
	 */
	private JPanel basePanel = null;
	
	/**
	 * �������
	 */
	private JPanel lrcPanel = null;
	
	/**
	 * ��ʽ����
	 */
	private JPanel stylePanel = null;
	
	/**
	 * �Ƿ�����ظ�����״̬
	 */
	private JLabel isRepeatStateLabel = null;
	
	/**
	 * ����ظ�����
	 */
	private JButton  repeatButton = null;
	
	/**
	 * ������ظ�����
	 */
	private JButton  noRepeatButton = null;
	
	/**
	 * ������״̬
	 */
	private JLabel lineFontStateLabel = null;
	
	/**
	 * ����������
	 */
	private  JButton lineFontButton =null;
	
	
	/**
	 * �������״̬
	 */
	private JLabel lrcFontStateLabel = null;
	
	/**
	 * ���������
	 */
	private  JButton lrcFontButton =null;
	
	/**
	 * �����״̬
	 */
	private JLabel  lineFontColorState = null;
	
	/**
	 * ��������ɫ����
	 */
	private  JButton lineFontColorButton =null;
	
	/**
	 * ���״̬
	 */
	private JLabel  lrcFontColorState = null;
	
	/**
	 * ���������ɫ����
	 */
	private  JButton lrcFontColorButton =null;
	
	
	/**
	 * ���ͻ��״̬
	 */
	private JLabel  lrcMarkFontColorState = null;
	
	/**
	 * ���ͻ��������ɫ����
	 */
	private  JButton lrcMarkFontColorButton =null;
	
	
	/**
	 * �����б�ѡ����ɫ״̬
	 */
	private JLabel  listSelectColorState = null;
	
	/**
	 * �����б�ѡ����ɫ����
	 */
	private  JButton listSelectColorButton =null;
	
	
	
	
	/**
	 * ����
	 */
	private static MusicSetFrame musicSetFrame = new MusicSetFrame();
	


	/**
	 * �޲ι��췽��
	 */
	private MusicSetFrame(){
		init();
	}
	
	/**
	 * ��ʼ������
	 */
	private void init(){
		setTabedPane = new JTabbedPane();
		setTabedPane.setBounds(0, 0, 340, 530);
		
		
		//��������
		basePanel = new JPanel();
		basePanel.setLayout(null);
		//basePanel.setOpaque(false);
		
		JLabel isRepeatLabel = new JLabel("�Ƿ�����ظ�����");
		isRepeatLabel.setFont(new Font("����",Font.BOLD,12));
		isRepeatLabel.setBounds(30, 20, 150, 15);
		
		//��ȡ�����ļ�
		String isRepeat = MusicPlayerConfig.getInstance().getPopString("isRepeat");
		String isRepeatText = "��ǰ״̬��������ظ�����";
		if(null!=isRepeat && !"".equals(isRepeat)){
			if("1".equals(isRepeat)){
				isRepeatText = "��ǰ״̬������ظ�����";
			}
		}
		
		isRepeatStateLabel = new JLabel(isRepeatText);
		isRepeatStateLabel.setBounds(30, 45,200, 15);
		
		repeatButton = new JButton("����ظ�����");
		repeatButton.setBounds(30, 70, 130, 25);
		
		noRepeatButton = new JButton("������ظ�����");
		noRepeatButton.setBounds(30, 105, 130, 25);
		
		
		basePanel.add(isRepeatLabel);
		basePanel.add(isRepeatStateLabel);
		basePanel.add(repeatButton);
		basePanel.add(noRepeatButton);
		
		
		//�б���ɫ����
		//ѡ����ɫ
		JLabel listLabel = new JLabel("���������б�ѡ����ɫ");
		listLabel.setFont(new Font("����",Font.BOLD,12));
		listLabel.setBounds(30, 145, 150, 15);
		basePanel.add(listLabel);
		
		//��ȡ�����ļ�
		String musicListSelectColorText = "��ǰ�����б�ѡ����ɫ��10,130,220";
		String musicListSelectbgColor = MusicPlayerConfig.getInstance().getPopString("musicListSelectbgColor");
		if(null!=musicListSelectbgColor && !"".equals(musicListSelectbgColor)){
			musicListSelectColorText="��ǰ�����б�ѡ����ɫ��"+musicListSelectbgColor;
		}
				
		
		listSelectColorState =  new JLabel(musicListSelectColorText);
		listSelectColorState.setBounds(30, 170, 350, 15);
		basePanel.add(listSelectColorState);
		
		listSelectColorButton = new JButton("ѡ����ɫ");
		listSelectColorButton.setBounds(30, 195, 130, 25);
		basePanel.add(listSelectColorButton);
		
		setTabedPane.add(basePanel,"��������");
		
		
		//�������
		lrcPanel = new JPanel();
		lrcPanel.setLayout(null);
		//lrcPanel.setOpaque(false);
		
		//===========================================
		//������
		JLabel fontLabel = new JLabel("���ø����������");
		fontLabel.setFont(new Font("����",Font.BOLD,12));
		fontLabel.setBounds(30, 20, 150, 15);
		lrcPanel.add(fontLabel);
		
		//��ȡ�����ļ�
		String lineFontName = MusicPlayerConfig.getInstance().getPopString("lineFontName");
		String lineFontSize = MusicPlayerConfig.getInstance().getPopString("lineFontSize");
		String fontText = "��ǰ���壺���壬�ֺţ�13";
		if(null!=lineFontName && !"".equals(lineFontName) && null!=lineFontSize && !"".equals(lineFontSize)){
				fontText = "��ǰ���壺"+lineFontName+"���ֺţ�"+lineFontSize;
		}
		
		lineFontStateLabel = new JLabel(fontText);
		lineFontStateLabel.setBounds(30, 45,350, 15);
		lrcPanel.add(lineFontStateLabel);
		
		lineFontButton = new JButton("ѡ������");
		lineFontButton.setBounds(30, 70, 130, 25);
		lrcPanel.add(lineFontButton);
		
		//===========================================
		
		//�������
		fontLabel = new JLabel("���ø������");
		fontLabel.setFont(new Font("����",Font.BOLD,12));
		fontLabel.setBounds(30, 110, 150, 15);
		lrcPanel.add(fontLabel);
		
		//��ȡ�����ļ�
		String lrcFontName = MusicPlayerConfig.getInstance().getPopString("lrcFontName");
		String lrcFontSize = MusicPlayerConfig.getInstance().getPopString("lrcFontSize");
		String lrcfontText = "��ǰ���壺���壬�ֺţ�13";
		if(null!=lrcFontName && !"".equals(lrcFontName) && null!=lrcFontSize && !"".equals(lrcFontSize)){
			lrcfontText = "��ǰ���壺"+lrcFontName+"���ֺţ�"+lrcFontSize;
		}
		
		lrcFontStateLabel = new JLabel(lrcfontText);
		lrcFontStateLabel.setBounds(30, 135,350, 15);
		lrcPanel.add(lrcFontStateLabel);
		
		lrcFontButton = new JButton("ѡ������");
		lrcFontButton.setBounds(30, 160, 130, 25);
		lrcPanel.add(lrcFontButton);
		
		
		//===========================================
		
		//����ɫ
		fontLabel = new JLabel("���ø��������ɫ");
		fontLabel.setFont(new Font("����",Font.BOLD,12));
		fontLabel.setBounds(30, 200, 150, 15);
		lrcPanel.add(fontLabel);
		
		//��ȡ�����ļ�
		String lineFontColorText = "��ǰ���������ɫ��10,130,220";
		String lineColor = MusicPlayerConfig.getInstance().getPopString("lineColor");
		if(null!=lineColor && !"".equals(lineColor)){
			lineFontColorText="��ǰ���������ɫ��"+lineColor;
		}
				
		
		lineFontColorState =  new JLabel(lineFontColorText);
		lineFontColorState.setBounds(30, 225, 350, 15);
		lrcPanel.add(lineFontColorState);
		
		lineFontColorButton = new JButton("ѡ����ɫ");
		lineFontColorButton.setBounds(30, 250, 130, 25);
		lrcPanel.add(lineFontColorButton);
		
		//===========================================
		//�����ɫ
		fontLabel = new JLabel("���ø����ɫ");
		fontLabel.setFont(new Font("����",Font.BOLD,12));
		fontLabel.setBounds(30, 290, 150, 15);
		lrcPanel.add(fontLabel);
		
		//��ȡ�����ļ�
		String lrcFontColorText = "��ǰ�����ɫ��10,130,220";
		String lrcColor = MusicPlayerConfig.getInstance().getPopString("generalColor");
		if(null!=lrcColor && !"".equals(lrcColor)){
			lrcFontColorText="��ǰ�����ɫ��"+lrcColor;
		}
				
		
		lrcFontColorState =  new JLabel(lrcFontColorText);
		lrcFontColorState.setBounds(30, 315, 350, 15);
		lrcPanel.add(lrcFontColorState);
		
		lrcFontColorButton = new JButton("ѡ����ɫ");
		lrcFontColorButton.setBounds(30, 340, 130, 25);
		lrcPanel.add(lrcFontColorButton);
		
		
		//===========================================
		
		//���ͻ����ɫ
		fontLabel = new JLabel("���ø��ͻ����ɫ");
		fontLabel.setFont(new Font("����",Font.BOLD,12));
		fontLabel.setBounds(30, 380, 150, 15);
		lrcPanel.add(fontLabel);
		
		//��ȡ�����ļ�
		String lrcMarkFontColorText = "��ǰ���ͻ����ɫ��10,130,220";
		String markColor = MusicPlayerConfig.getInstance().getPopString("markColor");
		if(null!=markColor && !"".equals(markColor)){
			lrcMarkFontColorText="��ǰ���ͻ����ɫ��"+markColor;
		}
				
		
		lrcMarkFontColorState =  new JLabel(lrcMarkFontColorText);
		lrcMarkFontColorState.setBounds(30, 405, 350, 15);
		lrcPanel.add(lrcMarkFontColorState);
		
		lrcMarkFontColorButton = new JButton("ѡ����ɫ");
		lrcMarkFontColorButton.setBounds(30, 430, 130, 25);
		lrcPanel.add(lrcMarkFontColorButton);
		
		setTabedPane.add(lrcPanel,"�������");
		
		
		//========================================
		
		//��ʽ����
		stylePanel = new JPanel();
		stylePanel.setLayout(null);
		
		JLabel styleLabel = new JLabel("���ò�������ʽ");
		styleLabel.setFont(new Font("����",Font.BOLD,12));
		styleLabel.setBounds(30, 20, 150, 15);
		stylePanel.add(styleLabel);
		
		JButton[] jbuttons = new JButton[10];
		StyleConfig styleConfig = StyleConfig.getInstance();
		List<Properties> listPop = styleConfig.getListPop();
		
		for(int i=0; i<listPop.size(); i++){
			final Properties pop = listPop.get(i);
			String title = pop.getProperty("title");
			
			if(null==title || "".equals(title)){
				title = "��ʽ"+i;
			}
			
			jbuttons[i] = new JButton(title);
			jbuttons[i].setBounds(30, (43*(i+1)), 130, 25);
			jbuttons[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					//������ʾ
					if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
						setStyle(pop);
					}
				}
			});
			
			stylePanel.add(jbuttons[i]);
			
		}
		
		setTabedPane.add(stylePanel,"��ʽ����");
		
		
		this.getContentPane().add(setTabedPane);
		this.setLayout(null);
		this.setResizable(false);
		this.setTitle("����������");

		// ���ڴ�С
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		int width = toolKit.getScreenSize().width;
		int height = toolKit.getScreenSize().height;

		this.setBounds((width - 340) / 2, (height - 465) / 2, 340, 530);
		this.setVisible(false);
		
		
		event();
	}
	
	
	
	
	
	/**
	 * �¼�
	 */
	private void event(){
		
		//����ظ�����
		repeatButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//������ʾ
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					isRepeatStateLabel.setText("��ǰ״̬������ظ�����");
					EastPanel.setIsRepeat(MusicConstant.REPEAT);
					//��������
					MusicPlayerConfig.getInstance().setPopString("isRepeat", String.valueOf(MusicConstant.REPEAT));
				}
			}
		});
		
		//����ظ�����
		noRepeatButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//������ʾ
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					isRepeatStateLabel.setText("��ǰ״̬��������ظ�����");
					EastPanel.setIsRepeat(MusicConstant.NO_REPEAT);
					//��������
					MusicPlayerConfig.getInstance().setPopString("isRepeat", String.valueOf(MusicConstant.NO_REPEAT));
				}
			}
		});
		
		
		//ѡ��������
		lineFontButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//������ʾ
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					LineFontChooser.getInstance().setVisible(true);
				}
			}
		});
		
		//�������
		lrcFontButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//������ʾ
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					LrcFontChooser.getInstance().setVisible(true);
				}
			}
		});
		
		//����ɫ
		lineFontColorButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//������ʾ
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					Color color = chooseColor(lineFontColorButton);
					if(null!=color){
					 String strColor = color.getRed()+","+color.getGreen()+","+color.getBlue();
					 lineFontColorState.setText("��ǰ���������ɫ��"+strColor);
					 //��������ɫ
					 LRCLabel.getInstance().setLineColor(color);
					 //��������
					 MusicPlayerConfig.getInstance().setPopString("lineColor", strColor);
					}
				}
			}
		});
		
		//�����ɫ
		lrcFontColorButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//������ʾ
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					Color color = chooseColor(lrcFontColorButton);
					if(null!=color){
					 String strColor = color.getRed()+","+color.getGreen()+","+color.getBlue();
					 lrcFontColorState.setText("��ǰ�����ɫ��"+strColor);
					 //���ø����ɫ
					 LRCLabel.getInstance().setGeneralColor(color);
					 //��������
					 MusicPlayerConfig.getInstance().setPopString("generalColor", strColor);
					}
				}
			}
		});
		
		//���ͻ����ɫ
		lrcMarkFontColorButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//������ʾ
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					Color color = chooseColor(lrcMarkFontColorButton);
					if(null!=color){
					 String strColor = color.getRed()+","+color.getGreen()+","+color.getBlue();
					 lrcMarkFontColorState.setText("��ǰ���ͻ����ɫ��"+strColor);
					 //���ø����ɫ
					 LRCLabel.getInstance().setMarkColor(color);
					 //��������
					 MusicPlayerConfig.getInstance().setPopString("markColor", strColor);
					}
				}
			}
		});
		
		
		//�����б�ѡ����ɫ
		listSelectColorButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//������ʾ
				if(e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==1){
					Color color = chooseColor(listSelectColorButton);
					if(null!=color){
					 String strColor = color.getRed()+","+color.getGreen()+","+color.getBlue();
					 listSelectColorState.setText("��ǰ�����б�ѡ����ɫ��"+strColor);
					 //����ѡ����ɫ
					 MusicInfoCellRenderer.setMusicListSelectbgColor(color);
					 EastPanel.getInstance().getMusicList().updateUI();
					 //��������
					 MusicPlayerConfig.getInstance().setPopString("musicListSelectbgColor", strColor);
					}
				}
			}
		});
	}
	
	/**
	 * ������ʽ
	 * @param styleConfig
	 */
	private void setStyle(Properties pop){
		
		//Ƥ��
		String skin = pop.getProperty("skin");
		if(null!=skin && !"".equals(skin)){
			ImageIcon imgIcon = new ImageIcon(skin);
			MusicFrame.setMusicFrameBackgroundImg(imgIcon);
		}
		
		//�б�ѡ����ɫ
		String musicListSelectbgColor = pop.getProperty("musicListSelectbgColor");
		if(null!=musicListSelectbgColor && !"".equals(musicListSelectbgColor)){
			 String[] str = musicListSelectbgColor.split(",");
			 Color color = new Color(Integer.valueOf(str[0]),Integer.valueOf(str[1]),Integer.valueOf(str[2]));
			 listSelectColorState.setText("��ǰ�����б�ѡ����ɫ��"+musicListSelectbgColor);
			 //����ѡ����ɫ
			 MusicInfoCellRenderer.setMusicListSelectbgColor(color);
			 EastPanel.getInstance().getMusicList().updateUI();
			 //��������
			 MusicPlayerConfig.getInstance().setPopString("musicListSelectbgColor", musicListSelectbgColor);
		}
		
		//�����ɫ
		String generalColor = pop.getProperty("generalColor");
		if(null!=generalColor && !"".equals(generalColor)){
			String[] str = generalColor.split(",");
			Color color = new Color(Integer.valueOf(str[0]),Integer.valueOf(str[1]),Integer.valueOf(str[2]));
			lrcFontColorState.setText("��ǰ�����ɫ��"+generalColor);
			//���ø����ɫ
			LRCLabel.getInstance().setGeneralColor(color);
			//��������
			MusicPlayerConfig.getInstance().setPopString("generalColor", generalColor);
		}
		
		//���ͻ����ɫ
		String markColor = pop.getProperty("markColor");
		if(null!=markColor && !"".equals(markColor)){
			String[] str = markColor.split(",");
		    Color color = new Color(Integer.valueOf(str[0]),Integer.valueOf(str[1]),Integer.valueOf(str[2]));
		    lrcMarkFontColorState.setText("��ǰ���ͻ����ɫ��"+markColor);
		    //���ø����ɫ
			LRCLabel.getInstance().setMarkColor(color);
			//��������
			MusicPlayerConfig.getInstance().setPopString("markColor", markColor);
		}
		
		//�������ɫ
		String lineColor = pop.getProperty("lineColor");
		if(null!=lineColor && !"".equals(lineColor)){
			String[] str = lineColor.split(",");
			Color color = new Color(Integer.valueOf(str[0]),Integer.valueOf(str[1]),Integer.valueOf(str[2]));
			 lineFontColorState.setText("��ǰ���������ɫ��"+lineColor);
			//��������ɫ
			LRCLabel.getInstance().setLineColor(color);
			//��������
			MusicPlayerConfig.getInstance().setPopString("lineColor", lineColor);
		}
		
	}
	
	
	/**
	 * ����������״̬
	 * @param str
	 */
	public void setLineFontStateLabel(String str,String str2){
		this.lineFontStateLabel.setText("��ǰ���壺"+str+"���ֺţ�"+str2);
	}
	
	/**
	 * ���ø������״̬
	 * @param str
	 */
	public void setLrcFontStateLabel(String str,String str2){
		this.lrcFontStateLabel.setText("��ǰ���壺"+str+"���ֺţ�"+str2);
	}
	
	
	/**
	 * ��ɫѡ����
	 * @param comp
	 * @return
	 */
	private Color chooseColor(Component comp){
		   Color rsltColor =JColorChooser.showDialog(this,
		     "��ɫѡ��",
		     comp.getBackground());
		   return rsltColor;
	}
	
	
	/**
	 * ���ʵ�� 
	 * @return
	 */
	public static MusicSetFrame getInstance(){
		if(null==musicSetFrame){
			System.out.println("���ֲ��������ô��ڳ�ʼ������");
			return null;
		}
		return musicSetFrame;
	}
	

}
