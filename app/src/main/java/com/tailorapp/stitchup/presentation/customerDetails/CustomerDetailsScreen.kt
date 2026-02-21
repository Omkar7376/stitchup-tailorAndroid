package com.tailorapp.stitchup.presentation.customerDetails

import android.net.Uri
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tailorapp.stitchup.data.remote.dto.customerDto.updateCustomerDto.UpdateCustomerRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.updatePantDto.UpdatePantRequestDto
import com.tailorapp.stitchup.domain.model.customer.getCustomer.GetCustomerDetailsResponse
import com.tailorapp.stitchup.domain.model.customer.updateShirt.UpdateShirtRequest
import com.tailorapp.stitchup.presentation.addCustomer.dataClasses.MeasurementField
import com.tailorapp.stitchup.presentation.common.CommonText
import com.tailorapp.stitchup.presentation.common.CommonTextField
import com.tailorapp.stitchup.presentation.common.TopBar
import com.tailorapp.stitchup.presentation.customerDetails.componenents.GenderDropdown
import com.tailorapp.stitchup.ui.theme.DarkBrown
import com.tailorapp.stitchup.ui.theme.SoftGolden

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerProfileScreen(
    viewModel: CustomerDetailsViewModel = hiltViewModel(),
    customerId: Int? = null,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    var showCustomerDialog by remember { mutableStateOf(false) }
    var showShirtDialog by remember { mutableStateOf(false) }
    var showPantDialog by remember { mutableStateOf(false) }
    var showAddShirtDialog by remember { mutableStateOf(false) }

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
                            .clickable {
                                navController.popBackStack()
                            },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = uiColor,
                    )
                },
                actions = {
                    TextButton(
                        onClick = {
                            val customer = uiState.customerDetails?.customer?.firstOrNull()
                            val encodedName = Uri.encode(customer?.NAME)
                            val encodedMobile = Uri.encode(customer?.MOB_NO)
                            navController.navigate(
                                "newOrder/${customer?.ID}/$encodedName/$encodedMobile"
                            )
                        }
                    ) {
                        CommonText("Create Order")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    uiState.customerDetails != null -> {
                        CustomerDetailsContent(
                            details = uiState.customerDetails!!,
                            onEditCustomer = {
                                val customer = uiState.customerDetails?.customer?.firstOrNull()
                                viewModel.setCustomerForEditing(customer)
                                showCustomerDialog = true
                            },
                            onEditShirt = {
                                val shirt =
                                    uiState.customerDetails?.shirtMeasurements?.firstOrNull()
                                viewModel.setShirtForEditing(shirt!!)
                                showShirtDialog = true
                            },
                            onEditPant = {
                                val pant = uiState.customerDetails?.pantMeasurements?.firstOrNull()
                                viewModel.setPantForEditing(pant!!)
                                showPantDialog = true
                            }
                        )
                    }
                }
            }
        }
        if (uiState.isLoading) {
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
    }
    if (showCustomerDialog) {
        UpdateCustomer(
            name = uiState.name,
            gender = uiState.gender,
            mobile = uiState.mobile,
            address = uiState.address,

            onNameChanged = viewModel::onNameChanged,
            onGenderChanged = viewModel::onGenderChanged,
            onMobileChanged = viewModel::onMobileChanged,
            onAddressChanged = viewModel::onAddressChanged,

            onUpdateClick = {
                val request = UpdateCustomerRequestDto(
                    name = uiState.name,
                    gender = uiState.gender,
                    mob_num = uiState.mobile,
                    address = uiState.address
                )
                viewModel.updateCustomerDetails(
                    id = customerId ?: 0,
                    updateCustomerRequest = request
                )
                showCustomerDialog = false
            },
            onDismiss = {
                showCustomerDialog = false
            }
        )
    }

    if (showShirtDialog) {
        UpdateShirt(
            shirtChest = uiState.shirtChest,
            shirtLength = uiState.shirtLength,
            shirtShoulder = uiState.shirtShoulder,
            shirtSleeve = uiState.shirtSleeve,
            shirtBicep = uiState.shirtBicep,
            shirtCuff = uiState.shirtCuff,
            shirtCollar = uiState.shirtCollar,
            shirtBack = uiState.shirtBack,
            shirtFront1 = uiState.shirtFront1,
            shirtFront2 = uiState.shirtFront2,
            shirtFront3 = uiState.shirtFront3,

            onShirtChestChanged = viewModel::onShirtChestChanged,
            onShirtLengthChanged = viewModel::onShirtLengthChanged,
            onShirtShoulderChanged = viewModel::onShirtShoulderChanged,
            onShirtSleeveChanged = viewModel::onShirtSleeveChanged,
            onShirtBicepChanged = viewModel::onShirtBicepChanged,
            onShirtCuffChanged = viewModel::onShirtCuffChanged,
            onShirtCollarChanged = viewModel::onShirtCollarChanged,
            onShirtBackChanged = viewModel::onShirtBackChanged,
            onShirtFront1Changed = viewModel::onShirtFront1Changed,
            onShirtFront2Changed = viewModel::onShirtFront2Changed,
            onShirtFront3Changed = viewModel::onShirtFront3Changed,

            onUpdateClick = {
                val request = UpdateShirtRequest(
                    back = uiState.shirtBack.toFloatOrNull() ?: 0,
                    chest = uiState.shirtChest.toFloatOrNull() ?: 0,
                    length = uiState.shirtLength.toFloatOrNull() ?: 0,
                    bicep = uiState.shirtBicep.toFloatOrNull() ?: 0,
                    sleeve = uiState.shirtSleeve.toFloatOrNull() ?: 0,
                    cuff = uiState.shirtCuff.toFloatOrNull() ?: 0,
                    collar = uiState.shirtCollar.toFloatOrNull() ?: 0,
                    shoulder = uiState.shirtShoulder.toFloatOrNull() ?: 0,
                    front1 = uiState.shirtFront1.toFloatOrNull() ?: 0,
                    front2 = uiState.shirtFront2.toFloatOrNull() ?: 0,
                    front3 = uiState.shirtFront3.toFloatOrNull() ?: 0,
                )
                viewModel.updateShirtDetails(
                    id = customerId ?: 0,
                    updateShirtRequest = request
                )
                showShirtDialog = false
            },
            onDismiss = {
                showShirtDialog = false
            }
        )
    }

    if (showPantDialog) {
        UpdatePant(
            pantOutsideLength = uiState.pantOutsideLength,
            pantInsideLength = uiState.pantInsideLength,
            pantRise = uiState.pantRise,
            pantWaist = uiState.pantWaist,
            pantSeat = uiState.pantSeat,
            pantThigh = uiState.pantThigh,
            pantKnee = uiState.pantKnee,
            pantBottom = uiState.pantBottom,

            onPantOutsideLengthChanged = viewModel::onPantOutsideChanged,
            onPantInsideLengthChanged = viewModel::onPantInsideChanged,
            onPantRiseChanged = viewModel::onPantRiseChanged,
            onPantWaistChanged = viewModel::onPantWaistChanged,
            onPantSeatChanged = viewModel::onPantSeatChanged,
            onPantThighChanged = viewModel::onPantThighChanged,
            onPantKneeChanged = viewModel::onPantKneeChanged,
            onPantBottomChanged = viewModel::onPantBottomChanged,

            onUpdateClick = {
                val request = UpdatePantRequestDto(
                    outsideLength = uiState.pantOutsideLength.toFloatOrNull() ?: 0,
                    bottom = uiState.pantBottom.toFloatOrNull() ?: 0,
                    insideLength = uiState.pantInsideLength.toFloatOrNull() ?: 0,
                    knee = uiState.pantKnee.toFloatOrNull() ?: 0,
                    rise = uiState.pantRise.toFloatOrNull() ?: 0,
                    seat = uiState.pantSeat.toFloatOrNull() ?: 0,
                    thigh = uiState.pantThigh.toFloatOrNull() ?: 0,
                    waist = uiState.pantWaist.toFloatOrNull() ?: 0,
                )
                viewModel.updatePantDetails(
                    id = customerId ?: 0,
                    updatePantRequest = request
                )
                showPantDialog = false
            },
            onDismiss = {
                showPantDialog = false
            }
        )
    }

}

