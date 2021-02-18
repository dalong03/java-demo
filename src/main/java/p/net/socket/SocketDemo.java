package p.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketDemo {
	public static void main(String[] args) {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress("localhost", 8090));
			System.out.println("connect to server successfully");
			new Thread(new ReadRunnable(socket)).start();
//			new Thread(new WriteRunnable(socket)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class ReadRunnable implements Runnable {
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
				is.read(bys);
				System.out.println(new String(bys));
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (socket != null) {
						socket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
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
			try (InputStream input = socket.getInputStream();
					Scanner sc = new Scanner(System.in);
					OutputStream output = socket.getOutputStream();
					PrintWriter pw = new PrintWriter(output);) {
				while (sc.hasNext()) {
					String line = sc.nextLine();
					System.out.println(line);
					if (line.equals("end")) {
						break;
					}
					pw.write(line + "\n");
					pw.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}
}
