package com.trianguloy.urlchecker.modules;

import android.content.Context;

import com.trianguloy.urlchecker.modules.list.ClearUrlModule;
import com.trianguloy.urlchecker.modules.list.DebugModule;
import com.trianguloy.urlchecker.modules.list.HistoryModule;
import com.trianguloy.urlchecker.modules.list.OpenModule;
import com.trianguloy.urlchecker.modules.list.PatternModule;
import com.trianguloy.urlchecker.modules.list.StatusModule;
import com.trianguloy.urlchecker.modules.list.TextInputModule;
import com.trianguloy.urlchecker.modules.list.VirusTotalModule;
import com.trianguloy.urlchecker.utilities.GenericPref;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager of all the modules
 */
public class ModuleManager {

    public final static AModuleData topModule = new TextInputModule();

    public final static List<AModuleData> toggleableModules = new ArrayList<>();

    static {
        // TODO: auto-load with reflection?
        toggleableModules.add(new HistoryModule());
        toggleableModules.add(new StatusModule());
        toggleableModules.add(new VirusTotalModule());
        toggleableModules.add(new ClearUrlModule());
        toggleableModules.add(new PatternModule());
        toggleableModules.add(new DebugModule());
    }

    public final static AModuleData bottomModule = new OpenModule();


    // ------------------- class -------------------

    private static final String PREF_SUFFIX = "_en";

    public static GenericPref.Bool getEnabledPrefOfModule(AModuleData module, Context cntx) {
        final GenericPref.Bool enabledPref = new GenericPref.Bool(module.getId() + PREF_SUFFIX, module.isEnabledByDefault());
        enabledPref.init(cntx);
        return enabledPref;
    }

    /**
     * Returns the uninitialized enabled middle modules
     *
     * @param cntx base context (for the sharedpref)
     * @return the list, may be empty
     */
    public static List<AModuleData> getEnabledMiddleModules(Context cntx) {
        List<AModuleData> enabled = new ArrayList<>();

        // check each module
        for (AModuleData module : toggleableModules) {
            if (getEnabledPrefOfModule(module, cntx).get()) {
                try {
                    // enabled, add
                    enabled.add(module);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return enabled;
    }

}
