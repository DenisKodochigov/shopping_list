package com.example.shopping_list.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

/*** Contract for information needed on every App navigation destination*/
interface ScreenDestination {
    val icon: ImageVector
    val route: String
}

/*** App app navigation destinations*/
object Baskets : ScreenDestination {
    override val icon = Icons.Filled.ShoppingBasket
    override val route = "baskets"
}

object ProductsBasket : ScreenDestination {
    override val icon = Icons.Filled.Dashboard
    override val route = "products"
    const val basketIdArg = "basket_type"
    val routeWithArgs = "${route}/{$basketIdArg}"
    val arguments = listOf(navArgument(basketIdArg) { type = NavType.LongType })
}

object Articles : ScreenDestination {
    override val icon = Icons.Filled.Dashboard
    override val route = "article"
}

object Setting : ScreenDestination {
    override val icon = Icons.Filled.Settings
    override val route = "settings"
}

//object SingleAccount : ScreenDestination {
//    // Added for simplicity, this icon will not in fact be used, as SingleAccount isn't
//    // part of the AppTabRow selection
//    override val icon = Icons.Filled.Money
//    override val route = "single_account"
//    const val accountTypeArg = "account_type"
//    val routeWithArgs = "$route/{$accountTypeArg}"
//    val arguments = listOf(
//        navArgument(accountTypeArg) { type = NavType.StringType })
//    val deepLinks = listOf(
//        navDeepLink { uriPattern = "App://$route/{$accountTypeArg}"})
//}
//object Accounts : ScreenDestination {
//    override val icon = Icons.Filled.Dashboard
//    override val route = "products"
//}
// Screens to be displayed in the top AppTabRow
val appTabRowScreens = listOf(Baskets, Articles, Setting)
