package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	public static boolean controlSwitch = true;
	

	public static void main(String a[])throws IOException{
		int requestsToQueue = 6;
		int port = 5000;
		Socket socket;
		

		ServerSocket serverSocket = new ServerSocket(port, requestsToQueue);

		System.out.println("Server starting up.\n");
		
		while(controlSwitch){
			
			socket = serverSocket.accept();
			new WorkerImpl(socket).start(); //thread if need to or run();
			
		}
		serverSocket.close();
	}

}
