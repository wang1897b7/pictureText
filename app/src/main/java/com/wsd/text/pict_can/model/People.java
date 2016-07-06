package com.wsd.text.pict_can.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.wsd.text.pict_can.db.AppDatabase;

/**
 * Created by Sun on 2016/7/6.
 */
@ModelContainer
@Table(database = AppDatabase.class)
//必须继承BaseModel，BaseModel包含了基本的数据库操作（save、delete、update、insert、exists）
public class People extends BaseModel {

    //自增id
    @PrimaryKey(autoincrement = true)
    public Long id;

    @Column
    public String name;

    @Column
    public String  password;
}
