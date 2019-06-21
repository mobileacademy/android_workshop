package com.example.androidworkshop;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.androidworkshop.utils.Utils;
import com.securepreferences.SecurePreferences;
import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

import static com.example.androidworkshop.utils.Utils.*;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    private static final String APP_SHARED_PREF_NAME = "secure_shared_pref";
    private static final String APP_DATABASE_NAME = "secure_database.db";

    private static MyApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        // here goes anything to init db, libraries ...

        Log.d(TAG, "onCreate fired here!");

        //FabricSdk.init(BuildConfig.fabric_key);
    }

    public static MyApplication getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MyApplication();
        }

        return INSTANCE;
    }

    public SharedPreferences getSecureSharedPref(Context context) {

        return new SecurePreferences(context, getPassword(this, SP_KEYSTORE_PASSWORD), APP_SHARED_PREF_NAME);

    }

    public SQLiteDatabase getEncryptedDatabase(Context context) {
        SQLiteDatabase.loadLibs(context); // load c classes

        File databaseFile = context.getDatabasePath(APP_DATABASE_NAME);
        databaseFile.mkdirs();
//        databaseFile.delete();

        String encPassword = Utils.getPassword(context, DB_KEYSTORE_PASSWORD);
        Log.d("app", "db pass = " + encPassword);
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, encPassword, null);

        database.execSQL("create table if not exists t1(a, b)");

        return database;

    }
}
