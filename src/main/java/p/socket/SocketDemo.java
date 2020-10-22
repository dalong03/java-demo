package p.socket;

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
			socket.connect(new InetSocketAddress("localhost", 8189));
			System.out.println("connect to server successfully");
			new Thread(new ReadRunnable(socket)).start();
			new Thread(new WriteRunnable(socket)).start();
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
			try (InputStream input = socket.getInputStream(); Scanner sc = new Scanner(input);) {
				while (sc.hasNext()) {
					String line = sc.nextLine();
					System.out.println(line);
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

	private static class WriteRunnable implements Runnable {
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
