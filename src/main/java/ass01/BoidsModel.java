package ass01;

import java.util.ArrayList;
import java.util.List;

public class BoidsModel {

    public final boolean jpf = true;

    private List<Boid> boids;
    private double separationWeight; 
    private double alignmentWeight; 
    private double cohesionWeight; 
    private final double width;
    private final double height;
    private final double maxSpeed;
    private final double perceptionRadius;
    private final double avoidRadius;

    public BoidsModel(int nboids,  
    						double initialSeparationWeight, 
    						double initialAlignmentWeight, 
    						double initialCohesionWeight,
    						double width, 
    						double height,
    						double maxSpeed,
    						double perceptionRadius,
    						double avoidRadius){
        separationWeight = initialSeparationWeight;
        alignmentWeight = initialAlignmentWeight;
        cohesionWeight = initialCohesionWeight;
        this.width = width;
        this.height = height;
        this.maxSpeed = maxSpeed;
        this.perceptionRadius = perceptionRadius;
        this.avoidRadius = avoidRadius;
        
        initBoids(nboids);

    }

    private void initBoids(int number){
        boids = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            P2d pos;
            V2d vel;
            if(jpf){
                double fakeRandom = (double) (i % 100) / 100;
                pos = new P2d(-width/2 + fakeRandom/2 * width, -height/2 + fakeRandom/3 * height);
                vel = new V2d(fakeRandom/4 * maxSpeed/2 - maxSpeed/4, fakeRandom/5 * maxSpeed/2 - maxSpeed/4);
            }else{
                pos = new P2d(-width/2 + Math.random() * width, -height/2 + Math.random() * height);
                vel = new V2d(Math.random() * maxSpeed/2 - maxSpeed/4, Math.random() * maxSpeed/2 - maxSpeed/4);
            }
            boids.add(new Boid(pos, vel));

        }
    }

    public synchronized void setBoidsNumber(int value){initBoids(value);}
    
    public synchronized List<Boid> getBoids(){
    	return boids;
    }
    
    public double getMinX() {
    	return -width/2;
    }

    public double getMaxX() {
    	return width/2;
    }

    public double getMinY() {
    	return -height/2;
    }

    public double getMaxY() {
    	return height/2;
    }
    
    public double getWidth() {
    	return width;
    }
 
    public double getHeight() {
    	return height;
    }

    public synchronized void setSeparationWeight(double value) {
    	this.separationWeight = value;
    }

    public synchronized void setAlignmentWeight(double value) {
    	this.alignmentWeight = value;
    }

    public synchronized void setCohesionWeight(double value) {
    	this.cohesionWeight = value;
    }

    public synchronized double getSeparationWeight() {
    	return separationWeight;
    }

    public synchronized double getCohesionWeight() {
    	return cohesionWeight;
    }

    public synchronized double getAlignmentWeight() {
    	return alignmentWeight;
    }
    
    public double getMaxSpeed() {
    	return maxSpeed;
    }

    public double getAvoidRadius() {
    	return avoidRadius;
    }

    public double getPerceptionRadius() {
    	return perceptionRadius;
    }
}
