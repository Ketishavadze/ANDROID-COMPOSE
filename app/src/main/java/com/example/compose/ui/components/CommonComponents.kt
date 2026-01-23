@OptIn(ExperimentalMaterial3Api::class)
package com.example.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.compose.R
import com.example.compose.ui.graphics.AppColors

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onApply: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
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
                    contentDescription = "Search",
                    tint = AppColors.AccentMint
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = AppColors.SearchFieldBg,
                unfocusedContainerColor = AppColors.SearchFieldBg,
                disabledContainerColor = AppColors.SearchFieldBg,

                focusedTextColor = AppColors.White,
                unfocusedTextColor = AppColors.White,

                focusedPlaceholderColor = AppColors.HintGray,
                unfocusedPlaceholderColor = AppColors.HintGray,

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = AppColors.White
            ),
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(Modifier.width(12.dp))

        IconButton(
            onClick = onApply,
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(AppColors.AccentMint)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_filter),
                contentDescription = "Apply search",
                tint = AppColors.OnAccent
            )
        }
    }
}

@Composable
fun UnreadBadge(
    count: Int,
    modifier: Modifier = Modifier
) {
    if (count <= 0) return

    Box(
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(AppColors.AccentMint),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.toString(),
            color = AppColors.OnAccent,
            fontWeight = FontWeight.Bold
        )
    }
}
