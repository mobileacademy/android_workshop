package com.example.securesharedpref.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface BeerDao {

    @Query("SELECT * FROM beer_table")
    fun getAll(): LiveData<List<BeerEntity>>

    @Query("SELECT * FROM beer_table WHERE id IN (:beerIds)")
    fun loadAllByIds(beerIds: List<String>): List<BeerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(beers: BeerEntity)

    @Delete
    fun delete(user: BeerEntity)

    @Query("DELETE FROM beer_table")
    fun deleteAll(): Int

    @Transaction
    fun insertAndDeleteInTransaction(level: BeerEntity) {
        // Anything inside this method runs in a single transaction.
        deleteAll()
        insert(level)
    }
}
