package com.riddhi.weatherforecast.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.adyen.android.assignment.R

/**
 * Defines a custom Material theme for the APOD (Astronomy Picture of the Day) app.
 *
 * I've used the dark color scheme and set provided custom colors (from resources as explained in readme).
 *
 * @param content The composable content to be displayed within the theme.
 */
@Composable
fun APODTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = colorResource(id = R.color.primary),
            background = colorResource(id = R.color.background),
            onPrimary = colorResource(id = R.color.textColor),
        ),
        content = content
    )
}