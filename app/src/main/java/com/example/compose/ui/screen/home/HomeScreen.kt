@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.compose.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.compose.R
import com.example.compose.ui.extensions.CollectInLaunchedEffect
import com.example.compose.ui.extensions.toRelativeTimeString
import com.example.compose.ui.snackbar.AppSnackbarHost
import com.example.compose.ui.snackbar.SnackbarController
import com.example.compose.ui.snackbar.UiMessage
import com.example.compose.domain.model.Post
import com.example.compose.domain.model.Story
import com.example.compose.navigation.Screen
import com.example.compose.ui.components.navigation.BottomNavigationBar
import com.example.compose.ui.screen.home.contract.HomeEvent
import com.example.compose.ui.screen.home.contract.HomeSideEffect
import com.example.compose.ui.theme.AppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Controller for this screen (or lift it to app-level if you want global snackbars)
    val snackbarController = remember { SnackbarController() }

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeEvent.LoadInitial)
    }

    // Convert side effects -> snackbar messages
    viewModel.sideEffect.CollectInLaunchedEffect {
        when (it) {
            is HomeSideEffect.ShowError -> snackbarController.send(UiMessage.Error(it.message))
        }
    }

    //es aris ubralo komentari
    // One place to actually show snackbars
    snackbarController.messages.CollectInLaunchedEffect {
        when (it) {
            is UiMessage.Error -> snackbarHostState.showSnackbar(
                message = it.text,
                duration = SnackbarDuration.Short
            )
            is UiMessage.Info -> snackbarHostState.showSnackbar(
                message = it.text,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        snackbarHost = { AppSnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = Screen.Home.route
            )
        },
        containerColor = AppTheme.colorScheme.background
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {

            SwipeRefresh(
                state = rememberSwipeRefreshState(state.isLoading),
                onRefresh = { viewModel.onEvent(HomeEvent.Refresh) },
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    item {
                        StoriesSection(stories = state.stories)
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    items(state.posts) { post ->
                        PostItem(post = post)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            if (state.isLoading && state.posts.isEmpty()) {
                CircularProgressIndicator(
                    color = AppTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun StoriesSection(stories: List<Story>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(stories) { story -> StoryItem(story) }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StoryItem(story: Story) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { /* TODO */ }
    ) {
        GlideImage(
            model = story.cover,
            contentDescription = story.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                    )
                )
        )

        Text(
            text = story.title,
            style = AppTheme.typography.titleMedium,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        )
    }
}

@Composable
fun PostItem(post: Post) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            PostHeader(post)
            PostBody(post)
            PostFooter(post)
        }
    }
}

@Composable
private fun PostBody(post: Post) {
    if (post.postDesc.isNotEmpty()) {
        Text(
            text = post.postDesc,
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
    }

    if (post.images.isNotEmpty()) {
        PostImagesGrid(images = post.images)
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PostHeader(post: Post) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(AppTheme.colorScheme.primary)
        ) {
            GlideImage(
                model = post.avatar,
                contentDescription = post.fullName,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                loading = placeholder(R.drawable.placeholder_avatar),
                failure = placeholder(R.drawable.placeholder_avatar),
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = post.fullName,
                style = AppTheme.typography.titleMedium,
                color = AppTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = post.postDate.toRelativeTimeString(),
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PostImagesGrid(images: List<String>) {
    when (images.size) {
        1 -> {
            GlideImage(
                model = images[0],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }

        2 -> {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                images.forEach { imageUrl ->
                    GlideImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        else -> {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GlideImage(
                        model = images[0],
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    GlideImage(
                        model = images[1],
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                // Optional: handle remaining images with another row or "+N" overlay.
            }
        }
    }
}

@Composable
fun PostFooter(post: Post) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FooterAction(
            icon = { Icon(painterResource(id = R.drawable.ic_message), contentDescription = "Comments") },
            text = "${post.commentsCount} Comments",
            onClick = { /* TODO */ }
        )

        FooterAction(
            icon = {
                Icon(
                    imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Likes",
                    tint = if (post.isLiked) AppTheme.colorScheme.primary
                    else AppTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            },
            text = "${post.likesCount} Likes",
            onClick = { /* TODO */ }
        )

        FooterAction(
            icon = { Icon(imageVector = Icons.Default.Share, contentDescription = "Share") },
            text = "Share",
            onClick = { /* TODO */ }
        )
    }
}

@Composable
private fun FooterAction(
    icon: @Composable () -> Unit,
    text: String,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        CompositionLocalProvider(LocalContentColor provides AppTheme.colorScheme.onSurface.copy(alpha = 0.6f)) {
            Box(Modifier.size(20.dp)) { icon() }
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = AppTheme.typography.bodySmall,
            color = AppTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}
