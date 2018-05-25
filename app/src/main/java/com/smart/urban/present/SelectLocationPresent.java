package com.smart.urban.present;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.RegeocodeRoad;
import com.amap.api.services.geocoder.StreetNumber;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.smart.urban.R;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.SelectLocationBean;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.ISelectLocationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 18-5-23.
 */

public class SelectLocationPresent extends BasePresenter<ISelectLocationView> implements
        LocationSource,
        AMap.OnCameraChangeListener,
        AMapLocationListener,
        PoiSearch.OnPoiSearchListener,
        AMap.OnMarkerDragListener,
        AMap.OnMarkerClickListener,
        GeocodeSearch.OnGeocodeSearchListener,
        AMap.OnMapTouchListener {
    private OnLocationChangedListener mListener;
    private Activity mContext;
    public AMapLocationClient mLocationClient;
    public AMapLocationClientOption mLocationOption = null;
    AMap aMap;
    private MapView mMapView;


    public SelectLocationPresent(Activity mContext) {
        this.mContext = mContext;
    }

    public void initMap(MapView mMapView) {
        this.mMapView = mMapView;
        aMap = mMapView.getMap();
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
        aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
        aMap.setOnMapTouchListener(this);// 对amap添加触摸地图事件监听器
        aMap.setOnMarkerDragListener(this);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
    }

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
                LatLng la = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                setMarket(la, amapLocation.getCity(), amapLocation.getAddress());
                Log.e("wan", "location:" + amapLocation.getLatitude());
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                doSearchQuery(amapLocation.getCityCode(), amapLocation.getLatitude(), amapLocation.getLongitude());
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("wan", "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());
            }
        }
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

    PoiSearch.Query query;
    PoiSearch poiSearch;
    private int currentPage = 1;

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String city, double latitude, double longitude) {
        String mType = "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施";
        query = new PoiSearch.Query("", mType, city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        poiSearch = new PoiSearch(mContext, query);
        poiSearch.setOnPoiSearchListener(this);
        //以当前定位的经纬度为准搜索周围5000米范围
        // 设置搜索区域为以lp点为圆心，其周围5000米范围
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 1000, true));//
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        Log.i("wan", "onCameraChange");
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        LatLng latLng = marker.getPosition();
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        Log.e("latitude", latitude + "");
        Log.e("longitude", longitude + "");
    }


    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        final LatLng latLng = cameraPosition.target;
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        Log.e("latitude", latitude + "");
        Log.e("longitude", longitude + "");
        doSearchQuery("", latitude, longitude);

        new Thread(new Runnable() {

            @Override
            public void run() {
                GeocodeSearch geocodeSearch = new GeocodeSearch(mContext);
                LatLonPoint point = new LatLonPoint(latLng.latitude, latLng.longitude);
                RegeocodeQuery regeocodeQuery = new RegeocodeQuery(
                        point, 1000, GeocodeSearch.AMAP);
                RegeocodeAddress address = null;
                try {
                    address = geocodeSearch
                            .getFromLocation(regeocodeQuery);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null == address) {
                    return;
                }
                final StringBuffer stringBuffer = new StringBuffer();
                String area = address.getProvince();// 省或直辖市
                String loc = address.getCity();// 地级市或直辖市
                String subLoc = address.getDistrict();// 区或县或县级市
                String ts = address.getTownship();// 乡镇
                String thf = null;// 道路
                List<RegeocodeRoad> regeocodeRoads = address.getRoads();// 道路列表
                if (regeocodeRoads != null && regeocodeRoads.size() > 0) {
                    RegeocodeRoad regeocodeRoad = regeocodeRoads.get(0);
                    if (regeocodeRoad != null) {
                        thf = regeocodeRoad.getName();
                    }
                }
                String subthf = null;// 门牌号
                StreetNumber streetNumber = address.getStreetNumber();
                if (streetNumber != null) {
                    subthf = streetNumber.getNumber();
                }
                String fn = address.getBuilding();// 标志性建筑,当道路为null时显示
                if (area != null)
                    stringBuffer.append(area);
                if (loc != null && !area.equals(loc))
                    stringBuffer.append(loc);
                if (subLoc != null)
                    stringBuffer.append(subLoc);
                if (ts != null)
                    stringBuffer.append(ts);
                if (thf != null)
                    stringBuffer.append(thf);
                if (subthf != null)
                    stringBuffer.append(subthf);
                if ((thf == null && subthf == null) && fn != null && !subLoc.equals(fn))
                    stringBuffer.append(fn + "附近");

                mGPSMarker.setSnippet(stringBuffer.toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mView != null) {
                            mGPSMarker.showInfoWindow();
                            mView.onMobileLocation(stringBuffer.toString());
                        }
                    }
                });
            }
        }).start();
    }


    Handler handler = new Handler();

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (mView != null) {
            Log.i("wan", "onPoiSearched");
            List<SelectLocationBean> address = new ArrayList<SelectLocationBean>();
            ArrayList<PoiItem> items = poiResult.getPois();
            if (items != null && items.size() > 0) {
                PoiItem item = null;
                for (int j = 0, count = items.size(); j < count; j++) {
                    item = items.get(j);
                    SelectLocationBean selectLocationBean = new SelectLocationBean();
                    selectLocationBean.setTitle(item.getTitle());
                    selectLocationBean.setCheck(j == 0 ? true : false);
                    address.add(selectLocationBean);
                }
            }
            mView.onLocationAddress(address);
            Log.i("wan", "address>>" + address.size());
        }
    }

    MarkerOptions markOptions;
    private Marker mGPSMarker;

    private void setMarket(LatLng latLng, String title, String content) {
        if (mGPSMarker != null) {
            mGPSMarker.remove();
        }
        //获取屏幕宽高
        WindowManager wm = mContext.getWindowManager();
        int width = (wm.getDefaultDisplay().getWidth()) / 2;
        int height = ((wm.getDefaultDisplay().getHeight()) / 2) - 80;
        markOptions = new MarkerOptions();
        markOptions.draggable(true);//设置Marker可拖动
        markOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poi_marker_1))).anchor(0.5f, 0.7f);
        //设置一个角标
        mGPSMarker = aMap.addMarker(markOptions);
        //设置marker在屏幕的像素坐标
        mGPSMarker.setPosition(latLng);
//        mGPSMarker.setTitle(title);
        mGPSMarker.setSnippet(content);
        //设置像素坐标
        mGPSMarker.setPositionByPixels(width, height);
        if (!TextUtils.isEmpty(content)) {
            mGPSMarker.showInfoWindow();
        }
        mMapView.invalidate();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        Log.i("wan", "onPoiItemSearched");
    }


    @Override
    public void onTouch(MotionEvent motionEvent) {

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                String addressName = result.getRegeocodeAddress().getFormatAddress(); // 逆转地里编码不是每次都可以得到对应地图上的opi
                Log.e("location", "逆地理编码回调  得到的地址：" + addressName);
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
