package lk.thiwak.fdia;

import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XposedMethodFinder {

    private static final String TAG = "FDia:XposedMethodFinder";

    /**
     * Find methods in classPath whose return type and parameter types match the given names.
     *
     * @param classPath full class name to inspect (e.g., "com.example.MyClass")
     * @param returnTypeName fully qualified return type name (e.g., "java.lang.String" or "int")
     * @param parameterTypeNames array of fully qualified parameter type names in order
     * @param classLoader XC_LoadPackage.LoadPackageParam.ClassLoader to load classes
     * @param includeInherited if true, search superclasses and interfaces
     * @return list of matching Method objects (may be empty)
     */

    public static List<Method> findFunctions(String classPath, String returnTypeName,
                                             String[] parameterTypeNames,
                                             ClassLoader classLoader,
                                             boolean includeInherited
    ) {
        List<Method> results = new ArrayList<>();
        try {
            Class<?> target = Class.forName(classPath, false, classLoader);
            Class<?> returnType = resolveType(returnTypeName, classLoader);
            Class<?>[] paramTypes = resolveTypes(parameterTypeNames, classLoader);

            // Walk class hierarchy if includeInherited true, otherwise only target
            Class<?> current = target;
            while (current != null) {
                Method[] methods = current.getDeclaredMethods();
                for (Method m : methods) {
                    // Optional: skip bridge/synthetic methods
                    if (m.isSynthetic() || m.isBridge()) continue;

                    // Match return type
                    if (!m.getReturnType().equals(returnType)) continue;

                    // Match parameter types length and each type
                    Class<?>[] mParams = m.getParameterTypes();
                    if (mParams.length != paramTypes.length) continue;

                    boolean paramsMatch = true;
                    for (int i = 0; i < mParams.length; i++) {
                        if (!mParams[i].equals(paramTypes[i])) {
                            paramsMatch = false;
                            break;
                        }
                    }
                    if (!paramsMatch) continue;

                    results.add(m);
                }
                if (!includeInherited) break;
                // interfaces also can be searched
                for (Class<?> iface : current.getInterfaces()) {
                    // search each interface's declared methods
                    for (Method m : iface.getDeclaredMethods()) {
                        if (m.isSynthetic() || m.isBridge()) continue;
                        if (!m.getReturnType().equals(returnType)) continue;
                        Class<?>[] mParams = m.getParameterTypes();
                        if (mParams.length != paramTypes.length) continue;
                        boolean paramsMatch = true;
                        for (int i = 0; i < mParams.length; i++) {
                            if (!mParams[i].equals(paramTypes[i])) {
                                paramsMatch = false;
                                break;
                            }
                        }
                        if (paramsMatch) results.add(m);
                    }
                }
                current = current.getSuperclass();
            }
        } catch (ClassNotFoundException e) {
            Log.d(TAG,"XposedMethodFinder: class not found: " + classPath + " -> " + e);
        } catch (Throwable t) {
            Log.d(TAG,"XposedMethodFinder: error: " + t);
        }
        return results;
    }

    private static Class<?> resolveType(String name, ClassLoader classLoader) throws ClassNotFoundException {
        if (name == null || name.isEmpty()) return void.class;
        switch (name) {
            case "boolean": return boolean.class;
            case "byte":    return byte.class;
            case "char":    return char.class;
            case "short":   return short.class;
            case "int":     return int.class;
            case "long":    return long.class;
            case "float":   return float.class;
            case "double":  return double.class;
            case "void":    return void.class;
            default:
                // handle array types like "java.lang.String[]" or "int[]"
                if (name.endsWith("[]")) {
                    String elem = name.substring(0, name.length() - 2);
                    Class<?> elemClass = resolveType(elem, classLoader);
                    // build array class
                    return java.lang.reflect.Array.newInstance(elemClass, 0).getClass();
                }
                return Class.forName(name, false, classLoader);
        }
    }

    private static Class<?>[] resolveTypes(String[] names, ClassLoader classLoader) throws ClassNotFoundException {
        if (names == null) return new Class<?>[0];
        Class<?>[] types = new Class<?>[names.length];
        for (int i = 0; i < names.length; i++) types[i] = resolveType(names[i], classLoader);
        return types;
    }
}
