import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class GameFrame extends JFrame {
	private GamePanel gamePanel = null;		//메모
	private MainMenuPanel mainMenuPanel = null;
	private GameFrame gm = this;
	public GameFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("게임");
		setSize(1000,580);
		setResizable(false);
		makeMenu();		//상단 메뉴
		//makeToolbar();	//상단 툴바
		
		mainMenuPanel = new MainMenuPanel(this);	//게임 시작하기 위해서 전달
		getContentPane().add(mainMenuPanel);
		
		setVisible(true);
	}
	
	public void startGame() {
		mainMenuPanel.setVisible(false);	//없어도 되긴 하던데 필요한가?
		gamePanel = new GamePanel(gm);
		getContentPane().add(gamePanel);
		setVisible(true);
	}
	
	private void makeMenu() {
		JMenuBar mb = new JMenuBar();
		this.setJMenuBar(mb);
		
		/*JMenu fileMenu = new JMenu("File");
		fileMenu.add(new JMenuItem("Open"));
		fileMenu.add(new JMenuItem("Save"));
		fileMenu.add(new JMenuItem("Save As"));
		fileMenu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			});
		fileMenu.add(exitItem);
		mb.add(fileMenu);
		
		JMenu editMenu = new JMenu("Edit");
		editMenu.add(new JMenu("insert"));
		mb.add(editMenu);*/
	}
	
	public void makeToolbar() {
		JToolBar bar = new JToolBar();
		bar.setBackground(Color.gray);
		getContentPane().add(bar, BorderLayout.NORTH);
		JButton b = new JButton("play");
		bar.add(b);
		
		ImageIcon normalIcon = new ImageIcon("normal.png");
		ImageIcon rolloverIcon = new ImageIcon("rollovered.png");
		ImageIcon pressedIcon = new ImageIcon("pressed.png");
		JButton imageBtn = new JButton(normalIcon);
		imageBtn.setRolloverIcon(rolloverIcon);
		imageBtn.setPressedIcon(pressedIcon);
		bar.add(imageBtn);
		bar.setFloatable(false);
	}
	
	public void resetGame() {
		gamePanel.setVisible(false);
		mainMenuPanel.setVisible(true);
	}
}
