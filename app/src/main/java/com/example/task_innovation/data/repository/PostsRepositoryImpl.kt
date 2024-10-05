package com.example.task_innovation.data.repository

import com.example.task_innovation.data.dataSource.remote.api.PostsApiServices
import com.example.task_innovation.data.mapper.toDomainModel
import com.example.task_innovation.domain.models.Posts
import com.example.task_innovation.core.manager.InternetManager
import com.example.task_innovation.data.dataSource.local.PostsDao
import com.example.task_innovation.data.mapper.toEntityModel
import com.example.task_innovation.domain.repository.PostsRepository
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val api: PostsApiServices,
    private val postsDao: PostsDao,
    private val internetManager: InternetManager
) : PostsRepository {
    override suspend fun getPosts(searchQuery: String?): List<Posts> {
        return if (internetManager.checkNetwork()) {
            val response = api.getPosts(searchQuery)
            val postsDto = response.articles

            val postsEntity = postsDto.map { it.toEntityModel() }

           postsDao.upsertPosts(postsEntity)
            val posts = postsDao.getAllPosts()
            posts.map { it.toDomainModel() }
        } else {
            postsDao.getAllPosts().map {
                it.toDomainModel()
            }
        }
    }

    override suspend fun getPostById(id: Int): Posts {
        return postsDao.getPostById(id).toDomainModel()
    }

}

