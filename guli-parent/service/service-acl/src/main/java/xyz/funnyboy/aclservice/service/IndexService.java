package xyz.funnyboy.aclservice.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * IndexService
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-03 17:50:04
 */
public interface IndexService
{
    /**
     * 根据用户名获取用户登录信息
     *
     * @param username
     * @return
     */
    Map<String, Object> getUserInfo(String username);

    /**
     * 根据用户名获取动态菜单
     *
     * @param username
     * @return
     */
    List<JSONObject> getMenu(String username);
}
