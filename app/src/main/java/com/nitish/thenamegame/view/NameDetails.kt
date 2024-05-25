package com.nitish.thenamegame.view

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nitish.thenamegame.R
import com.nitish.thenamegame.intent.PredictViewModel
import com.nitish.thenamegame.model.PredictionData
import com.nitish.thenamegame.model.PredictionDataState


@Composable
fun NameDetails(predictViewModel: PredictViewModel, navController: NavController){
    val viewState by predictViewModel.viewState.collectAsState()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.sage), modifier = Modifier
            .padding(top = 40.dp, bottom = 40.dp)
            .size(60.dp)
            .align(Alignment.CenterHorizontally), contentDescription ="A Sage", colorFilter = ColorFilter.tint(color = Color.Yellow) )

        Text(

            text = "\" HoHoHo  .. So ${predictViewModel.getName()} You Are of \"",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = 18.dp, top = 0.dp, end = 18.dp)
                .fillMaxWidth()

        )
        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxWidth()
                .height(360.dp)
                .border(width = 1.dp, color = Color.White)
        ){
            when(val state  = viewState){
                is PredictionDataState.Success -> {
                    val data = state.predictionData
                    reportCard(data = data)
                }

                is PredictionDataState.Error -> {
                    reportCard(data = PredictionData("error","error","error","error"))
                }
                is PredictionDataState.Loading -> {
                   reportCard(data = PredictionData())
                }
            }


        }

        Box(modifier = Modifier
            .width(150.dp)
            .height(50.dp)
            .background(color = Color.Yellow))
        {
            Row {
                Image(painter = painterResource(id = R.drawable.share), contentDescription ="share", modifier = Modifier
                    .padding(10.dp)
                    .size(36.dp)
                    .weight(1f)
                    .clickable { shareApp(context = context ) }
                )

                Image(painter = painterResource(id = R.drawable.close), contentDescription ="share", modifier = Modifier
                    .padding(10.dp)
                    .size(36.dp)
                    .weight(1f)
                    .clickable {
                        navController.popBackStack()
                    }
                )
            }
        }






    }
}

@Composable
fun reportCard(data:PredictionData){
    Row(modifier = Modifier
        .padding(top = 2.dp)
        .fillMaxWidth()
        .height(40.dp)){
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(color = Color.Yellow))
        {
            Text(

                text = "Age",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(start = 18.dp, top = 4.dp, end = 18.dp)
                    .fillMaxSize()

            )
        }
        Box(modifier = Modifier
            .weight(1.5f)
            .fillMaxSize()
            .background(color = Color.White))
        {
            data.age?.let {
                Text(

                    text = it,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 18.dp, top = 4.dp, end = 18.dp)
                        .fillMaxSize()

                )
            }
        }

    }
    Row(modifier = Modifier
        .padding(top = 2.dp)
        .fillMaxWidth()
        .height(40.dp)){
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(color = Color.Yellow))
        {
            Text(

                text = "Gender",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(start = 18.dp, top = 4.dp, end = 18.dp)
                    .fillMaxSize()

            )
        }
        Box(modifier = Modifier
            .weight(1.5f)
            .fillMaxSize()
            .background(color = Color.White))
        {
            data.gender?.let {
                Text(

                    text = it,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 18.dp, top = 4.dp, end = 18.dp)
                        .fillMaxSize()

                )
            }
        }

    }
    Row(modifier = Modifier
        .padding(top = 2.dp)
        .fillMaxWidth()
        .height(40.dp)){
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .background(color = Color.Yellow))
        {
            Text(

                text = "Nation",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(start = 18.dp, top = 4.dp, end = 18.dp)
                    .fillMaxSize()

            )
        }
        Box(modifier = Modifier
            .weight(1.5f)
            .fillMaxSize()
            .background(color = Color.White))
        {
            data.nation?.let {
                Text(

                    text = it,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 18.dp, top = 4.dp, end = 18.dp)
                        .fillMaxSize()

                )
            }
        }

    }
    data.joke?.let {
        Text(

            text = it,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = 18.dp, top = 20.dp, end = 18.dp)
                .fillMaxSize()

        )
    }

}


fun shareApp(context: Context) {
    val appPackageName = context.packageName
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "Check out this app: https://play.google.com/store/apps/details?id=$appPackageName")
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
}