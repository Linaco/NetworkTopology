package server.serverCore.transfert;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReceiveResponce implements Transfert {
	
	//To store data from transfert
	Map<Integer, Long> Runtime = new HashMap<>();
	ExecutorService service = Executors.newSingleThreadExecutor();
	TransfertSend sendTransfert;
	DatagramSocket socket = null;
	int name;
	
	public ReceiveResponce(int name){
		this.name = name;
	}

	@Override
	public void run() {
		
		
		try {
			socket = new DatagramSocket();
			
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
	
	private void receive() throws IOException {
		
		long time;

		
		byte[] buf = null;
		
		try {
			//se asteapta un pachet venit pe adresa grupului
			buf = new byte[size];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			while(true){
				socket.receive(packet);
				String s = new String(
						packet.getData(),
						packet.getOffset(),
						packet.getLength());
				if(s.contains("stop")){
					sendTransfert.stop();
				} else {
				
					time = System.currentTimeMillis();
					
					//se afiseaza continutul pachetului
					s = new String(
						packet.getData(),
						packet.getOffset(),
						packet.getLength());
					
					System.out.println("TIME : " + socket.getLocalPort() + " to " + packet.getPort() + " : " + (time - sendTransfert.getDebut()));
					
					//Updating the runtime map
					updateMap(s, time);
				}
			}
		
		} finally {
			socket.close();
		
		}
	}

	private void updateMap(String s, long time) {
		
		//String[] parts = s.split("-");
		int name = Integer.parseInt(s);
		
		Runtime.put(name, (long) (1000 / (time - sendTransfert.getDebut())));
		
		
	}
	
	public void startSend(){
		sendTransfert  = new TransfertSend(socket, name);
		service.execute(sendTransfert);
	}

}