package com.adyen.android.assignment.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.utils.formatAsMMDDYYYY

/**
 * Display the details of an Astronomy picture.
 */
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    astronomyPicture: AstronomyPicture,
    onFavourite:  (AstronomyPicture) -> Unit = {},
    onBack: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxSize()) {
        AstronomyImageSection(astronomyPicture = astronomyPicture, onBack = onBack, onFavourite = onFavourite)
        Text(
            text = astronomyPicture.explanation,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Display the image of an Astronomy picture and the back button.
 * This composable function does not use modifier from parent composable, as It needs to be max size
 */
@Composable
fun AstronomyImageSection(
    astronomyPicture: AstronomyPicture,
    onBack: () -> Unit = {},
    onFavourite: (AstronomyPicture) -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
    ) {
        APODImage(imageUrl = astronomyPicture.url)
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AppBar(onBack = onBack)
            Text(
                text = astronomyPicture.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            DateAndFavouriteRow(date = astronomyPicture.date.formatAsMMDDYYYY(), onFavourite = {
                onFavourite(astronomyPicture)
            })
        }
    }

}

/**
 * Display the image of an Astronomy picture using Coil.
 */
@Composable
fun APODImage(imageUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .error(android.R.drawable.stat_notify_error)
            .crossfade(true)
            .build(),
        contentDescription = "Astronomy Picture Image",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(onBack: () -> Unit = {}) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.our_universe)) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Icon"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        )
    )
}

/**
 * Display the date of an Astronomy picture and a favorite button.
 */
@Composable
fun DateAndFavouriteRow(modifier: Modifier = Modifier, date: String, onFavourite: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.titleLarge,
        )
        IconButton(onClick = { onFavourite() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_favorite_border),
                contentDescription = "Favorite Icon"
            )
        }
    }
}