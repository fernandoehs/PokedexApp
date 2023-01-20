package com.fernandoherrera.pokedexapp.pokemondetailscreen

import androidx.lifecycle.ViewModel
import com.fernandoherrera.pokedexapp.data.remote.responses.Pokemon
import com.fernandoherrera.pokedexapp.data.repository.PokemonRepository
import com.fernandoherrera.pokedexapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) :ViewModel() {

    suspend fun getPokemonInfo(pokemonName:String): Resource<Pokemon>{
        return repository.getPokemonInfo(pokemonName)
    }
}