package com.example.basket.ui.screens.products

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.basket.R
import com.example.basket.entity.Product
import com.example.basket.entity.SizeElement
import com.example.basket.entity.TypeText
import com.example.basket.entity.UPDOWN
import com.example.basket.navigation.ScreenDestination
import com.example.basket.ui.bottomsheets.bottomSheetProductAdd.BottomSheetProductAddGeneral
import com.example.basket.ui.components.CollapsingToolbar
import com.example.basket.ui.components.HeaderSection
import com.example.basket.ui.components.ShowArrowVer
import com.example.basket.ui.components.TextApp
import com.example.basket.ui.components.dialog.EditQuantityDialog
import com.example.basket.ui.components.dialog.SelectSectionDialog
import com.example.basket.ui.components.showFABs
import com.example.basket.ui.theme.SectionColor
import com.example.basket.ui.theme.getIdImage
import com.example.basket.ui.theme.sizeApp
import com.example.basket.ui.theme.styleApp
import com.example.basket.utils.bottomBarAnimatedScroll
import kotlin.math.roundToInt

@Composable
fun ProductsScreen(basketId: Long, screen: ScreenDestination)
{
    val viewModel: ProductViewModel = hiltViewModel()
    viewModel.getStateProducts(basketId)

    ProductScreenCreateView(
        basketId = basketId,
        screen = screen,
        viewModel = viewModel)
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ProductScreenCreateView(basketId: Long, screen: ScreenDestination, viewModel: ProductViewModel,
){
    val uiState by viewModel.productsScreenState.collectAsState()
    uiState.putProductInBasket = remember {{ product -> viewModel.putProductInBasket(product, basketId) }}
    uiState.changeProduct = remember {{ product -> viewModel.changeProduct(product, basketId) }}
    uiState.doChangeSection = remember {{ productList, idSection ->
        viewModel.changeSections(productList, idSection) }}
    uiState.doDeleteSelected = remember {{ productList -> viewModel.deleteSelectedProducts(productList) }}
    uiState.doSelected = remember {{ articleId -> viewModel.changeSelected(articleId) }}
    uiState.onAddProduct = remember {{ product: Product -> viewModel.addProduct(product, basketId) }}

    screen.textFAB = stringResource(id = R.string.products)
    screen.onClickFAB = remember {{ uiState.triggerRunOnClickFAB.value = true}}

    if (uiState.triggerRunOnClickFAB.value) BottomSheetProductAddGeneral( uiStateP = uiState)
    ProductsScreenLayout(uiState = uiState, screen = screen)
}

@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@Composable
fun ProductsScreenLayout(uiState: ProductsScreenState, screen: ScreenDestination,
){
    val isSelectedId: MutableState<Long> = remember { mutableLongStateOf(0L) }
    val deleteSelected: MutableState<Boolean> = remember { mutableStateOf(false) }
    val unSelected: MutableState<Boolean> = remember { mutableStateOf(false) }
    val changeSectionSelected: MutableState<Boolean> = remember { mutableStateOf(false) }
    var startScreen by remember { mutableStateOf(false) } // Индикатор первого запуска окна
    val bottomBarOffsetHeightPx = remember { mutableFloatStateOf(0f) }

    if (isSelectedId.value > 0L) {
        uiState.doSelected(isSelectedId.value)
        isSelectedId.value = 0
    }
    if (unSelected.value) {
        uiState.products.forEach { productList -> productList.forEach { it.isSelected = false } }
        unSelected.value = false
    }
    if (changeSectionSelected.value) {
        SelectSectionDialog(
            listSection = uiState.sections,
            onDismiss = { changeSectionSelected.value = false },
            onConfirm = {
                if (it != 0L) uiState.doChangeSection( uiState.products.flatten(), it)
                changeSectionSelected.value = false
            },
        )
    }
    if (deleteSelected.value) {
        uiState.doDeleteSelected(uiState.products.flatten())
        deleteSelected.value = false
    }

    Box( Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxHeight()
                .bottomBarAnimatedScroll(
                    height = sizeApp(SizeElement.HEIGHT_BOTTOM_BAR),
                    offsetHeightPx = bottomBarOffsetHeightPx
                ), ) {
            ProductLazyColumn(
                uiState = uiState,
                screen = screen,
                scrollOffset =-bottomBarOffsetHeightPx.floatValue.roundToInt(),
                doSelected = { idItem -> isSelectedId.value = idItem } )
        }
        startScreen = showFABs(
            startScreen = startScreen,
            isSelected = uiState.products.flatten().find { it.isSelected } != null,
            doDeleted = { deleteSelected.value = true },
            doChangeSection = { changeSectionSelected.value = true },
            doUnSelected = { unSelected.value = true },
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
        )
    }
}

