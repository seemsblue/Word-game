import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GamePanel extends JPanel {
	private ScorePanel scorePanel = new ScorePanel();
	public GamePanel(){
			setBackground(Color.yellow);
			setLayout(new BorderLayout());
			splitPanel();
	}
		
	private void splitPanel() {
		JSplitPane hPane = new JSplitPane();
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(500);
		add(hPane);
		
		JSplitPane vPane = new JSplitPane();
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		vPane.setDividerLocation(300);
		
		hPane.setRightComponent(vPane);
		
		vPane.setTopComponent(scorePanel);
		vPane.setBottomComponent(new EditPanel());
		hPane.setLeftComponent(new GameGround(scorePanel));	//gameGround의 생성자에 scorePanel의 주소를 넘겨줌

	}

}