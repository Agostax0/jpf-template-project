package pcd.lab03.cswithlocks_jpf;

import java.util.concurrent.locks.Lock;

import static gov.nasa.jpf.util.test.TestJPF.assertFalse;
import static gov.nasa.jpf.util.test.TestJPF.assertTrue;

public class MyWorkerA extends Worker {
	
	private Lock lock;
    private final TestCSWithLocks.Counter counter;

    public MyWorkerA(Lock lock, TestCSWithLocks.Counter counter){
		this.lock = lock;
        this.counter = counter;
    }
	
	public void run(){
		while (true){
		  a1();	
		  try {
			  lock.lockInterruptibly();
			  counter.inc();
			  assertTrue(counter.getCounter() == 1);
			  a2();	
			  a3();
		  } catch (InterruptedException ex) {
		  } finally {
			  counter.dec();
			  assertFalse(counter.getCounter() != 0);
			  lock.unlock();
		  }
		}
	}
	
	protected void a1(){
		// println("a1");
		// wasteRandomTime(100,500);	
	}
	
	protected void a2(){
		// println("a2");
		// wasteRandomTime(300,700);	
	}
	protected void a3(){
		// println("a3");
		// wasteRandomTime(300,700);	
	}
}

