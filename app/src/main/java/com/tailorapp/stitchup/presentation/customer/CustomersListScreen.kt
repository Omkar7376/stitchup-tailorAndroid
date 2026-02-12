package com.tailorapp.stitchup.presentation.customer

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TabRowDefaults.Indicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tailorapp.stitchup.presentation.addCustomer.AddCustomerViewModel
import com.tailorapp.stitchup.presentation.common.CommonText
import com.tailorapp.stitchup.presentation.common.SearchBar
import com.tailorapp.stitchup.presentation.common.TopBar
import com.tailorapp.stitchup.presentation.customer.componenets.CustomersItem
import com.tailorapp.stitchup.ui.theme.DarkBrown
import com.tailorapp.stitchup.ui.theme.SoftGolden
import com.tailorapp.stitchup.ui.theme.dimens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomersScreen(
    viewModel: CustomersViewModel = hiltViewModel(),
    navController: NavController,
    showTopBar: Boolean = true
) {
    var searchQuery by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isRefreshing = uiState.isLoading
    val pullRefreshState = rememberPullToRefreshState()

    LaunchedEffect(uiState.message) {
        uiState.message?.let { errorMessage ->
            snackbarHostState.showSnackbar(errorMessage)
            if (uiState.customers.isEmpty()) {
                scope.launch {
                    snackbarHostState.showSnackbar("Customers not found")
                }
            }
            viewModel.clearMessage()
        }
    }

    val uiColor = if (isSystemInDarkTheme()) SoftGolden else DarkBrown
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showTopBar) {
                TopBar(
                    modifier = Modifier.shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                        clip = true
                    ),
                    text = "Customers",
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { newQuery ->
                    searchQuery = newQuery
                    viewModel.search(newQuery)
                },
                placeholderText = "Search Customers"
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CommonText("Add Customer")
                IconButton(
                    onClick = {
                        navController.navigate("addCustomer")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = uiColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

            PullToRefreshBox(
                modifier = Modifier
                    .fillMaxSize(),
                state = pullRefreshState,
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.getCustomers() },
                contentAlignment = Alignment.TopCenter,
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    
                    items(uiState.customers) { customer ->
                        CustomersItem(
                            bookNumber = customer.BOOKNO,
                            name = customer.NAME,
                            mobile = customer.MOB_NO,
                            onClick = {
                                navController.navigate("customerDetails/${customer.ID}")
                            }
                        )
                    }
                }
            }
        }
    }
}

