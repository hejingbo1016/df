package cc.mrbird.febs.system.service;

import cc.mrbird.febs.system.entity.WechatUser;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author Hejingbo
 * @date 2020-08-05 23:39:42
 */
public interface IWechatUserService extends IService<WechatUser> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param wechatUser wechatUser
     * @return IPage<WechatUser>
     */
    IPage<WechatUser> findWechatUsers(QueryRequest request, WechatUser wechatUser);

    /**
     * 查询（所有）
     *
     * @param wechatUser wechatUser
     * @return List<WechatUser>
     */
    List<WechatUser> findWechatUsers(WechatUser wechatUser);

    /**
     * 新增
     *
     * @param wechatUser wechatUser
     */
    void createWechatUser(WechatUser wechatUser);

    /**
     * 修改
     *
     * @param wechatUser wechatUser
     */
    void updateWechatUser(WechatUser wechatUser);

    /**
     * 删除
     *
     * @param wechatUser wechatUser
     */
    void deleteWechatUser(WechatUser wechatUser);
}