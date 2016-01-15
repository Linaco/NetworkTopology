package server.serverCore.perf;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReceivePong implements PingPong {
	//To store date from ping
	Map<Integer, Long> Runtime = new HashMap<>();
	
	//To control the send
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
	
	private void receive() throws IOException {
		
		long time;
		
		byte[] buf = null;
		
		try {
			//se asteapta un pachet venit pe adresa grupului
			buf = new byte[size];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			while(true){
				socket.receive(packet);
				
				time = System.currentTimeMillis();
				
				//se afiseaza continutul pachetului
				String s = new String(
					packet.getData(),
					packet.getOffset(),
					packet.getLength());
				
				System.out.println("TIME : " + socket.getLocalPort() + " to " + packet.getPort() + " : " + (time - pingSend.getDebut()));
				
				//Updating the runtime map
				updateMap(s, time);
			}
		
		} finally {
			socket.close();
		
		}
	}

	private void updateMap(String s, long time) {
		
		//String[] parts = s.split("-");
		int name = Integer.parseInt(s);
		
		Runtime.put(name, (time - pingSend.getDebut()));
		
		
	}

}