package p.designpattern.proxy;

import java.io.FileOutputStream;
import java.lang.reflect.Method;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import p.designpattern.Cat;

public class CglibProxy {
	public static void main(String[] args) throws Exception {
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\tmp\\cglib");
		Enhancer enhancer = new Enhancer();
		enhancer.setUseCache(false);
		enhancer.setSuperclass(Cat.class);
		enhancer.setCallback(new ConcreteMethodInterceptor());

		Cat cat = (Cat) enhancer.create();
		cat.say();
		byte[] bs = DefaultGeneratorStrategy.INSTANCE.generate(enhancer);
		FileOutputStream fileOutputStream = new FileOutputStream("D:/tmp/cglib/cglib.class");
		fileOutputStream.write(bs);
		fileOutputStream.flush();
		fileOutputStream.close();
	}

	static class ConcreteMethodInterceptor implements MethodInterceptor {
		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			System.out.println("========");
			return proxy.invokeSuper(obj, args);
		}
	}
}
