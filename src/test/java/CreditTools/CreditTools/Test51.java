package CreditTools.CreditTools;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Test51 extends Dependencies {
	public static ArrayList alldata = null;
	public String URLPort = null;
	public static String url = null;
	public static String urlcz = null;
	public String[] str = null;
	public static Properties properties = null;

	@BeforeClass
	public void BeforeClass() throws IOException {
		InputStream in = Test51.class.getResourceAsStream("51case.properties");
		properties = new Properties();
		properties.load(in);
		url = (String) properties.get("url");
		urlcz = (String) properties.get("urlcz");


	}

	/**
	 * 授信正常用例,51请求
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void a_shouxin01() throws IOException, InterruptedException {
		String json = properties.getProperty("shouxinzc51");
		System.out.println(json);

		Map mapin = returnmap(json);
		String requestNum = getrequestNum();
		mapin.put("requestNum", requestNum);
		
		Map requestBody = (Map) mapin.get("requestBody");
		// 获取creditApplyNo
		String creditApplyNo = getcreditApplyNo();
		// 生成userinfo
		Map userinfo = getUserinfo();
		// 获取custName
		String fullName = (String) userinfo.get("custName");
		// 获取cardId
		String cardId = (String) userinfo.get("cardNum");
		// // 生成phone
		String mobile = getPhonenm();
		// 获取信用卡号
		String craditCardNum = getcraditCardNum();
		// 存入requestBody
		requestBody.put("creditApplyNo", creditApplyNo);
		requestBody.put("fullName", fullName);
		requestBody.put("cardId", cardId);
		requestBody.put("mobile", mobile);
		requestBody.put("craditCardNum", craditCardNum);

		// String requestBodyAFT = mapTostring(requestBody);
		mapin.put("requestBody", requestBody);
		System.out.println(mapin.get("requestNum"));
		System.out.println(mapin.get("serviceName"));

		// 将新增数据后的Map转为String
		String jsonin = mapTostring(mapin);
		System.out.println("请求参数" + jsonin);
//		String post = post1(url, jsonin);
//		System.out.println(post);
//		Map resmap = returnmap(post);
		// assertEquals(resmap.get("resultCode"), 2000);
	}

	
	/**
	 * 授信结果查询
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * 
	 */
	@Test
	public void b_sxjgcx01() throws IOException, InterruptedException {
		Map shouxin = shouxin();
		String creditApplyNo = shouxin.get("creditApplyNo").toString();
		String fullName = shouxin.get("fullName").toString();
		String cardId = shouxin.get("cardId").toString();
		String creditNo = shouxin.get("creditNo").toString();
		String json = properties.getProperty("sxjgcx_zc");
		System.out.println(json);
		Map mapin = returnmap(json);
		String requestNum = getrequestNum();
		mapin.put("requestNum", requestNum);
		Map requestBody = (Map) mapin.get("requestBody");
		requestBody.put("creditApplyNo", creditApplyNo);
		requestBody.put("fullName", fullName);
		requestBody.put("cardId", cardId);
		requestBody.put("creditNo", creditNo);

		mapin.put("requestBody", requestBody);
		// 将新增数据后的Map转为String
		String jsonin = mapTostring(mapin);
		System.out.println("请求参数" + jsonin);
		String post = post1(url, jsonin);
		System.out.println(post);

	}


	/** 流程--授信结果通知正常用例
	 * @throws InterruptedException 
	 * @throws IOException 
	 * 
	 */
	@Test
	public void c_sxjgtz01() throws IOException, InterruptedException{
		Map shouxin = shouxin();
		String creditApplyNo = shouxin.get("creditApplyNo").toString();
		String cardId = shouxin.get("cardId").toString();
		String creditNo = shouxin.get("creditNo").toString();
		String appLmt = shouxin.get("applyAmount").toString();
		String fullName = shouxin.get("fullName").toString();
		String json = properties.getProperty("sxjgtz_zc");
		Map mapin = returnmap(json);
		String requestNum = getrequestNum();
		mapin.put("requestNum", requestNum);
		Map requestBody = (Map) mapin.get("requestBody");
		requestBody.put("creditApplyNo", creditApplyNo);
		requestBody.put("cardId", cardId);
		requestBody.put("creditNo", creditNo);
		requestBody.put("appLmt", appLmt);
		requestBody.put("fullName", fullName);
		mapin.put("requestBody", requestBody);
		// 将新增数据后的Map转为String
		String jsonin = mapTostring(mapin);
		System.out.println("请求参数::" + jsonin);
		String post = post1(urlcz, jsonin);
		System.out.println("返回值::" + post);
	}
	

	/**
	 * 申请支用正常用例
	 * @throws InterruptedException 
	 * @throws IOException 
	 * 
	 */
	@Test
	public void d_zhiyong_zc() throws IOException, InterruptedException{
		Map<String, String> map = sxjgtz();
		String applyNo = getcreditApplyNo();
		String creditApplyNo = map.get("creditApplyNo").toString();
		String cardId = map.get("cardId").toString();
		String creditNo = map.get("creditNo").toString();
		String appLmt = map.get("appLmt").toString();
		String mobile = map.get("mobile");
		String fullName = map.get("fullName");
		String json = properties.getProperty("zysq_zc");
//		System.out.println(json);
		Map mapin = returnmap(json);
		String requestNum = getrequestNum();
		mapin.put("requestNum", requestNum);
		Map requestBody = (Map) mapin.get("requestBody");
		requestBody.put("creditApplyNo", creditApplyNo);
		requestBody.put("cardId", cardId);
		requestBody.put("creditNo", creditNo);
//		requestBody.put("appLmt", appLmt);
		requestBody.put("mobile", mobile);
		requestBody.put("applyNo", applyNo);
		requestBody.put("fullName", fullName);
		mapin.put("requestBody", requestBody);
		// 将新增数据后的Map转为String
		String jsonin = mapTostring(mapin);
		System.out.println("请求参数::" + jsonin);
		String post = post1(url, jsonin);
		System.out.println("返回值::" + post);
		
	}
	


	//授信存储基本信息
	public static Map shouxin() throws IOException, InterruptedException {
		Map<String, String> map = new HashMap<String, String>();
		String json = properties.getProperty("shouxinzc51");
//		System.out.println(json);

		Map mapin = returnmap(json);
		String requestNum = getrequestNum();
		mapin.put("requestNum", requestNum);
		Map requestBody = (Map) mapin.get("requestBody");
		// 获取creditApplyNo
		String creditApplyNo = getcreditApplyNo();
		// 生成userinfo
		Map userinfo = getUserinfo();
		// 获取custName
		String fullName = (String) userinfo.get("custName");
//		System.out.println("fullName+@@@@@@@@@@@"+fullName);
		// 获取cardId
		String cardId = (String) userinfo.get("cardNum");
		// // 生成phone
		String mobile = getPhonenm();
		// 获取信用卡号
		String craditCardNum = getcraditCardNum();
		//获取申请金额存入map中
		String applyAmount = requestBody.get("applyAmount").toString();
		System.out.println("申请授信参数applyAmount是"+ applyAmount);
		// 存入requestBody
		requestBody.put("creditApplyNo", creditApplyNo);
		requestBody.put("fullName", fullName);
		requestBody.put("cardId", cardId);
		requestBody.put("mobile", mobile);
		requestBody.put("craditCardNum", craditCardNum);

		mapin.put("requestBody", requestBody);

		// 将新增数据后的Map转为String  applyAmount
		String jsonin = mapTostring(mapin);
		String post = post1(url, jsonin);
		Map resmap = returnmap(post);
		Map responseBody = (Map) resmap.get("responseBody");
		String creditNo = responseBody.get("creditNo").toString();
		map.put("fullName", fullName);
		map.put("creditApplyNo", creditApplyNo);
		map.put("cardId", cardId);
		map.put("creditNo", creditNo);
		map.put("applyAmount", applyAmount);
		map.put("mobile", mobile);
		return map;
	}
	/**
	 * 授信结果通知后保留数据
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static Map sxjgtz() throws IOException, InterruptedException{
		Map<String,String> map = new HashMap<String, String>();
		Map shouxin = shouxin();
		
		String creditApplyNo = shouxin.get("creditApplyNo").toString();
		String cardId = shouxin.get("cardId").toString();
		String creditNo = shouxin.get("creditNo").toString();
		String appLmt = shouxin.get("applyAmount").toString();
//		System.out.println("appLmt@@@@@@@"+appLmt);
		String fullName  = shouxin.get("fullName").toString();
		String mobile  = shouxin.get("mobile").toString();
		String json = properties.getProperty("sxjgtz_zc");
//		String sql = "update biz_credit set amount=50000,rest_principal=50000,guaranty_fee=50000 where real_name ='"+fullName+"';";
//		insert(sql);
//		System.out.println(json);
		Map mapin = returnmap(json);
		String requestNum = getrequestNum();
		mapin.put("requestNum", requestNum);
		Map requestBody = (Map) mapin.get("requestBody");
		requestBody.put("creditApplyNo", creditApplyNo);
		requestBody.put("cardId", cardId);
		requestBody.put("creditNo", creditNo);
		requestBody.put("appLmt", appLmt);
		requestBody.put("fullName", fullName);
		mapin.put("requestBody", requestBody);
		// 将新增数据后的Map转为String
		String jsonin = mapTostring(mapin);
		System.out.println("请求参数::" + jsonin);
		String post = post1(url, jsonin);
		
		System.out.println("返回值::" + post);
		map.put("creditApplyNo", creditApplyNo);
		map.put("cardId", cardId);
		map.put("creditNo", creditNo);
		map.put("appLmt", appLmt);
		map.put("fullName", fullName);
		map.put("mobile", mobile);
		return map;
	}
}
