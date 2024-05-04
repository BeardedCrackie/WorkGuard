package sk.potociarm.workguard.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun WorkGuardTheme(
    content: @Composable () -> Unit
) {
    val typography = Typography(
        headlineSmall = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp
        ),
        titleLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        bodyLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        ),
        bodyMedium = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp
        ),
        labelMedium = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
    )
    MaterialTheme(
        content = content,
        typography = typography,
        )
    //todo theme
}