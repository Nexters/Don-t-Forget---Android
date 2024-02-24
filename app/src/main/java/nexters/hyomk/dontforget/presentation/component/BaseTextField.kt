package nexters.hyomk.dontforget.presentation.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nexters.hyomk.dontforget.ui.theme.Gray700
import nexters.hyomk.dontforget.ui.theme.Gray800
import nexters.hyomk.dontforget.ui.theme.Primary500
import nexters.hyomk.dontforget.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun BaseTextField(
    value: String,
    hint: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(color = White),
    helperTextEnabled: Boolean = false,
    helperText: String = "",
    counterMaxLength: Int = 0,
    enabled: Boolean = true,
    isError: Boolean = false,
    isSingleLine: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val DividerMargin = 17.dp
    val TextFieldMaxHeight = with(LocalDensity.current) {
        24.sp.toDp()
    } + DividerMargin
    val TextFieldMinHeight = with(LocalDensity.current) {
        24.sp.toDp()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val isFocused by interactionSource.collectIsFocusedAsState()

    val baseModifier =
        Modifier.fillMaxWidth().heightIn(min = TextFieldMinHeight, max = TextFieldMaxHeight).padding(horizontal = 17.dp)

    val customTextSelectionColors = TextSelectionColors(
        handleColor = Primary500,
        backgroundColor = Color.Transparent,
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        Column {
            BasicTextField(
                modifier = modifier,
                value = value,
                onValueChange = {
                    val newValue = if (counterMaxLength > 0) {
                        it.take(counterMaxLength)
                    } else {
                        it
                    }
                    onValueChange(newValue)
                },

                enabled = enabled,
                textStyle = textStyle.copy(textAlign = TextAlign.Center),
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    },
                ),
                singleLine = isSingleLine,
                interactionSource = interactionSource,
                visualTransformation = visualTransformation,
                cursorBrush = SolidColor(Primary500),
                decorationBox = @Composable { innerTextField ->
                    Surface(
                        modifier = baseModifier,
                        color = backgroundColor,
                        contentColor = White,
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            if (!isFocused && value.isEmpty()) {
                                Text(

                                    text = hint,
                                    color = Gray700,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                            innerTextField()
                        }
                    }
                },
            )

            if (helperTextEnabled) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    text = helperText,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = White,
                )
            }
            Divider(color = if (isFocused) Primary500 else Gray800, modifier = Modifier.padding(top = 17.dp))
        }
    }
}

@Preview
@Composable
fun PreviewBaseTextField() {
    BaseTextField(value = "hi", onValueChange = { s -> })
}
