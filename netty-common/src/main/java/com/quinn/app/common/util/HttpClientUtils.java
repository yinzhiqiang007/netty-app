package com.quinn.app.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;


public class HttpClientUtils {

	public static String post(String url, Map<String, String> params) { 
		CloseableHttpClient httpclient = HttpClients.createDefault(); 
		HttpPost post = postForm(url, params);
		String body = null; 
		try {
			CloseableHttpResponse response2 = httpclient.execute(post); 
			try {
		        HttpEntity entity2 = response2.getEntity();
		        body = EntityUtils.toString(entity2,"UTF-8"); 
		        EntityUtils.consume(entity2);
		    } finally {
		        response2.close();
		    }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 
		return body;
	}

	public static String get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
	    HttpGet httpGet = new HttpGet(url);
		String body = null; 
		try {
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
		    try {
		        HttpEntity entity1 = response1.getEntity();
		    	String charset = EntityUtils.getContentCharSet(entity1);
		        body = EntityUtils.toString(entity1);
		        EntityUtils.consume(entity1);
		    } finally {
		        response1.close();
		    }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return body;
	}
  

	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private static HttpPost postForm(String url, Map<String, String> params) {

		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return httpost;
	}

	public static String post(String url, String inMessageXml) {
		
		System.out.println("url..."+url);
		System.out.println("inMessageXml..."+inMessageXml);
		 //创建httpclient工具对象     
        HttpClient client = new HttpClient();      
        //创建post请求方法     
        PostMethod myPost = new PostMethod(url);      
        //设置请求超时时间     
        client.setConnectionTimeout(3000*1000);    
        String responseString = null;      
        try{      
            //设置请求头部类型     
            myPost.setRequestHeader("Content-Type","text/xml");    
            myPost.setRequestHeader("charset","utf-8");    
            //设置请求体，即xml文本内容，一种是直接获取xml内容字符串，一种是读取xml文件以流的形式     
            myPost.setRequestEntity(new StringRequestEntity(inMessageXml,"text/xml","utf-8"));     
            int statusCode = client.executeMethod(myPost);     
            //只有请求成功200了，才做处理  
            if(statusCode == HttpStatus.SC_OK){      
                InputStream inputStream = myPost.getResponseBodyAsStream();  
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));  
                StringBuffer stringBuffer = new StringBuffer();  
                String str = "";  
                while ((str = br.readLine()) != null) {  
                    stringBuffer.append(str);  
                }  
                responseString = stringBuffer.toString();  
            }      
        }catch (Exception e) {   
            e.printStackTrace();      
        }finally{  
             myPost.releaseConnection();   
        }  
        return responseString;      
	}
	
	
	
	
	
	public static String doPost(String url, Object data){

		//String jsonData1 = JSON.toJSONString(data);
		String jsonData = JSON.toJSONString(data);
		//String jsonData2 = (new Gson()).toJson(data);
		//JSON.toJSONString()

		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		try {
			StringEntity s = new StringEntity(jsonData, "utf-8");
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");//发送json数据需要设置contentType
			post.setEntity(s);
			HttpResponse res = httpclient.execute(post);
			if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				return EntityUtils.toString(res.getEntity());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	
	
	
	
	
	
	
	 /**
     * HttpURLConnection方式 模拟Http Get请求
     * @param urlStr
     *             请求路径
     * @param paramMap
     *             请求参数
     * @return
     * @throws Exception
     */
    public static String get(String urlStr, Map<String, String> paramMap) {
        urlStr = urlStr + "?" + getParamString(paramMap);
		System.out.println("11111111111111111:"+urlStr);
        HttpURLConnection conn = null;
        try {
            //创建URL对象
            URL url = new URL(urlStr);
            //获取URL连接
            conn = (HttpURLConnection) url.openConnection();
            //设置通用的请求属性
            setHttpUrlConnection(conn, "GET");
            //建立实际的连接
            conn.connect();
            //获取响应的内容
            return readResponseContent(conn.getInputStream());
        }catch (Exception e){
        	e.printStackTrace();
			return null;
		}
        finally {
            if (null != conn){
				conn.disconnect();
			}

        }

    }
	
	
	
	
	
	
	
	
	
	  /**
     * HttpURLConnection方式 模拟Http Post请求
     * @param urlStr
     *             请求路径
     * @return
     * @throws Exception
     */
    public static String postMap(String urlStr,Object object) throws Exception {
        HttpURLConnection conn = null;
        PrintWriter writer = null;
       
        try {
            //创建URL对象
            URL url = new URL(urlStr);
            //获取请求参数
            Map<String, String> params=objectToMap(object);
            String param = getParamString(params);
            //获取URL连接
            System.out.println("requestUrl:"+urlStr);
            System.out.println("outputStr:"+param);
            conn = (HttpURLConnection) url.openConnection();
            //设置通用请求属性
            setHttpUrlConnection(conn, "POST");
            //建立实际的连接
            conn.connect();
            //将请求参数写入请求字符流中
            writer = new PrintWriter(conn.getOutputStream());
            writer.print(param);
            writer.flush();
            //读取响应的内容
            return readResponseContent(conn.getInputStream());
        } finally {
            if (null != conn)
                conn.disconnect();
            if (null != writer)
                writer.close();
        }
    }
    
	
	
    /**
  	 * 转换对象为map
  	 * @param object
  	 * @param ignore
  	 * @return
  	 */
  	public static Map<String,String> objectToMap(Object object,String... ignore){
  		Map<String,String> tempMap = new LinkedHashMap<String, String>();
  		//获取本类的Fields
  		for(Field f : object.getClass().getDeclaredFields()){		
  			if(!f.isAccessible()){
  				f.setAccessible(true);
  			}
  			boolean ig = false;
  			if(ignore!=null&&ignore.length>0){
  				for(String i : ignore){
  					if(i.equals(f.getName())){
  						ig = true;
  						break;
  					}
  				}
  			}
  			if(ig){
  				continue;
  			}else{
  				Object o = null;
  				try {
  					o = f.get(object);
  				} catch (IllegalArgumentException e) {
  					e.printStackTrace();
  				} catch (IllegalAccessException e) {
  					e.printStackTrace();
  				}
  				tempMap.put(f.getName(), o==null?"":o.toString());
  			}
  		}
  		//获取基类的Fields
  		for(Field f : object.getClass().getFields()){
  			if(!f.isAccessible()){
  				f.setAccessible(true);
  			}
  			boolean ig = false;
  			if(ignore!=null&&ignore.length>0){
  				for(String i : ignore){
  					if(i.equals(f.getName())){
  						ig = true;
  						break;
  					}
  				}
  			}
  			if(ig){
  				continue;
  			}else{
  				Object o = null;
  				try {
  					o = f.get(object);
  				} catch (IllegalArgumentException e) {
  					e.printStackTrace();
  				} catch (IllegalAccessException e) {
  					e.printStackTrace();
  				}
  				tempMap.put(f.getName(), o==null?"":o.toString());
  			}
  		}
  		return tempMap;
  	}

	/**
	 * 将参数转为路径字符串
	 * @param paramMap
	 *             参数
	 * @return
	 */
	public static String getParamString(String urlStr, Map<String, String> paramMap) {
		urlStr = urlStr + "?" + getParamString(paramMap);
		return urlStr;
	}
  	
    /**
     * 将参数转为路径字符串
     * @param paramMap
     *             参数
     * @return
     */
    private static String getParamString(Map<String, String> paramMap) {
        if (null == paramMap || paramMap.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String key : paramMap.keySet()) { 
        	if(!key.equals("pd")){
        		if(CommonUtils.isNotEmpty(paramMap.get(key))){
            		//传入值不为空 拼接字符串
            		builder.append("&").append(key).append("=").append(paramMap.get(key));
            	}
        		else{
            		builder.append("&");
            	}
        	}
        	
        }
        return new String(builder.deleteCharAt(0).toString());
    }
	
	
    /**
     * 读取响应字节流并将之转为字符串
     * @param in
     *         要读取的字节流
     * @return
     * @throws IOException
     */
    private static String readResponseContent(InputStream in) throws IOException {
        Reader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            reader = new InputStreamReader(in,"utf-8");
            char[] buffer = new char[1024];
            int head = 0;
            while ((head = reader.read(buffer)) > 0) {
                content.append(new String(buffer, 0, head));
            }
            String result= content.toString();
            System.out.println("readResponseContent.."+result);
             return result;
        } finally {
            if (null != in)
                in.close();
            if (null != reader)
                reader.close();
        }
    }
    
    /**
     * 设置Http连接属性
     * @param conn
     *             http连接
     * @return
     * @throws ProtocolException
     * @throws Exception
     */
    private static void setHttpUrlConnection(HttpURLConnection conn,
                                             String requestMethod) throws ProtocolException {
        conn.setRequestMethod(requestMethod);
        conn.setRequestProperty("content-encoding", "UTF-8");
        conn.setRequestProperty("accept", "application/json");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("Accept-Language", "zh-CN");

        conn.setRequestProperty("User-Agent",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
        conn.setRequestProperty("Proxy-Connection", "Keep-Alive");

        if (null != requestMethod && "POST".equals(requestMethod)) {
            conn.setDoOutput(true);
            conn.setDoInput(true);
        }
    }

	   public static int postHttp(String url, String path, File file) throws IOException {

		  CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost(url);
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        mEntityBuilder.addBinaryBody("file", file);
        mEntityBuilder.addTextBody("path", path);
        httppost.setEntity(mEntityBuilder.build());
        HttpResponse resp = httpClient.execute(httppost);
        int code = resp.getStatusLine().getStatusCode();
        if (200 == code) {
            return 0;
        } else {
            return -1;
        }
    }


	public static File downloadFile(String url,String tempPath,String tempFileName) {
		FileOutputStream fos = null;
		InputStream is = null;
		URLConnection conn = null;
		try {
			URL u = new URL(url);
			System.out.println("downloadFile url......" + url);
			conn = u.openConnection();
			conn.setRequestProperty("User-Agent", "Mozilla/31.0 (compatible; MSIE 10.0; Windows NT; DigExt)");
			conn.connect();
			is = conn.getInputStream();
			conn.setConnectTimeout(10 * 1000);
			conn.setReadTimeout(20 * 1000);
			int fileSize = conn.getContentLength();
			if (fileSize < 1 || is == null) {

				return null;
			} else {
				String fileName = tempPath+tempFileName;
				System.out.println("downloadFile fileName......" + fileName);
				File downFile = new File(fileName);
				if (downFile.exists()) {
					downFile.delete();
				}
				fos = new FileOutputStream(downFile);
				byte[] bytes = new byte[1024 * 100];
				int len = -1;
				while ((len = is.read(bytes)) != -1) {
					fos.write(bytes, 0, len);
					fos.flush();
				}
				return downFile;
			}

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		} finally {
			try {

				if (is != null) {
					is.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (conn != null) {

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
    
   
    
    
    
}
