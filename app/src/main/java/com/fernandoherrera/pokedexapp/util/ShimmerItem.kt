package com.fernandoherrera.pokedexapp.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerItem() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(1.dp)
            .background(Color(0xFFD0D2D3))
    )
    Row(
        modifier = Modifier.padding(start = 1.dp, end = 1.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        /*Pokemon Item*/
        ShimmerAnimation {
            Box(
                modifier = Modifier
                    .size(width = 200.dp, height = 200.dp)
                    .background(brush = it)
            )
        }
    }
}