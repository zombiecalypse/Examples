package ludo;

import java.util.Hashtable;

public class FieldSquare extends Square {
	
	Hashtable<Color,Square> next;
	public FieldSquare() {
		super();
		next = new Hashtable<Color,Square>();
	}
	
	public void addNext(Color color, Square newSquare) {
		assert next.size() <= Color.size();
		if (next.containsKey(color))
			next.remove(color);
		next.put(color, newSquare);
		assert next.size() <= Color.size();
	}
	
	@Override
	public Square next(Color color) {
		assert next.containsKey(color);
		return next.get(color);
	}
	
	@Override
	public void enter(Piece p) {
		if (isOccupied())
			drawBack(getOccupants().get(0));
		occupants.addLast(p);
		
	}
	
	@Override
	public boolean isOccupied() {
		return !getOccupants().isEmpty();
	}
	
	public boolean isHome(){
		return false;
	}
	

	private void drawBack(Piece p) {
		p.sendHome();
	}
}

