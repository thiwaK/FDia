package lk.thiwak.fdia;

import android.util.Log;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MyDialog {

    private static final String TARGET_PKG = "net.omobio.dialogsc";
    private static final String MAIN_ACTIVITY_CLASS_PATH = TARGET_PKG + ".MainActivity";
    private static final String TAG = "FDia:" + TARGET_PKG;

    public void patch(final XC_LoadPackage.LoadPackageParam lpparam){
        if (!lpparam.packageName.equals(TARGET_PKG)) return;
        Log.d(TAG, "Loaded app: " + lpparam.packageName);

        Utilities utilities = new Utilities();

        // Makes root checker functions empty
        utilities.makeFunctionEmpty(lpparam.classLoader, MAIN_ACTIVITY_CLASS_PATH, "N1");
        utilities.makeFunctionEmpty(lpparam.classLoader, MAIN_ACTIVITY_CLASS_PATH, "O1");

        // Override booleans
        utilities.hookBooleanReturn(lpparam.classLoader, MAIN_ACTIVITY_CLASS_PATH, "P1", true);
        utilities.hookBooleanReturn(lpparam.classLoader, MAIN_ACTIVITY_CLASS_PATH, "C1", false);
        //hookBooleanReturn(lpparam.classLoader, CLASS_PATH, "B1", false);

    }
}
