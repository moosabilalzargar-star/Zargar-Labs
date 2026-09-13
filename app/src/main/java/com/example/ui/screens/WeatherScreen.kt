package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.DistrictWeather
import com.example.ui.components.ResponsibleAiDisclaimerCard
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.KashmirEmerald
import com.example.ui.theme.KashmirForestGreen
import com.example.ui.theme.KashmirLightGreen
import com.example.ui.theme.SaffronAmber
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.AgriViewModel

@Composable
fun WeatherScreen(
    viewModel: AgriViewModel
) {
    val selectedDistrict by viewModel.selectedDistrict.collectAsStateWithLifecycle()
    val allDistricts = viewModel.allDistrictsWeather

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF7))
            .padding(horizontal = 16.dp)
            .testTag("weather_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        item {
            Column {
                Text(
                    text = "Weather & Spray Windows",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = KashmirForestGreen
                    )
                )
                Text(
                    text = "Hyperlocal orchard climate data and SKUAST spray suitability for all J&K districts.",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray, fontSize = 12.sp)
                )
            }
        }

        // Horizontal District Selector
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                allDistricts.forEach { dist ->
                    val isSelected = dist.districtName == selectedDistrict.districtName
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) KashmirForestGreen else Color.White)
                            .border(1.dp, if (isSelected) KashmirEmerald else Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                            .clickable { viewModel.selectDistrict(dist) }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = dist.districtName,
                            color = if (isSelected) Color.White else Color(0xFF1E293B),
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Active District Detailed Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("weather_main_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = selectedDistrict.districtName,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = KashmirForestGreen
                                )
                            )
                            Text(
                                text = selectedDistrict.condition,
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = SaffronAmber,
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "${selectedDistrict.temperatureC}°C",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = KashmirForestGreen
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Metrics Grid (Humidity, Rain, Wind, Frost)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricBox(
                            icon = Icons.Default.WaterDrop,
                            title = "Humidity",
                            value = "${selectedDistrict.humidityPercent}%",
                            tint = Color(0xFF0284C7)
                        )
                        MetricBox(
                            icon = Icons.Default.Cloud,
                            title = "Rain Risk",
                            value = "${selectedDistrict.rainProbability}%",
                            tint = Color(0xFF4F46E5)
                        )
                        MetricBox(
                            icon = Icons.Default.Air,
                            title = "Wind",
                            value = "${selectedDistrict.windSpeedKmh} km/h",
                            tint = Color(0xFF059669)
                        )
                        MetricBox(
                            icon = Icons.Default.Warning,
                            title = "Frost Risk",
                            value = selectedDistrict.frostRiskLevel,
                            tint = if (selectedDistrict.frostRiskLevel == "High Alert") ErrorRed else Color(0xFFD97706)
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Spray Window Status Banner
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedDistrict.sprayStatus == "EXCELLENT") Color(0xFFF0FDF4) else Color(0xFFFFFBEB)
                        )
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (selectedDistrict.sprayStatus == "EXCELLENT") Icons.Default.CheckCircle else Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = if (selectedDistrict.sprayStatus == "EXCELLENT") SuccessGreen else SaffronAmber,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Agricultural Spray Window: ${selectedDistrict.sprayStatus}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (selectedDistrict.sprayStatus == "EXCELLENT") Color(0xFF166534) else Color(0xFF92400E)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = selectedDistrict.sprayAdvisory,
                                fontSize = 12.sp,
                                color = Color(0xFF374151),
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        // Section: Valley Overview Across Districts
        item {
            Text(
                text = "All Jammu & Kashmir Districts Overview",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = KashmirForestGreen
                )
            )
        }

        items(allDistricts) { district ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.selectDistrict(district) },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(district.districtName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = KashmirForestGreen)
                        Text("${district.temperatureC}°C • ${district.condition}", fontSize = 12.sp, color = Color.Gray)
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (district.sprayStatus == "EXCELLENT") Color(0xFFDCFCE7) else Color(0xFFFEF3C7)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Spray: ${district.sprayStatus}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (district.sprayStatus == "EXCELLENT") Color(0xFF166534) else Color(0xFF92400E)
                        )
                    }
                }
            }
        }

        item {
            ResponsibleAiDisclaimerCard()
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun MetricBox(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    tint: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = title, tint = tint, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.height(2.dp))
        Text(title, fontSize = 11.sp, color = Color.Gray)
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
    }
}
