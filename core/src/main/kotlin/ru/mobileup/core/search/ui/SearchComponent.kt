package ru.mobileup.core.search.ui

import me.aartikov.sesame.compose.form.control.InputControl

interface SearchComponent {

    val inputControl: InputControl

    var searchQuery: String

    fun onClearClick() = inputControl.onTextChanged("")
}