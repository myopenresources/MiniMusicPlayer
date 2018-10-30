package com.clock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Declare extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel panel, panel2, panel3;
	private JLabel title, content, content2, content3;
	private JButton end;

	public Declare()
	{

		Toolkit tok = Toolkit.getDefaultToolkit();
		int w = tok.getScreenSize().width;
		int h = tok.getScreenSize().height;
		this.setBounds((w - 230) / 2, (h - 200) / 2, 230, 190);

		this.setTitle("定时器");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		panel = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();

		title = new JLabel("简单说明");
		title.setFont(new Font(title.getFont().getFontName(), title.getFont()
				.getStyle(), 15));
		title.setForeground(Color.red);
		content = new JLabel("设定好几时几分几秒，然后选择");
		content2 = new JLabel("好执行方式就会自动的计时，当");
		content3 = new JLabel("计时完毕就会执行相应的方式。");

		end = new JButton("关闭");

		panel.add(title);
		panel2.add(content);
		panel2.add(content2);
		panel2.add(content3);
		panel3.add(end);

		this.add("North", panel);
		this.add("Center", panel2);
		this.add("South", panel3);

		end.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				dispose();

			}
		});

	}
	
	

}
