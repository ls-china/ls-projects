package com.ls.user.realm;

import java.io.Serializable;

public interface IResource extends Serializable {
    /**
     * 访问权限，全部，创建，更新，读取，删除
     */
    public static enum AccessType {
        /**
        * 拥有全部权限
        */
        ALL,
        /**
         * 创建权限
         */
        CREATE,
        /**
         * 更新权限
         */
        UPDATE,
        /**
         * 读取权限
         */
        READ,
        /**
         * 删除权限
         */
        DELTE;
    }

    public boolean isAccessable(IRole role, IResource.AccessType accessType);

}
