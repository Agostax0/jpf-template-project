package pcd.lab03.cswithlocks_jpf;

import java.util.concurrent.locks.Lock;

import static gov.nasa.jpf.util.test.TestJPF.assertFalse;
import static gov.nasa.jpf.util.test.TestJPF.assertTrue;

public class MyWorkerB extends Worker {
	
	private Lock lock;
    private final TestCSWithLocks.Counter counter;

    public MyWorkerB(Lock lock, TestCSWithLocks.Counter counter){
		this.lock = lock;
        this.counter = counter;
    }

	public void run(){
		while (true){
		  try {
			  lock.lockInterruptibly();
			  counter.inc();
			  b1();
			  b2();
			  assertTrue(counter.getCounter() == 1);
		  } catch (InterruptedException ex) {
		  } finally {
			  counter.dec();
			  assertFalse(counter.getCounter() != 0);

			  lock.unlock();
		  }
		  b3();
		}
	}
	
	protected void b1(){
		// println("b1");
		// wasteRandomTime(0,1000);	
	}
	
	protected void b2(){
		// println("b2");
		// wasteRandomTime(100,200);	
	}
	protected void b3(){
		// println("b3");
		// wasteRandomTime(1000,2000);	
	}
}
