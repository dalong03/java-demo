package p.lambda;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

public class IntroduceTest {
	public static void main(String[] args) {
		List<String> list = Lists.newArrayList();
		list.add("a");
		list.add("bb");

		// ��һ�������ݴ���
		list.sort(new StringComparator());
		System.out.println(list);
		// �ڶ�����ʹ��������
		list.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.length() - o2.length();
			}
		});
		System.out.println(list);
		// ��������ʹ��lambda���ʽ
		list.sort((String o1, String o2) -> o1.length() - o2.length());
		System.out.println(list);
		// ��Ϊjvm���Ը���lambda���������ƶ�lambda���ʽ�Ĳ������ͣ����Ի����Լ�Ϊ��������д��
		list.sort((o1, o2) -> o1.length() - o2.length());
		System.out.println(list);
		// ����Ϊjava8��comparator�ӿ�����comparing��̬�����������������������ã�
		list.sort(Comparator.comparing((String -> String.length())));
		System.out.println(list);
		// ���Ĳ���ʹ�÷�������
		list.sort(Comparator.comparing(String::length));
		System.out.println(list);
	}
}

class StringComparator implements Comparator<String> {
	@Override
	public int compare(String a1, String a2) {
		return a1.length() - a2.length();
	}
}
