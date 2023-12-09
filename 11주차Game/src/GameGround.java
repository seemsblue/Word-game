import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GameGround extends JPanel {
	private boolean threadFlag = false;
	private int time = 0;
	private JLabel timer = new JLabel("0",SwingConstants.CENTER);
	private int phase = 1;
	private ScorePanel scorePanel = null;	//생성자에서 ScorePanel을 받아올거임
	private int maxWord =25;	//단어 최대 25개
	private int movingWord = 5;	//근데 5개 안전빵 꽁쳐둠
	private JTextField textInput = new JTextField(20);
	private WordArr[] wordArr = new WordArr[maxWord];	
	private int wordCount = 0;	//다음은 배열의 0번째 단어 추락 예정
	private int wordSpeed = 500;
	private int wordFrequency = 5;		//int에 한번 단어 추락
	private TextSource textSource = null;
	private JLabel player = new JLabel();	//플레이어
	private ImageIcon [] playerImageIcon = { //플레이어 이미지 객체 배열
			new ImageIcon("images/배.png"),
			new ImageIcon("images/배2.png"),
			new ImageIcon("images/배3.png"),
			new ImageIcon("images/배0.png")
			};
	private Image playerImage = playerImageIcon[1].getImage();
	private ImageIcon[] backgroundImageIcon= {
			new ImageIcon("images/배경1.png"),
			new ImageIcon("images/배경2.png"),
			new ImageIcon("images/배경3.png")
	};
	private Image backgroundImage = backgroundImageIcon[0].getImage();
	private Point playerPoint = new Point(400,350);	//플레이어 위치
	private boolean playerPerquisite[] = {false,false,false,false,false,false,false};	//7개 능력
	
	private class MyDialog extends JDialog{
		String perquisite[] = {"속도 증가","흡혈","보호막", "'새' 친구" ,"거대화","방독면","미니미"};
		public MyDialog(JFrame frame, String title) {
			super(frame,title);
			setLayout(new GridLayout(2,3,5,20));
			for(int i=0; i<3;i++) {		//설명
				JButton selectButton = new JButton(Integer.toString(i));
				add(selectButton);
				selectButton.addActionListener(action);
			}
			for(int i=0; i<3;i++) {		//선택
				JButton selectButton = new JButton(Integer.toString(i));
				add(selectButton);
				selectButton.addActionListener(action);
			}
			setSize(400,200);
			
		}
		ActionListener action= new ActionListener() {	//버튼 액션리스너
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		};
	}
	
	public GameGround(ScorePanel scorePanel, TextSource textSource) {
		this.scorePanel = scorePanel;
		this.textSource = textSource;
		setLayout(null);
		scorePanel.getGameInfo(this);
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
		
		timer.setFont(new Font("고딕", Font.BOLD, 17));
		timer.setSize(50,20);
		timer.setLocation(0,0);
		timer.setBackground(Color.yellow); timer.setOpaque(true);
		add(timer);
		
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
		
		MyDialog dialog = new MyDialog((JFrame) this.getParent(), "");	//여기는 패널이라 프레임을 건네줘야 함
		dialog.setVisible(true);
		
		
		WordThread wordThread = new WordThread();
		wordThread.start();
		PlayerThread playerThread = new PlayerThread();
		playerThread.start();
		AnimationThread animationThread = new AnimationThread();
		animationThread.start();
		TimeThread timeThread =new TimeThread();
		timeThread.start();
		System.out.println("게임그라운드 시작");	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),this);
		g.drawImage(playerImage,playerPoint.x,playerPoint.y,100,70,this);
	}
	/**떨어지는 단어*/
	class WordArr {	 
		/**사용중인 단어 개수*/
		JLabel wordLabel = new JLabel("시험중");
		boolean fallFlag=false;	//떨어지고 있는 단어인지 확인, 초기값 false
		
		public void startDropWord() {	//단어 출발
			String word = textSource.next();
			wordLabel.setText(word);
			wordLabel.setSize(100,20);
			wordLabel.setLocation((int)(Math.random()*700)+10,20);	//10부터 729까지 x중에 출현
			wordLabel.setVisible(true);
		}
		public void fallWord() {
			wordLabel.setLocation(wordLabel.getX(), wordLabel.getY()+10);
			if(wordLabel.getY()>410) {	// 땅바닥에 닿으면
				nextWord();
			}
		}
		//*단어 맞추거나 바닥에 닿으면 숨기/
		public void nextWord() {	
			wordLabel.setText("행운의점수방지용임시텍스트@ㅇㄴ람ㅌ캬퍄객지2");
			fallFlag=false;
			wordLabel.setLocation(10, 10);
			wordLabel.setVisible(false);
			movingWord--;	//움직이고 있는 단어label 카운트-
		}
	}
	/**단어 드랍 쓰레드*/
	class WordThread extends Thread {		
		@Override
		public void run() {
			while(true) {	//1초에 한번 수행하는 작업 목록

				for(int i=0; i<20; i++) {
					if(wordArr[i].fallFlag==true)
					{
						wordArr[i].fallWord();
						//System.out.println(wordArr[i].wordLabel.getX()+","+wordArr[i].wordLabel.getY());
					}
				}
				try {
					if (threadFlag==true)
						return;
					sleep(wordSpeed);
				} catch (InterruptedException e) {	//인터룹트 받으면 종료
					return;
				}
			}
		}
	}
	/**플레이어와 피격판정 관리 쓰레드*/
	class PlayerThread extends Thread {		
		private boolean playerStar = false;		
		@Override
		public void run() {
			while(true)
			{
				for(int i=0;i<wordArr.length;i++) {
					if(wordArr[i].wordLabel.getY()>340) {	//플레이어와 부딪힐 수 있는 높이일 때 충돌했다면
						if(wordArr[i].wordLabel.getX()-playerPoint.x <89 && wordArr[i].wordLabel.getX()-playerPoint.x > -91) {	//88에서 -90
							if (playerStar==false) {
								scorePanel.decrease(10);	//닿으면 10점 감소
								scorePanel.hpDown(1);
							}
							else {
								scorePanel.increase(20);	//스타 상태면 20점 추가
							}
							System.out.println("충돌됨, 중심축과 거리 :"+(wordArr[i].wordLabel.getX()-playerPoint.x));
							//System.out.println("무적 상태 여부:"+playerStar);
							wordArr[i].nextWord();
						}
					}
				}
				try {
					if (threadFlag==true)
						return;
					sleep(200);	//충돌 체크 간격 - 테스트 결과 단어가 아무리 빨라도 단어 떨어지는 딜레이랑 같은 주기로 계산해도 충분함
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
	
	/**초단위로 게임 라운드 진행에 따라 달라지는 요소를 관리하고 시간을 재는 쓰레드*/
	class TimeThread extends Thread {	
		private Color[] c = {Color.green, Color.yellow, Color.blue, Color.red, Color.MAGENTA, Color.black};	//페이즈 올라갈 때마다 타이머 색도 바뀌기, 마지막라운드는 검정색으로 시간도 안보이는 의도
		private int phasePeriod = 50;
		JDialog dialog = new JDialog();
		//String select = JOptionPane.showInputDialog("능력을 선택하세요");
		public void run() {	//그런데 이걸 따로 만들면 단어쓰레드에서도 시간을 사용할건데... 흠..
			timer.setBackground(c[phase-1]);
			while(true) {	//1초에 한번 하는 작업 목록
				try {
					if (threadFlag==true)
						return;
					sleep(100);	//게임틱
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized(this) {
					time+=1;
					timer.setText(Integer.toString(time));
					if((time/phasePeriod)==phase) {	//phasePeriod에 한번씩 페이즈 증가
						if (phase < 6) {
							phase++;
							timer.setBackground(c[phase-1]);
							wordSpeed = wordSpeed - 70;	//페이즈 상승마다 단어 속도도 상승
						}
					}
				}
				if(time%wordFrequency==0&&movingWord<maxWord)	//평균 speed 초에 한번씩&&최대 개수 확인
				{
					wordArr[wordCount].fallFlag=true;
					wordArr[wordCount].startDropWord();
					movingWord++;
					wordCount++;
					wordCount = wordCount%20;
				}
			}
		}
	}
	/**배경 애니메이션 등 게임 규칙과 진행에 있어서 제일 중요하지 않은 쓰레드*/
	class AnimationThread extends Thread {	
		private int backcount = 0; 
		public void run() {
			while(true) {
				//애니메이션 움직임 표현을 여기에 입력
				backgroundImage = backgroundImageIcon[backcount].getImage();
				backcount++;
				backcount = backcount%3;
				textInput.requestFocus();	////다른 곳을 클릭해서 단어가 입력되지 않는 경우를 방지하기 위해 포커스와 리스너는 항상 텍스트인풋
				repaint();
				try {	
					sleep(1000);
					if (threadFlag==true)
						return;
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
	
	class PerquisiteThread extends Thread {	//퍽 선택하는 쓰레드
		public void run() {
			
		}
	}
	
	public class MyKey extends KeyAdapter {
		@Override 
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch(key) {
			case KeyEvent.VK_LEFT:
				if(playerPoint.x>5)
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
	
	public class PPanel extends JPanel{
		
	}
	
	/**Hp가 0이 될 경우 모든 쓰레드 중지하고 점수 표기,다시하기 또는 종료*/
	public void gameOver(int score) {
		threadFlag = true;
		playerImage = playerImageIcon[3].getImage();
		repaint();
	}
}
