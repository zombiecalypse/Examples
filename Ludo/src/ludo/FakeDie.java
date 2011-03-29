package ludo;

import java.util.LinkedList;
import java.util.Queue;

// AK You should use Javadoc to document classes. Javadoc comment start with
// double star. Each class should be document with at least one sentence.

// AK Using a scriptable dice for testing, very well done!

// AK Proper terminology would be MockDie or ScriptableDie...

public class FakeDie implements IDie {
	
	Queue<Integer> queue;
	
	public FakeDie(){
		queue = new LinkedList<Integer>();
	}
	
	public FakeDie send(int i){
		// AK Return itself is called a "fluent interface" but alas you seem not 
		// to make use of it: dice.add(1).add(2).add(3); Also nice would be a
		// vararg method #send(int... rolls) that's used: dice.send(1,2,3); 
		queue.add(i);
		return this;
	}
	
	public int roll() {
		// AK Outch, very dangerous code. Poll returns null on an empty queue 
		// which will result in a null pointer exception when Java tries to unbox
		// the integer into an int. Most programmers will never be able to drill 
		// down the cause of this bug.
		return queue.poll();
	}

}
