import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GameGround extends JPanel {
	private int time = 0;
	private ScorePanel scorePanel = null;	//생성자에서 ScorePanel을 받아올거임
	private JTextField textInput = new JTextField(20);
	private WordArr[] wordArr = new WordArr[20];	//단어 최대 20개
	private int wordCount = 0;	//다음은 배열의 0번째 단어 추락 예정
	private int speed = 10;		//speed틱에 한번 단어 추락
	private TextSource textSource = null;
	private JLabel player = new JLabel();	//플레이어
	private ImageIcon [] playerImageIcon = { //플레이어 이미지 객체 배열
			new ImageIcon("images/배.png"),
			new ImageIcon("images/배2.png"),
			new ImageIcon("images/배3.png"),
			new ImageIcon("normal.png")
			};
	private Image playerImage = playerImageIcon[1].getImage();
	private Point playerPoint = new Point(400,350);	//플레이어 위치
	
	public GameGround(ScorePanel scorePanel, TextSource textSource) {
		this.scorePanel = scorePanel;
		this.textSource = textSource;
		setLayout(null);
		
		textInput.setSize(300, 20);		//단어 입력칸
		textInput.setLocation(250, 470);
		add(textInput);
		
		textInput.addActionListener(new ActionListener() {	//단어 정답 입력받는 리스너 추가
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				String text = tf.getText();
				for(int i = 0; i<wordArr.length;i++)
				{
					if(text.equals(wordArr[i].wordLabel.getText())) {
						scorePanel.increase(10);
						wordArr[i].nextWord();
						tf.setText("");
					}
				}
			}
		});;
		textInput.addKeyListener(new MyKey());
		setFocusable(true);	
		addKeyListener(new MyKey());
		
		for (int i = 0; i < wordArr.length; i++) {	
	        wordArr[i] = new WordArr();
	        wordArr[i].wordLabel.setSize(60,20);
	        wordArr[i].wordLabel.setLocation(20,20);
	        wordArr[i].wordLabel.setVisible(false);
	        wordArr[i].wordLabel.setOpaque(true); //Opaque값을 true로 미리 설정해 주어야 배경색이 적용된다.
	        wordArr[i].wordLabel.setHorizontalAlignment(JLabel.CENTER);
	        wordArr[i].wordLabel.setBackground(Color.pink);
	        add(wordArr[i].wordLabel);
	    }
		
		WordThread wordThread = new WordThread();
		wordThread.start();
		PlayerThread playerThread = new PlayerThread();
		playerThread.start();
		AnimationThread animationThread = new AnimationThread();
		animationThread.start();
		System.out.println("게임그라운드 시작");	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(playerImage,playerPoint.x,playerPoint.y,100,70,this);
	}
	
	class WordArr {	//떨어지는 단어 
		JLabel wordLabel = new JLabel("시험중");
		boolean fallFlag=false;	//떨어지고 있는 단어인지 확인, 초기값 false
		
		public void startDropWord() {	//단어 출발
			String word = textSource.next();
			wordLabel.setText(word);
			wordLabel.setSize(100,20);
			wordLabel.setLocation((int)(Math.random()*735)+10,10);	//10부터 734까지 x중에 출현
			wordLabel.setVisible(true);
		}
		public void fallWord() {
			wordLabel.setLocation(wordLabel.getX(), wordLabel.getY()+10);
			if(wordLabel.getY()>400) {	// 땅바닥에 닿으면
				nextWord();
			}
		}
		public void nextWord() {	//단어 맞추거나 바닥에 닿으면 숨기
			wordLabel.setText("행운의점수방지용임시텍스트@ㅇㄴ람ㅌ캬퍄객지2");
			fallFlag=false;
			wordLabel.setLocation(10, 10);
			wordLabel.setVisible(false);
		}
	}
	
	class WordThread extends Thread {		//단어 드랍 쓰레드
		@Override
		public void run() {
			while(true) {	//1초에 한번 수행하는 작업 목록
				if(time%speed==0)	//지정된 초가 된다면 다음 단어 출발시키고 카운트 증가
				{
					wordArr[wordCount].fallFlag=true;
					wordArr[wordCount].startDropWord();
					wordCount++;
					wordCount = wordCount%20;
				}
				for(int i=0; i<20; i++) {
					if(wordArr[i].fallFlag==true)
					{
						wordArr[i].fallWord();
						//System.out.println(wordArr[i].wordLabel.getX()+","+wordArr[i].wordLabel.getY());
					}
				}
				try {	
					sleep(100);	//1틱 단위
					time+=1;	//시간 1틱 지남
				} catch (InterruptedException e) {	//인터룹트 받으면 종료
					return;
				}
			}
		}
	}
	
	class PlayerThread extends Thread {		//플레이어와 피격판정 관리 쓰레드
		private boolean playerStar = false;		
		@Override
		public void run() {
			while(true)
			{
				for(int i=0;i<wordArr.length;i++) {
					if(wordArr[i].wordLabel.getY()>350) {	//플레이어와 부딪힐 수 있는 높이일 때 충돌했다면
						if(wordArr[i].wordLabel.getX()-playerPoint.x <90 && wordArr[i].wordLabel.getX()-playerPoint.x > -91) {	//89에서 -90
							if (playerStar==false) {
								scorePanel.decrease(10);	//닿으면 10점 감소
							}
							else {
								scorePanel.increase(20);	//스타 상태면 20점 추가
							}
							System.out.println("충돌됨, 중심축과 거리 :"+(wordArr[i].wordLabel.getX()-playerPoint.x));
							System.out.println("무적 상태 여부:"+playerStar);
							wordArr[i].nextWord();
						}
					}
				}
				try {	
					sleep(200);	//테스트 결과 단어가 아무리 빨라도 단어 떨어지는 딜레이랑 같은 주기로 계산해도 충분함
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
	
	class AnimationThread extends Thread {	//배경 애니메이션 등 게임 규칙과 진행에 있어서 제일 중요하지 않은 쓰레드
		public void run() {
			while(true) {
				//애니메이션 움직임 표현을 여기에 입력
				textInput.requestFocus();	////다른 곳을 클릭해서 단어가 입력되지 않는 경우를 방지하기 위해 포커스와 리스너는 항상 텍스트인풋
				try {	
					sleep(1000);	//테스트 결과 단어가 아무리 빨라도 단어 떨어지는 딜레이랑 같은 주기로 계산해도 충분함
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
	
	public class MyKey extends KeyAdapter {
		@Override 
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch(key) {
			case KeyEvent.VK_LEFT:
				if(playerPoint.x>0)
					playerPoint.x = playerPoint.x-10;
				break;
			case KeyEvent.VK_RIGHT:
				if(playerPoint.x<700)
					playerPoint.x = playerPoint.x+10;
				//System.out.println(playerPoint.x);
				break;					
			case KeyEvent.VK_UP:

				break;
			case KeyEvent.VK_DOWN:

				break;
			case KeyEvent.VK_K:

				break;
			case KeyEvent.VK_X:

				break;
			}
			repaint();
		}
	}
}
