package com.jetbrains.greeting.presentation.Home

import com.jetbrains.greeting.core.BaseViewState
import com.jetbrains.greeting.core.CoreViewModel
import com.jetbrains.greeting.data.ProductsItem
import com.jetbrains.greeting.model.repository.HomeRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel :CoreViewModel<BaseViewState<HomeState>,HomeEvent>() {

    var data = HomeState()
    val homeRepo = HomeRepository()
    init {
        setState(BaseViewState.Loading)
    }

    override fun onTriggerEvent(event: HomeEvent) {
        when(event){
            is HomeEvent.showProducts ->{
                startLoading()
                fetchProduct()
            }

        }
    }

    private fun fetchProduct(){
        viewModelScope.launch {
            try {
                val products = homeRepo.getProductsApi()
                data = data.copy(products = products)
                setState(BaseViewState.Data(data))
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }
}