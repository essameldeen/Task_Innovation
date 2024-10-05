package com.example.task_innovation.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task_innovation.data.dataSource.local.PostsDao
import com.example.task_innovation.data.entity.PostsEntity

@Database(entities = [PostsEntity::class], version = 1, exportSchema = false)
abstract class PostsDataBase :RoomDatabase() {
    abstract fun postsDao(): PostsDao
}