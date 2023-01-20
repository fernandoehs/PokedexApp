package com.fernandoherrera.pokedexapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fernandoherrera.pokedexapp.R

val Roboto = FontFamily(
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold),
)

val RobotoCondensed = FontFamily(
    Font(R.font.roboto_condensed_light, FontWeight.Light),
    Font(R.font.roboto_condensed_regular, FontWeight.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val appFontFamily = FontFamily(
    Font(R.font.circularstd_book),
    Font(R.font.circularstd_medium, FontWeight.W600),
    Font(R.font.circularstd_black, FontWeight.Bold),
    Font(R.font.circularstd_bold, FontWeight.W900)
)

//private val defaultTypography = kotlin.text.Typography()
//val themeTypography = kotlin.text.Typography(
//    h1 = defaultTypography.h1.copy(fontFamily = appFontFamily),
//    h2 = defaultTypography.h2.copy(fontFamily = appFontFamily),
//    h3 = defaultTypography.h3.copy(fontFamily = appFontFamily),
//    h4 = defaultTypography.h4.copy(fontFamily = appFontFamily),
//    h5 = defaultTypography.h5.copy(fontFamily = appFontFamily),
//    h6 = defaultTypography.h6.copy(fontFamily = appFontFamily),
//    subtitle1 = defaultTypography.subtitle1.copy(fontFamily = appFontFamily),
//    subtitle2 = defaultTypography.subtitle2.copy(fontFamily = appFontFamily),
//    body1 = defaultTypography.body1.copy(fontFamily = appFontFamily),
//    body2 = defaultTypography.body2.copy(fontFamily = appFontFamily),
//    button = defaultTypography.button.copy(fontFamily = appFontFamily),
//    caption = defaultTypography.caption.copy(fontFamily = appFontFamily),
//    overline = defaultTypography.overline.copy(fontFamily = appFontFamily)

