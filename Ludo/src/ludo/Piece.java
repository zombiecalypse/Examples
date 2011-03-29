package ludo;

// AK You should use Javadoc to document classes. Javadoc comment start with
// double star. Each class should be document with at least one sentence.

/*
 * Responsibilities:
 * 		- moves over the Squares.
 */
public class Piece {
	private Square position;
	// AK This is redundant, see comment below.
	private boolean isHome;
	private Player player;
	private int howFar;

	public Piece(Player player) {
		this.position = null;
		this.isHome = false;
		this.player = player;
		this.howFar = 0;
	}

	public Square getPosition() {
		return position;
	}

	public boolean isHome() {
		// AK redundent state, just use: return getPosition().isHome();
		return isHome;
	}
	
	public Color getColor(){
		return this.player.getColor();
	}

	public void enter(Square square) {
		if (square != null) {
			this.position = square;
			square.enter(this);
			if (square.isHome()){
				// AK redundant, see above.
				isHome = true;
			}
		}
		else {
			// AK Strange code, this does not make sense to the reader! You
			// should never violate the principle of least surprose.
			this.position = null;
		}
	}

	public void move(int result) {
		Square newPosition = getDestination(result);
		// AK You are clearly in the need of a NullSquare class! Please refer
		// to the "Null Object" pattern.
		if (newPosition != null) {
			if (position != null)
				position.leave();
			this.enter(newPosition);
			howFar += result;
			assert ((isHome || position == null) || position.isOccupied());
		}
	}
	
	public Square getDestination(int result){
		if (this.position == null){
			// AK Strange place to implement Rule #5 ... rather implement a NestSquare.
			if (result == 6)
				return this.player.getNest();
			else
				return null;
		}
		return position.destination(getColor(), result);
	}
	
	public String toString() {
		// AK rather use: return player.getColor().toString().substring(0,1);
		switch(player.getColor()) {
		case BLUE :
			return "B";
		case RED :
			return "R";
		case GREEN :
			return "G";
		case YELLOW :
			return "Y";
		default :
			return "0";
		}
	}

	public void sendHome() {
		// AK Either put one-line if one actual line, or use curly braces!
		if (this.position != null)
			this.position.leave();
		this.enter(null);
		howFar = 0;
	}
	
	public int getHowFar(){
		return howFar;
	}
}
