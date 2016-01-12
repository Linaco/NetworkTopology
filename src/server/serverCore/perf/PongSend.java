package server.serverCore.perf;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class PongSend extends PingPong implements Runnable {
	DatagramSocket datagramSocket = null;
	byte[] buf = null;
	DatagramPacket raspuns = null;
	
	int name;
	
	public PongSend(int name){
		this.name = name;
	}

	@Override
	public void run() {
		
		try {
			
			datagramSocket = new DatagramSocket();
			
			buildBuf();
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
			socket.joinGroup(group);
			//se asteapta un pachet venit pe adresa grupului
			buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			
			while(true){
				socket.receive(packet);
				
				//SendPacket
				sendPong(packet.getAddress(), packet.getPort());

			}
		
		} finally {
			socket.leaveGroup(group);
			socket.close();
		
		}
	}

	private void sendPong(InetAddress address, int port) throws IOException {
		System.out.println("Ping received on " + name + " from " + address + ":" + port);
				
		//Trimite un pachet cu raspunsul catre client
		raspuns = new DatagramPacket(buf, buf.length, address, port);
		datagramSocket.send(raspuns);
		
		
	}
	
	private void buildBuf() {

		buf = new byte[256];
		
		//Construirea raspunsului
		String s = name + "";
		System.out.println(s);
		buf = s.getBytes();
		
	}

}