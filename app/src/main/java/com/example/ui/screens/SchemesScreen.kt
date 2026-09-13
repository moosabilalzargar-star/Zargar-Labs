package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GovScheme
import com.example.ui.theme.KashmirEmerald
import com.example.ui.theme.KashmirForestGreen
import com.example.ui.theme.KashmirLightGreen
import com.example.ui.theme.SaffronAmber
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.AgriViewModel

@Composable
fun SchemesScreen(
    viewModel: AgriViewModel
) {
    val context = LocalContext.current
    val schemes = viewModel.repository.getGovSchemes()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF7))
            .padding(horizontal = 16.dp)
            .testTag("schemes_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        item {
            Column {
                Text(
                    text = "Government Schemes & Subsidies",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = KashmirForestGreen
                    )
                )
                Text(
                    text = "Verified financial grants, HADP J&K 50-80% subsidies, PM-KISAN, and low-interest crop loans.",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray, fontSize = 12.sp)
                )
            }
        }

        items(schemes) { scheme ->
            GovSchemeCard(
                scheme = scheme,
                onCall = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${scheme.helpline.filter { it.isDigit() }}"))
                    context.startActivity(intent)
                }
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun GovSchemeCard(
    scheme: GovScheme,
    onCall: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = scheme.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = KashmirForestGreen
                    )
                    Text(
                        text = scheme.department,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(KashmirLightGreen)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = scheme.subsidyBenefit,
                        color = KashmirForestGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = scheme.briefDescription,
                fontSize = 12.sp,
                color = Color(0xFF334155),
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text("Eligibility:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF1E293B))
            scheme.eligibility.forEach { el ->
                Row(
                    modifier = Modifier.padding(vertical = 1.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(el, fontSize = 11.sp, color = Color(0xFF475569))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("Documents Required:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF1E293B))
            scheme.documentRequirements.forEach { doc ->
                Row(
                    modifier = Modifier.padding(vertical = 1.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(Icons.Default.Description, contentDescription = null, tint = SaffronAmber, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(doc, fontSize = 11.sp, color = Color(0xFF475569))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AccountBalance, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = scheme.officialPortal,
                        fontSize = 11.sp,
                        color = KashmirForestGreen,
                        fontWeight = FontWeight.Medium
                    )
                }

                Button(
                    onClick = onCall,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen)
                ) {
                    Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Helpline", fontSize = 11.sp)
                }
            }
        }
    }
}
