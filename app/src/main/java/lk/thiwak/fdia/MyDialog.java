package lk.thiwak.fdia;

import android.util.Log;


import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MyDialog {

    private static final String TARGET_PKG = "net.omobio.dialogsc";
    private static final String MAIN_ACTIVITY_CLASS_PATH = TARGET_PKG + ".MainActivity";
    private static final String SEC_MGR_CLASS_PATH = TARGET_PKG + ".security.SecurityManager";
    private static final String SEC_VAL_CLASS_PATH = TARGET_PKG + ".security.SecurityValidator";
    private static final String TAG = "FDia:MyDialog";

    private static final boolean PATCH_PRIMARY_METHODS_ONLY = true;

    public void patch(final XC_LoadPackage.LoadPackageParam lpparam){
        if (!lpparam.packageName.equals(TARGET_PKG)) return;
        Log.d(TAG, "Loaded app: " + lpparam.packageName);

        Utilities utilities = new Utilities();

        // === Patch MainActivity === //
        // Makes root checker functions empty
        utilities.makeFunctionEmpty(lpparam.classLoader, MAIN_ACTIVITY_CLASS_PATH, "N1");
        utilities.makeFunctionEmpty(lpparam.classLoader, MAIN_ACTIVITY_CLASS_PATH, "O1");

        // Patch GPlay integrity check
        utilities.hookBooleanReturn(lpparam.classLoader, MAIN_ACTIVITY_CLASS_PATH, "P1", true);

        if(!PATCH_PRIMARY_METHODS_ONLY){
            String[] booleanHookMainAct = {"B1", "C1", "w1", "A1", "u1", "v1", "m1", "l1", "t1", "r1", "z1", "s1", "x1"};
            for (String el : booleanHookMainAct) {
                utilities.hookBooleanReturn(lpparam.classLoader, MAIN_ACTIVITY_CLASS_PATH, el, false);
            }
        }

        // ===  Patch Security Validator === //
        utilities.hookBooleanReturn(lpparam.classLoader, SEC_VAL_CLASS_PATH, "isSecure", true);


        // ===  Patch Security Manager === //
        utilities.hookBooleanReturn(lpparam.classLoader, SEC_MGR_CLASS_PATH, "performComprehensiveSecurityCheck", true);
        utilities.hookBooleanReturn(lpparam.classLoader, SEC_MGR_CLASS_PATH, "verifyAppSignature", true);

        if(!PATCH_PRIMARY_METHODS_ONLY){
            String[] booleanHookSecMgr = {"a", "b", "c", "d", "e", "f", "g", "h", "k", "l", "m", "n",
                    "o", "j", "p", "hasRootManagementApps", "isAdvancedRootDetected",
                    "isCodeTampered", "isDebuggingDetected", "isDeveloperOptionsEnabled",
                    "isEmulatorEnvironment", "isHooked", "isProductionSecure", "isSSLClassModified",
                    "isSSLPinningBypassed", "isSystemTampered", "performRuntimeChecks"};
            for (String el : booleanHookSecMgr) {
                utilities.hookBooleanReturn(lpparam.classLoader, SEC_MGR_CLASS_PATH, el, false);
            }
        }


        // ===  Patch Other Checks === //
        utilities.hookBooleanReturn(lpparam.classLoader, "net.omobio.dialogsc.support.lib.RootInfoCallable", "a", false);
        if(!PATCH_PRIMARY_METHODS_ONLY){
            utilities.hookBooleanReturn(lpparam.classLoader, "net.omobio.dialogsc.tracker.debugger.FindDebugger", "a", false);
            utilities.hookBooleanReturn(lpparam.classLoader, "net.omobio.dialogsc.tracker.debugger.FindDebugger", "b", false);
            utilities.hookBooleanReturn(lpparam.classLoader, "net.omobio.dialogsc.tracker.debugger.FindDebugger", "c", false);
            utilities.hookBooleanReturn(lpparam.classLoader, "net.omobio.dialogsc.tracker.monkey.FindMonkey", "a", false);
            utilities.hookBooleanReturn(lpparam.classLoader, "net.omobio.dialogsc.tracker.taint.FindTaint", "a", false);
            utilities.hookBooleanReturn(lpparam.classLoader, "net.omobio.dialogsc.tracker.taint.FindTaint", "b", false);
            utilities.hookBooleanReturn(lpparam.classLoader, "net.omobio.dialogsc.tracker.taint.FindTaint", "hasTaintClass", false);
        }














//        utilities.logNetworkCommunication(lpparam.classLoader);
    }
}
