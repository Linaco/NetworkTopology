package server.serverCore.transfert;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TransfertSend implements Transfert {
	
	protected long debut;
	protected DatagramSocket socket;
	public boolean resting = true;
	private int name;
	
	public TransfertSend(DatagramSocket socket, int name){
		this.socket = socket;
		this.name = name;
	}

	@Override
	public void run(){
		
		try {
			resting=false;
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
		String toSend = name + new String(new char[size]).replace("\0", " ");
		
		try {
			
			buf = toSend.getBytes();
			packet = new DatagramPacket(buf, buf.length, group, port);		
			int i = 0;
			debut = System.currentTimeMillis();
			while(i < 1000){
				socket.send(packet);
				i++;
			}
			
			String s = "end";
			buf = s.getBytes();
			packet = new DatagramPacket(buf, buf.length, group, port);
			socket.send(packet);
			
			resting = true;
		
		} finally {
			//socket.close();
		
		}
	}

	public void stop() {
		Thread.currentThread().interrupt();
		
	}
	
	public boolean getRest(){
		return resting;
	}

}