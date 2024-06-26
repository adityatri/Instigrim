package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import billabongFontFamily
import component.ProfilePicture
import component.Stories
import creatorProfilePic
import hasComment
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import model.Post
import model.Story
import mvi.action.HomeAction
import mvi.state.HomeState
import mvi.viewmodel.HomeViewModel
import org.koin.compose.koinInject
import selectedCommentId
import shouldShowBottomSheet
import util.getAppTheme

expect val msgNotifOffsetX: Dp
expect val msgNotifOffsetY: Dp

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinInject()
) {
    HomeScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {

    MaterialTheme(
        colorScheme = getAppTheme()
    ) {
        Dashboard(state.posts, state.stories)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(posts: List<Post>, stories: List<Story>) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(scrollBehavior = scrollBehavior)
        },
        content = { innerPadding ->
            AnimatedVisibility(posts.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        item {
                            Stories(stories)
                        }
                        items(
                            items = posts,
                            key = {
                                it.id
                            }
                        ) {
                            PostItem(it)
                        }
                    },
                    contentPadding = innerPadding
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        title = {
            Text(
                "Instigrim",
                fontFamily = billabongFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 36.sp
            )
        },
        actions = {
            IconButton(onClick = { }) {
                Box(contentAlignment = Alignment.TopEnd) {
                    Icon(Icons.Filled.FavoriteBorder, "notification")
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Red)
                            .size(8.dp),
                    )
                }
            }
            IconButton(onClick = { }) {
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier
                        .padding(end = 4.dp)
                ) {
                    Icon(Icons.Filled.ChatBubbleOutline, "messages")
                    Box(
                        modifier = Modifier
                            .offset(x = 6.dp, y = (-5).dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.Red)
                                .size(18.dp)
                                .requiredHeight(IntrinsicSize.Min)
                                .offset(x = msgNotifOffsetX, y = msgNotifOffsetY),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "4",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostItem(data: Post) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Username(data)
        if (data.imgPath.size > 1) {
            ImageSlider(data)
        } else {
            Photo(data.imgPath[0])
            PostInteraction(isLiked = data.isLiked, isCommentExist = data.commentCount > 0)
        }
        Description(data)
    }
}

@Composable
fun Username(data: Post) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ProfilePicture(data.profilePic, isStoryAvailable = data.isStoryAvailable)
            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxHeight(),
                fontWeight = FontWeight.Bold,
                text = data.username
            )
            if (data.isVerified) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(start = 4.dp)
                        .fillMaxHeight(),
                    tint = Color(0xff0694F1),
                    imageVector = Icons.Rounded.Verified,
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(data: Post) {
    val images = arrayListOf<String>()
    data.imgPath.map {
        images.add(it)
    }
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        data.imgPath.size
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
                Photo(images[page])
            }
        }
    }
    PostInteraction(data.imgPath.size, pagerState, data.isLiked, data.commentCount > 0)
}

@Composable
fun Photo(imageUrl: String) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val painterResource: Resource<Painter> = asyncPainterResource(
        imageUrl,
        filterQuality = FilterQuality.Medium,
    )
    KamelImage(
        resource = painterResource,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth(),
        onLoading = { CircularProgressIndicator(it) },
        onFailure = { exception: Throwable ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = exception.message.toString(),
                    actionLabel = "Hide",
                )
            }
        }
    )
}

@Composable
fun Description(data: Post) {
    Column(modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp)) {
        Text(
            fontWeight = FontWeight.Bold,
            text = "${data.likes} likes"
        )
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(data.username)
                }
                if (data.caption.isNotEmpty()) {
                    val captionText = data.caption.replace("\\n", "\n").substringBefore('#')
                    append(" $captionText")

                    // assume that hashtag will always be on the end of the caption
                    // TODO: to improve this logic for changing hashtag color
                    val hashtagIndex = data.caption.indexOf('#')
                    if (hashtagIndex > 0) {
                        val listOfHashtag = data.caption.substring(hashtagIndex)
                        withStyle(style = SpanStyle(color = getAppTheme().tertiary)) {
                            append(listOfHashtag)
                        }
                    }
                }
            }
        )
        if (data.commentCount > 0) {
            ClickableText(
                text = AnnotatedString("View all ${data.commentCount} comments"),
                style = TextStyle(
                    color = Color.Gray
                ),
                modifier = Modifier.padding(top = 4.dp),
                onClick = {
                    shouldShowBottomSheet.value = !shouldShowBottomSheet.value
                    hasComment.value = true
                    creatorProfilePic.value = data.profilePic
                    selectedCommentId.value =
                        "T9CKeJfGecJo8YpLKl8t"    // hardcoded, as @DocumentId is not supported yet
                }
            )
        } else {
            Row {
                ClickableText(
                    text = AnnotatedString("Add a comment..."),
                    style = TextStyle(
                        color = Color.Gray
                    ),
                    modifier = Modifier.padding(top = 4.dp),
                    onClick = {

                    }
                )
            }
        }
        Text(
            text = "10 minutes ago",
            color = Color.Gray,
            fontSize = 13.sp
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostInteraction(
    imageCount: Int = 0,
    pagerState: PagerState? = null,
    isLiked: Boolean = false,
    isCommentExist: Boolean
) {
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
                horizontalArrangement = Arrangement.spacedBy(16.dp),
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
                IconButton(onClick = {
                    shouldShowBottomSheet.value = !shouldShowBottomSheet.value
                    hasComment.value = isCommentExist
                    selectedCommentId.value =
                        "T9CKeJfGecJo8YpLKl8t"    // hardcoded, as @DocumentId is not supported yet
                }) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = Icons.Outlined.ModeComment,
                        contentDescription = "comment",
                    )
                }
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = Icons.AutoMirrored.Outlined.Send,
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
            val color =
                if (pagerState.currentPage == iteration) getAppTheme().onTertiaryContainer else getAppTheme().tertiaryContainer
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