package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.EventEntity
import com.example.ui.theme.*
import com.example.ui.viewmodel.ReunionViewModel

@Composable
fun EventsScheduleScreen(
    viewModel: ReunionViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val events by viewModel.eventsList.collectAsState()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.openAddEventDialog() },
                containerColor = SaffronPrimary,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("कार्यक्रम जोडा", fontWeight = FontWeight.Bold) },
                modifier = Modifier.testTag("fab_add_event")
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("events_schedule_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Venue Card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, SaffronPrimary.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF831843).copy(alpha = 0.08f), Color(0xFFC2410C).copy(alpha = 0.08f))
                                )
                            )
                            .padding(18.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = SaffronPrimary,
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Place,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = "गेट-टुगेदर स्थळ व परिसर",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "श्री छत्रपती शिवाजी महाविद्यालय, उमरगा",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = SaffronPrimary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "📍 पत्ता: मुख्य सभागृह व महाविद्यालय परिसर, सोलापूर-हैदराबाद राष्ट्रीय महामार्ग, उमरगा, जि. धाराशिव (उस्मानाबाद) - ४१३६०६",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { viewModel.openCollegeMap(context) },
                                    colors = ButtonDefaults.buttonColors(containerColor = BlueCall),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("btn_event_screen_map")
                                ) {
                                    Icon(Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("नकाशावर पहा (Map)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }

                                OutlinedButton(
                                    onClick = { viewModel.shareGoogleFormLink(context) },
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("निमंत्रण पाठवा", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Schedule Section Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "दिवसभराची अधिकृत रूपरेषा",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "एकूण ${events.size} सत्रे व उपक्रम",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = GoldenAmber.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "सकाळी ०९ ते संध्याकाळी ०६",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldenAmber,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Timeline items
            items(events, key = { it.id }) { event ->
                EventTimelineCard(
                    event = event,
                    onEditClick = { viewModel.openEditEventDialog(event) },
                    onDeleteClick = { viewModel.deleteEvent(event) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun EventTimelineCard(
    event: EventEntity,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = getEventIcon(event.iconName)
    val accentColor = if (event.isHighlight) SaffronPrimary else MaroonPrimary

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = if (event.isHighlight) 1.5.dp else 1.dp,
                color = if (event.isHighlight) SaffronPrimary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .testTag("event_card_${event.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Time Slot Tag
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = accentColor.copy(alpha = 0.12f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = event.timeSlot,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )
                    }
                }

                if (event.isHighlight) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = GoldenAmber.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "⭐ मुख्य आकर्षण",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldenAmber,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.Top) {
                Surface(
                    shape = CircleShape,
                    color = accentColor.copy(alpha = 0.15f),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = event.titleMarathi,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (event.titleEnglish.isNotBlank()) {
                        Text(
                            text = event.titleEnglish,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = event.location,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = event.descriptionMarathi,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "संपादित करा",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }

                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "हटवा",
                        tint = RedDecline.copy(alpha = 0.8f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

fun getEventIcon(name: String): ImageVector {
    return when (name.lowercase()) {
        "coffee" -> Icons.Default.Coffee
        "sparkles" -> Icons.Default.AutoAwesome
        "school" -> Icons.Default.School
        "record_voice_over" -> Icons.Default.RecordVoiceOver
        "restaurant" -> Icons.Default.Restaurant
        "celebration" -> Icons.Default.Celebration
        "photo_camera" -> Icons.Default.PhotoCamera
        "handshake" -> Icons.Default.Handshake
        "emoji_emotions" -> Icons.Default.EmojiEmotions
        else -> Icons.Default.EventNote
    }
}
