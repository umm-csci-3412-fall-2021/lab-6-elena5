package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	// REPLACE WITH PORT PROVIDED BY THE INSTRUCTOR
	public static final int PORT_NUMBER = 6013;
	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		while (true) {
			Socket socket = serverSocket.accept();
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						InputStream fromSocket = socket.getInputStream();
						OutputStream toSocket = socket.getOutputStream();
						int data;
						while ((data = fromSocket.read()) != -1) {
							toSocket.write(data);
							toSocket.flush();
						}
						socket.shutdownOutput();
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}