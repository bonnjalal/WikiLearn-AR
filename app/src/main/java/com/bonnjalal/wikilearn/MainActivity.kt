package com.bonnjalal.wikilearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bonnjalal.wikilearn.ui.theme.WikiLearnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            installSplashScreen()
            WikiLearnTheme {
                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    Greeting("Android")
//                }
                WebViewPage("https://learn.wiki/")
//                WebViewPage("https://app.learn.wiki/learning/course/course-v1:WikimediaMAUG+RWICMA2+2024/home")
                //The Configuration object represents all of the current configurations, not just the ones that have changed.
               /* val configuration = LocalConfiguration.current
                when (configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        Toast.makeText(context, "landscape", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context, "portrait", Toast.LENGTH_SHORT).show()
                    }
                }*/
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WikiLearnTheme {
        Greeting("Android")
    }
}