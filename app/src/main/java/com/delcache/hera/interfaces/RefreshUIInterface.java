package com.delcache.hera.interfaces;

/**
 * Created by jack.yang on 16/12/23.
 */
public interface RefreshUIInterface {
    /**
     * 更新UI
     */
    void refreshUI(Object object);

    /**
     * 更新UI
     */
    void refreshWithResult(Object object, int type);


    void doError(int type);
}
