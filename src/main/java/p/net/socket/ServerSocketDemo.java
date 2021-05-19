package p.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerSocketDemo {

	public static void main(String[] args) {
		try (ServerSocket ss = new ServerSocket(8080, 1);) {
			System.out.println("server start");

			Executor pool = Executors.newFixedThreadPool(3);
			while (true) {
				Socket socket = ss.accept();
				System.out.println("accept socket" + socket.getRemoteSocketAddress());
				pool.execute(new ReadTask(socket));
//				pool.execute(new WriteTask(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static class WriteTask implements Runnable {
		private Socket socket;

		public WriteTask(Socket socket) {
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

	static class ReadTask implements Runnable {
		private Socket socket;

		public ReadTask(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {

			try (InputStream is = socket.getInputStream();) {
				byte[] byts = new byte[1024];
				int count = is.available();
				if (count > 0) {
					is.read(byts);
					System.out.println(new String(byts));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (socket != null) {
						socket.close();
						System.out.println(socket.getRemoteSocketAddress() + " socket closed");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
