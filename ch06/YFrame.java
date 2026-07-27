package ch06;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class YFrame extends Frame{

	YFrame() {
		setTitle("제목");
		setSize(300, 300);
		setBackground(new Color(220, 220, 220));
		setResizable(false);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		validate();
	}

	YFrame(int w, int h) {
		setTitle("제목");
		setSize(w, h);
		setBackground(new Color(220, 220, 220));
		setResizable(false);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		validate();
	}

	YFrame(Color c) {
		setTitle("제목");
		setSize(300, 300);
		setBackground(c);
		setResizable(false);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		validate();
	}

	YFrame(int w, int h, Color c) {
		setTitle("제목");
		setSize(w, h);
		setBackground(c);
		setResizable(false);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		validate();
	}

	public static void main(String[] args) {
		//YFrame mf = new YFrame(500, 200, Color.ORANGE);
		//YFrame mf2 = new YFrame(Color.PINK);
		//YFrame mf3 = new YFrame(250, 250);
		YFrame mf4 = new YFrame();
	}
}
