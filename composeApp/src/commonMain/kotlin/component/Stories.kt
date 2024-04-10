package component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.Story
import util.Constant

@Composable
fun Stories(data: List<Story>) {
    LazyRow(
        state = rememberLazyListState()
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    val painterResource: Resource<Painter> = asyncPainterResource(
                        Constant.MY_PROFILE_URL,
                        filterQuality = FilterQuality.Low,
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        KamelImage(
                            resource = painterResource,
                            contentDescription = "Profile picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Icon(
                            Icons.Filled.AddCircle,
                            "Add new story",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                Text(
                    text = "Your Story",
                    fontSize = 12.sp,
                    modifier = Modifier.offset(y = usernameOffsetY)
                )
            }
        }
        items(
            items = data,
            itemContent = { item ->
                StoryItem(item)
            },
        )
    }
    Divider(
        modifier = Modifier.padding(top = 8.dp),
        color = Color(0xffededed),
        thickness = 1.dp
    )
}

@Composable
fun StoryItem(data: Story) {
    val painterResource: Resource<Painter> = asyncPainterResource(
        data.profilePic,
        filterQuality = FilterQuality.Low,
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 8.dp, end = 16.dp)
    ) {
        KamelImage(
            resource = painterResource,
            contentDescription = "Profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Yellow, Color.Magenta, Color.Red),
                        start = Offset(0f, 0f),
                        end = Offset.Infinite
                    ),
                    shape = CircleShape
                )
                .border(
                    width = 5.dp,
                    color = Color.White,
                    shape = CircleShape,
                )
        )
        Text(
            text = data.username,
            fontSize = 12.sp,
            modifier = Modifier.offset(y = usernameOffsetY)
        )
    }
}

expect val usernameOffsetY: Dp