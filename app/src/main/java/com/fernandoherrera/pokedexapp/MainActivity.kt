package com.fernandoherrera.pokedexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fernandoherrera.pokedexapp.pokemonlist.PokemonListScreen
import com.fernandoherrera.pokedexapp.ui.theme.PokedexAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexAppTheme {
             val navController = rememberNavController()
             NavHost(
                 navController = navController,
                 startDestination = "pokemon_list_screen"
             ) {
                 composable("pokemon_list_screen"){
                    PokemonListScreen(navController = navController)
                 }
                 composable(
                     "pokemon_detail_screen",
                        arguments = listOf(
                         navArgument("dominantColor"){
                             type = NavType.IntType
                         },
                         navArgument("pokemonName"){
                             type = NavType.StringType
                         }
                    )
                 ){
                     val dominantColor = remember {
                         val color = it.arguments?.getInt("dominantColor")
                         color?.let { Color(it) } ?: Color.White

                     }
                     val pokemonName = remember{
                         it.arguments?.getString("pokemonName")
                     }


                 }



             }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokedexAppTheme {
        Greeting("Android")
    }
}