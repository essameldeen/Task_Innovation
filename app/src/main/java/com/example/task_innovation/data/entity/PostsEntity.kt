package com.example.task_innovation.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "post")
data class PostsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val title: String,
    val urlToImage: String,
)