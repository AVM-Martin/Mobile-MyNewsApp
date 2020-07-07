package id.my.avmmartin.mynewsapp.utils;

public class Constants {
    public static final String PACKAGE_NAME = "id.my.avmmartin.mynewsapp";

    private static final String API_KEY = "";
    public static final String JSON_URL = "https://newsapi.org/v2/everything?q=%s&apiKey=" + API_KEY;

    public static final String INTENT_IS_SAVED = PACKAGE_NAME + ".SAVED_NEWS";
    public static final String INTENT_NEWS_ID = PACKAGE_NAME + ".INTENT_NEWS_ID";

    // constructor

    private Constants() {
        // none
    }
}
