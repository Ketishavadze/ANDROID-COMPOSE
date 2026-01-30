@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.compose.ui.screen.home

import com.example.compose.navigation.Screen
import com.example.compose.ui.screen.home.contract.HomeEvent
import com.example.compose.ui.screen.home.contract.HomeSideEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.compose.domain.model.Post
import com.example.compose.domain.model.Story
import com.example.compose.ui.theme.AppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.text.SimpleDateFormat
import java.util.*
import com.example.compose.R


// Helper function to format timestamp
private fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60000 -> "Just now"
        diff < 3600000 -> "${diff / 60000}m ago"
        diff < 86400000 -> "${diff / 3600000}h ago"
        diff < 604800000 -> "${diff / 86400000}d ago"
        else -> {
            val sdf = SimpleDateFormat("MMM dd", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeEvent.LoadInitial)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        message = sideEffect.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = AppTheme.colorScheme.error,
                    contentColor = AppTheme.colorScheme.onError,
                    shape = RoundedCornerShape(8.dp)
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = Screen.Home.route
            )
        },
        containerColor = AppTheme.colorScheme.background
    ) { paddingValues ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(state.isLoading),
            onRefresh = { viewModel.onEvent(HomeEvent.Refresh) },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                // Stories Section
                item {
                    StoriesSection(stories = state.stories)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Posts Section
                items(state.posts) { post ->
                    PostItem(post = post)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        if (state.isLoading && state.posts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = AppTheme.colorScheme.primary
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
        items(stories) { story ->
            StoryItem(story = story)
        }
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
            .clickable { /* Handle story click */ }
    ) {
        GlideImage(
            model = story.cover,
            contentDescription = story.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        )

        // Story title
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Post Header
            PostHeader(post = post)

            // Post Content
            if (post.postDesc.isNotEmpty()) {
                Text(
                    text = post.postDesc,
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Post Images
            if (post.images.isNotEmpty()) {
                PostImagesGrid(images = post.images)
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Post Footer
            PostFooter(post = post)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PostHeader(post: Post) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User Avatar
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

        // User Info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = post.fullName,
                style = AppTheme.typography.titleMedium,
                color = AppTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = formatTimestamp(post.postDate),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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
            // Grid for 3 or more images
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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
            }
        }
    }
}

@Composable
fun PostFooter(post: Post) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Comments
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { /* Handle comments */ }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_message),
                contentDescription = "Comments",
                tint = AppTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${post.commentsCount} Comments",
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        // Likes
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { /* Handle likes */ }
        ) {
            Icon(
                imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Likes",
                tint = if (post.isLiked) AppTheme.colorScheme.primary else AppTheme.colorScheme.onSurface.copy(
                    alpha = 0.6f
                ),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${post.likesCount} Likes",
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        // Share
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { /* Handle share */ }
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = AppTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Share",
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentRoute: String
) {
    NavigationBar(
        containerColor = AppTheme.colorScheme.surface,
        contentColor = AppTheme.colorScheme.onSurface
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            selected = currentRoute == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = AppTheme.colorScheme.primary,
                unselectedIconColor = AppTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                indicatorColor = AppTheme.colorScheme.primary.copy(alpha = 0.1f)
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite"
                )
            },
            selected = currentRoute == Screen.Favorite.route,
            onClick = {
                navController.navigate(Screen.Favorite.route)
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = AppTheme.colorScheme.primary,
                unselectedIconColor = AppTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                indicatorColor = AppTheme.colorScheme.primary.copy(alpha = 0.1f)
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_message),
                    contentDescription = "Chat"
                )
            },
            selected = currentRoute == Screen.Chat.route,
            onClick = {
                navController.navigate(Screen.Chat.route)
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = AppTheme.colorScheme.primary,
                unselectedIconColor = AppTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                indicatorColor = AppTheme.colorScheme.primary.copy(alpha = 0.1f)
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            },
            selected = currentRoute == Screen.Notification.route,
            onClick = {
                navController.navigate(Screen.Notification.route)
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = AppTheme.colorScheme.primary,
                unselectedIconColor = AppTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                indicatorColor = AppTheme.colorScheme.primary.copy(alpha = 0.1f)
            )
        )
    }
}