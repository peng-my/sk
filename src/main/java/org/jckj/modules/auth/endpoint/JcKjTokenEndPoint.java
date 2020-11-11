package org.jckj.modules.auth.endpoint;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.jckj.core.launch.constant.AppConstant;
import org.jckj.core.log.annotation.ApiLog;
import org.jckj.core.redis.cache.JcKjRedis;
import org.jckj.core.tool.api.R;
import org.jckj.core.tool.support.Kv;
import org.jckj.core.tool.utils.Func;
import org.jckj.core.tool.utils.WebUtil;
import org.jckj.modules.auth.provider.ITokenGranter;
import org.jckj.modules.auth.provider.TokenGranterBuilder;
import org.jckj.modules.auth.provider.TokenParameter;
import org.jckj.modules.auth.utils.RandomUtil;
import org.jckj.modules.auth.utils.TokenUtil;
import org.jckj.modules.business.user.dto.UserExtDTO;
import org.jckj.modules.business.user.service.IUserExtService;
import org.jckj.modules.system.entity.UserInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

/**
 * 令牌端点
 *
 * @author Peng
 */
@ApiSort(1)
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_AUTH_NAME)
@Api(value = "用户授权认证", tags = "授权接口")
public class JcKjTokenEndPoint {
	private final JcKjRedis jcKjRedis;
	private final IUserExtService userExtService;
	@ApiLog("登录用户验证")
	@PostMapping("/oauth/token")
	@ApiOperation(value = "030 获取认证令牌", notes = "传入租户ID:tenantId,账号:account,密码:password")
	public Kv token(@ApiParam(value = "租户ID", required = true) @RequestParam String tenantId,
					@ApiParam(value = "账号", required = true) @RequestParam(required = false) String username,
					@ApiParam(value = "密码", required = true) @RequestParam(required = false) String password) {

		Kv authInfo = Kv.create();

		String grantType = WebUtil.getRequest().getParameter("grant_type");
		String refreshToken = WebUtil.getRequest().getParameter("refresh_token");

		String userType = Func.toStr(WebUtil.getRequest().getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);

		TokenParameter tokenParameter = new TokenParameter();
		tokenParameter.getArgs().set("tenantId", tenantId).set("username", username).set("password", password).set("grantType", grantType).set("refreshToken", refreshToken).set("userType", userType);

		ITokenGranter granter = TokenGranterBuilder.getGranter(grantType);
		UserInfo userInfo = granter.grant(tokenParameter);

		if (userInfo == null || userInfo.getUser() == null) {
			return authInfo.set("error_code", HttpServletResponse.SC_BAD_REQUEST).set("error_description", "用户名或密码不正确");
		}

		if (Func.isEmpty(userInfo.getRoles())) {
			return authInfo.set("error_code", HttpServletResponse.SC_BAD_REQUEST).set("error_description", "未获得用户的角色信息");
		}

		return TokenUtil.createAuthInfo(userInfo);
	}
	@PostMapping("/oauth/register")
	@ApiOperation(value = "用户注册",notes = "传入userExtDTO ")
	public R register(@RequestBody UserExtDTO userExtDTO) {
		return R.status(userExtService.register(userExtDTO));
	}
	@PostMapping("/oauth/resetPassword")
	@ApiOperation(value = "用户重置密码",notes = "传入userExtDTO ")
	public R resetPassword(@RequestBody UserExtDTO userExtDTO) {
		return R.status(userExtService.resetPassword(userExtDTO));
	}
	@GetMapping("/oauth/captchas")
	@ApiOperation(value = "获取验证码{手机或者邮箱}")
	public Kv captchas(String email,String phone,String type) {
		Integer types = Integer.valueOf(type);
		//生成随机数
		String code =null;
		String key=null;
		String value=null;
		switch (types){
			case 1:
				key="phone";
				value=phone;
				Object o = jcKjRedis.get(phone);
				if (o!=null){
					code=o.toString();
				}else {
					code= RandomUtil.getSixBitRandom();
					// 存入redis并设置过期时间为5分钟
					jcKjRedis.setEx(phone, code, Duration.ofMinutes(5));
				}
				break;
			case 2:
				key="email";
				value=email;
				Object o1 = jcKjRedis.get(email);
				if (o1!=null){
					code=o1.toString();
				}else {
					code=RandomUtil.getSixBitRandom();
					// 存入redis并设置过期时间为5分钟
					jcKjRedis.setEx(email, code, Duration.ofMinutes(5));
				}
				break;
			default:
				break;
		}
		// 将key和base64返回给前端
		return Kv.create().set(key,value).set("VerificationCode", code);
	}

}
