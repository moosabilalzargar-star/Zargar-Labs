package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.ResponsibleAiDisclaimerCard
import com.example.ui.navigation.Screen
import com.example.ui.theme.KashmirEmerald
import com.example.ui.theme.KashmirForestGreen
import com.example.ui.theme.KashmirLightGreen
import com.example.ui.theme.KashmirMint
import com.example.ui.theme.SaffronAmber
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.AgriViewModel

@Composable
fun HomeScreen(
    viewModel: AgriViewModel,
    onNavigate: (Screen) -> Unit
) {
    val context = LocalContext.current
    val weather by viewModel.selectedDistrict.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF7))
            .padding(horizontal = 16.dp)
            .testTag("home_screen_scroll"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // Hero Banner Section
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("home_hero_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(KashmirForestGreen, KashmirEmerald)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        // Founders & Location Pill
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0x33FFFFFF))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = SaffronAmber,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Founders: Basim Abdullah Zargar & Moosa Bilal Zargar",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "AI for a smarter, safer Kashmir.",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                lineHeight = 28.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Empowering valley growers with AI diagnostics, local voice chat, SKUAST advisory, weather spray windows, and direct mandi marketplace trade.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFFD8F3DC),
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Quick Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onNavigate(Screen.AiChat) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("home_ask_ai_btn"),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SaffronAmber)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Chat,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Ask AI", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                            }

                            Button(
                                onClick = { onNavigate(Screen.CropAnalysis) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("home_analyze_crop_btn"),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = KashmirMint)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalFlorist,
                                    contentDescription = null,
                                    tint = KashmirForestGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Analyze", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = KashmirForestGreen)
                            }

                            Button(
                                onClick = { onNavigate(Screen.VoiceAssistant) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("home_voice_btn"),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Mic,
                                    contentDescription = null,
                                    tint = KashmirForestGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Voice", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = KashmirForestGreen)
                            }
                        }
                    }
                }
            }
        }

        // Weather & Spray Window Glance Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(Screen.Weather) }
                    .testTag("home_weather_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.WbSunny,
                                contentDescription = null,
                                tint = SaffronAmber,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${weather.districtName} Weather",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = KashmirForestGreen
                                )
                            )
                        }

                        // Spray Status Tag
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (weather.sprayStatus == "EXCELLENT") Color(0xFFDCFCE7) else Color(0xFFFEF3C7)
                                )
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "Spray: ${weather.sprayStatus}",
                                color = if (weather.sprayStatus == "EXCELLENT") Color(0xFF166534) else Color(0xFF92400E),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "${weather.temperatureC}°C",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = KashmirForestGreen
                                )
                            )
                            Text(
                                text = weather.condition,
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Humidity", fontSize = 11.sp, color = Color.Gray)
                                Text("${weather.humidityPercent}%", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Rain Risk", fontSize = 11.sp, color = Color.Gray)
                                Text("${weather.rainProbability}%", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Frost Alert", fontSize = 11.sp, color = Color.Gray)
                                Text(weather.frostRiskLevel, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = if (weather.frostRiskLevel == "High Alert") Color.Red else Color(0xFF15803D))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = weather.sprayAdvisory,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF374151),
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }

        // Section Title: Core Capabilities & Monetization Features
        item {
            Text(
                text = "Services & Features",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = KashmirForestGreen
                )
            )
        }

        // Grid of 6 Key Functional Modules
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureGridCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Paid,
                        title = "Mandi & Direct Trade",
                        subtitle = "Sell direct without dalal; live Kashmir rates",
                        badge = "Monetize",
                        badgeColor = SaffronAmber,
                        onClick = { onNavigate(Screen.Marketplace) }
                    )

                    FeatureGridCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Science,
                        title = "Drone & Soil Booking",
                        subtitle = "Book certified drone spray & soil testing",
                        badge = "Save 60%",
                        badgeColor = SuccessGreen,
                        onClick = { onNavigate(Screen.Marketplace) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureGridCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Description,
                        title = "HADP & Subsidies",
                        subtitle = "50-80% orchard subsidies, PM-KISAN, KCC",
                        badge = "₹5,013 Cr",
                        badgeColor = KashmirEmerald,
                        onClick = { onNavigate(Screen.Schemes) }
                    )

                    FeatureGridCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Book,
                        title = "Crop Diary Ledger",
                        subtitle = "Track expenses, harvests & net profits",
                        badge = "Room DB",
                        badgeColor = Color(0xFF6366F1),
                        onClick = { onNavigate(Screen.CropDiary) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureGridCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.LocalFlorist,
                        title = "Knowledge Base",
                        subtitle = "Apples, Saffron, Walnuts spray guides",
                        badge = "Offline",
                        badgeColor = Color(0xFF0D9488),
                        onClick = { onNavigate(Screen.KnowledgeBase) }
                    )

                    FeatureGridCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Mic,
                        title = "5-Language Voice",
                        subtitle = "Kashmiri, Hindi, Urdu, Hinglish, English",
                        badge = "Voice AI",
                        badgeColor = SaffronOrange,
                        onClick = { onNavigate(Screen.VoiceAssistant) }
                    )
                }
            }
        }

        // Responsible AI Card
        item {
            ResponsibleAiDisclaimerCard()
        }

        // Emergency & Extension Helplines Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Verified J&K Agricultural Helplines",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = KashmirForestGreen
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Kisan Call Centre (Toll Free)", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Text("1800-180-1551 • 24/7 Expert Advice", fontSize = 11.sp, color = Color.Gray)
                        }
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:18001801551"))
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = "Call", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Call", fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("SKUAST-K Shalimar Agronomy", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Text("0194-2462159 • University Scientists", fontSize = 11.sp, color = Color.Gray)
                        }
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:01942462159"))
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = KashmirEmerald)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = "Call", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Call", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // About & Founders Footer Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(Screen.About) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "About Jehlum Sense AI",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = KashmirForestGreen
                        )
                        Text(
                            text = "Vision & story of Basim Abdullah Zargar & Moosa Bilal Zargar",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Go",
                        tint = KashmirForestGreen,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun FeatureGridCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String,
    badge: String,
    badgeColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(145.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(KashmirLightGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = KashmirForestGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(badgeColor.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = badge,
                        color = badgeColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = KashmirForestGreen,
                        fontSize = 13.sp
                    ),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Gray,
                        fontSize = 11.sp,
                        lineHeight = 14.sp
                    ),
                    maxLines = 2
                )
            }
        }
    }
}
