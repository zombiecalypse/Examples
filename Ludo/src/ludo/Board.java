package ludo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// AK You should use JavaDoc to document classes. JavaDoc comment start with
// double star. 

// AK This class seems to have two responsibilities, to control the game flow
// and to setup the board. I suggest that you refactor the setup responsibility
// into a class of its one. Please refer to the "Factory" design pattern.

/*
 * Responsibilities:
 * 		- Controls Game flow.
 */
public class Board {

	private RunSquare[] start = new RunSquare[Color.size()];
	private HomeSquare goal;
	private IDie die;
	private ArrayList<FieldSquare> squares;
	private LinkedList<Player> players;
	private XSquare homeColumns[] = new XSquare[4];
	
	Board(IDie die, Player players[]) {
		// AK this method is never called! If you make a method private, then
		// Eclipse will warn you about unused method. Avoid default visibility.
		this(die);
		for (int i = 0; i < players.length; i++) {
			this.players.add(players[i]);
		}
	}

	public Player makePlayer(Color c){
		Player player = new Player(c,getNest(c));
		this.players.add(player);
		return player;
	}
	
	
	private void createBoard() {
		for (int i = 0; i < 4; i++){
			start[i] = new RunSquare();
		}
		for (int i = 0; i < 4; i++) {
			// AK Define a method Color#get(index) that does the access and 
			// modulo magic for you.
			Color thisColor = Color.values()[i];
			Color nextColor = Color.values()[(i + 1) % 4];
			// AK currentColor would be a better name, typically this refers 
			// to this object and current to the current value in a loop.
			Square square = getNest(thisColor);
			// AK Casts are a code smell. See that you can get rid of this one
			// by either changing the parameter type of the #add method or the 
			// return type of the #getNest method.
			squares.add((FieldSquare) square);
			for (int j = 0; j < 10; j++) {
				// AK Avoid booleans as method parameters. The "false" below 
				// does not communicate its indent. Rather make two methods
				// of which one is called #addNewRunSquareWithConnection
				square = addNewRunSquareAfter(square, false);
			}
			XSquare xsquare = new XSquare(nextColor);
			squares.add(xsquare);
			homeColumns[nextColor.ordinal()] = xsquare;
			square.setNext(xsquare);
			// AK Again, casts are a code smell...
			square = (FieldSquare) square.next(nextColor);
			square = this.addNewRunSquareAfter(xsquare, false);
			square.setNext(getNest(nextColor));
			square = this.addNewRunSquareAfter(xsquare, true);
			for (int j = 0; j < 4; j++) {
				square = this.addNewRunSquareAfter(square, false);
			}
			square.setNext(this.goal);
		}
	}

	private Square addNewRunSquareAfter(Square afterThis,
			boolean otherConnection) {
		// AK See the comment re boolean parameters above.
		RunSquare newSquare = new RunSquare();
		squares.add(newSquare);
		// AK All if statements must have curly braces!
		if (afterThis == null )
			// AK do not use #println for error messages, use assert!!!
			System.out.println(squares.indexOf(newSquare));
		if (otherConnection) {
			((XSquare) afterThis).setRightNext(newSquare);
		} else {
			afterThis.setNext(newSquare);
		}
		return newSquare;
	}
	
	public Board(){
		this(new Die() );
	}

	public Board(IDie die) {
		// AK Using dependency injection to inject the dice class, well done!
		this.players = new LinkedList<Player>();
		// AK No need to hard-code the size, lists grow as needed. Avoid magic 
		// numbers. Avoid premature optimization.
		this.squares = new ArrayList<FieldSquare>(72);
		this.die = die;
		this.goal = new HomeSquare();
		createBoard();
	}
	
	public RunSquare getNest(Color color) {
		return start[color.ordinal()];
	}
	
	public List<FieldSquare> getSquares(){
		// AK Never return a mutable collection, this exposes your internal
		// state. Rather use: Collections.unmodifieableCollection(squares);
		return squares;
	}
	
	public void play(){
		// AK Assert is a keyword, no a function call.
		assert( !players.isEmpty());
		// AK You seem to need a eternally cycling collection. It would be 
		// write small wrapper class that implements this behavior. The wrapper
		// could eg inherit from Iterable<Player> such that you can use foreach
		// loop to iterate over a never ending stream of players.
		Player player = players.remove(0);
		player.play(die);
		players.addLast(player);
		assert (player != players.get(0));
	}
	
	/**
	 * Renders the board in ASCII graphics on screen.
	 */
	@Override
	public String toString() {
		// AK Printing responsibility is a class of its on, well done!		
		return (new PrettyBoardPrinter(this)).toString();
	}
	
	public XSquare getHomeColumn(Color c) {
		// AK you should mark this method as "used by tests only"
		return this.homeColumns[c.ordinal()];
	}
	
	public Player nextPlayer(){
		// AK you should mark this method as "used by tests only"
		return players.peek();
	}
	
	public HomeSquare getGoal() {
		// AK you should mark this method as "used by tests only"
		return this.goal;
	}
	
	public Player getPlayer(Color c) {
		for (Player i : this.players){
			if (i.getColor() == c)
				return i;
		}
		// AK Avoid null beyond method scope! http://c2.com/cgi/wiki?NoNullBeyondMethodScope
		// I have it that I never return null unless it is document in the method name.
		return null;
	}
	
	public boolean hasEnded(){
		return winner() != null;
	}
	
	public Player winner() {
		for (Player i : players){
			if (i.getHomeCount() == 4){
				return i;
			}
		}
		return null;
	}
}
