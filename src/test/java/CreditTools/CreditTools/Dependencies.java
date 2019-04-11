package CreditTools.CreditTools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.cloudloan.data.crypt.DecryptTools;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Dependencies {

	public static Workbook workbook = null;
	public static WritableWorkbook wwb = null;
	static OkHttpClient client = new OkHttpClient();
	public static final MediaType JSON = MediaType
			.parse("application/json; charset=utf-8");
	public static final MediaType JSON1 = MediaType
			.parse("application/x-www-form-urlencoded; charset=utf-8");
	// 用力集合
	public static ArrayList alldata = null;
	public static ArrayList<String> evdata = null;

	// 入参为测试地址路径，和json的参数形式，同步传输
	public static String post(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder()
				.addHeader("X-TBC-SOURCE", "tbc_zhtb_czb")
				.addHeader("X-TBC-SIGN", "").url(url).post(body).build();
		Response response = client.newCall(request).execute();
		String result = response.body().string();
		// 结果返回为字符串
		return result;
	}
	// 入参为测试地址路径，和json的参数形式，同步传输
		public static String post1(String url, String json) throws IOException {
			RequestBody body = RequestBody.create(JSON, json);
			Request request = new Request.Builder().
					url(url).post(body).build();
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			// 结果返回为字符串
			return result;
		}

	// GET方法
	public static String get(String url) throws IOException {
		OkHttpClient client = new OkHttpClient();
		String result = null;
		Request request = new Request.Builder().url(url).build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	// 生成手机号
	public static String getPhonenm() {
		StringBuilder str = new StringBuilder("");
		Random random = new Random();
		// 随机生成数字，并添加到字符串
		for (int i = 0; i < 8; i++) {
			str.append(random.nextInt(10));
		}
		String phnum = "150" + str;
		return phnum;

	}

	// 生成sourceUserId
	public static String getsourceUserId() {
		StringBuilder str = new StringBuilder("");
		Random random = new Random();
		// 随机生成数字，并添加到字符串
		for (int i = 0; i < 4; i++) {
			str.append(random.nextInt(10));
		}

		return str.toString();

	}

	// 生成bankCardNo
	public static String getbankCardNo() {
		StringBuilder str = new StringBuilder("");
		Random random = new Random();
		// 随机生成数字，并添加到字符串
		for (int i = 0; i < 8; i++) {
			str.append(random.nextInt(10));
		}
		String bankCardNo = "62226666" + str;
		return bankCardNo;

	}
	// 生成bankCardNo
		public static String getcraditCardNum() {
			StringBuilder str = new StringBuilder("");
			Random random = new Random();
			// 随机生成数字，并添加到字符串
			for (int i = 0; i < 11; i++) {
				str.append(random.nextInt(10));
			}
			String bankCardNo = "62226666" + str;
			return bankCardNo;

		}

	// 51生成creditApplyNo
	public static String getcreditApplyNo() {
		StringBuilder str = new StringBuilder("");
		Random random = new Random();
		// 随机生成数字，并添加到字符串
		for (int i = 0; i < 10; i++) {
			str.append(random.nextInt(10));
		}
		
		return str.toString();

	}

	// 51生成productId
	public static String getproductId() {
		StringBuilder str = new StringBuilder("");
		Random random = new Random();
		// 随机生成数字，并添加到字符串
		for (int i = 0; i < 5; i++) {
			str.append(random.nextInt(10));
		}
		String productId = "channel" + str;
		return productId;

	}

	// 51生成requestNum
	public static String getrequestNum() {
		StringBuilder str = new StringBuilder("");
		Random random = new Random();
		// 随机生成数字，并添加到字符串
		for (int i = 0; i < 6; i++) {
			str.append(random.nextInt(10));
		}
		String requestNum = "51CARDno" + str;
		return requestNum;

	}

	// czb 生成transactionId
	public static String gettransactionId() {
		StringBuilder str = new StringBuilder("");
		Random random = new Random();
		// 随机生成数字，并添加到字符串
		for (int i = 0; i < 4; i++) {
			str.append(random.nextInt(10));
		}
		String transactionId = "Test_" + str;
		return transactionId;

	}

	public static ArrayList readExcel(String path) {
		InputStream in = null;
		alldata = new ArrayList();
		evdata = new ArrayList<String>();
		String[] str = null;
		try {

			// 读取输入流
			in = new FileInputStream(path);
			workbook = Workbook.getWorkbook(in);
			// 读取sheet页默认从第一页开始
			Sheet s1 = workbook.getSheet(0);
			// Sheet s2 = readwb.getSheet("");
			// 读取列数
			int columns = s1.getColumns();
			// System.out.println("lieshu:" + columns);
			// 读取行数
			int rows = s1.getRows();
			// System.out.println("行數：" + rows);
			for (int i = 2; i < rows; i++) {
				str = new String[3];
				// for (int j = 1; j < columns; j++) {
				// if(j==1 || j==3 || j==4){

				Cell cell1 = s1.getCell(1, i);
				// System.out.print(cell1.getContents() + " ");
				if (cell1.getContents() != "") {
					str[0] = cell1.getContents();
				}
				Cell cell3 = s1.getCell(3, i);
				if (cell3.getContents() != "") {
					str[1] = cell3.getContents();
				}
				// System.out.print(cell3.getContents() + " ");
				Cell cell4 = s1.getCell(4, i);
				if (cell4.getContents() != "") {
					str[2] = cell4.getContents();
					// System.out.print(cell4.getContents() + " ");
				}
				alldata.add(str);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				// System.out.println("closed inputstream");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// System.out.println("读取测试用例完成");
		// System.out.println(alldata.size());
		return alldata;
	}

	// 获取新的身份信息
	public static Map getUserinfo() throws InterruptedException {
		Map<String, String> map = new HashMap<String, String>();
		System.setProperty("webdriver.chrome.driver", "d:\\chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
		// 设置 chrome 的无头模式
		chromeOptions.setHeadless(Boolean.TRUE);
		// 启动一个 chrome 实例
		WebDriver driver = new ChromeDriver(chromeOptions);
		// WebDriver driver = new HtmlUnitDriver();
		// Thread.sleep(2000);
		driver.manage().window().maximize();
		driver.get("http://sfz.ckd.cc/idcard.php");
		Thread.sleep(2000);
		String nameid = driver
				.findElement(
						By.xpath("/html/body/div[3]/div[3]/div[1]/div[2]/div[2]/table/tbody/tr[9]/td[1]"))
				.getText();
		String sex = driver
				.findElement(
						By.xpath("/html/body/div[3]/div[3]/div[1]/div[2]/div[2]/table/tbody/tr[9]/td[2]"))
				.getText();
		String[] nameId = nameid.split(" ");
		String name = nameId[0];
		String idno = nameId[1];
		driver.quit();
		map.put("custName", name);
		map.put("cardNum", idno);
		map.put("sex", sex);
		return map;
	}

	// 把String 结果转换为Map
	public static Map returnmap(String str) {
		// Map<String, Object> map =
		// com.alibaba.fastjson.JSON.parseObject(str,Map.class);
		// Map<String, Object> map =
		// FastJsonConfig.JSON.parseObject(str,Map.class);
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject
				.parseObject(str);
		// System.out.println(jsonObject.toString());
		Map<String, Object> map = (Map<String, Object>) jsonObject;
		return map;
	}
	public static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://39.106.164.42:9919/51card";
	    String username = "root";
	    String password = "dJ_xw2ve_Dc9_1OiY3";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	public static int insert(String sql) {
	    Connection conn = getConn();
	    int i = 0;
	    
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	// map zhuan string
	public static String mapTostring(Map map) {
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
	}

	public static void main(String[] args) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		// String a =
		// get("https://blog.csdn.net/wanglong12341/article/details/84257854");
		// System.out.println(a);
		// String a = getPhonenm();
		// System.out.println(a);
		// readExcel();
		// InputStream in =
		// AllTest.class.getResourceAsStream("json.properties");
		// Properties properties = new Properties();
		// properties.load(in);
		// String a = properties.getProperty("json");
		// System.out.println(a);
		// String url =
		// "http://39.106.164.42:9011/api/v1/credit/queryUserAmount";
		// String post1 = post(url, a);
		// System.out.println(post1);
		// Map map = new HashMap();
		// map.put("a", 1);
		// map.put("b", 2);
		// String c = mapTostring(map);
		// System.out.println(c);
		// System.out.println(map.get("name"));
		// ArrayList b = new ArrayList();
		// ArrayList a = new ArrayList();
		// a.add("1");
		// a.add("21111");
		// b.add(a);
		// System.out.println(b.size());
		// JSONObject jsonObject=JSONObject.fromObject(map);
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
		// 设置 chrome 的无头模式
		chromeOptions.setHeadless(Boolean.TRUE);
		// 启动一个 chrome 实例
		WebDriver driver = new ChromeDriver(chromeOptions);
		// WebDriver driver = new HtmlUnitDriver();
		// Thread.sleep(2000);
		driver.manage().window().maximize();
		driver.get("http://sfz.ckd.cc/idcard.php");
		Thread.sleep(2000);
		String nameid = driver
				.findElement(
						By.xpath("/html/body/div[3]/div[3]/div[1]/div[2]/div[2]/table/tbody/tr[9]/td[1]"))
				.getText();
		String sex = driver
				.findElement(
						By.xpath("/html/body/div[3]/div[3]/div[1]/div[2]/div[2]/table/tbody/tr[9]/td[2]"))
				.getText();
		String[] nameId = nameid.split(" ");
		String name = nameId[0];
		String idno = nameId[1];
		driver.quit();
		System.out.println(name+idno+sex);
	}

}