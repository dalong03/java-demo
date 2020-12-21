package p.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisProxy {
	private static List<String> servers = new ArrayList<>();
	static {
		servers.add("localhost:6379");
		servers.add("localhost:6380");
	}

	public static void main(String[] args) throws IOException {
		try (ServerSocket ss = new ServerSocket(6378);) {
			System.out.println("server start");

			ExecutorService pool = Executors.newFixedThreadPool(2);
			while (true) {
				Socket s = ss.accept();
				System.out.println("接入客户端 : " + s.getInetAddress());
				pool.execute(new R1(s));
			}
		}

	}

	static class R1 implements Runnable {
		private Socket socket;

		public R1(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try (InputStream is = socket.getInputStream(); OutputStream os = socket.getOutputStream();) {
				byte[] request = new byte[1024];
				is.read(request);
				String req = new String(request);
				System.out.println(req);

				String[] params = req.split("\r\n");
				int length = Integer.valueOf(params[3].split("\\$")[1]);
				int sid = length % servers.size();

				String[] serverInfo = servers.get(sid).split(":");
				System.out.println("选择连到服务器" + serverInfo[0] + ":" + serverInfo[1]);

				try (Socket clientProxy = new Socket(serverInfo[0], Integer.valueOf(serverInfo[1]));) {
					clientProxy.getOutputStream().write(request);

					byte[] response = new byte[1024];
					clientProxy.getInputStream().read(response);

					os.write(response);
					os.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
