package com.jetbrains.greeting.presentation.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jetbrains.greeting.core.BaseViewState
import com.jetbrains.greeting.presentation.Navigation.BottomNavItem
import com.jetbrains.greeting.presentation.components.EmptyView
import com.jetbrains.greeting.presentation.components.ErrorView
import com.jetbrains.greeting.presentation.components.LoadingView
import com.seiko.imageloader.rememberImagePainter
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, navController: NavController
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.onTriggerEvent(HomeEvent.showProducts)
    }

    HomeScreenBody(modifier, uiState, navController) { viewModel.onTriggerEvent(it) }

}


@Composable
fun HomeScreenBody(
    modifier: Modifier = Modifier,
    uiState: BaseViewState<*>,
    navController: NavController,
    onEvent: (HomeEvent) -> Unit
) {
    when (uiState) {
        is BaseViewState.Data -> {
            val homeState = uiState.value as? HomeState

            if (homeState != null) {
                HomeScreenContent(modifier, homeState, onEvent, navController)
            }
        }

        BaseViewState.Empty -> EmptyView()
        is BaseViewState.Error -> ErrorView(message = uiState.throwable.message.toString())
        BaseViewState.Loading -> LoadingView()
    }

}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    uiState: HomeState,
    onEvent: (HomeEvent) -> Unit,
    navController: NavController
) {
    BoxWithConstraints {
        val scope = this
        val maxWidth = scope.maxWidth
        var cols = 2
        var modifier = Modifier.fillMaxWidth()
        if (maxWidth > 840.dp && maxWidth<=1080.dp) {
            cols = 3
            modifier = Modifier.widthIn(max = 1080.dp)
        }else if (maxWidth > 1080.dp){
            cols = 4
            modifier = Modifier.widthIn(max = 1920.dp)
        }

        val scrollState = rememberLazyGridState()
        val products = uiState.products
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            SearchBarHome(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(cols),
                state = scrollState,
                contentPadding = PaddingValues(16.dp)
            ) {
                products?.let { products ->
                    items(products) { product ->
                        ProductCard(
                            modifier = Modifier.padding(8.dp).clickable {
                                navController.navigate("${BottomNavItem.ProductDetails.route}/${product.id.toString()}")
                            },
                            product.title,
                            product.image,
                            product.price.toString(),
                            product.category,
                            product.rating?.rate?.toFloat()
                        )
                    }
                }
            }
        }

    }

}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    title: String? = "No Tittle Available",
    url: String? = "No Image to show",
    price: String? = "0.0",
    category: String? = "Null",
    ratting: Float? = 0.0f
) {

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth().height(300.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
    ) {
        val painter = rememberImagePainter(url ?: "No Tittle Available")
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painter,
                modifier = Modifier.height(150.dp).weight(0.6f).fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier.fillMaxWidth().weight(0.2f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    title ?: "No Image to show",
                    modifier = Modifier.weight(1f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Box(
                    modifier = Modifier.clip(
                        RoundedCornerShape(50)
                    ).background(MaterialTheme.colorScheme.primary)
                ) {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }

            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    category ?: "Null",
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(modifier = Modifier.fillMaxWidth().weight(0.2f)) {
                Text(price ?: "0.0", modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("${ratting}")
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarHome(modifier: Modifier = Modifier) {

    SearchBar(
        modifier = modifier.fillMaxWidth(),
        query = "",
        onQueryChange = {},
        onSearch = {},
        active = false,
        placeholder = { Text("Search Products") },
        onActiveChange = {},
    ) {

    }
}


@Composable
fun PrevProductsCard() {
    ProductCard(
        title = "Snickers off white 2024",
        category = "Nike",
        url = "https://images.unsplash.com/photo-1542291026-7eec264c27ff?q=80&w=1770&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        ratting = 3.9f,
        price = "38"
    )

}
