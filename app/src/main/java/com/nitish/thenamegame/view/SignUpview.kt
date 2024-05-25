package com.nitish.thenamegame.view

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nitish.thenamegame.R
import com.nitish.thenamegame.model.SignInState


@Composable
fun SignupPage(state: SignInState, onSignInClick: () -> Unit) {

    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let {error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Image(painter = painterResource(id = R.drawable.sage), modifier = Modifier
            .padding(top = 80.dp, bottom = 120.dp)
            .size(242.dp)
            .align(Alignment.CenterHorizontally), contentDescription ="A Sage", colorFilter = ColorFilter.tint(color = Color.Yellow) )


        Row {

            Button(
                onClick =  onSignInClick,
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
                    painter = painterResource(id = R.drawable.googl),
                    contentDescription = "Google"
                )
                Text(

                    text = "Sign in with Google",
                    color = Color.Black,
                    modifier = Modifier.padding(start = 18.dp, end = 18.dp)

                )
            }
        }

        Text(text = "By joining, you agree to our T&C and Privacy Policy" , color = Color.White, fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(top = 20.dp) )

    }
}