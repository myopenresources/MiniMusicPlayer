package com.clock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SetTime extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel panel1, panel2, panel3, panel4, panel5;
	private JLabel title, hourLabel, pointLabel, secLabel;
	private JTextField setHour, setPoint, setSec;
	private JButton closeDown, reboot, cancel, state, empty, end;// �ػ���������ע����˵������ա��˳�

	private JMenuBar menuBar;
	private JMenu menu1;
	private JMenuItem menuItem1, menuItem2, menuItem3, menuItem4, menuItem5,
			menuItem6, menuItem7;
	private Runtime rt;

	public static final int CLOSE_DOWN = 1;
	public static final int REBOOT = 2;
	public static final int CANCEL = 3;

	private int flag;

	public SetTime(int f)
	{
		flag = f;

		this.setTitle("��ʱ�� ");
		this.setResizable(false);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit tok = Toolkit.getDefaultToolkit();
		int w = tok.getScreenSize().width;
		int h = tok.getScreenSize().height;
		this.setBounds((w - 230) / 2, (h - 200) / 2, 230, 190);

	}

	public void event()
	{
		menuBar = new JMenuBar();

		menu1 = new JMenu("����");

		menuItem1 = new JMenuItem("�����ػ�");
		menuItem2 = new JMenuItem("��������");
		menuItem3 = new JMenuItem("����ע��");
		menuItem4 = new JMenuItem("�鿴˵��");
		menuItem5 = new JMenuItem("�������");
		menuItem6 = new JMenuItem("ģʽ����");
		menuItem7 = new JMenuItem("�˳�");

		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		panel5.setLayout(new BorderLayout());

		title = new JLabel();
		title.setText("���趨ʱ��");
		title.setForeground(Color.red);
		title.setFont(new Font(title.getFont().getFontName(), title.getFont()
				.getStyle(), 15));

		hourLabel = new JLabel();
		hourLabel.setText("ʱ:");

		pointLabel = new JLabel();
		pointLabel.setText("��:");

		secLabel = new JLabel();
		secLabel.setText("��:");

		setHour = new JTextField(3);
		setPoint = new JTextField(3);
		setSec = new JTextField(3);

		closeDown = new JButton("�ػ�");
		reboot = new JButton("����");
		cancel = new JButton("ע��");
		state = new JButton("˵��");
		empty = new JButton("���");
		end = new JButton("�˳�");

		menuBar.add(menu1);

		menu1.add(menuItem1);
		menu1.add(menuItem2);
		menu1.add(menuItem3);
		menu1.add(menuItem4);
		menu1.add(menuItem5);
		menu1.add(menuItem6);
		menu1.add(menuItem7);

		panel1.add(title);

		panel2.add(hourLabel);
		panel2.add(setHour);

		panel2.add(pointLabel);
		panel2.add(setPoint);

		panel2.add(secLabel);
		panel2.add(setSec);

		panel3.add(closeDown);
		panel3.add(reboot);
		panel3.add(cancel);
		panel4.add(state);
		panel4.add(empty);
		panel4.add(end);

		panel5.add("North", panel3);
		panel5.add("Center", panel4);

		this.setJMenuBar(menuBar);
		this.add("North", panel1);
		this.add("Center", panel2);
		this.add("South", panel5);

		rt = Runtime.getRuntime();

		menuItem1.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				try
				{
					int in = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫ�ػ���",
							"��ʾ", JOptionPane.YES_NO_OPTION);

					if (in == JOptionPane.YES_OPTION)
					{
						rt.exec("shutdown.exe -s -t 30");
						System.exit(0);
					}

				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "ִ��ʧ�ܣ�");
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
					int in = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫ������",
							"��ʾ", JOptionPane.YES_NO_OPTION);

					if (in == JOptionPane.YES_OPTION)
					{
						rt.exec("shutdown.exe -r");
						System.exit(0);
					}
					
					
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "ִ��ʧ�ܣ�");
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
					int in = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫע����",
							"��ʾ", JOptionPane.YES_NO_OPTION);

					if (in == JOptionPane.YES_OPTION)
					{
						rt.exec("shutdown.exe -l");
						System.exit(0);
					}
					
					
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "ִ��ʧ�ܣ�");
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
				clear();

			}
		});

		menuItem6.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				new Clock().event();
				SetTime.this.dispose();

			}
		});
		menuItem7.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				dispose();

			}
		});

		closeDown.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				try
				{
					int ho = Integer.parseInt(setHour.getText());
					int po = Integer.parseInt(setPoint.getText());
					int se = Integer.parseInt(setSec.getText());

					if (flag == Clock.MODE)
					{
						if (ho >= 0 && ho < 168 && po >= 0 && po < 60
								&& se >= 0 && se < 60)
						{
							TimerModel1 timer = new TimerModel1(ho, po, se, CLOSE_DOWN);
							timer.event();
							timer.setVisible(true);
							SetTime.this.dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(null, "������Ϸ��ķ�Χ��");
							setHour.setText("");
							setPoint.setText("");
							setSec.setText("");
							setHour.requestFocus();
						}

					}
					else if (flag == Clock.MODE2)
					{
						if (ho >= 0 && ho < 24 && po >= 0 && po < 60 && se >= 0
								&& se < 60)
						{
							TimerModel2 timer2 = new TimerModel2(ho, po, se,
									CLOSE_DOWN);
							timer2.event();
							timer2.setVisible(true);
							SetTime.this.dispose();

						}

						else
						{

							JOptionPane.showMessageDialog(null, "������Ϸ��ķ�Χ��");
							setHour.setText("");
							setPoint.setText("");
							setSec.setText("");
							setHour.requestFocus();
						}
					}

				}

				catch (Exception e)
				{
					if (setHour.getText().length() == 0
							|| setPoint.getText().length() == 0
							|| setSec.getText().length() == 0)
					{
						JOptionPane.showMessageDialog(null, "������������");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "���������֣�");
						setHour.setText("");
						setPoint.setText("");
						setSec.setText("");
						setHour.requestFocus();
					}

				}
			}
		});

		reboot.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				try
				{
					int ho = Integer.parseInt(setHour.getText());
					int po = Integer.parseInt(setPoint.getText());
					int se = Integer.parseInt(setSec.getText());

					if (flag == Clock.MODE)
					{
						if (ho >= 0 && ho < 168 && po >= 0 && po < 60
								&& se >= 0 && se < 60)
						{
							TimerModel1 timer = new TimerModel1(ho, po, se, REBOOT);
							timer.event();
							timer.setVisible(true);
							SetTime.this.dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(null, "������Ϸ��ķ�Χ��");
							setHour.setText("");
							setPoint.setText("");
							setSec.setText("");
							setHour.requestFocus();
						}

					}
					else if (flag == Clock.MODE2)
					{
						if (ho >= 0 && ho < 24 && po >= 0 && po < 60 && se >= 0
								&& se < 60)
						{
							TimerModel2 timer2 = new TimerModel2(ho, po, se, REBOOT);
							timer2.event();

							timer2.setVisible(true);
							SetTime.this.dispose();

						}

						else
						{

							JOptionPane.showMessageDialog(null, "������Ϸ��ķ�Χ��");
							setHour.setText("");
							setPoint.setText("");
							setSec.setText("");
							setHour.requestFocus();
						}
					}

				}

				catch (Exception e)
				{
					if (setHour.getText().length() == 0
							|| setPoint.getText().length() == 0
							|| setSec.getText().length() == 0)
					{
						JOptionPane.showMessageDialog(null, "������������");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "���������֣�");
						setHour.setText("");
						setPoint.setText("");
						setSec.setText("");
						setHour.requestFocus();
					}

				}
			}
		});

		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				try
				{
					int ho = Integer.parseInt(setHour.getText());
					int po = Integer.parseInt(setPoint.getText());
					int se = Integer.parseInt(setSec.getText());

					if (flag == Clock.MODE)
					{
						if (ho >= 0 && ho < 168 && po >= 0 && po < 60
								&& se >= 0 && se < 60)
						{
							TimerModel1 timer = new TimerModel1(ho, po, se, CANCEL);
							timer.event();
							timer.setVisible(true);
							SetTime.this.dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(null, "������Ϸ��ķ�Χ��");
							setHour.setText("");
							setPoint.setText("");
							setSec.setText("");
							setHour.requestFocus();
						}

					}
					else if (flag == Clock.MODE2)
					{
						if (ho >= 0 && ho < 24 && po >= 0 && po < 60 && se >= 0
								&& se < 60)
						{
							TimerModel2 timer2 = new TimerModel2(ho, po, se, CANCEL);
							timer2.event();
							timer2.setVisible(true);
							SetTime.this.dispose();

						}

						else
						{

							JOptionPane.showMessageDialog(null, "������Ϸ��ķ�Χ��");
							setHour.setText("");
							setPoint.setText("");
							setSec.setText("");
							setHour.requestFocus();
						}
					}

				}

				catch (Exception e)
				{
					if (setHour.getText().length() == 0
							|| setPoint.getText().length() == 0
							|| setSec.getText().length() == 0)
					{
						JOptionPane.showMessageDialog(null, "������������");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "���������֣�");
						setHour.setText("");
						setPoint.setText("");
						setSec.setText("");
						setHour.requestFocus();
					}

				}
			}
		});

		state.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				Declare declare = new Declare();
				declare.setVisible(true);

			}
		});
		empty.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent arg0)
			{
				clear();
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

	private void clear()
	{
		if (setHour.getText().length() == 0 && setPoint.getText().length() == 0
				&& setSec.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(null, "û�п�����յ����ݣ�");
			setHour.requestFocus();
		}
		else if (setHour.getText().length() == 0
				&& setPoint.getText().length() > 0
				&& setSec.getText().length() > 0)
		{
			setPoint.setText("");
			setSec.setText("");
			setHour.requestFocus();

		}
		else if (setHour.getText().length() > 0
				&& setPoint.getText().length() == 0
				&& setSec.getText().length() > 0)
		{
			setHour.setText("");
			setSec.setText("");
			setHour.requestFocus();
		}
		else if (setHour.getText().length() > 0
				&& setPoint.getText().length() > 0
				&& setSec.getText().length() == 0)
		{
			setHour.setText("");
			setPoint.setText("");
			setHour.requestFocus();

		}
		else if (setHour.getText().length() == 0
				&& setPoint.getText().length() == 0
				&& setSec.getText().length() > 0)
		{
			setSec.setText("");
			setHour.requestFocus();
		}
		else if (setHour.getText().length() == 0
				&& setPoint.getText().length() > 0
				&& setSec.getText().length() == 0)
		{
			setPoint.setText("");
			setHour.requestFocus();
		}
		else if (setHour.getText().length() > 0
				&& setPoint.getText().length() == 0
				&& setSec.getText().length() == 0)
		{
			setHour.setText("");
			setHour.requestFocus();
		}

		else
		{

			setHour.setText("");
			setPoint.setText("");
			setSec.setText("");
			setHour.requestFocus();
		}
	}

}
