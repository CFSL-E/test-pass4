package com.example.passkeeper.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

data class PasswordItem(val id: Int, val title: String, val subtitle: String, val initial: String)

val dummyData = listOf(
    PasswordItem(1, "Google", "zzyzs666@gmail.com", "G"),
    PasswordItem(2, "Apple ID", "zzyzs666@icloud.com", "A"),
    PasswordItem(3, "Github", "zzyzs666", "G"),
    PasswordItem(4, "Bank", "**** 1234", "B")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassKeeperApp() {
    // Jetpack Compose WindowSizeClass would be used here normally to detect Foldable/Tablet
    // For this example, we use a basic scaffold
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add new password */ },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        bottomBar = {
            // In a real app this adaptively becomes a NavigationRail on Foldables/Tablets
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Filled.VpnKey, contentDescription = "All") },
                    label = { Text("所有密码") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Filled.Payment, contentDescription = "Cards") },
                    label = { Text("支付信息") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                    label = { Text("设置") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            
            // Search Bar
            SearchBarList()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarList() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize()) {
        Box(Modifier.padding(horizontal = 16.dp, vertical = 12.dp).fillMaxWidth()) {
            SearchBar(
                query = text,
                onQueryChange = { text = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text("搜索密码", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                leadingIcon = { Icon(Icons.Default.Menu, contentDescription = "Menu") },
                trailingIcon = { 
                     Surface(
                        shape = CircleShape, 
                        color = MaterialTheme.colorScheme.primary, 
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("Z", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
               // Search suggestions drop down
            }
        }

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

        // List with animations
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            delay(100)
            visible = true
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(dummyData) { index, item ->
                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically(
                        initialOffsetY = { 100 },
                        animationSpec = tween(durationMillis = 400, delayMillis = index * 50, easing = FastOutSlowInEasing)
                    ) + fadeIn(animationSpec = tween(400))
                ) {
                    PasswordCard(item)
                }
            }
        }
    }
}

@Composable
fun PasswordCard(item: PasswordItem) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { /* open detail */ },
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
            Column(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
