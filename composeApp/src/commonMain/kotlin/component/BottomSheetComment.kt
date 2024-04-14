package component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.flexible.bottomsheet.material3.BottomSheetDefaults
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.FlexibleSheetValue
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import model.Comment
import util.screenHeight

private var creatorPic: String = ""

@Composable
fun BottomSheetComments(
    selectedCommentId: String,
    hasComment: Boolean,
    creatorProfilePic: String,
    onDismissRequest: () -> Unit
) {
    var currentSheetTarget by remember {
        mutableStateOf(FlexibleSheetValue.IntermediatelyExpanded)
    }
    val isSkipSlightlyExpanded: Boolean
    val sheetSize: FlexibleSheetSize

    if (hasComment) {
        sheetSize = FlexibleSheetSize(
            fullyExpanded = fullyExpandedValueByPlatform
        )
        isSkipSlightlyExpanded = true
        creatorPic = creatorProfilePic
    } else {
        sheetSize = FlexibleSheetSize(
            fullyExpanded = fullyExpandedValueByPlatform,
            intermediatelyExpanded = 0.5f,
            slightlyExpanded = 0.25f,
        )
        isSkipSlightlyExpanded = false
    }

    val bottomSheetState = rememberFlexibleBottomSheetState(
        flexibleSheetSize = sheetSize,
        isModal = true,
        skipSlightlyExpanded = isSkipSlightlyExpanded,
        containSystemBars = false,
        allowNestedScroll = false
    )

    /*
    Calculate screen size based on current bottom sheet state.
    To be used for displaying bottom sheet content with center alignment.
     */
    val flexibleSheetSize = bottomSheetState.flexibleSheetSize
    val expectedSheetSize: Dp = when (bottomSheetState.targetValue) {
        FlexibleSheetValue.FullyExpanded -> screenHeight() * (flexibleSheetSize.fullyExpanded - 0.1f)
        FlexibleSheetValue.IntermediatelyExpanded -> screenHeight() * (flexibleSheetSize.intermediatelyExpanded - 0.1f)
        FlexibleSheetValue.SlightlyExpanded -> screenHeight() * (flexibleSheetSize.slightlyExpanded - 0.1f)
        FlexibleSheetValue.Hidden -> 1.dp
    }

    FlexibleBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState,
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xff858585)) },
        onTargetChanges = { sheetValue ->
            currentSheetTarget = sheetValue
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        var list by remember { mutableStateOf(listOf<Comment>()) }
        if (hasComment) {
            LaunchedEffect(Unit) {
                list = getComments(selectedCommentId)
            }
        }
        BottomSheetContent(list, expectedSheetSize)
    }
}

@Composable
private fun BottomSheetContent(
    data: List<Comment> = arrayListOf(),
    currentSheetSize: Dp
) {
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-8).dp),
            text = "Comments",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Divider(
            modifier = Modifier.padding(top = 8.dp),
            color = Color(0xffededed),
            thickness = 1.dp
        )
        if (data.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(currentSheetSize),
                content = {
                    items(
                        // sort list of comments by its likes count, descending
                        items = data.sortedByDescending { it.likes }
                    ) {
                        CommentItem(it)
                    }
                }
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(currentSheetSize),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No comments yet",
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Start the conversation.",
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CommentItem(data: Comment) {
    Row(
        modifier = Modifier.padding(
            top = 8.dp,
            start = 8.dp,
            end = 16.dp
        )
    ) {
        ProfilePicture(data.profilePic, modifier = Modifier.padding(8.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                Text(
                    text = data.username,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxHeight()
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
            Text(
                text = data.userComment
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Reply",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
                if (data.isTranslationAvailable) {
                    Text(
                        text = "See translation",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box {
                        Icon(
                            modifier = Modifier
                                .size(20.dp),
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "like",
                        )
                        if (data.isLikedByCreator) {
                            Box(
                                modifier = Modifier
                                    .padding(start = 14.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .padding(2.dp)
                            ) {
                                ProfilePicture(
                                    creatorPic,
                                    smallPic = true
                                )
                            }
                        }
                    }

                    if (data.likes > 0) {
                        Text(
                            text = data.likes.toString(),
                            modifier = Modifier.padding(start = 4.dp),
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}

suspend fun getComments(documentId: String): List<Comment> {
    val firebaseFirestore = Firebase.firestore
    try {
        val comments = firebaseFirestore
            .collection("posts")
            .document(documentId)
            .collection("comments")
            .get()
        return comments.documents.map {
            it.data()
        }
    } catch (e: Throwable) {
        throw e
    }
}

/*
Set the value of bottom sheet size : FlexibleSheetValue.FullyExpanded.
Should be declared natively on each platform due to different output/behavior.
Full screen in iOS require 0.9f, while in Android it's 1f.
 */
expect val fullyExpandedValueByPlatform: Float