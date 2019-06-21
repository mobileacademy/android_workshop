package com.example.androidworkshop.database

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.example.securesharedpref.database.BeerEntity
import timber.log.Timber

class BeerViewModel(application: Application) : AndroidViewModel(application) {

    private var mediatorSourceBeer: MediatorLiveData<List<BeerEntity>> = MediatorLiveData()
    private lateinit var localDatabase: MyRoomDatabase

    init {
        // set by default null, until we get data from the database.
        mediatorSourceBeer.value = null

        localDatabase = MyRoomDatabase.getInstance(application)

        mediatorSourceBeer.addSource(localDatabase.beerDao().getAll(), mediatorSourceBeer::setValue)

    }

    fun getBeerList(): LiveData<List<BeerEntity>> {
        return  mediatorSourceBeer
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("View model cleared!")
    }
}