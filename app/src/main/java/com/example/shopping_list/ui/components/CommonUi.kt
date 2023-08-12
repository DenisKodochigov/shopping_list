package com.example.shopping_list.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.RemoveDone
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.shopping_list.R
import com.example.shopping_list.entity.CustomTriangleShape
import com.example.shopping_list.entity.Direcions
import com.example.shopping_list.entity.SortingBy
import com.example.shopping_list.entity.TypeText
import com.example.shopping_list.ui.theme.ButtonColorsMy
import com.example.shopping_list.ui.theme.ScaffoldColor
import com.example.shopping_list.ui.theme.styleApp

@Composable
fun HeaderScreen(text: String) {
    Spacer(modifier = Modifier.height(24.dp))
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(text, style = styleApp(nameStyle = TypeText.NAME_SCREEN))
    }
}

@Composable
fun HeaderImScreen(text: String, idImage:Int ) {
    Column( Modifier.fillMaxWidth() ){
        Image(
            painter = painterResource(id = idImage),
            contentDescription = "Photo",
            contentScale = Crop,
            modifier = Modifier.height(340.dp)
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(ScaffoldColor)
            .padding(horizontal = 12.dp, vertical = 4.dp)) {
            Text( text = text, style = styleApp(nameStyle = TypeText.NAME_SCREEN),
                modifier = Modifier.align(alignment = Alignment.BottomCenter) )
        }
    }
}
@Composable
fun HeaderSection(text: String, modifier: Modifier) {
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier
            .fillMaxWidth()
            .padding(start = 12.dp), horizontalArrangement = Arrangement.Start) {
        Text(text, style = styleApp(nameStyle = TypeText.NAME_SECTION))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyExposedDropdownMenuBox(
    listItems: List<Pair<Long, String>>,
    label: String?,
    modifier: Modifier,
    filtering: Boolean,
    enterValue: MutableState<Pair<Long, String>>?,
    readOnly: Boolean = false,
    enabled: Boolean = true
) {

    var focusItem by remember { mutableStateOf(false) }
    val localFocusManager = LocalFocusManager.current
    var expandedLocal by remember { mutableStateOf(false) }
    var enteredText by remember { mutableStateOf("") }

    enteredText = if (enterValue != null && !focusItem) enterValue.value.second else ""

    ExposedDropdownMenuBox(
        expanded = expandedLocal && enabled,
        modifier = modifier,
        onExpandedChange = { expandedLocal = !expandedLocal }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(1f)
                .onFocusChanged { focusItem = it.isFocused },
            value = enteredText,
            singleLine = true,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = styleApp(nameStyle = TypeText.EDIT_TEXT),
            onValueChange = {
                enteredText = it
                focusItem = false
                enterValue!!.value = Pair(0, it)
                expandedLocal = true
            },
            label = { if (label != null)
                Text(text = label, style = styleApp(nameStyle = TypeText.EDIT_TEXT_TITLE)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLocal) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    localFocusManager.clearFocus()
                    enterValue!!.value = Pair(0, enteredText)
                    expandedLocal = false
                }
            ),
        )
        var filteringOptions = listItems
        if (filtering) filteringOptions =
            listItems.filter { it.second.contains(enteredText, ignoreCase = true) }
        if (filteringOptions.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expandedLocal && enabled,
                onDismissRequest = { expandedLocal = false }) {
                filteringOptions.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            enteredText = item.second
                            expandedLocal = false
                            enterValue!!.value = item
                            localFocusManager.clearFocus() },
                        text = {
                            Text(text = item.second, style = styleApp(nameStyle = TypeText.EDIT_TEXT))}
                    ) 
                }
            }
        } else expandedLocal = false
    }
}

