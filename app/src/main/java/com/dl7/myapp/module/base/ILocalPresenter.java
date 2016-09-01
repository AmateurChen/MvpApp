package com.dl7.myapp.module.base;

/**
 * Created by long on 2016/9/1.
 * 提供本地数据库操作的 Presenter
 */
public interface ILocalPresenter<T> extends IBasePresenter {

    /**
     * 插入数据
     * @param data  数据
     */
    void insert(T data);

    /**
     * 删除数据
     * @param data  数据
     */
    void delete(T data);

    /**
     * 交换位置
     * @param fromPos   起始位置
     * @param toPos     目标位置
     */
    void swap(int fromPos, int toPos);
}
