@file:OptIn(ExperimentalMaterial3Api::class)

package com.rynekryz.graphyte.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rynekryz.graphyte.R
import com.rynekryz.graphyte.ui.theme.Symbol

private class LicenseEntry(val title: String, val subtitle: String, val rawRes: Int?)

private val licenseEntries = listOf(
    LicenseEntry("Graphyte", "GNU General Public License v3.0 or later", R.raw.license_gpl3),
    LicenseEntry("AndroidX libraries", "core, activity-compose, lifecycle · Apache License 2.0", R.raw.license_apache2),
    LicenseEntry("Jetpack Compose", "ui, foundation, animation, runtime · Apache License 2.0", R.raw.license_apache2),
    LicenseEntry("Material 3", "components and theming · Apache License 2.0", R.raw.license_apache2),
    LicenseEntry("Kotlin", "standard library and coroutines by JetBrains · Apache License 2.0", R.raw.license_apache2),
    LicenseEntry("OkHttp", "HTTP client by Square · Apache License 2.0", R.raw.license_apache2),
    LicenseEntry("MapLibre Android", "map rendering engine · BSD 2-Clause", R.raw.license_bsd2),
    LicenseEntry("Google Sans Flex", "Device font by Google, used through the system font family", null),
)

@Composable
fun LicensesScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val haptics = rememberHaptics()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Open source licenses", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { haptics.click(); onBack() }) {
                        Symbol("arrow_back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                text = "Graphyte is free and open source. These are the licenses of everything the app uses. Tap one to read the full text.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 8.dp, bottom = 12.dp),
            )
            licenseEntries.forEachIndexed { index, entry ->
                val shape = when (index) {
                    0 -> RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
                    licenseEntries.lastIndex -> RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 24.dp, bottomEnd = 24.dp)
                    else -> RoundedCornerShape(4.dp)
                }
                var expanded by rememberSaveable(index) { mutableStateOf(false) }
                Surface(
                    shape = shape,
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 1.dp),
                ) {
                    Column {
                        val expandable = entry.rawRes != null
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = expandable) {
                                    haptics.click()
                                    expanded = !expanded
                                }
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(entry.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                                Spacer(Modifier.height(2.dp))
                                Text(
                                    entry.subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            if (expandable) {
                                Symbol(
                                    if (expanded) "expand_less" else "expand_more",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                        if (expandable && expanded) {
                            val text = remember(index) {
                                context.resources.openRawResource(entry.rawRes!!).bufferedReader().readText()
                            }
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily.Monospace,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp)
                                    .heightIn(max = 320.dp)
                                    .verticalScroll(rememberScrollState()),
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
