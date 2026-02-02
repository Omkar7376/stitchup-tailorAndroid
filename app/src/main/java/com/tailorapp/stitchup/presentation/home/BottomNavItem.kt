package com.tailorapp.stitchup.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Orders : BottomNavItem("orders", "Orders", Icons.Default.List)
    object Customer : BottomNavItem("customers", "Customers", Icons.Default.Person)
    object Invoices : BottomNavItem("invoices", "Invoices", Icons.Default.Description)
}