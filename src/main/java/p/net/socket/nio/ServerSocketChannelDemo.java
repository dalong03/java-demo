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

public class ServerSocketChannelDemo {
	public static void main(String[] args) {
		try {
			// 创建 ServerSocketChannel 通道，绑定监听端口为 8080
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(8090));
			// 设置为非阻塞模式
			serverSocketChannel.configureBlocking(false);
			// 注册选择器 , 设置选择器选择的操作类型
			Selector selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

			while (true) {
				// 等待请求，每次等待阻塞 3s ，超过时间则向下执行，若传入 0 或不传值，则在接收到请求前一直阻塞
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
					try {
						// 如果是连接请求，调用处理器的连接处理方法
						if (selectionKey.isAcceptable()) {
							handleAccept(selectionKey);
						}
						// 如果是读请求，调用对应的读方法
						if (selectionKey.isReadable()) {
							handleRead(selectionKey);
						}
					} catch (IOException e) {
						keyIterator.remove();
						continue;
					}
				}
				// 处理完毕从待处理集合移除该选择键
				keyIterator.remove();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 连接请求处理方法
	 */
	public static void handleAccept(SelectionKey selectionKey) throws IOException {
		// 通过选择器键获取服务器套接字通道，通过 accept() 方法获取套接字通道连接
		SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
		// 设置套接字通道为非阻塞模式
		socketChannel.configureBlocking(false);
		// 为套接字通道注册选择器，该选择器为服务器套接字通道的选择器，即选择到该 SocketChannel 的选择器
		// 设置选择器关心请求为读操作，设置数据读取的缓冲器容量为处理器初始化时候的缓冲器容量
		socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(1024));
	}

	public static void handleRead(SelectionKey selectionKey) throws IOException {
		// 获取套接字通道
		SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
		// 获取缓冲器并进行重置 ,selectionKey.attachment() 为获取选择器键的附加对象
		ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
		byteBuffer.clear();
		// 没有内容则关闭通道
		if (socketChannel.read(byteBuffer) == -1) {
			socketChannel.close();
		} else {
			// 将缓冲器转换为读状态
			byteBuffer.flip();
			// 将缓冲器中接收到的值按 localCharset 格式编码保存
			String receivedRequestData = Charset.forName("UTF-8").newDecoder().decode(byteBuffer).toString();
			System.out.println(" 接收到客户端的请求数据： " + receivedRequestData);
			// 返回响应数据给客户端
			String responseData = " 已接收到你的请求数据，响应数据为： ( 响应数据 )";
			byteBuffer = ByteBuffer.wrap(responseData.getBytes("UTF-8"));
			socketChannel.write(byteBuffer);
			// 关闭通道
			socketChannel.close();
		}
	}
}
