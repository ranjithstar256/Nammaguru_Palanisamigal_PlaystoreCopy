package com.app.nammaguru_palanisamigal.APIcalls;
import com.app.nammaguru_palanisamigal.model.Banner_Model;
import com.app.nammaguru_palanisamigal.model.Images_Model;
import com.app.nammaguru_palanisamigal.model.Upcoming_model;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiCall {

//    POST --- Body
//    GET  --- Query

//    @POST("get_all_feeds")
//    Single<Feed> feeds(@Query("user_id") String email);

    @POST("banners")
    Single<Banner_Model> getBanner(@Body Request_params params);

    @POST("posts/getimagelist")
    Single<Images_Model> getImages(@Body Request_params params);

    @POST("posts/getvideolist")
    Single<Images_Model> getVideos(@Body Request_params params);

    @POST("posts/upcomingevents")
    Single<Upcoming_model> upcomingEvents(@Body Request_params params);

    @POST("posts/recents")
    Single<Upcoming_model> recentEvents(@Body Request_params params);

    @POST("users/savedeviceinfo")
    Single<Upcoming_model> Notification(@Body Request_params params);

}