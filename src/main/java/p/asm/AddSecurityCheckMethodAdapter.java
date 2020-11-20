package p.asm;

import com.sun.xml.internal.ws.org.objectweb.asm.MethodAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

class AddSecurityCheckMethodAdapter extends MethodAdapter {
	public AddSecurityCheckMethodAdapter(MethodVisitor mv) {
		super(mv);
	}

	public void visitCode() {
		visitMethodInsn(Opcodes.INVOKESTATIC, "p/asm/SecurityChecker", "checkSecurity", "()V");
	}
}
