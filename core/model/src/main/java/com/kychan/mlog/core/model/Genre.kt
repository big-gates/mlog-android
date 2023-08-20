package com.kychan.mlog.core.model

enum class  Genre(val id: Int, val kr: String, val en: String) {
    Action1(28,"액션", "Action"),
    Action2(10759,"액션", "Action"),
    Adventure1(12,"모험","Adventure"),
    Adventure2(10759,"모험","Adventure"),
    Comedy(35,"코미디","Comedy"),
    Drama(18,"드라마","Drama"),
    Fantasy1(14,"판타지","Fantasy"),
    Fantasy2(10765,"판타지","Fantasy"),
    Family(10751,"가족","Family"),
    History(36,"역사","History"),
    Horror(27,"공포","Horror"),
    Music(10402,"음악","Music"),
    Mystery(9648,"미스터리","Mystery"),
    Romance(10749,"로맨스","Romance"),
    ScienceFiction1(878,"SF","Science Fiction"),
    ScienceFiction2(10765,"SF","Science Fiction"),
    TVMovie(10770,"TV 영화","TV Movie"),
    Thriller(53,"스릴러","Thriller"),
    War1(10752,"전쟁","War"),
    War2(10768,"전쟁","War"),
    Western(37,"서부","Western"),
    Animation(16,"애니메이션","Animation"),
    Crime(80,"범죄","Crime"),
    Documentary(99,"다큐멘터리","Documentary"),
    Kids(10762,"어린이","Kids"),
    News(10763,"시사","News"),
    Reality(10764,"리얼리티","Reality"),
    SoapOpera(10766,"연속극","Soap Opera"),
    Talk(10767,"대화","Talk"),
    Politics(10768,"정치","Politics"),
}

/**
 * API TEST를 위한 임시 데이터 모델
 */
data class Genre2(
    val id: Int,
    val name: String
)