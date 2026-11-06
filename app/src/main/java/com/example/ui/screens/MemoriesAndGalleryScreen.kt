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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CollegeMemoryItem
import com.example.data.model.CommitteeMember
import com.example.ui.theme.*
import com.example.ui.viewmodel.ReunionViewModel

@Composable
fun MemoriesAndGalleryScreen(
    viewModel: ReunionViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val memories = viewModel.memories
    val committee = viewModel.committeeMembers

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("memories_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // College Nostalgia Header
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, GoldenAmber.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF831843).copy(alpha = 0.1f), Color(0xFFC2410C).copy(alpha = 0.1f))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = GoldenAmber,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "आठवणींचा सोनेरी कट्टा",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "श्री छत्रपती शिवाजी महाविद्यालय, उमरगा (१९९५-१९९७)",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SaffronPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "काळ पुढे निघून गेला, केस पांढरे झाले, जबाबदाऱ्या वाढल्या... पण महाविद्यालयाच्या त्या आवारात घालवलेले सोनेरी दिवस आणि मित्रांचे हसरे चेहरे आजही मनात तसेच ताजे आहेत. हे गेट-टुगेदर म्हणजे त्याच जुन्या क्षणांना पुन्हा जगण्याची अनमोल संधी!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontStyle = FontStyle.Italic,
                            lineHeight = 22.sp
                        )
                    }
                }
            }
        }

        // Memories List
        item {
            Text(
                text = "महाविद्यालयीन आठवणींचे क्षण",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        items(memories) { memory ->
            MemoryCard(memory = memory)
        }

        // Organizing Committee Section
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "१९९७ गेट-टुगेदर आयोजक समिती",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "कार्यक्रमाच्या नियोजनासाठी व मार्गदर्शनासाठी खालील मित्रांशी संपर्क साधा",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(committee) { member ->
            CommitteeMemberCard(
                member = member,
                onCallClick = { viewModel.dialPhone(context, member.phone) },
                onWhatsappClick = { viewModel.openWhatsApp(context, member.phone, member.name) }
            )
        }

        // College Address & Batch Note
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🏛️ श्री छत्रपती शिवाजी महाविद्यालय, उमरगा",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "स्थापना: १९५९ • मराठवाडा शिक्षण प्रसारक मंडळ संचलित\nराष्ट्रीय महामार्ग क्र. ६५, उमरगा, जि. धाराशिव (उस्मानाबाद)\nपिन कोड: ४१३६०६, महाराष्ट्र",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "« मैत्रीचा हा सोहळा... अखंड राहो आमचा मेळावा! »",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaroonPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun MemoryCard(
    memory: CollegeMemoryItem,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SaffronPrimary.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = memory.tag,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaffronPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Text(
                    text = memory.yearTag,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = memory.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = memory.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun CommitteeMemberCard(
    member: CommitteeMember,
    onCallClick: () -> Unit,
    onWhatsappClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f), RoundedCornerShape(14.dp))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = MaroonPrimary.copy(alpha = 0.15f),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = member.name.firstOrNull()?.toString() ?: "आ",
                        fontWeight = FontWeight.Bold,
                        color = MaroonPrimary,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = member.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = member.role,
                    style = MaterialTheme.typography.bodySmall,
                    color = SaffronPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp
                )
                Text(
                    text = "📍 ${member.location} • 📞 ${member.phone}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                IconButton(
                    onClick = onCallClick,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(BlueCall.copy(alpha = 0.12f))
                ) {
                    Icon(Icons.Default.Phone, contentDescription = "कॉल करा", tint = BlueCall, modifier = Modifier.size(18.dp))
                }

                IconButton(
                    onClick = onWhatsappClick,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(EmeraldGreen.copy(alpha = 0.12f))
                ) {
                    Icon(Icons.Default.Chat, contentDescription = "व्हॉट्सॲप", tint = EmeraldGreen, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
