package p.jni;

public class JNIDemo {
    public native void testHello();
    
    public static void main(String[] args){
        System.loadLibrary("hello");
        JNIDemo jniDemo = new JNIDemo();
        jniDemo.testHello();
    }
}
