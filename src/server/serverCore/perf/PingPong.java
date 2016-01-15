package server.serverCore.perf;

public interface PingPong extends Runnable{
	
	int port = 4447; //pingpong
	String IP = "230.0.0.1";
	int WAIT = 2000;
	
	int size = 128;

}
