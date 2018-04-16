package com.smart.urban.config;

import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.smart.urban.bean.CameraPicBean;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by root on 18-3-28.
 */

public class Constants {
        public static final String BASE_URL = "http://111.231.222.163:8080/";
//    public static final String BASE_URL = "http://10.15.208.136:8080/ ";
    //是否开启打印日志信息
    public static final boolean DEBUG = true;

    public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
    public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 北京市中关村经纬度
    public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度
    public static final LatLng FANGHENG = new LatLng(39.989614, 116.481763);// 方恒国际中心经纬度
    public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
    public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度
    public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 郑州市经纬度


    public static MultipartBody.Part prepareFilePart(String partName, String path) {
        File file = new File(path);
        try {
            if (file.exists()) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
                return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 多张图片上传
     *
     * @param cameraPicBeans
     * @return
     */
    public static MultipartBody.Part[] getFileMaps(List<CameraPicBean> cameraPicBeans) {
        cameraPicBeans.remove(cameraPicBeans.size() - 1);
        MultipartBody.Part[] pars = new MultipartBody.Part[cameraPicBeans.size()];
        for (int i = 0; i < cameraPicBeans.size(); i++) {
            CameraPicBean bean = cameraPicBeans.get(i);
            if (bean.getPic() != null) {
                pars[i] = prepareFilePart("files", bean.getPic());
            }
        }
        return pars;
    }


    public static String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>html{padding:15px;} body{word-wrap:break-word;font-size:13px;padding:0px;margin:0px} p{padding:0px;margin:0px;font-size:13px;color:#222222;line-height:1.3;} img{padding:0px,margin:0px;max-width:100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
