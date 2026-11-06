package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.EventEntity
import com.example.ui.theme.SaffronPrimary
import com.example.ui.viewmodel.ReunionViewModel

val IconOptions = listOf(
    "sparkles" to "दीपप्रज्वलन / उत्सव",
    "coffee" to "चहा / नाश्ता",
    "school" to "सत्कार / प्राध्यापक",
    "record_voice_over" to "भाषण / मनोगत",
    "restaurant" to "स्नेहभोजन",
    "celebration" to "मनोरंजन / गाणी",
    "photo_camera" to "ग्रुप फोटो",
    "handshake" to "कल्याण निधी / बैठक",
    "emoji_emotions" to "सांगता"
)

@Composable
fun AddEditEventDialog(
    event: EventEntity?,
    viewModel: ReunionViewModel,
    onDismiss: () -> Unit
) {
    var timeSlot by remember { mutableStateOf(event?.timeSlot ?: "१०:०० AM - ११:०० AM") }
    var titleMarathi by remember { mutableStateOf(event?.titleMarathi ?: "") }
    var titleEnglish by remember { mutableStateOf(event?.titleEnglish ?: "") }
    var location by remember { mutableStateOf(event?.location ?: "महाविद्यालय मुख्य सभागृह") }
    var descriptionMarathi by remember { mutableStateOf(event?.descriptionMarathi ?: "") }
    var iconName by remember { mutableStateOf(event?.iconName ?: "sparkles") }
    var isHighlight by remember { mutableStateOf(event?.isHighlight ?: false) }

    var isError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("add_event_dialog")
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (event == null) "नवीन कार्यक्रम जोडा" else "कार्यक्रम संपादित करा",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "बंद करा")
                    }
                }

                HorizontalDivider()

                OutlinedTextField(
                    value = timeSlot,
                    onValueChange = { timeSlot = it },
                    label = { Text("वेळ (Time Slot) *") },
                    placeholder = { Text("उदा. १०:०० AM - ११:०० AM") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = titleMarathi,
                    onValueChange = {
                        titleMarathi = it
                        isError = false
                    },
                    label = { Text("कार्यक्रमाचे नाव (मराठी) *") },
                    placeholder = { Text("उदा. प्राध्यापकांचा सत्कार") },
                    isError = isError && titleMarathi.isBlank(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = titleEnglish,
                    onValueChange = { titleEnglish = it },
                    label = { Text("कार्यक्रमाचे नाव (इंग्रजी - ऐच्छिक)") },
                    placeholder = { Text("Teacher Felicitation") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("कॅम्पस मधील ठिकाण *") },
                    placeholder = { Text("उदा. मुख्य सभागृह / लॉन") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = descriptionMarathi,
                    onValueChange = { descriptionMarathi = it },
                    label = { Text("कार्यक्रमाची सविस्तर माहिती") },
                    placeholder = { Text("या सत्रात काय होणार आहे...") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = isHighlight,
                        onCheckedChange = { isHighlight = it }
                    )
                    Text("हा मुख्य विशेष कार्यक्रम आहे (Highlight)", fontSize = 13.sp)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(onClick = onDismiss) {
                        Text("रद्द करा")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (titleMarathi.isBlank() || timeSlot.isBlank()) {
                                isError = true
                                return@Button
                            }
                            val updated = (event ?: EventEntity(
                                timeSlot = timeSlot,
                                titleMarathi = titleMarathi,
                                titleEnglish = titleEnglish,
                                location = location,
                                descriptionMarathi = descriptionMarathi,
                                iconName = iconName,
                                isHighlight = isHighlight
                            )).copy(
                                timeSlot = timeSlot.trim(),
                                titleMarathi = titleMarathi.trim(),
                                titleEnglish = titleEnglish.trim(),
                                location = location.trim(),
                                descriptionMarathi = descriptionMarathi.trim(),
                                iconName = iconName,
                                isHighlight = isHighlight
                            )
                            viewModel.saveEvent(updated)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
                    ) {
                        Text("जतन करा")
                    }
                }
            }
        }
    }
}
