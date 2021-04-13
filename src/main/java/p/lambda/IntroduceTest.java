package p.lambda;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

public class IntroduceTest {
	public static void main(String[] args) {
		List<String> list = Lists.newArrayList();
		list.add("a");
		list.add("bb");

		// 第一步：传递代码
		list.sort(new StringComparator());
		System.out.println(list);
		// 第二步：使用匿名类
		list.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.length() - o2.length();
			}
		});
		System.out.println(list);
		// 第三部：使用lambda表达式
		list.sort((String o1, String o2) -> o1.length() - o2.length());
		System.out.println(list);
		// 因为jvm可以根据lambda上下文来推断lambda表达式的参数类型，所以还可以简化为下面这种写法
		list.sort((o1, o2) -> o1.length() - o2.length());
		System.out.println(list);
		// 又因为java8的comparator接口中有comparing静态方法，它可以像下面这样用：
		list.sort(Comparator.comparing((String -> String.length())));
		System.out.println(list);
		// 第四步：使用方法引用
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
