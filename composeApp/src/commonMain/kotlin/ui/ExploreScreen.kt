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
import util.screenHeight

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
            AnimatedVisibility(boxItems.isNotEmpty()) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(3),
                    verticalItemSpacing = 2.dp,
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.padding(top = it.calculateTopPadding())
                ) {
                    items(boxItems) {
                        Item(item = it)
                    }
                }
            }
        }
    }
}

class GridItem(
    val id: String,
    val color: Color,
    val screenRatio: Int
)

@Composable
fun Item(
    modifier: Modifier = Modifier,
    item: GridItem
) {
    Box(
        modifier = modifier
            .background(item.color)
            .height(screenHeight().div(item.screenRatio)),
        contentAlignment = Alignment.Center
    ) {
        Text(item.id)
    }
}

val boxItems: List<GridItem> = listOf(
    GridItem(
        "1",
        Color.Red,
        6
    ),
    GridItem(
        "2",
        Color.Blue,
        6
    ),
    GridItem(
        "3",
        Color.Yellow,
        3
    ),
    GridItem(
        "4",
        Color.Green,
        6
    ),
    GridItem(
        "5",
        Color.Gray,
        6
    ),
    GridItem(
        "6",
        Color.Cyan,
        6
    ),
    GridItem(
        "7",
        Color.Red,
        3
    ),
    GridItem(
        "8",
        Color.Blue,
        6
    ),
    GridItem(
        "9",
        Color.Yellow,
        6
    ),
    GridItem(
        "10",
        Color.Green,
        6
    ),
    GridItem(
        "11",
        Color.Gray,
        6
    ),
    GridItem(
        "12",
        Color.Cyan,
        3
    ),
    GridItem(
        "13",
        Color.Red,
        6
    ),
    GridItem(
        "14",
        Color.Blue,
        6
    ),
    GridItem(
        "15",
        Color.Yellow,
        6
    ),
)