package com.jetbrains.greeting.presentation.Home

import androidx.lifecycle.viewModelScope
import com.jetbrains.greeting.core.BaseViewState
import com.jetbrains.greeting.core.CoreViewModel
import com.jetbrains.greeting.model.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository:HomeRepository) :CoreViewModel<BaseViewState<HomeState>,HomeEvent>() {

    var data = HomeState()
    init {
        setState(BaseViewState.Loading)
    }

    override fun onTriggerEvent(event: HomeEvent) {
        when(event){
            is HomeEvent.showProducts ->{
                startLoading()
                fetchProduct()
            }

            is HomeEvent.showSingleProduct ->{
                startLoading()
                fetchSingleProduct(event.productId)
            }
        }
    }

    private fun fetchProduct(){
        viewModelScope.launch {
            try {
                val products = repository.getProductsApi()
                data = data.copy(products = products)
                setState(BaseViewState.Data(data))
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun fetchSingleProduct(productId: String) {
        viewModelScope.launch {
            try {
                val product = repository.getSingleProduct(productId)
                data = data.copy(singleProduct = product)
                setState(BaseViewState.Data(data))
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

}