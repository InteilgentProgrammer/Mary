package com.mary

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mary.ui.theme.White

//--------------------------------------------------------------------------------------------------
// value to Change ui
@Composable
fun MaryRemember() {
    var intRemember by remember { mutableStateOf(0) }
}

//--------------------------------------------------------------------------------------------------
// Function
var maryToast: (String, Context) -> Unit =
    { text, context ->
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

//--------------------------------------------------------------------------------------------------
@Composable
fun MaryBackground(
    color: Color = White,
    isIconDark: Boolean = true,
    padding: Float = 1f,
    content: @Composable BoxScope.() -> Unit
) {
    val systemUi: SystemUiController = rememberSystemUiController()
    systemUi.setSystemBarsColor(color, isIconDark)
    Row {
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .weight(padding, true)
                .background(color)
        )
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .weight(14f, true)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .background(color)
                .weight(padding, true)
        )
    }

}

//--------------------------------------------------------------------------------------------------
@Composable
fun MaryColumn(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}
//--------------------------------------------------------------------------------------------------
//Add Screen
//sealed class Screen(val route: String) {
//    object App : Screen(route = "app")
//    object Management : Screen(route = "management")
//    object Customer : Screen(route = "customer")
//}

//@Composable
//fun MaryHost(NavHost:NavHostController) {
//NavHost(navController = NavHost, startDestination = Screen.App.route ){
//    composable(Screen.App.route){App(controller = NavHost)}
//    composable(Screen.Management.route){ Management()}
//    composable(Screen.Customer.route){ Customer()}
//}
//}
