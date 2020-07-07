package id.my.avmmartin.mynewsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.my.avmmartin.mynewsapp.data.manager.OfflineNewsManager;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DB_NAME = "mynewsapp";
    private static final int DB_VERSION = OfflineNewsManager.VERSION;

    private OfflineNewsManager offlineNewsManager;

    // overridden method

    @Override
    public void onCreate(SQLiteDatabase db) {
        OfflineNewsManager.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        OfflineNewsManager.onUpgrade(db, oldVersion, newVersion);
    }

    // constructor

    private SQLiteDatabase db;

    DatabaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        db = getWritableDatabase();

        offlineNewsManager = new OfflineNewsManager(db);
    }

    // destructor

    void endTask() {
        db.close();
    }

    // getter

    OfflineNewsManager getOfflineNewsManager() {
        return offlineNewsManager;
    }
}
