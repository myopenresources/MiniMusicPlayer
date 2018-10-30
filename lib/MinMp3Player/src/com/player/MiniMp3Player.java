package com.player;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.List;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.ScrollPane;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.MediaLocator;
import javax.media.Time;
import javax.media.bean.playerbean.MediaPlayer;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.clock.Clock;
import com.constant.MniMp3Constant;

public class MiniMp3Player extends JFrame
{
	private static final long serialVersionUID = 1L;

	// 背景图片框
	private JLabel backImage;

	private String imagePath = "images/backImage.png";

	// 背景图片
	private ImageIcon background = new ImageIcon("images/backImage.png");

	// 面板
	private JPanel backPanel;

	// 北、中、南、东面板
	private JPanel northPanel, centerPanel, southPanel, eastPanel;

	// 中面板里面的面板
	private JPanel centerNorthPanel, centerCenterPanel, centerSouthPanel;

	// 标题
	private JLabel title;

	// 开始、上一曲、下一曲、快进、快退、模式、添加、声音、音量
	private JLabel mediaPlay, mediaPrev, mediaNext, mediaForward, mediaBack,
			mode, mediaAdd, mediaSound, mediaVolume;

	// 标题、按钮图片
	private ImageIcon systemIcon, title1, title2, play1, play2, stop1, stop2,
			prev1, prev2, next1, next2, forward1, forward2, back1, back2,
			mode1_1, mode1_2, mode2_1, mode2_2, mode3_1, mode3_2, mode4_1,
			mode4_2, add1, add2, sound1, sound2, noSound1, noSound2, volume1_1,
			volume1_2, volume2_1, volume2_2, volume3_1, volume3_2, volume4_1,
			volume4_2, volume5_1, volume5_2;

	// 开始、暂停标记
	private boolean playSign = MniMp3Constant.STOP;

	// 播放模式标记
	private int modeSign = MniMp3Constant.MODE1;

	// 是否静音标记
	private boolean soundSign = MniMp3Constant.SOUND;

	// 音量标记
	private int volumeSign = MniMp3Constant.VOLUME3;

	private String volumeSize = "3";

	private double nextSize = 0.5;

	// 播放列表指针
	private int musicIndex = 0;

	// 音乐播放列表
	private List musicList;

	// 存放音乐
	ArrayList<String> tempList;

	// 音乐路径
	private ArrayList<String> pathList;

	// 音乐播放列表中的滚动条
	private ScrollPane scroll;

	// 音乐总数
	private JLabel musicCount;

	// 显示当前播放的音乐
	private JLabel musicMessage;

	// 文件选择器
	private JFileChooser musicSelect;

	// 文件过滤器
	private FileNameExtensionFilter musicFilter;

	// 播放对象
	private MediaPlayer player = null;

	// 定位器
	private MediaLocator mediaLocator;

	// 存放临时随机数
	private int tempRandom = -1;

	// 弹出菜单
	private JPopupMenu popupMenu;

	// 弹出菜项
	private JMenuItem deleteItem, removeItem;

	// 菜单条
	private JMenuBar menuBar;

	// 菜单
	private JMenu skinMenu, next, clockMenu, helpMenu;

	// 菜单项
	private JMenuItem defult, skin1, skin2, skin3, skin4, skin5, skin6, next5,
			next10, next15, next20, next25, next30, clock, stateItem,
			aboutItem;

	// 托盘图标
	private TrayIcon trayIcon;

	// 系统托盘
	private SystemTray systemTray;

	// 最小化后的菜单
	private PopupMenu systemMenu;

	private MenuItem systemOption, systemExit;

	public MiniMp3Player() throws AWTException
	{
		systemIcon = new ImageIcon("images/systemIcon.png");
		backImage = new JLabel(background);
		backImage.setBounds(0, 0, background.getIconWidth(),
				background.getIconHeight());
		backPanel = (JPanel) this.getContentPane();
		backPanel.setOpaque(false);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(backImage, new Integer(Integer.MIN_VALUE));
		backPanel.setLayout(new BorderLayout());

		this.setTitle("Mini Mp3 Player");
		this.init();
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		int width = toolKit.getScreenSize().width;
		int height = toolKit.getScreenSize().height;
		this.setBounds((width - 320) / 2, (height - 400) / 2, 365, 350);
		this.setIconImage(systemIcon.getImage());
		this.setResizable(false);

		systemMenu = new PopupMenu();
		systemOption = new MenuItem("打开");
		systemExit = new MenuItem("退出");
		systemMenu.add(systemOption);
		systemMenu.add(systemExit);

		trayIcon = new TrayIcon(systemIcon.getImage(),
				"操作：双击打开,右击打开、退出。\n版本：MiniMp3Player1.7 Beta\n作者：陈江海",
				systemMenu);
		systemTray = SystemTray.getSystemTray();
		systemTray.add(trayIcon);

		this.event();
		this.setVisible(true);

	}

