package com.zee.club.home.data.source.http.service;

import io.reactivex.observers.DisposableObserver;

public abstract class RestfulCallback<T> extends DisposableObserver<T> {

    public abstract void onSuccess(T t);

    public abstract void onFailure(Throwable e);

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e);
    }

    @Override
    public void onComplete() {

    }
}
