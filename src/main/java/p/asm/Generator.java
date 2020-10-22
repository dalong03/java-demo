package p.asm;

import java.io.File;
import java.io.FileOutputStream;
import org.objectweb.asm.*;

public class Generator {
	public static void main(String[] args) throws Exception {
		ClassReader cr = new ClassReader("p.asm.Account");
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
//		cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
		byte[] data = cw.toByteArray();
		File file = new File(System.getProperty("user.dir") + "/target/classes/" + "p/asm/Account.class");
		FileOutputStream fout = new FileOutputStream(file);
		fout.write(data);
		fout.close();
	}
}
