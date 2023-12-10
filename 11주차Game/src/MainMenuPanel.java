import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel {
	private GamePanel gamePanel = null;
	private RankSource rank = new RankSource();
	private ImageIcon icon =new ImageIcon("MainMenu.jpg");
	private Image img = icon.getImage();
	private ImageIcon [] flagIcon = { //플레이어 이미지 객체 배열
			new ImageIcon("images/flag1.png"),
			new ImageIcon("images/flag2.png"),
			new ImageIcon("images/flag3.png"),
			new ImageIcon("images/flag4.png"),
			new ImageIcon("images/flag5.png"),
			new ImageIcon("images/flag6.png"),
			};
	private Image flagImage1 = flagIcon[0].getImage();
	private Image flagImage2 = flagIcon[2].getImage();
	private Image flagImage3 = flagIcon[4].getImage();
	
    public MainMenuPanel(GameFrame gameFrame) {
    	this.setLayout(null);
        JButton startButton = new JButton("");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("시작 버튼 클릭");
                gameFrame.startGame();
            }
        });

        JButton helpButton = new JButton("");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 도움말 버튼이 눌렸을 때 수행할 동작 
                System.out.println("도움말 버튼 클릭");
            }
        });

        JButton rankButton = new JButton("");
        rankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 순위 버튼이 눌렸을 때 수행할 동작 
                System.out.println("순위 버튼 클릭");
            }
        });
        startButton.setSize(100,100);
        startButton.setLocation(100,260);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);	//밑 3줄은 버튼을 안보이게 각각 테두리 배경요소 투명하게 만든거임
        startButton.setEnabled(true);
        startButton.setBorderPainted(false);
        startButton.addMouseListener(new MouseAdapter() {	//이미지에는 마우스어댑터가 안들어가고,라벨은 이미지 사이즈가 조절이 안되니까 투명버튼 만들고 걔가 이미지까지 다 조절하게
            @Override
            public void mouseEntered(MouseEvent e) {
            	flagImage1 = flagIcon[1].getImage();	//롤오버도 투명버튼이 대신 수행
            	repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	flagImage1 = flagIcon[0].getImage();	//롤아웃
            	repaint();
            }
        });
        helpButton.setSize(100,100);
        helpButton.setLocation(300,320);
        helpButton.setOpaque(false);
        helpButton.setContentAreaFilled(false);	//밑 3줄은 버튼을 안보이게 각각 테두리 배경요소 투명하게 만든거임
        helpButton.setEnabled(true);
        helpButton.setBorderPainted(false);
        helpButton.addMouseListener(new MouseAdapter() {	//이미지에는 마우스어댑터가 안들어가고,라벨은 이미지 사이즈가 조절이 안되니까 투명버튼 만들고 걔가 이미지까지 다 조절하게
            @Override
            public void mouseEntered(MouseEvent e) {
            	flagImage2 = flagIcon[3].getImage();	//롤오버도 투명버튼이 대신 수행
            	repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	flagImage2 = flagIcon[2].getImage();	//롤아웃
            	repaint();
            }
        });
        rankButton.setSize(100,100);
        rankButton.setLocation(530,390);
        rankButton.setOpaque(false);
        rankButton.setContentAreaFilled(false);	//밑 3줄은 버튼을 안보이게 각각 테두리 배경요소 투명하게 만든거임
        rankButton.setEnabled(true);
        rankButton.setBorderPainted(false);
        rankButton.addMouseListener(new MouseAdapter() {	//이미지에는 마우스어댑터가 안들어가고,라벨은 이미지 사이즈가 조절이 안되니까 투명버튼 만들고 걔가 이미지까지 다 조절하게
            @Override
            public void mouseEntered(MouseEvent e) {
            	flagImage3 = flagIcon[5].getImage();	//롤오버도 투명버튼이 대신 수행
            	repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	flagImage3 = flagIcon[4].getImage();	//롤아웃
            	repaint();
            }
        });
        add(startButton);
        add(helpButton);
        add(rankButton);
        
        
    }
    
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(img,0,0,this.getWidth(),this.getHeight(),null);
    	g.drawImage(flagImage1,100,260,100,100,null);
    	g.drawImage(flagImage2,300,320,100,100,null);
    	g.drawImage(flagImage3,530,390,100,100,null);
    }
}
