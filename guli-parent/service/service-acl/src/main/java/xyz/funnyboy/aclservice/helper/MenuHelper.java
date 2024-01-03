package xyz.funnyboy.aclservice.helper;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;
import xyz.funnyboy.aclservice.entity.AclPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据权限数据构建登录用户左侧菜单数据
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-03 19:43:36
 */
public class MenuHelper
{
    /**
     * 构建菜单
     *
     * @param treeNodes 树节点
     * @return {@link List}<{@link JSONObject}>
     */
    public static List<JSONObject> build(List<AclPermission> treeNodes) {
        List<JSONObject> menus = new ArrayList<>();
        if (treeNodes.size() != 1) {
            return menus;
        }

        // 顶级菜单
        AclPermission topNode = treeNodes.get(0);
        // 一级菜单集合
        List<AclPermission> oneMenuList = topNode.getChildren();
        for (AclPermission one : oneMenuList) {
            JSONObject oneMenu = buildMenu(one, true, false);

            List<JSONObject> children = new ArrayList<>();

            //  二级菜单集合
            List<AclPermission> twoMenuList = one.getChildren();
            for (AclPermission two : twoMenuList) {
                JSONObject twoMenu = buildMenu(two, false, false);
                children.add(twoMenu);

                // 三级菜单集合
                List<AclPermission> threeMenuList = two.getChildren();
                for (AclPermission three : threeMenuList) {
                    if (StringUtils.isEmpty(three.getPath())) {
                        continue;
                    }

                    JSONObject threeMenu = buildMenu(three, false, true);
                    children.add(threeMenu);
                }
            }
            oneMenu.put("children", children);
            menus.add(oneMenu);
        }
        return menus;
    }

    /**
     * 构建菜单
     *
     * @param permission 许可
     * @param top        是否顶部节点
     * @param hidden     隐藏
     * @return {@link JSONObject}
     */
    private static JSONObject buildMenu(AclPermission permission, boolean top, boolean hidden) {
        // 菜单信息
        JSONObject menu = new JSONObject();
        menu.put("path", permission.getPath());
        menu.put("component", permission.getComponent());
        if (top) {
            menu.put("redirect", "noredirect");
        }
        menu.put("name", "name_" + permission.getId());
        menu.put("hidden", hidden);
        // 菜单元信息
        JSONObject twoMeta = new JSONObject();
        twoMeta.put("title", permission.getName());
        if (top) {
            twoMeta.put("icon", permission.getIcon());
        }
        menu.put("meta", twoMeta);
        return menu;
    }
}
