package com.smart.urban.config;

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
//    public static final String BASE_URL = "http://47.105.53.173:8080/";
//        public static final String BASE_URL = "http://10.15.209.200:8080/city/";
//    public static final String BASE_URL = "http://10.15.208.136:8080/";
    //是否开启打印日志信息
    public static final boolean DEBUG = true;


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
