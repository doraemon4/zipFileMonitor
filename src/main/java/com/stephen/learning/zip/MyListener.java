package com.stephen.learning.zip;

/**
 * @Auther: jack
 * @Date: 2018/8/25 12:15
 * @Description:
 */
public class MyListener implements ProgressListener {
    @Override
    public void onStart() {
        System.out.println("开始..........");
    }

    @Override
    public void onProgress(long progress) {
        System.out.println("进度为:"+progress);
    }

    @Override
    public void onError(Exception e) {
        System.out.println("失败..........");
    }

    @Override
    public void onCompleted() {
        System.out.println("成功..........");
    }
}
