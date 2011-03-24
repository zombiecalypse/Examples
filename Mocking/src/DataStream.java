import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class DataStream extends Observable {
	private IServerConnection connection;
	private LinkedList<Observer> observers;
	private PlayerStat stat;
	private Integer readingDefeated;
	

	public DataStream(IServerConnection connection) {
		this.connection = connection;
		this.connection.connectToServer();
		this.observers = new LinkedList<Observer>();
	}
	
	public void receiveComplete() throws ConnectionException {
		connection.requestData();
		stat = new PlayerStat();
		String line;
		do  {
			line = connection.pull();
			interpret(line);
		} while (!hasFinished(line));
		notifyObservers();
	}
	
	private void interpret(String pull) throws ConnectionException {
		if (stat.name == null) {
			stat.name = pull;
		} 
		else if (stat.score == null) {
			stat.score = Integer.valueOf(pull);
		}
		else if (stat.defeated == null) {
			stat.defeated = new LinkedList<String>();
			startReadingDefeated(pull);
		} 
		else if (readingDefeated == null) {
			readingDefeated = Integer.valueOf(pull);
		}
		else if (pull.equals("\\KTHXBAI")) {
			endReadingDefeated();
		}
		else if (readingDefeated > 0) {
			stat.defeated.add(pull);
			readingDefeated--;
		}
		else 
			throw new ConnectionException();
	}

	private void endReadingDefeated() throws ConnectionException {
		if (readingDefeated != 0) 
			throw new ConnectionException();
	}

	private void startReadingDefeated(String pull) {
		readingDefeated = Integer.getInteger(pull);
	}

	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(this, stat);
		}
	}

	public boolean hasFinished(String line2) {
		return line2.equals("\\KTHXBAI");
	}
}
