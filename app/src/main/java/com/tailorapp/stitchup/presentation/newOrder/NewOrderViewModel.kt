package com.tailorapp.stitchup.presentation.newOrder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.orderDto.createOrder.CreateOrderReqDto
import com.tailorapp.stitchup.data.remote.dto.orderDto.createOrder.OrderReqData
import com.tailorapp.stitchup.domain.usecase.order.CreateOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewOrderViewModel @Inject constructor(private val orderUseCase: CreateOrderUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(NewOrderUiState())
    val uiState: StateFlow<NewOrderUiState> = _uiState.asStateFlow()

    fun createOrder(
        id: Int,
        request: CreateOrderReqDto
    ) {
        viewModelScope.launch {
            orderUseCase.createOrder(id, request).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                        Log.d("###", "onCreateOrderClicked: Loading")
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                message = result.data?.message ?: "Order Created Successfully",

                                orderDate = "",
                                deliveryDate = "",
                                orderType = "Select",
                                shirtQty = "",
                                shirtAmt = "",
                                pantQty = "",
                                pantAmt = "",
                                discount = "",
                                advance = "",
                                note = ""
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.value = NewOrderUiState(error = result.message)
                        Log.d("###", "onCreateOrderClicked: ${result.message}")
                    }
                }
            }
        }
    }

    fun onCreateOrderClicked() {
        Log.d("###", "Button Clicked")
        val state = _uiState.value
        when {
            state.customerName.isBlank() -> return sendMessage("Customer Name is Required")
            state.customerMobile.isBlank() -> return sendMessage("Customer Mobile is Required")
            state.orderDate.isBlank() -> return sendMessage("Order Date is Required")
            state.deliveryDate.isBlank() -> return sendMessage("Delivery Date is Required")
            state.orderType.isBlank() -> return sendMessage("Order Type is Required")
            state.discount.isBlank() -> return sendMessage("Discount is Required")
            state.advance.isBlank() -> return sendMessage("Advance is Required")
            state.note.isBlank() -> return sendMessage("Note is Required")
        }

        if (state.orderType == "Shirt" || state.orderType == "Both") {
            when {
                state.shirtQty.isBlank() -> return sendMessage("Shirt Quantity is Required")
                state.shirtAmt.isBlank() -> return sendMessage("Shirt Amount is Required")
            }
        }

        if (state.orderType == "Pant" || state.orderType == "Both") {
            when {
                state.pantQty.isBlank() -> return sendMessage("Pant Quantity is Required")
                state.pantAmt.isBlank() -> return sendMessage("Pant Amount is Required")
            }
        }

        val request = CreateOrderReqDto(
            orderData = OrderReqData(
                advanceAmount = state.advance.toFloatOrNull() ?: 0,
                deliveryDate = state.deliveryDate,
                discount = state.discount.toFloatOrNull() ?: 0,
                note = state.note,
                orderDate = state.orderDate,
                orderType = state.orderType,
                pantAmount = if (state.orderType == "Pant" || state.orderType == "Both") {
                    state.pantAmt.toFloatOrNull() ?: 0
                } else {
                    0
                },
                pantQnt = if (state.orderType == "Pant" || state.orderType == "Both") {
                    state.pantQty.toFloatOrNull() ?: 0
                } else {
                    0
                },
                shirtAmount = if (state.orderType == "Shirt" || state.orderType == "Both") {
                    state.shirtAmt.toFloatOrNull() ?: 0
                } else {
                    0
                },
                shirtQnt = if (state.orderType == "Shirt" || state.orderType == "Both") {
                    state.shirtQty.toFloatOrNull() ?: 0
                } else {
                    0
                },
                status = "isPending"
            )
        )

        Log.d("###", "Calling API with id = ${state.customerId}")
        if (state.customerId == 0) {
            Log.d("###", "CustomerId is 0")
        } else {
            createOrder(
                id = state.customerId,
                request = request
            )
        }
    }

    fun onOrderDateChanged(value: String) {
        _uiState.update { it.copy(orderDate = value) }
    }

    fun onOrderDeliveryDateChanged(value: String) {
        _uiState.update { it.copy(deliveryDate = value) }
    }

    fun onOrderTypeChanged(value: String) {
        _uiState.update { it.copy(orderType = value) }
    }

    fun onDiscountChanged(value: String) {
        _uiState.update { it.copy(discount = value) }
    }

    fun onAdvanceChanged(value: String) {
        _uiState.update { it.copy(advance = value) }
    }

    fun onNoteChanged(value: String) {
        _uiState.update { it.copy(note = value) }
    }

    fun onShirtQntChanged(value: String) {
        _uiState.update { it.copy(shirtQty = value) }
    }

    fun onShirtAmtChanged(value: String) {
        _uiState.update { it.copy(shirtAmt = value) }
    }

    fun onPantQntChanged(value: String) {
        _uiState.update { it.copy(pantQty = value) }
    }

    fun onPantAmtChanged(value: String) {
        _uiState.update { it.copy(pantAmt = value) }
    }

    fun setCustomerId(id: Int) {
        _uiState.update { it.copy(customerId = id) }
    }

    fun setCustomerDetailsFromProfile(
        name: String,
        mobile: String
    ) {
        _uiState.update {
            it.copy(
                customerName = name,
                customerMobile = mobile
            )
        }
    }

    fun sendMessage(message: String) {
        _uiState.update { it.copy(message = message) }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
}