package com.example.ui.screens

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.CropDiseaseInfo
import com.example.ui.components.ResponsibleAiDisclaimerCard
import com.example.ui.navigation.Screen
import com.example.ui.theme.ErrorRed
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
fun CropAnalysisScreen(
    viewModel: AgriViewModel,
    onNavigateToServices: () -> Unit
) {
    val context = LocalContext.current
    val analyzedDisease by viewModel.analyzedDisease.collectAsStateWithLifecycle()
    val isAnalyzing by viewModel.isAnalyzingCrop.collectAsStateWithLifecycle()
    val selectedBitmap by viewModel.selectedCropBitmap.collectAsStateWithLifecycle()
    val allDiseases = viewModel.repository.getCommonDiseases()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, it))
                } else {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                }
                viewModel.analyzeCropPhoto(bitmap)
            } catch (e: Exception) {
                viewModel.setFeedback("Could not load image.")
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF7))
            .padding(horizontal = 16.dp)
            .testTag("crop_analysis_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // Header Section
        item {
            Column {
                Text(
                    text = "AI Crop Doctor & Diagnostics",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = KashmirForestGreen
                    )
                )
                Text(
                    text = "Upload a leaf or fruit photo to receive instant likely findings, symptoms, and SKUAST recommended remedies.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                )
            }
        }

        // Responsible AI Callout
        item {
            ResponsibleAiDisclaimerCard()
        }

        // Upload Area Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("upload_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (selectedBitmap != null) {
                        Image(
                            bitmap = selectedBitmap!!.asImageBitmap(),
                            contentDescription = "Selected Crop Photo",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(190.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFF1F5F9))
                                .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.AddAPhoto,
                                    contentDescription = "Upload",
                                    tint = KashmirForestGreen,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Select an apple leaf, saffron corm, or walnut photo",
                                    fontSize = 12.sp,
                                    color = Color(0xFF475569)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { imagePickerLauncher.launch("image/*") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("pick_photo_btn"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen)
                        ) {
                            Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Pick from Gallery", fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = {
                                // Run sample analysis on Apple Scab
                                viewModel.analyzeCropPhoto(null, allDiseases[0])
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("sample_scan_btn"),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Psychology, contentDescription = null, tint = SaffronAmber, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Test Sample", fontSize = 12.sp, color = KashmirForestGreen)
                        }
                    }
                }
            }
        }

        // Quick Test Kashmir Disease Samples Chips
        item {
            Column {
                Text(
                    text = "Or Select Known Kashmir Crop Issue:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = KashmirForestGreen
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    allDiseases.forEach { disease ->
                        val isCurrent = analyzedDisease?.id == disease.id
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isCurrent) SaffronAmber else Color.White)
                                .border(1.dp, if (isCurrent) SaffronOrange else Color(0xFFE2E8F0), RoundedCornerShape(14.dp))
                                .clickable { viewModel.analyzeCropPhoto(null, disease) }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "${disease.commonName} (${disease.cropName.substringBefore(" ")})",
                                color = if (isCurrent) Color.White else Color(0xFF1E293B),
                                fontSize = 11.sp,
                                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // Analysis In-Progress Indicator
        if (isAnalyzing) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            color = KashmirForestGreen,
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Analyzing Leaf Patterns...", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Comparing against SKUAST Kashmir pathology dataset", fontSize = 11.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }

        // Detailed Diagnosis Results Card
        val disease = analyzedDisease
        if (disease != null && !isAnalyzing) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("diagnosis_result_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Title & Status
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = disease.commonName,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = KashmirForestGreen,
                                        fontSize = 17.sp
                                    )
                                )
                                Text(
                                    text = "${disease.scientificName} • ${disease.cropName}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.Gray,
                                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                    )
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (disease.severityLevel.contains("Severe")) Color(0xFFFEE2E2) else Color(0xFFFEF3C7)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = disease.severityLevel,
                                    color = if (disease.severityLevel.contains("Severe")) ErrorRed else Color(0xFFB45309),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Confidence Meter
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Likely Finding Probability", fontSize = 11.sp, color = Color.Gray)
                                Text("${disease.confidenceBaseline}% Confidence", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = KashmirForestGreen)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { disease.confidenceBaseline / 100f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = if (disease.confidenceBaseline > 90) SuccessGreen else SaffronAmber,
                                trackColor = Color(0xFFE2E8F0)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Visible Symptoms
                        Text("Visible Pathological Symptoms:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF1E293B))
                        Spacer(modifier = Modifier.height(4.dp))
                        disease.visualSymptoms.forEach { symptom ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text("• ", color = SaffronAmber, fontWeight = FontWeight.Bold)
                                Text(symptom, fontSize = 12.sp, color = Color(0xFF334155), lineHeight = 16.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Safe Cultural & Organic Actions
                        Text("Safe Cultural & Organic Controls:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF1E293B))
                        Spacer(modifier = Modifier.height(4.dp))
                        disease.culturalPrevention.forEach { step ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(step, fontSize = 12.sp, color = Color(0xFF334155), lineHeight = 16.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // SKUAST Recommended Chemical Treatment
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4))
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "SKUAST Spray Treatment (Strict Dosage):",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = Color(0xFF166534)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = disease.recommendedSprayTreatment,
                                    fontSize = 12.sp,
                                    color = Color(0xFF14532D),
                                    lineHeight = 16.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Human Expert Referral Trigger
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED))
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Warning, contentDescription = null, tint = SaffronOrange, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "When to Contact Agricultural Expert:",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = Color(0xFF9A3412)
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = disease.skuastReferralAdvice,
                                    fontSize = 12.sp,
                                    color = Color(0xFF7C2D12),
                                    lineHeight = 16.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Expert Call & Booking CTA Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:01942462159"))
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen)
                            ) {
                                Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Call SKUAST", fontSize = 12.sp)
                            }

                            Button(
                                onClick = onNavigateToServices,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SaffronAmber)
                            ) {
                                Text("Book 1-on-1 Consult", fontSize = 12.sp, color = Color.White)
                            }
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}
