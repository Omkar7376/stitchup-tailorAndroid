package com.tailorapp.stitchup.presentation.newOrder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tailorapp.stitchup.presentation.addCustomer.DatePickerField
import com.tailorapp.stitchup.presentation.addCustomer.OrderTypeDropdown
import com.tailorapp.stitchup.presentation.addCustomer.dataClasses.MeasurementField
import com.tailorapp.stitchup.presentation.common.ButtonCommon
import com.tailorapp.stitchup.presentation.common.CommonTextField
import com.tailorapp.stitchup.presentation.common.TopBar
import com.tailorapp.stitchup.presentation.order.OrdersViewModel
import com.tailorapp.stitchup.ui.theme.DarkBrown
import com.tailorapp.stitchup.ui.theme.SoftGolden

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewOrder(
    customerId: Int? = null,
    name: String? = null,
    mobile: String? = null,
    navController: NavController,
    viewModel: NewOrderViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.value.message) {
        uiState.value.message?.let { errorMessage ->
            snackBarHostState.showSnackbar(errorMessage)
            viewModel.clearMessage()
        }
    }

    LaunchedEffect(customerId) {
        customerId?.let {
            viewModel.setCustomerId(it)
            viewModel.setCustomerDetailsFromProfile(
                name = name ?: "",
                mobile = mobile ?: ""
            )
        }
    }

    val uiColor = if (isSystemInDarkTheme()) SoftGolden else DarkBrown
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopBar(
                text = "Orders",
                modifier = Modifier.shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                    clip = true
                ),
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
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    NewOrderDetails(
                        orderDate = uiState.value.orderDate,
                        deliveryDate = uiState.value.deliveryDate,
                        orderType = uiState.value.orderType,
                        shirtQty = uiState.value.shirtQty,
                        shirtAmt = uiState.value.shirtAmt,
                        pantQty = uiState.value.pantQty,
                        pantAmt = uiState.value.pantAmt,
                        discount = uiState.value.discount,
                        advance = uiState.value.advance,
                        note = uiState.value.note,

                        onOrderDateChanged = { viewModel.onOrderDateChanged(it) },
                        onDeliveryDateChanged = { viewModel.onOrderDeliveryDateChanged(it) },
                        onOrderTypeChanged = { viewModel.onOrderTypeChanged(it) },
                        onShirtQtyChanged = { viewModel.onShirtQntChanged(it) },
                        onShirtAmtChanged = { viewModel.onShirtAmtChanged(it) },
                        onPantQtyChanged = { viewModel.onPantQntChanged(it) },
                        onPantAmtChanged = { viewModel.onPantAmtChanged(it) },
                        onDiscountChanged = { viewModel.onDiscountChanged(it) },
                        onAdvanceChanged = { viewModel.onAdvanceChanged(it) },
                        onNoteChanged = { viewModel.onNoteChanged(it) }
                    )
                }

                item {
                    ButtonCommon(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Create Order",
                        enabled = true,
                        onClick = {
                            viewModel.onCreateOrderClicked()
                        }
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewOrderDetails(
    orderDate: String,
    deliveryDate: String,
    orderType: String,
    shirtQty: String,
    shirtAmt: String,
    pantQty: String,
    pantAmt: String,
    discount: String,
    advance: String,
    note: String,

    onOrderDateChanged: (String) -> Unit,
    onDeliveryDateChanged: (String) -> Unit,
    onOrderTypeChanged: (String) -> Unit,
    onShirtQtyChanged: (String) -> Unit,
    onShirtAmtChanged: (String) -> Unit,
    onPantQtyChanged: (String) -> Unit,
    onPantAmtChanged: (String) -> Unit,
    onDiscountChanged: (String) -> Unit,
    onAdvanceChanged: (String) -> Unit,
    onNoteChanged: (String) -> Unit,
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
        modifier = Modifier,
        selectedOrderType = orderType,
        onOrderTypeSelected = { onOrderTypeChanged(it) },
    )

    Spacer(modifier = Modifier.height(6.dp))

    if (orderType == "Select"){
        null
    } else {
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
            if (orderType == "Shirt" || orderType == "Both") {
                ShirtDetails(
                    shirtQty = shirtQty,
                    shirtAmt = shirtAmt,
                    onShirtQtyChanged = { onShirtQtyChanged(it) },
                    onShirtAmtChanged = { onShirtAmtChanged(it) }
                )
            } else null

            if (orderType == "Pant" || orderType == "Both") {
                PantDetails(
                    pantQty = pantQty,
                    pantAmt = pantAmt,
                    onPantQtyChanged = { onPantQtyChanged(it) },
                    onPantAmtChanged = { onPantAmtChanged(it) }
                )
            } else null
        }
    }

    Spacer(modifier = Modifier.height(6.dp))

    val fields = listOf(
        MeasurementField("Discount", discount, onDiscountChanged),
        MeasurementField("Advance", advance, onAdvanceChanged),
        MeasurementField("Note", note, onNoteChanged),
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
    }
}

@Composable
fun ShirtDetails(
    shirtQty: String,
    shirtAmt: String,

    onShirtQtyChanged: (String) -> Unit,
    onShirtAmtChanged: (String) -> Unit,
) {
    val fields = listOf(
        MeasurementField("Shirt Quantity", shirtQty, onShirtQtyChanged),
        MeasurementField("Shirt Amount", shirtAmt, onShirtAmtChanged),
    )

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

@Composable
fun PantDetails(
    pantQty: String,
    pantAmt: String,

    onPantQtyChanged: (String) -> Unit,
    onPantAmtChanged: (String) -> Unit,
) {
    val fields = listOf(
        MeasurementField("Pant Quantity", pantQty, onPantQtyChanged),
        MeasurementField("Pant Amount", pantAmt, onPantAmtChanged),
    )


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