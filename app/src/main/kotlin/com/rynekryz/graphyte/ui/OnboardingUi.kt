@file:OptIn(ExperimentalMaterial3Api::class)

package com.rynekryz.graphyte.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.rynekryz.graphyte.ui.theme.Symbol
import kotlinx.coroutines.launch

private data class OobePage(
    val icon: String,
    val container: Color,
    val onContainer: Color,
    val title: String,
    val body: String,
    val lines: List<String> = emptyList(),
)

@Composable
private fun oobePages() = MaterialTheme.colorScheme.run {
    listOf(
        OobePage(
            icon = "map",
            container = primaryContainer,
            onContainer = onPrimaryContainer,
            title = "Welcome to Graphyte",
            body = "Maps without strings. Free, open source and private by design.",
        ),
        OobePage(
            icon = "explore",
            container = secondaryContainer,
            onContainer = onSecondaryContainer,
            title = "Explore anywhere",
            body = "Search any place in the world or just tap the map to see what is there.",
            lines = listOf(
                "Search for places, addresses and landmarks",
                "Tap a building to learn what it is",
                "Long press to drop a pin anywhere",
            ),
        ),
        OobePage(
            icon = "directions",
            container = tertiaryContainer,
            onContainer = onTertiaryContainer,
            title = "Get around with ease",
            body = "Routes for driving, walking and riding with turn by turn guidance.",
            lines = listOf(
                "Fastest route with live rerouting",
                "Voice guidance speaks every turn",
                "Keep the screen on while you drive",
            ),
        ),
        OobePage(
            icon = "palette",
            container = primaryContainer,
            onContainer = onPrimaryContainer,
            title = "Made for you",
            body = "Graphyte wears the colors of your wallpaper and adapts to your day.",
            lines = listOf(
                "Material You colors from your system theme",
                "Light, dark and an automatic night map",
                "Save the places you care about",
            ),
        ),
        OobePage(
            icon = "lock",
            container = secondaryContainer,
            onContainer = onSecondaryContainer,
            title = "Private by default",
            body = "No account. No tracking. No ads. Your data never leaves your device.",
            lines = listOf(
                "Search history and saved places stay local",
                "Map data from OpenStreetMap and its community",
                "Source code is open under GPLv3",
            ),
        ),
    )
}

@Composable
fun OnboardingScreen(onDone: () -> Unit) {
    val haptics = rememberHaptics()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val pages = oobePages()
    val pager = rememberPagerState { pages.size }
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED,
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { granted ->
        hasPermission = granted.values.any { it }
    }

    fun nextPage() {
        scope.launch {
            val target = pager.currentPage + 1
            if (target < pages.size) pager.animateScrollToPage(target) else onDone()
        }
    }

    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
        Column(Modifier.fillMaxSize().statusBarsPadding()) {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(
                    onClick = {
                        haptics.tick()
                        scope.launch { pager.animateScrollToPage(pager.currentPage - 1) }
                    },
                    enabled = pager.currentPage > 0,
                ) { Text("Back") }
                Spacer(Modifier.weight(1f))
                TextButton(
                    onClick = { haptics.tick(); onDone() },
                    enabled = pager.currentPage < pages.size - 1,
                ) { Text("Skip") }
            }

            HorizontalPager(state = pager, modifier = Modifier.weight(1f)) { page ->
                val p = pages[page]
                Column(
                    Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Surface(
                        shape = RoundedCornerShape(44.dp),
                        color = p.container,
                        modifier = Modifier.size(148.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Symbol(p.icon, size = 64.dp, tint = p.onContainer)
                        }
                    }
                    Text(
                        p.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 32.dp),
                    )
                    Text(
                        p.body,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 12.dp).widthIn(max = 320.dp),
                    )
                    if (p.lines.isNotEmpty()) {
                        Column(
                            Modifier.padding(top = 20.dp).widthIn(max = 340.dp),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            p.lines.forEach { line ->
                                Row(
                                    Modifier.fillMaxWidth().padding(vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Symbol("check_circle", size = 20.dp, tint = MaterialTheme.colorScheme.primary)
                                    Spacer(Modifier.width(12.dp))
                                    Text(line, style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }
            }

            Row(
                Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val offset = pager.currentPage + pager.currentPageOffsetFraction
                pages.indices.forEach { i ->
                    val selected = i == offset.toInt()
                    val width by animateDpAsState(
                        targetValue = if (selected) 24.dp else 8.dp,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMediumLow,
                        ),
                        label = "dot-width",
                    )
                    val color by animateColorAsState(
                        targetValue = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerHighest,
                        label = "dot-color",
                    )
                    Box(
                        Modifier
                            .padding(horizontal = 4.dp)
                            .size(width = width, height = 8.dp)
                            .clip(CircleShape)
                            .background(color),
                    )
                }
            }

            Row(
                Modifier.fillMaxWidth().navigationBarsPadding().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (pager.currentPage == pages.size - 1) {
                    if (!hasPermission) {
                        FilledTonalButton(
                            onClick = {
                                haptics.click()
                                permissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                    )
                                )
                            },
                            modifier = Modifier.weight(1f),
                        ) {
                            Symbol("my_location", size = 20.dp)
                            Spacer(Modifier.width(8.dp))
                            Text("Turn on location")
                        }
                    }
                    Button(
                        onClick = { haptics.confirm(); onDone() },
                        modifier = Modifier.weight(1f),
                    ) { Text("Get started") }
                } else {
                    Text(
                        "You can change everything later in Settings",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f),
                    )
                    Button(onClick = { haptics.click(); nextPage() }) { Text("Next") }
                }
            }
        }
    }
}
