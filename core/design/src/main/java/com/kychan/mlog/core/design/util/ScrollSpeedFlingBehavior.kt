package com.kychan.mlog.core.design.util

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlin.math.abs

/**
 * limitVelocity 의 값이 클 수록 스크롤이 빨라집니다.
 * Custom GridView가 생각보다 무거워 스크롤이 너무 빠르면 컴포넌트를 생성하면서 Main Thread(UI Thread)를 많이 잡고있어
 * 의도치 않는 현상이 발생하여 스크롤 속도를 조절하는 maxScrollFlingBehavior를 만들었습니다.
 *
 * @param limitVelocity 값이 크면 스크롤 속도가 빨라집니다.
 * **/
@Composable
fun maxScrollFlingBehavior(limitVelocity: Float = 15000F): FlingBehavior {
    val flingSpec = rememberSplineBasedDecay<Float>()
    return remember(flingSpec) {
        ScrollSpeedFlingBehavior(flingSpec, limitVelocity)
    }
}

class ScrollSpeedFlingBehavior(
    private val flingDecay: DecayAnimationSpec<Float>,
    private val limitVelocity: Float,
) : FlingBehavior {
    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
        // 매우 빠른 스크롤을 막기 위한 것
        val newVelocity = if (initialVelocity > 0F) minOf(initialVelocity, limitVelocity)
        else maxOf(initialVelocity, -limitVelocity)

        return if (abs(newVelocity) > 1f) {
            var velocityLeft = newVelocity
            var lastValue = 0f
            AnimationState(
                initialValue = 0f,
                initialVelocity = newVelocity,
            ).animateDecay(flingDecay) {
                val delta = value - lastValue
                val consumed = scrollBy(delta)
                lastValue = value
                velocityLeft = this.velocity
                // 반올림 오류를 방지하고 사용하지 않는 항목이 있으면 중지합니다
                if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
            }
            velocityLeft
        } else newVelocity
    }
}