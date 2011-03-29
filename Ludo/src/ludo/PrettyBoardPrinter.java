package ludo;

// AK You should use Javadoc to document classes. Javadoc comment start with
// double star. Each class should be document with at least one sentence.

// AK Using format strings, very nice class, well done!

public class PrettyBoardPrinter {
	private Board board;
	private static final String template = ""+
	"                        +---+---+---+                        \n"+
	"                        | 11 | 12 | 13 |                        \n"+
	"    red: %1$d              +---+---+---+     green: %2$d           \n"+
	"                        | 10 # 14 # 19 |                        \n"+
	"                        +---+===+---+                        \n"+
	"                        | 09 # 15 # 20 |                        \n"+
	"                        +---+===+---+                        \n"+
	"                        | 08 # 16 # 21 |                        \n"+
	"                        +---+===+---+                        \n"+
	"                        | 07 # 17 # 22 |                        \n"+
	"                        +---+===+---+                        \n"+
	"                        | 06 # 18 # 23 |                        \n"+
	"+---+---+---+---+---+---+---+===+---+---+---+---+---+---+---+\n"+
	"| 67 | 01 | 02 | 03 | 04 | 05 |           | 24 | 25 | 26 | 27 | 28 | 29 |\n"+
	"+---+===+===+===+===+===+ R:%3$d  G:%4$d  +===+===+===+===+===+---+\n"+
	"| 66 | 68 # 69 # 70 # 71 # 72 #           # 36 # 35 # 34 # 33 # 32 | 30 |\n"+
	"+---+===+===+===+===+===+ B:%5$d  Y:%6$d  +===+===+===+===+===+---+\n"+
	"| 65 | 64 | 63 | 62 | 61 | 60 |           | 41 | 40 | 39 | 38 | 37 | 31 |\n"+
	"+---+---+---+---+---+---+---+===+---+---+---+---+---+---+---+\n"+
	"                        | 59 # 54 # 42 |                        \n"+
	"                        +---+===+---+                        \n"+
	"    blue: %7$d             | 58 # 53 # 43 |   yellow: %8$d            \n"+
	"                        +---+===+---+                        \n"+
	"                        | 57 # 52 # 44 |                        \n"+
	"                        +---+===+---+                        \n"+
	"                        | 56 # 51 # 45 |                        \n"+
	"                        +---+===+---+                        \n"+
	"                        | 55 # 50 # 46 |                        \n"+
	"                        +---+---+---+                        \n"+
	"                        | 49 | 48 | 47 |                        \n"+
	"                        +---+---+---+                        \n";

		
	public PrettyBoardPrinter(Board board){
		this.board = board;
	}
	
	@Override
	synchronized public String toString(){
		// AK You are in need of a NullPlayer, and then Board#getPlayer should
		// return that null player instead of a plain null value.
		Player red,green,blue,yellow,nil;
		nil = new Player(null, null);
		red = board.getPlayer(Color.RED);
		red = (red == null) ? nil : red;
		green = board.getPlayer(Color.GREEN);
		green = (green == null) ? nil : green;
		blue = board.getPlayer(Color.BLUE);
		blue = (blue == null) ? nil : blue;
		yellow = board.getPlayer(Color.YELLOW);
		yellow = (yellow == null) ? nil : yellow;
		
		String output = String.format(template,
				red.getNestCount(),
				green.getNestCount(),
				red.getHomeCount(),
				green.getHomeCount(),
				blue.getHomeCount(),
				yellow.getHomeCount(),
				blue.getNestCount(),
				yellow.getNestCount());
		int n = 1;
		for(Square i : board.getSquares()){
			output = output.replaceFirst(String.format("%1$02d", n++), i.toString());
		}
		if (board.winner() != null){
			output = output + "\n\tAnd the winner is... " + board.winner();
		}
		return output;
	}
}
