import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TextSource {
	private Vector<String> wordVector = new Vector<String>(30000);
	public TextSource(Component parent) {
		try {
			Scanner scanner = new Scanner(new FileReader("words.txt"));
			while(scanner.hasNext()) {
				String word = scanner.nextLine();
				wordVector.add(word);
			}
			scanner.close();
			JOptionPane.showMessageDialog(parent, "단어 읽기 완료");
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
	
}
