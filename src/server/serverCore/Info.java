package server.serverCore;

import java.io.Serializable;

public class Info implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5680365664532471516L;
	public int name;
	public String IP;
	public int port;
	
	public int selfPerf;
	

	
	public Info(int name){
		this.name = name;
	}

}
