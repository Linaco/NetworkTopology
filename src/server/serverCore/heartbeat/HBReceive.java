package server.serverCore.heartbeat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//Date date = new Date();

public class HBReceive implements Heartbeat {
	
	public HBReceive(){
		
	}
	
	Map<Integer, Date> Runtime = new HashMap<>();
	Map<Integer, String> Services = new HashMap<>();

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
		
		byte[] buf = null;
		
		try {
			//se alatura grupului aflat la adresa si portul specificate
			socket = new MulticastSocket(port);
			System.out.println("Loopback mode disabled: " +
					socket.getLoopbackMode());
			socket.joinGroup(group);
			//se asteapta un pachet venit pe adresa grupului
			buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			
			while(true){
				socket.receive(packet);
				
				//se afiseaza continutul pachetului
				String s = new String(
					packet.getData(),
					packet.getOffset(),
					packet.getLength());
			
				System.out.println(s);
				
				//Updating the runtime map
				updateMap(s);
			}
		
		} finally {
			socket.leaveGroup(group);
			socket.close();
		
		}
	}

	private void updateMap(String s) {
		
		String[] parts = s.split("-");
		int name = Integer.parseInt(parts[0]);
		
		String services = getServices(parts);
		
		//Services.put(name, Arrays.copyOfRange(parts, 1, (parts.length - 1));
		Services.put(name, services);		
		Runtime.put(name, new Date());
		
	}

	private String getServices(String[] parts) {
		String s = "";
		
		for (int i = 1; i < parts.length; i++){
			s += parts[i] + "; ";
		}
		
		
		return s;
	}

}