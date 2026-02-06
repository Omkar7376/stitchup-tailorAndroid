package com.tailorapp.stitchup.presentation.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tailorapp.stitchup.domain.model.customer.getCustomer.GetCustomerDetailsResponse
import com.tailorapp.stitchup.presentation.common.TopBar
import com.tailorapp.stitchup.ui.theme.DarkBrown
import com.tailorapp.stitchup.ui.theme.SoftGolden

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerProfileScreen(
    viewModel: CustomersViewModel = hiltViewModel(),
    customerId: Int? = null,
    navController : NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCustomerDetails(id = customerId ?: 0)
    }

    val uiColor = if (isSystemInDarkTheme()) SoftGolden else DarkBrown
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                modifier = Modifier.shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                    clip = true
                ),
                text = "Customer Details",
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .clickable{
                                navController.popBackStack()
                            },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = uiColor,
                        )
                }
            )
        }
        ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    uiState.isLoading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.3f))
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = {}
                                ),
                            contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                color = uiColor,
                                strokeWidth = 3.dp
                            )
                        }
                    }

                    uiState.customerDetails != null -> {
                        CustomerDetailsContent(
                            details = uiState.customerDetails!!
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomerDetailsContent(details: GetCustomerDetailsResponse) {
    val customer = details.customer.firstOrNull()
    val shirt = details.shirtMeasurements.firstOrNull()
    val pant = details.pantMeasurements.firstOrNull()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            SectionCard(title = "Customer Details") {
                InfoRow("Name", customer?.NAME)
                InfoRow("Age", customer?.AGE)
                InfoRow("Gender", customer?.GENDER)
                InfoRow("Mobile", customer?.MOB_NO)
                InfoRow("Address", customer?.ADDRESS)
                InfoRow("Book No", customer?.BOOKNO)
            }
        }

        item {
            SectionCard(title = "Shirt Measurements") {
                InfoRow("Chest", shirt?.CHEST)
                InfoRow("Back", shirt?.BACK)
                InfoRow("Bicep", shirt?.BICEP)
                InfoRow("Sleeve", shirt?.SLEEVE)
                InfoRow("Cuff", shirt?.CUFF)
                InfoRow("Collar", shirt?.COLLAR)
                InfoRow("Shoulder", shirt?.SHOULDER)
                InfoRow("Front1", shirt?.FRONT1)
                InfoRow("Front2", shirt?.FRONT2)
                InfoRow("Front3", shirt?.FRONT3)
                InfoRow("Length", shirt?.LENGTH)
                InfoRow("Amount", shirt?.AMOUNT)
            }
        }

        item {
            SectionCard(title = "Pant Measurements") {
                InfoRow("Amount", pant?.AMOUNT)
                InfoRow("Waist", pant?.WAIST)
                InfoRow("Thigh", pant?.THIGH)
                InfoRow("Seat", pant?.SEAT)
                InfoRow("Rise", pant?.RISE)
                InfoRow("Knee", pant?.KNEE)
                InfoRow("Bottom", pant?.BOTTOM)
                InfoRow("Inside Length", pant?.INSIDE_LENGTH)
                InfoRow("Outside Length", pant?.OUTSIDE_LENGTH)
            }
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            content()
        }
    }
}

@Composable
fun InfoRow(label: String, value: Any?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = value?.toString() ?: "-",
            color = Color.Gray
        )
    }
}