@Composable
fun MyTextH1(text: String, modifier: Modifier, textAlign: TextAlign) {
    Text(
        text = text,
        style = styleApp(nameStyle = TypeText.TEXT_IN_LIST),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun MyTextH2(text: String, modifier: Modifier) {
    Text(
        text = text,
        style = styleApp(nameStyle = TypeText.TEXT_IN_LIST_SETTING),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Composable
fun TextButtonOK(onConfirm: () -> Unit, enabled: Boolean = true) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = onConfirm, enabled = enabled) {
            MyTextH2( stringResource(R.string.ok), Modifier)
        }
    }
}

@Composable
fun MyOutlinedTextFieldWithoutIcon(
    modifier: Modifier, enterValue: MutableState<String>, typeKeyboard: String
) {

    val localFocusManager = LocalFocusManager.current
    var enterText by remember { mutableStateOf(enterValue.value) }
    var keyboardOptions =
        KeyboardOptions(keyboardType = KeyboardType.Decimal).copy(imeAction = ImeAction.Done)
    if (typeKeyboard == "text") keyboardOptions =
        KeyboardOptions.Default.copy(imeAction = ImeAction.Done)

    OutlinedTextField(
        modifier = modifier,
        value = enterText,
        singleLine = true,
        textStyle = styleApp(nameStyle = TypeText.EDIT_TEXT),
        onValueChange = {
            enterText = it
            enterValue.value = it
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                localFocusManager.clearFocus()
                enterValue.value = enterText
//                keyboardController?.hide()
            }
        ),
//        leadingIcon = { enterText = onAddIconEditText(onNewArticle,enterText) },
//        trailingIcon = { enterText = onAddIconEditText(onNewArticle,enterText) }
    )
}

@Composable
fun MyOutlinedTextFieldWithoutIconClearing(
    modifier: Modifier,
    enterValue: MutableState<String>,
    typeKeyboard: String
) {

    val localFocusManager = LocalFocusManager.current
    var focusItem by remember { mutableStateOf(false) }
    var enterText by remember { mutableStateOf("") }
    enterText = if (!focusItem) enterValue.value else ""
//    val keyboardController = LocalSoftwareKeyboardController.current
    var keyboardOptions =
        KeyboardOptions(keyboardType = KeyboardType.Decimal).copy(imeAction = ImeAction.Done)
    if (typeKeyboard == "text") keyboardOptions =
        KeyboardOptions.Default.copy(imeAction = ImeAction.Done)

    OutlinedTextField(
        modifier = modifier.onFocusChanged { focusItem = it.isFocused },
        value = enterText,
        singleLine = true,
        textStyle = styleApp(nameStyle = TypeText.EDIT_TEXT),
        onValueChange = {
            focusItem = false
            enterText = it
            enterValue.value = it
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                localFocusManager.clearFocus()
                enterValue.value = enterText
//                keyboardController?.hide()
            }
        ),
//        leadingIcon = { enterText = onAddIconEditText(onNewArticle,enterText) },
//        trailingIcon = { enterText = onAddIconEditText(onNewArticle,enterText) }
    )
}

@Composable fun ButtonCircle( modifier: Modifier, iconButton: ImageVector, onClick: () -> Unit) {
    val radius = 25.dp
    IconButton (
        modifier = modifier
            .clip(RoundedCornerShape(radius, radius, radius, radius))
            .size(60.dp),
        onClick = { onClick() }) {
        Icon( imageVector = iconButton, null, tint = ButtonColorsMy , modifier = Modifier.size(60.dp))
    }
}



@Composable fun showFABs(startScreen: Boolean, isSelected: Boolean, modifier: Modifier, doDeleted: ()->Unit,
             doChangeSection: ()->Unit, doUnSelected:()->Unit):Boolean {
    var startScreenLocal = startScreen
    if (isSelected) {
        startScreenLocal = true
        ShowFABs_(show = true,
            modifier = modifier,
            doDeleted = doDeleted,
            doChangeSection = doChangeSection,
            doUnSelected = doUnSelected)
    } else if (startScreenLocal){
        ShowFABs_(show = false, doDeleted = {}, doChangeSection = {}, doUnSelected = {}, modifier = modifier)
    }
    return startScreenLocal
}