@Composable
fun CustomerDetailsContent(
    details: GetCustomerDetailsResponse,
    onEditCustomer: () -> Unit = {},
    onEditShirt: () -> Unit = {},
    onEditPant: () -> Unit = {},
    onAddShirtClick: () -> Unit = {},
    onAddPantClick: () -> Unit = {}
) {
    val customer = details.customer.firstOrNull()
    val shirt = details.shirtMeasurements.firstOrNull()
    val pant = details.pantMeasurements.firstOrNull()
    val uiColor = if (isSystemInDarkTheme()) SoftGolden else DarkBrown

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            SectionCard(title = "Customer Details", onClick = onEditCustomer) {
                InfoRow("Book No", customer?.BOOKNO)
                InfoRow("Name", customer?.NAME)
                InfoRow("Gender", customer?.GENDER)
                InfoRow("Mobile", customer?.MOB_NO)
                InfoRow("Address", customer?.ADDRESS)
                InfoRow("Created Date", formatDate(customer?.CREATED_AT))
                InfoRow("Updated Date", formatDate(customer?.UPDATED_AT))
            }
        }

        if (shirt != null) {
            item {
                SectionCard(title = "Shirt Measurements", onClick = onEditShirt) {
                    InfoRow("Back", shirt.BACK)
                    InfoRow("Shoulder", shirt.SHOULDER)
                    InfoRow("Collar", shirt.COLLAR)
                    InfoRow("Chest", shirt.CHEST)
                    InfoRow("Length", shirt.LENGTH)
                    InfoRow("Sleeve", shirt.SLEEVE)
                    InfoRow("Bicep", shirt.BICEP)
                    InfoRow("Cuff", shirt.CUFF)
                    InfoRow("Front1", shirt.FRONT1)
                    InfoRow("Front2", shirt.FRONT2)
                    InfoRow("Front3", shirt.FRONT3)
                }
            }
        } else {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CommonText("Add Shirt Measurement")
                    IconButton(
                        onClick = onAddShirtClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = uiColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        if (pant != null) {
            item {
                SectionCard(title = "Pant Measurements", onClick = onEditPant) {
                    InfoRow("Outside Length", pant.OUTSIDE_LENGTH)
                    InfoRow("Inside Length", pant.INSIDE_LENGTH)
                    InfoRow("Rise", pant.RISE)
                    InfoRow("Waist", pant.WAIST)
                    InfoRow("Seat", pant.SEAT)
                    InfoRow("Thigh", pant.THIGH)
                    InfoRow("Knee", pant.KNEE)
                    InfoRow("Bottom", pant.BOTTOM)
                }
            }
        } else {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CommonText("Add Pant Measurement")
                    IconButton(
                        onClick = onAddPantClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = uiColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

fun formatDate(dateString: String?): String {
    if (dateString.isNullOrBlank()) return "N/A"
    return try {
        if (dateString.contains("T")) dateString.substringBefore("T")
        else dateString.take(10)
    } catch (e: Exception) {
        "N/A"
    }
}

@Composable
fun SectionCard(
    title: String,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val uiColor = if (isSystemInDarkTheme()) SoftGolden else DarkBrown
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText(title)
                IconButton(onClick = { onClick() }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = uiColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

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

@Composable
fun UpdateCustomer(
    name: String,
    gender: String,
    mobile: String,
    address: String,

    onNameChanged: (String) -> Unit,
    onGenderChanged: (String) -> Unit,
    onMobileChanged: (String) -> Unit,
    onAddressChanged: (String) -> Unit,

    onUpdateClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val uiColor = if (isSystemInDarkTheme()) SoftGolden else DarkBrown

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { CommonText("Edit Customer Details") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CommonTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Name",
                    value = name,
                    onValueChange = { onNameChanged(it) },
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GenderDropdown(
                        modifier = Modifier.weight(1f),
                        selectedGender = gender,
                        onGenderSelected = { onGenderChanged(it) }
                    )
                }
                CommonTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Mobile",
                    value = mobile,
                    onValueChange = { onMobileChanged(it) },
                )
                CommonTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Address",
                    value = address,
                    onValueChange = { onAddressChanged(it) },
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onUpdateClick()
                }
            ) {
                Text("Update", color = uiColor)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel", color = uiColor)
            }
        }
    )
}

@Composable
fun UpdateShirt(
    shirtChest: String,
    shirtLength: String,
    shirtShoulder: String,
    shirtSleeve: String,
    shirtBicep: String,
    shirtCuff: String,
    shirtCollar: String,
    shirtBack: String,
    shirtFront1: String,
    shirtFront2: String,
    shirtFront3: String,

    onShirtChestChanged: (String) -> Unit,
    onShirtLengthChanged: (String) -> Unit,
    onShirtShoulderChanged: (String) -> Unit,
    onShirtSleeveChanged: (String) -> Unit,
    onShirtBicepChanged: (String) -> Unit,
    onShirtCuffChanged: (String) -> Unit,
    onShirtCollarChanged: (String) -> Unit,
    onShirtBackChanged: (String) -> Unit,
    onShirtFront1Changed: (String) -> Unit,
    onShirtFront2Changed: (String) -> Unit,
    onShirtFront3Changed: (String) -> Unit,

    onUpdateClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val fields = listOf(
        MeasurementField("Back", shirtBack, onShirtBackChanged),
        MeasurementField("Shoulder", shirtShoulder, onShirtShoulderChanged),
        MeasurementField("Collar", shirtCollar, onShirtCollarChanged),
        MeasurementField("Chest", shirtChest, onShirtChestChanged),
        MeasurementField("Length", shirtLength, onShirtLengthChanged),
        MeasurementField("Sleeve", shirtSleeve, onShirtSleeveChanged),
        MeasurementField("Bicep", shirtBicep, onShirtBicepChanged),
        MeasurementField("Cuff", shirtCuff, onShirtCuffChanged),
        MeasurementField("Front1", shirtFront1, onShirtFront1Changed),
        MeasurementField("Front2", shirtFront2, onShirtFront2Changed),
        MeasurementField("Front3", shirtFront3, onShirtFront3Changed),
    )

    val uiColor = if (isSystemInDarkTheme()) SoftGolden else DarkBrown

    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        title = { CommonText("Edit Shirt Details") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                CommonText("Shirt Measurement")

                fields.chunked(3).forEach { rowFields ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        rowFields.forEach { field ->
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                CommonTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    label = field.label,
                                    value = field.value,
                                    onValueChange = field.onValueChange
                                )
                            }
                        }

                        repeat(3 - rowFields.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onUpdateClick()
                }
            ) {
                Text("Update", color = uiColor)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel", color = uiColor)
            }
        }
    )
}

