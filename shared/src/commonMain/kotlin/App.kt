@file:OptIn(ExperimentalFoundationApi::class)

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Post
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun App() {
    MaterialTheme {
        Dashboard(generateDummyData())
    }
}

expect val billabongFontFamily: FontFamily

@Composable
fun Dashboard(userData: List<Post>) {
    Column() {
        Text(
            "Instigrim",
            fontFamily = billabongFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            modifier = Modifier.padding(16.dp)
        )

        AnimatedVisibility(userData.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize(),
                content = {
                    items(userData) {
                        PostPublished(it)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PostPublished(data: Post) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Username(data.username, data.isVerified)
        if (data.postedPhoto.size > 1) {
            ImageSlider(data.postedPhoto)
        } else {
            Image(
                painter = painterResource(data.postedPhoto[0]),
                "",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            PostInteraction()
        }
        Description(data)
    }
}

@Composable
fun Username(userName: String, isVerified: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                fontWeight = FontWeight.Bold,
                text = userName
            )
            if (isVerified) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(start = 4.dp),
                    tint = Color.Blue,
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = "verified",

                    )
            }
        }
        Icon(
            modifier = Modifier
                .size(24.dp),
            imageVector = Icons.Default.MoreVert,
            contentDescription = "more"
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ImageSlider(photos: List<String>) {
    val images = arrayListOf<Painter>()
    photos.map {
        images.add(painterResource(it))
    }
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        photos.size
    }
    HorizontalPager(
        pageSpacing = 0.dp,
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomCenter) {
                Image(
                    painter = images[page],
                    contentDescription = "x",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
    PostInteraction(photos.size, pagerState)
}

@Composable
fun ImageIndicator(imageCount: Int, pagerState: PagerState) {
    Row(
        Modifier
            .height(40.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(imageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.Blue else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(6.dp)

            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostInteraction(imageCount: Int = 0, pagerState: PagerState? = null) {
    Box(contentAlignment = Alignment.BottomCenter) {
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .padding(start = 12.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "like"
                )
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = Icons.Default.ChatBubbleOutline,
                    contentDescription = "comment"
                )
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = Icons.Default.Send,
                    contentDescription = "share"
                )
            }
            Icon(
                modifier = Modifier
                    .size(24.dp),
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = "like"
            )
        }
        pagerState?.apply {
            ImageIndicator(imageCount, pagerState)
        }

    }

}

@Composable
fun Description(data: Post) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 4.dp),
                tint = Color.Black,
                imageVector = Icons.Default.Favorite,
                contentDescription = "verified"
            )
            Text("${data.likes} likes")
        }
        Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            Text(
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp),
                text = data.username
            )
            Text(data.description)
        }
        Text(
            text = data.publishedDate,
            color = Color.DarkGray,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
    }
}

fun generateDummyData(): List<Post> {
    val dummies = arrayListOf<Post>()

    dummies.add(
        Post(
            "android",
            true,
            31658,
            arrayListOf(
                "We will be there!",
                "Can't wait... *sent from Pixel 7",
                "I'm still in love with Pixel 3."
            ),
            "Gr8 things are coming.",
            "",
            arrayListOf(
                "drawable/android_1a.jpg",
                "drawable/android_1b.jpg",
                "drawable/android_1c.jpg"
            ),
            publishedDate = "30 May"
        )
    )

    dummies.add(
        Post(
            "googlepixel",
            true,
            8713,
            arrayListOf("Thanks for sharing!", "Stunning!", "Awww the caption! ♥️", "🔥🔥"),
            "Share your favorite family memories using #TeamPixel",
            "",
            arrayListOf("drawable/android_1.webp"),
            publishedDate = "5 April"
        )
    )

    dummies.add(
        Post(
            "john_doe",
            false,
            9288271,
            comments = null,
            "Help me reach the most liked post in Instagram pleaseee...",
            "",
            arrayListOf("drawable/android_3.webp"),
            publishedDate = "3 August"
        )
    )

    return dummies
}
