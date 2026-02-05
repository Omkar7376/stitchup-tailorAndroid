package com.tailorapp.stitchup.presentation.addCustomer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tailorapp.stitchup.presentation.addCustomer.dataClasses.MeasurementField
import com.tailorapp.stitchup.presentation.common.ButtonCommon
import com.tailorapp.stitchup.presentation.common.CommonTextField
import com.tailorapp.stitchup.presentation.common.ExpandableSection
import com.tailorapp.stitchup.presentation.common.TopBar
import com.tailorapp.stitchup.ui.theme.DarkBrown
import com.tailorapp.stitchup.ui.theme.SoftGolden
import com.tailorapp.stitchup.ui.theme.dimens
import com.tailorapp.stitchup.ui.theme.focusedTextFieldText
import com.tailorapp.stitchup.ui.theme.textFieldContainer
import com.tailorapp.stitchup.ui.theme.unfocusedTextFieldText
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomerScreen(
    navController: NavController,
    viewModel: AddCustomerViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.value.message) {
        uiState.value.message?.let { errorMessage ->
            snackbarHostState.showSnackbar(errorMessage)
            viewModel.clearMessage()
        }
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
                text = "New Customers",
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
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    CustomerDetails(
                        name = uiState.value.name,
                        age = uiState.value.age,
                        gender = uiState.value.gender,
                        mobile = uiState.value.mobile,
                        address = uiState.value.address,

                        onNameChanged = { viewModel.onNameChanged(it) },
                        onAgeChanged = { viewModel.onAgeChanged(it) },
                        onGenderChanged = { viewModel.onGenderChanged(it) },
                        onMobileChanged = { viewModel.onMobileChanged(it) },
                        onAddressChanged = { viewModel.onAddressChanged(it) },
                    )
                }

                item {
                    OrderDetails(
                        orderDate = uiState.value.orderDate,
                        deliveryDate = uiState.value.deliveryDate,
                        orderType = uiState.value.orderType,
                        onOrderDateChanged = { viewModel.onOrderDateChanged(it) },
                        onDeliveryDateChanged = { viewModel.onOrderDeliveryDateChanged(it) },
                        onOrderTypeChanged = { viewModel.onOrderTypeChanged(it) }
                    )
                }

                if (uiState.value.orderType == "Shirt" || uiState.value.orderType == "Both") {
                    item {
                        ShirtMeasurement(
                            shirtQty = uiState.value.shirtQty,
                            shirtAmt = uiState.value.shirtAmt,
                            shirtChest = uiState.value.shirtChest,
                            shirtLength = uiState.value.shirtLength,
                            shirtShoulder = uiState.value.shirtShoulder,
                            shirtSleeve = uiState.value.shirtSleeve,
                            shirtBicep = uiState.value.shirtBicep,
                            shirtCuff = uiState.value.shirtCuff,
                            shirtCollar = uiState.value.shirtCollar,
                            shirtBack = uiState.value.shirtBack,
                            shirtFront1 = uiState.value.shirtFront1,
                            shirtFront2 = uiState.value.shirtFront2,
                            shirtFront3 = uiState.value.shirtFront3,

                            onShirtQtyChanged = { viewModel.onShirtQntChanged(it) },
                            onShirtAmtChanged = { viewModel.onShirtAmtChanged(it) },
                            onShirtChestChanged = { viewModel.onShirtChestChanged(it) },
                            onShirtLengthChanged = { viewModel.onShirtLengthChanged(it) },
                            onShirtShoulderChanged = { viewModel.onShirtShoulderChanged(it) },
                            onShirtSleeveChanged = { viewModel.onShirtSleeveChanged(it) },
                            onShirtBicepChanged = { viewModel.onShirtBicepChanged(it) },
                            onShirtCuffChanged = { viewModel.onShirtCuffChanged(it) },
                            onShirtCollarChanged = { viewModel.onShirtCollarChanged(it) },
                            onShirtBackChanged = { viewModel.onShirtBackChanged(it) },
                            onShirtFront1Changed = { viewModel.onShirtFront1Changed(it) },
                            onShirtFront2Changed = { viewModel.onShirtFront2Changed(it) },
                            onShirtFront3Changed = { viewModel.onShirtFront3Changed(it) },
                        )
                    }
                } else null

                if (uiState.value.orderType == "Pant" || uiState.value.orderType == "Both") {
                    item {
                        PantMeasurement(
                            pantQty = uiState.value.pantQty,
                            pantAmt = uiState.value.pantAmt,
                            pantOutsideLength = uiState.value.pantOutsideLength,
                            pantInsideLength = uiState.value.pantInsideLength,
                            pantRise = uiState.value.pantRise,
                            pantWaist = uiState.value.pantWaist,
                            pantSeat = uiState.value.pantSeat,
                            pantThigh = uiState.value.pantThigh,
                            pantKnee = uiState.value.pantKnee,
                            pantBottom = uiState.value.pantBottom,

                            onPantQtyChanged = { viewModel.onPantQntChanged(it) },
                            onPantAmtChanged = { viewModel.onPantAmtChanged(it) },
                            onPantOutsideLengthChanged = { viewModel.onPantOutsideChanged(it) },
                            onPantInsideLengthChanged = { viewModel.onPantInsideChanged(it) },
                            onPantRiseChanged = { viewModel.onPantRiseChanged(it) },
                            onPantWaistChanged = { viewModel.onPantWaistChanged(it) },
                            onPantSeatChanged = { viewModel.onPantSeatChanged(it) },
                            onPantThighChanged = { viewModel.onPantThighChanged(it) },
                            onPantKneeChanged = { viewModel.onPantKneeChanged(it) },
                            onPantBottomChanged = { viewModel.onPantBottomChanged(it) },
                        )
                    }
                } else null

                item {
                    Payment(
                        discount = uiState.value.discount,
                        advance = uiState.value.advance,
                        note = uiState.value.note,

                        onDiscountChanged = { viewModel.onDiscountChanged(it) },
                        onAdvanceChanged = { viewModel.onAdvanceChanged(it) },
                        onNoteChanged = { viewModel.onNoteChanged(it) }
                    )
                }

                item {
                    ButtonCommon(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Create Customer",
                        enabled = true,
                        onClick = {
                            viewModel.onCreateCustomerClicked()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomerDetails(
    name: String,
    age: String,
    gender: String,
    mobile: String,
    address: String,

    onNameChanged: (String) -> Unit,
    onAgeChanged: (String) -> Unit,
    onGenderChanged: (String) -> Unit,
    onMobileChanged: (String) -> Unit,
    onAddressChanged: (String) -> Unit,
) {
    Text("BOOK NO #} ", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Text("Customer Details", style = MaterialTheme.typography.titleMedium)

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
        CommonTextField(
            modifier = Modifier.weight(1f),
            label = "Age",
            value = age,
            onValueChange = { onAgeChanged(it) },
        )
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderDetails(
    orderDate: String,
    deliveryDate: String,
    orderType: String,

    onOrderDateChanged: (String) -> Unit,
    onDeliveryDateChanged: (String) -> Unit,
    onOrderTypeChanged: (String) -> Unit,
) {
    Text("Order Details", style = MaterialTheme.typography.titleMedium)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DatePickerField(
            modifier = Modifier.weight(1f),
            label = "Order Date",
            selectedDate = orderDate,
            onDateSelected = { onOrderDateChanged(it) }
        )

        DatePickerField(
            modifier = Modifier.weight(1f),
            label = "Delivery Date",
            selectedDate = deliveryDate,
            onDateSelected = { onDeliveryDateChanged(it) },
        )
    }

    OrderTypeDropdown(
        selectedOrderType = orderType,
        onOrderTypeSelected = { onOrderTypeChanged(it) },
    )

}

@Composable
fun ShirtMeasurement(
    shirtQty: String,
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

    onShirtQtyChanged: (String) -> Unit,
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
) {
    val fields = listOf(
        MeasurementField("Quantity", shirtQty, onShirtQtyChanged),
        MeasurementField("Amount", shirtAmt, onShirtAmtChanged),
        MeasurementField("Back", shirtBack, onShirtBackChanged),
        MeasurementField("Shoulder", shirtShoulder, onShirtShoulderChanged),
        MeasurementField("Collar", shirtCollar, onShirtCollarChanged),
        MeasurementField("Length", shirtLength, onShirtLengthChanged),
        MeasurementField("Chest", shirtChest, onShirtChestChanged),
        MeasurementField("Front1", shirtFront1, onShirtFront1Changed),
        MeasurementField("Front2", shirtFront2, onShirtFront2Changed),
        MeasurementField("Front3", shirtFront3, onShirtFront3Changed),
        MeasurementField("Sleeve", shirtSleeve, onShirtSleeveChanged),
        MeasurementField("Bicep", shirtBicep, onShirtBicepChanged),
        MeasurementField("Cuff", shirtCuff, onShirtCuffChanged),
    )

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

        Text("Shirt Measurement", style = MaterialTheme.typography.titleMedium)

        fields.chunked(3).forEach { rowFields ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
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

            /* FlowRow(
                 modifier = Modifier.padding(8.dp),
                 maxItemsInEachRow = 3,
                 horizontalArrangement = Arrangement.spacedBy(8.dp),
                 verticalArrangement = Arrangement.spacedBy(10.dp)
             ) {
                 fields.forEach { field ->
                     CommonTextField(
                         modifier = Modifier,
                         label = field.label,
                         value = field.value,
                         onValueChange = field.onValueChange
                     )
                 }
             }
             LazyVerticalGrid(
                 columns = GridCells.Fixed(3),
                 modifier = Modifier
                     .fillMaxWidth()
                     .heightIn(min = 300.dp),
                 contentPadding = PaddingValues(10.dp),
                 horizontalArrangement = Arrangement.spacedBy(8.dp),
                 verticalArrangement = Arrangement.spacedBy(10.dp)
             ) {
                 items(fields) { field ->
                     CommonTextField(
                         label = field.label,
                         value = field.value,
                         onValueChange = field.onValueChange
                     )
                     CommonTextField(
                         modifier = Modifier.fillMaxWidth(),
                         label = "Amount",
                         value = shirtAmt,
                         onValueChange = { onShirtAmtChanged(it) }
                     )
                     CommonTextField (
                         modifier = Modifier.fillMaxWidth(),
                         label = "Chest",
                         value = shirtChest,
                         onValueChange = { onShirtChestChanged(it) }
                     )
                     CommonTextField (
                         modifier = Modifier.fillMaxWidth(),
                         label = "Length",
                         value = shirtLength,
                         onValueChange = { onShirtLengthChanged(it) }
                     )
                     CommonTextField (
                         modifier = Modifier.fillMaxWidth(),
                         label = "Shoulder",
                         value = shirtShoulder,
                         onValueChange = { onShirtShoulderChanged(it) }
                     )
                     CommonTextField (
                         modifier = Modifier.fillMaxWidth(),
                         label = "Sleeve",
                         value = shirtSleeve,
                         onValueChange = { onShirtSleeveChanged(it) }
                     )
                     CommonTextField (
                         modifier = Modifier.fillMaxWidth(),
                         label = "Cuff",
                         value = shirtCuff,
                         onValueChange = { onShirtCuffChanged(it) }
                     )
                     CommonTextField (
                         modifier = Modifier.fillMaxWidth(),
                         label = "Collar",
                         value = shirtCollar,
                         onValueChange = { onShirtCollarChanged(it) }
                     )
                     CommonTextField (
                         modifier = Modifier.fillMaxWidth(),
                         label = "Back",
                         value = shirtBack,
                         onValueChange = { onShirtBackChanged(it) }
                     )
                     CommonTextField (
                         modifier = Modifier.fillMaxWidth(),
                         label = "Front 1",
                         value = shirtFront1,
                         onValueChange = { onShirtFront1Changed(it) }
                     )
                     CommonTextField (
                         modifier = Modifier.fillMaxWidth(),
                         label = "Front 2",
                         value = shirtFront2,
                         onValueChange = { onShirtFront2Changed(it) }
                     )
                     CommonTextField (
                         modifier = Modifier.fillMaxWidth(),
                         label = "Front 3",
                         value = shirtFront3,
                         onValueChange = { onShirtFront3Changed(it) }
                     )
                 }*/
        }
    }
}

@Composable
fun PantMeasurement(
    pantQty: String,
    pantAmt: String,
    pantOutsideLength: String,
    pantInsideLength: String,
    pantRise: String,
    pantWaist: String,
    pantSeat: String,
    pantThigh: String,
    pantKnee: String,
    pantBottom: String,

    onPantQtyChanged: (String) -> Unit,
    onPantAmtChanged: (String) -> Unit,
    onPantOutsideLengthChanged: (String) -> Unit,
    onPantInsideLengthChanged: (String) -> Unit,
    onPantRiseChanged: (String) -> Unit,
    onPantWaistChanged: (String) -> Unit,
    onPantSeatChanged: (String) -> Unit,
    onPantThighChanged: (String) -> Unit,
    onPantKneeChanged: (String) -> Unit,
    onPantBottomChanged: (String) -> Unit,
) {
    val fields = listOf(
        MeasurementField("Quantity", pantQty, onPantQtyChanged),
        MeasurementField("Amount", pantAmt, onPantAmtChanged),
        MeasurementField("Outside Len", pantOutsideLength, onPantOutsideLengthChanged),
        MeasurementField("Inside Length", pantInsideLength, onPantInsideLengthChanged),
        MeasurementField("Rise", pantRise, onPantRiseChanged),
        MeasurementField("Waist", pantWaist, onPantWaistChanged),
        MeasurementField("Seat", pantSeat, onPantSeatChanged),
        MeasurementField("Thigh", pantThigh, onPantThighChanged),
        MeasurementField("Knee", pantKnee, onPantKneeChanged),
        MeasurementField("Bottom", pantBottom, onPantBottomChanged),
    )
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
        Text("Pant Measurement", style = MaterialTheme.typography.titleMedium)

        fields.chunked(3).forEach { rowFields ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
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

 /*      CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Pant Quantity",
            value = pantQty,
            onValueChange = { onPantQtyChanged(it) }
        )
        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Pant Amount",
            value = pantAmt,
            onValueChange = { onPantAmtChanged(it) }
        )
        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Pant Outside Length",
            value = pantOutsideLength,
            onValueChange = { onPantOutsideLengthChanged(it) }
        )
        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Pant Inside Length",
            value = pantInsideLength,
            onValueChange = { onPantInsideLengthChanged(it) }
        )
        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Pant Rise",
            value = pantRise,
            onValueChange = { onPantRiseChanged(it) }
        )
        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Pant Waist",
            value = pantWaist,
            onValueChange = { onPantWaistChanged(it) }
        )
        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Pant Seat",
            value = pantSeat,
            onValueChange = { onPantSeatChanged(it) }
        )
        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Pant Thigh",
            value = pantThigh,
            onValueChange = { onPantThighChanged(it) }
        )
        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Pant Knee",
            value = pantKnee,
            onValueChange = { onPantKneeChanged(it) }
        )
        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Pant Bottom",
            value = pantBottom,
            onValueChange = { onPantBottomChanged(it) }
        )*/
    }
}

@Composable
fun Payment(
    discount: String,
    advance: String,
    note: String,

    onDiscountChanged: (String) -> Unit,
    onAdvanceChanged: (String) -> Unit,
    onNoteChanged: (String) -> Unit,
) {
    val uiColor = if (isSystemInDarkTheme()) SoftGolden else DarkBrown
    val fields = listOf(
        MeasurementField("Discount", discount, onDiscountChanged),
        MeasurementField("Advance", advance, onAdvanceChanged),
    )
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
        Text("Payment", style = MaterialTheme.typography.titleMedium)

        fields.chunked(2).forEach { rowFields ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowFields.forEach { field ->
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        CommonTextField(
                            modifier = Modifier,
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

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Note", style = MaterialTheme.typography.labelMedium, color = uiColor) },
            value = note,
            onValueChange = { onNoteChanged(it) },
            colors = TextFieldDefaults.colors(
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
                focusedPlaceholderColor = MaterialTheme.colorScheme.focusedTextFieldText,
                unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
                focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
            ),
        )

       /* CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Advance",
            value = advance,
            onValueChange = { onAdvanceChanged(it) }
        )
        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "discount",
            value = discount,
            onValueChange = { onDiscountChanged(it) }
        )
        CommonTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Note",
            value = note,
            onValueChange = { onNoteChanged(it) }
        )*/
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerField(
    label: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val date = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                                .toString()
                            onDateSelected(date)
                        }
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
    Box(modifier = modifier) {
        OutlinedTextField(
            label = { Text(label) },
            value = selectedDate,
            onValueChange = { onDateSelected(it) },
            readOnly = true,
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(onClick = { showDialog = true })
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderDropdown(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
    modifier: Modifier
) {
    val genders = listOf("Male", "Female", "Other")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedGender,
            onValueChange = { onGenderSelected(it) },
            readOnly = true,
            label = { Text("Gender") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            genders.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(text = gender) },
                    onClick = {
                        onGenderSelected(gender)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTypeDropdown(
    selectedOrderType: String,
    onOrderTypeSelected: (String) -> Unit
) {
    val orderTypes = listOf("Shirt", "Pant", "Both")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOrderType,
            onValueChange = { onOrderTypeSelected(it) },
            readOnly = true,
            label = { Text("Order Type") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            orderTypes.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        onOrderTypeSelected(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

