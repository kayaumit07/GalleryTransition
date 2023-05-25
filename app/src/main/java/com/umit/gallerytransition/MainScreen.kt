package com.umit.gallerytransition

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val images = remember {
        mutableStateListOf(
            "https://www.arkasturizm.com/Content/images/Uploads/Resized/640-450/antalya-71715.jpg",
            "https://www.ozbilgsoft.com/wp-content/uploads/2021/04/antalya-web-tasarim.jpg",
            "https://www.manzara.gen.tr/w1/Antalya-Kalei%C3%A7i-Liman.jpg",
            "https://kulturveyasam.com/wp-content/uploads/2018/05/antalya_sahil_ilcesi-09-min.jpg",
            "https://i.cnnturk.com/i/cnnturk/75/1200x675/611baec05cf3b019acee1e69.jpg",
            "https://images.unsplash.com/photo-1582030826675-8b596001240a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8YW50YWx5YSUyQyUyMHR1cmtleXxlbnwwfHwwfHw%3D&w=1000&q=80"
        )
    }

    Scaffold(modifier = Modifier.padding(vertical = 48.dp)) {
        val pagerState = rememberPagerState()
        val matrix = remember {
            ColorMatrix()
        }
        HorizontalPager(pageCount = images.size, state = pagerState) { index ->
            val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
            val imageSize by animateFloatAsState(
                targetValue = if (pageOffset != 0.0f) 0.75f else 1.0f,
                animationSpec = tween(500)
            )

            LaunchedEffect(key1 = imageSize) {
                if (pageOffset != 0.0f) {
                    matrix.setToSaturation(0.0f)
                } else {
                    matrix.setToSaturation(1.0f)
                }
            }

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .graphicsLayer {
                        scaleX = imageSize
                        scaleY = imageSize
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(images[index])
                    .build(), contentDescription = "Image",
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(matrix)
            )
        }
    }
}