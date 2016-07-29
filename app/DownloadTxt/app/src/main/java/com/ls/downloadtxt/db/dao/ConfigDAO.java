package com.ls.downloadtxt.db.dao;

import android.content.Context;
import android.os.Environment;
import com.ls.downloadtxt.db.entity.Config;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;

public class ConfigDAO {
    private DbManager dbManager;

    private DbManager.DaoConfig getDbConfig(Context context) {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbDir(context.getDatabasePath("test").getParentFile());
        daoConfig.setDbName("config.db");
        daoConfig.setDbVersion(1);
        daoConfig.setAllowTransaction(true);
        return daoConfig;
    }

    private synchronized DbManager ensureDbManager(Context context) {
        if (null == dbManager) {
            dbManager = x.getDb(getDbConfig(context));
        }
        return dbManager;
    }

    public Config get(Context context) {
        Config config = null;
        DbManager dbManager = ensureDbManager(context);
        try {
            config = dbManager.findById(Config.class, 1);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (null == config) {
            config = new Config(1, true, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Environment.DIRECTORY_DOWNLOADS);
            try {
                dbManager.save(config);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return config;
    }

    public boolean save(Context context, Config config) {
        boolean flag = false;
        if (null != config) {
            try {
                ensureDbManager(context).saveOrUpdate(config);
                flag = true;
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
}
