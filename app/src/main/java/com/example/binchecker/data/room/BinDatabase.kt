package com.example.binchecker.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.binchecker.model.BinModel

@Database(entities = [BinModel::class], version = 9)
abstract class BinDatabase : RoomDatabase() {

    abstract fun getBinDao() : BinDao

    companion object{
        @Volatile
        private var database: BinDatabase ?= null

        fun getInstance(context: Context): BinDatabase{
            return if (database == null){
                database = Room.databaseBuilder(context, BinDatabase::class.java,"db").build()
                database as BinDatabase
            }
            else{
                database as BinDatabase
            }
        }
    }
}