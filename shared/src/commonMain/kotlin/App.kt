import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Post
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
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
        Image(
            painter = painterResource(data.postedPhoto),
            "",
            modifier = Modifier
                .padding(top = 4.dp, bottom = 4.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        PostInteraction()
        Description(data)
    }
}

@Composable
fun Username(userName: String, isVerified: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
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
                    contentDescription = "verified"
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

@Composable
fun PostInteraction() {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 12.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
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
            "drawable/android_1.webp",
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
            "drawable/android_2.webp",
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
            "drawable/android_3.webp",
            publishedDate = "3 August"
        )
    )

    return dummies
}
