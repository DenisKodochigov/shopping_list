package com.example.basket.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.basket.navigation.AppNavHost
import com.example.basket.navigation.Baskets
import com.example.basket.navigation.appTabRowScreens
import com.example.basket.navigation.navigateToScreen
import com.example.basket.ui.components.BottomBarApp
import com.example.basket.ui.components.FloatingActionButtonApp
import com.example.basket.ui.theme.AppTheme

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("RememberReturnType", "UnrememberedMutableState", "SuspiciousIndentation")
@Composable
fun MainApp() {
    AppTheme {
        val showBottomSheet = remember { mutableStateOf(false) }
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val animCurrentScreen = appTabRowScreens.find {
            it.route == currentDestination?.route
        } ?: Baskets

        Scaffold(
            modifier = Modifier
                .padding(14.dp)
                .semantics { testTagsAsResourceId = true }
                .background(color = MaterialTheme.colorScheme.background),
            bottomBar = {
                BottomBarApp(
                    currentScreen = animCurrentScreen, //currentScreen,
                    onTabSelection = { newScreen ->
                        navController.navigateToScreen(newScreen.route)
                    })
            },
            floatingActionButton = {
                FloatingActionButtonApp(
                    offset = 68.dp, top = 0.dp, icon = Icons.Filled.Add,
                    onClick = { showBottomSheet.value = true } )
            },
            floatingActionButtonPosition = FabPosition.Center,
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                showBottomSheet = showBottomSheet)
        }
    }
}
@Preview
@Composable fun MainAppPreview(){
    MainApp()
}