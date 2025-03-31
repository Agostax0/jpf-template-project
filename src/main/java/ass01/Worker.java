package ass01;

import java.util.ArrayList;
import java.util.List;

public class Worker extends Thread {

    private final int index;

    private BoidsModel model;
    private final int availableProcessors = Runtime.getRuntime().availableProcessors();
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

        for(int i= 0; i < model.getBoids().size(); i++){
            if(i % availableProcessors == index){
                myBoids.add(model.getBoids().get(i));
            }
        }
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
            log("aspetto che finiscano le letture [" + this.readToWriteBarrier.getQueuePosition() + "]");
            this.readToWriteBarrier.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeMyBoids() {
        for(var boid: myBoids) {
            boid.updateVelocity(model);
            boid.updatePos(model);
        }
    }

    private void readMyBoids() {
        for(var boid: myBoids) boid.readNearbyBoids(this.model);
    }

    private void signalEnd() {
        try {
            log("ho finito [" + this.endBarrier.getQueuePosition() + "]");
            this.endBarrier.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void awaitStart() {
        try {
            log("aspetto di partire in coda con [" + this.startBarrier.getQueuePosition() + "]");
            this.startBarrier.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void log(String msg) {
        //System.out.println("[Thread: " + index + " ] " +msg);
    }
}
