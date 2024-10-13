package com.adyen.android.assignment.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.ApiState
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.utils.formatAsMMDDYYYY
import com.adyen.android.assignment.viewmodels.MainViewModel

/**
 * Checks for API State and displays the appropriate UI based on the state.
 */
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    onSelect: (AstronomyPicture) -> Unit = {}
) {

    val apodApiState = mainViewModel.apodApiState.collectAsState()

    when (apodApiState.value) {
        is ApiState.Error -> {
            val errorState = (apodApiState.value as ApiState.Error)
            // Display error screen with title, message, and retry option
            ErrorScreen(title = errorState.title, message = errorState.message, onRetry = {
                mainViewModel.retry()
            })
        }

        ApiState.Loading -> Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display loading indicator while data is fetched
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }

        is ApiState.Success<*> -> {
            val recentList = mainViewModel.recentPictures.collectAsState()
            // Display the list of Astronomy pictures when data is successfully loaded
            APODTitleList(
                modifier = modifier,
                list = recentList.value,
                onSelect = onSelect,
            )
        }
    }


}

/**
 * Display the list of Astronomy pictures in a vertical scrollable column.
 * I preferred Column with vertical Scroll (over lazycolumn)
 * Because, If I want to add another list of favorites.. lazy column would not allow that..
 */
@Composable
fun APODTitleList(
    modifier: Modifier = Modifier,
    list: List<AstronomyPicture>,
    onSelect: (AstronomyPicture) -> Unit = {}
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.our_universe),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        ListTitle(stringResource(id = R.string.latest))
        APODList(list = list, onSelect = onSelect)
    }
}

/**
 * Display a title for a list of Astronomy pictures.
 */
@Composable
fun ListTitle(title: String, modifier: Modifier = Modifier) {
    Text(title, style = MaterialTheme.typography.titleMedium, modifier = modifier.padding(8.dp))
}

/**
 * Display a list of Astronomy pictures.
 */
@Composable
fun APODList(
    modifier: Modifier = Modifier, list: List<AstronomyPicture>,
    onSelect: (AstronomyPicture) -> Unit = {}
) {
    Column(modifier = modifier) {
        for (item in list) {
            APODListItem(astronomyPicture = item, onSelect = onSelect)
        }
    }
}

/**
 * Display a single Astronomy picture item.
 */
@Composable
fun APODListItem(
    modifier: Modifier = Modifier,
    astronomyPicture: AstronomyPicture,
    onSelect: (AstronomyPicture) -> Unit = {}
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable {
            onSelect(astronomyPicture)
        }) {
        // Display the Astronomy picture image using Coil from image url
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(astronomyPicture.url)
                .error(android.R.drawable.stat_notify_error)
                .crossfade(true)
                .transformations(CircleCropTransformation())
                .build(), contentDescription = "Astronomy Picture Image",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = astronomyPicture.title, style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = astronomyPicture.date.formatAsMMDDYYYY(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}