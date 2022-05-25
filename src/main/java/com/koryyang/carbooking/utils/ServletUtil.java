package com.koryyang.carbooking.utils;

import com.koryyang.carbooking.model.bo.user.UserBO;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
public class ServletUtil {

    /**
     * store user info of current thread
     */
    private static final ThreadLocal<UserBO> CURRENT_TENANT_HOLDER = new ThreadLocal<>();

    /**
     * get current user info
     * @return userBO
     */
    public static UserBO getCurrentUser() {
        return CURRENT_TENANT_HOLDER.get();
    }

    /**
     * set current user info
     * @param userBO userBO
     */
    public static void setCurrentUser(UserBO userBO) {
        CURRENT_TENANT_HOLDER.set(userBO);
    }

    /**
     * remove current user info
     */
    public static void removeCurrentUser() {
        CURRENT_TENANT_HOLDER.remove();
    }

}
