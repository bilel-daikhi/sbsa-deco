package org.sbsa.deco.utils;


import java.util.ResourceBundle;

public enum FxmlView {

    about {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("label.text.About");
        }

        @Override
        public String getFxmlFile() {
            return "/views/about.fxml";
        }
    }, items {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("text.total.items");
        }

        @Override
        public String getFxmlFile() {
            return "/views/items.fxml";
        }
    }  , main {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("dashBoard.title");
        }

        @Override
        public String getFxmlFile() {
            return "/views/main.fxml";
        }
    }, home {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("dashBoard.title");
        }

        @Override
        public String getFxmlFile() {
            return "/views/home.fxml";
        }
    }, statistics {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("statistics");
        }

        @Override
        public String getFxmlFile() {
            return "/views/statistics.fxml";
        }
    }, subCategories {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("title.subcategories");
        }

        @Override
        public String getFxmlFile() {
            return "/views/subCategories.fxml";
        }
    }, categories {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("title.categories");
        }

        @Override
        public String getFxmlFile() {
            return "/views/categories.fxml";
        }
    };

    public static String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle", new CustomResourceBundleControl("UTF-8")).getString(key);
    }

    public abstract String getTitle();

    public abstract String getFxmlFile();

}
