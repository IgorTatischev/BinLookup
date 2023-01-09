package com.example.binchecker.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "description_table")
data class BinModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bin: Int,
    @Embedded val bank: Bank,
    val brand: String = "null",
    @Embedded val country: Country,
    @Embedded val number: Number,
    val prepaid: Boolean,
    val scheme: String = "null",
    val type: String = "null"
)

data class Bank(
    val city: String = "null",
    @ColumnInfo(name = "bank_name") val name: String? = "null",
    val phone: String = "null",
    val url: String = "null"
)

data class Country(
    val alpha2: String = "null",
    val currency: String = "null",
    val emoji: String = "null",
    val latitude: Int,
    val longitude: Int,
    @ColumnInfo(name = "country_name") val name: String = "null",
    val numeric: String = "null"
)

data class Number(
    val length: Int,
    val luhn: Boolean
)

