package com.clock;


import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Clock extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel panel1, panel2, panel3;
	private JLabel title;
	private JRadioButton jradioButton1, jradioButton2;
	private ButtonGroup buttonGroup;
	private JButton start, end;
	private int flag;
	public static final int MODE = 1;
	public static final int MODE2 = 2;

	public Clock()
	{

		Toolkit tok = Toolkit.getDefaultToolkit();
		int w = tok.getScreenSize().width;
		int h = tok.getScreenSize().height;
		this.setBounds((w - 200) / 2, (h - 200) / 2, 200, 150);

		this.setTitle("定时器");
		this.setResizable(false);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void event()
	{

		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();

		title = new JLabel();
		title.setText("请选择模式");
		title.setFont(new Font(title.getFont().getFontName(), title.getFont()
				.getStyle(), 15));
		title.setForeground(Color.red);

		jradioButton1 = new JRadioButton();
		jradioButton1.setText("计时模式");
		jradioButton1.setSelected(true);
		flag = MODE;

		jradioButton2 = new JRadioButton();
		jradioButton2.setText("时间模式");

		buttonGroup = new ButtonGroup();
		buttonGroup.add(jradioButton1);
		buttonGroup.add(jradioButton2);

		start = new JButton();
		start.setText("开始");

		end = new JButton();
		end.setText("退出");

		panel1.add(title);
		panel2.add(jradioButton1);
		panel2.add(jradioButton2);
		panel3.add(start);
		panel3.add(end);

		this.add("North", panel1);
		this.add("Center", panel2);
		this.add("South", panel3);

		this.setVisible(true);

		jradioButton1.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				flag = MODE;
			}
		});

		jradioButton2.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				flag = MODE2;
			}
		});

		start.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{

				SetTime setTime = new SetTime(flag);
				setTime.event();
				setTime.setVisible(true);
				Clock.this.dispose();

			}
		});

		end.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				dispose();
			}
		});
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		});

	}

}