	// 初始化界面方法
	private void init()
	{
		title1 = new ImageIcon("images/title1.png");
		title2 = new ImageIcon("images/title2.png");
		play1 = new ImageIcon("images/play1.png");
		play2 = new ImageIcon("images/play2.png");
		stop1 = new ImageIcon("images/stop1.png");
		stop2 = new ImageIcon("images/stop2.png");
		prev1 = new ImageIcon("images/prev1.png");
		prev2 = new ImageIcon("images/prev2.png");
		next1 = new ImageIcon("images/next1.png");
		next2 = new ImageIcon("images/next2.png");
		back1 = new ImageIcon("images/backrewind1.png");
		back2 = new ImageIcon("images/backrewind2.png");
		forward1 = new ImageIcon("images/forwardrewind1.png");
		forward2 = new ImageIcon("images/forwardrewind2.png");
		mode1_1 = new ImageIcon("images/mode1_1.png");
		mode1_2 = new ImageIcon("images/mode1_2.png");
		mode2_1 = new ImageIcon("images/mode2_1.png");
		mode2_2 = new ImageIcon("images/mode2_2.png");
		mode3_1 = new ImageIcon("images/mode3_1.png");
		mode3_2 = new ImageIcon("images/mode3_2.png");
		mode4_1 = new ImageIcon("images/mode4_1.png");
		mode4_2 = new ImageIcon("images/mode4_2.png");
		add1 = new ImageIcon("images/add1.png");
		add2 = new ImageIcon("images/add2.png");
		sound1 = new ImageIcon("images/sound1.png");
		sound2 = new ImageIcon("images/sound2.png");
		noSound1 = new ImageIcon("images/nosound1.png");
		noSound2 = new ImageIcon("images/nosound2.png");
		volume1_1 = new ImageIcon("images/volume1_1.png");
		volume1_2 = new ImageIcon("images/volume1_2.png");
		volume2_1 = new ImageIcon("images/volume2_1.png");
		volume2_2 = new ImageIcon("images/volume2_2.png");
		volume3_1 = new ImageIcon("images/volume3_1.png");
		volume3_2 = new ImageIcon("images/volume3_2.png");
		volume4_1 = new ImageIcon("images/volume4_1.png");
		volume4_2 = new ImageIcon("images/volume4_2.png");
		volume5_1 = new ImageIcon("images/volume5_1.png");
		volume5_2 = new ImageIcon("images/volume5_2.png");

		menuBar = new JMenuBar();
		skinMenu = new JMenu("皮肤");
		next = new JMenu("进度");
		clockMenu = new JMenu("定时");
		helpMenu = new JMenu("帮助");
		defult = new JMenuItem("默认");
		skin1 = new JMenuItem("皮肤1");
		skin2 = new JMenuItem("皮肤2");
		skin3 = new JMenuItem("皮肤3");
		skin4 = new JMenuItem("皮肤4");
		skin5 = new JMenuItem("皮肤5");
		skin6 = new JMenuItem("皮肤6");
		next5 = new JMenuItem("快进5");
		next10 = new JMenuItem("快进10");
		next15 = new JMenuItem("快进15");
		next20 = new JMenuItem("快进20");
		next25 = new JMenuItem("快进25");
		next30 = new JMenuItem("快进30");
		clock = new JMenuItem("模式");
		stateItem = new JMenuItem("说明");
		aboutItem = new JMenuItem("关于");

		skinMenu.add(defult);
		skinMenu.add(skin1);
		skinMenu.add(skin2);
		skinMenu.add(skin3);
		skinMenu.add(skin4);
		skinMenu.add(skin5);
		skinMenu.add(skin6);
		next.add(next5);
		next.add(next10);
		next.add(next15);
		next.add(next20);
		next.add(next25);
		next.add(next30);
        clockMenu.add(clock);
		helpMenu.add(stateItem);
		helpMenu.add(aboutItem);
		menuBar.add(skinMenu);
		menuBar.add(next);
		menuBar.add(clockMenu);
		menuBar.add(helpMenu);

		menuBar.setOpaque(false);

		this.setJMenuBar(menuBar);

		northPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		eastPanel = new JPanel();

		centerPanel.setLayout(new BorderLayout());

		centerNorthPanel = new JPanel();
		centerCenterPanel = new JPanel();
		centerSouthPanel = new JPanel();

		northPanel.setOpaque(false);
		centerPanel.setOpaque(false);
		southPanel.setOpaque(false);
		eastPanel.setOpaque(false);
		centerNorthPanel.setOpaque(false);
		centerCenterPanel.setOpaque(false);
		centerSouthPanel.setOpaque(false);

		title = new JLabel(title1);
		mediaPlay = new JLabel(stop1);
		mediaPrev = new JLabel(prev1);
		mediaNext = new JLabel(next1);
		mediaBack = new JLabel(back1);
		mediaForward = new JLabel(forward1);
		mode = new JLabel(mode1_1);
		mediaAdd = new JLabel(add1);
		mediaSound = new JLabel(sound1);
		mediaVolume = new JLabel(volume3_1);

		musicCount = new JLabel();
		musicCount.setVisible(false);

		deleteItem = new JMenuItem("删除");
		removeItem = new JMenuItem("清空");

		popupMenu = new JPopupMenu();
		popupMenu.add(deleteItem);
		popupMenu.add(removeItem);

		musicList = new List(14);

		pathList = new ArrayList<String>();
		tempList = new ArrayList<String>();

		input();
		initProperties();
		musicFilter = new FileNameExtensionFilter("音乐文件(*.mp3)", "mp3");

		musicSelect = new JFileChooser();
		musicSelect.setDialogTitle("请选择音乐文件！");
		musicSelect.setFileFilter(musicFilter);
		musicSelect.setMultiSelectionEnabled(true);

		scroll = new ScrollPane(ScrollPane.SCROLLBARS_NEVER);
		scroll.add(musicList);
		scroll.setSize(200, 210);

		musicMessage = new JLabel();

		mediaPlay.setEnabled(false);
		mediaPrev.setEnabled(false);
		mediaNext.setEnabled(false);
		mediaBack.setEnabled(false);
		mediaForward.setEnabled(false);
		mediaSound.setEnabled(false);
		mediaVolume.setEnabled(false);

		centerNorthPanel.add(mediaPlay);
		centerNorthPanel.add(mediaPrev);
		centerNorthPanel.add(mediaNext);
		centerNorthPanel.add(mediaBack);
		centerNorthPanel.add(mediaForward);
		
		centerCenterPanel.add(mediaSound);
		centerCenterPanel.add(mediaVolume);
		centerCenterPanel.add(mode);
		
		centerCenterPanel.add(mediaAdd);
		centerSouthPanel.add(musicCount);

		northPanel.add(title);
		centerPanel.add("North", centerNorthPanel);
		centerPanel.add("Center", centerCenterPanel);
		centerPanel.add("South", centerSouthPanel);
		eastPanel.add(musicList);
		southPanel.add(musicMessage);

		this.add("North", northPanel);
		this.add("Center", centerPanel);
		this.add("South", southPanel);
		this.add("East", eastPanel);

	}

