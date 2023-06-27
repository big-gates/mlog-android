package com.kychan.mlog.feature.mypage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kychan.mlog.core.design.theme.White

enum class SortType(val title: String) {
    SAVE_RECENT("최근에 담은 순"),
    SAVE_OLD("담은 지 오래된 순"),
    MY_SCORE_HIGH("내 별점 높은 순"),
    MY_SCORE_LOW("내 별점 낮은 순"),
    AVERAGE_SCORE_HIGH("평균 별점 높은 순"),
    AVERAGE_SCORE_LOW("평균 별점 낮은 순"),
}

@Composable
fun StorageSortBottomSheetContent(
    isRatePage: Boolean = true,
    clickSortType: (type: SortType) -> Unit,
) {
    val orderTypeNameList = SortType.values()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 15.dp)
    ) {
        orderTypeNameList.forEach { sortType ->
            if (!isRatePage && (sortType == SortType.MY_SCORE_HIGH || sortType == SortType.MY_SCORE_LOW)) return@forEach
            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                    ) {
                        clickSortType(sortType)
                    },
                text = sortType.title,
                color = White
            )
        }
    }
}