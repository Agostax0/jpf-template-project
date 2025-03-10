package pcd.lab03.cswithlocks_jpf;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestCSWithLocks {
	public static class Counter{
		private int counter = 0;

		public int getCounter(){return this.counter;}

		public void inc(){this.counter++;}

		public void dec(){this.counter--;}
	}

	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		Counter counter = new Counter();
		new MyWorkerB(lock, counter).start();
		new MyWorkerA(lock, counter).start();
	}
}
