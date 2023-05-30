package com.example.shopping_list.ui.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping_list.data.DataRepository
import com.example.shopping_list.entity.ErrorApp
import com.example.shopping_list.entity.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel  @Inject constructor(
    private val errorApp: ErrorApp,
    private val dataRepository: DataRepository
): ViewModel() {

    private val _stateProductsScreen = MutableStateFlow(StateProductsScreen())
    val stateProductsScreen: StateFlow<StateProductsScreen> = _stateProductsScreen.asStateFlow()

    fun getStateProducts(basketId: Long){
        getListProducts(basketId)
        getListArticle()
        getListGroup()
        getListUnit()
        getNameBasket(basketId)
    }

    private fun getListProducts(basketId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getListProducts(basketId) }.fold(
                onSuccess = { _stateProductsScreen.update { currentState ->
                    currentState.copy(products = it as MutableList<Product>) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    private fun getListArticle() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getListArticle() }.fold(
                onSuccess = { _stateProductsScreen.update { currentState ->
                    currentState.copy(articles = it) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    private fun getListGroup() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getGroups() }.fold(
                onSuccess = { _stateProductsScreen.update { currentState ->
                    currentState.copy(group = it) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    private fun getListUnit() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { dataRepository.getUnits() }.fold(
                onSuccess = {
                    _stateProductsScreen.update { currentState -> currentState.copy(unitA = it) } },
                onFailure = { errorApp.errorApi(it.message!!) }
            )
        }
    }

    private fun getNameBasket(basketId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.getNameBasket(basketId)
            }.fold(
                onSuccess = {_stateProductsScreen.update { currentState ->
                    currentState.copy(nameBasket = it ) }},
                onFailure = { errorApp.errorApi(it.message!!)}
            )
        }
    }

    fun addProduct(product: Product, basketId: Long){
        product.basketId = basketId
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.addProduct(product)
            }.fold(
                onSuccess = {_stateProductsScreen.update { currentState ->
                    currentState.copy(products = it) }},
                onFailure = { errorApp.errorApi(it.message!!)}
            )
        }
        getListArticle()
        getListGroup()
        getListUnit()
    }

    fun putProductInBasket(product: Product, basketId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.putProductInBasket(product, basketId)
            }.fold(
                onSuccess = {_stateProductsScreen.update { currentState ->
                    currentState.copy(products = it ) }},
                onFailure = { errorApp.errorApi(it.message!!)}
            )
        }
    }

    fun movePositionProductInBasket(basketId: Long, direction: Int){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.setPositionProductInBasket(basketId, direction)
            }.fold(
                onSuccess = {_stateProductsScreen.update { currentState ->
                    currentState.copy(products = it) }},
                onFailure = { errorApp.errorApi(it.message!!)}
            )
        }
    }

    fun changeProductInBasket(product: Product, basketId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.changeProductInBasket(product, basketId)
            }.fold(
                onSuccess = {_stateProductsScreen.update { currentState ->
                    currentState.copy(products = it) } },
                onFailure = { errorApp.errorApi(it.message!!)}
            )
        }
    }

    fun changeGroupSelectedProduct(productList: List<Product>, idGroup: Long){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.changeGroupSelectedProduct(productList, idGroup) }.fold(
                onSuccess = {_stateProductsScreen.update { currentState ->
                    currentState.copy(products = it) }},
                onFailure = { errorApp.errorApi(it.message!!)}
            )
        }
    }

    fun deleteSelectedProducts(productList: List<Product>){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                dataRepository.deleteSelectedProduct(productList)
            }.fold(
                onSuccess = {_stateProductsScreen.update { currentState ->
                    currentState.copy(products = it) }},
                onFailure = {
                    errorApp.errorApi(it.message!!)}
            )
        }
    }
}