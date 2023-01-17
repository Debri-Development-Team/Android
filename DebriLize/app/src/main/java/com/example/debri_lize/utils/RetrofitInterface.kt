package com.example.debri_lize.utils

import com.example.debri_lize.base.BaseResponse
import com.example.debri_lize.data.auth.*
import com.example.debri_lize.data.board.Board
import com.example.debri_lize.data.board.BoardFavorite
import com.example.debri_lize.data.class_.*
import com.example.debri_lize.data.curriculum.*
import com.example.debri_lize.data.post.*
import com.example.debri_lize.data.post.Comment
import com.example.debri_lize.data.post.Post
import com.example.debri_lize.data.post.PostDetail
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {

    //5.1 jwt ACCESS-TOKEN 갱신 api
    @PATCH("api/jwt/refresh")
    fun token(@Header("ACCESS-TOKEN") authToken: String, @Body refreshToken : String): Call<BaseResponse<Token>>

    //1.1 회원가입 api
    @POST("api/user/signUp")
    fun signUp(@Body user: UserSignup): Call<BaseResponse<User>>

    //1.2 로그인 api
    @POST("api/auth/login")
    fun login(@Body user : UserLogin): Call<BaseResponse<User>>

    //1.5 이메일 인증 api
    @POST("api/auth/authEmail")
    fun getCode(@Body emailAddress : String) : Call<BaseResponse<Email>>

    //2.1 게시판 즐겨찾기 생성 api
    @POST("api/board/scrap/{boardIdx}")
    fun createScrapBoard(@Path("boardIdx") boardIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //2.2 게시판 즐겨찾기 해제 api
    @PATCH("api/board/scrap/cancel/{boardIdx}")
    fun cancelScrapBoard(@Path("boardIdx") boardIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //2.3 게시판 즐겨찾기 한 리스트 조회 api
    @GET("api/board/scrap/getList")
    fun showScrapBoardList(@Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<BoardFavorite>>>

    //2.4 게시판 즐겨찾기 안한 리스트 조회 api
    @GET("api/board/unscrap/getList")
    fun showUnScrapBoardList(@Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<Board>>>

    //2.5 전체 게시판 리스트 조회 api
    @GET("api/board/allList")
    fun showBoardList(@Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<Board>>>

    //3.1 게시물 생성 api
    @POST("api/post/create")
    fun createPost(@Body post: Post, @Header("ACCESS-TOKEN") authToken: String): Call<BaseResponse<Post>>

    //3.2 게시물 수정 api
    @PATCH("api/post/{postIdx}")
    fun editPost(@Body editPost : EditPost, @Path("postIdx") postIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //3.3 게시물 삭제 api
    @PATCH("api/post/{postIdx}/status")
    fun deletePost(@Path("postIdx") postIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //3.4 게시물 좋아요 생성 api
    @POST("api/post/like")
    fun createPostLike(@Body postLikeCreate: PostLikeCreate, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //3.5 게시물 좋아요 취소 api
    @PATCH("api/post/like/cancel")
    fun cancelPostLike(@Body postLikeCancel: PostLikeCancel, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //3.7 [특정 게시판] 게시물 리스트 조회 api
    @GET("api/post/getList/{boardIdx}/{pageNum}")
    fun showEachPostList(@Path("boardIdx") boardIdx: Int, @Path("pageNum") pageNum: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<PostInfo>>

    //3.7.1 [전체 범위(키워드 검색)] 게시물 리스트 조회 api
    @POST("api/post/getSearchList")
    fun showPostList(@Body searchPost : SearchPost, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<PostInfo>>

    //3.8 게시물 조회 api
    @GET("api/post/get/{postIdx}")
    fun showPostDetail(@Path("postIdx") postIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<PostDetail>>

    //3.9 게시물 스크랩 설정 api
    @POST("api/post/scrap/{postIdx}")
    fun createPostScrap(@Path("postIdx") postIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //3.9.1 게시물 스크랩 해제 api
    @POST("api/post/unscrap/{postIdx}")
    fun cancelPostScrap(@Path("postIdx") postIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //3.9.2 스크랩 게시물 조회 api
    @GET("api/post/getMyScrap/{pageNum}")
    fun showScrapPostList(@Path("pageNum") pageNum: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<PostList>>>

    //4.1 게시물 댓글 작성 api
    @POST("api/comment/replyOnPost/create")
    fun createComment(@Body comment: Comment, @Header("ACCESS-TOKEN") authToken: String): Call<BaseResponse<Comment>>

    //4.2 대댓글 작성 api
    @POST("api/comment/replyOnReply/create")
    fun createCocomment(@Body cocomment: Cocomment, @Header("ACCESS-TOKEN") authToken: String): Call<BaseResponse<Comment>>

    //4.3 댓글/대댓글 조회 api
    @GET("api/comment/get/{postIdx}")
    fun showComment(@Path("postIdx") postIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<CommentList>>>

    //4.4 댓글, 대댓글 삭제
    @PATCH("api/comment/delete/{commentIdx}")
    fun deleteComment(@Path("commentIdx") commentIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<Comment>>

    //4.6 댓글 좋아요 생성
    @POST("api/comment/like/create/{commentIdx}")
    fun createCommentLike(@Path("commentIdx") commentIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<createCommentLike>>

    //4.7 댓글 좋아요 삭제
    @PATCH("api/comment/like/delete/{commentIdx}")
    fun deleteCommentLike(@Path("commentIdx") commentIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<deleteCommentLike>>

    //6.1 게시물 신고 api
    @POST("api/report/postReport")
    fun reportPost(@Body report: ReportPost, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //6.2 댓글, 대댓글 신고 api
    @POST("api/report/commentReport")
    fun reportComment(@Body report: ReportComment, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //6.3 사용자 신고 & 차단 api
    @POST("api/report/user/{postIdx}")
    fun reportUser(@Body reason: String, @Path("postIdx") postIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //7.2 강의 즐겨찾기 api
    @POST("api/lecture/scrap/create")
    fun createLectureScrap(@Body lectureScrap: LectureScrap, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //7.2.1 강의 즐겨찾기 삭제 api
    @PATCH("api/lecture/scrap/delete")
    fun cancelLectureScrap(@Body lectureScrap: LectureScrap, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //7.3 즐겨찾기 한 강의 리스트 조회 api
    @GET("api/lecture/getScrapList/{userIdx}")
    fun showLectureFavorite(@Path("userIdx") userIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<Lecture>>>

    //7.4 강의 상세 조회 api
    @GET("api/lecture/getLecture/{lectureIdx}")
    fun showLectureDetail(@Path("lectureIdx") lectureIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<Lecture>>

    //7.4.1 강의 검색 api
    @GET("api/lecture/search")
    fun showLectureSearch(@Query ("lang") lang:String?,@Query ("type") type:String?,@Query ("price") price:String?,@Query ("key") key:String?, @Query ("pageNum") pageNum: Int?, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<SearchLecture>>

    //7.5 로드맵 리스트 조회 api
    @GET("api/lecture/roadmap/list")
    fun showRoadMapList(@Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<RoadMapList>>>

    //7.5.1 로드맵 상세 조회 api
    @GET("api/lecture/roadmap/view")
    fun showRoadMapDetail(@Query ("mod") mod:String?, @Header("ACCESS-TOKEN") authToken: String) :Call<BaseResponse<List<RoadMap>>>

    //7.5.2 로드맵 to 커리큘럼 api
    @POST("api/lecture/roadmap/copy")
    fun copyRoadmapToCurri(@Body copyRoadMap : copyRoadMap, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<CurriIdx>>

    //7.6 강의 리뷰 작성 api
    @POST("api/lecture/review/create")
    fun createLectureReview(@Body lectureReview : LectureReview, @Header("ACCESS-TOKEN") authToken: String) :Call<BaseResponse<LectureReview>>

    //7.6.1 강의 리뷰 조회 api
    @GET("api/lecture/review/get/{pageNum}")
    fun showLectureReview(@Path ("pageNum") pageNum: Int, @Query ("lectureIdx") lectureIdx:Int?, @Header("ACCESS-TOKEN") authToken: String) :Call<BaseResponse<ShowLectureReview>>

    //7.7 강의 좋아요 api
    @POST("api/lecture/like/create")
    fun createLectureLike(@Query ("lectureIdx") lectureIdx:Int?, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<LikeSuccess>>

    //7.7.1 강의 좋아요 삭제 api
    @PATCH("api/lecture/like/delete")
    fun deleteLectureLike(@Query ("lectureIdx") lectureIdx:Int?, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<LikeSuccess>>

    //8.1 커리큘럼 생성 api
    @POST("api/curri/create")
    fun createCurriculum(@Body newCurriculum : NewCurriculum, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<CurriIdx>>

    //8.2 커리큘럼 리스트 조회 api : 내가 추가한 커리큘럼들
    @GET("api/curri/getList")
    fun myCurriculumList(@Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<Curriculum>>>

    //8.3 커리큘럼 상세 조회 api : 홈
    @GET("api/curri/getThisCurri/{curriIdx}")
    fun showCurriculumDetail(@Path("curriIdx") curriIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<CurriculumDetail>>

    //8.4.1 커리큘럼 제목 수정 api
    @PATCH("api/curri/modify/name")
    fun editCurriculumName(@Body editCurriculumName : EditCurriculumName, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //8.4.2 커리큘럼 공유 상태 수정 api
    @PATCH("api/curri/modify/visibleStatus")
    fun editCurriculumVisible(@Body editCurriculumVisible : EditCurriculumVisible, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //8.4.3 커리큘럼 활성 상태 수정 api
    @PATCH("api/curri/modify/status")
    fun editCurriculumStatus(@Body editCurriculumStatus : EditCurriculumStatus, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //8.5 강의자료 추가 api : 홈 > 새로운 강의자료 추가하기
    @POST("api/curri/insertLecture")
    fun addLectureInCurriculum(@Body addLecture: AddLecture, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //8.6 커리큘럼 삭제 api
    @PATCH("api/curri/delete/{curriIdx}")
    fun deleteCurriculum(@Path("curriIdx") curriIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //8.7 챕터 수강 완료 및 취소 api
    @PATCH("api/curri/chapter/status")
    fun completeChapter(@Body completeChapter : CompleteChapter, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //8.8 커리큘럼 좋아요(추천) 생성 api
    @POST("api/curri/scrap/{curriIdx}")
    fun createCurriLike(@Path("curriIdx") curriIdx:Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<CurriculumLike>>

    //8.9 커리큘럼 좋아요(추천) 취소 api
    @PATCH("api/curri/unScrap/{scrapIdx}")
    fun cancelCurriLike(@Path("scrapIdx") scrapIdx:Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //8.10 커리큘럼 좋아요(추천) 리스트 조회 api
    @GET("api/curri/getScrapList")
    fun showScrapCurriList(@Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<Curriculum>>>

    //8.10.1 커리큘럼 좋아요(추천) TOP 10 리스트 조회 api
    @GET("api/curri/scrap/topList")
    fun showTop10List(@Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<Top10>>>

    //8.11 커리큘럼 리셋 api
    @PATCH("api/curri/reset/{curriIdx}")
    fun resetCurriculum(@Path("curriIdx") curriIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //8.12 커리큘럼 리뷰 작성 api
    @POST("api/curri/review/create")
    fun createReview(@Body review : Review, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<Review>>

    //8.12.1 커리큘럼 리뷰 조회 api
    @GET("api/curri/review/getList/{curriIdx}")
    fun showReview(@Path("curriIdx") curriIdx: Int, @Query("pageNum") pageNum: Int?, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<ShowReview>>

    //8.13 커리큘럼 복붙 api
    @POST("api/curri/copy")
    fun copyCurriculum(@Body copyCurriculum : CopyCurriculum, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<Copy>>

    //8.14 최신 커리큘럼 리스트 조회 api
    @POST("api/curri/getNewList")
    fun showGetNewCurriList(@Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<Top10>>>
}