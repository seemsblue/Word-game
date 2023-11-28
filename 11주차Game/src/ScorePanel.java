import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	private int score = 0;
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	
	public ScorePanel() {
		setBackground(Color.yellow);
		add(new JLabel("점수"));
		add(scoreLabel);
	}
	
	public void increase(int i) {
		score += i;
		scoreLabel.setText(Integer.toString(score));
	}
	public void decrease(int i) {
		score += i;
		scoreLabel.setText(Integer.toString(score));
	}
}
