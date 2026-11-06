package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ClassmateEntity
import com.example.ui.components.ClassmateAvatar
import com.example.ui.components.QuickStatCard
import com.example.ui.components.RsvpBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.ReunionViewModel

@Composable
fun HomeScreen(
    viewModel: ReunionViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val stats by viewModel.stats.collectAsState()
    val classmates by viewModel.filteredClassmates.collectAsState()
    val events by viewModel.eventsList.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Celebratory Banner
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hero_banner_card")
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFF9A3412), Color(0xFF831843), Color(0xFF4C0519))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = GoldenAmber.copy(alpha = 0.25f),
                                border = null
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.School,
                                        contentDescription = null,
                                        tint = GoldenLight,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "१९९५ - १९९७ बॅच",
                                        color = GoldenLight,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.2f),
                                modifier = Modifier.size(34.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Celebration,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "श्री छत्रपती शिवाजी महाविद्यालय",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )

                        Text(
                            text = "उमरगा • बारावी वर्ग मित्रमेळावा",
                            style = MaterialTheme.typography.titleMedium,
                            color = GoldenLight,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "२५+ वर्षांनंतर पुन्हा एकत्र! आपल्या महाविद्यालयातील ते सोनेरी दिवस, मैत्रीचे बंध आणि जुन्या आठवणींना उजाळा देणारा भव्य सोहळा.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.9f),
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { viewModel.setScreen(AppScreen.CLASSMATES) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GoldenAmber,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_view_friends")
                            ) {
                                Icon(Icons.Default.People, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("मित्र यादी पहा", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }

                            OutlinedButton(
                                onClick = { viewModel.setScreen(AppScreen.EVENTS) },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                                border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(Color.White, Color.White))),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_view_events")
                            ) {
                                Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("कार्यक्रम रूपरेषा", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Google Form Data Center Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, SaffronPrimary.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    .testTag("google_form_hub_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(SaffronPrimary.copy(alpha = 0.15f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Assignment,
                                contentDescription = null,
                                tint = SaffronPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "गुगल फॉर्म माहिती संकलन",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "मित्रांचे फोटो, काम, पत्ता व विचार संकलन",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "गेट-टुगेदरसाठी गुगल फॉर्मद्वारे सर्व मित्रांची माहिती एकत्रित केली जात आहे. खालील बटनांचा वापर करून नवीन माहिती जोडा किंवा लिंक मित्रांना पाठवा.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilledTonalButton(
                            onClick = { viewModel.openAddClassmateDialog() },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_add_friend_manual")
                        ) {
                            Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("+ मित्र जोडा", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { viewModel.shareGoogleFormLink(context) },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1.1f)
                                .testTag("btn_share_form_link")
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("फॉर्म पाठवा", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.openGoogleForm(context) },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_open_form_browser")
                        ) {
                            Icon(Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("फॉर्म उघडा", fontSize = 11.sp)
                        }

                        OutlinedButton(
                            onClick = { viewModel.openImportExportDialog() },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_open_sync_dialog")
                        ) {
                            Icon(Icons.Default.SyncAlt, contentDescription = null, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("डेटा आयात/CSV", fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Stats Grid
        item {
            Column {
                Text(
                    text = "नोंदणी व उपस्थिती आकडेवारी",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickStatCard(
                        title = "एकूण नोंदणी",
                        value = "${stats.totalCount}",
                        icon = Icons.Default.Groups,
                        accentColor = SaffronPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    QuickStatCard(
                        title = "नक्की उपस्थित",
                        value = "${stats.attendingCount}",
                        icon = Icons.Default.CheckCircle,
                        accentColor = EmeraldGreen,
                        modifier = Modifier.weight(1f)
                    )
                    QuickStatCard(
                        title = "शहरे / गावे",
                        value = "${stats.uniqueCitiesCount}",
                        icon = Icons.Default.LocationCity,
                        accentColor = BlueCall,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Venue & Countdown Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                    .testTag("venue_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = SaffronPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "गेट-टुगेदर स्थळ व तारीख",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "📍 स्थळ: श्री छत्रपती शिवाजी महाविद्यालय परिसर / मुख्य सभागृह, उमरगा, जि. धाराशिव (उस्मानाबाद)",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "🗓️ वेळ: सकाळी ०९:०० ते सायंकाळी ०६:०० पर्यंत",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

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
                                .testTag("btn_open_map")
                        ) {
                            Icon(Icons.Default.Directions, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Google Maps वर पहा", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = { viewModel.setScreen(AppScreen.EVENTS) },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_view_schedule_from_home")
                        ) {
                            Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("वेळापत्रक पहा", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Recent Classmates Preview
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "वर्गमित्र झलक",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    TextButton(
                        onClick = { viewModel.setScreen(AppScreen.CLASSMATES) },
                        modifier = Modifier.testTag("btn_see_all_classmates")
                    ) {
                        Text("सर्व पहा (${classmates.size})", color = SaffronPrimary, fontWeight = FontWeight.Bold)
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = SaffronPrimary, modifier = Modifier.size(18.dp))
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(classmates.take(6)) { friend ->
                        HomeClassmateCard(
                            classmate = friend,
                            onCardClick = { viewModel.selectClassmate(friend) },
                            onCallClick = { viewModel.dialPhone(context, friend.phone) },
                            onWhatsappClick = { viewModel.openWhatsApp(context, friend.whatsapp, friend.fullName) }
                        )
                    }
                }
            }
        }

        // Upcoming Event Highlights
        item {
            Column {
                Text(
                    text = "कार्यक्रमाची मुख्य आकर्षणे",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))

                events.filter { it.isHighlight }.take(3).forEach { evt ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = SaffronPrimary.copy(alpha = 0.15f),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = SaffronPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = evt.titleMarathi,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "⏰ ${evt.timeSlot} • 📍 ${evt.location}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeClassmateCard(
    classmate: ClassmateEntity,
    onCardClick: () -> Unit,
    onCallClick: () -> Unit,
    onWhatsappClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .width(220.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .clickable { onCardClick() }
            .testTag("home_friend_card_${classmate.id}")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                ClassmateAvatar(
                    name = classmate.fullName,
                    colorIndex = classmate.avatarColorIndex,
                    sizeDp = 44
                )
                RsvpBadge(status = classmate.attendanceStatus)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = classmate.fullName,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = classmate.jobTitle.ifBlank { classmate.occupationCategory },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 11.sp
            )

            Text(
                text = "📍 ${classmate.currentCity} (मूळ: ${classmate.nativeVillage})",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                IconButton(
                    onClick = onCallClick,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(BlueCall.copy(alpha = 0.12f))
                ) {
                    Icon(Icons.Default.Phone, contentDescription = "कॉल करा", tint = BlueCall, modifier = Modifier.size(16.dp))
                }

                IconButton(
                    onClick = onWhatsappClick,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(EmeraldGreen.copy(alpha = 0.12f))
                ) {
                    Icon(Icons.Default.Chat, contentDescription = "व्हॉट्सॲप", tint = EmeraldGreen, modifier = Modifier.size(16.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                FilledTonalButton(
                    onClick = onCardClick,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text("माहिती", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
