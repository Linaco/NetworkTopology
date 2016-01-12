package server.serverCore.perf;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReceivePong extends PingPong implements Runnable {
	
	PingSend pingSend;
	DatagramSocket socket = null;

	@Override
	public void run() {
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		try {
			
			socket = new DatagramSocket();
			pingSend  = new PingSend(socket);
			
			service.execute(pingSend);
			
			Thread.sleep(1000);
			
			receive();
		} catch (IOException | InterruptedException e) {
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
		
		//DatagramSocket socket = null;
		
		byte[] buf = null;
		
		try {
			//se asteapta un pachet venit pe adresa grupului
			buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			System.out.println("---------------------------------------------------------");
			System.out.println("---------------------ReceivePong-------------------------");
			System.out.println("socket : " + socket.getLocalPort());
			System.out.println("---------------------------------------------------------");
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
			socket.close();
		
		}
	}

	private void updateMap(String s, long time) {
		
		//String[] parts = s.split("-");
		System.out.println("String :" + s + "<-");
		int name = Integer.parseInt(s);
		
		Runtime.put(name, (time - pingSend.getDebut()));
		System.out.println("Pong received on from " + name);
		
	}

}