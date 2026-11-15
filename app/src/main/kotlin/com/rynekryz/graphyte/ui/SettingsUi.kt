@file:OptIn(ExperimentalMaterial3Api::class)

package com.rynekryz.graphyte.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.content.pm.PackageManager
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.rynekryz.graphyte.data.MapStyleOption
import com.rynekryz.graphyte.data.Settings
import com.rynekryz.graphyte.data.ThemeMode
import com.rynekryz.graphyte.data.UnitsMode
import com.rynekryz.graphyte.ui.theme.Symbol

@Composable
private fun AvatarBadge(avatar: Bitmap?, size: Dp) {
    Box(
        Modifier.size(size).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center,
    ) {
        if (avatar != null) {
            Image(
                bitmap = avatar.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Symbol("person", size = size / 2, tint = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}

private enum class RoundingType { TOP, MIDDLE, BOTTOM, SINGLE }

private fun roundingShape(type: RoundingType) = when (type) {
    RoundingType.TOP -> RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
    RoundingType.BOTTOM -> RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 24.dp, bottomEnd = 24.dp)
    RoundingType.MIDDLE -> RoundedCornerShape(4.dp)
    RoundingType.SINGLE -> RoundedCornerShape(24.dp)
}

@Composable
private fun SettingsRow(
    icon: String,
    title: String,
    subtitle: String? = null,
    roundingType: RoundingType = RoundingType.SINGLE,
    onClick: (() -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    val interaction = remember { MutableInteractionSource() }
    Surface(
        onClick = onClick ?: {},
        enabled = onClick != null,
        interactionSource = interaction,
        shape = roundingShape(roundingType),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        modifier = Modifier.fillMaxWidth().pressScale(interaction, 0.97f),
    ) {
        Row(
            Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Symbol(icon, size = 24.dp, tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                if (!subtitle.isNullOrBlank()) {
                    Text(
                        subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            trailing?.invoke()
        }
    }
}

@Composable
private fun SettingsGroupLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 8.dp, top = 24.dp, bottom = 12.dp),
    )
}

@Composable
private fun SettingsGroup(content: @Composable ColumnScope.() -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp), content = content)
}

@Composable
private fun Segmented(
    options: List<Pair<String, String>>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
) {
    val haptics = rememberHaptics()
    var width by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val segment = width / options.size.toFloat()
    val x by animateFloatAsState(
        targetValue = segment * selectedIndex,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        label = "segmented-x",
    )
    Box(
        Modifier
            .fillMaxWidth()
            .height(40.dp)
            .onSizeChanged { width = it.width },
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .offset(x = with(density) { x.toDp() })
                .width(with(density) { segment.toDp() })
                .fillMaxHeight(),
        ) {}
        Row(Modifier.fillMaxSize()) {
            options.forEachIndexed { i, (_, label) ->
                val on = i == selectedIndex
                val color by animateColorAsState(
                    targetValue = if (on) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                    label = "segmented-label",
                )
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { haptics.click(); onSelect(i) },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(label, style = MaterialTheme.typography.labelLarge, color = color, maxLines = 1)
                }
            }
        }
    }
}

