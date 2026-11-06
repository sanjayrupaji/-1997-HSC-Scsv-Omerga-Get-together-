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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ClassmateEntity
import com.example.ui.components.ClassmateAvatar
import com.example.ui.components.RsvpBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.ReunionViewModel

val CategoryFilterOptions = listOf(
    "सर्व",
    "व्यवसाय",
    "वैद्यकीय",
    "शिक्षण",
    "शासकीय",
    "शेती",
    "अभियांत्रिकी",
    "इतर"
)

val RsvpFilterOptions = listOf(
    "सर्व",
    "उपस्थित राहणार",
    "नक्की नाही"
)

@Composable
fun ClassmatesListScreen(
    viewModel: ReunionViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val classmates by viewModel.filteredClassmates.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.categoryFilter.collectAsState()
    val selectedRsvp by viewModel.attendanceFilter.collectAsState()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.openAddClassmateDialog() },
                containerColor = SaffronPrimary,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.PersonAdd, contentDescription = null) },
                text = { Text("नवीन मित्र जोडा", fontWeight = FontWeight.Bold) },
                modifier = Modifier.testTag("fab_add_classmate")
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("classmates_list_screen")
        ) {
            // Search Bar & Filter Header
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.setSearchQuery(it) },
                        placeholder = { Text("नाव, गाव, शहर, व्यवसाय किंवा आठवण शोधा...") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = SaffronPrimary)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotBlank()) {
                                IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = "शोध साफ करा")
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SaffronPrimary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("search_field")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Profession Category Filter Chips
                    Text(
                        text = "व्यवसाय/क्षेत्र निहाय:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(CategoryFilterOptions) { cat ->
                            val isSelected = selectedCategory == cat
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.setCategoryFilter(cat) },
                                label = { Text(cat, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SaffronPrimary,
                                    selectedLabelColor = Color.White
                                ),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.testTag("chip_cat_$cat")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // RSVP Attendance Filter Chips
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            RsvpFilterOptions.forEach { rsvp ->
                                val isSelected = selectedRsvp == rsvp
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { viewModel.setAttendanceFilter(rsvp) },
                                    label = { Text(rsvp, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = GoldenAmber,
                                        selectedLabelColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.testTag("chip_rsvp_$rsvp")
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = "${classmates.size} मित्र",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // List of classmates
            if (classmates.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "कोणतेही मित्र आढळले नाहीत",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "वेगळे नाव किंवा गाव शोधून पहा अथवा नवीन मित्राची नोंदणी करा.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                viewModel.setSearchQuery("")
                                viewModel.setCategoryFilter("सर्व")
                                viewModel.setAttendanceFilter("सर्व")
                            },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("सर्व फिल्टर्स काढा")
                        }
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(classmates, key = { it.id }) { classmate ->
                        ClassmateListItemCard(
                            classmate = classmate,
                            onCardClick = { viewModel.selectClassmate(classmate) },
                            onCallClick = { viewModel.dialPhone(context, classmate.phone) },
                            onWhatsappClick = { viewModel.openWhatsApp(context, classmate.whatsapp, classmate.fullName) },
                            onShareClick = { viewModel.shareClassmateCard(context, classmate) },
                            onEditClick = { viewModel.openEditClassmateDialog(classmate) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ClassmateListItemCard(
    classmate: ClassmateEntity,
    onCardClick: () -> Unit,
    onCallClick: () -> Unit,
    onWhatsappClick: () -> Unit,
    onShareClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .clickable { onCardClick() }
            .testTag("classmate_card_${classmate.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                ClassmateAvatar(
                    name = classmate.fullName,
                    colorIndex = classmate.avatarColorIndex,
                    sizeDp = 52
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = classmate.fullName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        RsvpBadge(status = classmate.attendanceStatus)
                    }

                    if (classmate.nickname.isNotBlank()) {
                        Text(
                            text = "टोपणनाव: \"${classmate.nickname}\"",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaroonPrimary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "${classmate.jobTitle.ifBlank { classmate.occupationCategory }} • ${classmate.organization}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "सध्या: ${classmate.currentCity} • मूळ: ${classmate.nativeVillage}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // Thoughts / Memories preview if available
            if (classmate.thoughtsAndMemories.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = CreamBackground,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatQuote,
                            contentDescription = null,
                            tint = GoldenAmber,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = classmate.thoughtsAndMemories,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    // Call Button
                    IconButton(
                        onClick = onCallClick,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(BlueCall.copy(alpha = 0.12f))
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = "कॉल", tint = BlueCall, modifier = Modifier.size(18.dp))
                    }

                    // WhatsApp Button
                    IconButton(
                        onClick = onWhatsappClick,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(EmeraldGreen.copy(alpha = 0.12f))
                    ) {
                        Icon(Icons.Default.Chat, contentDescription = "व्हॉट्सॲप", tint = EmeraldGreen, modifier = Modifier.size(18.dp))
                    }

                    // Share Card
                    IconButton(
                        onClick = onShareClick,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "शेअर", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
                    }

                    // Edit
                    IconButton(
                        onClick = onEditClick,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "संपादित करा", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
                    }
                }

                FilledTonalButton(
                    onClick = onCardClick,
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text("सविस्तर माहिती", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
                }
            }
        }
    }
}
