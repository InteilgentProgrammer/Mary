package com.mary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mary.lab.EN
import com.mary.lab.button.MaryButton
import com.mary.lab.customer
import com.mary.lab.management
import com.mary.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                App()


        }
    }
}

@Composable
fun App() {
    MaryBackground(color = White, isIconDark = true, padding = 2f) {
        TwoApp()
    }
}

@Composable
fun TwoApp() {
        MaryColumn {
            MaryButton(onClick = { /*TODO*/ }) {
                Text(text = customer[EN])
            }
            MaryButton(onClick = { /*TODO*/ }) {
                Text(text = management[EN])
            }


        }
    }


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    App()
}