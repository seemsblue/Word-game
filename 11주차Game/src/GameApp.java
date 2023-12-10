
public class GameApp {
	public static void main(String[] args) {
		new GameFrame();
	}
	/**호출되면 GameApp에서 새로운 GameFrame을 생성하고 교체한다*/
}




//		/** */  <<  사이에 함수 설명 넣으면 자동완성 할때 툴팁이 나온다는 걸 알아버림, 최대한 고쳐놔야지 
//		깃허브 이해할 수 가 없 네?		그래도 몬가 해놓으니까 편하다
//		해쉬맵으로 저장하니까 키값이 중복이 안될텐데, 그러면 같은 점수면 늦게 찍은 사람이 -1점 당하는걸로 하기
//		이미지에 롤오버 롤아웃 넣기



/*패널 메모				
 *ㄴ 					GameFrame(+메뉴 툴바)	
 *ㄴ						GamePanel (or) MainMenu								
 *ㄴ		GameGround(800*600)			 	ScorePanel		EditPanel
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * */

//까먹지 말고 repaint 키보드 말고 더 자주 하는데로 옮겨놔라 >> 아니 왜 배경을 포토샵으로 그리고 투명부분을 안칠해서 문제를 만들어 아오 그냥 repaint 2개써
