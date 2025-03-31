package ass01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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

        for(int i= 0; i < model.getBoids().size(); i++){
            if(i % availableProcessors == index){
                myBoids.add(model.getBoids().get(i));
            }
        }
    }

    public void run() {

        //while (true) {
            awaitStart();

            readMyBoids();

            awaitReadToThenWrite();
            
            writeMyBoids();

            signalEnd();
        //}
    }

    private void awaitReadToThenWrite() {
        try {
            log("aspetto gli altri prima di scrivere");
            this.readToWriteBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }finally {
            log("han finito di leggere");
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
            log("ho finito aspetto gli altri");
            this.endBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }finally {
            log("tutti hanno finito");
        }
    }

    private void awaitStart() {
        try {
            log("aspetto il main");
            this.startBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }finally {
            log("il main mi ha svegliato");
        }
    }

    private synchronized void log(String msg){
        System.out.println("[Thread " + this.index + "] " + msg);
    }
}
