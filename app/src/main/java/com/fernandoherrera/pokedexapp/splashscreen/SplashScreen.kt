package com.fernandoherrera.pokedexapp.splashscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fernandoherrera.pokedexapp.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navController: NavHostController
) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alpha = animateFloatAsState(
        targetValue = if(startAnimation) 0f else 1f ,
        animationSpec = tween(
            durationMillis = 2500
        )
    )
    LaunchedEffect(key1 = true ){
        startAnimation = true
        delay(1500)
        navController.popBackStack()
        navController.navigate("pokemon_list_screen")

    }
    Splash(alpha = alpha.value)
}

@Composable
fun Splash(alpha: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_pokeball) ,
            contentDescription = "Logo Pokemon",
            Modifier
                .size(250.dp, 250.dp)
                .alpha(alpha))
    }
}

