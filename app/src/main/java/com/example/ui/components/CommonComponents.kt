package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.data.model.LanguageOption
import com.example.data.model.SupportedLanguages
import com.example.ui.navigation.Screen
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.KashmirEmerald
import com.example.ui.theme.KashmirForestGreen
import com.example.ui.theme.KashmirLightGreen
import com.example.ui.theme.KashmirMint
import com.example.ui.theme.SaffronAmber
import com.example.ui.theme.SaffronLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JehlumTopBar(
    currentScreen: Screen,
    selectedLanguage: LanguageOption,
    onLanguageSelected: (LanguageOption) -> Unit,
    onProfileClick: () -> Unit,
    userRole: String? = null,
    onAdminClick: () -> Unit = {}
) {
    var langMenuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { }
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(KashmirLightGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFlorist,
                        contentDescription = "Logo",
                        tint = KashmirForestGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Jehlum Sense AI",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "J&K Farming Intelligence",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = KashmirLightGreen,
                            fontSize = 10.sp
                        )
                    )
                }
            }
        },
        actions = {
            // Language selector button
            Box {
                IconButton(
                    onClick = { langMenuExpanded = true },
                    modifier = Modifier.testTag("language_selector_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(KashmirEmerald)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Translate,
                            contentDescription = "Language",
                            tint = SaffronAmber,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = selectedLanguage.nativeLabel,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                DropdownMenu(
                    expanded = langMenuExpanded,
                    onDismissRequest = { langMenuExpanded = false }
                ) {
                    SupportedLanguages.forEach { lang ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "${lang.nativeLabel} (${lang.name})",
                                    fontWeight = if (lang.code == selectedLanguage.code) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            onClick = {
                                onLanguageSelected(lang)
                                langMenuExpanded = false
                            }
                        )
                    }
                }
            }

            // Admin button if role is admin
            if (userRole == "admin") {
                IconButton(
                    onClick = onAdminClick,
                    modifier = Modifier.testTag("admin_header_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = "Admin Panel",
                        tint = SaffronAmber
                    )
                }
            }

            // Profile Avatar Button
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier.testTag("profile_button")
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Profile",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = KashmirForestGreen,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

@Composable
fun JehlumBottomBar(
    currentRoute: String,
    onNavigate: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = { onNavigate(Screen.Home) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = KashmirForestGreen,
                selectedTextColor = KashmirForestGreen,
                indicatorColor = KashmirLightGreen
            ),
            modifier = Modifier.testTag("nav_home")
        )

        NavigationBarItem(
            selected = currentRoute == Screen.AiChat.route,
            onClick = { onNavigate(Screen.AiChat) },
            icon = { Icon(Icons.Default.Chat, contentDescription = "AI Chat") },
            label = { Text("AI Chat", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = KashmirForestGreen,
                selectedTextColor = KashmirForestGreen,
                indicatorColor = KashmirLightGreen
            ),
            modifier = Modifier.testTag("nav_chat")
        )

        NavigationBarItem(
            selected = currentRoute == Screen.CropAnalysis.route,
            onClick = { onNavigate(Screen.CropAnalysis) },
            icon = { Icon(Icons.Default.LocalFlorist, contentDescription = "Crop Doctor") },
            label = { Text("Crop Doctor", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = KashmirForestGreen,
                selectedTextColor = KashmirForestGreen,
                indicatorColor = KashmirLightGreen
            ),
            modifier = Modifier.testTag("nav_analysis")
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Marketplace.route,
            onClick = { onNavigate(Screen.Marketplace) },
            icon = { Icon(Icons.Default.Paid, contentDescription = "Mandi & Earn") },
            label = { Text("Mandi/Earn", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = KashmirForestGreen,
                selectedTextColor = KashmirForestGreen,
                indicatorColor = KashmirLightGreen
            ),
            modifier = Modifier.testTag("nav_marketplace")
        )

        NavigationBarItem(
            selected = currentRoute == Screen.CropDiary.route,
            onClick = { onNavigate(Screen.CropDiary) },
            icon = { Icon(Icons.Default.Book, contentDescription = "Diary") },
            label = { Text("Farm Diary", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = KashmirForestGreen,
                selectedTextColor = KashmirForestGreen,
                indicatorColor = KashmirLightGreen
            ),
            modifier = Modifier.testTag("nav_diary")
        )
    }
}

@Composable
fun ResponsibleAiDisclaimerCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFBEB)
        ),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFFDE68A)))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Responsible AI Advisory",
                tint = SaffronAmber,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Responsible AI & Qualified Agronomy Notice",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92400E)
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Jehlum Sense AI provides probabilistic guidance based on SKUAST schedules and visual signs. It does NOT replace qualified laboratory diagnoses. Always consult SKUAST scientists or your local Horticulture Officer before applying scheduled chemical sprays.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                        color = Color(0xFF78350F),
                        lineHeight = 15.sp
                    )
                )
            }
        }
    }
}
