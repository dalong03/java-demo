package p.designpattern;

import java.io.Serializable;

public class Cat implements Animal, Serializable {

	private static final long serialVersionUID = -946345950604460725L;

	@Override
	public void say() {
		System.out.println("I am a cat");

	}

}