@Composable
private fun EmbeddedChoice(
    icon: String,
    title: String,
    subtitle: String,
    roundingType: RoundingType,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        shape = roundingShape(roundingType),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Symbol(icon, size = 24.dp, tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                    Text(
                        subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun SwitchRow(
    icon: String,
    title: String,
    subtitle: String?,
    checked: Boolean,
    roundingType: RoundingType = RoundingType.SINGLE,
    onChange: (Boolean) -> Unit,
) {
    val haptics = rememberHaptics()
    SettingsRow(
        icon = icon,
        title = title,
        subtitle = subtitle,
        roundingType = roundingType,
        onClick = { onChange(!checked) },
        trailing = {
            Switch(
                checked = checked,
                onCheckedChange = { haptics.click(); onChange(it) },
            )
        },
    )
}

@Composable
fun SettingsScreen(
    settings: Settings,
    avatar: Bitmap?,
    recentsCount: Int,
    savedCount: Int,
    mapStyle: MapStyleOption,
    onDismiss: () -> Unit,
    onName: (String) -> Unit,
    onVoice: (Boolean) -> Unit,
    onTestVoice: () -> Unit,
    onKeepScreenOn: (Boolean) -> Unit,
    onUnits: (UnitsMode) -> Unit,
    onTheme: (ThemeMode) -> Unit,
    onHaptics: (Boolean) -> Unit,
    onRotateGestures: (Boolean) -> Unit,
    onTiltGestures: (Boolean) -> Unit,
    onAutoNight: (Boolean) -> Unit,
    onMapStyle: (MapStyleOption) -> Unit,
    onPickPhoto: () -> Unit,
    onRemovePhoto: () -> Unit,
    onClearRecents: () -> Unit,
    onClearSaved: () -> Unit,
    onAbout: () -> Unit,
    onLicenses: () -> Unit,
) {
    val haptics = rememberHaptics()
    val context = LocalContext.current
    var editingName by rememberSaveable { mutableStateOf(false) }
    val versionName = remember(context) {
        runCatching {
            val pm = context.packageManager
            val info = if (Build.VERSION.SDK_INT >= 33) {
                pm.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
            } else {
                @Suppress("DEPRECATION")
                pm.getPackageInfo(context.packageName, 0)
            }
            info.versionName
        }.getOrNull() ?: "?"
    }
    val nameDisplay = settings.name.ifBlank { "Set your name" }

    fun openUrl(url: String) {
        runCatching { context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri())) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { haptics.click(); onDismiss() }) {
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
                .padding(horizontal = 16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AvatarBadge(avatar, 88.dp)
                Text(
                    nameDisplay,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .clickable { haptics.click(); editingName = true },
                    color = if (settings.name.isBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                )
                if (settings.name.isBlank() || avatar == null) {
                    Text(
                        "Stored on this device only",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp),
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 12.dp),
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        onClick = { haptics.click(); onPickPhoto() },
                    ) {
                        Box(Modifier.size(44.dp), contentAlignment = Alignment.Center) {
                            Symbol("photo_camera", size = 22.dp)
                        }
                    }
                    if (avatar != null) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            onClick = { haptics.click(); onRemovePhoto() },
                        ) {
                            Box(Modifier.size(44.dp), contentAlignment = Alignment.Center) {
                                Symbol("delete", size = 22.dp)
                            }
                        }
                    }
                }
            }

            SettingsGroupLabel("Navigation")
            SettingsGroup {
                SettingsRow(
                    "volume_up",
                    "Voice guidance",
                    subtitle = "Announce turns, reroutes and arrival",
                    roundingType = RoundingType.TOP,
                    onClick = { onVoice(!settings.voice) },
                    trailing = {
                        Switch(
                            checked = settings.voice,
                            onCheckedChange = { haptics.click(); onVoice(it) },
                        )
                    },
                )
                SettingsRow(
                    "graphic_eq",
                    "Test voice",
                    subtitle = "Play a sample voice announcement",
                    roundingType = RoundingType.BOTTOM,
                    onClick = onTestVoice,
                )
            }

            SettingsGroupLabel("Display")
            SettingsGroup {
                EmbeddedChoice(
                    "dark_mode",
                    "Theme",
                    "Match system, always light or always dark",
                    roundingType = RoundingType.TOP,
                ) {
                    Segmented(
                        options = ThemeMode.entries.map { it.name to it.label },
                        selectedIndex = ThemeMode.entries.indexOf(settings.theme),
                        onSelect = { haptics.click(); onTheme(ThemeMode.entries[it]) },
                    )
                }
                EmbeddedChoice(
                    "straighten",
                    "Distance units",
                    "Autodetect, kilometers or miles",
                    roundingType = RoundingType.BOTTOM,
                ) {
                    Segmented(
                        options = UnitsMode.entries.map { it.name to it.label },
                        selectedIndex = UnitsMode.entries.indexOf(settings.units),
                        onSelect = { haptics.click(); onUnits(UnitsMode.entries[it]) },
                    )
                }
            }

            SettingsGroupLabel("Map")
            SettingsGroup {
                EmbeddedChoice(
                    "layers",
                    "Default map type",
                    "The look of the tiles when the app opens",
                    roundingType = RoundingType.TOP,
                ) {
                    Segmented(
                        options = MapStyleOption.entries.map { it.name to it.label },
                        selectedIndex = MapStyleOption.entries.indexOf(mapStyle),
                        onSelect = { haptics.click(); onMapStyle(MapStyleOption.entries[it]) },
                    )
                }
                SwitchRow(
                    icon = "nightlight",
                    title = "Automatic night map",
                    subtitle = "Use the night map while dark theme is on",
                    checked = settings.autoNight,
                    roundingType = RoundingType.BOTTOM,
                    onChange = onAutoNight,
                )
            }

            SettingsGroupLabel("Gestures")
            SettingsGroup {
                SwitchRow(
                    icon = "screen_rotation",
                    title = "Map rotation",
                    subtitle = "Spin the map with two fingers",
                    checked = settings.rotateGestures,
                    roundingType = RoundingType.TOP,
                    onChange = onRotateGestures,
                )
                SwitchRow(
                    icon = "3d_rotation",
                    title = "Tilt the map",
                    subtitle = "See buildings in 3D by swiping with two fingers",
                    checked = settings.tiltGestures,
                    roundingType = RoundingType.BOTTOM,
                    onChange = onTiltGestures,
                )
            }

            SettingsGroupLabel("Convenience")
            SettingsGroup {
                SwitchRow(
                    icon = "brightness_high",
                    title = "Keep screen on",
                    subtitle = "Screen won't turn off while using Graphyte",
                    checked = settings.keepScreenOn,
                    roundingType = RoundingType.TOP,
                    onChange = onKeepScreenOn,
                )
                SwitchRow(
                    icon = "vibration",
                    title = "Haptic feedback",
                    subtitle = "Small vibrations on actions",
                    checked = settings.haptics,
                    roundingType = RoundingType.BOTTOM,
                    onChange = onHaptics,
                )
            }

            SettingsGroupLabel("Privacy")
            SettingsGroup {
                SettingsRow(
                    "history",
                    "Clear search history",
                    subtitle = if (recentsCount > 0) "$recentsCount saved" else "Empty",
                    roundingType = RoundingType.TOP,
                    onClick = onClearRecents,
                )
                SettingsRow(
                    "bookmark_remove",
                    "Clear saved places",
                    subtitle = if (savedCount > 0) "$savedCount saved" else "Empty",
                    roundingType = RoundingType.BOTTOM,
                    onClick = onClearSaved,
                )
            }

            SettingsGroupLabel("About")
            SettingsGroup {
                SettingsRow(
                    "map",
                    "Graphyte",
                    subtitle = "v$versionName · by Ryne",
                    roundingType = RoundingType.TOP,
                    onClick = onAbout,
                )
                SettingsRow(
                    "gavel",
                    "Open source licenses",
                    subtitle = "Licenses of everything the app uses",
                    roundingType = RoundingType.MIDDLE,
                    onClick = onLicenses,
                )
                SettingsRow(
                    "balance",
                    "License",
                    subtitle = "GNU General Public License v3.0 (GPLv3)\nFree, open source software. You may use, study, share and improve this software.",
                    roundingType = RoundingType.BOTTOM,
                    onClick = { openUrl("https://github.com/rynekryz/graphyte/blob/main/LICENSE") },
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }

    if (editingName) {
        NameDialog(current = settings.name, onDismiss = { editingName = false }, onConfirm = {
            onName(it)
            editingName = false
        })
    }
}

@Composable
private fun NameDialog(current: String, onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var name by rememberSaveable { mutableStateOf(current) }
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Your name") },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it.trimStart().take(24) },
                singleLine = true,
                placeholder = { Text("Set your name") },
            )
        },
        confirmButton = { TextButton(onClick = { onConfirm(name) }, enabled = name.isNotBlank()) { Text("Save") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } },
    )
}
