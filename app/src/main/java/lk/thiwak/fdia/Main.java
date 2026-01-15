package lk.thiwak.fdia;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class Main implements IXposedHookLoadPackage {
    private static final String TAG = "FDia";

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        Log.d(TAG, "Initiate");

        new MyDialog().patch(lpparam);


    }

}