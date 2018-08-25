package com.stephen.learning.zip;

/**
 * @Auther: jack
 * @Date: 2018/8/25 10:32
 * @Description: 进度监听器
 */
public interface ProgressListener {
    void onStart();

    void onProgress(long progress);

    void onError(Exception e);

    void onCompleted();
}
