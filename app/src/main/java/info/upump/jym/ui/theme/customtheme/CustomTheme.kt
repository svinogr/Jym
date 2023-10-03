package info.upump.jym.ui.theme.customtheme/*
package info.upump.jym.ui.theme.customtheme

import android.graphics.fonts.FontFamily
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object CustomTheme {
    val colors: CustomColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: CustomTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}


data class CustomTypography(
    val h1: TextStyle = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    val body1: TextStyle = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
)


val LocalColors = staticCompositionLocalOf { lightColors() }

val LocalTypography = staticCompositionLocalOf {
    CustomTypography()
}*/
