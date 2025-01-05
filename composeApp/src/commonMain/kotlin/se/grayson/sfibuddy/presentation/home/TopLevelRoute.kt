package se.grayson.sfibuddy.presentation.home

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

data class TopLevelRoute<T>(val name: StringResource, val route: T, val icon: ImageVector)
