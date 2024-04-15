package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import util.Constant

@Composable
fun ProfileScreen() {
    MaterialTheme() {
        ProfileDetail()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetail() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ProfileTopAppBar(scrollBehavior = scrollBehavior)
        },
        content = { innerPadding ->
            AccountDetail(
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "dummy",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Filled.ExpandMore,
                        "Expand",
                        tint = Color.Gray,
                        modifier = Modifier.offset(x = (-8).dp)
                    )
                }
            }
        },
//        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//            containerColor = Color.White,
//            scrolledContainerColor = Color.White
//        ),
        actions = {
            IconButton(onClick = { }) {
                Box(contentAlignment = Alignment.TopEnd) {
                    Icon(Icons.Filled.AlternateEmail, "Thread")
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Red)
                            .size(8.dp),
                    )
                }
            }
            IconButton(onClick = { }) {
                Icon(Icons.Outlined.AddBox, "Create")
            }
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Menu, "Settings")
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun AccountDetail(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                Text(
                    text = "39",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "posts"
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                Text(
                    text = "903",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "followers"
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                Text(
                    text = "696",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "following"
                )
            }
        }
        Text(
            text = "Dummy Account",
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Nothing to see here ~"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            OutlinedButton(
                onClick = {},
                border = BorderStroke(1.dp, Color.DarkGray),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Text(
                    text = "Edit profile",
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
            }
            OutlinedButton(
                onClick = {},
                border = BorderStroke(1.dp, Color.DarkGray),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Text(
                    text = "Share profile",
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
            }
            OutlinedButton(
                onClick = {},
                border = BorderStroke(1.dp, Color.DarkGray),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(IntrinsicSize.Min),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Icon(
                    Icons.Outlined.PersonAdd,
                    "Follow",
                    tint = Color.DarkGray
                )
            }
        }
    }
}