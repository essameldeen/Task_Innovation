package com.example.task_innovation.presentation.postDetails

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.task_innovation.presentation.postDetails.component.DetailsTopBar

import com.example.task_innovation.R
import com.example.task_innovation.core.commonCompose.EmptyScreen
import com.example.task_innovation.core.commonCompose.ShimmerEffect
import com.example.task_innovation.core.utils.Dimens.PostImageHeight
import com.example.task_innovation.core.utils.Dimens.MediumPadding1
import com.example.task_innovation.domain.models.Posts

@Composable
fun PostDetailsScreen(
    viewModel: PostDetailsViewModel,
    navigateUp: () -> Unit
) {

    val context = LocalContext.current

    val viewState by viewModel.viewState.collectAsState()

    when {
        viewState.isLoading -> repeat(10) {
            ShimmerEffect()
        }

        viewState.post != null -> {
            DetailsArticle(
                post = viewState.post,
                context,
                navigateUp,
            )
        }

        viewState.error != null -> {
            EmptyScreen(viewState.error)
        }
    }


}

@Composable
fun DetailsArticle(
    post: Posts?,
    context: Context,
    navigateUp: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
          onBackClick = navigateUp,
            title = stringResource(R.string.details)
         )

        Column (
            modifier = Modifier.fillMaxSize().padding(
                start = MediumPadding1,
                end = MediumPadding1,
                top = MediumPadding1
            ),
        ){

            AsyncImage(
                model = ImageRequest.Builder(context).data(post?.urlToImage).build(),
                placeholder = painterResource(R.drawable.img_place_holder),
                contentDescription = "Details Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(PostImageHeight)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(MediumPadding1))
            Text(
                text = post?.title ?: stringResource(R.string.no_title),
                style = MaterialTheme.typography.displaySmall,
                color = colorResource(
                    id = R.color.text_title
                )
            )

        }
    }
}