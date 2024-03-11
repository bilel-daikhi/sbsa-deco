package org.sbsa.deco.utils;

//import com.google.gson.Gson;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Preferences {

//    private static final String CONFIG_FILE = "config.txt";

    private String initialPathFileChooserProductsController;

    private String initialPathFileChooserSettingsController;

    public Preferences() {
        initialPathFileChooserProductsController = System.getProperty("user.home");
        initialPathFileChooserSettingsController = System.getProperty("user.home");
    }

//    public static void initConfig() {
//        Writer writer = null;
//        try {
//            Preferences preferences = new Preferences();
//            Gson gson = new Gson();
//            writer = new FileWriter(CONFIG_FILE);
//            gson.toJson(preferences, writer);
//        } catch (IOException ex) {
//            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                writer.close();
//            } catch (IOException ex) {
//                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

//    public static Preferences getPreferences() {
//        Gson gson = new Gson();
//        Preferences preferences = new Preferences();
//        try {
//            preferences = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
//        } catch (FileNotFoundException ex) {
//            initConfig();
//            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
//            Resources.notification(PropertiesCache.getInstance().getProperty("avertissement"), PropertiesCache.getInstance().getProperty("config.file.not.found"), "warning.png");
//        }
//        return preferences;
//    }

//    public static void writePreferencesToFile(Preferences preferences) {
//        Writer writer = null;
//        try {
//            Gson gson = new Gson();
//            writer = new FileWriter(CONFIG_FILE);
//            gson.toJson(preferences, writer);
//        } catch (IOException ex) {
//            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                writer.close();
//            } catch (IOException ex) {
//                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

    public String getInitialPathFileChooserProductsController() {
        return initialPathFileChooserProductsController;
    }

    public void setInitialPathFileChooserProductsController(String initialPathFileChooserProductsController) {
        this.initialPathFileChooserProductsController = initialPathFileChooserProductsController;
    }

    public String getInitialPathFileChooserSettingsController() {
        return initialPathFileChooserSettingsController;
    }

    public void setInitialPathFileChooserSettingsController(String initialPathFileChooserSettingsController) {
        this.initialPathFileChooserSettingsController = initialPathFileChooserSettingsController;
    }
}
