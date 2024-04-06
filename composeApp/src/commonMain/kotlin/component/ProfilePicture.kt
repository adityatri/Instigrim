package component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun ProfilePicture(photoPath: String, modifier: Modifier = Modifier, smallPic: Boolean = false) {
    val painterResource: Resource<Painter> = asyncPainterResource(
        photoPath,
        filterQuality = FilterQuality.Low,
    )
    val imageSize =
        if (smallPic) {
            16.dp
        } else {
            32.dp
        }
    KamelImage(
        resource = painterResource,
        contentDescription = "Profile picture",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(imageSize)
            .clip(CircleShape),
    )
}