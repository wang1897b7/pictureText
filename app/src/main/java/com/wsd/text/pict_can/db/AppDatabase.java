package com.wsd.text.pict_can.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Sun on 2016/7/6.
 */
@Database(name=AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    //数据库名称
    public static final String NAME="AppDatabase";

    //数据库版本号
    public static final int VERSION=1;
}
