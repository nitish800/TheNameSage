package com.nitish.thenamegame.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nitish.thenamegame.R
import com.nitish.thenamegame.intent.PredictViewModel
import com.nitish.thenamegame.model.animObj


@Composable

fun HomePage(predictViewModel: PredictViewModel, navController: NavController){
    var Name by remember { mutableStateOf("")}
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Image(painter = painterResource(id = R.drawable.sage), modifier = Modifier
            .padding(top = 80.dp, bottom = 60.dp)
            .size(160.dp)
            .align(Alignment.CenterHorizontally), contentDescription ="A Sage", colorFilter = ColorFilter.tint(color = Color.Yellow) )

        TextField(
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 18.sp
            ),
            value = Name ,
            onValueChange = { newText -> Name = newText },
            label = { Text(
                text ="Name",
                fontSize = 18.sp
                    ) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(id = R.color.dark),
                focusedContainerColor = colorResource(id = R.color.dark),
                focusedTextColor = Color.Yellow,
                unfocusedTextColor = Color.Yellow,
                unfocusedLabelColor = Color.Yellow

            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
        )

        Row {

            Button(
                onClick = {
                        animObj.soundClick(context)

                          if(Name.length > 1){
                                predictViewModel.setName(Name)
                              navController.switchTab("PROFILE")
                              Log.e("cliecked", "cliecked 2")

                          }else{
                              Toast.makeText(
                                  context,
                                  "Name is Empty",
                                  Toast.LENGTH_LONG
                              ).show()
                          }
                },
                modifier = Modifier
                    .padding(start = 18.dp, end = 18.dp, top = 90.dp)
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.White
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sage),
                    contentDescription = "Sage"
                )
                Text(

                    text = "Analyze This Name",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 18.dp, end = 18.dp)

                )
            }
        }



    }
}

fun NavController.switchTab(s: String) {
    navigate(s) {

        graph.startDestinationRoute?.let { route ->
            popUpTo(route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}
