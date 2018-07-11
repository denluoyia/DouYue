package com.denluoyia.douyue.manager.db.origin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.denluoyia.douyue.utils.LogUtil;
import com.denluoyia.douyue.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denluoyia
 * Date 2018/06/30
 * DouYue
 */
public class BookSearchHistoryDbOpenHelper extends SQLiteOpenHelper{

    private static final String TAG = BookSearchHistoryDbOpenHelper.class.getSimpleName();

    private static final String DB_NAME = "book_search_history.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "books";


    public static final class BookSearchHistoryDbHelper{
        protected volatile static BookSearchHistoryDbHelper sInstance = null;
        private BookSearchHistoryDbOpenHelper mDbOpenHelper;
        private SQLiteDatabase mDatabase;

        public static BookSearchHistoryDbHelper getsInstance(){
            if (null == sInstance){
                synchronized (BookSearchHistoryDbHelper.class){
                    if (null == sInstance){
                        sInstance = new BookSearchHistoryDbHelper();
                    }
                }
            }
            return sInstance;
        }

        private BookSearchHistoryDbHelper(){
            mDbOpenHelper = new BookSearchHistoryDbOpenHelper(UIUtil.getContext(), DB_NAME, null, DB_VERSION);
            mDatabase = mDbOpenHelper.getWritableDatabase();
        }

        private SQLiteDatabase getSQLiteDatabase() {
            if (null == mDatabase) {
                mDatabase = mDbOpenHelper.getWritableDatabase();
            }
            return mDatabase;
        }


        public long insert(String searchBookName){
            delete(searchBookName); //如果重复 先删除之前的那条数据
            List<String> list = queryAll();
            if (list.size() >= 10){ //最多只存5条历史记录
                delete(list.get(list.size()-1));
            }
            final SQLiteDatabase database = getSQLiteDatabase();
            ContentValues values   = new ContentValues();
            values.put(BooksColumns.BOOK_NAME, searchBookName);
            return database.insert(TABLE_NAME, null, values);
        }

        public long delete(String searchBookName){
            if (!queryByBookName(TABLE_NAME, BooksColumns.BOOK_NAME, searchBookName)) return -1;
            final SQLiteDatabase database = getSQLiteDatabase();
            return database.delete(TABLE_NAME, BooksColumns.BOOK_NAME + "=?", new String[]{searchBookName});
        }

        public List<String> queryAll(){
            Cursor cursor = null;
            SQLiteDatabase database = getSQLiteDatabase();
            List<String> list = new ArrayList<>();
            try {
                cursor = database.query(TABLE_NAME, null, null, null, null, null, BooksColumns._ID + " desc");
                while (cursor.moveToNext()) {
                    String bookName = cursor.getString(cursor.getColumnIndex(BooksColumns.BOOK_NAME));
                    list.add(bookName);
                }
            } catch (Exception e) {
                LogUtil.e(TAG, "queryAll Exception : " + e.getMessage());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return list;
        }

        public boolean queryByBookName(String tableName, String selection, String bookName){
            Cursor cursor = null;
            final SQLiteDatabase database = getSQLiteDatabase();
            try {
                cursor = database.query(TABLE_NAME, null, selection + "=?", new String[]{bookName}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    return true;
                }
            } catch (Exception e) {
                LogUtil.e(TAG, "queryByBookName : " + e.getMessage());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return false;
        }

    }



    private static final class BooksColumns implements BaseColumns {
        static final String BOOK_NAME = "book_name";
    }

    public BookSearchHistoryDbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sb = "CREATE TABLE IF NOT EXISTS [" + TABLE_NAME + "] ([" +
                BooksColumns._ID + "] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,["  +
                        BooksColumns.BOOK_NAME + "] VARCHAR);";
        db.execSQL(sb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
