package org.sbsa.deco.utils;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class PropertiesCache {
    private final Locale locale = new Locale("fr", "FR");
    private final ResourceBundle bundle = ResourceBundle.getBundle("Bundle", locale, new CustomResourceBundleControl("UTF-8"));

    private PropertiesCache() {

    }

    public static PropertiesCache getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String getProperty(String key) {
        return bundle.getString(key);
    }

    public Set<String> getAllPropertyNames() {
        return bundle.keySet();
    }

    public boolean containsKey(String key) {
        return bundle.containsKey(key);
    }

    //Bill Pugh Solution for singleton pattern
    private static class LazyHolder {
        private static final PropertiesCache INSTANCE = new PropertiesCache();
    }
}
