package com.music.echo.ui.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp

fun Modifier.tvFocusable(
    shape: Shape = RoundedCornerShape(12.dp),
    borderColor: Color? = null,
    scaleOnFocus: Float = 1.06f,
    onClick: (() -> Unit)? = null
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val scale by animateFloatAsState(if (isFocused) scaleOnFocus else 1.0f, label = "tv_focus")
    val ring = borderColor ?: MaterialTheme.colorScheme.primary
    this
        .scale(scale)
        .then(if (isFocused) Modifier.border(2.5.dp, ring, shape) else Modifier)
        .onKeyEvent { ev ->
            if (onClick != null && ev.type == KeyEventType.KeyUp &&
                (ev.key == Key.DirectionCenter || ev.key == Key.Enter || ev.key == Key.NumPadEnter)
            ) { onClick(); true } else false
        }
        .then(
            if (onClick != null) Modifier.clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
            else Modifier.focusable(interactionSource = interactionSource)
        )
}
