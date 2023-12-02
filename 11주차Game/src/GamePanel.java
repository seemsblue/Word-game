import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GamePanel extends JPanel {
	private ScorePanel scorePanel = new ScorePanel();						//Score패널 생성하고, 정보 가지고 있음
	private TextSource textSource = new TextSource(this);					//새 단어를 입력하기 위해서 text 벡터는 패널에서 가지고 있는걸로
	private GameGround gameGround = new GameGround(scorePanel, textSource);	//gameGround의 생성자에 scorePanel의 주소를 넘겨줌
	public GamePanel(){		//전체 패널임
			setLayout(new BorderLayout());
			splitPanel();
	}
		
	private void splitPanel() {
		JSplitPane hPane = new JSplitPane();	//나누기
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);	//가로 2열로 만듦
		hPane.setDividerLocation(800);
		add(hPane);
		
		
		JSplitPane vPane = new JSplitPane();
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);	//2행으로 만듦
		vPane.setDividerLocation(300);
		
		hPane.setRightComponent(vPane);		//나눈 것의 오른쪽은 vPane
		
		hPane.setLeftComponent(gameGround);	
		vPane.setTopComponent(scorePanel);	//이미 private로 가지고 있음
		vPane.setBottomComponent(new EditPanel(textSource));
	}

}