package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class WorkerImpl { 
	Socket socket; 
	
	WorkerImpl (Socket socket) {
		this.socket = socket;
	} 
	
	public void start(){
		DataOutputStream out = null;
		BufferedReader in = null;
		
		try{
			out = new DataOutputStream(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String request;
			request = in.readLine();
			
			System.out.println("Processing request "+ request);
			//printRemoteAddress(request, out);
			
			processHTTPRequest(request, out);
			socket.close();//closes this connection, but not the server;
		}
		catch(IOException ioe){
			System.out.println(ioe);
		}
		
	}

	
	static void processHTTPRequest(String request, DataOutputStream out){
		try {
			System.out.println("Processing request: " + request);
			String response = "";
			if (request.indexOf("GET") > -1){
				response = "HTTP/1.1 200 OK\r\n";
			}else if (request.indexOf("POST") > -1){
				response = "HTTP/1.1 200 OK\r\n";
			}
			out.write(response.getBytes());
			System.out.println("response: " +response +" : "+ response.getBytes());
		}catch (Exception e){
			e.printStackTrace();	
		}
	}
	
	static void printRemoteAddress(String name, PrintStream out){
		try{
			out.println("Looking up " + name + "...");
			InetAddress machine = InetAddress.getByName(name);
			out.println("Host name : "+ ipToText(machine.getAddress()));
		}catch (UnknownHostException ex){
			out.println("Failed in atempt to look up "+ name);
		}
	}
	
	static String ipToText(byte ip[]){  
		StringBuffer result = new StringBuffer();
		for(int i=0;i<ip.length;i++){
			if(i>0)result.append(".");
			result.append(0xff & ip[i]);
		}
		return result.toString();
	}
}
