package ass01;

import java.util.concurrent.BrokenBarrierException;

public class MyCyclicBarrier {
    private final int parties;
    private int currentParties = 0;
    private String name = null;

    private boolean isBroken = false;

    public MyCyclicBarrier(final int parties){
        this.parties = parties;
    }

    public MyCyclicBarrier(final int parties, final String name){
        this.parties = parties;
        this.name = name;
    }

    public synchronized void await() throws InterruptedException, BrokenBarrierException {
        this.isBroken = false;
        this.currentParties++;

        if(this.currentParties == this.parties){
            this.isBroken = true;
            //tutti hanno raggiunto la barriera
            notifyAll();
            this.currentParties = 0;
        }
        else{
            while(this.currentParties < this.parties && !isBroken){
                //aspetto gli altri
                wait();
            }
        }
    }

    public synchronized int getCurrentParties(){return this.currentParties;}
    public synchronized int getParties(){return this.parties;}
    public synchronized String getQueuePosition(){return this.currentParties + "/" + this.parties;}
}
