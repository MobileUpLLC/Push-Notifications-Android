package ru.mobileup.features.photo.ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import me.aartikov.replica.single.Loadable
import ru.mobileup.core.search.ui.FakeSearchComponent
import ru.mobileup.core.search.ui.SearchWidget
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.core.widget.EmptyPlaceholder
import ru.mobileup.core.widget.LceWidget
import ru.mobileup.features.R
import ru.mobileup.features.photo.domain.Photo
import ru.mobileup.features.photo.domain.PhotoId

@Composable
fun PhotoListUi(
    component: PhotoListComponent,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        component.randomPhotosState.data?.let { items ->
            EmptySearchingPlaceholder(
                data = items,
                visible = component.isEmptySearchingPlaceholderVisible
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            val animatingOffset by animateFloatAsState(
                if (component.isEmptySearchingPlaceholderVisible) 250f else 0F,
                tween(delayMillis = if (component.isEmptySearchingPlaceholderVisible) 0 else 500)
            )

            SearchWidget(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .offset(y = animatingOffset.dp),
                component = component.searchComponent,
                placeholderText = stringResource(R.string.photos_search_placeholder)
            )

            SearchingContent(
                state = component.searchingPhotosState,
                onRetryClick = component::onRetryClick,
                visible = !component.isEmptySearchingPlaceholderVisible
            )
        }
    }
}

@Composable
private fun SearchingContent(
    state: Loadable<List<Photo>>,
    onRetryClick: () -> Unit,
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    LceWidget(
        modifier = modifier,
        state = state,
        onRetryClick = onRetryClick
    ) { items, loading ->
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(delayMillis = 500)),
            exit = fadeOut()
        ) {
            if (items.isEmpty() && !loading) {
                EmptyPlaceholder(description = stringResource(R.string.photos_empty_description))
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                ) {
                    items(items = items) {
                        Image(
                            modifier = Modifier
                                .padding(8.dp)
                                .height(140.dp)
                                .clip(MaterialTheme.shapes.small),
                            painter = rememberImagePainter(data = it.url),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptySearchingPlaceholder(
    data: List<Photo>,
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(2000)),
            exit = fadeOut()
        ) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                userScrollEnabled = false,
                content = {
                    items(items = data) {
                        Image(
                            modifier = Modifier
                                .padding(8.dp)
                                .height(140.dp)
                                .clip(MaterialTheme.shapes.small),
                            painter = rememberImagePainter(data = it.url),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            )

            Box(
                modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colors.background.copy(alpha = 0f),
                                MaterialTheme.colors.background
                            ),
                            endY = 800f
                        )
                    )
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PhotoListUiPreview() {
    AppTheme {
        PhotoListUi(FakePhotoListComponent())
    }
}

class FakePhotoListComponent : PhotoListComponent {

    override val searchComponent = FakeSearchComponent()

    override val randomPhotosState = Loadable(
        loading = true,
        data = listOf(
            Photo(
                id = PhotoId("1"),
                url = "https://example_img.ru"
            ),
            Photo(
                id = PhotoId("5"),
                url = "https://example_img.ru"
            ),
            Photo(
                id = PhotoId("7"),
                url = "https://example_img.ru"
            )
        )
    )

    override val searchingPhotosState = Loadable(
        loading = true,
        data = listOf(
            Photo(
                id = PhotoId("1"),
                url = "https://example_img.ru"
            ),
            Photo(
                id = PhotoId("5"),
                url = "https://example_img.ru"
            ),
            Photo(
                id = PhotoId("7"),
                url = "https://example_img.ru"
            )
        )
    )

    override val isEmptySearchingPlaceholderVisible: Boolean = false

    override fun onRetryClick() = Unit
}
