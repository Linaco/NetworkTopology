package server.serverCore.heartbeat;

public interface Heartbeat extends Runnable{

	int port = 4446;
	String IP = "230.0.0.1";
	int WAIT = 2000;
}
