import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class RankSource {
	int n;
	private HashMap<Integer,String> rankHash =new HashMap<Integer,String>();
    private List<Integer> keyList = null;	//정렬하기 위해서 키(점수)값 담은 리스트 만듦
	private int high[] = new int[5];	//최고득점자 인덱스번호 5개 저장
	private String highName[] = new String[5];
	
 	public RankSource() {				//책 맨 뒤에서 "정렬" 써있는거 찾고 와 드디어 이거다 했는데 그래픽 가운데 왼쪽정렬이었음...
 		try {
			Scanner scanner = new Scanner(new FileReader("rank.txt"));
			while(scanner.hasNext()) {	//다음 라인이 없을 때까지
				String name = scanner.nextLine();
				String score = scanner.nextLine();
				rankHash.put(Integer.parseInt(score), name);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("랭킹 파일 없음");
		}
 		keyList = new ArrayList<>(rankHash.keySet());
 		keyList.sort(Comparator.reverseOrder());
        for (int j = 0 ; j<3 ; j++) {	//키값을 가지고 리스트 순회하기
        	if (keyList.size()<(j-1))	//랭킹 데이터 모자라면 그만 저장
        		break;
        	System.out.println(keyList.size());
        	int key = keyList.get(j);
            System.out.println("key: " + key + ", value: " + rankHash.get(key));	//큰것부터 작은거까지.
            high[j]=key;
            highName[j]=rankHash.get(key);
            System.out.println(j+"번 입력");
        }
        System.out.println(high[0]+","+highName[0]);
        System.out.println("랭킹소스 클래스 호출환료");
	}
}
