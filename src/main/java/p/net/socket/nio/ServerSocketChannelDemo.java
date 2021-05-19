package p.net.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSocketChannelDemo {
	public static void main(String[] args) {
		try {
			// 创建 ServerSocketChannel 通道，绑定监听端口为 8080
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(8090));
			System.out.println("ServerSocketChannel run");
			Selector selector = Selector.open();

			new Thread(new Acceptor(serverSocketChannel, selector), "Acceptor").start();

			ExecutorService pool = Executors.newFixedThreadPool(2);

			while (true) {
				if (selector.select(3000) == 0) {
					System.out.println(" 等待请求超时 ......");
					continue;
				}
				System.out.println("----- 处理请求 -----");
				// 获取待处理的选择键集合
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> keyIterator = keys.iterator();
				while (keyIterator.hasNext()) {
					SelectionKey selectionKey = keyIterator.next();
					// 如果是读请求，调用对应的读方法
					if (selectionKey.isReadable()) {
						keyIterator.remove();
						pool.execute(new ReadTask(selectionKey));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class Acceptor implements Runnable {
		ServerSocketChannel serverSocketChannel;
		Selector selector;

		Acceptor(ServerSocketChannel serverSocketChannel, Selector selector) {
			this.serverSocketChannel = serverSocketChannel;
			this.selector = selector;
		}

		@Override
		public void run() {
			System.out.println("Acceptor run");
			while (true) {
				try {
					SocketChannel socketChannel = serverSocketChannel.accept();
					System.out.println("accept socket" + socketChannel.getRemoteAddress());
					socketChannel.configureBlocking(false);
					socketChannel.register(selector, SelectionKey.OP_READ);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class ReadTask implements Runnable {
		SelectionKey selectionKey;

		ReadTask(SelectionKey selectionKey) {
			this.selectionKey = selectionKey;
		}

		@Override
		public void run() {
			// 获取套接字通道
			SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
			// 获取缓冲器并进行重置 ,selectionKey.attachment() 为获取选择器键的附加对象
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			byteBuffer.clear();
			// 没有内容则关闭通道
			if (socketChannel.isOpen()) {
				try {
					if (socketChannel.read(byteBuffer) == -1) {
						socketChannel.close();
					} else {
						// 将缓冲器转换为读状态
						byteBuffer.flip();
						// 将缓冲器中接收到的值按 localCharset 格式编码保存
						String receivedRequestData = Charset.forName("UTF-8").newDecoder().decode(byteBuffer).toString();
						System.out.println(socketChannel.getRemoteAddress() + "请求数据: " + receivedRequestData);
						socketChannel.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
