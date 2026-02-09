package com.tailorapp.stitchup.presentation.customerDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.customerDto.updateCustomerDto.UpdateCustomerRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.updatePantDto.UpdatePantRequestDto
import com.tailorapp.stitchup.domain.model.customer.getCustomer.CustomerDetails
import com.tailorapp.stitchup.domain.model.customer.getCustomer.PantMeasurement
import com.tailorapp.stitchup.domain.model.customer.getCustomer.ShirtMeasurement
import com.tailorapp.stitchup.domain.model.customer.updateShirt.UpdateShirtRequest
import com.tailorapp.stitchup.domain.usecase.customer.GetCustomerDetailsUseCase
import com.tailorapp.stitchup.domain.usecase.customer.UpdateCustomerUseCase
import com.tailorapp.stitchup.domain.usecase.customer.UpdatePantUseCase
import com.tailorapp.stitchup.domain.usecase.customer.UpdateShirtUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerDetailsViewModel @Inject constructor(
    private val getCustomerDetailsUseCase: GetCustomerDetailsUseCase,
    private val updateCustomerUseCase: UpdateCustomerUseCase,
    private val updateShirtUseCase: UpdateShirtUseCase,
    private val updatePantUseCase: UpdatePantUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CustomerDetailsUiState())
    val uiState: StateFlow<CustomerDetailsUiState> = _uiState.asStateFlow()

    fun getCustomerDetails(id: Int) {
        viewModelScope.launch {
            getCustomerDetailsUseCase.getCustomerDetails(id).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                        Log.d("###", "getCustomerDetails: Loading")
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            customerDetails = result.data,
                            error = null
                        )
                        Log.d("###", "getCustomerDetails: ${result.data}")
                        Log.d("###", "getCustomerDetails: ${result.data?.customer}")
                        Log.d("###", "getCustomerDetails: ${result.data?.shirtMeasurements}")
                        Log.d("###", "getCustomerDetails: ${result.data?.pantMeasurements}")
                    }
                    is Resource.Error -> {
                        _uiState.value = uiState.value.copy(error = result.message)
                        Log.d("###", "getCustomerDetails: ${result.message}")
                    }
                }
            }
        }
    }

    fun updateCustomerDetails(id: Int, updateCustomerRequest: UpdateCustomerRequestDto) {
        viewModelScope.launch {
            updateCustomerUseCase.updateCustomer(id, updateCustomerRequest).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                        Log.d("###", "updateCustomerDetails: Loading")
                    }

                    is Resource.Success -> {
                        val customer = result.data?.customer?.firstOrNull()
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = null,
                            updateCustomerDetails = result.data,
                            isUpdateSuccess = true
                        )
                        getCustomerDetails(id)
                        Log.d("###", "updateCustomerDetails: ${result.data}")
                    }

                    is Resource.Error -> {
                        _uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = result.message,
                        )
                        Log.d("###", "updateCustomerDetails: ${result.message}")
                    }
                }
            }
        }
    }

    fun updateShirtDetails(id: Int, updateShirtRequest: UpdateShirtRequest) {
        viewModelScope.launch {
            updateShirtUseCase.updateShirt(id, updateShirtRequest).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                        Log.d("###", "updateShirtDetails: Loading")
                    }

                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = null,
                            updateShirtDetails = result.data,
                            isUpdateSuccess = true
                        )
                        getCustomerDetails(id)
                        Log.d("###", "updateShirtDetails: ${result.data}")
                    }

                    is Resource.Error -> {
                        _uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = result.message,
                        )
                        Log.d("###", "updateShirtDetails: ${result.message}")
                    }
                }
            }
        }
    }

    fun updatePantDetails(id: Int, updatePantRequest: UpdatePantRequestDto) {
        viewModelScope.launch {
            updatePantUseCase.updatePantMeasurement(id, updatePantRequest).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                        Log.d("###", "updatePantDetails: Loading")
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = null,
                            updatePantDetails = result.data,
                            isUpdateSuccess = true
                        )
                        getCustomerDetails(id)
                    }
                    is Resource.Error -> {
                        _uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = result.message,
                        )
                        Log.d("###", "updatePantDetails: ${result.message}")
                    }
                }
            }
        }
    }

    fun setCustomerForEditing(oldCustomer: CustomerDetails?) {
        val customer = _uiState.value.customerDetails?.customer?.firstOrNull()

        _uiState.value = _uiState.value.copy(
            name = customer?.NAME ?: "",
            gender = customer?.GENDER ?: "",
            mobile = customer?.MOB_NO ?: "",
            address = customer?.ADDRESS ?: "",
        )
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

    fun setShirtForEditing(oldShirt: ShirtMeasurement) {
        val shirt = _uiState.value.customerDetails?.shirtMeasurements?.firstOrNull()
        _uiState.value = _uiState.value.copy(
            shirtChest = shirt?.CHEST?.toString() ?: "",
            shirtLength = shirt?.LENGTH?.toString() ?: "",
            shirtShoulder = shirt?.SHOULDER?.toString() ?: "",
            shirtSleeve = shirt?.SLEEVE?.toString() ?: "",
            shirtBicep = shirt?.BICEP?.toString() ?: "",
            shirtCuff = shirt?.CUFF?.toString() ?: "",
            shirtCollar = shirt?.COLLAR?.toString() ?: "",
            shirtBack = shirt?.BACK?.toString() ?: "",
            shirtFront1 = shirt?.FRONT1?.toString() ?: "",
            shirtFront2 = shirt?.FRONT2?.toString() ?: "",
            shirtFront3 = shirt?.FRONT3?.toString() ?: "",
        )
    }

    fun onShirtChestChanged(value: String) {
        _uiState.update { it.copy(shirtChest = value) }
    }

    fun onShirtLengthChanged(value: String) {
        _uiState.update { it.copy(shirtLength = value) }
    }

    fun onShirtShoulderChanged(value: String) {
        _uiState.update { it.copy(shirtShoulder = value) }
    }

    fun onShirtSleeveChanged(value: String) {
        _uiState.update { it.copy(shirtSleeve = value) }
    }

    fun onShirtBicepChanged(value: String) {
        _uiState.update { it.copy(shirtBicep = value) }
    }

    fun onShirtCuffChanged(value: String) {
        _uiState.update { it.copy(shirtCuff = value) }
    }

    fun onShirtCollarChanged(value: String) {
        _uiState.update { it.copy(shirtCollar = value) }
    }

    fun onShirtBackChanged(value: String) {
        _uiState.update { it.copy(shirtBack = value) }
    }

    fun onShirtFront1Changed(value: String) {
        _uiState.update { it.copy(shirtFront1 = value) }
    }

    fun onShirtFront2Changed(value: String) {
        _uiState.update { it.copy(shirtFront2 = value) }
    }

    fun onShirtFront3Changed(value: String) {
        _uiState.update { it.copy(shirtFront3 = value) }
    }

    fun setPantForEditing(oldPant: PantMeasurement) {
        val pant = _uiState.value.customerDetails?.pantMeasurements?.firstOrNull()
        _uiState.value = _uiState.value.copy(
            pantOutsideLength = pant?.OUTSIDE_LENGTH?.toString() ?: "",
            pantInsideLength = pant?.INSIDE_LENGTH?.toString() ?: "",
            pantWaist = pant?.WAIST?.toString() ?: "",
            pantSeat = pant?.SEAT?.toString() ?: "",
            pantRise = pant?.RISE?.toString() ?: "",
            pantKnee = pant?.KNEE?.toString() ?: "",
            pantThigh = pant?.THIGH?.toString() ?: "",
            pantBottom = pant?.BOTTOM?.toString() ?: "",
        )
    }

    fun onPantOutsideChanged(value: String) {
        _uiState.update { it.copy(pantOutsideLength = value) }
    }

    fun onPantInsideChanged(value: String) {
        _uiState.update { it.copy(pantInsideLength = value) }
    }

    fun onPantRiseChanged(value: String) {
        _uiState.update { it.copy(pantRise = value) }
    }

    fun onPantWaistChanged(value: String) {
        _uiState.update { it.copy(pantWaist = value) }
    }

    fun onPantSeatChanged(value: String) {
        _uiState.update { it.copy(pantSeat = value) }
    }

    fun onPantThighChanged(value: String) {
        _uiState.update { it.copy(pantThigh = value) }
    }

    fun onPantKneeChanged(value: String) {
        _uiState.update { it.copy(pantKnee = value) }
    }

    fun onPantBottomChanged(value: String) {
        _uiState.update { it.copy(pantBottom = value) }
    }

    fun sendMessage(message: String) {
        _uiState.update { it.copy(message = message) }
    }
}