package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen

val AvatarPalette = listOf(
    Color(0xFFC2410C), // Deep Saffron
    Color(0xFF0D9488), // Teal
    Color(0xFF2563EB), // Blue
    Color(0xFF7C3AED), // Violet
    Color(0xFFDB2777), // Pink
    Color(0xFFD97706), // Amber
    Color(0xFF059669), // Emerald
    Color(0xFF831843)  // Maroon
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReunionTopAppBar(
    currentScreen: AppScreen,
    onOpenGoogleForm: () -> Unit,
    onOpenImportExport: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "श्री छत्रपती शिवाजी महाविद्यालय",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = "उमरगा • १९९७ बारावी वर्ग गेट-टुगेदर",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        actions = {
            IconButton(
                onClick = onOpenGoogleForm,
                modifier = Modifier.testTag("action_google_form")
            ) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = "गुगल फॉर्म",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = onOpenImportExport,
                modifier = Modifier.testTag("action_import_export")
            ) {
                Icon(
                    imageVector = Icons.Default.SyncAlt,
                    contentDescription = "आयात व निर्यात",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
    )
}

@Composable
fun ReunionBottomNavigationBar(
    currentScreen: AppScreen,
    onScreenSelected: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp,
        windowInsets = WindowInsets.navigationBars,
        modifier = modifier.testTag("bottom_nav_bar")
    ) {
        val items = listOf(
            Triple(AppScreen.HOME, "मुख्य", Icons.Default.Home),
            Triple(AppScreen.CLASSMATES, "मित्र परिवार", Icons.Default.Group),
            Triple(AppScreen.EVENTS, "कार्यक्रम", Icons.Default.EventNote),
            Triple(AppScreen.MEMORIES, "आठवणी", Icons.Default.AutoAwesome)
        )

        items.forEach { (screen, label, icon) ->
            val isSelected = currentScreen == screen
            NavigationBarItem(
                selected = isSelected,
                onClick = { onScreenSelected(screen) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 11.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.testTag("nav_item_${screen.name.lowercase()}")
            )
        }
    }
}

@Composable
fun ClassmateAvatar(
    name: String,
    colorIndex: Int,
    sizeDp: Int = 48,
    modifier: Modifier = Modifier
) {
    val bgColor = AvatarPalette.getOrElse(colorIndex.coerceAtLeast(0) % AvatarPalette.size) { SaffronPrimary }
    val initial = name.trim().firstOrNull()?.toString() ?: "म"

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(sizeDp.dp)
            .clip(CircleShape)
            .background(bgColor)
            .border(1.5.dp, Color.White.copy(alpha = 0.5f), CircleShape)
    ) {
        Text(
            text = initial,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = (sizeDp * 0.44).sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RsvpBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, icon) = when {
        status.contains("उपस्थित") || status.contains("Attending") ->
            Triple(Color(0xFFDCFCE7), Color(0xFF166534), Icons.Default.CheckCircle)
        status.contains("नक्की") || status.contains("Tentative") ->
            Triple(Color(0xFFFEF3C7), Color(0xFF92400E), Icons.Default.Help)
        else ->
            Triple(Color(0xFFFEE2E2), Color(0xFF991B1B), Icons.Default.Cancel)
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = status,
                color = textColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun QuickStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(accentColor.copy(alpha = 0.15f))
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
