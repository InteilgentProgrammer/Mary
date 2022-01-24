package com.start.mary

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect


//--------------------------------------------------------------------------------------------------

@Stable
interface MaryPaddingValues {

    fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp
    fun calculateTopPadding(): Dp
    fun calculateRightPadding(layoutDirection: LayoutDirection): Dp
    fun calculateBottomPadding(): Dp
    @Immutable
    class Absolute(
        @Stable
        private val left: Dp = 0.dp,
        @Stable
        private val top: Dp = 0.dp,
        @Stable
        private val right: Dp = 0.dp,
        @Stable
        private val bottom: Dp = 0.dp
    ) : MaryPaddingValues {
        override fun calculateLeftPadding(layoutDirection: LayoutDirection) = left

        override fun calculateTopPadding() = top

        override fun calculateRightPadding(layoutDirection: LayoutDirection) = right

        override fun calculateBottomPadding() = bottom

        override fun equals(other: Any?): Boolean {
            if (other !is Absolute) return false
            return left == other.left &&
                    top == other.top &&
                    right == other.right &&
                    bottom == other.bottom
        }

        override fun hashCode() =
            ((left.hashCode() * 31 + top.hashCode()) * 31 + right.hashCode()) *
                    31 + bottom.hashCode()

        override fun toString() =
            "PaddingValues.Absolute(left=$left, top=$top, right=$right, bottom=$bottom"
    }
}

//--------------------------------------------------------------------------------------------------

@Stable
interface MaryButtonElevation {
    /**
     * Represents the elevation used in a button, depending on [enabled] and
     * [interactionSource].
     *
     * @param enabled whether the button is enabled
     * @param interactionSource the [InteractionSource] for this button
     */
    @Composable
    fun elevation(enabled: Boolean, interactionSource: InteractionSource): State<Dp>
}
//--------------------------------------------------------------------------------------------------

@Stable
interface MaryButtonColors {
    @Composable
    fun backgroundColor(enabled: Boolean): State<Color>

    @Composable
    fun contentColor(enabled: Boolean): State<Color>
}

//--------------------------------------------------------------------------------------------------
@Immutable
private class MaryDefaultButtonColors(
    private val backgroundColor: Color,
    private val contentColor: Color,
    private val disabledBackgroundColor: Color,
    private val disabledContentColor: Color
) : MaryButtonColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) backgroundColor else disabledBackgroundColor)
    }

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) contentColor else disabledContentColor)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as MaryDefaultButtonColors
        if (backgroundColor != other.backgroundColor) return false
        if (contentColor != other.contentColor) return false
        if (disabledBackgroundColor != other.disabledBackgroundColor) return false
        if (disabledContentColor != other.disabledContentColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = backgroundColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + disabledBackgroundColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        return result
    }
}

//--------------------------------------------------------------------------------------------------
private val MaryDefaultIncomingSpec =
    TweenSpec<Dp>(durationMillis = 120, easing = FastOutSlowInEasing)

//--------------------------------------------------------------------------------------------------
private val MaryDefaultOutgoingSpec =
    TweenSpec<Dp>(durationMillis = 150, easing = CubicBezierEasing(0.40f, 0.00f, 0.60f, 1.00f))

//--------------------------------------------------------------------------------------------------
private object MaryElevationDefaults {
    fun incomingAnimationSpecForInteraction(interaction: Interaction): AnimationSpec<Dp>? {
        return when (interaction) {
            is PressInteraction.Press -> MaryDefaultIncomingSpec
            is DragInteraction.Start -> MaryDefaultIncomingSpec
            else -> null
        }
    }

    fun outgoingAnimationSpecForInteraction(interaction: Interaction): AnimationSpec<Dp>? {
        return when (interaction) {
            is PressInteraction.Press -> MaryDefaultOutgoingSpec
            is DragInteraction.Start -> MaryDefaultOutgoingSpec
            // TODO: use [HoveredOutgoingSpec] when hovered
            else -> null
        }
    }
}

//--------------------------------------------------------------------------------------------------
internal suspend fun Animatable<Dp, *>.maryAnimateElevation(
    target: Dp,
    from: Interaction? = null,
    to: Interaction? = null
) {
    val spec = when {
        // Moving to a new state
        to != null -> MaryElevationDefaults.incomingAnimationSpecForInteraction(to)
        // Moving to default, from a previous state
        from != null -> MaryElevationDefaults.outgoingAnimationSpecForInteraction(from)
        // Loading the initial state, or moving back to the baseline state from a disabled /
        // unknown state, so just snap to the final value.
        else -> null
    }
    if (spec != null) animateTo(target, spec) else snapTo(target)
}

