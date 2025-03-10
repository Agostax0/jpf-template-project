package pcd.lab03.ex01_synchwithsem;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.Semaphore;

import static gov.nasa.jpf.util.test.TestJPF.assertTrue;
import static pcd.lab03.ex01_synchwithsem.Pinger.PING;

public class Ponger extends ActiveComponent {

    private final Semaphore mutex;
    private final TestPingPong.Counter counter;

	public static final String PONG = "pong";

    public Ponger(Semaphore mutex, TestPingPong.Counter counter) {
        this.mutex = mutex;
        this.counter = counter;
    }
	
	public void run() {
		while (true) {
			try {
				mutex.acquire();
				counter.inc();
				println(PONG);
				assertTrue(counter.getCounter() == 1);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			finally {
				counter.dec();
				assertTrue(counter.getCounter() == 0);
				mutex.release();
			}
		}
	}
}