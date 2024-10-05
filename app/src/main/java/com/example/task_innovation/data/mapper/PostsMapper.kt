package com.example.task_innovation.data.mapper
import com.example.task_innovation.data.dataSource.remote.response.PostDto
import com.example.task_innovation.data.entity.PostsEntity
import com.example.task_innovation.domain.models.Posts




fun PostDto.toDomainModel(): Posts {
    return Posts(
        title = title ?: "",
        urlToImage = urlToImage ?: "",
    )
}
fun PostDto.toEntityModel(): PostsEntity {
    return PostsEntity(
        title = title ?: "",
        urlToImage = urlToImage ?: "",
    )
}


fun PostsEntity.toDomainModel(): Posts {
    return Posts(
        title = title,
        urlToImage = urlToImage,
        id = id
    )
}


