package com.passkeeper.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

data class PasswordItem(val id: Int, val title: String, val subtitle: String, val initial: String)

val dummyData = listOf(
    PasswordItem(1, "Google", "zzyzs666@gmail.com", "G"),
    PasswordItem(2, "Apple ID", "zzyzs666@icloud.com", "A"),
    PasswordItem(3, "Github", "zzyzs666", "G")
)

@Composable
fun HomeScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToAdd: () -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAdd,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(16.dp),
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
            ) {
                Icon(Icons.Rounded.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(Modifier.weight(1f)) {
                PasswordListContent(onNavigateToSettings, onNavigateToDetail)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordListContent(onNavigateToSettings: () -> Unit, onNavigateToDetail: (Int) -> Unit) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(Modifier.fillMaxSize()) {
        SearchBar(
            query = text,
            onQueryChange = { text = it },
            onSearch = { focusManager.clearFocus() },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text("搜索密码", color = MaterialTheme.colorScheme.onSurfaceVariant) },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
            ),
            leadingIcon = {
                if (active) {
                    IconButton(onClick = { 
                        active = false
                        text = ""
                        focusManager.clearFocus()
                    }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                } else {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Rounded.Menu, contentDescription = "Menu")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = if (active) 0.dp else 16.dp, vertical = if (active) 0.dp else 12.dp)
        ) {
            // Options could go here
        }

        // List with animations
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            delay(50)
            visible = true
        }

        val filteredData = if (text.isBlank()) {
            dummyData
        } else {
            dummyData.filter {
                it.title.contains(text, ignoreCase = true) || it.subtitle.contains(text, ignoreCase = true)
            }
        }

        if (!active) {
            // Filter chips
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = true,
                    onClick = { },
                    label = { Text("全部") }
                )
                FilterChip(
                    selected = false,
                    onClick = { },
                    label = { Text("最近添加") }
                )
                FilterChip(
                    selected = false,
                    onClick = { },
                    label = { Text("常用") }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 8.dp, bottom = 88.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(filteredData) { index, item ->
                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically(
                        initialOffsetY = { 50 },
                        animationSpec = tween(durationMillis = 400, delayMillis = index * 50, easing = FastOutSlowInEasing)
                    ) + fadeIn(animationSpec = tween(400))
                ) {
                    PasswordCard(item, onClick = { onNavigateToDetail(item.id) })
                }
            }
        }
    }
}

@Composable
fun PasswordCard(item: PasswordItem, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() },
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            },
            supportingContent = {
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.initial,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            },
            colors = ListItemDefaults.colors(
                containerColor = androidx.compose.ui.graphics.Color.Transparent
            )
        )
    }
}
