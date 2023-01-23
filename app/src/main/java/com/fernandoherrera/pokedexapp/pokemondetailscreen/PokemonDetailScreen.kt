package com.fernandoherrera.pokedexapp.pokemondetailscreen

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.fernandoherrera.pokedexapp.R
import com.fernandoherrera.pokedexapp.data.remote.responses.Ability
import com.fernandoherrera.pokedexapp.data.remote.responses.DiamondPearl
import com.fernandoherrera.pokedexapp.data.remote.responses.Pokemon
import com.fernandoherrera.pokedexapp.data.remote.responses.Type
import com.fernandoherrera.pokedexapp.util.Resource
import com.fernandoherrera.pokedexapp.util.parseStatToAbbr
import com.fernandoherrera.pokedexapp.util.parseStatToColor
import com.fernandoherrera.pokedexapp.util.parseTypeToColor
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    pokemonName: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonName = pokemonName)
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 16.dp)

    ) {
        PokemonDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
        )
            PokemonDetailStateWrapper(
                pokemonInfo = pokemonInfo,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = pokemonImageSize / 2f,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .shadow(10.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.surface)
                    .padding(16.dp)
                    .size(100.dp)
                    .padding(
                        top = topPadding + pokemonImageSize / 2f,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            )

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            var isVisible by remember {
                mutableStateOf(false)
            }
            val alpha by animateFloatAsState(
                targetValue = if (isVisible) 1f else 0f,
                animationSpec = tween(durationMillis = 1500)
            )
            if (pokemonInfo is Resource.Success) {
                pokemonInfo.data?.sprites?.let {
                    AsyncImage(
                        model = it.front_default,
                        onState= {isVisible = true},
                        contentDescription = pokemonInfo.data.name,
                        modifier = Modifier
                            .size(pokemonImageSize)
                            .alpha(alpha = alpha)
                            .offset(y = topPadding)
                    )
                }
            }
        }
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 190.dp) ){
            Text(
                text = "#${pokemonInfo.data?.id} ${pokemonInfo.data?.name?.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                }}",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface
            )
        }

    }
}

@Composable
fun PokemonDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier.background(
            Brush.verticalGradient(
                listOf(
                    Color.Black,
                    Color.Transparent
                )
            )
        )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}


@Composable
fun PokemonDetailStateWrapper(
    pokemonInfo: Resource<Pokemon>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (pokemonInfo) {
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
        is Resource.Success -> {
            pokemonInfo.data?.let {
                PokemonDetailSection(
                    pokemonInfo = it,
                    modifier = modifier
                        .offset(y = (-20).dp)
                )
            }
        }
        is Resource.Error -> {
            Text(
                text = (pokemonInfo.message ?: "Unknown Error"),
                color = Color.Red,
                modifier = modifier
            )
        }
    }
}



@Composable
fun PokemonDetailSection(
    pokemonInfo: Pokemon,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 20.dp)
            .verticalScroll(scrollState)
    ) {
        TabScreen( pokemonInfo )
    }
}

