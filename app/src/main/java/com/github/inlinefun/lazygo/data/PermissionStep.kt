package com.github.inlinefun.lazygo.data

import androidx.compose.ui.graphics.vector.ImageVector

data class PermissionStep(
    val titleId: Int,
    val descriptionId: Int,
    val buttonLabel: Int,
    val icon: ImageVector,
    val action: () -> Unit,
    val requirementSatisfied: () -> Boolean,
)
