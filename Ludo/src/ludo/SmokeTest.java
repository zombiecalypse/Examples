package ludo;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

public class SmokeTest {
	private Board board;
	private Player players[];
	private PrettyBoardPrinter printer;
	private int playercount; // AK remove unused variables
	private ArrayList<Color> colors;
	private Random rand;
	private Die die;
	private static boolean printOut = true;
	
	Color chooseColor(){
		int randomInt = rand.nextInt(colors.size());
		Color color = colors.get(randomInt);
		colors.remove(randomInt);
		return color;
	}
	
	public void SetUp(int playercount){
		
		colors = new ArrayList<Color>();
		for(int i = 0; i<4;i++){
			colors.add(Color.values()[i]);
		}
		die = new Die();
		board = new Board( die );
		rand = new Random();
		players = new Player[playercount];
		for (int i = 0; i < playercount; i++){
			players[i] = board.makePlayer(chooseColor());
		}
		printer = new PrettyBoardPrinter(board);
	}
	
	@Test
	public void PlayTwo(){
		PlayX(2);
	}
	
	@Test 
	public void PlayThree(){
		PlayX(3);
	}
	
	@Test
	public void PlayFour(){
		PlayX(4);
	}
	
	public void PlayX(int playercount){
		SetUp(playercount);
		while(!board.hasEnded()){
			board.play();
			if (printOut){
				String output = "next: "+board.nextPlayer().getColor()+"\n" +
								printer.toString()+"\n"+
								"rolled: "+die.getlastResult()+"\n";
				System.out.println(output);
			}
		}
	}
	
	@Ignore
	@Test
	public void PlayMany() {
		for (int i = 0; i<10000; i++){
			PlayX(2);
			PlayX(3);
			PlayX(4);
			System.out.print(".");
			if (i%100 == 99)
				System.out.println();
		}
	}
}