@Composable
fun PokemonAbilitiesSection(abilities: List<Ability>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        for (ability in abilities) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(Color.Blue)
                    .height(35.dp)
            ) {
                Text(
                    text = ability.ability.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                    },
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun PokemonTypeSection(types: List<Type>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .height(35.dp)
            ) {
                Text(
                    text = type.type.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                    },
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun PokemonDetailDataSection(
    pokemonWeight: Int,
    pokemonHeight: Int,
    sectionHeight: Dp = 80.dp
) {
    val pokemonWeightInKg = remember {
        StrictMath.round(pokemonWeight * 100f) / 1000f
    }
    val pokemonHeightInMeters = remember {
        StrictMath.round(pokemonHeight * 100f) / 1000f
    }
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        PokemonDetailDataItem(
            dataValue = pokemonWeightInKg,
            dataUnit = "kg",
            dataIcon = painterResource(id = R.drawable.ic_weight ),
            modifier = Modifier.weight(1f)
        )
        Spacer(
            modifier = Modifier
                .size(1.dp, sectionHeight)
                .background(Color.LightGray)
        )
        PokemonDetailDataItem(
            dataValue = pokemonHeightInMeters,
            dataUnit = "m",
            dataIcon = painterResource(id = R.drawable.ic_height),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PokemonDetailDataItem(
    dataValue: Float,
    dataUnit: String,
    dataIcon: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(painter = dataIcon, contentDescription = null, tint = MaterialTheme.colors.onSurface)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$dataValue$dataUnit",
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun PokemonStat(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statValue / statMaxValue.toFloat()
        } else 0f,
        animationSpec = tween(
            animDuration,
            animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color(0xFF505050)
                } else {
                    Color.LightGray
                }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = statName,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = (curPercent.value * statMaxValue).toInt().toString(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PokemonBaseStats(
    pokemonInfo: Pokemon,
    animDelayPerItem: Int = 100
) {
    val maxBaseStat = remember {
        pokemonInfo.stats.maxOf { it.base_stat }
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Estatus Base: ",
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(6.dp))

        for (i in pokemonInfo.stats.indices) {
            val stat = pokemonInfo.stats[i]
            PokemonStat(
                statName = parseStatToAbbr(stat),
                statValue = stat.base_stat,
                statMaxValue = maxBaseStat,
                statColor = parseStatToColor(stat),
                animDelay = i * animDelayPerItem
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabScreen(  pokemonInfo: Pokemon) {
    val pagerState = rememberPagerState(pageCount = 3)
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        Tabs(pagerState)
        TabsContent(pagerState = pagerState, pokemonInfo = pokemonInfo)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(pagerState: PagerState, pokemonInfo: Pokemon) {
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> SectionOne(pokemonInfo = pokemonInfo)
            1 -> SectionTwo(pokemonInfo = pokemonInfo)
            2 -> SectionThree(pokemonInfo = pokemonInfo)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf("About", "Stats", "Evo")
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        contentColor = Color.Black,
        divider = {
            TabRowDefaults.Divider(
                thickness = 3.dp,
                color = Color.Black
            )
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 3.dp,
                color = Color.LightGray
            )
        }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        text = list[index],
                        color = if (pagerState.currentPage == index) Color.Black else Color.DarkGray
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@Composable
fun SectionOne(pokemonInfo: Pokemon) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Peso y Altura :",
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(15.dp))
        PokemonDetailDataSection(
            pokemonWeight = pokemonInfo.weight,
            pokemonHeight = pokemonInfo.height
        )
        Text(
            text = "Tipo :",
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSurface
        )
        PokemonTypeSection(types = pokemonInfo.types)
        Text(
            text = "Habilidades :",
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSurface
        )
        PokemonAbilitiesSection(abilities = pokemonInfo.abilities)
        if (pokemonInfo.held_items.isNotEmpty()){
            Text(
                text = "Especial item :",
                fontSize = 20.sp,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = pokemonInfo.held_items[0].item.name,
                color = Color.Black,
                fontSize = 18.sp
            )
        }
        PokemonBaseStats(pokemonInfo = pokemonInfo)
    }
}

@Composable
fun SectionTwo(pokemonInfo: Pokemon) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
     Text(text = "En Progreso...")
    }
}

@Composable
fun SectionThree(pokemonInfo: Pokemon) {
    Column(
        modifier = Modifier.width(300
            .dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Evolución :",
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(10.dp))
        PokemonEvo(pokemonInfo = pokemonInfo)
    }
}


@Composable
fun PokemonEvo(pokemonInfo: Pokemon) {

    fun ImageEvo(generation:String, level:String) ="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/${generation}/${level}/${pokemonInfo?.id}.png"

    val YellowGenI = ImageEvo("generation-i","yellow")
    val GoldGenII = ImageEvo("generation-ii","gold")
    val FireredLeafgreenGenIII = ImageEvo("generation-iii","firered-leafgreen")
    val DiamondPearlGenIV = ImageEvo("generation-iv","diamond-pearl")
    val BlackWhiteGenV = ImageEvo("generation-v","black-white")
    val OmegarubyGenVI = ImageEvo("generation-vi","omegaruby-alphasapphire")
    val UltraGenVII = ImageEvo("generation-vii","ultra-sun-ultra-moon")

    val ListEvoGen = listOf(
        YellowGenI,
        GoldGenII,
        FireredLeafgreenGenIII,
        DiamondPearlGenIV,
        BlackWhiteGenV,
        OmegarubyGenVI,
        UltraGenVII
        )

            ListEvoGen.forEachIndexed{ index, item ->
                    Text(
                        text =" Generación ${index+1}",
                        fontSize = 20.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                    Image(
                        painter = rememberAsyncImagePainter(item),
                        contentDescription = null,
                        modifier = Modifier.size(150.dp)
                    )
            }
}