@Composable
fun UpdatePant(
    pantOutsideLength: String,
    pantInsideLength: String,
    pantRise: String,
    pantWaist: String,
    pantSeat: String,
    pantThigh: String,
    pantKnee: String,
    pantBottom: String,

    onPantOutsideLengthChanged: (String) -> Unit,
    onPantInsideLengthChanged: (String) -> Unit,
    onPantRiseChanged: (String) -> Unit,
    onPantWaistChanged: (String) -> Unit,
    onPantSeatChanged: (String) -> Unit,
    onPantThighChanged: (String) -> Unit,
    onPantKneeChanged: (String) -> Unit,
    onPantBottomChanged: (String) -> Unit,

    onUpdateClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val fields = listOf(
        MeasurementField("Outside Length", pantOutsideLength, onPantOutsideLengthChanged),
        MeasurementField("Inside Length", pantInsideLength, onPantInsideLengthChanged),
        MeasurementField("Rise", pantRise, onPantRiseChanged),
        MeasurementField("Waist", pantWaist, onPantWaistChanged),
        MeasurementField("Seat", pantSeat, onPantSeatChanged),
        MeasurementField("Thigh", pantThigh, onPantThighChanged),
        MeasurementField("Knee", pantKnee, onPantKneeChanged),
        MeasurementField("Bottom", pantBottom, onPantBottomChanged),
    )

    val uiColor = if (isSystemInDarkTheme()) SoftGolden else DarkBrown

    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        title = { CommonText("Edit Pant Details") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                CommonText("Pant Measurement")

                fields.chunked(3).forEach { rowFields ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        rowFields.forEach { field ->
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                CommonTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    label = field.label,
                                    value = field.value,
                                    onValueChange = field.onValueChange
                                )
                            }
                        }

                        repeat(3 - rowFields.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onUpdateClick()
                }
            ) {
                Text("Update", color = uiColor)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel", color = uiColor)
            }
        }
    )
}

