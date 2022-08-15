package com.example.debri_lize.utils

import com.example.debri_lize.base.BaseResponse
import com.example.debri_lize.data.auth.Token
import com.example.debri_lize.data.auth.User
import com.example.debri_lize.data.auth.UserLogin
import com.example.debri_lize.data.auth.UserSignup
import com.example.debri_lize.data.board.Board
import com.example.debri_lize.data.board.BoardFavorite
import com.example.debri_lize.data.class_.Lecture
import com.example.debri_lize.data.class_.LectureScrap
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
    @GET("api/post/getList/{boardIdx}")
    fun showEachPostList(@Path("boardIdx") boardIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<PostList>>>

    //3.7.1 [전체 범위(키워드 검색)] 게시물 리스트 조회 api
    @POST("api/post/getSearchList")
    fun showPostList(@Body keyword: String, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<PostList>>>

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
    @GET("api/post/getMyScrap")
    fun showScrapPostList(@Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<PostList>>>

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

    //6.1 게시물 신고 api
    @POST("api/report/postReport")
    fun reportPost(@Body report: ReportPost, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //6.2 댓글, 대댓글 신고 api
    @POST("api/report/commentReport")
    fun reportComment(@Body report: ReportComment, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //7.2 강의 즐겨찾기 api
    @POST("api/lecture/scrap/create")
    fun createLectureScrap(@Body lectureScrap: LectureScrap, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //7.2.1 강의 즐겨찾기 삭제 api
    @PATCH("api/lecture/scrap/delete")
    fun cancelLectureScrap(@Body lectureScrap: LectureScrap, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //7.3 즐겨찾기 한 강의 리스트 조회 api
    @GET("api/lecture/getScrapList/{userIdx}")
    fun showLectureFavorite(@Path("userIdx") userIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<Lecture>>>

    //7.4.1 강의 검색 api
    @GET("api/lecture/search")
    fun showLectureSearch(@Query ("lang") lang:String?,@Query ("type") type:String?,@Query ("price") price:String?,@Query ("key") key:String?, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<Lecture>>>

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
    @POST("api/curri/delete/{curriIdx}")
    fun deleteCurriculum(@Path("curriIdx") curriIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<String>>

    //8.12 커리큘럼 리뷰 작성 api
    @POST("api/curri/review/create")
    fun createReview(@Body review : Review, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<Review>>

    //8.12.1 커리큘럼 리뷰 조회 api
    @GET("api/curri/review/getList/{curriIdx}")
    fun showReview(@Path("curriIdx") curriIdx: Int, @Header("ACCESS-TOKEN") authToken: String) : Call<BaseResponse<List<Review>>>
}