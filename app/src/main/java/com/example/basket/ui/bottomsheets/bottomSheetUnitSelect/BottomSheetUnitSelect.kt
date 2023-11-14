package com.example.basket.ui.bottomsheets.bottomSheetUnitSelect

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basket.R
import com.example.basket.entity.BottomSheetInterface
import com.example.basket.entity.TagsTesting
import com.example.basket.entity.UPDOWN
import com.example.basket.ui.bottomsheets.bottomSheetProduct.BottomSheetProductState
import com.example.basket.ui.bottomsheets.component.ButtonConfirmText
import com.example.basket.ui.bottomsheets.component.FieldName
import com.example.basket.ui.components.ShowArrowVer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetUnitSelect(uiState: BottomSheetInterface)
{
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true },)
    ModalBottomSheet(
        onDismissRequest = uiState.onDismissSelectUnit,
        modifier = Modifier
            .testTag(TagsTesting.BASKETBOTTOMSHEET)
            .padding(horizontal = dimensionResource(id = R.dimen.bottom_sheet_padding_hor1)),
        shape = MaterialTheme.shapes.small,
        containerColor = BottomSheetDefaults.ContainerColor,
        contentColor = contentColorFor(BottomAppBarDefaults.containerColor),
        tonalElevation = BottomSheetDefaults.Elevation,
        scrimColor = BottomSheetDefaults.ScrimColor,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        windowInsets = BottomSheetDefaults.windowInsets,
        sheetState = sheetState,
        content = { BottomSheetUnitSelectContent(uiState) })
}
@Composable
fun BottomSheetUnitSelectContent(uiState: BottomSheetInterface)
{
    Column(modifier = Modifier.fillMaxWidth().padding(
        horizontal = dimensionResource(id = R.dimen.bottom_sheet_padding_hor),
        vertical = dimensionResource(id = R.dimen.bottom_sheet_item_padding_ver)
    ),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        FieldName(uiState)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.bottom_sheet_item_padding_ver)))
        BoxExistingUnit(uiState)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.bottom_sheet_item_padding_ver)))
        ButtonConfirmText(uiState)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.bottom_sheet_item_padding_ver)))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoxExistingUnit(uiState: BottomSheetInterface)
{
    val listItems = uiState.unitApp.filter {
        if (uiState.enteredNameUnit.value != null) {
            it.nameUnit.contains(uiState.enteredNameUnit.value, ignoreCase = true)
        } else true
    }

    val listState = rememberLazyGridState()
    val showArrowUp = remember {
        derivedStateOf { listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index != 0 } }.value
    val showArrowDown = remember {
        derivedStateOf { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index !=
                listState.layoutInfo.totalItemsCount - 1 } }.value
    Column {
        ShowArrowVer(direction = UPDOWN.UP, enable = showArrowUp && listItems.isNotEmpty(), drawLine = true)
        LazyVerticalGrid(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            columns = GridCells.Adaptive(minSize = 90.dp),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.height(70.dp).fillMaxWidth()
        ) {
            items(items = listItems) { unit ->
                Text(
                    text = unit.nameUnit,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .animateItemPlacement()
                        .clickable { uiState.enteredNameUnit.value = unit.nameUnit
                            uiState.selectedUnit.value = unit}
                        .padding(4.dp)
                        .background(
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.tertiaryContainer)
                )
            }
        }
        ShowArrowVer(direction = UPDOWN.DOWN, enable = showArrowDown && listItems.isNotEmpty(), drawLine = true)
    }
}

@Preview
@Composable
fun BottomSheetUnitSelectLayoutPreview(){
    BottomSheetUnitSelectContent(BottomSheetProductState())
}