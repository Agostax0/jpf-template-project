package pcd.lab03.ex01_synchwithsem;

import java.util.Objects;
import java.util.concurrent.Semaphore;

import static gov.nasa.jpf.util.test.TestJPF.assertTrue;
import static pcd.lab03.ex01_synchwithsem.Ponger.PONG;

public class Pinger extends ActiveComponent {

	public static final String PING = "ping";
	private final Semaphore mutex;
    private final TestPingPong.Counter counter;

    public Pinger(Semaphore mutex, TestPingPong.Counter counter) {
        this.mutex = mutex;
        this.counter = counter;
    }
	
	public void run() {
		while (true) {
            try {
                mutex.acquire();
				counter.dec();
				println(PING);
				assertTrue(counter.getCounter() == -1);

			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			finally {
				counter.inc();
				assertTrue(counter.getCounter() == 0);
				mutex.release();
			}

		}
	}
}