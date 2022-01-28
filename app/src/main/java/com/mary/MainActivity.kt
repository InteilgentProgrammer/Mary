package com.mary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mary.lab.button.MaryButton
import com.mary.lab.others.AR
import com.mary.lab.others.EN
import com.mary.lab.others.customer
import com.mary.lab.others.management
import com.mary.lab.text.MaryText
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
        MaryTheme {
            TwoApp()
        }

    }
}

@Composable
fun TwoApp() {
    MaryColumn {

        MaryButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(1f)) {
            MaryText(text = customer[EN], modifier = Modifier.padding(16.dp))

        }
        Spacer(modifier = Modifier.padding(vertical = 14.dp))
        MaryButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(1f)) {
            MaryText(text = management[EN], modifier = Modifier.padding(16.dp))
        }

    }

}


