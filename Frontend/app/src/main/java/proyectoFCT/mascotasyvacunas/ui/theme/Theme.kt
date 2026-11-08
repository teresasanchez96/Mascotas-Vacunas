package proyectoFCT.mascotasyvacunas.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun MascotasYVacunasTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val configuration = LocalConfiguration.current
    val densityActual = LocalDensity.current

    val factorAncho = configuration.screenWidthDp / 411f
    val factorAlto = configuration.screenHeightDp / 840f

    val factorEscala = minOf(
        factorAncho,
        factorAlto
    ).coerceIn(0.80f, 1f)

    val densidadAdaptada = Density(
        density = densityActual.density * factorEscala,
        fontScale = densityActual.fontScale
    )

    androidx.compose.runtime.CompositionLocalProvider(
        LocalDensity provides densidadAdaptada
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

@Composable
fun anchoDialogoAdaptado(): Dp {
    val anchoPantalla = LocalConfiguration.current.screenWidthDp
    return if (anchoPantalla < 390) 280.dp else 320.dp
}

@Composable
fun dimensionAdaptada(
    compacta: Dp,
    normal: Dp
): Dp {
    val anchoPantalla = LocalConfiguration.current.screenWidthDp

    return if (anchoPantalla < 390) compacta else normal
}