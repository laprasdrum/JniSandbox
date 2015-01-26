package com.laprasdrum.jnisandbox.lib;

public class NdkManager {
    private static NdkManager sInstance;

    public static NdkManager getInstance() {
        if (sInstance == null) {
            sInstance = new NdkManager();
        }
        return sInstance;
    }

    private NdkManager() {

    }

    /* A native method that is implemented by the
     * 'JniSandbox' native library, which is packaged
     * with this application.
     */
    public native String stringFromJNI();
    public native int addTwoFromJNI(int a, int b);
    public native int countFromJNI();

    /* this is used to load the 'JniSandbox' library on application
     * startup. The library has already been unpacked into
     * /data/data/com.laprasdrum.jnisandbox/lib/libhello-jni.so at
     * installation time by the package manager.
     */
    static {
        System.loadLibrary("JniSandbox");
    }
}
