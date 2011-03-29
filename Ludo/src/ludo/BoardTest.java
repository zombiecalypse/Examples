package ludo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

// AK Test must not be verbose! To avoid verbose output, use the methods given
// in this blog post: http://scglectures.unibe.ch/p2/2009/howto-turn-off-systemout

// AK Good test coverage but terrible granularity. You should at least have one
// test per class (unit tests should test units, aka classes) and per test class
// two to twelve tests. Also you should have one test class per game rule with
// two to five tests each. Please refer to the sample solution.

public class BoardTest {

	Board board;
	FakeDie die;
	Player player1,player2;
	PrettyBoardPrinter printer;

	@Before
	public void setup() throws Exception {
		die = new FakeDie();
		board = new Board( die );
		player1 = board.makePlayer(Color.RED);
		player2 = board.makePlayer(Color.BLUE);
		// AK to make this test non-verbose you could eg use a NullBoardPrinter
		// class that does not print anything at all. 
		printer = new PrettyBoardPrinter(board);
	}

	@Test
	public void testCreateBoard() {
		// AK tests methods should start with should, eg shouldCreateBoard 
		System.out.println(printer.toString());
	}
	
	@Test
	public void testNests() {
		// AK test methods should start with should this forces you to document the
		// intent of your tests, and helps readers & maintainers to understand your 
		// test code. Justing naming the unit under test is of limited help. What
		// does this method do: #shouldLeaveNestOnSix or #shouldNotLeaveNestOnSix !?
		die.send(6);
		die.send(0);	//Because player will role again after 6
		die.send(6);
		die.send(0);
		board.play();
		// AK use #assertEquals to compare object, you'll get better error messages!
		assertTrue(player1.getPiece(0).getPosition().equals( board.getNest(Color.RED)));
		board.play();
		assertTrue(player2.getPiece(0).getPosition().equals( board.getNest(Color.BLUE)));
	}

	@Test
	public void testHomeColumns() {
		// AK Avoid loops and other abstractions in test code! Tests should 
		// exercise concrete examples of program execution. Given this test
		// I cannot see at one glance what it does it, it such as likely to
		// be buggy as your actual code. Unit testing is like double-entry 
		// book keeping, you achieve twice the same thing but with opposing 
		// means. For example in actual code you should use loops and avoid
		// magic numbers, whereas in test code you should avoid loops and make
		// use magic numbers aka concrete values!
		// AK As a guideline, one test should contain no more than one or two
		// assertions! There is about a dozen tests buried in this one method.
		for (int i = 0; i < 4 ; i++){
			// TODO step though this code
			Color thisColor = Color.values()[i];
			XSquare homeColumn = board.getHomeColumn(thisColor);
			for(int j = 0; j < 4 ; j++){
				Color otherColor = Color.values()[j];
				XSquare other = board.getHomeColumn(otherColor);
				assertTrue(otherColor == thisColor || homeColumn != other);
				assertTrue(otherColor == thisColor || homeColumn.next(thisColor) != homeColumn.next(otherColor));
			}
			int count = 0;
			for (Square j = homeColumn; j != board.getGoal() ; j = j.next(thisColor)){
				System.out.println(count+((FieldSquare) j).toString());
				assertTrue(count<6);
				count+= 1;
			}
			assertTrue(count==6);
		}
	}

	@Test
	public void testHome() {
		// AK What should... this test example do !? 
		// AK Unroll all loops in tests. Be as concrete as possible!
		HomeSquare goal = board.getGoal();
		ArrayList<Piece> occupands = new ArrayList<Piece>(16);
		for(int i = 0 ; i < 3 ; i++) {
				Piece p = player1.getPiece(i);
				p.enter(goal);
				occupands.add(p);
		}
		assertTrue(goal.getOccupants(player1.getColor()).size() == 3);
		List<Piece> occupiingGoal = goal.getOccupants();
		for (Piece i : occupands){
			assertTrue(occupiingGoal.contains(i));
		}
		assertTrue(!board.hasEnded() && board.winner() == null);
		player1.getPiece(3).enter(goal);
		assertTrue(board.hasEnded() && (board.winner() == board.getPlayer(Color.RED)));
	}
	
	@Test
	public void testMoving() {
		// AK What should... this test example do !?
		Square tmp; // AK never never never use "temp" as a name! 
		die.send(6); //Red
		die.send(0); //Red
		board.play();
		tmp = player1.getPiece(0).getPosition();
		assertTrue(tmp.equals( board.getNest(Color.RED)));
		die.send(0); //Blue
		tmp = tmp.getNext().getNext();
		board.play();
		die.send(2); //Red
		board.play();
		assertTrue (player2.getPiece(0).getPosition() == null);
		die.send(2); //Blue
		board.play();
		assertTrue (tmp.equals(player1.getPiece(0).getPosition()));
		die.send(54);
		board.play();
		die.send(0);
		board.play();
		assertTrue (board.getGoal().getOccupants(player1.getColor()).size() == 1);
		die.send(6);
		die.send(0);
		die.send(0);
		board.play();
		board.play();
		assertTrue (player1.getPiece(0).getPosition() != null);
	}
}
