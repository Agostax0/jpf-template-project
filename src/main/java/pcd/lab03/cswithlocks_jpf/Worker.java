package pcd.lab03.cswithlocks_jpf;

public abstract class Worker extends Thread {
		
	public Worker(){
	}

	protected void print(String msg){
		synchronized (System.out){
			System.out.print(msg);
		}
	}

	protected void println(String msg){
		synchronized (System.out){
			System.out.println(msg);
		}
	}

}
