package ludo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// AK You should use Javadoc to document classes. Javadoc comment start with
// double star. Each class should be document with at least one sentence.

public class HomeSquare extends Square {
	
	/** Constructor */
	// AK Avoid pointless comments!

	public HomeSquare() {
		super();
	}

	/** Public Methods */
	// AK This is certainly not meant to be the JavaDoc of the following method.

	@Override
	public boolean isOccupied() {
		return false;
	}
	
	public List<Piece> getOccupants(Color c) {
		// AK For your information: in more modern languages, as eg C#, you could write 
		// return super.getOccupants().where(Piece p => currentPiece.getColor() == c);
		Iterator<Piece> it = super.getOccupants().iterator();
		Piece currentPiece;
		List<Piece> pieces = new ArrayList<Piece>();
		// AK Use foreach loops whenever possible! that is
		// for (Piece each: super.getOccupants()) { ...
		while (it.hasNext()) {
			currentPiece = it.next();
			if (currentPiece.getColor() == c)
				pieces.add(currentPiece);
		}
		return pieces;
	}
	
	// AK If you comment out code, at least tell why you've done so!
//	
//	@Override
//	public Square destination(Color c, int steps) {
//		if (steps == 0)
//			return this;
//		return null;
//	}
	
	/** Protected Methods */
	// AK This is certainly not meant to be the JavaDoc of the following method.

	@Override
	protected Square next(Color c) {
		// AK If you return null you should document it in the method name.
		return null;
	}
	
	/** Set- and get-Methods */
	// AK This is certainly not meant to be the JavaDoc of the following method.

	@Override
	public void setNext(Square square) {
		// AK Wouldn't you rather want to throw an assertion here!
		super.setNext(null);
	}

	@Override
	public	boolean isHome() {
		// AK Using polymorphism, well done! 
		return true;
	}
}
