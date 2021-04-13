package p.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.compress.utils.Lists;

public class FunctionalInterfaceTest {
	/**
	 * java.util.function.Predicate<T>�ӿڶ�����һ������test�ĳ��󷽷��������ܷ��� T���󣬲�����һ��boolean��
	 * 
	 * @param list
	 * @param p
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> filter(List<T> list, Predicate<T> p) {
		List<T> results = Lists.newArrayList();
		for (T s : list) {
			if (p.test(s)) {
				results.add(s);
			}
		}
		return results;
	}

	/**
	 * java.util.function.Consumer<T>������һ������accept�ĳ��󷽷��������ܷ���T �Ķ���û�з��أ�
	 * void�����������Ҫ��������T�Ķ��󣬲�����ִ��ĳЩ�������Ϳ���ʹ�� ����ӿڡ�
	 *
	 * ���������������һ��forEach����������һ��Integers���б��������� ÿ��Ԫ��ִ�в���
	 *
	 * @param list
	 * @param consumer
	 * @param <T>
	 */
	public static <T> void forEach(List<T> list, Consumer<T> consumer) {
		for (T i : list) {
			consumer.accept(i);
		}
	}

	/**
	 * java.util.function.Function<T, R>�ӿڶ�����һ������apply�ķ�����������һ��
	 * ����T�Ķ��󣬲�����һ������R�Ķ����������Ҫ����һ��Lambda��������������Ϣӳ��
	 * ��������Ϳ���ʹ������ӿڣ�������ȡƻ��������������ַ���ӳ��Ϊ���ĳ��ȣ���
	 *
	 * ������Ĵ����У���������չʾ���������������һ��map�������Խ�һ��String�б�ӳ�䵽����ÿ�� String���ȵ�Integer�б�
	 * 
	 * @param list
	 * @param f
	 * @param <T>
	 * @param <R>
	 * @return
	 */
	public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
		ArrayList<R> result = Lists.newArrayList();
		for (T t : list) {
			result.add(f.apply(t));
		}
		return result;
	}

	public static void main(String[] args) {
		List<String> listOfString = Lists.newArrayList();
		listOfString.add("a");
		Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
		List<String> filter = filter(listOfString, nonEmptyStringPredicate);
		System.out.println(filter);

		forEach(Arrays.asList(1, 2, 3, 4, 5), (System.out::println));

		List<Integer> map = map(Arrays.asList("lambdas", "in", "action"), (String::length));
		System.out.println(map);
	}
}
