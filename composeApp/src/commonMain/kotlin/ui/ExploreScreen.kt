package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import enum.ItemType
import model.ExploreItem
import util.screenHeight
import kotlin.random.Random

expect fun getVideoItemIndexByPlatform(): Array<Int>

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen() {
    MaterialTheme {
        Scaffold(
            topBar = {
                SearchBar(
                    active = false,
                    onActiveChange = {},
                    onQueryChange = {},
                    onSearch = {},
                    placeholder = {
                        Text("Search")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            "Search"
                        )
                    },
                    query = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp
                        )
                ) {
                    // show search history or suggestion
                }

            }
        ) {
            AnimatedVisibility(dummyItems.isNotEmpty()) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(3),
                    verticalItemSpacing = 2.dp,
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.padding(top = it.calculateTopPadding())
                ) {
                    items(dummyItems) {
                        Item(item = it)
                    }
                }
            }
        }
    }
}

@Composable
fun Item(
    modifier: Modifier = Modifier,
    item: ExploreItem
) {
    val boxSize = screenHeight().div(6)
    val rectangleSize = (boxSize * 2) + 2.dp
    Box(
        modifier = modifier
            .background(item.color)
            .fillMaxWidth()
            .then(
                if (item.type == ItemType.IMAGE)
                    Modifier.height(boxSize)
                else
                    Modifier.height(rectangleSize)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.id.toString(),
            color = Color.White
        )
    }
}

/*
    Item order in StaggeredGrid seems working fine in iOS (left to right), but in Android somehow the
    items are not rendered in sequence (can refer to the number on each item).
    Hence I'm using expect function 'getVideoItemIndexByPlatform' to return index values from each platform.
 */
val dummyItems = (1..20).map { id ->
    ExploreItem(
        id,
        Color(Random.nextLong(0xFFFFFFFF)).copy(alpha = 1f),
        type = if (id in getVideoItemIndexByPlatform())
            ItemType.VIDEO else ItemType.IMAGE
    )
}