package lk.thiwak.fdia;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class Main implements IXposedHookLoadPackage {
    private static final String TARGET_PKG = "net.omobio.dialogsc";
    private static final String CLASS_PATH = TARGET_PKG + ".MainActivity";
    private static final String TAG = "FDia";

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

        new MyDialog().patch(lpparam);


    }

}