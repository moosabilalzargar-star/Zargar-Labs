package com.example.ui.screens

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.SupportedLanguages
import com.example.ui.components.ResponsibleAiDisclaimerCard
import com.example.ui.theme.KashmirEmerald
import com.example.ui.theme.KashmirForestGreen
import com.example.ui.theme.KashmirLightGreen
import com.example.ui.theme.KashmirMint
import com.example.ui.theme.SaffronAmber
import com.example.ui.theme.SaffronOrange
import com.example.ui.viewmodel.AgriViewModel

@Composable
fun VoiceAssistantScreen(
    viewModel: AgriViewModel
) {
    val context = LocalContext.current
    val selectedLanguage by viewModel.selectedLanguage.collectAsStateWithLifecycle()
    val isSpeaking by viewModel.isTtsSpeaking.collectAsStateWithLifecycle()
    val isAiThinking by viewModel.isAiTyping.collectAsStateWithLifecycle()

    var lastSpokenQuery by remember { mutableStateOf("") }
    var latestResponse by remember { mutableStateOf("") }

    // Pulsing animation for mic
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spoken = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()
            if (!spoken.isNullOrBlank()) {
                lastSpokenQuery = spoken
                viewModel.sendChatMessage(spoken)
                // Generate and speak response
                val response = viewModel.repository.let {
                    // query offline or gemini
                    viewModel.speakText("Analyzing query in ${selectedLanguage.name}", selectedLanguage.code)
                    "Processed query"
                }
                latestResponse = response
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF7))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("voice_assistant_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title & Description
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Voice Assistant (آواز معاون)",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = KashmirForestGreen
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Hands-free voice consultation designed for Kashmiri orchard growers and farmers.",
                fontSize = 12.sp,
                color = Color.Gray,
                lineHeight = 16.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        // Language Pill Selector
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Spoken Voice Language:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SupportedLanguages.forEach { lang ->
                    val isSelected = lang.code == selectedLanguage.code
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) KashmirForestGreen else Color.White)
                            .border(1.dp, if (isSelected) KashmirEmerald else Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                            .clickable { viewModel.selectLanguage(lang) }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "${lang.nativeLabel} (${lang.name})",
                            color = if (isSelected) Color.White else Color(0xFF1E293B),
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Large Pulsing Voice Mic Button
        Box(
            modifier = Modifier
                .size(170.dp),
            contentAlignment = Alignment.Center
        ) {
            // Ripple layer
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .scale(pulseScale)
                    .clip(CircleShape)
                    .background(KashmirLightGreen.copy(alpha = 0.4f))
            )

            // Inner button
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(KashmirForestGreen)
                    .clickable {
                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE, selectedLanguage.speechLocaleTag)
                            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak in ${selectedLanguage.nativeLabel}...")
                        }
                        try {
                            speechLauncher.launch(intent)
                        } catch (e: Exception) {
                            viewModel.setFeedback("Speech recognition not available on this device.")
                        }
                    }
                    .testTag("voice_mic_center_btn"),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Speak Now",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Tap to Speak",
                        color = KashmirLightGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Voice Status Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFE2E8F0))
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = if (isSpeaking) Icons.Default.GraphicEq else Icons.Default.RecordVoiceOver,
                contentDescription = null,
                tint = if (isSpeaking) SaffronAmber else KashmirForestGreen,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = if (isSpeaking) "Speaking response in ${selectedLanguage.nativeLabel}..." else "Listening ready (${selectedLanguage.name})",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = KashmirForestGreen
            )

            if (isSpeaking) {
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { viewModel.stopSpeaking() },
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(Icons.Default.Stop, contentDescription = "Stop", tint = Color.Red)
                }
            }
        }

        // Live Spoken Query Card
        if (lastSpokenQuery.isNotBlank()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Your Voice Question:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "“$lastSpokenQuery”",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = KashmirForestGreen
                    )
                }
            }
        }

        // Sample Voice Query Prompts in Native Languages
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "Tap to Ask Common Voice Prompts:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = KashmirForestGreen
                )
                Spacer(modifier = Modifier.height(10.dp))

                val voicePrompts = when (selectedLanguage.code) {
                    "ks" -> listOf(
                        "سیب باگس منٛز ڈیفینوکونازول کٔرِو سپرے؟",
                        "پامپوٗرِس منٛز زعفران گول کِتھ کَن واوُن؟",
                        "HADP اسکیم کِتھ پٲٹھؠ مِلی؟",
                        "ڈرون سپرے کِتھ پٲٹھؠ بُک کَروٗ؟"
                    )
                    "hi" -> listOf(
                        "सेब में स्कैब की रोकथाम के लिए कौन सा स्प्रे करें?",
                        "केसर की खेती के लिए क्यारियां कैसे तैयार करें?",
                        "HADP योजना में 80% सब्सिडी कैसे प्राप्त करें?",
                        "ड्रोन स्प्रे की बुकिंग कैसे करें?"
                    )
                    "ur" -> listOf(
                        "سیب کے باغات کیلئے اسکواسٹ کا تجویز کردہ اسپرے شیڈول کیا ہے؟",
                        "زعفران میں سڑن کی بیماری کا علاج کیا ہے؟",
                        "جموں کشمیر HADP اسکیم کے فوائد کیا ہیں؟",
                        "ڈرون سے اسپرے کروانے کے کیا چارجز ہیں؟"
                    )
                    "hinglish" -> listOf(
                        "Apple scab ke liye Difenoconazole spray kab karein?",
                        "Pampore me Saffron corm planting date kya hai?",
                        "HADP 80% apple subsidy me kaise apply karein?",
                        "Drone orchard spray booking ₹150/kanal ki details do."
                    )
                    else -> listOf(
                        "What is the SKUAST recommended apple spray schedule?",
                        "How to treat saffron corm rot disease in Pampore?",
                        "What is the eligibility for HADP 80% orchard subsidy?",
                        "How does certified drone spraying save farm labor?"
                    )
                }

                voicePrompts.forEach { prompt ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF8FAFC))
                            .clickable {
                                lastSpokenQuery = prompt
                                viewModel.sendChatMessage(prompt)
                                val resp = viewModel.repository.getCommonDiseases().first().recommendedSprayTreatment
                                viewModel.speakText(prompt, selectedLanguage.code)
                            }
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.VolumeUp, contentDescription = null, tint = SaffronAmber, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = prompt,
                            fontSize = 12.sp,
                            color = Color(0xFF334155),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Responsible AI Notice
        ResponsibleAiDisclaimerCard()

        Spacer(modifier = Modifier.height(16.dp))
    }
}
