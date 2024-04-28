package com.bonnjalal.wikilearn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun PageLoading(showLoader:Boolean, setShowLoader: (showDialog:Boolean) -> Unit){

//    val loaderDialogScreen = remember { mutableStateOf(showLoader) }
    if (showLoader) {

        // Dialog function
        Dialog(
            onDismissRequest = {
//                loaderDialogScreen.value = false
                setShowLoader(false)
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false // experimental
            )
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {



                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.wiki_learn),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth(),
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        //.........................Text: title
                        Text(
                            text = "Loading...",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .fillMaxWidth(),
                            letterSpacing = 2.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        //.........................Text : description
                        Text(
                            text = "المرجو الانتظار ...",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                            letterSpacing = 3.sp,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        //.........................Spacer
                        Spacer(modifier = Modifier.height(48.dp))
                    }


                    Image(
                        painter = painterResource(id = R.drawable.wikimedia_ma),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(120.dp)
                            .fillMaxWidth()
                            .padding(bottom = 48.dp),
                    )

                }

            }
        }

    }
}