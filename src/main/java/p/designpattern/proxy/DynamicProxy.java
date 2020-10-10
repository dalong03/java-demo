package p.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import p.designpattern.Animal;
import p.designpattern.Cat;

public class DynamicProxy {
	public static void main(String[] args) {
		Animal animal = new Cat();
		InvocationHandler hander = new InvocationHandler1(animal);
		Animal proxy = (Animal) Proxy.newProxyInstance(Cat.class.getClassLoader(), Cat.class.getInterfaces(), hander);
		proxy.say();
	}

	private static class InvocationHandler1 implements InvocationHandler {
		private Animal animal;

		public InvocationHandler1(Animal animal) {
			this.animal = animal;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return method.invoke(animal, args);
		}

	}

}
