package server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.serverCore.Info;
import server.serverCore.ServerCore;
import server.serverCore.heartbeat.*;
import server.serverCore.perf.*;
import server.serverCore.transfert.*;

public class Server {
	
	//local snapshot
	public Info info;
	
	//name of the server to give to other thread
	public int serverName;
	
	//thread taht will be needed to access some data
	HBReceive heartBeat;
	ReceivePong pingPong;
	ReceiveResponce transfert;
	
	public Server(int name){
		this.serverName = name;
		info = new Info(serverName);
		
		
		List<Runnable> runnables = new ArrayList<Runnable>();
		
		heartBeat = new HBReceive();
		
		//HeartBeat
		runnables.add(heartBeat);
		runnables.add(new HBSend(this));
		
		//----Performances
		//PingPong
		pingPong = new ReceivePong();
		runnables.add(pingPong);
		runnables.add(new PongSend(serverName));
		
		//Transfert
		transfert = new ReceiveResponce(serverName);
		runnables.add(transfert);
		runnables.add(new SendResponce(serverName));
		
		//SelfPerformance
		runnables.add(new SelfPerf(this));
		
		//Services
		runnables.add(new ServerCore(this));
		
		
		//Cette fois on créer un pool de 10 threads maximum
		ExecutorService execute = Executors.newFixedThreadPool(20);
		
		executeRunnables(execute, runnables);
		
		
	}
	
	
	private void executeRunnables(final ExecutorService service, List<Runnable> runnables){
        //On exécute chaque "Runnable" de la liste "runnables"
		
		System.out.println("Start : " + serverName);
		for(Runnable r : runnables){

			service.execute(r);
		}
		service.shutdown();
	}
	
	public String display(){
		System.out.println("Display");
		String toDisplay = "";
		
		Map<Integer, Date> map = heartBeat.getRuntime();
		Set<Integer> cles = map.keySet();
		Iterator<Integer> it = cles.iterator();
		while (it.hasNext()){
		   int cle = it.next();
		   toDisplay += cle + " : " + heartBeat.getPerf().get(cle) + "KHz \t" + pingPong.getRuntime().get(cle) + "ms\t " + transfert.getRuntime().get(cle) + "Mo/S\t" + map.get(cle)+"\t" + heartBeat.getPort().get(cle) +"\n";
		}
		return toDisplay;
	}
	
	private void startSnapshot(){
		transfert.startSend();
	}


	public String getSnapshot(String s) {
		// TODO Auto-generated method stub
		if(s.contains("true")){
			System.out.println("StartSnapShot");
			startSnapshot();
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("wait for Display");
		return display();
	}
}