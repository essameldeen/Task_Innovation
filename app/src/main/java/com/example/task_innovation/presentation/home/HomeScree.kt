package com.example.task_innovation.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task_innovation.R
import com.example.task_innovation.core.commonCompose.EmptyScreen
import com.example.task_innovation.core.commonCompose.ShimmerEffect
import com.example.task_innovation.core.utils.Dimens.ExtraSmallPadding2
import com.example.task_innovation.core.utils.Dimens.MediumPadding1
import com.example.task_innovation.presentation.home.compose.PostTitle
import com.example.task_innovation.ui.theme.Purple40

@Composable
fun HomeScreen(
    viewModel: HomePostsViewModel,
    navigateToDetails: (Int) -> Unit,
){

    val viewState by viewModel.viewState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MediumPadding1)
            .statusBarsPadding()
    ) {

        Row (modifier = Modifier.fillMaxWidth()){
            Image(
                painter = painterResource(id = R.drawable.ic_blog_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
                    .padding(horizontal = MediumPadding1)
            )

            Text(
                stringResource(R.string.blogs),
                Modifier.fillMaxWidth().padding(12.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Purple40
                )


        }

        Spacer(modifier = Modifier.height(MediumPadding1))

        when {
            viewState.isLoading -> repeat(10) {
                ShimmerEffect()
            }
            viewState.posts != null -> {
                 viewState.posts?.let { posts->
                     LazyColumn(
                         modifier = Modifier.fillMaxSize(),
                         verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                         contentPadding = PaddingValues(ExtraSmallPadding2)
                     ) {
                         items(posts.size){ index->

                             posts[index].let { post->
                                 PostTitle(
                                     modifier = Modifier,
                                     post.title.toString()
                                 ) {
                                     navigateToDetails.invoke(post.id ?: -1)
                                 }
                             }
                             if(index !=posts.size){
                                 Divider(color = Color.LightGray, thickness = 1.dp)
                             }


                         }
                     }
                 }

            }

            viewState.error != null -> {
                EmptyScreen(viewState.error)
            }
        }


    }


}


