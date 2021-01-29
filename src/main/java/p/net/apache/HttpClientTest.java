package p.net.apache;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import p.utils.RSAUtils;

public class HttpClientTest {
	public static void main(String[] args) {
		HttpClientTest test = new HttpClientTest();
		test.doGetTestOne();
	}
	
	@Test
	public void doGetTestOne() {
		// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 创建Get请求
		HttpGet httpGet = new HttpGet("http://localhost:8888/licensingservice/default");

		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			System.out.println("响应状态为:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("响应内容长度为:" + responseEntity.getContentLength());
				System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void doPostTestOne() {
		// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 创建Post请求
		HttpPost httpPost = new HttpPost("http://localhost:8012/ingest/getuserkey");

		JSONObject ret = new JSONObject();
		ret.put("appid", "localhost");
		ret.put("openid", "1a351b42c718354259e316de0f02a1d4b8234876");
		ret.put("password", "1234");
		ret.put("sig", "123");
		System.out.println(ret.toJSONString());
		
		Map<String, String> params = new HashMap<>();
		params.put("content", RSAUtils.mutiEncryptByPriKey(ret.toJSONString()));
		params.put("content", "eGOtzIBDhaleau06C/JgWMaLIj2VbYtUcZVzNQuA61k6rQpoHXMbopceEcYbm9WcfRPj0wlu0t7aOmzmc3g7X2VjqgY6HjcL1nEBrLA/wV4sRCImU0fLNpYJuRRaTS17USHDufvNrXFR1W6xnUDcWa6qzNLgBY8IXJdBbQh1xrE=SBPBlOzWr6g3M/glVTVDOWa0zp9zbMZixtEfAKSGmYi1mQwQPN1s/Tg18Sr6BS460u3m5GnjhzGfIOjWm0HYACQtuzHKWKfbeK1oEh5TXExL9RhTmBZCC8IDUUQoV8LEh0pEyE716rXJu7emvERp02G0uOs/j57OzPRRRB0J81w=eXcXhdl4OfqpgR3QqSneYqNd08OdOAAHY5DmDi8gu+OFGEeIDhLPXkP9DRWW8Pa8Zk2JgxnxOAR1Ebu/eJlOe1QRaqVXkB8Q+nVjdylDjBCIy8MiJsIizVa4wiUvHk186vmzDYlu4XwCkcqmt1pfFwndI6hZ0JyBxWzDydgYrs0=OXElk513By6Wo3cc604ixRVOBxWwZ2UiXYFiNrQAaVjdVlld6q7j01fAOyr/kH2GnUHZzVjrrx6PYGaUy1u+QJOtfIO4qukmlrUb/+uxKCr5sVjwLa7UCfN9YilowMKAWbXbtbTCLQPxQTjgCgmXTr7lOVX14SqS8m1vbAA/nb4=cDFJvMVvDpn+pV3lKaQA5AVMCHy4Or+Umf7pvO1k78FhePsfMWCUm3aJqWxlj+CLNjOF6PeZ0SgQu1oMUNSB4CRSnnnETN39oGoznsk8K40tIFRR9MYBQeWsGsIT2EixRz/661O79WFohfs4ygMAx/JB9NVCh9tA0fvYjkr4VNw=ID+6bld/yVDn8oLn3kz7X6XtkIUdB4GGTn2o4XdeCQLeyxIabs2Gtqld7+dhjys+UNphIKrkGointQ0eLbwk0rDTox6BqdBJeaGiHPa1xDOmJLQEsW3BIGG5CIW3KKXnEkPKvoRLCNEbPLWKQLtS/ciJ9SEyzABdmz+DB+pXHF8=QeHyj2Htipk27vAB+bNMCbBRdEu9sv83IPzDYvSiOrRiXktetA2lPzuF4Y8lQzKdAIU7JAsc7AU0NoEcnXwpsuKm2bfyznEsRL0k+kSEL3WocdiKlfuxp/bUi6QsL9J2oIbO9LMfvnBOW4h6UOZrlTqrtsMHObB+eQ5hYnqREJ0=OYxx2ODshVo3L7A/vp/3v6McThVvjRRC2RIOjDe83T4Y/W7yWHo2wJEUyElL4CF+zA8y+5NteIS1t7PJ7GUJ7pR6paeXZaLhCM92kPCmIWeOzLZMqK0Q0l8YEhis8A6Xl7DeRJqDSNNqNlIP60XEI+WIDjQqHC76b1l+o5FTB5k=aMqOza1chwrZ/o1u3nwXGuG98UpY6YmiHzlIzDESjWxSiwNhnuvfYuqdet0TEOICk7syuPqaqVG9mP23Q94rkAaIKky82Wq8k3XqZBAWT/K4fRGDBlyfk6wsCtFczGqtQL54PaQ3zdG7sWcAtakNfYKr1qDNc1ymz1a39ypEcxg=");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpPost);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			System.out.println("响应状态为:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("响应内容长度为:" + responseEntity.getContentLength());
				System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
