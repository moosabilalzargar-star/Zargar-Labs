package com.example.ui.screens

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.SupportedLanguages
import com.example.ui.navigation.Screen
import com.example.ui.theme.KashmirEmerald
import com.example.ui.theme.KashmirForestGreen
import com.example.ui.theme.KashmirLightGreen
import com.example.ui.theme.SaffronAmber
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.AgriViewModel

@Composable
fun LoginScreen(
    viewModel: AgriViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgot: () -> Unit
) {
    var email by remember { mutableStateOf("farmer@jehlum.ai") }
    var password by remember { mutableStateOf("kashmir2026") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF7))
            .padding(24.dp)
            .testTag("login_screen"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(KashmirLightGreen),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Agriculture, contentDescription = null, tint = KashmirForestGreen, modifier = Modifier.size(36.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Jehlum Sense AI",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = KashmirForestGreen
            )
        )
        Text(
            text = "AI for a smarter, safer Kashmir",
            fontSize = 13.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage!!, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.login(
                    email = email,
                    pass = password,
                    onSuccess = onLoginSuccess,
                    onError = { errorMessage = it }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("login_submit_btn"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen)
        ) {
            Text("Sign In", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Quick 1-Tap Demo Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = {
                    email = "farmer@jehlum.ai"
                    password = "kashmir2026"
                    viewModel.login(email, password, onLoginSuccess) { errorMessage = it }
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Demo Farmer", fontSize = 11.sp, color = KashmirForestGreen)
            }

            OutlinedButton(
                onClick = {
                    email = "admin@jehlum.ai"
                    password = "admin2026"
                    viewModel.login(email, password, onLoginSuccess) { errorMessage = it }
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Demo Admin", fontSize = 11.sp, color = SaffronAmber)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onNavigateToForgot) {
                Text("Forgot Password?", fontSize = 12.sp, color = Color.Gray)
            }
            TextButton(onClick = onNavigateToRegister) {
                Text("Create Account", fontSize = 12.sp, color = KashmirForestGreen, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun RegisterScreen(
    viewModel: AgriViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("Shopian") }
    var kanalsText by remember { mutableStateOf("8.0") }
    var primaryCrops by remember { mutableStateOf("Apple, Walnut") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF7))
            .padding(24.dp)
            .testTag("register_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Join Jehlum Sense AI",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = KashmirForestGreen
                )
            )
            Text(
                text = "Register your orchard or farm in Jammu & Kashmir",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        item {
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }

        item {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }

        item {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }

        item {
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number (+91)") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }

        item {
            OutlinedTextField(
                value = district,
                onValueChange = { district = it },
                label = { Text("District in J&K (e.g. Baramulla, Shopian, Pulwama)") },
                leadingIcon = { Icon(Icons.Default.Place, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }

        item {
            OutlinedTextField(
                value = kanalsText,
                onValueChange = { kanalsText = it },
                label = { Text("Farm Size in Kanals") },
                leadingIcon = { Icon(Icons.Default.Agriculture, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }

        item {
            OutlinedTextField(
                value = primaryCrops,
                onValueChange = { primaryCrops = it },
                label = { Text("Primary Crops (e.g. Apple, Saffron, Walnut)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }

        if (errorMessage != null) {
            item {
                Text(text = errorMessage!!, color = Color.Red, fontSize = 12.sp)
            }
        }

        item {
            Button(
                onClick = {
                    val kanals = kanalsText.toDoubleOrNull() ?: 1.0
                    viewModel.register(
                        fullName = fullName,
                        email = email,
                        pass = password,
                        phone = phone,
                        district = district,
                        kanals = kanals,
                        crops = primaryCrops,
                        onSuccess = onRegisterSuccess,
                        onError = { errorMessage = it }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("register_submit_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen)
            ) {
                Text("Create Account", fontWeight = FontWeight.Bold)
            }
        }

        item {
            TextButton(onClick = onNavigateToLogin) {
                Text("Already have an account? Sign In", fontSize = 12.sp, color = KashmirForestGreen)
            }
        }
    }
}

@Composable
fun ForgotPasswordScreen(
    viewModel: AgriViewModel,
    onBackToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF7))
            .padding(24.dp)
            .testTag("forgot_password_screen"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Reset Password",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = KashmirForestGreen
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Enter your registered email to receive password reset instructions.",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        if (statusMessage != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = statusMessage!!,
                color = KashmirForestGreen,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.forgotPassword(email) { msg ->
                    statusMessage = msg
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = KashmirForestGreen)
        ) {
            Text("Send Reset Link", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onBackToLogin) {
            Text("Back to Sign In", fontSize = 12.sp, color = KashmirForestGreen)
        }
    }
}

@Composable
fun ProfileScreen(
    viewModel: AgriViewModel,
    onNavigateToLogin: () -> Unit,
    onNavigateToAdmin: () -> Unit
) {
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val selectedLanguage by viewModel.selectedLanguage.collectAsStateWithLifecycle()

    val user = currentUser

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF7))
            .padding(16.dp)
            .testTag("profile_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // User Overview Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(KashmirLightGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = KashmirForestGreen,
                            modifier = Modifier.size(54.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = user?.fullName ?: "Farmer Guest",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = KashmirForestGreen
                        )
                    )
                    Text(
                        text = user?.email ?: "guest@jehlum.ai",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (user?.role == "admin") SaffronAmber else KashmirLightGreen)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = if (user?.role == "admin") "ADMINISTRATOR" else "VERIFIED GROWER",
                                color = if (user?.role == "admin") Color.White else KashmirForestGreen,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (user?.isProSubscriber == true) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SuccessGreen)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "PRO CLUB ACTIVE",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Farm Specifications Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Orchard & Land Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = KashmirForestGreen
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    ProfileDetailRow("District", user?.district ?: "Baramulla")
                    ProfileDetailRow("Farm Area", "${user?.farmSizeKanals ?: 12.0} Kanals (~${((user?.farmSizeKanals ?: 12.0) / 8.0)} Acres)")
                    ProfileDetailRow("Primary Crops", user?.primaryCrops ?: "Apple, Walnut")
                    ProfileDetailRow("Contact Phone", user?.phone ?: "+91 94190 12345")
                    ProfileDetailRow("App Language", selectedLanguage.name)
                }
            }
        }

        // Quick Actions & Admin Toggle
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (user?.role == "admin") {
                    Button(
                        onClick = onNavigateToAdmin,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronAmber),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.VerifiedUser, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Open Admin Dashboard", fontWeight = FontWeight.Bold)
                    }
                } else {
                    OutlinedButton(
                        onClick = { viewModel.switchRoleToAdmin() },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = SaffronAmber, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Switch to Admin Mode (Founders Access)", color = KashmirForestGreen, fontSize = 12.sp)
                    }
                }

                Button(
                    onClick = {
                        viewModel.logout()
                        onNavigateToLogin()
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Logout", fontWeight = FontWeight.Bold)
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun ProfileDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 12.sp, color = Color.Gray)
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
    }
}
