//package org.jckj.modules.business.test.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alipay.api.AlipayApiException;
//import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.AllArgsConstructor;
//import org.jckj.core.boot.ctrl.JcKjController;
//import org.jckj.core.log.logger.JcKjLogger;
//import org.jckj.core.tool.api.R;
//import org.jckj.modules.business.pay.payentity.AliPay;
//import org.jckj.modules.business.pay.utils.PayUtils;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
///**
//	* 控制器
//	*
//	* @author Chill
//	*/
//@RestController
//@AllArgsConstructor
//@RequestMapping("/test")
//@Api(value = "角色", tags = "角色")
////@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
//public class TestController extends JcKjController {
//private JcKjLogger jcKjLogger;
//private MongoTemplate mongoTemplate;
//	private static final String getUserRoId="get.userRoleId";
//	private static final String getAdminRoId="get.adminRoleId";
//	/**
//	 * 详情
//	 */
////	@ApiLog("测试查看,查看详情")
//	@PostMapping("/detail")
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value = "详情", notes = "传入空")
//	public R<String> detail(String json) {
//		json="{\"name\":\"test\"}";
//		Query query = new Query();
//		query.addCriteria(Criteria.where("name").is("test"));
//		JSONObject data = JSONObject.parseObject(json);
//		List<JSONObject> jsonObjects = mongoTemplate.find(query, JSONObject.class,"123");
//		List<JSONObject> all = mongoTemplate.findAll(JSONObject.class, "123");
//		String s="已经存在";
//		if (jsonObjects.size()>0){
////			Update update=new Update();
////			update.set("name","test1");
////		mongoTemplate.updateFirst(query,update,"123");
////		s="修改成功";
//			mongoTemplate.remove(query,JSONObject.class,"123");
//			s="删除成功";
//		}else {
//			JSONObject save = mongoTemplate.insert(data,"123");
//			System.out.println(save);
//			s="添加成功";
//		}
//
//
////		id =Integer.valueOf("s");
////		jcKjLogger.warn("123","测试查看,查看详情123");
//		return R.data(s);
//	}
//
//	/**
//	 * 列表
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "列表", notes = "传入空")
//	public R<String> list() {
//		return R.data("列表");
//	}
//
//	/**
//	 *
//	 */
//	@GetMapping("/pay")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "列表", notes = "传入空")
//	public void pay(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {
//		PayUtils payUtils=new PayUtils();
//		payUtils.aliPayCs(request,response);
//	}
//	//2.a1lipay支持同步返回地址
//	@GetMapping("/getReturnUrlInfo")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "同步通知", notes = "传入空")
//	public String alipayReturnUrlInfo(HttpServletRequest request) {
//		PayUtils payUtils=new PayUtils();
//		String synchronous = payUtils.synchronous(request);
//		return synchronous;
//	}
//	//3.alipay异步通知调用地址
//	@PostMapping("/getNotifyUrlInfo")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "异步通知", notes = "传入空")
//	public void aliPayNotifyUrlInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		PayUtils payUtils=new PayUtils();
//		AliPay notify = payUtils.notify(request, response);
//		System.err.println(notify);
//	}
//	@PostMapping("/picture")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "异步通知", notes = "传入空")
//	public void picture(MultipartFile file,HttpServletResponse response)  {
////		String s = Base64.getEncoder().encodeToString(file.getBytes());
//////		BASE64Encoder base64Encoder =new BASE64Encoder();
////		//截取文件名的后缀
////		String contentType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//////		String contentType = file.getContentType();
//////		String contentType = "docx";
////		String originalFilename = file.getOriginalFilename();
//////		String encode = base64Encoder.encode(file.getBytes());
//////		System.err.println(StringUtil.format("文件类型：{}",contentType));
////////		String base64EncoderImg = file.getOriginalFilename()+","+ base64Encoder.encode(file.getBytes());
//////		JSONObject en1 = TxApi.picture(s, "en");
//////		System.err.println(en1);
//////		JSONObject en = YouDaoApi.picture(encode, "en");
//////		System.err.println(en);
////		JSONObject en = YouDaoApi.upload(s, originalFilename, contentType, "en");
////		System.out.println(en);
////		if (en.containsKey("errorCode")){
////			Integer errorCode = Integer.valueOf(en.get("errorCode").toString());
////			if (errorCode==0){
////				String flownumber = en.get("flownumber").toString();
////				JSONObject query = YouDaoApi.query(flownumber);
////				if (query.containsKey("errorCode")){
////					Integer errorCode1 = Integer.valueOf(query.get("errorCode").toString());
////					Integer status = Integer.valueOf(query.get("status").toString());
////					if (errorCode1==0&&status==4){
////						String word = YouDaoApi.download(flownumber, "word");
////						System.err.println(word);
////					}
////				}
////			}
////		}
////		InputStream file1 = null;
////		try {
////			file1 = YouDaoApi.file(file);
////			int index;
////			byte[] bytes = new byte[1024];
////			FileOutputStream downloadFile = new FileOutputStream("D:\\testfile\\1.docx");
////			while ((index = file1.read(bytes)) != -1) {
////				downloadFile.write(bytes, 0, index);
////				downloadFile.flush();
////			}
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//
//
//
////		file1.close();
////		downloadFile.close();
//
//
////		String s = CountDoc.readStream(result);
////		System.out.println(s);
////		System.out.println(file1);
//	}
//}
