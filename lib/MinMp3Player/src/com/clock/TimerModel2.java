package com.clock;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TimerModel2 extends JFrame implements Runnable
{

	private static final long serialVersionUID = 1L;
	private JPanel panel1, panel2, panel3;
	private JLabel title, prompt;
	private JButton stop, end;
	private JMenuBar menuBar;
	private JMenu menu1;
	private JMenuItem menuItem1, menuItem2, menuItem3, menuItem4, menuItem5;
	private Runtime rt;
	private Thread thread;
	private int timerHour, timerPoint, timerSec;
	private int flag;
	private boolean sign;

	public TimerModel2(int h, int p, int s, int f)
	{
		timerHour = h;
		timerPoint = p;
		timerSec = s;
		flag = f;

		this.setTitle("定时器");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		Toolkit tok = Toolkit.getDefaultToolkit();
		int width = tok.getScreenSize().width;
		int height = tok.getScreenSize().height;
		this.setBounds((width - 220) / 2, (height - 150) / 2, 220, 150);

	}

	public void event()
	{
		sign = true;
		menuBar = new JMenuBar();

		menu1 = new JMenu("功能");
		

		menuItem1 = new JMenuItem("立即关机");
		menuItem2 = new JMenuItem("立即重启");
		menuItem3 = new JMenuItem("立即注销");
		menuItem4 = new JMenuItem("查看说明");
		menuItem5 = new JMenuItem("结束");
	
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();

		title = new JLabel();
		prompt = new JLabel();
		title.setForeground(Color.blue);
		prompt.setForeground(Color.red);

		stop = new JButton("暂停");
		end = new JButton("结束");

		menuBar.add(menu1);


		menu1.add(menuItem1);
		menu1.add(menuItem2);
		menu1.add(menuItem3);
		menu1.add(menuItem4);
		menu1.add(menuItem5);

	
		
		panel1.add(title);
		panel2.add(prompt);
		panel3.add(stop);
		panel3.add(end);

		this.setJMenuBar(menuBar);
		this.add("North", panel1);
		this.add("Center", panel2);
		this.add("South", panel3);

		if (flag == SetTime.CLOSE_DOWN)
		{
			title.setText("将在" + timerHour + "点" + timerPoint + "分" + timerSec
					+ "秒时关机！");
		}
		else if (flag == SetTime.REBOOT)
		{
			title.setText("将在" + timerHour + "点" + timerPoint + "分" + timerSec
					+ "秒时重启！");
		}
		else if (flag == SetTime.CANCEL)
		{
			title.setText("将在" + timerHour + "点" + timerPoint + "分" + timerSec
					+ "秒时注销！");
		}
		thread = new Thread(this);
		thread.start();

		 rt = Runtime.getRuntime();
		menuItem1.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				try
				{
					int in = JOptionPane.showConfirmDialog(null, "您确定要关机吗？",
							"提示", JOptionPane.YES_NO_OPTION);

					if (in == JOptionPane.YES_OPTION)
					{
						sign = false;
						rt.exec("shutdown.exe -s -t 30");
						System.exit(0);
					}
					
					
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "执行失败！");
					e.printStackTrace();
				}
			}
		});
		
		menuItem2.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				try
				{
					int in = JOptionPane.showConfirmDialog(null, "您确定要重启吗？",
							"提示", JOptionPane.YES_NO_OPTION);

					if (in == JOptionPane.YES_OPTION)
					{
						sign=false;
						rt.exec("shutdown.exe -r");
						System.exit(0);
					}
				
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "执行失败！");
					e.printStackTrace();
				}
			}
		});
		menuItem3.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				try
				{
					int in = JOptionPane.showConfirmDialog(null, "您确定要注销吗？",
							"提示", JOptionPane.YES_NO_OPTION);

					if (in == JOptionPane.YES_OPTION)
					{
						sign=false;
						rt.exec("shutdown.exe -l");
						System.exit(0);
					}
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "执行失败！");
					e.printStackTrace();
				}
			}
		});
		menuItem4.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				Declare declare = new Declare();
				declare.setVisible(true);
			}
		});
		menuItem5.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				over();
				 
				
			}
		});
		
	
		
		stop.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				if (stop.getText() == "暂停")
				{ // 使用e.getActionCommand()和stop.getActionCommand()是一样的
					sign = false;
					stop.setText("开始");
				}
				else if (stop.getText() == "开始")
				{
					sign = true;
					stop.setText("暂停");
				}

			}
		});

		end.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				over();
			}
		});
		
		

	}
	private void over(){
		int in = JOptionPane.showConfirmDialog(null, "您确定要结束吗？", "提示",
				JOptionPane.YES_NO_OPTION);

		if (in == JOptionPane.YES_OPTION)
		{
			sign = false;
			dispose();
		}
	}

	public void run()
	{
		while (true)
		{
			if (sign)
			{
				currentTime();
			}
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void currentTime()
	{
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dataformat = new SimpleDateFormat("HH:mm:ss");

		prompt.setText("当前时间：" + dataformat.format(calendar.getTime()));

		if (calendar.get(Calendar.HOUR_OF_DAY) == timerHour)
		{

			if (calendar.get(Calendar.MINUTE) == timerPoint)
			{

				if (calendar.get(Calendar.SECOND) == timerSec)
				{
					carryOut();
				}
			}

		}

	}

	public void carryOut()
	{
		Runtime rt = Runtime.getRuntime();
		if (flag == SetTime.CLOSE_DOWN)
		{
			try
			{
				rt.exec("shutdown.exe -s -t 30");
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(null, "执行失败！");
				e.printStackTrace();
			}
		}
		else if (flag == SetTime.REBOOT)
		{
			try
			{
				rt.exec("shutdown.exe -r");
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(null, "执行失败！");
				e.printStackTrace();
			}
		}
		else if (flag == SetTime.CANCEL)
		{
			try
			{
				rt.exec("shutdown.exe -l");
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(null, "执行失败！");
				e.printStackTrace();
			}
		}
		dispose();
	}


}
