package server.serverCore.transfert;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class SendResponce implements Transfert {
	DatagramSocket datagramSocket = null;
	byte[] buf = null;
	DatagramPacket raspuns = null;
	
	int name;
	
	public SendResponce(int name){
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
			buf = new byte[size];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			
			while(true){
				socket.receive(packet);
				String s = new String(
						packet.getData(),
						packet.getOffset(),
						packet.getLength());
				
				int name = Integer.parseInt(s.substring(0, 1));
				
				boolean end = false;
				while(!end){
					socket.receive(packet);
					s = new String(
							packet.getData(),
							packet.getOffset(),
							packet.getLength());
					try{
						if(Integer.parseInt(s.substring(0, 1)) != name){
							sendStop(packet.getAddress(), packet.getPort());
						}
					} catch (Exception e){
						
					}
					
					if(s.contains("end")){
						end = true;
					}
					
				}
				
				//SendPacket
				sendPong(packet.getAddress(), packet.getPort());

			}
		
		} finally {
			socket.leaveGroup(group);
			socket.close();
		
		}
	}

	private void sendStop(InetAddress address, int port) throws IOException {
		buf = new byte[256];
		
		//Construirea raspunsului
		String s = "stop";
		buf = s.getBytes();
		raspuns = new DatagramPacket(buf, buf.length, address, port);
		datagramSocket.send(raspuns);
		
	}

	private void sendPong(InetAddress address, int port) throws IOException {
		buildBuf();
		//Trimite un pachet cu raspunsul catre client
		raspuns = new DatagramPacket(buf, buf.length, address, port);
		datagramSocket.send(raspuns);
		
		
	}
	
	private void buildBuf() {

		buf = new byte[256];
		
		//Construirea raspunsului
		String s = name + "";
		buf = s.getBytes();
		
	}

}