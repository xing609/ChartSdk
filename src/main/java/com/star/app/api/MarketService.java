package com.star.app.api;

import java.util.List;

import com.star.app.base.BaseModel;
import com.star.app.model.Explain;
import com.star.app.model.Future;
import com.star.app.model.FutureItem;
import com.star.app.model.Tape;
import com.star.app.model.TrendChartModel;
import com.star.app.model.kminute.KLine;
import com.star.app.model.kminute.MinuteModel;
import com.star.app.model.kminute.NewLast;
import com.star.app.model.pop.KMenuTime;
import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Description：行情模块接口
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-3-28  18:12
 */

public interface MarketService {

    //行情标题栏
    @GET("rest/contract/getMenus")
    Flowable<BaseModel<List<Future>>> getMenus();

    //获取k线时间
    @GET("rest/future/getMinuteKlineInterval")
    Flowable<BaseModel<List<KMenuTime>>> getMinuteKlineInterval();

    //获取最新展示数据
    @GET("rest/contract/getLast")
    Flowable<BaseModel<NewLast>> getNewLast(@Query("contract") String contract);

    //合约说明
    @GET("rest/future/getInstruction")
    Flowable<BaseModel<List<Explain>>> getInstruction(@Query("contract") String contract);

    //利率说明
    @GET("rest/marketIndex/getInstruction")
    Flowable<BaseModel<List<Explain>>> getRateInstruction(@Query("contract") String contract);

    //获取k线数据
    @GET("rest/future/getKlines")
    Flowable<BaseModel<List<KLine>>> getKlines(@Query("contract") String contract, @Query("dataType") String dataType);

    //获取分时图数据
    @GET("rest/future/getMinutes")
    Flowable<BaseModel<MinuteModel>> getMinutes(@Query("contract") String contract, @Query("preIndex") Integer preIndex);
//
//    //获取利率数据
    @GET("rest/rate/getMarketIndexChart")
    Flowable<BaseModel<List<TrendChartModel>>> getMarketIndexChart(@Query("contract") String contract);
//
//    //全部菜单
//    @GET("rest/contract/getMenus")
//    Flowable<BaseModel<List<FutureItem>>> getAllMenus();
//
    //行情标题栏列表
    @GET("rest/contract/getQuotations")
    Flowable<BaseModel<List<FutureItem>>> getQuotations(@Query("menuId") int menuId);
//
//    //清空自选
//    @GET("secure/rest/favorites/delFavoritesCodeAll")
//    Flowable<BaseModel> delFavoritesCodeAll();

    //自选
    @GET("secure/rest/favorites/findByFileFavoritesCode")
    Flowable<BaseModel<List<FutureItem>>> getFutures();

    //自选排序
    @GET("secure/rest/favorites/resetSortFavoritesCode")
    Flowable<BaseModel> resetSortFavoritesCode(@Query("id") long id, @Query("sort") int sort);
//
//
//    //单个添加自选
    @GET("secure/rest/favorites/addFileFavoritesCode")
    Flowable<BaseModel<FutureItem>> addFileFavoritesCode(@Query("typeId") String typeId, @Query("codeId") String codeId);
//
//
//    //是否添加到自选
//    @GET("secure/rest/favorites/getFileFavoritesCodecheck")
//    Flowable<BaseModel<FutureItem>> getFileFavoritesCodecheck(@Query("typeId") String typeId, @Query("codeId") String codeId);
//
//
//    //行情搜索
//    @GET("rest/future/search")
//    Flowable<BaseModel<List<FutureItem>>> marketSearch(@Query("searchName") String searchName);
//
//
//    //批量添加自选
//    @POST("secure/rest/favorites/batchAddFileFavoritesCode")
//    Flowable<BaseModel> batchAddFileFavoritesCode(@Body List<AddFutureParameter> params);
//
    //删除自选
    @POST("secure/rest/favorites/delFavoritesCode")
    Flowable<BaseModel> delFavoritesCode(@Body List<Long> ids);
//
//    //子合约
//    @GET("rest/contract/getContracts")
//    Flowable<BaseModel<List<FutureItem>>> getContracts(@Query("menuId") int menuId);
//
//    //已选
//    @GET("secure/rest/futures/getSubscribeFutures")
//    Flowable<BaseModel<List<ExChange>>> getSubscribeFutures();
//
//    //分时图添加到自选
//    @POST("secure/rest/futures/add")
//    Flowable<BaseModel> addFutures(@Body RequestBody body);
//
//    //分时图删除自选
//    @POST("secure/rest/futures/delete")
//    Flowable<BaseModel> deleteFutures(@Body RequestBody body);
//
//    //首页菜单
//    @GET("rest/menu/config")
//    Flowable<BaseModel<List<HomeMenu>>> getConfig();
//
//    //是否显示盘口
    @GET("rest/future/containsPosition")
    Flowable<BaseModel> getContainsPosition(@Query("contract") String contract, @Query("bizType") String bizType);
//
//    //获取盘口详情
    @GET("rest/future/getPositionQuotation")
    Flowable<BaseModel<List<Tape>>> getPositionQuotation(@Query("contract") String contract);

}
