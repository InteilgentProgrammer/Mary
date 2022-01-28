package com.mary.lab.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.mary.R

@Composable
fun MaryImage(
    modifier: Modifier = Modifier,
    painter: Int = R.drawable.ic_launcher_foreground,
    contentDescription: String? = "Mary",
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    Image(
        painter = painterResource(painter),
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment, contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
    )
}