package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client {
	PersonnalAgentFrame frame;
	
	public static void main(String args[]) {
		
		new Client();
	}
	
	public Client(){
		frame = new PersonnalAgentFrame(this);
	}
	
	//fired when the user give an address
	public void init(String s){
		String[] comand = s.split(" ");
		
		String[] parts = comand[0].split(":");
		String IP = parts[0];
		int port = Integer.parseInt(parts[1]);
		
		String force = "false";

		if(comand.length > 1){
			force = "true";
		}
		
		try {
			startSocket(IP, port, force);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//send a message to the server pointed by the address
	private void startSocket(String iP, int port, String message) throws UnknownHostException {
		InetAddress address = InetAddress.getByName(iP);
		DatagramSocket socket = null;
		DatagramPacket packet = null;
		byte[] buf = null;
		try {
			//Construirea unui socket pentru comunicare
			socket = new DatagramSocket();
			//Construirea si trimiterea pachetului cu cererea catre server
			//String s = "John";
			buf = message.getBytes();
			packet = new DatagramPacket(buf, buf.length, address, port);
			socket.send(packet);
			//Se asteapta pachetul cu raspunsul de la server
			buf = new byte[2048];
			packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);
			
			//Afisarea raspunsului
			String raspuns = new String(
					packet.getData(),
					packet.getOffset(),
					packet.getLength());
			frame.setTextLine(raspuns);
			System.out.println(raspuns);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			socket.close();
		}
		
	}
}
