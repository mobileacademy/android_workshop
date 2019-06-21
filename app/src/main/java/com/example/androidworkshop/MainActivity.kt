package com.example.androidworkshop

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate")


        val mySecureShPref = MyApplication.getInstance().getSecureSharedPref(this)
        mySecureShPref.edit().putString("secure_key", "my_secret").commit()

        val savedValue = mySecureShPref.getString("secure_key", "")

        //Toast.makeText(this, "values is = $savedValue", Toast.LENGTH_LONG).show()

        val mySecureDb = MyApplication.getInstance().getEncryptedDatabase(this)
        mySecureDb.execSQL("insert into t1(a, b) values(?, ?)", arrayOf<Any>("second value", "two for the show"))
        mySecureDb.close()
    }
}
