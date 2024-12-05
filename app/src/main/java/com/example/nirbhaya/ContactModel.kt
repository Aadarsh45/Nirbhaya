package com.example.nirbhaya

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contactList")
data class ContactModel(
    val name: String,

    @PrimaryKey
    val number: String
)

