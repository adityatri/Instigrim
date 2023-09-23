import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.myapplication.common.R

@Composable
fun MainView() = App()

actual val billabongFontFamily = FontFamily(
    Font(R.font.billabong)
)
