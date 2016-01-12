package server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.serverCore.heartbeat.*;
import server.serverCore.perf.*;
import server.services.*;

public class Server {
	
	int serverName;
	
	public Server(int name){
		this.serverName = name;
		
		
		List<Runnable> runnables = new ArrayList<Runnable>();
		
		//HBReceive heartBeat = new HBReceive();
		
		//HeartBeat
		//runnables.add(new HBReceive(this));
		//runnables.add(new HBSend(serverName));
		
		//----Performances
		//PingPong
		runnables.add(new PongSend(serverName));
		runnables.add(new ReceivePong());
		
		//SelfPerformance
		//runnables.add(new SelfPerf());
		
		//Services
		//runnables.add(new HelloWorld());
		
		
		//Cette fois on créer un pool de 10 threads maximum
		ExecutorService execute = Executors.newFixedThreadPool(10);
		
		executeRunnables(execute, runnables);
		System.out.println("TEST");
	}
	
	
	public void executeRunnables(final ExecutorService service, List<Runnable> runnables){
        //On exécute chaque "Runnable" de la liste "runnables"
		
		System.out.println("Start : " + serverName);
		for(Runnable r : runnables){

			service.execute(r);
		}
		service.shutdown();
	}
}