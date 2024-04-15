package util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.toPx(): Float = with(LocalDensity.current) { this@toPx.toPx() }

@Composable
fun Int.pxToDp(): Dp = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
expect fun screenHeight(): Dp