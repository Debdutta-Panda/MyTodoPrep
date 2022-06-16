package com.debduttapanda.mytodoprep.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.debduttapanda.mytodoprep.R

val fonts = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_italic, style = FontStyle.Italic),
    Font(R.font.montserrat_bold, weight = FontWeight.Bold),
    Font(R.font.montserrat_blackitalic, weight = FontWeight.Bold, style = FontStyle.Italic)
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = fonts,
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        letterSpacing = 0.25.sp,
    )
)

val field_placeholder_big = TextStyle(
    fontSize = 32.sp,
    fontFamily = FontFamily(
        Font(R.font.montserrat_thin)
    )
)