@Composable
fun ProductLazyColumn(
    uiState: ProductsScreenState,
    screen: ScreenDestination,
    scrollOffset:Int,
    doSelected: (Long) -> Unit
) {
    val listState = rememberLazyListState()
    val editProduct: MutableState<Product?> = remember { mutableStateOf(null) }

    val showArrowUp = remember {
        derivedStateOf { listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index != 0 }}.value
    val showArrowDown = remember {
        derivedStateOf { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index !=
            listState.layoutInfo.totalItemsCount - 1 } }.value

    if (editProduct.value != null ) {
        EditQuantityDialog(
            product = editProduct.value!!,
            listUnit = uiState.unitApp,
            onDismiss = { editProduct.value = null },
            onConfirm = {
                editProduct.value = null
                uiState.changeProduct (it)
            }
        )
    }
    CollapsingToolbar(
        text = stringResource(screen.textHeader)+ " " + uiState.nameBasket ,
        idImage = getIdImage(screen),
        scrollOffset = scrollOffset)
    Spacer(modifier = Modifier.height(2.dp))
    ShowArrowVer(direction = UPDOWN.UP, enable = showArrowUp && uiState.products.isNotEmpty(), drawLine = false)
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .padding(vertical = dimensionResource(R.dimen.lazy_padding_ver))
    ) {
        items(items = uiState.products) { item ->
            Column(modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(
                    if (item[0].article.section.colorSection != 0L) Color(item[0].article.section.colorSection)
                    else SectionColor
                )) {
                HeaderSection(text = item[0].article.section.nameSection, modifier = Modifier)
                ProductsLayoutColum(
                    products = item,
                    putProductInBasket = uiState.putProductInBasket,
                    editProduct = { product -> editProduct.value = product },
                    doSelected = doSelected )
            }
        }
    }
    ShowArrowVer(direction = UPDOWN.DOWN, enable = showArrowDown && uiState.products.isNotEmpty(), drawLine = false)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsLayoutColum(
    products: List<Product> ,
    putProductInBasket: (Product) -> Unit,
    editProduct: (Product) -> Unit,
    doSelected: (Long) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.lazy_padding_ver))
    ) {
        for (product in products){
            key(product.idProduct){
                val dismissState = rememberDismissState(
                    confirmValueChange = {
                        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                            putProductInBasket( product ) }
                        false
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier.padding(vertical = 1.dp),
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                    background = {  },
                    dismissContent = { SectionProduct(product, doSelected, editProduct) },
                )
            }
        }
    }
}

@Composable
fun SectionProduct(
    sectionItems: Product,
    doSelected: (Long)->Unit,
    editProduct: (Product) -> Unit,
){
    val localDensity = LocalDensity.current
    var heightIs by remember { mutableStateOf(0.dp) }
    Box(
        Modifier
            .padding(horizontal = 6.dp)
            .onGloballyPositioned { coordinates ->
                heightIs = with(localDensity) { coordinates.size.height.toDp() }
            })
    {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(6.dp))
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier
                    .width(8.dp)
                    .background(if (sectionItems.isSelected) Color.Red else Color.LightGray)
                    .height(heightIs)
                    .align(Alignment.CenterVertically)
                    .clickable { doSelected(sectionItems.idProduct) }
            )
            TextApp (text = sectionItems.article.nameArticle,
                modifier = Modifier
                    .weight(1f)
                    .clickable { doSelected(sectionItems.idProduct) }
                    .padding(
                        start = dimensionResource(R.dimen.lazy_padding_hor),
                        top = dimensionResource(R.dimen.lazy_padding_ver),
                        bottom = dimensionResource(R.dimen.lazy_padding_ver)
                    ),
                style = styleApp(nameStyle = TypeText.TEXT_IN_LIST),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.width(4.dp))
            val num = if (sectionItems.value.rem(1).equals(0.0)) sectionItems.value.toInt()
            else sectionItems.value

            TextApp (text = num.toString(),
                style = styleApp(nameStyle = TypeText.TEXT_IN_LIST),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .width(70.dp)
                    .padding(vertical = dimensionResource(R.dimen.lazy_padding_ver))
                    .clickable { editProduct(sectionItems) },
            )
            Spacer(modifier = Modifier.width(4.dp))
            TextApp (text = sectionItems.article.unitApp.nameUnit,
                style = styleApp(nameStyle = TypeText.TEXT_IN_LIST),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .width(50.dp)
                    .padding(vertical = dimensionResource(R.dimen.lazy_padding_ver))
                    .clickable { doSelected(sectionItems.idProduct) }
            )
        }
        if ( sectionItems.putInBasket ) Divider(
            color = MaterialTheme.colorScheme.onSurface,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = heightIs / 2, start = 8.dp, end = 8.dp)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun ProductsScreenLayoutPreview() {

}