	// 初始化配置文件
	private void initProperties()
	{
		Properties prop = new Properties();
		/*
		 * InputStream in = Object.class
		 * .getResourceAsStream("mp3.config.properties");
		 */

		try
		{
			InputStream in = new FileInputStream("mp3.config.properties");
			prop.load(in);
			backImage.setIcon(new ImageIcon(prop.getProperty("bgUrl").trim()));
			String temp = prop.getProperty("modeSign").trim();
			volumeSize = prop.getProperty("volumeSize");
			nextSize = Double.valueOf(prop.getProperty("nextSize"));

			int tempMode = MniMp3Constant.MODE1;

			if (temp.equals("1"))
			{
				tempMode = MniMp3Constant.MODE1;
				mode.setIcon(mode1_1);
			}
			else if (temp.equals("2"))
			{
				tempMode = MniMp3Constant.MODE2;
				mode.setIcon(mode2_1);
			}
			else if (temp.equals("3"))
			{
				tempMode = MniMp3Constant.MODE3;

				mode.setIcon(mode3_1);

			}
			else if (temp.equals("4"))
			{
				tempMode = MniMp3Constant.MODE4;
				mode.setIcon(mode4_1);
			}
			modeSign = tempMode;

			if (volumeSize.equals("1"))
			{

				mediaVolume.setIcon(volume1_1);
				volumeSign = MniMp3Constant.VOLUME1;
			}
			else if (volumeSize.equals("2"))
			{
				mediaVolume.setIcon(volume2_1);
				volumeSign = MniMp3Constant.VOLUME2;
			}
			else if (volumeSize.equals("3"))
			{
				mediaVolume.setIcon(volume3_1);
				volumeSign = MniMp3Constant.VOLUME3;
			}
			else if (volumeSize.equals("4"))
			{
				mediaVolume.setIcon(volume4_1);
				volumeSign = MniMp3Constant.VOLUME4;
			}
			else if (volumeSize.equals("5"))
			{
				mediaVolume.setIcon(volume5_1);
				volumeSign = MniMp3Constant.VOLUME5;
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// 事件方法
	public void event()
	{
		// 鼠标移到标题
		title.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				title.setIcon(title2);
			}
		});

		// 鼠标离开标题
		title.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent e)
			{
				title.setIcon(title1);
			}
		});

		// 播放、暂停按钮按下事件
		mediaPlay.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (mediaPlay.isEnabled())
				{
					if (playSign == MniMp3Constant.PLAY)
					{
						player.start();
						playSign = MniMp3Constant.STOP;
						mediaPlay.setIcon(stop1);
					}
					else if (playSign == MniMp3Constant.STOP)
					{
						player.stop();
						playSign = MniMp3Constant.PLAY;
						mediaPlay.setIcon(play1);
					}
				}

			}

		});

		// 鼠标移到播放、暂停按钮改变按钮事件
		mediaPlay.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				if (playSign == MniMp3Constant.PLAY)
				{
					mediaPlay.setIcon(play2);
				}
				else if (playSign == MniMp3Constant.STOP)
				{
					mediaPlay.setIcon(stop2);
				}
			}
		});

		// 鼠标离开播放暂停按钮改变按钮事件
		mediaPlay.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent e)
			{
				if (playSign == MniMp3Constant.PLAY)
				{
					mediaPlay.setIcon(play1);
				}
				else if (playSign == MniMp3Constant.STOP)
				{
					mediaPlay.setIcon(stop1);
				}
			}
		});

		// 上一曲按钮按下事件
		mediaPrev.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (mediaPrev.isEnabled())
				{
					prev();
				}
			}
		});

		// 鼠标移到上一曲按钮改变按钮事件
		mediaPrev.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				if (mediaPrev.isEnabled())
					mediaPrev.setIcon(prev2);
			}
		});

		// 鼠标离开上一曲按钮改变按钮事件
		mediaPrev.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent e)
			{
				if (mediaPrev.isEnabled())
					mediaPrev.setIcon(prev1);
			}
		});

		// 下一曲按钮按下事件
		mediaNext.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				if (mediaNext.isEnabled())
				{
					next();
				}

			}
		});

		// 鼠标移到下一曲按钮改变按钮事件
		mediaNext.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				if (mediaNext.isEnabled())
					mediaNext.setIcon(next2);
			}
		});

		// 鼠标离开下一曲按钮改变按钮事件
		mediaNext.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent e)
			{
				if (mediaNext.isEnabled())
					mediaNext.setIcon(next1);
			}
		});

		// 按下快进按钮事件
		mediaForward.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (mediaForward.isEnabled())
				{
					if (player != null)
					{
						boolean sign = false;
						if (player.getMediaTime().getSeconds() + nextSize < player
								.getDuration().getSeconds())
						{
							sign = true;
						}
						if (sign)
						{
							player.setMediaTime(new Time(player.getMediaTime()
									.getSeconds() + nextSize));
						}

					}
				}
			}
		});

		// 鼠标移到快进按钮事件
		mediaForward.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				if (mediaForward.isEnabled())
				{
					mediaForward.setIcon(forward2);
				}
			}
		});

		// 鼠标离开快进按钮事件
		mediaForward.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent e)
			{
				if (mediaForward.isEnabled())
				{
					mediaForward.setIcon(forward1);
				}
			}
		});

		// 按下快退按钮事件
		mediaBack.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (mediaForward.isEnabled())
				{
					if (player != null)
					{
						boolean sign = false;
						if (player.getMediaTime().getSeconds() > nextSize)
						{
							sign = true;
						}

						if (sign)
						{
							player.setMediaTime(new Time(player.getMediaTime()
									.getSeconds() - nextSize));
						}
					}
				}
			}
		});

		// 鼠标移到快退按钮事件
		mediaBack.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				if (mediaBack.isEnabled())
				{
					mediaBack.setIcon(back2);
				}
			}
		});

		// 鼠标离开快退按钮事件
		mediaBack.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent e)
			{
				if (mediaBack.isEnabled())
				{
					mediaBack.setIcon(back1);
				}
			}
		});

		// 播放模式按钮事件
		mode.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				switch (modeSign)
				{
				case MniMp3Constant.MODE1:
					mode.setIcon(mode2_1);
					modeSign = MniMp3Constant.MODE2;
					break;
				case MniMp3Constant.MODE2:
					mode.setIcon(mode3_1);
					modeSign = MniMp3Constant.MODE3;
					break;
				case MniMp3Constant.MODE3:
					mode.setIcon(mode4_1);
					modeSign = MniMp3Constant.MODE4;
					break;
				case MniMp3Constant.MODE4:
					mode.setIcon(mode1_1);
					modeSign = MniMp3Constant.MODE1;
					break;
				}
			}
		});

		// 鼠标移到播放模式
		mode.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				switch (modeSign)
				{
				case MniMp3Constant.MODE1:
					mode.setIcon(mode1_2);
					break;
				case MniMp3Constant.MODE2:
					mode.setIcon(mode2_2);
					break;
				case MniMp3Constant.MODE3:
					mode.setIcon(mode3_2);
					break;
				case MniMp3Constant.MODE4:
					mode.setIcon(mode4_2);
					break;
				}
			}
		});

		// 鼠标离开播放模式
		mode.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent e)
			{
				switch (modeSign)
				{
				case MniMp3Constant.MODE1:
					mode.setIcon(mode1_1);
					break;
				case MniMp3Constant.MODE2:
					mode.setIcon(mode2_1);
					break;
				case MniMp3Constant.MODE3:
					mode.setIcon(mode3_1);
					break;
				case MniMp3Constant.MODE4:
					mode.setIcon(mode4_1);
					break;
				}
			}
		});

		// 添加按钮按下事件
		mediaAdd.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				boolean tempSign = false;
				String tempStr = null;
				String subStr = null;
				int result = musicSelect.showOpenDialog(null);
				boolean isadd = false;
				if (result == JFileChooser.APPROVE_OPTION)
				{
					File[] musicFile = musicSelect.getSelectedFiles();
					if (tempList.size() == 0)
					{
						for (int i = 0; i < musicFile.length; i++)
						{
							tempList.add(musicFile[i].toString());

							File file = new File(musicFile[i].toString());
							tempStr = new String(file.getName().trim());
							if (tempStr.length() >= 15)
							{
								subStr = new String(tempStr.substring(0, 15));
							}
							else
							{
								subStr = tempStr;
							}
							if (i < 9)
							{
								musicList.add("00" + (i + 1) + ".  " + subStr);
							}
							else if (i < 99)
							{
								musicList.add("0" + (i + 1) + ".  " + subStr);
							}
							else if (i < 999)
							{
								musicList.add((i + 1) + ".  " + subStr);
							}
							pathList.add(file.getPath());
							isadd = true;

						}
					}
					else
					{

						for (int i = 0; i < musicFile.length; i++)
						{
							for (int j = 0; j < tempList.size(); j++)
							{
								if (tempList.get(j).equals(
										musicFile[i].toString()))
								{
									tempSign = false;
									break;
								}
								tempSign = true;

							}
							if (tempSign)
							{
								tempList.add(musicFile[i].toString());
								File file = new File(musicFile[i].toString());
								tempStr = new String(file.getName().trim());
								if (tempStr.length() >= 15)
								{
									subStr = new String(tempStr
											.substring(0, 15));
								}
								else
								{
									subStr = tempStr;
								}
								int t = 0;
								if (isadd)
								{
									t = musicList.getItemCount() + 1;
								}
								else
								{
									t = musicList.getItemCount();
								}

								System.out.println(t++);

								if (t < 10)
								{
									musicList
											.add("00" + (t++) + ".  " + subStr);
								}
								else if (t < 100)
								{
									musicList.add("0" + (t++) + ".  " + subStr);
								}
								else if (t < 1000)
								{
									musicList.add((t++) + ".  " + subStr);
								}

								pathList.add(file.getPath());

							}
						}
					}
				}
				musicCount.setText("当前音乐 " + (musicIndex + 1) + "/"
						+ musicList.getItemCount());
			}
		});

		// 鼠标移到添加按钮改变按钮事件
		mediaAdd.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				mediaAdd.setIcon(add2);
			}
		});

		// 鼠标离开添加按钮改变按钮事件
		mediaAdd.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent e)
			{
				mediaAdd.setIcon(add1);
			}
		});

		// 按下声音按钮事件
		mediaSound.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (mediaSound.isEnabled())
				{
					if (soundSign == MniMp3Constant.SOUND)
					{
						if (player != null)
						{
							player.setVolumeLevel("0");
							mediaVolume.setEnabled(false);

						}
						mediaSound.setIcon(noSound1);
						soundSign = MniMp3Constant.NO_SOUND;

					}
					else if (soundSign == MniMp3Constant.NO_SOUND)
					{
						if (player != null)
						{
							if (volumeSign == MniMp3Constant.VOLUME1)
							{
								volumeSize = "1";
							}
							else if (volumeSign == MniMp3Constant.VOLUME2)
							{
								volumeSize = "2";
							}
							else if (volumeSign == MniMp3Constant.VOLUME3)
							{
								volumeSize = "3";
							}
							else if (volumeSign == MniMp3Constant.VOLUME4)
							{
								volumeSize = "4";
							}
							else if (volumeSign == MniMp3Constant.VOLUME5)
							{
								volumeSize = "5";
							}
							player.setVolumeLevel(volumeSize);
							mediaVolume.setEnabled(true);
						}
						mediaSound.setIcon(sound1);
						soundSign = MniMp3Constant.SOUND;

					}
				}
			}
		});

		// 鼠标移到声音按钮事件
		mediaSound.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				if (soundSign == MniMp3Constant.SOUND)
				{
					mediaSound.setIcon(sound2);
				}
				else if (soundSign == MniMp3Constant.NO_SOUND)
				{
					mediaSound.setIcon(noSound2);
				}
			}
		});

		// 鼠标离开声音按钮事件
		mediaSound.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent e)
			{
				if (soundSign == MniMp3Constant.SOUND)
				{
					mediaSound.setIcon(sound1);
				}
				else if (soundSign == MniMp3Constant.NO_SOUND)
				{
					mediaSound.setIcon(noSound1);
				}
			}
		});

		// 按下音量按钮事件
		mediaVolume.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (mediaVolume.isEnabled() && player != null)
				{
					switch (volumeSign)
					{
					case MniMp3Constant.VOLUME1:

						volumeSize = "2";

						mediaVolume.setIcon(volume2_1);
						volumeSign = MniMp3Constant.VOLUME2;
						break;
					case MniMp3Constant.VOLUME2:

						volumeSize = "3";
						mediaVolume.setIcon(volume3_1);
						volumeSign = MniMp3Constant.VOLUME3;
						break;
					case MniMp3Constant.VOLUME3:

						volumeSize = "4";

						mediaVolume.setIcon(volume4_1);
						volumeSign = MniMp3Constant.VOLUME4;
						break;
					case MniMp3Constant.VOLUME4:

						volumeSize = "5";

						mediaVolume.setIcon(volume5_1);
						volumeSign = MniMp3Constant.VOLUME5;
						break;
					case MniMp3Constant.VOLUME5:

						volumeSize = "1";

						mediaVolume.setIcon(volume1_1);
						volumeSign = MniMp3Constant.VOLUME1;
						break;
					}
					player.setVolumeLevel(volumeSize);
				}
			}
		});

		// 鼠标移到音量按钮事件
		mediaVolume.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				switch (volumeSign)
				{
				case MniMp3Constant.VOLUME1:
					mediaVolume.setIcon(volume1_2);
					break;
				case MniMp3Constant.VOLUME2:
					mediaVolume.setIcon(volume2_2);
					break;
				case MniMp3Constant.VOLUME3:
					mediaVolume.setIcon(volume3_2);
					break;
				case MniMp3Constant.VOLUME4:
					mediaVolume.setIcon(volume4_2);
					break;
				case MniMp3Constant.VOLUME5:
					mediaVolume.setIcon(volume5_2);
					break;
				}
			}
		});

		// 鼠标离开音量按钮事件
		mediaVolume.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent e)
			{
				switch (volumeSign)
				{
				case MniMp3Constant.VOLUME1:
					mediaVolume.setIcon(volume1_1);
					break;
				case MniMp3Constant.VOLUME2:
					mediaVolume.setIcon(volume2_1);
					break;
				case MniMp3Constant.VOLUME3:
					mediaVolume.setIcon(volume3_1);
					break;
				case MniMp3Constant.VOLUME4:
					mediaVolume.setIcon(volume4_1);
					break;
				case MniMp3Constant.VOLUME5:
					mediaVolume.setIcon(volume5_1);
					break;
				}

			}
		});

		// 双击播放列表事件
		musicList.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				try
				{
					if (e.getClickCount() == 2 && musicList.getItemCount() > 0)
					{
						String path = "file:///"
								+ pathList.get(musicList.getSelectedIndex());
						musicIndex = musicList.getSelectedIndex();
						mediaLocator = new MediaLocator(path.trim());

						play(mediaLocator);
						mediaPlay.setEnabled(true);
						mediaPrev.setEnabled(true);
						mediaNext.setEnabled(true);
						mediaForward.setEnabled(true);
						mediaBack.setEnabled(true);
						mediaSound.setEnabled(true);
						mediaVolume.setEnabled(true);
						if (playSign = MniMp3Constant.PLAY)
						{
							mediaPlay.setIcon(stop1);
							playSign = MniMp3Constant.STOP;
						}
						mediaSound.setIcon(sound1);
						/*
						 * if(volumeSize.equals("1")){
						 * mediaVolume.setIcon(volume1_1); }else
						 * if(volumeSize.equals("2")){
						 * mediaVolume.setIcon(volume2_1); }else
						 * if(volumeSize.equals("3")){
						 * mediaVolume.setIcon(volume3_1); }else
						 * if(volumeSize.equals("4")){
						 * mediaVolume.setIcon(volume4_1); }else
						 * if(volumeSize.equals("5")){
						 * mediaVolume.setIcon(volume5_1); }
						 */

						// mediaVolume.setIcon(volume3_1);
						soundSign = MniMp3Constant.SOUND;
						// volumeSign = MyConstant.MODE3;
						player.setVolumeLevel(volumeSize);

					}
				}
				catch (ArrayIndexOutOfBoundsException e1)
				{
					System.out.println(e1.toString());

				}
				catch (Exception e3)
				{
					JOptionPane.showMessageDialog(null, "可能音乐文件不存在了，或是其它原因！");

				}

			}

		});

		// 右击播放列表弹出弹出菜单事件
		musicList.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (e.getButton() == MouseEvent.BUTTON3)
				{
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}

			}

		});

		// 按下删除项事件
		deleteItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (musicList.getItemCount() > 0)
				{
					try
					{
						if (player != null)
						{
							player.stop();
							player.close();
							del();
						}
						else
						{
							del();
						}
					}
					catch (ArrayIndexOutOfBoundsException e2)
					{
						JOptionPane.showMessageDialog(null, "请选中要删除的音乐！");
						System.out.println(e2.toString());
					}
				}
			}

		});

		// 按下清空事件
		removeItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (musicList.getItemCount() > 0)
				{
					if (player != null)
					{
						player.stop();
						player.close();
					}
					musicIndex = 0;
					musicList.removeAll();
					tempList.clear();
					pathList.clear();
					mediaPlay.setEnabled(false);
					mediaPrev.setEnabled(false);
					mediaNext.setEnabled(false);
					mediaForward.setEnabled(false);
					mediaBack.setEnabled(false);
					mediaSound.setEnabled(false);
					mediaVolume.setEnabled(false);
					tempRandom = -1;
					playSign = MniMp3Constant.STOP;
					modeSign = MniMp3Constant.MODE1;
					soundSign = MniMp3Constant.SOUND;
					volumeSign = MniMp3Constant.VOLUME3;
					mediaPlay.setIcon(stop1);
					mode.setIcon(mode1_1);
					mediaVolume.setIcon(volume3_1);
					musicMessage.setText("");
					musicCount.setVisible(false);
				}
			}
		});

		// 菜中的说明事件
		stateItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane
						.showMessageDialog(
								null,
								"1.第1个按钮播放/暂停。\n2.第2个按钮上一曲。\n3.第3个按钮下一曲。\n4.第4个按钮快退。\n5.第5个按钮快进。\n6.第6个按钮静音和取消静音。\n7.第7个按钮音量大小。\n8.第8个按钮音乐播放模式。\n9.第9个按钮添加音乐。\n10.双击播放列表中的音乐播放。\n11.右击播放列表中的音乐删除。\n12.右击播放列表清空。\n13.按下窗口右上角的关闭最小化到托盘中。\n14.最小化托盘后双击或右击打开。\n15.最小托盘后右击退出。");
			}
		});

		// 菜中的关于事件
		aboutItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane
						.showMessageDialog(
								null,
								"本 Mp3 播放器是一款 Mni 型的，功\n能、操作简单，很多方面还待改善。\n版本：MiniMp3Player1.7 Beta\n作者：陈江海\n修改时间：2012-07-15");
			}
		});

		// 菜中的皮肤事件
		defult.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				backImage.setIcon(background);
				imagePath = "images/backImage.png";
			}

		});

		skin1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				backImage.setIcon(new ImageIcon("images/skin1.png"));
				imagePath = "images/skin1.png";
			}

		});

		skin2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				backImage.setIcon(new ImageIcon("images/skin2.png"));
				imagePath = "images/skin2.png";
			}

		});

		skin3.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				backImage.setIcon(new ImageIcon("images/skin3.png"));
				imagePath = "images/skin3.png";
			}

		});

		skin4.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				backImage.setIcon(new ImageIcon("images/skin4.png"));
				imagePath = "images/skin4.png";
			}

		});

		skin5.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				backImage.setIcon(new ImageIcon("images/skin5.png"));
				imagePath = "images/skin5.png";
			}

		});

		skin6.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				backImage.setIcon(new ImageIcon("images/skin6.png"));
				imagePath = "images/skin6.png";
			}

		});

		next5.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				nextSize = 5.0;
			}

		});

		next10.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				nextSize = 10.0;
			}

		});

		next15.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				nextSize = 15.0;
			}

		});

		next20.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				nextSize = 20.0;
			}

		});

		next25.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				nextSize = 25.0;
			}

		});

		next30.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				nextSize = 30.0;
			}

		});
		
		clock.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new Clock().event();
			}

		});
		
		
		// 窗体关闭事件
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		});

		// 双击托盘打开音乐播放器
		trayIcon.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					setExtendedState(JFrame.NORMAL);
					setVisible(true);
				}
			}
		});

		// 点击弹出菜单中的打开
		systemOption.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setExtendedState(JFrame.NORMAL);
				setVisible(true);
			}
		});

		// 点击弹出菜单中的退出
		systemExit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mediaExit();
			}
		});

	}

	// 播放方法
	private void play(MediaLocator mediaLocator)
	{
		if (player == null)
		{
			player = new MediaPlayer();

		}
		else
		{
			player.stop();
			player.close();
			player = new MediaPlayer();
		}

		if (soundSign == MniMp3Constant.SOUND)
		{
			if (player != null)
			{
				if (volumeSign == MniMp3Constant.VOLUME1)
				{
					volumeSize = "1";
				}
				else if (volumeSign == MniMp3Constant.VOLUME2)
				{
					volumeSize = "2";
				}
				else if (volumeSign == MniMp3Constant.VOLUME3)
				{
					volumeSize = "3";
				}
				else if (volumeSign == MniMp3Constant.VOLUME4)
				{
					volumeSize = "4";
				}
				else if (volumeSign == MniMp3Constant.VOLUME5)
				{
					volumeSize = "5";
				}
				player.setVolumeLevel(volumeSize);
				// System.out.println(player.getVolumeLevel());
				mediaVolume.setEnabled(true);
			}
		}
		else
		{
			player.setVolumeLevel("0");
		}
		player.setMediaLocator(mediaLocator);
		player.start();
		musicCount.setVisible(true);
		musicCount.setText("当前音乐 " + (musicIndex + 1) + "/"
				+ musicList.getItemCount());
		String musicName = player.getMediaLocation();
		if (musicName.length() >= 30)
		{
			String subStr = musicName.substring(8, 30);
			musicMessage.setText("当前播放 " + subStr);
		}
		else
		{
			String subStr = musicName.substring(8);
			musicMessage.setText("当前播放 " + subStr);
		}
		//MyPlayer.this.setTitle(musicName.substring(13));

		// 判断歌曲是否播放完
		player.addControllerListener(new ControllerListener()
		{

			public void controllerUpdate(ControllerEvent e)
			{
				if (e instanceof EndOfMediaEvent)
				{
					player.stop();
					player.close();

					if (modeSign == MniMp3Constant.MODE1)
					{
						orderPlayer();
					}
					else if (modeSign == MniMp3Constant.MODE2)
					{
						listLoop();
					}
					else if (modeSign == MniMp3Constant.MODE3)
					{
						repeatOne();
					}
					else if (modeSign == MniMp3Constant.MODE4)
					{
						if (musicList.getItemCount() > 1)
						{
							listRandom();
						}
						else
						{
							next();
						}

					}
				}
			}

		});
	}

	// 下一曲方法
	private void next()
	{
		if (modeSign == MniMp3Constant.MODE4 && musicList.getItemCount() > 1)
		{
			listRandom();
		}
		else
		{
			if (musicList.getItemCount() - 1 - musicIndex == 0)
			{
				musicIndex = 0;
				String path = "file:///" + pathList.get(musicIndex);
				mediaLocator = new MediaLocator(path.trim());
				play(mediaLocator);
			}
			else
			{
				musicIndex += 1;
				String path = "file:///" + pathList.get(musicIndex);
				mediaLocator = new MediaLocator(path.trim());
				play(mediaLocator);
			}
			mediaPlay.setEnabled(true);
		}
	}

	// 上一曲方法
	private void prev()
	{
		if (modeSign == MniMp3Constant.MODE4 && musicList.getItemCount() > 1)
		{
			listRandom();
		}
		else
		{
			if (musicIndex == 0)
			{
				musicIndex = musicList.getItemCount() - 1;
				String path = "file:///" + pathList.get(musicIndex);
				mediaLocator = new MediaLocator(path.trim());
				play(mediaLocator);
			}
			else
			{
				musicIndex -= 1;
				String path = "file:///" + pathList.get(musicIndex);
				mediaLocator = new MediaLocator(path.trim());
				play(mediaLocator);
			}
			mediaPlay.setEnabled(true);
		}
	}

	// 顺序播放
	private void orderPlayer()
	{

		if (musicIndex == musicList.getItemCount() - 1)
		{
			if (musicList.getItemCount() - musicIndex > 0)
			{
				String path = "file:///" + pathList.get(musicIndex);
				mediaLocator = new MediaLocator(path.trim());
				play(mediaLocator);

			}
			else
			{
				String path = "file:///" + pathList.get(0);
				mediaLocator = new MediaLocator(path.trim());
				play(mediaLocator);
			}
			player.stop();
			player.close();

			musicIndex = 0;
			mediaPlay.setEnabled(false);
			mediaPrev.setEnabled(false);
			mediaNext.setEnabled(false);
			mediaForward.setEnabled(false);
			mediaBack.setEnabled(false);
			mediaSound.setEnabled(false);
			mediaVolume.setEnabled(false);
			tempRandom = -1;
			playSign = MniMp3Constant.STOP;
			modeSign = MniMp3Constant.MODE1;
			soundSign = MniMp3Constant.SOUND;
			volumeSign = MniMp3Constant.VOLUME3;
			mediaPlay.setIcon(stop1);
			mode.setIcon(mode1_1);
			mediaVolume.setIcon(volume3_1);
			musicMessage.setText("");
			musicCount.setVisible(false);

		}
		else
		{
			musicIndex += 1;
			String path = "file:///" + pathList.get(musicIndex);
			mediaLocator = new MediaLocator(path.trim());
			play(mediaLocator);
		}
	}

	// 列表循环
	private void listLoop()
	{
		next();
	}

	// 单曲循环
	private void repeatOne()
	{
		String path = "file:///" + pathList.get(musicIndex);
		mediaLocator = new MediaLocator(path.trim());
		play(mediaLocator);
	}

	// 随机播放
	private void listRandom()
	{
		musicIndex = randomNum(musicList.getItemCount());
		String path = "file:///" + pathList.get(musicIndex);
		mediaLocator = new MediaLocator(path.trim());
		play(mediaLocator);
	}

	// 处理重复随机数
	private int randomNum(int num)
	{
		int randomIndex = (int) (Math.random() * num);
		if (tempRandom == randomIndex)
		{
			randomIndex = randomNum(num);

		}
		tempRandom = randomIndex;
		return randomIndex;

	}

	// 删除音乐方法
	private void del()
	{
		if (musicList.getItemCount() == 1
				&& musicList.getSelectedIndex() == musicIndex)
		{
			musicList.removeAll();
			tempList.clear();
			pathList.clear();
		}
		else if (musicList.getItemCount() > 1)
		{
			if (musicIndex == 0 && musicIndex == musicList.getSelectedIndex())
			{
				tempList.remove(musicList.getSelectedIndex());
				pathList.remove(musicList.getSelectedIndex());
				musicList.remove(musicList.getSelectedIndex());
			}
			else if (musicIndex == musicList.getSelectedIndex()
					&& musicIndex < (musicList.getItemCount() - 1))
			{
				tempList.remove(musicList.getSelectedIndex());
				pathList.remove(musicList.getSelectedIndex());
				musicList.remove(musicList.getSelectedIndex());
			}
			else if (musicIndex == musicList.getSelectedIndex()
					&& musicIndex == (musicList.getItemCount() - 1))
			{
				tempList.remove(musicList.getSelectedIndex());
				pathList.remove(musicList.getSelectedIndex());
				musicList.remove(musicList.getSelectedIndex());
				musicIndex = musicIndex - 1;
			}
			else if (musicList.getSelectedIndex() < musicIndex)
			{
				tempList.remove(musicList.getSelectedIndex());
				pathList.remove(musicList.getSelectedIndex());
				musicList.remove(musicList.getSelectedIndex());
				musicIndex = musicIndex - 1;
			}
			else if (musicList.getSelectedIndex() > musicIndex)
			{
				tempList.remove(musicList.getSelectedIndex());
				pathList.remove(musicList.getSelectedIndex());
				musicList.remove(musicList.getSelectedIndex());
			}
			musicMessage.setText("");
			mediaPlay.setIcon(stop1);
			// mediaVolume.setIcon(volume3_1);
			playSign = MniMp3Constant.STOP;
			// volumeSign = MyConstant.VOLUME3;
			mediaVolume.setEnabled(false);
			mediaSound.setEnabled(false);
			mediaPlay.setEnabled(false);
			soundSign = MniMp3Constant.SOUND;
			mediaPrev.setEnabled(false);
			mediaNext.setEnabled(false);
			mediaForward.setEnabled(false);
			mediaBack.setEnabled(false);
			tempRandom = -1;
			modeSign = MniMp3Constant.MODE1;
			mode.setIcon(mode1_1);
			musicCount.setVisible(false);
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < musicList.getItemCount(); i++)
			{
				String temp = musicList.getItem(i);
				String subTemp = temp.substring(temp.indexOf('.'));
				// System.out.println(subTemp);

				if (i < 9)
				{
					list.add("00" + (i + 1) + subTemp);
				}
				else if (i < 99)
				{
					list.add("0" + (i + 1) + subTemp);
				}
				else if (i < 999)
				{
					list.add((i + 1) + subTemp);
				}

				// list.add((i + 1) + subTemp);

			}
			// System.out.println(list);
			musicList.removeAll();
			for (String str : list)
			{
				musicList.add(str);
			}
			// musicList.add((i+1)+". "+subStr);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "请选中要删除的音乐！");
		}
	}

	// 退出音乐播放器方法
	private void mediaExit()
	{
		if (player != null)
		{
			player.stop();
			player.close();
		}
		outPut();
		outProperties();
		System.exit(0);
	}

	// 写入音乐播放列表到txt
	private void outPut()
	{
		try
		{
			FileWriter fwMusicPath = new FileWriter("musicPath.txt");
			BufferedWriter bfMusicPath = new BufferedWriter(fwMusicPath);
			for (int i = 0; i < musicList.getItemCount(); i++)
			{
				bfMusicPath.write(pathList.get(i));
				bfMusicPath.newLine();
			}
			bfMusicPath.flush();
			bfMusicPath.close();
			fwMusicPath.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// 将配置信息写到properties文件中
	private void outProperties()
	{
		try
		{

			String element1 = backImage.getIcon().toString();
			String element2 = String.valueOf(modeSign);
			String element3 = volumeSize;
			String element4 = String.valueOf(nextSize);

			Properties prop = new Properties();
			prop.setProperty("bgUrl", element1);
			prop.setProperty("modeSign", element2);
			prop.setProperty("volumeSize", element3);
			prop.setProperty("nextSize", element4);

			PrintWriter pw = new PrintWriter("mp3.config.properties");
			prop.list(pw);
			pw.flush();
			pw.close();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	// 读取音乐到播放列表中
	private void input()
	{
		try
		{
			FileReader frMusicPath = new FileReader("musicPath.txt");
			BufferedReader brMusicPath = new BufferedReader(frMusicPath);
			try
			{
				String strTemp = null;
				String tempStr = null;
				String subStr = null;
				int i = 0;
				while ((strTemp = brMusicPath.readLine()) != null)
				{
					pathList.add(strTemp);
					File file = new File(strTemp);
					tempStr = new String(file.getName().trim());
					if (tempStr.length() >= 15)
					{
						subStr = new String(tempStr.substring(0, 15));
					}
					else
					{
						subStr = tempStr;
					}

					if (i < 9)
					{
						musicList.add("00" + (i + 1) + ".  " + subStr);
					}
					else if (i < 99)
					{
						musicList.add("0" + (i + 1) + ".  " + subStr);
					}
					else if (i < 999)
					{
						musicList.add((i + 1) + ".  " + subStr);
					}
					tempList.add(file.toString());
					i++;
				}
				brMusicPath.close();
				frMusicPath.close();

			}
			catch (IOException e)
			{
				System.out.println(e.toString());
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) throws AWTException
	{
		// JFrame.setDefaultLookAndFeelDecorated(true);
		new MiniMp3Player();

	}

}
