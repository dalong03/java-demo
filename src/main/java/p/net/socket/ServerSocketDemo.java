package p.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerSocketDemo {

	public static void main(String[] args) {
		try (ServerSocket ss = new ServerSocket(8189);) {
			System.out.println("server start");

			Executor pool = Executors.newFixedThreadPool(3);
			while (true) {
				Socket socket = ss.accept();
				System.out.println(socket.getRemoteSocketAddress() + " accept socket");
				pool.execute(new EchoRunnable(socket));
				pool.execute(new EchoRunnable2(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static class EchoRunnable implements Runnable {
		private Socket socket;

		public EchoRunnable(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {

			try (OutputStream os = socket.getOutputStream();) {
				os.write("welcome".getBytes());
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
					System.out.println(socket.getRemoteSocketAddress() + " socket closed");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	private static class EchoRunnable2 implements Runnable {
		private Socket socket;

		public EchoRunnable2(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {

			try (InputStream is = socket.getInputStream(); Scanner sc = new Scanner(is);) {
				while (sc.hasNextLine())
					sc.nextLine();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
					System.out.println(socket.getRemoteSocketAddress() + " socket closed");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
