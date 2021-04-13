package p.lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HuanRao {

	/**
	 * �ǵ���Ϊ������
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String processFile() throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
			return br.readLine();
		}
	}

	/**
	 * ִ��һ����Ϊ
	 * 
	 * @param bufferedReaderProcessor
	 * @return
	 * @throws IOException
	 */
	public static String processFile(BufferedReaderProcessor bufferedReaderProcessor) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("D:/tmp/1.txt"))) {
			return bufferedReaderProcessor.process(br);
		}
	}

	public static void main(String[] args) throws IOException {
		String result = HuanRao.processFile();
	}
}

/**
 * ʹ�ú���ʽ�ӿڴ�����Ϊ �ӿ�ֻ����һ�����󷽷�����ʵ���ĺ���������Ϊ (BufferedReader) -> String
 * ��Ӧ������������ǣ�����ֵΪString�������Ĳ���ΪBuffereReader����
 */
@FunctionalInterface
interface BufferedReaderProcessor {
	String process(BufferedReader b) throws IOException;

	public static void main(String[] args) throws IOException {
		// ����lambda���ʽ
		HuanRao.processFile(BufferedReader::readLine);
		HuanRao.processFile((BufferedReader br) -> br.readLine() + br.readLine());
	}
}
