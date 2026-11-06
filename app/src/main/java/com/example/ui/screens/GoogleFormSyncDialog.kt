package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.SaffronPrimary
import com.example.ui.viewmodel.ReunionViewModel

@Composable
fun GoogleFormSyncDialog(
    viewModel: ReunionViewModel,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val formUrl by viewModel.googleFormUrl.collectAsState()
    var editFormUrl by remember { mutableStateOf(formUrl) }
    var importText by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var isSuccess by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.88f)
                .testTag("google_form_sync_dialog")
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Column {
                        Text(
                            text = "गुगल फॉर्म व डेटा संकलन केंद्र",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "माहिती शेअर, आयात (Import) व निर्यात (Export)",
                            style = MaterialTheme.typography.bodySmall,
                            color = SaffronPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "बंद करा")
                    }
                }

                HorizontalDivider()

                // Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Google Form Link Section
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "🔗 गुगल फॉर्म लिंक (Google Form Link)",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = editFormUrl,
                                onValueChange = { editFormUrl = it },
                                label = { Text("फॉर्म URL") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        viewModel.updateGoogleFormUrl(editFormUrl)
                                        viewModel.openGoogleForm(context)
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("फॉर्म उघडा", fontSize = 11.sp)
                                }

                                Button(
                                    onClick = {
                                        viewModel.updateGoogleFormUrl(editFormUrl)
                                        viewModel.shareGoogleFormLink(context)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("शेअर करा", fontSize = 11.sp)
                                }
                            }
                        }
                    }

                    // Import Section
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "📥 गुगल फॉर्म रिस्पॉन्स आयात करा (Import CSV / JSON)",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "गुगल फॉर्मच्या रिस्पॉन्स शीटमधील ओळी कॉपी करून खालील बॉक्समध्ये पेस्ट करा (किंवा एका मित्राची ओळ: नाव, टोपणनाव, शाखा, व्यवसाय, पद, कंपनी, मूळ गाव, शहर, पत्ता, मोबाईल, व्हॉट्सॲप, ईमेल, रक्तगट, हजेरी, आठवणी):",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = importText,
                                onValueChange = { importText = it },
                                placeholder = { Text("येथे CSV किंवा JSON डेटा पेस्ट करा...") },
                                minLines = 4,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("input_import_text")
                            )

                            if (statusMessage != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = statusMessage ?: "",
                                    color = if (isSuccess) EmeraldGreen else MaterialTheme.colorScheme.error,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = {
                                    if (importText.isBlank()) {
                                        statusMessage = "कृपया पेस्ट करण्यासाठी डेटा टाका"
                                        isSuccess = false
                                        return@Button
                                    }
                                    viewModel.importData(importText) { success, msg ->
                                        isSuccess = success
                                        statusMessage = msg
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("btn_confirm_import")
                            ) {
                                Text("डेटा ॲपमध्ये आयात करा", fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // Export Section
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "📤 सर्व मित्रांची माहिती एक्सपोर्ट करा (Export CSV)",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "सर्व वर्गमित्रांची संकलित माहिती एक्सेल किंवा गुगल शीटमध्ये वापरण्यासाठी CSV स्वरूपात कॉपी करा.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = {
                                    val csv = viewModel.getExportCsv()
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("SCSM 1997 Reunion CSV", csv)
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(context, "CSV डेटा क्लिपबोर्डवर कॉपी झाला!", Toast.LENGTH_LONG).show()
                                },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("CSV क्लिपबोर्डवर कॉपी करा", fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // Reset Option
                    TextButton(
                        onClick = {
                            viewModel.resetToDefaultData()
                            onDismiss()
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("मूळ नमुना माहिती पूर्ववत करा (Reset Data)", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
