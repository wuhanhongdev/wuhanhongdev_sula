package com.sula.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


@SuppressWarnings("deprecation")
public class HttpClientUtil {

	

    private static final String CHARSET = HTTP.UTF_8;
    private static HttpClient   customerHttpClient;

    public static synchronized HttpClient getHttpClient()
    {
        if (null == customerHttpClient)
        {
            HttpParams params = new BasicHttpParams();
            // 设置一些基本参数
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, CHARSET);
            HttpProtocolParams.setUseExpectContinue(params, true);
            HttpProtocolParams.setUserAgent(params, "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
                    + "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
            // 超时设置
            /* 从连接池中取连接的超时时间 */
            ConnManagerParams.setTimeout(params, 1000);
            /* 连接超时 */
            HttpConnectionParams.setConnectionTimeout(params, 2000);
            /* 请求超时 */
            HttpConnectionParams.setSoTimeout(params, 14000);

            // 设置我们的HttpClient支持HTTP和HTTPS两种模式
            SchemeRegistry schReg = new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

            // 使用线程安全的连接管理来创建HttpClient
            ClientConnectionManager conMgr = new MyBasicClientConnectionManager(params, schReg);
            customerHttpClient = new DefaultHttpClient(conMgr, params);
        }
        return customerHttpClient;
    }

    /**
     * 发送请求
     * 
     * @author: 
     * @time:2015-10-15 上午11:11:48
     */
    public static String sendPostRequest(String reqURL, Map<String, String> reqData)
    {
        HttpPost httpPost = new HttpPost(reqURL);
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        Set<String> set = reqData.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext())
        {
            String key = it.next();
            formParams.add(new BasicNameValuePair(key, reqData.get(key)));
            String ss = key +":"+reqData.get(key);
            System.out.println(ss);
        }
        UrlEncodedFormEntity urlEncodedFormEntity;
        try
        {
            urlEncodedFormEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpClient client = getHttpClient();
            HttpResponse response = client.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null)
            {
                String str = "";
                try
                {
                    str = EntityUtils.toString(httpEntity);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                return str;
            }
            else
            {
                return null;
            }
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
