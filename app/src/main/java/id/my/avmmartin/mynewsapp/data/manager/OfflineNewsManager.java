package id.my.avmmartin.mynewsapp.data.manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import id.my.avmmartin.mynewsapp.data.model.News;

public class OfflineNewsManager {
    static final String TABLE_NAME = "news";
    public static final int VERSION = 1;

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String IMAGE_URL = "image";
    private static final String IMAGE_FILEPATH = "filepath";
    private static final String DESCRIPTION = "description";
    private static final String CONTENT = "content";
    private static final String URL = "url";

    public int size() {
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    // create read delete

    public long insertNewNews(News news) {
        return db.insert(TABLE_NAME, null, toContentValues(news));
    }

    public News getNewsByPosition(int position) {
        try (Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null)) {
            cursor.moveToPosition(position);
            return toObject(cursor);
        }
    }

    public News getNewsById(long id) {
        String selection = (
            ID + " = ?"
        );
        String[] selectionArgs = {
            Long.toString(id),
        };

        try (Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)) {
            cursor.moveToFirst();
            return toObject(cursor);
        }
    }

    public void deletePlanById(long id) {
        String whereClause = (
            ID + " = ?"
        );
        String[] whereArgs = {
            Long.toString(id),
        };

        db.delete(TABLE_NAME, whereClause, whereArgs);
    }

    // helper

    private News toObject(Cursor cursor) {
        News news = new News(
            cursor.getInt(cursor.getColumnIndex(ID)),
            cursor.getString(cursor.getColumnIndex(TITLE)),
            cursor.getString(cursor.getColumnIndex(IMAGE_URL)),
            cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
            cursor.getString(cursor.getColumnIndex(CONTENT)),
            cursor.getString(cursor.getColumnIndex(URL))
        );
        news.setOffline(cursor.getString(cursor.getColumnIndex(IMAGE_FILEPATH)));

        return news;
    }

    private ContentValues toContentValues(News news) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE, news.getTitle());
        contentValues.put(IMAGE_URL, news.getImageUrl());
        contentValues.put(IMAGE_FILEPATH, news.getImageFilepath());
        contentValues.put(DESCRIPTION, news.getDescription());
        contentValues.put(CONTENT, news.getContent());
        contentValues.put(URL, news.getUrl());

        return contentValues;
    }

    // overridden method

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT, "
                + IMAGE_URL + " TEXT, "
                + IMAGE_FILEPATH + " TEXT, "
                + DESCRIPTION  + " TEXT, "
                + CONTENT + " TEXT, "
                + URL + " TEXT"
                + ");"
        );
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";"
            );
            onCreate(db);
        }
    }

    // constructor

    private SQLiteDatabase db;

    public OfflineNewsManager(SQLiteDatabase db) {
        this.db = db;
    }
}
