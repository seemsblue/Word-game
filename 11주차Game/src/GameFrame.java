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
 
	
	public GameFrame() {
		setTitle("게임");
		setSize(800,600);
		makeMenu();
		makeToolbar();

		gamePanel = new GamePanel();
		getContentPane().add(gamePanel,BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	private void makeMenu() {
		JMenuBar mb = new JMenuBar();
		this.setJMenuBar(mb);
		
		JMenu fileMenu = new JMenu("File");
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
		mb.add(editMenu);
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
}
