package com.example.task_innovation.presentation.home.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PostTitle(
    modifier: Modifier = Modifier,
    title:String,
    onTextClick:()-> Unit
){
    Text(title, modifier.fillMaxWidth().padding(12.dp).clickable { onTextClick() }, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
}
