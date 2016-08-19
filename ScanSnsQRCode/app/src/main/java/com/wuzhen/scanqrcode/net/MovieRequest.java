package com.wuzhen.scanqrcode.net;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.kokozu.core.Configurators;
import com.kokozu.net.HttpRequest;
import com.kokozu.net.RequestParams;
import com.kokozu.net.result.HttpResult;
import com.kokozu.net.result.HttpResultFactory;
import com.kokozu.net.result.IResult;
import com.kokozu.util.ParseUtil;
import com.kokozu.util.TextUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 普通请求类。
 *
 * @author wuzhen
 * @version Version 1.0, 2014-11-01
 */
public class MovieRequest extends HttpRequest {

    public MovieRequest(Context context, String url, RequestParams params) {
        super(context, url, params);
    }

    @Override
    protected Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("channel_id", "2"); // 渠道 ID
        headers.put("channel", "Android"); // 固定值：ios、android
        headers.put("version", Configurators.getAppVersionName(mContext));
        return headers;
    }

    @Override
    protected HttpResult makeHttpResult(String data) {
        try {
            final JSONObject object = JSONObject.parseObject(data);

            final int status = ParseUtil.parseInt(object, "status");
            String error = ParseUtil.parseString(object, "error");
            String message = ParseUtil.parseString(object, "message");

            // create HttpResult
            HttpResult result = HttpResultFactory.makeResult(data);
            result.setStatus((status == 0) ? IResult.SUCCESS : status);
            result.setMessage(TextUtil.isEmpty(error) ? message : error);
            result.setAction(ParseUtil.parseString(object, "action"));
            result.setTotal(ParseUtil.parseInt(object, "total"));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResultFactory.makeResponseIllegalResult(mContext);
    }
}
