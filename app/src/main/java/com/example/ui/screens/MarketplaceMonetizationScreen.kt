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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.data.db.MarketplaceListingEntity
import com.example.data.model.AgriService
import com.example.ui.components.ResponsibleAiDisclaimerCard
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
fun MarketplaceMonetizationScreen(
    viewModel: AgriViewModel
) {
    val context = LocalContext.current
    val listings by viewModel.marketplaceListings.collectAsStateWithLifecycle()
    val services = viewModel.repository.getAgriServices()
    val mandiRates = viewModel.repository.getMandiRates()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableIntStateOf(0) }
    var showAddListingDialog by remember { mutableStateOf(false) }
    var selectedServiceForBooking by remember { mutableStateOf<AgriService?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF7))
            .padding(horizontal = 16.dp)
            .testTag("marketplace_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // Header Section
        item {
            Column {
                Text(
                    text = "Mandi, Trade & Farm Earnings",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = KashmirForestGreen
                    )
                )
                Text(
                    text = "Direct market trade to eliminate dalal commissions, certified drone sprays, and soil testing.",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray, fontSize = 12.sp)
                )
            }
        }

        // Jehlum Sense Pro Club (Monetization Membership Banner)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = SaffronAmber, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Jehlum Sense Pro Club",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        val isPro = currentUser?.isProSubscriber == true
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isPro) SuccessGreen else SaffronAmber)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = if (isPro) "PRO ACTIVE" else "₹299 / Season",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Get satellite frost warnings by SMS, featured gold listings seen by 200+ fruit merchants across India, and zero-fee buyer connections.",
                        color = Color(0xFFCBD5E1),
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { viewModel.toggleProSubscriber() },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentUser?.isProSubscriber == true) Color(0xFF475569) else SaffronAmber
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (currentUser?.isProSubscriber == true) "Manage Pro Membership (Active)" else "Upgrade to Jehlum Sense Pro (₹299)",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Sub-tabs: 0: Marketplace Trade, 1: Book Tech Services, 2: Live Mandi Rates
        item {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = KashmirForestGreen
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Direct Trade (${listings.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Book Services", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("Mandi Rates", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
            }
        }

        // TAB 0: Direct Farmer Marketplace
        if (selectedTab == 0) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Orchard-Direct Listings",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = KashmirForestGreen
                        )
                    )

                    Button(
                        onClick = { showAddListingDialog = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen),
                        modifier = Modifier.testTag("add_listing_btn")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("List Produce", fontSize = 12.sp)
                    }
                }
            }

            items(listings) { item ->
                MarketplaceCard(
                    listing = item,
                    onContact = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${item.phone}"))
                        context.startActivity(intent)
                    }
                )
            }
        }

        // TAB 1: On-Demand Agri-Tech Services (Drone Spraying, Soil Testing, Expert 1-on-1)
        if (selectedTab == 1) {
            item {
                Text(
                    text = "High-Tech Field Services for Growers",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = KashmirForestGreen
                    )
                )
            }

            items(services) { service ->
                AgriServiceCard(
                    service = service,
                    onBook = { selectedServiceForBooking = service }
                )
            }
        }

        // TAB 2: Live Mandi Rates Table
        if (selectedTab == 2) {
            item {
                Text(
                    text = "Today's Fruit & Saffron Mandi Rates",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = KashmirForestGreen
                    )
                )
            }

            items(mandiRates) { rate ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(rate.mandiName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = KashmirForestGreen)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("${rate.commodity} (${rate.variety})", fontSize = 12.sp, color = Color.Gray)
                            Text(rate.priceRange, fontSize = 11.sp, color = Color(0xFF475569))
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = rate.modalPrice,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 15.sp,
                                color = KashmirForestGreen
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.TrendingUp,
                                    contentDescription = null,
                                    tint = SuccessGreen,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text("Strong Demand", fontSize = 10.sp, color = SuccessGreen, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }
        }

        item {
            ResponsibleAiDisclaimerCard()
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }

    // Modal Dialog: Add Produce Listing
    if (showAddListingDialog) {
        AddListingDialog(
            onDismiss = { showAddListingDialog = false },
            onAdd = { produce, variety, quantity, price, grade ->
                viewModel.addMarketplaceListing(produce, variety, quantity, price, grade)
                showAddListingDialog = false
            }
        )
    }

    // Modal Dialog: Book Service (Drone Spray or Soil Test)
    selectedServiceForBooking?.let { srv ->
        BookServiceDialog(
            service = srv,
            onDismiss = { selectedServiceForBooking = null },
            onConfirm = { district, location, kanals, fee, date ->
                viewModel.bookService(srv.title, district, location, kanals, fee, date)
                selectedServiceForBooking = null
            }
        )
    }
}

