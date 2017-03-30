package com.bfchengnuo.myapplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by 冰封承諾Andy on 2017/3/19 0019.
 * 内容提供器
 * 其他应用访问使用
 */

public class MyProvider extends ContentProvider {
    private SQLiteDatabase db;
    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.bfchengnuo.myapplication", "User", 0);
        uriMatcher.addURI("com.bfchengnuo.myapplication", "User/#", 1);
        uriMatcher.addURI("com.bfchengnuo.myapplication", "User2", 2);
        uriMatcher.addURI("com.bfchengnuo.myapplication", "User2/#", 3);
    }

    @Override
    public boolean onCreate() {
        // 初始化时调用，一般用于数据库的创建和升级
        // 返回true表示初始化成功，false表示失败
        // 只有当ContentResolver尝试访问的时候才会被初始化
        db = getContext().openOrCreateDatabase("user.db", Context.MODE_PRIVATE, null);
//        db.execSQL("Create table User( _id INTEGER PRIMARY KEY AUTOINCREMENT, USER_NAME TEXT);");
//        db.execSQL("insert into User values(1,'loli')");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // 使用uri来确定是那张表，projection表示查询那些列，后面的是约束，最后一个是排序
        switch (uriMatcher.match(uri)){
            case 0:
                return db.rawQuery("select * from User", null);
            case 1:
                // 获取id的值
                String id = uri.getPathSegments().get(1);
                break;
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // 根据传入的URI来返回相应的MIME类型
        switch (uriMatcher.match(uri)){
            case 0:
                return "vnd.android.cursor.dir/vnd.com.bfchengnuo.myapplication.user";
            case 1:
                return "vnd.android.cursor.item/vnd.com.bfchengnuo.myapplication.user";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // 添加完成后，返回一个用于表示这条记录的URI
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // 返回被删除的行数
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        // 返回的是受影响的行数
        return 0;
    }
}
