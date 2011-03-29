package ludo;

import java.util.ArrayList;

// AK You should use Javadoc to document classes. Javadoc comment start with
// double star. Each class should be document with at least one sentence.

/*
 * Responsibilities:
 * 		- Decides what Piece to move.
 */
public class Player {
	
	// AK Make all field private! This saves you the pain of debugging non-local
	// access to the private parts of your objects. 
	Color color;
	private ArrayList<Piece> pieces;
	FieldSquare nest;
	
	// AK The same for methods, make the private whenever possible.
	Player(Color color, FieldSquare nest){
		this.color = color;
		this.pieces = new ArrayList<Piece>();
		this.nest = nest;
		for (int i = 0; i<4;i++){
			this.pieces.add(new Piece(this));
		}
	}

	public Piece getPiece(int i) {
		return pieces.get(i);
	}

	public void play(IDie die) {
		int result = die.roll();
		Piece piece = this.choosePiece(result);
		piece.move(result);
		arrange();
		// AK refactor these lines into a private method called #rule06 so 
		// maintainers can find the implementation of the game rules.
		if(result == 6)
			play(die);
	}
	
	private void arrange() {
		// AK I do not parse this method, in strong need of documentation and
		// refactoring. There are at least 5-7 smaller methods buried in here!
		ArrayList<Piece> newList = new ArrayList<Piece>();
		int size = 0;
		for (int i=pieces.size()-1; i>=0; i--) {
			if (pieces.get(i).isHome())
				// AK curly braces, curly braces... :(
				newList.add(pieces.remove(i));
			// AK wrong indentation, use Ctrl-I on your code!	
			}
		for (int i=pieces.size()-1; i>=0; i--) {
			if (pieces.get(i) == null)
				// AK curly braces, curly braces... :(
				newList.add(0, pieces.remove(i));
		} 
		size = pieces.size();
		for (int i=0; i<size; i++) {
			newList.add(i, pieces.remove(0));
		}
		if (!pieces.isEmpty()){
			System.out.println(pieces.toString());
		}
		assert pieces.isEmpty();
		pieces = newList;
	}
	
	private boolean shouldMoveOutOfNest(int dieRoll){
		return dieRoll == 6 && getNestCount() != 0 && (
				// Big Brother id watching you
				// -> and little Brother is watching him.
				!nest.isOccupied() || 
				nest.getOccupants().get(0).getColor() != color);
	}

	// You may want to make this methods more 'intelligent' later.
	// -> We are currently training our SkyNet AI. // AK smile :)
	private Piece choosePiece(int dieRoll) {
		if (shouldMoveOutOfNest(dieRoll)){ 
			return getPieceFromNest();
		}
		else if (getBoardCount() > 0){
			return getBestOnField(dieRoll);
		}
		else {
			return pieces.get(0);
		}
	}
	
	private int getBoardCount() {
		return 4-(getHomeCount() + getNestCount());
	}

	private Piece getPieceFromNest(){
		for (int i = 0; i<4;i++){
			if (pieces.get(i).getPosition() == null){
				return pieces.get(i);
			}
		}
		assert (false);
		return null;
	}
	
	/**
	 * This is where the smarts happen.
	 * @param result int representing how many squares to move.
	 * @return piece that should move.
	 */
	private Piece getBestOnField(int result){
		// AK An AI, awesome! Still try to write shorter methods :)
		// Or maybe even factor it into multiple classes...
		Piece temporaryBest = null;
		int bestWorth = -1000;
		Piece pieceToCheck;			
		for (int i = 0 ; i<4 ; i++) {

			pieceToCheck = pieces.get(i);
			if (pieceToCheck.isHome() || pieceToCheck.getPosition() == null){
				continue;
			}
			int newWorth = worth(pieceToCheck.getHowFar() + result,
					pieceToCheck.getDestination(result));
			assert newWorth > -1000;
			if ( newWorth > bestWorth ){
				temporaryBest = pieceToCheck;
			}
		}
		assert (temporaryBest != null);
		return temporaryBest;
	}
	
	private int worth(int position, Square square){
		int worthSoFar = 0;
		if (square == null)
			return 0;
		if (square.isHome())
			worthSoFar += 100;
		else if (position < 56){
			worthSoFar += position;
		}
		else 
			worthSoFar += (56-position)*9;
		if (square.isOccupied()){
			Piece occupant = square.getOccupants().get(0);
			if (occupant.getColor() == color)
				worthSoFar -= 100;
			else 
				worthSoFar += 40;
		}
		return worthSoFar;
	}

	public Color getColor() {
		return color;
	}

	public int getHomeCount() {
		int count = 0;
		for (Piece p : pieces){
			if (p.isHome())
				count++;
		}
		return count;
	}
	
	public int getNestCount(){
		int count = 0;
		for (Piece p : pieces){
			if (p.getPosition() == null)
				count++;
		}
		return count;
	}

	public Square getNest() {
		return nest;
	}

	public String toString(){
		return color.toString();
	}
}
