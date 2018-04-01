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
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.config.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 18-3-28.
 */

public class LocationPresent implements GeoFenceListener,
        AMap.OnMapClickListener,
        LocationSource,
        AMapLocationListener,
        CompoundButton.OnCheckedChangeListener
        , AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener {
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

    public LocationPresent(Context mContext) {
        this.mContext = mContext;
        present = new DriveRoutePresent(mContext);
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
        settings.setMyLocationButtonEnabled(true);
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(5, 5, 5, 5));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(55);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(5, 5, 5, 5));
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        LatLng latLng = new LatLng(106.572016, 29.539337);
        addCenterMarker(latLng);
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
            addMarkersToMap();
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
        ToastUtils.showShort("你点击了infoWindow窗口" + marker.getTitle());
        ToastUtils.showShort("当前地图可视区域内Marker数量:" + aMap.getMapScreenMarkers().size());
    }


    private void addMarkersToMap() {
        //绘制地图上面的mark
        MarkerOptions markerOption1 = new MarkerOptions().anchor(0.5f, 0.5f)
                .position(Constants.CHENGDU).title("成都市")
                .snippet("成都市:30.679879, 104.064855")
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher)))
                .draggable(true)
                .period(10)
                .setFlat(true);

        MarkerOptions markerOption2 = new MarkerOptions().anchor(0.5f, 0.5f)
                .position(Constants.XIAN).title("成都市")
                .snippet("成都市:30.679879, 104.064855")
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher)))
                .draggable(true)
                .period(10)
                .setFlat(true);
        marker2 = aMap.addMarker(markerOption1);
        marker3 = aMap.addMarker(markerOption2);
    }
}