//--------------------------------------------------------------------------------------------------
@Stable
private class MaryDefaultButtonElevation(
    private val defaultElevation: Dp,
    private val pressedElevation: Dp,
    private val disabledElevation: Dp,
) : MaryButtonElevation {
    @Composable
    override fun elevation(enabled: Boolean, interactionSource: InteractionSource): State<Dp> {
        val interactions = remember { mutableStateListOf<Interaction>() }
        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> {
                        interactions.add(interaction)
                    }
                    is PressInteraction.Release -> {
                        interactions.remove(interaction.press)
                    }
                    is PressInteraction.Cancel -> {
                        interactions.remove(interaction.press)
                    }
                }
            }
        }

        val interaction = interactions.lastOrNull()

        val target = if (!enabled) {
            disabledElevation
        } else {
            when (interaction) {
                is PressInteraction.Press -> pressedElevation
                else -> defaultElevation
            }
        }

        val animatable: Animatable<Dp, AnimationVector1D> =
            remember { Animatable(target, Dp.VectorConverter) }

        if (!enabled) {
            LaunchedEffect(target) {
                animatable.snapTo(target)
            }
        } else {
            LaunchedEffect(target) {
                val lastInteraction = when (animatable.targetValue) {
                    pressedElevation -> PressInteraction.Press(Offset.Zero)
                    else -> null
                }
                animatable.maryAnimateElevation(
                    from = lastInteraction,
                    to = interaction,
                    target = target
                )
            }
        }

        return animatable.asState()
    }
}

//--------------------------------------------------------------------------------------------------
object MaryButtonDefaults {
    private val ButtonHorizontalPadding = 16.dp
    private val ButtonVerticalPadding = 8.dp
    val ContentPadding = PaddingValues(
        start = ButtonHorizontalPadding,
        top = ButtonVerticalPadding,
        end = ButtonHorizontalPadding,
        bottom = ButtonVerticalPadding
    )
    val MinWidth = 64.dp
    val MinHeight = 36.dp
    val IconSize = 18.dp
    val IconSpacing = 8.dp

    @Composable
    fun elevation(
        defaultElevation: Dp = 2.dp,
        pressedElevation: Dp = 8.dp,
        disabledElevation: Dp = 0.dp
    ): MaryButtonElevation {
        return remember(defaultElevation, pressedElevation, disabledElevation) {
            MaryDefaultButtonElevation(
                defaultElevation = defaultElevation,
                pressedElevation = pressedElevation,
                disabledElevation = disabledElevation
            )
        }
    }

    @Composable
    fun buttonColors(
        backgroundColor: Color = MaterialTheme.colors.primary,
        contentColor: Color = contentColorFor(backgroundColor),
        disabledBackgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
            .compositeOver(MaterialTheme.colors.surface),
        disabledContentColor: Color = MaterialTheme.colors.onSurface
            .copy(alpha = ContentAlpha.disabled)
    ): MaryButtonColors = MaryDefaultButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor
    )

    @Composable
    fun outlinedButtonColors(
        backgroundColor: Color = MaterialTheme.colors.surface,
        contentColor: Color = MaterialTheme.colors.primary,
        disabledContentColor: Color = MaterialTheme.colors.onSurface
            .copy(alpha = ContentAlpha.disabled)
    ): MaryButtonColors = MaryDefaultButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = backgroundColor,
        disabledContentColor = disabledContentColor
    )

    @Composable
    fun textButtonColors(
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = MaterialTheme.colors.primary,
        disabledContentColor: Color = MaterialTheme.colors.onSurface
            .copy(alpha = ContentAlpha.disabled)
    ): MaryButtonColors = MaryDefaultButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = backgroundColor,
        disabledContentColor = disabledContentColor
    )

    const val OutlinedBorderOpacity = 0.12f
    val OutlinedBorderSize = 1.dp
    val outlinedBorder: BorderStroke
        @Composable
        get() = BorderStroke(
            OutlinedBorderSize, MaterialTheme.colors.onSurface.copy(alpha = OutlinedBorderOpacity)
        )
    private val TextButtonHorizontalPadding = 8.dp
    val TextButtonContentPadding = PaddingValues(
        start = TextButtonHorizontalPadding,
        top = ContentPadding.calculateTopPadding(),
        end = TextButtonHorizontalPadding,
        bottom = ContentPadding.calculateBottomPadding()
    )
}

//--------------------------------------------------------------------------------------------------
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
   /**/ elevation: MaryButtonElevation? = MaryButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    /**/colors: MaryButtonColors = MaryButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = MaryButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val contentColor by colors.contentColor(enabled)
    Surface(
        modifier = modifier,
        shape = shape,
        color = colors.backgroundColor(enabled).value,
        contentColor = contentColor.copy(alpha = 1f),
        border = border,
        elevation = elevation?.elevation(enabled, interactionSource)?.value ?: 0.dp,
        onClick = onClick,
        enabled = enabled,
        role = Role.Button,
        interactionSource = interactionSource,
        indication = rememberRipple()
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(
                value = MaterialTheme.typography.button
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = ButtonDefaults.MinHeight
                        )
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}

//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------

@Composable
fun MaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: MaryButtonElevation? = MaryButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: MaryButtonColors = MaryButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = MaryButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    BaryButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding, content = content
    )
}
//--------------------------------------------------------------------------------------------------
