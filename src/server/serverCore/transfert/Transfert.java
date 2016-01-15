package server.serverCore.transfert;

public interface Transfert extends Runnable{
	
	int port = 4448;
	String IP = "230.0.0.1";
	int WAIT = 2000;
	
	int size = 1024;

}
