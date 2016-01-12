package server.serverCore.perf;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PingSend extends PingPong implements Runnable {
	
	protected long debut;
	protected DatagramSocket socket;
	
	public PingSend(DatagramSocket socket){
		this.socket = socket;
	}

	@Override
	public void run(){
		
		try {
			send();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public long getDebut(){
		return debut;
	}

	private void send() throws IOException, InterruptedException {
		InetAddress group = InetAddress.getByName(IP);
		
		byte[] buf = null;
		DatagramPacket packet = null;
		
		try {
			//trimite un pachet catre toti clientii din grup
			String s = "";
			buf = s.getBytes();
			
			packet = new DatagramPacket(buf, buf.length, group, port);			
			while(true){
				Thread.sleep(WAIT);
				debut = System.currentTimeMillis();
				socket.send(updatePacket(packet));
			}
		
		} finally {
			socket.close();
		
		}
	}

	private DatagramPacket updatePacket(DatagramPacket packet) {
		// TODO Auto-generated method stub
		
		return packet;
		
	}

}