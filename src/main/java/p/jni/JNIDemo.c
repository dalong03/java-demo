#include "jni.h"
#include "p_jni_JNIDemo.h"

 
JNIEXPORT void JNICALL
Java_p_jni_JNIDemo_testHello(JNIEnv *env, jobject obj)
{
    printf("Helloworld!\n");
}