@Composable
fun MarketplaceCard(
    listing: MarketplaceListingEntity,
    onContact: () -> Unit
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = listing.produceName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = KashmirForestGreen
                        )
                        if (listing.isFeatured) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SaffronAmber)
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text("PRO FEATURED", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Text(
                        text = "${listing.variety} • ${listing.district}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Text(
                    text = listing.expectedPrice,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = KashmirForestGreen
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Stock: ${listing.quantityAvailable}", fontSize = 11.sp, color = Color(0xFF334155))
                    Text("Grade: ${listing.qualityGrade}", fontSize = 11.sp, color = Color(0xFF059669), fontWeight = FontWeight.Medium)
                    Text("Grower: ${listing.farmerName}", fontSize = 11.sp, color = Color.Gray)
                }

                Button(
                    onClick = onContact,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen)
                ) {
                    Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Contact", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun AgriServiceCard(
    service: AgriService,
    onBook: () -> Unit
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
                        text = service.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = KashmirForestGreen
                    )
                    Text(
                        text = service.subtitle,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(KashmirLightGreen)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "₹${service.pricePerUnit.toInt()} ${service.unitLabel}",
                        color = KashmirForestGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = service.description,
                fontSize = 12.sp,
                color = Color(0xFF374151),
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
            service.highlights.forEach { hl ->
                Row(
                    modifier = Modifier.padding(vertical = 1.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(hl, fontSize = 11.sp, color = Color(0xFF4B5563))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onBook,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Book ${service.title}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun AddListingDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, String, String, String) -> Unit
) {
    var produce by remember { mutableStateOf("Apple") }
    var variety by remember { mutableStateOf("Red Delicious Grade A") }
    var quantity by remember { mutableStateOf("100 Boxes (1,800 Kg)") }
    var price by remember { mutableStateOf("₹1,200 / box") }
    var grade by remember { mutableStateOf("Export Grade 1") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("List Produce for Sale", fontWeight = FontWeight.Bold, color = KashmirForestGreen) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = produce,
                    onValueChange = { produce = it },
                    label = { Text("Crop / Produce (e.g. Apple, Saffron)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = variety,
                    onValueChange = { variety = it },
                    label = { Text("Variety") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Available Quantity") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Expected Price (₹)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = grade,
                    onValueChange = { grade = it },
                    label = { Text("Quality Grade") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onAdd(produce, variety, quantity, price, grade) },
                colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen)
            ) {
                Text("Publish Listing")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun BookServiceDialog(
    service: AgriService,
    onDismiss: () -> Unit,
    onConfirm: (String, String, Double, Double, String) -> Unit
) {
    var district by remember { mutableStateOf("Baramulla") }
    var location by remember { mutableStateOf("Apple Orchard, Sopore Road") }
    var kanalsText by remember { mutableStateOf("10") }
    var scheduledDate by remember { mutableStateOf("2026-09-20") }

    val kanals = kanalsText.toDoubleOrNull() ?: 1.0
    val totalFee = kanals * service.pricePerUnit

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Book ${service.title}", fontWeight = FontWeight.Bold, color = KashmirForestGreen) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Rate: ₹${service.pricePerUnit.toInt()} ${service.unitLabel}",
                    fontSize = 12.sp,
                    color = KashmirForestGreen,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = district,
                    onValueChange = { district = it },
                    label = { Text("District in J&K") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Orchard / Village Address") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = kanalsText,
                    onValueChange = { kanalsText = it },
                    label = { Text("Farm Area (Kanals / Units)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = scheduledDate,
                    onValueChange = { scheduledDate = it },
                    label = { Text("Preferred Date (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Total Estimated Charge: ₹${totalFee.toInt()}",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    color = KashmirForestGreen
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(district, location, kanals, totalFee, scheduledDate) },
                colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen)
            ) {
                Text("Confirm Booking")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
