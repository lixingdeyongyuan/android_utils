package com.lx.dbmastertest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * 数据库管理类
 * 1.初始化数据库相关数据
 * 2.创建数据库
 * 3.升级数据库
 * 4.降级数据库
 * Created by haha on 18-2-23.
 */

public class DBMaster {
    private static final int DATABASE_VERSION = 1;

    private Context mContext;
    private String mDBName;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mWriteDB;
    private SQLiteDatabase mReadDB;

    public DBMaster(Context context, String dbName) {
        mContext = context;
        mDBName = dbName;
    }

    private void close() {
        boolean isOpenDB = mWriteDB != null && mReadDB != null && mWriteDB.isOpen() && mReadDB.isOpen();
        if (isOpenDB) {
            mDBHelper.close();
        }
        mDBHelper = null;
        mWriteDB = null;
        mReadDB = null;
    }

    private void initDBVariable() {
        mDBHelper = new DatabaseHelper(mContext, mDBName, null, DATABASE_VERSION);
        mWriteDB = mDBHelper.getWritableDatabase();
        mReadDB = mDBHelper.getReadableDatabase();
    }

    public void createOrOpenDatabase() {
        close();
        initDBVariable();
    }

    private void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        //TODO 创建数据库表
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.beginTransaction();
            //TODO 升级数据库表
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            Log.e("haha", "onUpgrade oldVersion =" + oldVersion + ",newVersion =" + newVersion + ",e.getMessage()=" + e.getMessage());
            rebuildDB(db);
        }
    }

    private void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == newVersion) {
            return;
        }
        rebuildDB(db);
    }

    private void rebuildDB(SQLiteDatabase db) {
        // 1.先删除所有的表
        String sql = "select name from sqlite_master where type='table' order by name";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            ArrayList<String> tableList = new ArrayList<>();
            while (cursor.moveToNext()) {
                String tableName = cursor.getString(0);
                tableList.add(tableName);
            }
            cursor.close();
            for (String tableName : tableList) {
                db.execSQL("drop table if exists " + "'" + tableName + "'");
            }
        }
        // 2.重新再创建所有的表
        onCreate(db);
    }


    public class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            DBMaster.this.onCreate(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            DBMaster.this.onUpgrade(db, oldVersion, newVersion);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            DBMaster.this.onDowngrade(db, oldVersion, newVersion);
        }
    }
}
