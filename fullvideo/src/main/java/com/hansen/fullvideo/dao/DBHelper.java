package com.hansen.fullvideo.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hansen.fullvideo.bean.BigScreenBean;
import com.hansen.fullvideo.bean.BigScreenBeanDao;
import com.hansen.fullvideo.bean.DaoMaster;
import com.hansen.fullvideo.bean.DaoSession;
import com.hansen.fullvideo.bean.SingleViewLocation;
import com.hansen.fullvideo.bean.SingleViewLocationDao;

import java.util.List;

public class DBHelper {

    private static final String DATABASE_NAME = "green_full_video_db.db";
    private static final int DATABASE_VERSION = 1;
    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dao
     */
    private BigScreenBeanDao bigScreenBeanDao;
    private SingleViewLocationDao singleViewLocationDao;

    private static DBHelper mDbController;

    /**
     * 获取单例
     */
    public static DBHelper getInstance(Context context) {
        if (mDbController == null) {
            synchronized (DBHelper.class) {
                if (mDbController == null) {
                    mDbController = new DBHelper(context);
                }
            }
        }
        return mDbController;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public DBHelper(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);
        mDaoMaster = new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        bigScreenBeanDao = mDaoSession.getBigScreenBeanDao();
        singleViewLocationDao = mDaoSession.getSingleViewLocationDao();
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);
        }
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     *
     * @return
     */
    private SQLiteDatabase getWritableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }


    /**
     * 会自动判定是插入还是替换
     *
     * @param bigScreenBean
     */
    public void insertOrReplace(BigScreenBean bigScreenBean) {
        bigScreenBeanDao.insertOrReplace(bigScreenBean);
    }

    /**
     * 存入单个控件的位置信息
     *
     * @param singleViewLocation
     */
    public void insertOrReplaceSingleLocation(SingleViewLocation singleViewLocation) {
        singleViewLocationDao.insertOrReplace(singleViewLocation);
    }

    /**
     * 插入一条记录，表里面要没有与之相同的记录
     *
     * @param bigScreenBean
     */
    public long insert(BigScreenBean bigScreenBean) {
        return bigScreenBeanDao.insert(bigScreenBean);
    }

    /**
     * 更新数据
     *
     * @param bigScreenBean
     */
    public void update(BigScreenBean bigScreenBean) {
        BigScreenBean mOldPersonInfor = bigScreenBeanDao.queryBuilder().where(BigScreenBeanDao.Properties.Id.eq(bigScreenBean.getId())).build().unique();//拿到之前的记录
        if (mOldPersonInfor != null) {

            bigScreenBeanDao.update(mOldPersonInfor);
        }
    }

    /**
     * 按条件查询数据数据坐标
     */
    public List<BigScreenBean> searchByWhere(String wherecluse) {
        List<BigScreenBean> personInfors = (List<BigScreenBean>) bigScreenBeanDao.queryBuilder().where(BigScreenBeanDao.Properties.Type.eq(wherecluse)).build().unique();
        return personInfors;
    }

    /**
     * 查询所有数据
     */
    public List<BigScreenBean> searchAll() {
        List<BigScreenBean> personInfors = bigScreenBeanDao.queryBuilder().list();
        return personInfors;
    }

    /**
     * 根据type类型删除同type的数据
     */
    public void deleteByType(int type) {
        List<BigScreenBean> bigScreenBeans = bigScreenBeanDao.queryBuilder().where(BigScreenBeanDao.Properties.Type.eq(type)).build().list();

        bigScreenBeanDao.deleteInTx(bigScreenBeans);

    }

    /**
     * 查询当前列的数据
     *
     * @param column
     */
    public List<BigScreenBean> searchByColumm(int column) {
        List<BigScreenBean> bigScreenBeans = bigScreenBeanDao.queryBuilder().where(BigScreenBeanDao.Properties.Column.eq(column)).build().list();

        return bigScreenBeans;

    }

    /**
     * 清除某个预案数据
     */
    public void deleteAll() {

       bigScreenBeanDao.deleteAll();

    }
}

