package com.smart.urban.present;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.widget.CompoundButton;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.model.NaviLatLng;
import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.LocationListBean;
import com.smart.urban.config.Constants;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.ILocationView;
import com.smart.urban.view.ILoginView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 18-3-28.
 */

public class LocationPresent implements GeoFenceListener,
        AMap.OnMapClickListener,
        LocationSource,
        AMapLocationListener,
        CompoundButton.OnCheckedChangeListener
        , AMap.OnMarkerClickListener,
        AMap.OnInfoWindowClickListener {
    //声明mLocationClient对象
    public AMapLocationClient mLocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private Context mContext;
    private OnLocationChangedListener mListener;
    private Marker centerMarker;
    DriveRoutePresent present;
    private BitmapDescriptor ICON_YELLOW = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
    private MarkerOptions markerOption = null;
    private List<Marker> markerList = new ArrayList<>();
    // 中心点坐标
    private LatLng centerLatLng = null;
    private AMap aMap;
    private Marker marker2;// 有跳动效果的marker对象
    private Marker marker3;// 从地上生长的marker对象
    ILocationView mView;

    public LocationPresent(Context mContext, ILocationView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    public void initMap(MapView mMapView) {

        aMap = mMapView.getMap();
        aMap.setOnInfoWindowClickListener(this);
        aMap.setOnMarkerClickListener(this);
        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        //设置定位监听
        aMap.setLocationSource(this);
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(false);
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(5, 5, 5, 5));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(55);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(5, 5, 5, 5));
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//        LatLng latLng = new LatLng(106.572016, 29.539337);
//        addCenterMarker(latLng);
    }

    public void initMap(AMapNaviView mMapView) {
        aMap = mMapView.getMap();
        aMap.setOnInfoWindowClickListener(this);
        aMap.setOnMarkerClickListener(this);
        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        //设置定位监听
        aMap.setLocationSource(this);
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(true);
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(5, 5, 5, 5));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(55);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(5, 5, 5, 5));
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    boolean isShow = true;

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                amapLocation.getAoiName();//
                SharedPreferencesUtils.init(mContext)
                        .put("start_lat", amapLocation.getLatitude() + "")
                        .put("address", amapLocation.getAddress() + "")
                        .put("start_lon", amapLocation.getLongitude() + "");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                Log.e("wan", "location:" + amapLocation.getLatitude());
                if (isShow) {
                    mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                    isShow = false;
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("wan", "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onGeoFenceCreateFinished(List<GeoFence> list, int i, String s) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        markerOption.icon(ICON_YELLOW);
        centerLatLng = latLng;
        addCenterMarker(centerLatLng);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(mContext);
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mLocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 只是为了获取当前位置，所以设置为单次定位
            mLocationOption.setOnceLocation(true);
            // 设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }


    private void addCenterMarker(LatLng latlng) {
        if (null == centerMarker) {
            centerMarker = aMap.addMarker(markerOption);
        }
        if (centerMarker != null) {
            centerMarker.setPosition(latlng);
            markerList.add(centerMarker);
        }

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        if (mView != null) {
            LatLng latLng = marker.getOptions().getPosition();
            SharedPreferencesUtils.init(mContext)
                    .put("end_lat", latLng.latitude + "")
                    .put("end_lon", latLng.longitude + "")
                    .put("title", marker.getTitle());
            mView.onLocationView();
        }
    }

    /**
     * 地图上绘制marks
     */
    private void addMarkersToMap(List<LocationListBean> model) {
        aMap.clear();
        //绘制地图上面的mark
        ArrayList<MarkerOptions> markerOptions = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            LocationListBean bean = model.get(i);
            LatLng latLng = new LatLng(Double.valueOf(bean.getLatitude()), Double.valueOf(bean.getLongitude()));
            MarkerOptions options = new MarkerOptions().anchor(0.5f, 0.5f)
                    .position(latLng).title(bean.getName())
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(mContext.getResources(), setImageState(Integer.valueOf(bean.getType())))))
                    .draggable(true)
                    .period(10)
                    .snippet("点击导航")
                    .setFlat(true);
            markerOptions.add(options);
        }
        aMap.addMarkers(markerOptions, true);
    }

    /**
     * 根据关键字搜索地图坐标点
     *
     * @param tx
     */
    public void getLocationSearch(String tx, final boolean state) {
        if (mView != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", SharedPreferencesUtils.init(mContext).getString("userId"));
            map.put("token", SharedPreferencesUtils.init(mContext).getString("token"));
            map.put("page", 1);
            map.put("rows", 20);
            map.put("keywords", tx);
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getLocationSearch(map), new ApiCallback<BaseResult<List<LocationListBean>>>() {
                @Override
                public void onSuccess(BaseResult<List<LocationListBean>> model) {
                    if (state) {
                        calculate(model.data);
                    } else {
                        addMarkersToMap(model.data);
                        mView.onLocationList(model.data, false);
                    }
                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.errmsg);
                }
            });

        }
    }

    int locationNumber = 8;

    public void calculate(List<LocationListBean> listBeans) {
        List<LocationListBean> list = new ArrayList<>();
        for (int i = 0; i < listBeans.size(); i++) {
            LocationListBean bean = listBeans.get(i);
            double start_lon = Double.valueOf(SharedPreferencesUtils.init(mContext).getString("start_lon"));
            double start_lat = Double.valueOf(SharedPreferencesUtils.init(mContext).getString("start_lat"));
            LatLng startLat = new LatLng(start_lat, start_lon);
            double end_lat = Double.valueOf(bean.getLatitude());
            double end_lon = Double.valueOf(bean.getLongitude());
            LatLng endLat = new LatLng(end_lat, end_lon);
            float distance = AMapUtils.calculateLineDistance(startLat, endLat) / 1000;
            Log.i("wan", "计算的距离：" + distance);
            bean.setCalculate(distance);//计算距离
        }

        Collections.sort(listBeans, new Comparator<LocationListBean>() {
            @Override
            public int compare(LocationListBean o1, LocationListBean o2) {
                Log.i("location", "o1---" + o1.getCalculate());
                Integer id1 = (int) o1.getCalculate();
                Log.i("location", "o1---" + id1.intValue());
                Integer id2 = (int) o2.getCalculate();
                return id1.compareTo(id2);
            }
        });
        //计算离自己最近的五个点
        if (listBeans.size() <= locationNumber) {
            list.addAll(listBeans);
        } else {
            for (int j = 0; j < locationNumber; j++) {
                list.add(listBeans.get(j));
            }
        }
        mView.onLocationList(list, true);
        //计算距离排序
        addMarkersToMap(list);

    }


    public int setImageState(int type) {
        switch (type) {
            //酒店
            case 1:
                return R.drawable.icon_jiu_dian;
            //饭店
            case 2:
                return R.drawable.icon_fan_dian;
            //超市
            case 3:
                return R.drawable.icon_chao_shi;
            //厕所
            case 4:
                return R.drawable.icon_jiu_dian;
            //菜市场
            case 5:
                return R.drawable.icon_cai_shi_chang;
            //小区
            case 6:
                return R.drawable.icon_jiu_dian;
            //派出所
            case 7:
                return R.drawable.icon_pai_chu_suo;
            //政府
            case 8:
                return R.drawable.icon_zheng_fu;
            //其他
            case 12:
                return R.drawable.icon_other;
            //医院
            case 13:
                return R.drawable.icon_other;
        }
        return R.drawable.icon_other;
    }

    public void onDestroy() {
        if (aMap != null) {
            aMap = null;
        }
    }
}
