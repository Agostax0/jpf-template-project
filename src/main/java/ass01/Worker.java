package ass01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;

public class Worker extends Thread {

    private final int index;

    private BoidsModel model;
    private final int availableProcessors = 4;
    private List<Boid> myBoids = new ArrayList<>();
    private final MyCyclicBarrier startBarrier;
    private final MyCyclicBarrier endBarrier;

    private final MyCyclicBarrier readToWriteBarrier;

    public Worker(final int index, final MyCyclicBarrier startBarrier, final MyCyclicBarrier endBarrier, final MyCyclicBarrier readToWriteBarrier) {
        this.index = index;
        this.startBarrier = startBarrier;
        this.endBarrier = endBarrier;
        this.readToWriteBarrier = readToWriteBarrier;
    }


    public void setModel(BoidsModel model) {
        this.model = model;

        myBoids.clear();

        myBoids.addAll(model.getBoids());

        log("#BOIDS " + myBoids.size());
    }

    public void run() {

        while (true) {
            awaitStart();

            readMyBoids();

            awaitReadToThenWrite();
            
            writeMyBoids();

            signalEnd();
        }
    }

    private void awaitReadToThenWrite() {
        try {
            log("#READ_WAIT " + readToWriteBarrier.getQueuePosition());
            this.readToWriteBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }finally {
            log("#READ_DONE " + readToWriteBarrier.getQueuePosition());
        }
    }

    private void writeMyBoids() {
        for(var boid: myBoids) {
            //boid.updateVelocity(model);
            //boid.updatePos(model);
        }
    }

    private void readMyBoids() {
        for(var boid: myBoids){/*boid.readNearbyBoids(this.model);*/}
    }

    private void signalEnd() {
        try {
            log("#END_WAIT " + endBarrier.getQueuePosition());
            this.endBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }finally {
            log("#END_DONE " + endBarrier.getQueuePosition());
        }
    }

    private void awaitStart() {
        try {
            log("#START_WAIT " + startBarrier.getQueuePosition());
            this.startBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }finally {
            log("#START_DONE " + startBarrier.getQueuePosition());
        }
    }

    private synchronized void log(String msg){
        System.out.println("[Thread " + this.index + "] " + msg);
    }
}
