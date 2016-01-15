package server.serverCore.perf;

import server.Server;

public class SelfPerf implements PingPong {
	
	Server server;
	
	int perf = 0;
	
	public SelfPerf(Server server){
		this.server = server;
	}

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
			int k = 0;
			for(int i = 0; i < 100000000 ; i++){
				k = i * k * i ;
			}
			time = System.currentTimeMillis();
			
			long delta = time - debut;
			if(delta == 0)
				delta = 100000;
			
			perf = (int)(100000 / (delta));
			
			System.out.println("Perf : " + perf);
			
			server.info.selfPerf = perf;
			//System.out.println("SELFPERF : Sur server.info : " + server.info.selfPerf);
			
			Thread.sleep(3000);
			
		}
		
	}
	
	public int getPerf(){
		return perf;
	}

}