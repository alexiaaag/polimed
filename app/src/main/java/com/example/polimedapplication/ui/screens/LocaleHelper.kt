import android.annotation.SuppressLint
import android.os.Build
import android.os.LocaleList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@SuppressLint("LocalContextConfigurationRead")
@Composable
fun SetAppLocaleToRomanian() {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val locale = Locale("ro")
        Locale.setDefault(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val resources = context.resources
            val configuration = resources.configuration
            configuration.setLocale(locale)
            configuration.setLocales(LocaleList(locale)) // ✅ corect
            context.createConfigurationContext(configuration)
        }
    }
}
