package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ResponsibleAiDisclaimerCard
import com.example.ui.theme.KashmirEmerald
import com.example.ui.theme.KashmirForestGreen
import com.example.ui.theme.KashmirLightGreen
import com.example.ui.theme.SaffronAmber
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.AgriViewModel

@Composable
fun AboutScreen(
    viewModel: AgriViewModel
) {
    var feedbackName by remember { mutableStateOf("") }
    var feedbackMessage by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF7))
            .padding(16.dp)
            .testTag("about_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // Brand Hero
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = KashmirForestGreen)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(KashmirLightGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.LocalFlorist, contentDescription = null, tint = KashmirForestGreen, modifier = Modifier.size(24.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Jehlum Sense AI",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = "AI for a smarter, safer Kashmir",
                                fontSize = 12.sp,
                                color = KashmirLightGreen
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Born in the Kashmir Valley, Jehlum Sense AI is an agricultural intelligence platform crafted to solve real farming hurdles: disease spread in apple orchards, saffron karewa rejuvenation, weather-induced chemical wash-offs, and predatory middleman commissions.",
                        color = Color(0xFFD8F3DC),
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        // Founders Tribute Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = SaffronAmber, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Founders & Visionaries",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = KashmirForestGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    FounderBioItem(
                        name = "Basim Abdullah Zargar",
                        role = "Co-Founder",
                        description = "Leading the vision for sustainable agronomy modernization, farmer-friendly digital literacy, and AI diagnostics tailored to high-density apple orchards and Karewa saffron beds across Jammu & Kashmir."
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    FounderBioItem(
                        name = "Moosa Bilal Zargar",
                        role = "Co-Founder",
                        description = "Driving the product architecture, zero-broker marketplace innovation, multilingual voice accessibility for vernacular growers, and drone-assisted orchard services."
                    )
                }
            }
        }

        // Mission & Expansion Roadmap
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Public, contentDescription = null, tint = KashmirForestGreen, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Mission & Future Roadmap",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = KashmirForestGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "• Phase 1 (Jammu & Kashmir): Comprehensive coverage of Apple, Saffron, Walnut, and Paddy in Baramulla, Shopian, Pulwama, Kupwara, Srinagar, Anantnag, and Jammu.\n• Phase 2 (Across India): Integration with national mandis (eNAM), Himachal Pradesh temperate horticulture, and Punjab/Haryana grain belts.\n• Phase 3 (Global Temperate Agriculture): Adapting the AI pathology models for European and Central Asian apple and saffron growers.",
                        fontSize = 12.sp,
                        color = Color(0xFF334155),
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Responsible AI Notice
        item {
            ResponsibleAiDisclaimerCard()
        }

        // Contact & Farmer Feedback Form
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Email, contentDescription = null, tint = KashmirForestGreen, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Contact Founders & Share Feedback",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = KashmirForestGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Have questions about your orchard or suggestions for our founders? Send us a note directly.",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (isSubmitted) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFDCFCE7))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Thank you! Your feedback has been received by Basim Abdullah Zargar and Moosa Bilal Zargar.",
                                color = Color(0xFF166534),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    } else {
                        OutlinedTextField(
                            value = feedbackName,
                            onValueChange = { feedbackName = it },
                            label = { Text("Your Name or Village") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = feedbackMessage,
                            onValueChange = { feedbackMessage = it },
                            label = { Text("Your Question or Feedback") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                if (feedbackMessage.isNotBlank()) {
                                    isSubmitted = true
                                    viewModel.setFeedback("Thank you for reaching out to the Jehlum Sense AI founders!")
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Submit Message", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun FounderBioItem(name: String, role: String, description: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = KashmirForestGreen)
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(KashmirLightGreen)
                    .padding(horizontal = 6.dp, vertical = 1.dp)
            ) {
                Text(role, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = KashmirForestGreen)
            }
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = description,
            fontSize = 11.sp,
            color = Color(0xFF475569),
            lineHeight = 15.sp
        )
    }
}
