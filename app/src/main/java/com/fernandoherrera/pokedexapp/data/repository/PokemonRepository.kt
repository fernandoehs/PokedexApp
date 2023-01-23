package com.fernandoherrera.pokedexapp.data.repository

import com.fernandoherrera.pokedexapp.data.remote.PokeApi
import com.fernandoherrera.pokedexapp.data.remote.responses.Pokemon
import com.fernandoherrera.pokedexapp.data.remote.responses.PokemonList
import com.fernandoherrera.pokedexapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private  val api:PokeApi
){

    suspend fun getPokemonList(limit:Int, offset:Int): Resource<PokemonList>{
        val response = try{
            api.getPokemonList(limit,offset)
        } catch (e:Exception){
            return Resource.Error("Un error desconocido ha ocurrido")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon>{
        val response = try{
            api.getPokemonInfo(pokemonName)
        } catch (e:Exception){
            return Resource.Error("Un error desconocido ha ocurrido")
        }
        return Resource.Success(response)
    }

}