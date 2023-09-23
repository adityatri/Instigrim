import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Typeface
import androidx.compose.ui.window.ComposeUIViewController
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Typeface

actual val billabongFontFamily: FontFamily = FontFamily(
    Typeface(loadCustomFont("billabong"))
)

fun MainViewController() = ComposeUIViewController { App() }

private fun loadCustomFont(name: String): Typeface {
    return Typeface.makeFromName(name, FontStyle.NORMAL)
}