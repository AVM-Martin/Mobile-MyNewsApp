package id.my.avmmartin.mynewsapp.data;

import android.content.Context;

import id.my.avmmartin.mynewsapp.data.model.News;
import id.my.avmmartin.mynewsapp.utils.OnlineDataLoaderUtils;

public class DataManager {
    private DatabaseManager databaseManager;
    private VolleyManager volleyManager;

    // browse online news

    public void fetchNewsByQuery(final OnlineDataLoaderUtils loaderUtils, String query) {
         volleyManager.getOnlineNewsManager().fetchOnlineData(loaderUtils, query);
    }

    public int getOnlineNewsSize() {
        return volleyManager.getOnlineNewsManager().size();
    }

    public News getOnlineNewsByPosition(int position) {
        return volleyManager.getOnlineNewsManager().get(position);
    }

    public News getOnlineNewsById(int id) {
        // since position == id
        return volleyManager.getOnlineNewsManager().get(id);
    }

    // browse saved news

    public int getSavedNewsSize() {
        return databaseManager.getOfflineNewsManager().size();
    }

    public News getSavedNewsByPosition(int position) {
        return databaseManager.getOfflineNewsManager().getNewsByPosition(position);
    }

    public News getSavedNewsById(long id) {
        return databaseManager.getOfflineNewsManager().getNewsById(id);
    }

    // save news

    public long saveNews(News news) {
        return databaseManager.getOfflineNewsManager().insertNewNews(news);
    }

    public void unsaveNewsById(long id) {
        databaseManager.getOfflineNewsManager().deletePlanById(id);
    }

    // singleton constructor

    private static DataManager instance = null;

    public static DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context.getApplicationContext());
        }

        return instance;
    }

    private DataManager(Context context) {
        databaseManager = new DatabaseManager(context);
        volleyManager = new VolleyManager(context);
    }

    // destructor

    public void endTask() {
        databaseManager.endTask();
        volleyManager.endTask();

        instance = null;
    }
}
