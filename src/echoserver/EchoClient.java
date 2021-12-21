package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	static int PORT_NUMBER = 6013;
	private static String server = "";

	public static void main(String[] args) throws IOException {

		if (args.length == 0) {
			server = "127.0.0.1";
		} else {
			server = args[0];
		}
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException {
		try {
			Socket socket = new Socket(server, PORT_NUMBER);
			InputStream userInput = System.in;
			OutputStream userOutput = System.out;

			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			Thread toServer = new Thread(new Runnable() {
				@Override
				public void run() {
					int data;
					try{
						while((data = userInput.read()) != -1)
						{
							outputStream.write(data);
							outputStream.flush();
						}
						socket.shutdownOutput();

					} catch (Exception e){
						e.printStackTrace();
					}
				}
			});

			Thread fromServer = new Thread(new Runnable() {
				@Override
				public void run() {
					int data;
					try{
						while((data = inputStream.read()) != -1)
						{
							userOutput.write(data);
							userOutput.flush();
						}
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			});

			toServer.start();
			fromServer.start();

			toServer.join();
			fromServer.join();


			socket.close();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}