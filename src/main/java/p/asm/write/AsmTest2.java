package p.asm.write;

import java.io.FileOutputStream;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AsmTest2 extends ClassLoader implements Opcodes {

	public static void main(String[] args) throws Exception {
		ClassWriter cw = new ClassWriter(0);
		// 类名
		cw.visit(V1_8, ACC_PUBLIC, "p/asm/write/_766ComLeakInfo", null, "p/asm/write/LeakInfo", null);
		// 构造函数
		MethodVisitor mw = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
		mw.visitVarInsn(ALOAD, 0);
		mw.visitMethodInsn(INVOKESPECIAL, "p/asm/write/LeakInfo", "<init>", "()V");
		mw.visitInsn(RETURN);
		mw.visitMaxs(1, 1);
		mw.visitEnd();

		// 字段
		FieldVisitor fv = cw.visitField(ACC_PUBLIC, "description", "Ljava/lang/String;", null, null);
		fv.visitEnd();

		byte[] code = cw.toByteArray();

		// 将二进制流写到本地磁盘上
		FileOutputStream fos = new FileOutputStream("D:/tmp/asm.class");
		fos.write(code);
		fos.close();

		AsmTest2 loader = new AsmTest2();
		Class<?> clazz = loader.defineClass(null, code, 0, code.length);
		Object beanObj = clazz.getConstructor().newInstance();

		clazz.getField("description").set(beanObj, "Adobe客户信息泄露!");

		String nameString = (String) clazz.getField("description").get(beanObj);
		System.out.println("filed value : " + nameString);
	}
}
