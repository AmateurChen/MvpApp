package com.dl7.myapp.module.channel;

import com.dl7.myapp.local.dao.NewsTypeDao;
import com.dl7.myapp.local.table.NewsTypeBean;
import com.dl7.myapp.local.table.NewsTypeBeanDao;
import com.dl7.myapp.module.base.ILocalPresenter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by long on 2016/9/1.
 * 栏目管理 Presenter
 */
public class ChannelPresenter implements ILocalPresenter<NewsTypeBean> {

    private final IChannelView mView;
    private final NewsTypeBeanDao mDbDao;

    public ChannelPresenter(IChannelView view, NewsTypeBeanDao dbDao) {
        mView = view;
        mDbDao = dbDao;
    }


    @Override
    public void getData() {
        // 从数据库获取
        final List<NewsTypeBean> checkList = mDbDao.queryBuilder().list();
        final List<String> typeList = new ArrayList<>();
        for (NewsTypeBean bean : checkList) {
            typeList.add(bean.getTypeId());
        }
        Observable.from(NewsTypeDao.getAllChannels())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<NewsTypeBean, Boolean>() {
                    @Override
                    public Boolean call(NewsTypeBean newsTypeBean) {
                        // 过滤掉已经选中的栏目
                        return !typeList.contains(newsTypeBean.getTypeId());
                    }
                })
                .toList()
                .subscribe(new Subscriber<List<NewsTypeBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                    }

                    @Override
                    public void onNext(List<NewsTypeBean> uncheckList) {
                        mView.loadData(checkList, uncheckList);
                    }
                });
    }

    @Override
    public void getMoreData() {
    }

    @Override
    public void insert(NewsTypeBean data) {
        mDbDao.insert(data);
        Logger.e(mDbDao.queryBuilder().list().toString());
    }

    @Override
    public void delete(NewsTypeBean data) {
        mDbDao.delete(data);
    }

    @Override
    public void swap(int fromPos, int toPos) {
        Logger.w(mDbDao.queryBuilder().list().toString());
    }
}
