package ru.mobileup.core.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.aartikov.sesame.compose.form.control.InputControl
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.core.utils.resolve

@Composable
fun SmOutlinedTextField(
    inputControl: InputControl,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    textFieldHeight: Dp = Dp.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = inputControl.visualTransformation,
    header: String? = null
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.bringIntoViewRequester(bringIntoViewRequester)
    ) {
        val focusRequester = remember { FocusRequester() }

        SideEffect {
            if (inputControl.hasFocus) {
                focusRequester.requestFocus()
            }
        }

        LaunchedEffect(key1 = inputControl) {
            inputControl.scrollToItEvent.collectLatest {
                bringIntoViewRequester.bringIntoView()
            }
        }
        header?.let {
            Text(
                text = it,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(textFieldHeight)
                .focusRequester(focusRequester)
                .onFocusEvent { focusState ->
                    if (focusState.isFocused) {
                        coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                    }
                }
                .onFocusChanged { inputControl.onFocusChanged(it.isFocused) },
            value = inputControl.text,
            onValueChange = inputControl::onTextChanged,
            isError = inputControl.error != null,
            visualTransformation = visualTransformation,
            placeholder = {
                Text(placeholder)
            },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            shape = MaterialTheme.shapes.medium,
            singleLine = inputControl.singleLine,
            maxLines = maxLines,
            keyboardOptions = inputControl.keyboardOptions,
            enabled = inputControl.enabled,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                placeholderColor = MaterialTheme.colors.onSurface
            )
        )
        ErrorText(inputControl.error?.resolve())
    }
}

@Composable
fun ErrorText(
    errorText: String?,
    modifier: Modifier = Modifier
) {
    errorText?.let {
        Text(
            modifier = modifier.padding(top = 8.dp),
            text = it,
            color = MaterialTheme.colors.error
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SmOutlinedTextFieldPreview() {
    val inputControl = InputControl(
        initialText = "initialText",
        singleLine = false,
        maxLength = 16,
        keyboardOptions = KeyboardOptions.Default,
    )
    AppTheme {
        SmOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            inputControl = inputControl,
            placeholder = "Placeholder"
        )
    }
}