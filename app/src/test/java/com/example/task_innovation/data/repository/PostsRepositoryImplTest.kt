package com.example.task_innovation.data.repository

import com.example.task_innovation.core.manager.InternetManager
import com.example.task_innovation.data.dataSource.local.PostsDao
import com.example.task_innovation.data.dataSource.remote.api.PostsApiServices
import com.example.task_innovation.data.dataSource.remote.response.PostDto
import com.example.task_innovation.data.dataSource.remote.response.PostsResponse
import com.example.task_innovation.data.entity.PostsEntity
import com.example.task_innovation.data.mapper.toDomainModel
import com.example.task_innovation.data.mapper.toEntityModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PostsRepositoryImplTest {

    @Mock
    private lateinit var apiService: PostsApiServices

    @Mock
    private lateinit var postsDao: PostsDao

    @Mock
    private lateinit var internetManager: InternetManager

    private lateinit var repository: PostsRepositoryImpl


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = PostsRepositoryImpl(apiService, postsDao, internetManager)
    }

    @Test
    fun `test getAllPost from api with have network connection and save to database`() =
        runBlocking {
            whenever(internetManager.checkNetwork()).thenReturn(true)

            val response = PostsResponse(
                status = "s",
                articles = listOf(
                    PostDto(
                        "hi test",
                        "descr",
                        "title",
                        "url",
                    )
                ),
                totalResults = 1
            )

            val postsEntities = response.articles.map { it.toEntityModel().copy(id = 1) }
            val postsDomainModels = postsEntities.map { it.toDomainModel() }


            whenever(apiService.getPosts("sport")).thenReturn(response)

            val result = repository.getPosts("sport")


            verify(postsDao).upsertPosts(postsEntities)

            assertEquals(postsDomainModels, result)
        }

    @Test
    fun `test getPostById should return the correct Post`() = runTest {
        val postId = 1
        val postsEntity = PostsEntity(
            1,
            "title",
            "description",
        )
        whenever(postsDao.getPostById(postId)).thenReturn(postsEntity)

        val result = repository.getPostById(postId)
        assertEquals(postsEntity.toDomainModel(), result)
    }

}