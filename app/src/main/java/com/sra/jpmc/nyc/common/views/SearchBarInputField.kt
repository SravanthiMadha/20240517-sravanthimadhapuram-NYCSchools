package com.sra.jpmc.nyc.common.views

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.sra.jpmc.nyc.R
import com.sra.jpmc.nyc.ui.theme.Typography

/**
 * This will be use for Serch input field
 */
@Composable
fun SearchBarInputField(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val focusRequester = remember { FocusRequester() }
    val searchSemantics = "SearchBarSearch"
    val suggestionsAvailableSemantics = "SuggestionsAvailable"
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = colorResource(id = R.color.white), // Get color from colors.xml

    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = modifier
                .focusRequester(focusRequester)
                .onFocusChanged { if (it.isFocused) onActiveChange(true) }
                .semantics {
                    contentDescription = searchSemantics
                    if (active) {
                        stateDescription = suggestionsAvailableSemantics
                    }
                    onClick {
                        focusRequester.requestFocus()
                        true
                    }
                }
                .background(colorResource(id = R.color.purple_700))
                .padding(10.dp),

            enabled = enabled,
            singleLine = true,
            textStyle = Typography.h5,
            cursorBrush = SolidColor(Color.Gray),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch(query) }),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (query.isEmpty()) {
                        Text(text = "Search", color = Color.Gray) // Set hint text here
                    }
                    innerTextField()
                }
            }
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}
