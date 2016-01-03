package server.serverCore.perf;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PongReceive extends PingPong implements Runnable {
	
	PingSend pingSend = new PingSend();

	@Override
	public void run() {
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		try {
			
			service.execute(pingSend);
			
			receive();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			service.shutdown();
		}
		
		
		
	}
	
	public Map<Integer, Long> getRuntime() {
		return Runtime;
	}
	
	public void receive() throws IOException {
		
		long time;
		
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
				
				time = System.currentTimeMillis();
				
				//se afiseaza continutul pachetului
				String s = new String(
					packet.getData(),
					packet.getOffset(),
					packet.getLength());
				
				//Updating the runtime map
				updateMap(s, time);
			}
		
		} finally {
			socket.leaveGroup(group);
			socket.close();
		
		}
	}

	private void updateMap(String s, long time) {
		
		String[] parts = s.split("-");
		int name = Integer.parseInt(parts[0]);
		
		Runtime.put(name, (time - pingSend.getDebut()));
		
	}

}