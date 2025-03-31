package ass01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class UpdaterMaster {

    private final int availableProcessors = Runtime.getRuntime().availableProcessors();

    private final List<Worker> workers = new ArrayList<>();
    private final CyclicBarrier startBarrier;
    private final CyclicBarrier endBarrier;

    public UpdaterMaster() {

        this.startBarrier = new CyclicBarrier(availableProcessors + 1);
        this.endBarrier = new CyclicBarrier(availableProcessors + 1);

        var readToWriteBarrier = new CyclicBarrier(availableProcessors);

        for (int index = 0; index < availableProcessors; index++) {
            Worker worker = new Worker(index, this.startBarrier, this.endBarrier, readToWriteBarrier);
            workers.add(worker);
            worker.start();
        }
    }

    public void update(BoidsModel model) {
        this.workers.forEach(it -> it.setModel(model));
        try {
            log("via libera ai worker");
            this.startBarrier.await(); //quando il main esegue questo await gli altri thread partono
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        try {
            log("aspetto i worker");
            this.endBarrier.await(); //quando gli altri thread finiscono libero il main
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        log("i worker han finito");
    }

    private synchronized void log(String msg){
        System.out.println("[Thread: main] " + msg);
    }
}
