package xyz.funnyboy.aclservice.helper;

import xyz.funnyboy.aclservice.entity.AclPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 根据权限数据构建菜单数据
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-03 19:39:59
 */
public class PermissionHelper
{
    /**
     * 使用递归方法建菜单
     *
     * @param treeNodes 树节点
     * @return {@link List}<{@link AclPermission}>
     */
    public static List<AclPermission> build(List<AclPermission> treeNodes) {
        return treeNodes.stream()
                        .filter(treeNode -> "0".equals(treeNode.getPid()))
                        .map(treeNode -> {
                            treeNode.setLevel(1);
                            return findChildren(treeNode, treeNodes);
                        })
                        .collect(Collectors.toList());
    }

    /**
     * 递归查找子节点
     *
     * @param treeNode  树节点
     * @param treeNodes 树节点集合
     * @return {@link AclPermission}
     */
    public static AclPermission findChildren(AclPermission treeNode, List<AclPermission> treeNodes) {
        treeNode.setChildren(new ArrayList<>());

        treeNodes.stream()
                 .filter(it -> treeNode.getId()
                                       .equals(it.getPid()))
                 .forEach(it -> {
                     it.setLevel(treeNode.getLevel() + 1);
                     treeNode.getChildren()
                             .add(findChildren(it, treeNodes));
                 });
        return treeNode;
    }
}
