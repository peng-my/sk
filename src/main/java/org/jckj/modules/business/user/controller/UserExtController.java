package org.jckj.modules.business.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.jckj.core.mp.support.Condition;
import org.jckj.core.mp.support.Query;
import org.jckj.core.secure.JcKjUser;
import org.jckj.core.secure.utils.AuthUtil;
import org.jckj.core.tool.api.R;
import org.jckj.modules.business.user.entity.UserExt;
import org.jckj.modules.business.user.service.IUserExtService;
import org.jckj.modules.business.user.vo.UserAdminExtVO;
import org.jckj.modules.business.user.vo.UserExtVO;
import org.jckj.modules.business.user.vo.UserManageExtVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("userext/userext")
@Api(value = "用户表", tags = "用户表接口")
public class UserExtController {
    private final IUserExtService userExtService;

    /**
     * 用户信息
     */
    @GetMapping("/getInfo")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "用户信息", notes = "type 1为主 2为子")
    public R<UserExtVO> getInfo(String type) {
        return R.data(userExtService.getInfo(type));
    }

    /**
     * 修改昵称
     */
    @PostMapping("/updataName")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "昵称", notes = "name")
    public R updataName(String name) {
        return R.status(userExtService.updataName(name));
    }

    /**
     * 添加子账号
     */
        @PostMapping("/submitOrUpdata")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "添加子账号", notes = "userExt")
    public R submitOrUpdata(@Valid @RequestBody UserExt userExt) {
        return R.status(userExtService.submitOrUpdata(userExt));
    }

    /**
     * 子账号分页
     */
    @GetMapping("/getIpage")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "子账号分页", notes = "page,userExt")
    public R<IPage<UserExt>> getIpage(Query query, UserExt userExt) {
        return R.data(userExtService.getIpage(Condition.getPage(query),userExt));
    }

    /**
     * 修改密码
     */
    @PostMapping("/updataPassword")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改密码", notes = "password,id")
    public R updataPassword(String password,String newPassword) {
        return R.status(userExtService.updataPassword(password,newPassword));
    }

//    /**
//     * 注册
//     */
//    @GetMapping("/resetPasswords")
//    @ApiOperationSupport(order = 6)
//    @ApiOperation(value = "注册", notes = "password,id")
//    public R register(String password,String id) {
//        return R.status(userExtService.resetPassword(password,Long.valueOf(id)));
//    }

    /**
     * 自定义分页用户管理
     */
    @GetMapping("/selectUserManageExtVOPage")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "自定义分页用户管理", notes = "page,userManageExtVO")
    public R<IPage<UserManageExtVO>> selectUserManageExtVOPage(Query query, UserManageExtVO userManageExtVO) {
        return R.data(userExtService.selectUserManageExtVOPage(Condition.getPage(query),userManageExtVO));
    }

    /**
     * 自定义分页用户管理
     */
    @GetMapping("/hasBan")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "自定义分页用户管理", notes = "userId")
    public R hasBan(String userId) {
        return R.status(userExtService.hasBan(Long.valueOf(userId)));
    }

    /**
     * 自定义管理员分页
     */
    @GetMapping("/selectAdminIpage")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "自定义管理员分页", notes = "page,account")
    public R selectAdminIpage(IPage<UserAdminExtVO> page, String account) {
        return R.data(userExtService.selectAdminIpage(page,account));
    }

    /**
     * 保存头像
     */
    @PostMapping("/saveHeadPortrait")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "保存头像", notes = "url")
    public R saveHeadPortrait(@ApiParam(value = "用户头像url", required = true) @RequestParam String url) {
        return R.status(userExtService.saveHeadPortrait(url));
    }

    /**
     * 获取签名
     */
    @PostMapping("/generateUserSig")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取签名", notes = "")
    public R generateUserSig() {
        JcKjUser user = AuthUtil.getUser();
        String userId="";
        if (user!=null){
            userId=String.valueOf(user.getUserId());
        }
        return R.data(userExtService.generateUserSig(userId));
    }
}
