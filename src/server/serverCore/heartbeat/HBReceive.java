package server.serverCore.heartbeat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import server.serverCore.Info;

//Date date = new Date();

public class HBReceive implements Heartbeat {
	
	public HBReceive(){
		
	}
	
	Map<Integer, Date> Runtime = new HashMap<>();
	Map<Integer, Integer> portServers = new HashMap<>();
	Map<Integer, Integer> perf = new HashMap<>();

	@Override
	public void run() {
		
		try {
			receive();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void receive() throws IOException {
		//adresa IP si portul care reprezinta grupul de clienti
		InetAddress group = InetAddress.getByName(IP);
		
		MulticastSocket socket = null;
		
		byte[] buf = new byte[1024];
		
		try {
			//se alatura grupului aflat la adresa si portul specificate
			socket = new MulticastSocket(port);

			socket.joinGroup(group);
			//se asteapta un pachet venit pe adresa grupului
			//buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			
			while(true){
				socket.receive(packet);
				
				//Get object
				byte[] data = packet.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                Info info = (Info) is.readObject();
				
				//Updating the runtime map
				updateMap(info);
			}
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			socket.leaveGroup(group);
			socket.close();
		
		}
	}

	private void updateMap(Info info) {
		//System.out.println(info.name);
		Runtime.put(info.name, new Date());
		
		//perf
		perf.put(info.name, info.selfPerf);
		
		//port for the user to connect 
		portServers.put(info.name, info.port);
		
	}
	
	public Map<Integer, Date> getRuntime(){
		return Runtime;
	}
	
	public Map<Integer, Integer> getPerf(){
		return perf;
	}

	public Map<Integer, Integer> getPort() {
		// TODO Auto-generated method stub
		return portServers;
	}

}