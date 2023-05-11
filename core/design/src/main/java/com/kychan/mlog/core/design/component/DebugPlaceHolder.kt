package com.kychan.mlog.core.design.component

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource

/**
 * LocalInspectionMode.current는 미리보기 모드에 있는지 확인하기 위해서 사용이 됩니다.
 * 참조 : https://developer.android.com/reference/kotlin/androidx/compose/ui/platform/package-summary#LocalInspectionMode()
 */
@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) = if (LocalInspectionMode.current) {
    painterResource(id = debugPreview)
} else {
    null
}