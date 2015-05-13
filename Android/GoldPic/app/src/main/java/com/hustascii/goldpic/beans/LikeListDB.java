package com.hustascii.goldpic.beans;

import android.content.Context;
import android.util.Log;

import com.hustascii.goldpic.Helper.DataBaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei on 15/5/13.
 */
public class LikeListDB {
    private DataBaseHelper databaseHelper = null;

    public LikeListDB(Context ctx) {
        // TODO Auto-generated constructor stub
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(ctx,
                    DataBaseHelper.class);
        }
    }

    public void add(LikeList record) {
        RuntimeExceptionDao<LikeList, Integer> dao = databaseHelper
                .getRuntimeExceptionDao(LikeList.class);
        if(!is_exist(record.getPic_id())){
            dao.create(record);
        }
    }

    public boolean is_exist(String pic_id){
        RuntimeExceptionDao<LikeList, Integer> dao = databaseHelper
                .getRuntimeExceptionDao(LikeList.class);
        int num = 0;
        try {
            num = dao.queryBuilder().where().eq("pic_id",pic_id).query().size();
        }catch (SQLException e){
            Log.v("error",e.getMessage());
        }

        if(0 == num){
            return false;
        }
        return true;
    }


    public List<String> queryall() {
        RuntimeExceptionDao<LikeList, Integer> dao = databaseHelper
                .getRuntimeExceptionDao(LikeList.class);
        List<LikeList> list = dao.queryForAll();
        List<String> s = new ArrayList<>();
        for (LikeList pic:list){
            s.add(pic.getPic_id());
        }
        return s;
    }

    public LikeList getLast() {
        RuntimeExceptionDao<LikeList, Integer> dao = databaseHelper
                .getRuntimeExceptionDao(LikeList.class);
        int size = dao.queryForAll().size();
        if (size == 0)
            return null;

        return dao.queryForAll().get(size-1);

    }


    public void delete(String pic_id){
        if(is_exist(pic_id)){
            RuntimeExceptionDao<LikeList, Integer> dao = databaseHelper
                    .getRuntimeExceptionDao(LikeList.class);
            try{
                DeleteBuilder<LikeList,Integer> delete = dao.deleteBuilder();
                delete.where().eq("pic_id",pic_id);
                delete.delete();
            }catch (SQLException e){
                Log.v("error",e.getMessage());
            }
        }
    }

    public void releaseDataHelper() {
		/*
		 * 释放资源
		 */
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
