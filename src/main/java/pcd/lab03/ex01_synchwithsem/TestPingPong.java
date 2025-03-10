package pcd.lab03.ex01_synchwithsem;

import java.util.Objects;
import java.util.concurrent.Semaphore;

/**
 * Unsynchronized version
 * 
 * @TODO make it sync 
 * @author aricci
 *
 */
public class TestPingPong {
	static Semaphore mutex = new Semaphore(1, true);

	public static class Counter{
		private int counter = 0;

		public int getCounter(){return this.counter;}

		public void inc(){this.counter++;}

		public void dec(){this.counter--;}
	}


	public static void main(String[] args) {
		final Counter counter = new Counter();

		new Pinger(mutex, counter).start();
		new Ponger(mutex, counter).start();
	}

}
