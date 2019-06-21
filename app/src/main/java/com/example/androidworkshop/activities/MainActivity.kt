package com.example.androidworkshop.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.androidworkshop.utils.DownloadCompletedEvent
import com.example.androidworkshop.MyApplication
import com.example.androidworkshop.services.MyIntentService
import com.example.androidworkshop.R
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

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

        startServiceBtn.setOnClickListener {
            //startService(Intent(this, MyIntentService::class.java))

            startActivity(Intent(this, ListActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public fun onEvent(event: DownloadCompletedEvent) {

        // handle the event on the main thread
        Timber.d("on event happens here")

        Toast.makeText(this, "Event here", Toast.LENGTH_LONG).show()
    }
}
