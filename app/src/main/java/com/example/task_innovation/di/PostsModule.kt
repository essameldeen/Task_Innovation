package com.example.task_innovation.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.task_innovation.core.db.PostsDataBase
import com.example.task_innovation.core.manager.InternetManager
import com.example.task_innovation.core.manager.InternetManagerImpl
import com.example.task_innovation.core.utils.PostsResponse.BASE_URL
import com.example.task_innovation.core.utils.PostsResponse.POSTS_DATABASE_NAME
import com.example.task_innovation.data.dataSource.local.PostsDao
import com.example.task_innovation.data.dataSource.remote.api.PostsApiServices
import com.example.task_innovation.data.repository.PostsRepositoryImpl
import com.example.task_innovation.domain.repository.PostsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostsModule {


    @Provides
    @Singleton
    fun provideInternetManager(@ApplicationContext applicationContext: Context): InternetManager =
        InternetManagerImpl(applicationContext)


    @Provides
    @Singleton
    fun provideNewsDataBase(
        application: Application
    ): PostsDataBase {
        return Room.databaseBuilder(
            application,
            klass = PostsDataBase::class.java,
            name = POSTS_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePostsDao(postsDataBase: PostsDataBase): PostsDao {
        return postsDataBase.postsDao()
    }

    @Provides
    @Singleton
    fun provide():OkHttpClient{
        val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY) // Log the request and response body

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return  client
    }

    @Provides
    @Singleton
    fun providePostsApi(client: OkHttpClient): PostsApiServices {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PostsApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        postsApiService: PostsApiServices,
        postsDao: PostsDao,
        internetManager: InternetManager

    ): PostsRepository {
        return PostsRepositoryImpl(postsApiService, postsDao, internetManager)

    }
}
