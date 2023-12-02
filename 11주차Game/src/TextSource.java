import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TextSource {
	private Vector<String> wordVector = new Vector<String>(30000);
	public TextSource(Component parent) {	//GameGround를 받아옴
		try {
			Scanner scanner = new Scanner(new FileReader("krword.txt"));
			while(scanner.hasNext()) {	//다음 라인이 없을 때까지
				String word = scanner.nextLine();
				wordVector.add(word);	//wordVector에 저장
			}
			scanner.close();
			//JOptionPane.showMessageDialog(parent, "단어 읽기 완료");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("텍스트 파일 없음");
			System.exit(0);
		}
	}
	
	public String next() {
		int n = wordVector.size();
		int index = (int)(Math.random()*n);
		return wordVector.get(index);
	}
	
	public void addText(String newWord) {
		wordVector.add(newWord);
	}
}
