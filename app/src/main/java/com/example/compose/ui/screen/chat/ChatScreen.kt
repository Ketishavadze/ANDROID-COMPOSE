@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.compose.ui.screen.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.compose.R
import com.example.compose.domain.model.Chat
import com.example.compose.domain.model.MessageType

@Composable
fun ChatScreen(
    vm: ChatViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) { vm.onIntent(ChatContract.Intent.Load) }

    val filtered = remember(state.chats, state.appliedQuery) {
        val q = state.appliedQuery.lowercase()
        if (q.isBlank()) state.chats
        else state.chats.filter { it.owner.lowercase().contains(q) } // owner = name surname
    }

    val backgroundDark = Color(0xFF0F1B22)
    val hintGray = Color(0xFF8D98A1)
    val searchBg = Color(0xFF1A2A33)
    val accentGreen = Color(0xFF34E89E)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundDark)
            .padding(16.dp)
    ) {
        SearchRow(
            query = state.inputQuery,
            onQueryChange = { vm.onIntent(ChatContract.Intent.UpdateInput(it)) },
            onApply = { vm.onIntent(ChatContract.Intent.ApplySearch) },
            searchBg = searchBg
        )

        Spacer(Modifier.height(16.dp))

        when {
            state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                CircularProgressIndicator(color = accentGreen)
            }

            state.error != null -> Text(
                text = "Error: ${state.error}",
                color = hintGray
            )

            filtered.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                Text("No chats found", color = hintGray)
            }

            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filtered, key = { it.id }) { chat ->
                    ChatRowItem(
                        chat = chat,
                        hintGray = hintGray,
                        accentGreen = accentGreen
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchRow(
    query: String,
    onQueryChange: (String) -> Unit,
    onApply: () -> Unit,
    searchBg: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .height(52.dp),
            singleLine = true,
            placeholder = { Text("Search") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_google),
                    contentDescription = "Search"
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = searchBg,
                unfocusedContainerColor = searchBg,
                disabledContainerColor = searchBg,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedPlaceholderColor = Color(0xFF8D98A1),
                unfocusedPlaceholderColor = Color(0xFF8D98A1),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White
            ),
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(Modifier.width(12.dp))

        IconButton(
            onClick = onApply,
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFF34E89E))
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_filter),
                contentDescription = "Apply search",
                tint = Color(0xFF0F1B22)
            )
        }
    }
}

@Composable
private fun ChatRowItem(
    chat: Chat,
    hintGray: Color,
    accentGreen: Color
) {
    val rowBg = Color(0xFF12232C)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 72.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(rowBg)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (chat.imageUrl != null) {
            AsyncImage(
                model = chat.imageUrl,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2A3B45))
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = chat.owner,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = chat.lastActive,
                    color = hintGray,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (chat.isTyping) {
                    Text(
                        text = "•••",
                        color = accentGreen,
                        style = MaterialTheme.typography.titleMedium
                    )
                } else {
                    val iconRes = when (chat.lastMessageType) {
                        MessageType.TEXT -> null
                        MessageType.FILE -> R.drawable.ic_file
                        MessageType.VOICE -> R.drawable.ic_voice
                    }

                    if (iconRes != null) {
                        Icon(
                            painter = painterResource(iconRes),
                            contentDescription = "Type",
                            tint = hintGray,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                    }

                    Text(
                        text = chat.lastMessage,
                        color = hintGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(Modifier.weight(1f))

                if (chat.unreadMessages > 0) {
                    UnreadBadge(count = chat.unreadMessages, accentGreen = accentGreen)
                }
            }
        }
    }
}

@Composable
private fun UnreadBadge(
    count: Int,
    accentGreen: Color
) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(accentGreen),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.toString(),
            color = Color(0xFF0F1B22),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
    }
}