@Composable fun ShowFABs_(show: Boolean, modifier: Modifier, doDeleted: ()->Unit, doChangeSection: ()->Unit, doUnSelected:()->Unit){
    Box( modifier ) {
        FabAnimation(show = show, offset = 0.dp, icon = Icons.Filled.Delete, onClick = doDeleted)
        FabAnimation(show = show, offset = 64.dp, icon = Icons.Filled.Dns, onClick = doChangeSection)
        FabAnimation(show = show, offset = 128.dp, icon = Icons.Filled.RemoveDone, onClick = doUnSelected)
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun SwitcherButton(doChangeSorting: (SortingBy) -> Unit) {

    val cornerDp = 4.dp

    var sortingPosition by remember { mutableStateOf(true) }

    Row( verticalAlignment = Alignment.CenterVertically,  horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .background(color = Color.Transparent, shape = RoundedCornerShape(cornerDp))
            .clickable{
                if (sortingPosition) doChangeSorting(SortingBy.BY_SECTION) else doChangeSorting(SortingBy.BY_NAME)
                sortingPosition = !sortingPosition
            }
    ) {
        Text(
            text = stringResource(id = R.string.by_name),
            fontWeight = if (sortingPosition) FontWeight.Bold else FontWeight.Normal,
            style = styleApp(nameStyle = TypeText.TEXT_IN_LIST_SMALL)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = stringResource(id = R.string.by_section),
            fontWeight = if (!sortingPosition) FontWeight.Bold else FontWeight.Normal,
            style = styleApp(nameStyle = TypeText.TEXT_IN_LIST_SMALL)
        )
    }
}

@Composable
fun TextForSwitchingButton(text: String, frameSize: Dp, modifier: Modifier){
    Box(modifier = modifier.width(frameSize)){
        Text( text = text, style = styleApp(nameStyle = TypeText.TEXT_IN_LIST_SMALL),
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 4.dp)
                .align(alignment = Alignment.Center)
        )
    }
}

@Composable fun CircleMy(color: Color){
    Box(
        modifier = Modifier
            .size(size = 40.dp)
            .clip(shape = CircleShape)
            .background(color = color)
    ) {
    }
}


@Composable fun SliderApp(
    modifier: Modifier = Modifier,
    position: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    step: Int,
    direction: Direcions,
    onSelected: (Float)->Unit )
{
//    Slider(
//        modifier = modifier,
//        value = position,
//        valueRange = valueRange,
//        steps = step,
//        onValueChange = { onSelected(it)},
//        colors = SliderDefaults.colors(
//            thumbColor = Color(0xFF575757),
//            activeTrackColor = Color(0xFFA2A2A2),
//            inactiveTrackColor = Color(0xFFA2A2A2),
//            inactiveTickColor = Color(0xFFA2A2A2),
//            activeTickColor = Color(0xFFA2A2A2)
//        ))
    SliderApp1(
        modifier = modifier,
        position = position,
        valueRange = valueRange,
        step = step,
        direction = direction,
        onSelected = onSelected)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun SliderApp1(
    modifier: Modifier = Modifier,
    position: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    step: Int,
    direction: Direcions,
    onSelected: (Float)->Unit )
{
    val interactionSource = remember { MutableInteractionSource() }
    val colors =  SliderDefaults.colors(
                    thumbColor = Color(0xFF575757),
                    activeTrackColor = Color(0xFFA2A2A2),
                    activeTickColor = Color(0xFFA2A2A2),
                    inactiveTrackColor = Color(0xFFA2A2A2),
                    inactiveTickColor = Color(0xFFA2A2A2),
                    disabledThumbColor = Color.Transparent,
                    disabledActiveTrackColor = Color.Transparent,
                    disabledActiveTickColor = Color.Transparent,
                    disabledInactiveTrackColor = Color.Transparent,
                    disabledInactiveTickColor = Color.Transparent,
                )
    Slider(
        modifier = modifier,
        value = position,
        valueRange = valueRange,
        steps = step,
        onValueChange = { onSelected(it) },
        thumb = { thumbValue ->
            SliderDefaults.Thumb(
                modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = CustomTriangleShape(direction))
                    .drawWithContent {  },
                interactionSource = interactionSource,
                thumbSize = DpSize(34.dp,34.dp),
                colors = colors
            )
        },
//        track = { position ->
//            Box(
//                modifier = Modifier
////                    .border(
////                        width = 1.dp,
////                        color = Color.LightGray.copy(alpha = 0.4f),
////                        shape = RectangleShape
////                    )
//                    .background(Color.LightGray)
//                    .padding(3.5.dp),
//                contentAlignment = Alignment.CenterStart
//            ) {
//                Box( modifier = Modifier
//                        .background( brush = Brush.linearGradient(listOf(Color.LightGray, Color.Gray)))
//                )
//            }
//        }
    )
}
