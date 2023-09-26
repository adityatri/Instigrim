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
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(userData: List<Post>) {
    val scrollBehavior = enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Instigrim",
                        fontFamily = billabongFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 32.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    scrolledContainerColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.FavoriteBorder, "notification")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Message, "messages")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            AnimatedVisibility(userData.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        items(userData) {
                            PostPublished(it)
                        }
                    },
                    contentPadding = innerPadding
                )
            }
        }
    )
}

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun PostPublished(data: Post) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Username(data)
        if (data.postedPhoto.size > 1) {
            ImageSlider(data)
        } else {
            Image(
                painter = painterResource(data.postedPhoto[0]),
                "",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            PostInteraction(isLiked = data.isLiked)
        }
        Description(data)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Username(data: Post) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(data.profilePic),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                fontWeight = FontWeight.Bold,
                text = data.username
            )
            if (data.isVerified) {
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

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(data: Post) {
    val images = arrayListOf<Painter>()
    data.postedPhoto.map {
        images.add(painterResource(it))
    }
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        data.postedPhoto.size
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
    PostInteraction(data.postedPhoto.size, pagerState, data.isLiked)
}

@OptIn(ExperimentalFoundationApi::class)
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
fun PostInteraction(imageCount: Int = 0, pagerState: PagerState? = null, isLiked: Boolean = false) {
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
                if (isLiked) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "like",
                        tint = Color.Red
                    )
                } else {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "like"
                    )
                }
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
            "drawable/pic_android.jpg",
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
            "drawable/pic_google.jpg",
            arrayListOf("drawable/pixel_1.jpg"),
            publishedDate = "5 April"
        )
    )

    dummies.add(
        Post(
            "john_doe",
            false,
            9288271,
            comments = null,
            "Faith can move the mountains~",
            "drawable/pic_john.jpg",
            arrayListOf("drawable/john_1.jpg"),
            publishedDate = "3 August",
            isLiked = true
        )
    )

    return dummies
}
