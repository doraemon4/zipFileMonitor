package com.stephen.learning.zip;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.progress.ProgressMonitor;

import java.io.File;

/**
 * @Auther: jack
 * @Date: 2018/8/25 11:30
 * @Description: 监听线程
 */
@Slf4j
@AllArgsConstructor
public class MonitorThread implements Runnable {
    /**源文件压缩包*/
    private ProgressMonitor monitor;
    private ProgressListener listener;

    @Override
    public void run() {
        int precentDone = 0;
        try{
            if (listener == null) {
                return;
            }
            listener.onStart();
            while (true && !monitor.isCancelAllTasks()) {
                // 每隔50ms,发送一个解压进度出去
                Thread.sleep(50);
                precentDone = monitor.getPercentDone();
                listener.onProgress(precentDone);
                if (precentDone >= 100) {
                    break;
                }
            }
            listener.onCompleted();
        }catch (Exception e){
            listener.onError(e);
        }
    }
}
