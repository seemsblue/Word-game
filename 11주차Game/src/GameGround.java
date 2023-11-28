import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameGround extends JPanel {
	private ScorePanel scorePanel = null;
	private JTextField textInput = new JTextField(20);
	private JLabel label = new JLabel("여기");
	private TextSource textSource = null;
	
	public GameGround(ScorePanel scorePanel) {
		this.scorePanel = scorePanel;
		
		setLayout(null);
		label.setSize(100,20);
		label.setLocation(10, 10);
		add(label);
		
		textInput.setSize(300, 20);
		textInput.setLocation(30, 400);
		add(textInput);
		
		textInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				String text = tf.getText();
				if(text.equals(label.getText())) {
					scorePanel.increase(10);
					String word = textSource.next();
					label.setText(word);
					label.setText(word);
					label.setLocation(10,10);
					tf.setText("");
				}
			}
		});;
		textSource =new TextSource(this);
		MyThread th = new MyThread();
		th.start();
	}
	
	class MyThread extends Thread {
		@Override
		public void run() {
			while(true) {
				label.setLocation(label.getX(),label.getY()+10);
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
}
