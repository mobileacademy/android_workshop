package com.example.androidworkshop.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.securesharedpref.database.BeerDao
import com.example.securesharedpref.database.BeerEntity

@Database(entities = [BeerEntity::class], version = 1)
public abstract class MyRoomDatabase: RoomDatabase() {

    abstract fun beerDao(): BeerDao

    companion object {

        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): MyRoomDatabase {
            return INSTANCE
                ?: synchronized(this) {
                    var instance = INSTANCE
                    if (instance == null) {

                        instance = Room.databaseBuilder(context, MyRoomDatabase::class.java, "app_database.db")
                            .fallbackToDestructiveMigration()//remote sources more reliable
                            .build()


                        INSTANCE = instance
                    }
                    INSTANCE!!
                }
        }
    }
}