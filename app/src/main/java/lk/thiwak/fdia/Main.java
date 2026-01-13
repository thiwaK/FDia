package lk.thiwak.fdia;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Main implements IXposedHookLoadPackage {
    // global class path
    private static final String TARGET_PKG = "net.omobio.dialogsc";
    private static final String CLASS_PATH = TARGET_PKG + ".MainActivity";
    private static final String TAG = "FDia";

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals(TARGET_PKG)) return;

        Log.d(TAG, "Loaded app: " + lpparam.packageName);
        XposedBridge.log("Loaded app: " + lpparam.packageName + " - hooking " + CLASS_PATH);

        // Makes root checker functions empty
        makeFunctionEmpty(lpparam.classLoader, CLASS_PATH, "N1");
        makeFunctionEmpty(lpparam.classLoader, CLASS_PATH, "O1");

        // Override booleans
        hookBooleanReturn(lpparam.classLoader, CLASS_PATH, "P1", true);
        hookBooleanReturn(lpparam.classLoader, CLASS_PATH, "C1", false);
        //hookBooleanReturn(lpparam.classLoader, CLASS_PATH, "B1", false);

    }


    /**
     * Replace all overloads of methodName in the target class with an empty implementation.
     * @param classLoader ClassLoader from the target app (lpparam.classLoader).
     * @param className full class name (e.g., "com.example.MainActivity").
     * @param methodName method name to replace (e.g., "N1" or "onCreate").
     */
    private void makeFunctionEmpty(ClassLoader classLoader, String className, String methodName) {
        try {
            Class<?> cls = XposedHelpers.findClass(className, classLoader);
            Log.d(TAG, "makeFunctionEmpty: found class " + className);
            for (Method m : cls.getDeclaredMethods()) {
                if (!m.getName().equals(methodName)) continue;
                if (Modifier.isNative(m.getModifiers())) {
                    Log.w(TAG, "Skipping native method: " + m);
                    continue;
                }
                final Method targetMethod = m;
                try {
                    XposedBridge.hookMethod(targetMethod, new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                            Class<?> ret = targetMethod.getReturnType();
                            if (ret == void.class) return null;
                            if (ret == boolean.class) return false;
                            if (ret == byte.class) return (byte)0;
                            if (ret == char.class) return (char)0;
                            if (ret == short.class) return (short)0;
                            if (ret == int.class) return 0;
                            if (ret == long.class) return 0L;
                            if (ret == float.class) return 0f;
                            if (ret == double.class) return 0d;
                            return null; // reference types
                        }
                    });
                    Log.d(TAG, "Replaced method: " + targetMethod);
                } catch (Throwable t) {
                    Log.e(TAG, "Failed to replace method " + targetMethod, t);
                }
            }
        } catch (Throwable t) {
            Log.e(TAG, "makeFunctionEmpty: failed to find/replace in " + className, t);
        }
    }


    /**
     * Hook all methods named methodName in className to always return the given boolean value.
     *
     * @param classLoader class loader from the target app (lpparam.classLoader)
     * @param className   full runtime class name, e.g. "com.example.MainActivity"
     * @param methodName  method name to hook, e.g. "P1" or "C1" or "B1"
     * @param returnValue boolean value to force-return (true or false)
     */
    private void hookBooleanReturn(ClassLoader classLoader, String className, String methodName, final boolean returnValue) {
        try {
            Class<?> cls = XposedHelpers.findClass(className, classLoader);
            Log.d(TAG, "hookBooleanReturn: found class " + className + " for method " + methodName);
            // use getMethods() to include inherited/public methods as well
            Method[] methods = cls.getMethods();
            int hookedCount = 0;
            for (final Method m : methods) {
                if (!m.getName().equals(methodName)) continue;
                // only hook methods that return boolean/Boolean
                Class<?> ret = m.getReturnType();
                if (!(ret == boolean.class || ret == Boolean.class)) {
                    Log.w(TAG, "Skipping " + m + " because return type is not boolean");
                    continue;
                }
                if (Modifier.isNative(m.getModifiers())) {
                    Log.w(TAG, "Skipping native method: " + m);
                    continue;
                }
                try {
                    // hook the Method object directly to preserve overload signature
                    XposedBridge.hookMethod(m, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            // Log this/class info
                            Object thiz = param.thisObject;
                            Log.d(TAG, "Calling " + m + " this=" + (thiz != null ? thiz.getClass().getName() : "static"));

                            // Log all arguments
                            Object[] args = param.args;
                            if (args == null || args.length == 0) {
                                Log.d(TAG, "Args: none");
                            } else {
                                StringBuilder sb = new StringBuilder("Args:");
                                for (int i = 0; i < args.length; i++) {
                                    sb.append(" [").append(i).append("]=").append(String.valueOf(args[i]));
                                }
                                Log.d(TAG, sb.toString());
                            }

                            // Prevent original method from executing and return desired boolean
                            param.setResult(returnValue);
                        }
                    });

                    hookedCount++;
                    Log.d(TAG, "Hooked boolean method: " + m);
                } catch (Throwable t) {
                    Log.e(TAG, "Failed to hook method: " + m, t);
                }
            }
            if (hookedCount == 0) {
                Log.w(TAG, "No boolean methods named " + methodName + " found in " + className);
            } else {
                Log.i(TAG, "Total hooked methods: " + hookedCount);
            }
        } catch (Throwable t) {
            Log.e(TAG, "hookBooleanReturn: failed for " + className + "#" + methodName, t);
        }
    }

}