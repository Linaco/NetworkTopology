import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.Server;
import server.serverCore.perf.SelfPerf;


public class Main {
	public static void main(String[] args) {
		
		System.out.println("---------------Main-------------");
		System.out.println();

        new Server(1);
        //new Server(2);
        //new Server(3);

        //System.out.println("---------------END-------------");
    }

}
