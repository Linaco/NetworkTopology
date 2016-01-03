package server.serverCore.perf;

public class SelfPerf  extends PingPong implements Runnable {
	
	//int it = 1;
	int perf = 0;

	@Override
	public void run() {
		
		try {
			test();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void test() throws InterruptedException {
		
		long debut;
		long time;
		
		while(true){
			debut = System.currentTimeMillis();
			for(int i = 0; i < 1000000000 ; i++){
			}
			time = System.currentTimeMillis();
			
			perf = (int)(time - debut);
			
			Thread.sleep(3000);
			
		}
		
	}
	
	public int getPerf(){
		return perf;
	}

}