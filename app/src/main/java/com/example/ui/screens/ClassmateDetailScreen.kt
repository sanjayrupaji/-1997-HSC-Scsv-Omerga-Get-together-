package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ClassmateEntity
import com.example.ui.components.ClassmateAvatar
import com.example.ui.components.RsvpBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.ReunionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassmateDetailScreen(
    classmate: ClassmateEntity,
    viewModel: ReunionViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    BackHandler {
        viewModel.selectClassmate(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(classmate.fullName, fontWeight = FontWeight.Bold, maxLines = 1) },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.selectClassmate(null) },
                        modifier = Modifier.testTag("btn_back_detail")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "मागे जा")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.shareClassmateCard(context, classmate) },
                        modifier = Modifier.testTag("action_share_detail")
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "शेअर करा")
                    }
                    IconButton(
                        onClick = { viewModel.openEditClassmateDialog(classmate) },
                        modifier = Modifier.testTag("action_edit_detail")
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "संपादित करा")
                    }
                    IconButton(
                        onClick = { showDeleteConfirmation = true },
                        modifier = Modifier.testTag("action_delete_detail")
                    ) {
                        Icon(Icons.Default.DeleteOutline, contentDescription = "हटवा", tint = RedDecline)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .testTag("classmate_detail_screen")
        ) {
            // Profile Header Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color(0xFF831843), Color(0xFF9A3412), Color(0xFFC2410C))
                        )
                    )
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ClassmateAvatar(
                        name = classmate.fullName,
                        colorIndex = classmate.avatarColorIndex,
                        sizeDp = 84,
                        modifier = Modifier.border(3.dp, Color.White, CircleShape)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = classmate.fullName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    if (classmate.nickname.isNotBlank()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "टोपणनाव: \"${classmate.nickname}\"",
                            style = MaterialTheme.typography.titleSmall,
                            color = GoldenLight,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "१९९७ HSC • ${classmate.stream}",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        RsvpBadge(status = classmate.attendanceStatus)
                    }
                }
            }

            // Quick Connect Action Bar
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ActionContactButton(
                        icon = Icons.Default.Phone,
                        label = "कॉल करा",
                        color = BlueCall,
                        onClick = { viewModel.dialPhone(context, classmate.phone) },
                        testTag = "detail_action_call"
                    )

                    ActionContactButton(
                        icon = Icons.Default.Chat,
                        label = "व्हॉट्सॲप",
                        color = EmeraldGreen,
                        onClick = { viewModel.openWhatsApp(context, classmate.whatsapp, classmate.fullName) },
                        testTag = "detail_action_whatsapp"
                    )

                    ActionContactButton(
                        icon = Icons.Default.Email,
                        label = "ईमेल",
                        color = MaroonPrimary,
                        onClick = { viewModel.sendEmail(context, classmate.email, classmate.fullName) },
                        testTag = "detail_action_email"
                    )

                    ActionContactButton(
                        icon = Icons.Default.Share,
                        label = "शेअर करा",
                        color = SaffronPrimary,
                        onClick = { viewModel.shareClassmateCard(context, classmate) },
                        testTag = "detail_action_share"
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Thoughts and Memories (मनातील विचार आणि आठवणी)
                if (classmate.thoughtsAndMemories.isNotBlank()) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.5.dp, GoldenAmber.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(GoldenAmber.copy(alpha = 0.15f))
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.FormatQuote,
                                        contentDescription = null,
                                        tint = GoldenAmber,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "मनातील विचार व मित्रांसाठी संदेश",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "\"${classmate.thoughtsAndMemories}\"",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontStyle = FontStyle.Italic,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }

                // Favorite College Memory
                if (classmate.favoriteMemory.isNotBlank()) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = SaffronPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "महाविद्यालयातील अविस्मरणीय आठवण",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = classmate.favoriteMemory,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                // Profession & Work Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Work,
                                contentDescription = null,
                                tint = SaffronPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "सध्याचे काम व व्यवसाय",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        DetailRow(label = "व्यवसाय क्षेत्र", value = classmate.occupationCategory)
                        DetailRow(label = "हुद्दा / पद", value = classmate.jobTitle.ifBlank { "नमुद नाही" })
                        DetailRow(label = "कंपनी / संस्था", value = classmate.organization.ifBlank { "नमुद नाही" })
                    }
                }

                // Residence & Contact Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = null,
                                tint = SaffronPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "पत्ता व संपर्क माहिती",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        DetailRow(label = "मूळ गाव", value = classmate.nativeVillage)
                        DetailRow(label = "सध्याचे शहर", value = classmate.currentCity)
                        if (classmate.address.isNotBlank()) {
                            DetailRow(label = "पूर्ण पत्ता", value = classmate.address)
                        }
                        DetailRow(label = "मोबाईल नंबर", value = classmate.phone.ifBlank { "उपलब्ध नाही" })
                        if (classmate.whatsapp.isNotBlank() && classmate.whatsapp != classmate.phone) {
                            DetailRow(label = "व्हॉट्सॲप", value = classmate.whatsapp)
                        }
                        if (classmate.email.isNotBlank()) {
                            DetailRow(label = "ईमेल", value = classmate.email)
                        }
                    }
                }

                // Personal Details Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = SaffronPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "इतर माहिती",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        DetailRow(label = "रक्तगट (Blood Group)", value = classmate.bloodGroup)
                        DetailRow(label = "गेट-टुगेदर हजेरी", value = classmate.attendanceStatus)
                        DetailRow(label = "शाखा (Stream)", value = classmate.stream)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("मित्र हटवायचा आहे का?") },
            text = { Text("${classmate.fullName} यांची माहिती ॲपमधून हटवली जाईल. आपण खात्रीपूर्वक पुढे जात आहात का?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteClassmate(classmate)
                        showDeleteConfirmation = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = RedDecline)
                ) {
                    Text("हटवा")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) {
                    Text("रद्द करा")
                }
            }
        )
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.9f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1.1f)
        )
    }
}

@Composable
fun ActionContactButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick() }
    ) {
        Surface(
            shape = CircleShape,
            color = color.copy(alpha = 0.14f),
            modifier = Modifier
                .size(48.dp)
                .testTag(testTag)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
