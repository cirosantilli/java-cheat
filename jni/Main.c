#include <string.h>
#include <jni.h>
#include "Main.h"

JNIEXPORT jint JNICALL Java_Main_intMethod
(JNIEnv *env, jobject obj, jint i) {
    return i * i;
}

JNIEXPORT jboolean JNICALL Java_Main_booleanMethod
(JNIEnv *env, jobject obj, jboolean boolean) {
    return !boolean;
}

JNIEXPORT jintArray JNICALL Java_Main_intArrayMethod
(JNIEnv *env, jobject obj, jintArray array) {
    int i;
    jsize len = (*env)->GetArrayLength(env, array);
    jint *body = (*env)->GetIntArrayElements(env, array, 0);
    for (i=0; i<len; i++) {
        body[i] += 1;
    }
    (*env)->ReleaseIntArrayElements(env, array, body, 0);
    return array;
}

JNIEXPORT jstring JNICALL Java_Main_stringMethod
(JNIEnv *env, jobject obj, jstring string) {
    const char *str = (*env)->GetStringUTFChars(env, string, 0);
    char cap[128];
    char* cPtr;
    strcpy(cap, str);
    (*env)->ReleaseStringUTFChars(env, string, str);
    for (cPtr = cap; *cPtr != '\0'; cPtr++) {
        *cPtr = toupper(*cPtr);
    }
    return (*env)->NewStringUTF(env, cap);
}
