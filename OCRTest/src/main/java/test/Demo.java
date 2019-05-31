package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
/**阿里云ocr行驶证识别*/
public class Demo {
	public String getBodys(){
		 //图片转化为二进制的Base64编码字符串
        String imgFile="C:\\Users\\Administrator\\Pictures\\2017916.jpg";
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String base64String=new String(Base64.encodeBase64(data));
		return base64String;
	}
	public static void main(String[] args) {
		Demo demo=new Demo();
	    String host = "https://dm-53.data.aliyun.com";
	    String path = "/rest/160601/ocr/ocr_vehicle.json";
	    String method = "POST";
	    String appcode = "996b09946c064ef2abed7b0d372438f4";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    //根据API的要求，定义相对应的Content-Type
	    headers.put("Content-Type", "application/json; charset=UTF-8");
	    Map<String, String> querys = new HashMap<String, String>();
	    String bodys = "{\"inputs\":[{\"image\":{\"dataType\":50,\"dataValue\":\""+demo.getBodys()+"\"}}]}";


	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
	    	System.out.println(response.toString());
	    	//获取response的body
	    	System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
}
