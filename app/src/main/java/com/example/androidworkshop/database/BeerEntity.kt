package com.example.securesharedpref.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "beer_table")
data class BeerEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "desc")
    val desc: String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String)