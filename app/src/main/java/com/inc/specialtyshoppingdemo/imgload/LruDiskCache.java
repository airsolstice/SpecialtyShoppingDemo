package com.inc.specialtyshoppingdemo.imgload;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Holy-Spirit on 2016/5/30.
 */
@SuppressWarnings("ALL")
public class LruDiskCache {

    private File mCacheDir = null;

    public LruDiskCache(Context context) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            mCacheDir = new File(Environment.getExternalStorageDirectory(),"SpecialtyGoods");
//            mCacheDir = Environment.getExternalStorageDirectory();
        } else {


            mCacheDir = new File(context.getCacheDir(),"SpecialtyGoods");;

        }

        if (!mCacheDir.exists()) {
            mCacheDir.mkdirs();
        }

    }

    public File get(String url) {
        return new File(mCacheDir, String.valueOf(url.hashCode()+".png"));
    }

    public void cleanCache() {

        File[] files = mCacheDir.listFiles();

        if (files == null || files.length <= 0) {
            return;
        }

        for (File f : files) {
            f.delete();
        }

    }

}
