package server.serverCore.heartbeat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class HBSend implements Heartbeat {
	
	int serverName;
	
	public HBSend(int name){
		this.serverName = name;
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

	private void send() throws IOException, InterruptedException {
		InetAddress group = InetAddress.getByName(IP);
		
		byte[] buf = null;
		DatagramPacket packet = null;
		
		//se creeaza un socket pe un port oarecare
		DatagramSocket socket = new DatagramSocket();
		
		try {
			//trimite un pachet catre toti clientii din grup
			String s = setMessage();
			buf = s.getBytes();
			
			packet = new DatagramPacket(buf, buf.length, group, port);
			while(true){
				Thread.sleep(WAIT);
				socket.send(updatePacket(packet));
			}
		
		} finally {
			socket.close();
		
		}
	}

	private String setMessage() {
		
		return serverName + "";
	}

	private DatagramPacket updatePacket(DatagramPacket packet) {
		// TODO Auto-generated method stub
		
		return packet;
		
	}

}