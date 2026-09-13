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
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.db.ServiceBookingEntity
import com.example.ui.theme.KashmirEmerald
import com.example.ui.theme.KashmirForestGreen
import com.example.ui.theme.KashmirLightGreen
import com.example.ui.theme.SaffronAmber
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.AgriViewModel

@Composable
fun AdminScreen(
    viewModel: AgriViewModel
) {
    val context = LocalContext.current
    val stats by viewModel.adminStats.collectAsStateWithLifecycle()
    val bookings by viewModel.serviceBookings.collectAsStateWithLifecycle()
    val totalRevenue by viewModel.totalServiceRevenue.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF7))
            .padding(16.dp)
            .testTag("admin_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = SaffronAmber, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Admin Control Dashboard",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = KashmirForestGreen
                            )
                        )
                    }
                    Text(
                        text = "Founders Operations: Basim Abdullah Zargar & Moosa Bilal Zargar",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray, fontSize = 11.sp)
                    )
                }
            }
        }

        // Metrics Grid (Farmers, Bookings, Produce Listings, Revenue)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AdminMetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Registered Farmers",
                        value = "${stats["farmers"] ?: 2}",
                        icon = Icons.Default.People,
                        color = KashmirForestGreen
                    )

                    AdminMetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Service Bookings",
                        value = "${stats["bookings"] ?: 1}",
                        icon = Icons.Default.LocalShipping,
                        color = SaffronAmber
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AdminMetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Market Listings",
                        value = "${stats["listings"] ?: 3}",
                        icon = Icons.Default.Store,
                        color = KashmirEmerald
                    )

                    AdminMetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Service Revenue",
                        value = "₹${(totalRevenue ?: 1500.0).toInt()}",
                        icon = Icons.Default.Paid,
                        color = SuccessGreen
                    )
                }
            }
        }

        // Active Service Bookings Section (Monetization Dispatch)
        item {
            Text(
                text = "Drone & Soil Testing Booking Dispatches (${bookings.size})",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = KashmirForestGreen
                )
            )
        }

        items(bookings) { booking ->
            ServiceBookingAdminCard(
                booking = booking,
                onCall = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${booking.phone}"))
                    context.startActivity(intent)
                }
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun AdminMetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, fontSize = 11.sp, color = Color.Gray)
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = color
            )
        }
    }
}

@Composable
fun ServiceBookingAdminCard(
    booking: ServiceBookingEntity,
    onCall: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = booking.serviceTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = KashmirForestGreen
                    )
                    Text(
                        text = "Farmer: ${booking.farmerName} • ${booking.district}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(KashmirLightGreen)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = booking.status,
                        color = KashmirForestGreen,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Location: ${booking.farmLocation} • Scheduled: ${booking.scheduledDate}",
                fontSize = 11.sp,
                color = Color(0xFF334155)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Estimated Fee: ₹${booking.estimatedFee.toInt()} (${booking.acreageOrKanals.toInt()} Kanals)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = KashmirForestGreen
                )

                IconButton(
                    onClick = onCall,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.Call, contentDescription = "Call Farmer", tint = KashmirForestGreen, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
