package component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun ProfilePicture(
    photoPath: String,
    modifier: Modifier = Modifier,
    smallPic: Boolean = false,
    isStoryAvailable: Boolean = false
) {
    val painterResource: Resource<Painter> = asyncPainterResource(
        photoPath,
        filterQuality = FilterQuality.Low,
    )
    val imageSize =
        if (smallPic) {
            16.dp
        } else {
            36.dp
        }
    if (isStoryAvailable) {
        KamelImage(
            resource = painterResource,
            contentDescription = "Profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(imageSize)
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
                    width = 3.dp,
                    color = Color.White,
                    shape = CircleShape,
                )
        )
    } else {
        KamelImage(
            resource = painterResource,
            contentDescription = "Profile picture",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(imageSize)
                .clip(CircleShape),
        )
    }
}