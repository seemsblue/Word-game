import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	private GameGround gm= null;
	private int playerHp=3;
	private int score = 0;
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	private JLabel HpLabel = new JLabel(Integer.toString(playerHp));
	private ImageIcon [] hpIcon={
		new ImageIcon("images/Hp1.png"),
		new ImageIcon("images/Hp2.png"),
		new ImageIcon("images/Hp3.png"),
		new ImageIcon("images/Hp4.png"),
		new ImageIcon("images/Hp4.png"),
		new ImageIcon("images/Hp4.png"),
		new ImageIcon("images/Hp4.png")
	};
	private Image hpImage = hpIcon[2].getImage();
	public ScorePanel() {
		setLayout(null);
		setBackground(Color.yellow);
		JLabel label = new JLabel("score :");
		label.setSize(60,20);
		label.setFont(new Font("고딕",Font.ITALIC,17));
		JLabel label2 = new JLabel("배 내구도 :");
		label2.setSize(80,20);
		scoreLabel.setSize(50, 20);
		HpLabel.setSize(50, 30);
		label.setLocation(20,10);
		label2.setLocation(12,200);
		label2.setFont(new Font("고딕",Font.BOLD,15));
		scoreLabel.setLocation(80,10);
		scoreLabel.setHorizontalAlignment(JLabel.RIGHT);
		scoreLabel.setFont(new Font("고딕",Font.ITALIC,17));
		HpLabel.setLocation(88,195);
		HpLabel.setForeground(Color.BLUE);
		HpLabel.setFont(new Font("고딕",Font.BOLD,22));
		add(label);
		add(label2);
		add(scoreLabel);
		add(HpLabel);
	}
	/**gameGround 객체 받아오는 함수 */
	public void getGameInfo(GameGround gameGround) {
		this.gm = gameGround;
	}
	/** int만큼 점수 증가*/
	public void increase(int i) {
		score += i;
		scoreLabel.setText(Integer.toString(score));
	}
	/** int만큼 점수 감소*/
	public void decrease(int i) {
		score = score - i;
		if(score<0)
			score=0;
		scoreLabel.setText(Integer.toString(score));
	}
	/** int만큼 생명 증가(최대 7)*/
	public void hpUp(int i) {
		if(playerHp<7)
			playerHp +=i;
		
		if(playerHp<7)	//초상화 처리
			hpImage = hpIcon[playerHp-1].getImage();
		else
			hpImage = hpIcon[7].getImage();
		HpLabel.setText(Integer.toString(playerHp));
		repaint();
	}
	/** int만큼 생명 감소, 0이하로 떨어질 시 gameOver호출*/
	public void hpDown(int i) {
		if (playerHp>1)
			playerHp -=i;
		else {
			HpLabel.setText("0");
			gm.gameOver(score);
			return;
		}
		
		if(playerHp<7)	//초상화 처리
			hpImage = hpIcon[playerHp-1].getImage();
		else
			hpImage = hpIcon[7].getImage();
		HpLabel.setText(Integer.toString(playerHp));
		repaint();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(hpImage,10,40,160,160,this);
	}
}
