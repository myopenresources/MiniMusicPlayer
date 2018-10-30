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

		this.setTitle("��ʱ��");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		panel = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();

		title = new JLabel("��˵��");
		title.setFont(new Font(title.getFont().getFontName(), title.getFont()
				.getStyle(), 15));
		title.setForeground(Color.red);
		content = new JLabel("�趨�ü�ʱ���ּ��룬Ȼ��ѡ��");
		content2 = new JLabel("��ִ�з�ʽ�ͻ��Զ��ļ�ʱ����");
		content3 = new JLabel("��ʱ��Ͼͻ�ִ����Ӧ�ķ�ʽ��");

		end = new JButton("�ر�");

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
