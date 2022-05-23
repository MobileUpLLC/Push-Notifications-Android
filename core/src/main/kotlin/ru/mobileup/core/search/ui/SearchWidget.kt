package ru.mobileup.core.search.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import me.aartikov.sesame.compose.form.control.InputControl
import ru.mobileup.core.R
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.core.widget.SmActionButton
import ru.mobileup.core.widget.SmOutlinedTextField

@Composable
fun SearchWidget(
    component: SearchComponent,
    modifier: Modifier = Modifier,
    placeholderText: String = ""
) {
    val isNotEmptyText = component.inputControl.text.isNotEmpty()

    SmOutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        inputControl = component.inputControl,
        placeholder = placeholderText,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_24_search),
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface
            )
        },
        trailingIcon = if (isNotEmptyText) {
            {
                SmActionButton(
                    id = R.drawable.ic_24_close,
                    onClick = component::onClearClick,
                    tint = MaterialTheme.colors.onBackground
                )
            }
        } else {
            null
        }
    )
}

class FakeSearchComponent : SearchComponent {

    override val inputControl = InputControl(
        singleLine = true,
        maxLength = 20,
        keyboardOptions = KeyboardOptions.Default
    )

    override var searchQuery: String = ""
}

@Preview(showBackground = true)
@Composable
fun SearchWidgetPreview() {
    AppTheme {
        SearchWidget(component = FakeSearchComponent(), placeholderText = "Search")
    }
}