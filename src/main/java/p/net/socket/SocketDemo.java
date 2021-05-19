package p.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketDemo {
	public static void main(String[] args) {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress("localhost", 8090));
			socket.setKeepAlive(true);
			System.out.println("connect to server successfully");
//			new Thread(new ReadRunnable(socket)).start();
			new Thread(new WriteRunnable(socket)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class ReadRunnable implements Runnable {
		private Socket socket;

		public ReadRunnable(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {

			try (InputStream is = socket.getInputStream();) {
				byte[] bys = new byte[1024];
				int avi = is.available();
				System.out.println(avi);
				if(avi > 0) {
					is.read(bys);
					System.out.println(new String(bys));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
//				try {
//					if (socket != null) {
//						socket.close();
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}

		}

	}

	static class WriteRunnable implements Runnable {
		private Socket socket;

		public WriteRunnable(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try (OutputStream output = socket.getOutputStream(); 
					PrintWriter pw = new PrintWriter(output);) {
				pw.write("hello\n");
				pw.flush();
				System.out.println("done");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
//				try {
//					socket.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}

		}

	}
}
