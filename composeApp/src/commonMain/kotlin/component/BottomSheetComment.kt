package component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import util.screenHeight


@Composable
fun BottomSheetComments(comments: List<String>, onDismissRequest: () -> Unit) {
    var currentSheetTarget by remember {
        mutableStateOf(FlexibleSheetValue.IntermediatelyExpanded)
    }
    val isSkipSlightlyExpanded: Boolean
    val sheetSize: FlexibleSheetSize

    if (comments.isEmpty()) {
        sheetSize = FlexibleSheetSize(
            fullyExpanded = fullyExpandedValueByPlatform
        )
        isSkipSlightlyExpanded = true
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
        BottomSheetContent(comments, expectedSheetSize)
    }
}

@Composable
private fun BottomSheetContent(data: List<String> = arrayListOf(), currentSheetSize: Dp) {
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
            LazyColumn {
                // TODO: show list of comments
            }
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
private fun CommentItem(data: String) {

}

/*
Set the value of bottom sheet size : FlexibleSheetValue.FullyExpanded.
Should be declared natively on each platform due to different output/behavior.
Full screen in iOS require 0.9f, while in Android it's 1f.
 */
expect val fullyExpandedValueByPlatform: Float