@Composable
fun AddShirt(
    shirtQnt: String,
    shirtAmt: String,
    shirtChest: String,
    shirtLength: String,
    shirtShoulder: String,
    shirtSleeve: String,
    shirtBicep: String,
    shirtCuff: String,
    shirtCollar: String,
    shirtBack: String,
    shirtFront1: String,
    shirtFront2: String,
    shirtFront3: String,

    onShirtQntChanged: (String) -> Unit,
    onShirtAmtChanged: (String) -> Unit,
    onShirtChestChanged: (String) -> Unit,
    onShirtLengthChanged: (String) -> Unit,
    onShirtShoulderChanged: (String) -> Unit,
    onShirtSleeveChanged: (String) -> Unit,
    onShirtBicepChanged: (String) -> Unit,
    onShirtCuffChanged: (String) -> Unit,
    onShirtCollarChanged: (String) -> Unit,
    onShirtBackChanged: (String) -> Unit,
    onShirtFront1Changed: (String) -> Unit,
    onShirtFront2Changed: (String) -> Unit,
    onShirtFront3Changed: (String) -> Unit,

    onAddClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val fields = listOf(
        MeasurementField("Quantity", shirtQnt, onShirtQntChanged),
        MeasurementField("Amount", shirtAmt, onShirtAmtChanged),
        MeasurementField("Back", shirtBack, onShirtBackChanged),
        MeasurementField("Shoulder", shirtShoulder, onShirtShoulderChanged),
        MeasurementField("Collar", shirtCollar, onShirtCollarChanged),
        MeasurementField("Chest", shirtChest, onShirtChestChanged),
        MeasurementField("Length", shirtLength, onShirtLengthChanged),
        MeasurementField("Sleeve", shirtSleeve, onShirtSleeveChanged),
        MeasurementField("Bicep", shirtBicep, onShirtBicepChanged),
        MeasurementField("Cuff", shirtCuff, onShirtCuffChanged),
        MeasurementField("Front1", shirtFront1, onShirtFront1Changed),
        MeasurementField("Front2", shirtFront2, onShirtFront2Changed),
        MeasurementField("Front3", shirtFront3, onShirtFront3Changed),
    )

    val uiColor = if (isSystemInDarkTheme()) SoftGolden else DarkBrown

    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        title = { CommonText("Add Shirt Details") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                CommonText("Shirt Measurement")

                fields.chunked(3).forEach { rowFields ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        rowFields.forEach { field ->
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                CommonTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    label = field.label,
                                    value = field.value,
                                    onValueChange = field.onValueChange
                                )
                            }
                        }

                        repeat(3 - rowFields.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAddClick()
                }
            ) {
                Text("Add", color = uiColor)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel", color = uiColor)
            }
        }
    )
}