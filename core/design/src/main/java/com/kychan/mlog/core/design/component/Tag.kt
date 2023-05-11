package com.kychan.mlog.core.design.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kychan.mlog.core.designsystem.R

@Composable
fun MovieTag(
    tagName: String,
    rotate: Float = -45f,
    size: Dp = 60.dp,
    fontSize: TextUnit = 10.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    overflow: TextOverflow = TextOverflow.Ellipsis,
){
    val widthRatio = 0.64
    val heightRatio = 0.5
    val offsetRatio = 0.07

    Box(
        modifier = Modifier.rotate(rotate),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(size)
                .scale(1.4f)
            ,
            contentScale = ContentScale.Fit,
            painter = painterResource(R.drawable.tag),
            contentDescription = "",
        )
        Text(
            modifier = Modifier
                .widthIn(max = (size.value * widthRatio).dp)
                .heightIn(max = (size.value * heightRatio).dp)
                .offset(-(size.value * offsetRatio).dp, (0).dp)
                .align(Alignment.Center)
            ,
            textAlign = TextAlign.Center,
            text = if(tagName.length >=5) tagName.breakDown(2) else tagName,
            fontSize = fontSize,
            fontWeight = fontWeight,
            overflow = overflow
        )
    }
}

private fun String.breakDown(breakDownIndex: Int) = "${this.substring(0,breakDownIndex)}\n${this.substring(breakDownIndex)}"

@Preview
@Composable
fun MovieTagPreview(){
    MovieTag(
        tagName = "드라마",
    )
}