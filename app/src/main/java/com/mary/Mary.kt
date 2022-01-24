package com.start.mary

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.start.R

//Color
object MaryColor {
    val White = Color(0XFFFFFFFF)
    val Black = Color(0XFF000000)
    val Orange = Color(0xFFFFB800)
    val Red = Color(0xFFFF0000)
    val DarkGray = Color(0xFF444444)
    val Gray = Color(0xFF888888)
    val LightGray = Color(0xFFCCCCCC)
    val Blue = Color(0xFF0000FF)
    val Yellow = Color(0xFFFFFF00)
    val Cyan = Color(0xFF00FFFF)
    val Magenta = Color(0xFFFF00FF)
    val Transparent = Color(0x00000000)
}
//--------------------------------------------------------------------------------------------------
// Mary Shapes
val maryShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)
//--------------------------------------------------------------------------------------------------
//Languages
val language = listOf("english", "arabic")
val mary = listOf("Mary", "ماري")
val english = listOf("English", "الانكليزية")
val arabic = listOf("Arabic", "العربية")
val red = listOf("Red", "احمر")
val black = listOf("Black", "اسود")
val management = listOf("Management", "مدير")
val customer = listOf("Customer", "الزبون")
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
    color: Color = MaryColor.White,
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
@Composable
fun MaryText(
    modifier: Modifier = Modifier,
    text: String = "Mary",
    textColor: Color = Color.Unspecified,
    fontSize: Int = 25,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = TextAlign.Center,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        fontSize = fontSize.sp,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style
    )
}
//--------------------------------------------------------------------------------------------------
@Composable
fun MaryImage(
    modifier: Modifier = Modifier,
    painter: Int = R.drawable.ic_launcher_foreground,
    contentDescription: String? = "Mary",
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    Image(
        painter = painterResource(painter),
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment, contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
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
