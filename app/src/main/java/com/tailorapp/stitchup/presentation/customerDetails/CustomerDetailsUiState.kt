package com.tailorapp.stitchup.presentation.customerDetails

import com.tailorapp.stitchup.domain.model.customer.getCustomer.GetCustomerDetailsResponse
import com.tailorapp.stitchup.domain.model.customer.updateCustomer.UpdateCustomerResponse
import com.tailorapp.stitchup.domain.model.customer.updateShirt.UpdateShirtResponse

data class CustomerDetailsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null,
    val customerDetails: GetCustomerDetailsResponse? = null,
    val updateCustomerDetails: UpdateCustomerResponse? = null,
    val updateShirtDetails: UpdateShirtResponse? = null,
    val isUpdateSuccess: Boolean = false,

    val name: String = "",
    val age: String = "",
    val gender: String = "",
    val mobile: String = "",
    val address: String = "",

    val shirtChest: String = "",
    val shirtLength: String = "",
    val shirtShoulder: String = "",
    val shirtSleeve: String = "",
    val shirtBicep: String = "",
    val shirtCuff: String = "",
    val shirtCollar: String = "",
    val shirtBack: String = "",
    val shirtFront1: String = "",
    val shirtFront2: String = "",
    val shirtFront3: String = "",

    val pantOutsideLength: String = "",
    val pantInsideLength: String = "",
    val pantRise: String = "",
    val pantWaist: String = "",
    val pantSeat: String = "",
    val pantThigh: String = "",
    val pantKnee: String = "",
    val pantBottom: String = "",
)
