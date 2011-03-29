package ludo;

import java.util.Random;

//AK Each class should be document with at least one sentence.

/**
 * 
 * @author group06
 */
public class Die implements IDie{

		private Random rnd;
		private int lastResult;
		
		public Die(long seed){
			lastResult = 0;
			rnd = new Random();
			rnd.setSeed(seed);
		}
		
		public Die() {
			lastResult = 0;
			rnd = new Random();
		}
		
		public int roll(){
			int ret = rnd.nextInt(6)+1;
			lastResult = ret;
			assert ret>0 && ret<7;
			return ret;
		}

		public int getlastResult() {
			// AK mark this method as "used for testing only"
			return lastResult;
		}
}
