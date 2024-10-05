package com.example.task_innovation.data.dataSource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.task_innovation.data.entity.PostsEntity

@Dao
interface PostsDao {
    @Query("SELECT * FROM post WHERE id = :id")
    suspend fun getPostById(id: Int): PostsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPosts(posts: List<PostsEntity>)

    @Query("SELECT * FROM post")
    suspend fun getAllPosts(): List<PostsEntity>


}


