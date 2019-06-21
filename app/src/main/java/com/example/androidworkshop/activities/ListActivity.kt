package com.example.androidworkshop.activities

import android.app.Application
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.androidworkshop.MyApplication
import com.example.androidworkshop.R
import com.example.androidworkshop.database.MyRoomDatabase
import com.example.androidworkshop.networking.BeerApiController
import com.example.securesharedpref.database.BeerEntity
import org.jetbrains.anko.doAsync
import timber.log.Timber

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
    }


    override fun onResume() {
        super.onResume()



        val db = MyRoomDatabase.getInstance(this)

        doAsync {

            // call to retrofit to get list of beers
            val beerController = BeerApiController()
            beerController.start()

            val listOfBeers = beerController.listOfBeers

            db.beerDao().deleteAll()
            for (i in listOfBeers.indices) {
                val b = listOfBeers[i]
                val entity = BeerEntity(b.id, b.name, b.description, "")
                Timber.d("insert item to db")
                db.beerDao().insert(entity)
            }
        }


        // update listview
    }
}
