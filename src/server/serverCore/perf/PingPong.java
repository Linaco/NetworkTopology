package server.serverCore.perf;

import java.util.HashMap;
import java.util.Map;

import server.Network;

public abstract class PingPong implements Network{
	
	int port = 4447; //pingpong
	String IP = "230.0.0.1";
	int WAIT = 2000;
	
	Map<Integer, Long> Runtime = new HashMap<>();

}
