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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.db.CropDiaryEntity
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.KashmirEmerald
import com.example.ui.theme.KashmirForestGreen
import com.example.ui.theme.KashmirLightGreen
import com.example.ui.theme.KashmirMint
import com.example.ui.theme.SaffronAmber
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.AgriViewModel

@Composable
fun CropDiaryScreen(
    viewModel: AgriViewModel
) {
    val entries by viewModel.diaryEntries.collectAsStateWithLifecycle()
    val totalExpenses by viewModel.totalExpenses.collectAsStateWithLifecycle()
    val totalIncome by viewModel.totalIncome.collectAsStateWithLifecycle()

    var showAddDialog by remember { mutableStateOf(false) }

    val expenses = totalExpenses ?: 0.0
    val income = totalIncome ?: 0.0
    val netProfit = income - expenses

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF7))
            .padding(horizontal = 16.dp)
            .testTag("crop_diary_screen"),
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
                    Text(
                        text = "Crop Diary & Ledger",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = KashmirForestGreen
                        )
                    )
                    Text(
                        text = "Track dates, chemical sprays, expenses, harvests, and profit.",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray, fontSize = 12.sp)
                    )
                }

                Button(
                    onClick = { showAddDialog = true },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen),
                    modifier = Modifier.testTag("add_diary_entry_btn")
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Entry", fontSize = 12.sp)
                }
            }
        }

        // Summary Cards (Expenses, Income, Net Profit)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SummaryStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Expenses",
                    amount = "₹${expenses.toInt()}",
                    color = ErrorRed,
                    icon = Icons.Default.TrendingDown
                )
                SummaryStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Income",
                    amount = "₹${income.toInt()}",
                    color = SuccessGreen,
                    icon = Icons.Default.TrendingUp
                )
                SummaryStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Net Profit",
                    amount = "₹${netProfit.toInt()}",
                    color = if (netProfit >= 0) KashmirForestGreen else ErrorRed,
                    icon = Icons.Default.LocalFlorist
                )
            }
        }

        // Entries Section
        item {
            Text(
                text = "Recorded Farm Activities (${entries.size})",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = KashmirForestGreen
                )
            )
        }

        if (entries.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Book, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No diary entries recorded yet.", fontWeight = FontWeight.Bold, color = Color.Gray)
                        Text("Click 'Add Entry' above to log your spray dates, labor costs, or harvest income.", fontSize = 12.sp, color = Color.Gray, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    }
                }
            }
        }

        items(entries) { entry ->
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
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(KashmirLightGreen)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = entry.activityType,
                                        color = KashmirForestGreen,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = entry.cropName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = KashmirForestGreen
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Date: ${entry.date}",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }

                        IconButton(
                            onClick = { viewModel.deleteDiaryEntry(entry.id) },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFF94A3B8), modifier = Modifier.size(18.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = entry.description,
                        fontSize = 12.sp,
                        color = Color(0xFF334155),
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (entry.expenseAmount > 0) {
                            Text(
                                text = "Cost: -₹${entry.expenseAmount.toInt()}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = ErrorRed
                            )
                        }
                        if (entry.harvestAmountKg > 0) {
                            Text(
                                text = "Yield: ${entry.harvestAmountKg.toInt()} Kg",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = KashmirForestGreen
                            )
                        }
                        if (entry.incomeAmount > 0) {
                            Text(
                                text = "Income: +₹${entry.incomeAmount.toInt()}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }

    if (showAddDialog) {
        AddDiaryEntryDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { date, crop, activity, desc, expense, harvest, incomeVal ->
                viewModel.addDiaryEntry(date, crop, activity, desc, expense, harvest, incomeVal)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun SummaryStatCard(
    modifier: Modifier = Modifier,
    title: String,
    amount: String,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(title, fontSize = 11.sp, color = Color.Gray)
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(14.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = amount,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = color
            )
        }
    }
}

@Composable
fun AddDiaryEntryDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, String, String, Double, Double, Double) -> Unit
) {
    var date by remember { mutableStateOf("2026-09-14") }
    var crop by remember { mutableStateOf("Apple") }
    var activity by remember { mutableStateOf("Spray") }
    var desc by remember { mutableStateOf("Applied protective fungicide spray before rain.") }
    var expenseText by remember { mutableStateOf("2500") }
    var harvestText by remember { mutableStateOf("0") }
    var incomeText by remember { mutableStateOf("0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Crop Diary Entry", fontWeight = FontWeight.Bold, color = KashmirForestGreen) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = crop,
                    onValueChange = { crop = it },
                    label = { Text("Crop (e.g. Apple, Saffron, Walnut)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = activity,
                    onValueChange = { activity = it },
                    label = { Text("Activity (Spray, Pruning, Harvest, Weeding)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Notes / Observations") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = expenseText,
                    onValueChange = { expenseText = it },
                    label = { Text("Expense Cost (₹)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = harvestText,
                    onValueChange = { harvestText = it },
                    label = { Text("Harvest Amount (Kg / Boxes)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = incomeText,
                    onValueChange = { incomeText = it },
                    label = { Text("Income Received (₹)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val exp = expenseText.toDoubleOrNull() ?: 0.0
                    val harv = harvestText.toDoubleOrNull() ?: 0.0
                    val inc = incomeText.toDoubleOrNull() ?: 0.0
                    onAdd(date, crop, activity, desc, exp, harv, inc)
                },
                colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen)
            ) {
                Text("Save Entry")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
