package p.sql;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MySqlServerDemo {

	public static void main(String[] args) {
		try (ServerSocket ss = new ServerSocket(3307);) {
			System.out.println("server start");

			Executor pool = Executors.newFixedThreadPool(2);
			while (true) {
				Socket socket = ss.accept();
				System.out.println(socket.getRemoteSocketAddress() + " accept socket");
				pool.execute(new EchoRunnable(socket));
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
			try (InputStream input = socket.getInputStream();) {
				byte[] bys = new byte[2048];
				input.read(bys);
				String content = new String(bys);
				System.out.println(content);
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
