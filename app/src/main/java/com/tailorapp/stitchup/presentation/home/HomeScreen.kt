package com.tailorapp.stitchup.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tailorapp.stitchup.R
import com.tailorapp.stitchup.presentation.common.CustomCard
import com.tailorapp.stitchup.presentation.common.TopBar
import com.tailorapp.stitchup.presentation.customer.CustomersScreen
import com.tailorapp.stitchup.presentation.invoice.InvoicesScreen
import com.tailorapp.stitchup.presentation.order.OrdersScreen
import com.tailorapp.stitchup.ui.theme.Brown
import com.tailorapp.stitchup.ui.theme.DarkBrown
import com.tailorapp.stitchup.ui.theme.Gray
import com.tailorapp.stitchup.ui.theme.SoftGolden
import com.tailorapp.stitchup.ui.theme.SoftMediumGolden
import com.tailorapp.stitchup.ui.theme.White
import com.tailorapp.stitchup.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navTo: (String) -> Unit = {}
) {

    val bottomNavController = rememberNavController()

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Orders,
        BottomNavItem.Customer,
        BottomNavItem.Invoices
    )

    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val topBarTitle = items.find { it.route == currentDestination?.route }?.title ?: BottomNavItem.Home.title

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                modifier = Modifier.shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                    clip = true
                ),
                text = topBarTitle,
                navigationIcon = {},
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.dimens.small2,
                        vertical = MaterialTheme.dimens.small2
                    )
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(20.dp),
                        clip = true
                    ),
                containerColor = if (isSystemInDarkTheme()) Gray else White,
                contentColor = if (isSystemInDarkTheme()) White else DarkBrown,
                windowInsets = WindowInsets(0.dp)
            ) {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(screen.icon, contentDescription = null)
                        },
                        label = {
                            Text(screen.title)
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route }  == true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = if (isSystemInDarkTheme()) SoftGolden else Brown,
                            selectedTextColor = if (isSystemInDarkTheme()) SoftGolden else Brown,
                            unselectedIconColor = if (isSystemInDarkTheme()) White else DarkBrown,
                            unselectedTextColor = if (isSystemInDarkTheme()) White else DarkBrown,
                            indicatorColor = Color.Transparent
                        ),
                        onClick = {
                            bottomNavController.navigate(screen.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }

        }
    ) {innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(BottomNavItem.Home.route){
                HomeTabContent(
                    navTo = navTo
                )
            }
            composable(BottomNavItem.Orders.route){
                OrdersScreen()
            }
            composable(BottomNavItem.Customer.route){
                CustomersScreen()
            }
            composable(BottomNavItem.Invoices.route){
                InvoicesScreen()
            }
        }
    }
}

@Composable
fun HomeTabContent(
    navTo: (String) -> Unit = {}
) {
    val cards = listOf(
        CardItems("Add Customers", R.drawable.addcustomer, "addCustomer"),
        CardItems("Customers List", R.drawable.customerlist, "customerList"),
        CardItems("Delivery Orders", R.drawable.deliveryorder, "deliveryOrder"),
        CardItems("Total Orders", R.drawable.totalorders, "totalOrders"),
        CardItems("Assign Order", R.drawable.asignorder, "assignOrder"),
        CardItems("Workers List", R.drawable.workerlist, "workerList"),
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(cards.size) { index ->
            val item = cards[index]
            val rowIndex = index / 2
            val columnIndex = index % 2
            val cardColor = if (isSystemInDarkTheme()) {
                if ((rowIndex + columnIndex) % 2 == 0) {
                    Gray.copy(alpha = 0.8f)
                } else {
                    Gray
                }
            } else {
                if ((rowIndex + columnIndex) % 2 == 0) {
                    SoftGolden
                } else {
                    SoftMediumGolden
                }
            }

            CustomCard(
                icon = item.icon,
                title = item.title,
                containerColor = cardColor,
                onClick = {
                    navTo(cards[index].route)
                }
            )
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun Logout(
    onLogoutClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.buttonHeight),
        onClick = { onLogoutClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) Gray else DarkBrown,
            contentColor = SoftGolden
        ),
        shape = RoundedCornerShape(4.dp)

    ) {
        Text(
            text = "Logout",
            style = typography.labelMedium.copy(fontWeight = FontWeight.Medium),
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun Prev() {
    HomeScreen(navTo = {})
}