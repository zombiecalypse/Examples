package ludo;
import java.util.LinkedList;
import java.util.List;

// AK You should use Javadoc to document classes. Javadoc comment start with
// double star. Each class should be document with at least one sentence.

// AK You list responsibilities, very well done!

/*
 * Responsibilities: 
 * 		- Provides next Square, given the color of the Piece
 * 		- Knows if and by whom it is occupied.
 */
public abstract class Square {
	
	/** Private Attributes */
	// AK This is certainly not meant to be the JavaDoc of the following field.

	protected LinkedList<Piece> occupants;
	private Square next;

	/** Constructor */
	// AK Avoid pointless comments!

	public Square() {
		occupants = new LinkedList<Piece>();
		next = null;
	}
	
	/** Abstract Methods */
	// AK This is certainly not meant to be the JavaDoc of the following method.

	abstract public boolean isOccupied();
	abstract protected Square next(Color color);
	abstract public boolean isHome();
	
	/** Public Methods */
	// AK This is certainly not meant to be the JavaDoc of the following method.

	public void enter(Piece p){
		occupants.addLast(p);
	}
	

	public Square destination(Color c, int steps){
		if (steps == 0){
			return this;
		}
		if (next(c) != null){
			return next(c).destination(c, steps-1);
		}
		// AK Avoid null to communicate failure. Rather use "Design By Contract"
		// that is have two methods, a #hasDestination that return a boolean and
		// then fail in this methods with an assertion if there is no destination.
		// Clients are then supposed to check the boolean method first.
		return null;
	}
	
	public void leave() {
		assert (occupants.size()!=0);
		occupants.remove(0);
	}
	
	public void addOccupant(Piece p) {
		occupants.addLast(p);
	}
	
	/** Set- and get-Methods */
	// AK This is certainly not meant to be the JavaDoc of the following method.

	public void setNext(Square s) {
		next = s;
	}
	
	public Square getNext() {
		return next;
	}
	
	public void setOccupants(List<Piece> list) {
		// AK Shouldn't that rather be called #resetOccupants !?
		occupants.clear();
		occupants.addAll(list);
	}
	
	public LinkedList<Piece> getOccupants() {
		// AK Never never never return a naked collection, this exposes the
		// internal state of your class. Clients might remove elements with
		// your consent. 
		// Rather use: return Collections.unmodifiableCollection(occupants);
		return occupants;
	}
	
	/** toString */
	// AK This is certainly not meant to be the JavaDoc of the following method.

	public String toString(){
		// AK Never never never call a variable "temp".
		// AK Use StringBuilder to build strings...
		String temp = "";
		// AK A foreach loop, at last, well done! :)
		for (Piece i : occupants){
			temp += i.toString();
		}
		if (temp.length() < 1)
			temp += " ";
		// AK What the heck is this !?
		return temp +"";
	}
}
