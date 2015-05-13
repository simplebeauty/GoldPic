package com.hustascii.goldpic.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hustascii.goldpic.beans.CollectList;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by wei on 15/5/13.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper{


    private static final String DB_NAME = "GoldPic.db";
    private static final int DB_VSERSION = 1;
    private Dao<CollectList, Integer> collectListDao = null;
    private Context mContext;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VSERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        try {
            TableUtils.createTable(connectionSource, CollectList.class);
            collectListDao = getCollectListDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
                          int arg3) {

    }

    @Override
    public void close() {
        super.close();
        collectListDao = null;
    }

    public Dao<CollectList, Integer> getCollectListDao()
            throws SQLException {
        if (collectListDao == null) {
            collectListDao = getDao(CollectList.class);
        }
        return collectListDao;
    }


}
