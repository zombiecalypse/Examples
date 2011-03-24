import java.util.LinkedList;
import java.util.List;

/*
 * Damn you Java for not providing a simple compound type
 * like pythons tuples (1,"a",file).
 */
public class PlayerStat {
	public String name;
	public Integer score;
	public List<String> defeated = new LinkedList<String>();
}
