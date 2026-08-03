package ch12;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

// 구역주석: 구역 지정 ctrl + shift + /  
// 커서주석: 커서있는 곳 ctrl + /
public class RunnableFrameEx5 extends MFrame implements Runnable {

	Color c;
	int x, y;
	Random r;

	// 생성자
	public RunnableFrameEx5(Color c, int x, int y) {
		super(300, 300); // MFrame의 생성자 호출 (가로 300, 세로 300)
		this.c = c;
		r = new Random();
		setLocation(x, y); // 창이 뜨는 위치값
		setVisible(true);  // 화면에 프레임 띄우기
	}

	// 쓰레드 시작을 돕는 메서드
	public void start() {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < 100; i++) {
				x = r.nextInt(300); // 프레임 창 안쪽에 그려지도록 250 정도로 조정
				y = r.nextInt(300);
				Thread.sleep(1);
				repaint(); // 화면 다시 그리기 요청 -> update() -> paint()
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 깜빡임을 줄이거나 기본 배경을 지우는 작업
	@Override
	public void update(Graphics g) {
		paint(g);
	}

	// 화면에 실제로 그리는 기능
	@Override
	public void paint(Graphics g) {
		g.setColor(c);
		g.fillOval(x, y, 30, 30); // 지정한 색상(c)으로 (x,y) 위치에 원 그리기
	}

	public static void main(String[] args) {
		RunnableFrameEx5 f1 = new RunnableFrameEx5(Color.BLUE, 100, 100);
		RunnableFrameEx5 f2 = new RunnableFrameEx5(Color.RED, 400, 100);
		
		f1.start();
		f2.start();
	}
}