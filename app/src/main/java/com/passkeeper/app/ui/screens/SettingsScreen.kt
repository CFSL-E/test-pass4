package com.passkeeper.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ColorLens
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.PrivacyTip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onNavigateBack: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text("设置", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "返回")
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                SettingsSectionTitle("常规")
            }
            item {
                ListItem(
                    headlineContent = { Text("语言") },
                    supportingContent = { Text("简体中文") },
                    leadingContent = { Icon(Icons.Rounded.Language, contentDescription = null) },
                    modifier = Modifier.clickable { }
                )
            }
            item {
                ListItem(
                    headlineContent = { Text("主题外观") },
                    supportingContent = { Text("跟随系统") },
                    leadingContent = { Icon(Icons.Rounded.ColorLens, contentDescription = null) },
                    modifier = Modifier.clickable { }
                )
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                SettingsSectionTitle("安全")
            }
            item {
                ListItem(
                    headlineContent = { Text("生物识别解锁") },
                    supportingContent = { Text("已开启指纹/面容解锁") },
                    leadingContent = { Icon(Icons.Rounded.Fingerprint, contentDescription = null) },
                    modifier = Modifier.clickable { }
                )
            }
            item {
                ListItem(
                    headlineContent = { Text("修改主密码") },
                    supportingContent = { Text("定期修改以保障数据安全") },
                    leadingContent = { Icon(Icons.Rounded.Lock, contentDescription = null) },
                    modifier = Modifier.clickable { }
                )
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                SettingsSectionTitle("关于")
            }
            item {
                ListItem(
                    headlineContent = { Text("隐私政策") },
                    supportingContent = { Text("查看我们如何保护你的数据") },
                    leadingContent = { Icon(Icons.Rounded.PrivacyTip, contentDescription = null) },
                    modifier = Modifier.clickable { }
                )
            }
            item {
                ListItem(
                    headlineContent = { Text("版本信息") },
                    supportingContent = { Text("1.0.0 (Build 2026)") },
                    leadingContent = { Icon(Icons.Rounded.Info, contentDescription = null) },
                    modifier = Modifier.clickable { }
                )
            }
        }
    }
}

@Composable
fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
    )
}
