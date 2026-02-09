package com.tailorapp.stitchup.presentation.addCustomer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.OrderDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.PantMeasurementDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.ShirtMeasurementDto
import com.tailorapp.stitchup.domain.usecase.customer.AddCustomerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCustomerViewModel @Inject constructor(private val addCustomerUseCase: AddCustomerUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(AddCustomerUiState())
    val uiState: StateFlow<AddCustomerUiState> = _uiState.asStateFlow()

    fun addNewCustomer(request: AddCustomerRequestDto) {
        viewModelScope.launch {
            addCustomerUseCase.addCustomer(request).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = AddCustomerUiState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = AddCustomerUiState(isLoading = false, message = result.data?.message)
                        sendMessage("Customer Created Successfully....")
                        Log.d("###", "addNewCustomer: ${result.data}")
                    }
                    is Resource.Error -> {
                        _uiState.value = AddCustomerUiState(error = result.message)
                    }
                }
            }
        }
    }

    fun onCreateCustomerClicked(){
        val state = _uiState.value
        when {
            state.name.isBlank() -> return sendMessage("Name is Required")
            state.gender.isBlank() -> return sendMessage("Gender is Required")
            state.mobile.isBlank() -> return sendMessage("Mobile is Required")
            state.address.isBlank() -> return sendMessage("Address is Required")

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
                state.shirtChest.isBlank() -> return sendMessage("Shirt Chest is Required")
                state.shirtLength.isBlank() -> return sendMessage("Shirt Length is Required")
                state.shirtShoulder.isBlank() -> return sendMessage("Shirt Shoulder is Required")
                state.shirtSleeve.isBlank() -> return sendMessage("Shirt Sleeve is Required")
                state.shirtBicep.isBlank() -> return sendMessage("Shirt Bicep is Required")
                state.shirtCuff.isBlank() -> return sendMessage("Shirt Cuff is Required")
                state.shirtCollar.isBlank() -> return sendMessage("Shirt Collar is Required")
                state.shirtBack.isBlank() -> return sendMessage("Shirt Back is Required")
                state.shirtFront1.isBlank() -> return sendMessage("Shirt Front 1 is Required")
                state.shirtFront2.isBlank() -> return sendMessage("Shirt Front 2 is Required")
                state.shirtFront3.isBlank() -> return sendMessage("Shirt Front 3 is Required")
            }
        }

        if (state.orderType == "Pant" || state.orderType == "Both") {
            when {
                state.pantQty.isBlank() -> return sendMessage("Pant Quantity is Required")
                state.pantAmt.isBlank() -> return sendMessage("Pant Amount is Required")
                state.pantOutsideLength.isBlank() -> return sendMessage("Pant Outside Length is Required")
                state.pantInsideLength.isBlank() -> return sendMessage("Pant Inside Length is Required")
                state.pantRise.isBlank() -> return sendMessage("Pant Rise is Required")
                state.pantWaist.isBlank() -> return sendMessage("Pant Waist is Required")
                state.pantSeat.isBlank() -> return sendMessage("Pant Seat is Required")
                state.pantThigh.isBlank() -> return sendMessage("Pant Thigh is Required")
                state.pantKnee.isBlank() -> return sendMessage("Pant Knee is Required")
                state.pantBottom.isBlank() -> return sendMessage("Pant Bottom is Required")
            }
        }

        val request = AddCustomerRequestDto(
            name = state.name,
            gender = state.gender,
            mob_num = state.mobile,
            address = state.address,
            order = OrderDto(
                orderDate = state.orderDate,
                deliveryDate = state.deliveryDate,
                orderType = state.orderType,
                shirtQnt = state.shirtQty.toFloatOrNull() ?: 0,
                shirtAmount = state.shirtAmt.toFloatOrNull() ?: 0,
                pantQnt = state.pantQty.toFloatOrNull() ?: 0,
                pantAmount = state.pantAmt.toFloatOrNull() ?: 0,
                advanceAmount = state.advance.toFloatOrNull() ?: 0,
                discount = state.discount.toFloatOrNull() ?: 0,
                status = "isPending",
                note = state.note,
            ),
            shirtMeasurement =
                if (state.orderType == "Shirt" || state.orderType == "Both") {
                    ShirtMeasurementDto(
                        chest = state.shirtChest.toFloatOrNull() ?: 0,
                        collar = state.shirtCollar.toFloatOrNull() ?: 0,
                        back = state.shirtBack.toFloatOrNull() ?: 0,
                        cuff = state.shirtCuff.toFloatOrNull() ?: 0,
                        front1 = state.shirtFront1.toFloatOrNull() ?: 0,
                        front2 = state.shirtFront2.toFloatOrNull() ?: 0,
                        front3 = state.shirtFront3.toFloatOrNull() ?: 0,
                        length = state.shirtLength.toFloatOrNull() ?: 0,
                        shoulder = state.shirtShoulder.toFloatOrNull() ?: 0,
                        sleeve = state.shirtSleeve.toFloatOrNull() ?: 0,
                    )
                } else null,

            pantMeasurement =
                if (state.orderType == "Pant" || state.orderType == "Both") {
                    PantMeasurementDto(
                        bottom = state.pantBottom.toFloatOrNull() ?: 0,
                        knee = state.pantKnee.toFloatOrNull() ?: 0,
                        outsideLength = state.pantOutsideLength.toFloatOrNull() ?: 0,
                        insideLength = state.pantInsideLength.toFloatOrNull() ?: 0,
                        seat = state.pantSeat.toFloatOrNull() ?: 0,
                        thigh = state.pantThigh.toFloatOrNull() ?: 0,
                        waist = state.pantWaist.toFloatOrNull() ?: 0,
                        rise = state.pantRise.toFloatOrNull() ?: 0,
                    )
                } else null
        )

        addNewCustomer(request)
        Log.d("###", "onCreateCustomerClicked: ${addNewCustomer(request)}")
        Log.d("###", "Shirt: ${request.shirtMeasurement}")
        Log.d("###", "Pant: ${request.pantMeasurement}")
    }

    fun onNameChanged(value: String) {
        _uiState.update { it.copy(name = value) }
    }

    fun onGenderChanged(value: String) {
        _uiState.update { it.copy(gender = value) }
    }

    fun onMobileChanged(value: String) {
        _uiState.update { it.copy(mobile = value) }
    }

    fun onAddressChanged(value: String) {
        _uiState.update { it.copy(address = value) }
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

    fun onShirtQntChanged(value: String){
        _uiState.update { it.copy(shirtQty = value ) }
    }

    fun onShirtAmtChanged(value: String){
        _uiState.update { it.copy(shirtAmt = value) }
    }

    fun onShirtChestChanged(value: String){
        _uiState.update { it.copy(shirtChest = value) }
    }

    fun onShirtLengthChanged(value: String){
        _uiState.update { it.copy(shirtLength = value ) }
    }

    fun onShirtShoulderChanged(value: String){
        _uiState.update { it.copy(shirtShoulder = value ) }
    }

    fun onShirtSleeveChanged(value: String){
        _uiState.update { it.copy(shirtSleeve = value ) }
    }

    fun onShirtBicepChanged(value: String){
        _uiState.update { it.copy(shirtBicep = value ) }
    }

    fun onShirtCuffChanged(value: String){
        _uiState.update { it.copy(shirtCuff = value ) }
    }

    fun onShirtCollarChanged(value: String){
        _uiState.update { it.copy(shirtCollar = value ) }
    }

    fun onShirtBackChanged(value: String){
        _uiState.update { it.copy(shirtBack = value ) }
    }

    fun onShirtFront1Changed(value: String){
        _uiState.update { it.copy(shirtFront1 = value ) }
    }

    fun onShirtFront2Changed(value: String){
        _uiState.update { it.copy(shirtFront2 = value ) }
    }

    fun onShirtFront3Changed(value: String){
        _uiState.update { it.copy(shirtFront3 = value ) }
    }

    fun onPantQntChanged(value: String){
        _uiState.update { it.copy(pantQty = value ) }
    }

    fun onPantAmtChanged(value: String){
        _uiState.update { it.copy(pantAmt = value ) }
    }

    fun onPantOutsideChanged(value: String){
        _uiState.update { it.copy(pantOutsideLength = value ) }
    }

    fun onPantInsideChanged(value: String){
        _uiState.update { it.copy(pantInsideLength = value ) }
    }

    fun onPantRiseChanged(value: String){
        _uiState.update { it.copy(pantRise = value ) }
    }

    fun onPantWaistChanged(value: String){
        _uiState.update { it.copy(pantWaist = value ) }
    }

    fun onPantSeatChanged(value: String){
        _uiState.update { it.copy(pantSeat = value ) }
    }

    fun onPantThighChanged(value: String){
        _uiState.update { it.copy(pantThigh = value ) }
    }

    fun onPantKneeChanged(value: String){
        _uiState.update { it.copy(pantKnee = value ) }
    }

    fun onPantBottomChanged(value: String){
        _uiState.update { it.copy(pantBottom = value ) }
    }

    fun sendMessage(message: String) {
        _uiState.update { it.copy(message = message) }
    }

    fun clearMessage(){
        _uiState.value = _uiState.value.copy(message = null)
    }
}