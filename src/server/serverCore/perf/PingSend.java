package server.serverCore.perf;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PingSend implements PingPong {
	
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
		
		byte[] buf = new byte[size];
		DatagramPacket packet = null;
		
		try {
			
			packet = new DatagramPacket(buf, buf.length, group, port);			
			while(true){
				Thread.sleep(WAIT);
				debut = System.currentTimeMillis();
				socket.send(packet);
			}
		
		} finally {
			socket.close();
		
		}
	}

}