package com.kychan.mlog.core.model

enum class Genre(val id: List<Int>,val kr: String, val en: String) {
    Action(listOf(28,10759),"액션", "Action"),
    Adventure(listOf(12,10759),"모험","Adventure"),
    Comedy(listOf(35),"코미디","Comedy"),
    Drama(listOf(18),"드라마","Drama"),
    Fantasy(listOf(14,10765),"판타지","Fantasy"),
    Family(listOf(10751),"가족","Family"),
    History(listOf(36),"역사","History"),
    Horror(listOf(27),"공포","Horror"),
    Music(listOf(10402),"음악","Music"),
    Mystery(listOf(9648),"미스터리","Mystery"),
    Romance(listOf(10749),"로맨스","Romance"),
    ScienceFiction(listOf(878,10765),"SF","Science Fiction"),
    TVMovie(listOf(10770),"TV 영화","TV Movie"),
    Thriller(listOf(53),"스릴러","Thriller"),
    War(listOf(10752,10768),"전쟁","War"),
    Western(listOf(37),"서부","Western"),
    Animation(listOf(16),"애니메이션","Animation"),
    Crime(listOf(80),"범죄","Crime"),
    Documentary(listOf(99),"다큐멘터리","Documentary"),
    Kids(listOf(10762),"어린이","Kids"),
    News(listOf(10763),"시사","News"),
    Reality(listOf(10764),"리얼리티","Reality"),
    SoapOpera(listOf(10766),"연속극","Soap Opera"),
    Talk(listOf(10767),"대화","Talk"),
    Politics(listOf(10768),"정치","Politics"),
}

/**
 * API TEST를 위한 임시 데이터 모델
 */
data class Genre2(
    val id: Int,
    val name: String
)