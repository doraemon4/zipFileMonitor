package com.stephen.learning.zip;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.progress.ProgressMonitor;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: jack
 * @Date: 2018/8/25 10:39
 * @Description: 文件压缩解压工具
 */
@Slf4j
public class FileCompressUtil {
    /**
     * 压缩文件目录
     *
     * @param srcPath  源文件目录
     * @param destPath 压缩后放置的目录
     * @param pass     压缩的密钥
     * @return
     * @throws ZipException
     */
    public static File encryptZip(String srcPath, String destPath, String pass) throws ZipException {
        File srcDir = new File(srcPath);
        if (!srcDir.exists()) {
            log.error("source file path not exists. srcPath=" + srcPath);
            return null;
        } else {
            File destDir = new File(destPath);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            if (destDir.isFile()) {
                log.error("destPath expected a directory, but is an exists file");
                return null;
            } else {
                String destStr = srcDir + ".zip";
                File destFile = new File(destStr);
                ZipFile zipFile = new ZipFile(destFile);
                ZipParameters parameters = new ZipParameters();
                parameters.setCompressionMethod(8);
                parameters.setCompressionLevel(5);
                parameters.setEncryptFiles(true);
                parameters.setEncryptionMethod(99);
                parameters.setAesKeyStrength(3);
                parameters.setPassword(pass);
                zipFile.addFolder(srcPath, parameters);
                return zipFile.getFile();
            }
        }
    }

    /**
     * 压缩文件集合
     *
     * @param files
     * @param destPath
     * @param pass
     * @return
     * @throws ZipException
     */
    public static File encryptZip(ArrayList<File> files, String destPath, String pass) throws ZipException {
        File destDir = new File(destPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        if (destDir.isFile()) {
            log.error("destPath expected a directory, but is an exists file");
            return null;
        } else {
            String destStr = destDir + ".zip";
            File destFile = new File(destStr);
            ZipFile zipFile = new ZipFile(destFile);
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(8);
            parameters.setCompressionLevel(5);
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(99);
            parameters.setAesKeyStrength(3);
            parameters.setPassword(pass);
            zipFile.addFiles(files, parameters);
            return zipFile.getFile();
        }
    }

    /**
     * 解压压缩文件
     *
     * @param srcFile    源文件路径
     * @param targetFile 目的文件夹路径
     * @param pass       密钥
     * @throws ZipException
     */
    public static void encryptUnZip(String srcFile, String targetFile, String pass) throws ZipException {
        ZipFile zipFile = new ZipFile(srcFile);
        File destDir = new File(targetFile);
        if (destDir.isDirectory() && !destDir.exists()) {
            destDir.mkdir();
        }

        if (zipFile.isEncrypted()) {
            zipFile.setPassword(pass);
        }
        zipFile.extractAll(targetFile);
    }

    public static void encryptUnZipWithProgress(String srcFile, String targetFile, String pass, ProgressListener listener, boolean isDeleteZip) throws ZipException {
        File file = new File(srcFile);
        ZipFile zipFile = new ZipFile(srcFile);
        zipFile.setFileNameCharset("utf-8");
        if (!zipFile.isValidZipFile()) {
            throw new ZipException("exception!");
        }
        //判断目的文件见是否存在，不存在就新建
        File destDir = new File(targetFile);
        if (destDir.isDirectory() && !destDir.exists()) {
            destDir.mkdir();
        }
        if (zipFile.isEncrypted()) {
            zipFile.setPassword(pass);
        }
        //监听
        ProgressMonitor monitor = zipFile.getProgressMonitor();
        Thread thread = new Thread(new MonitorThread(file, monitor, listener, isDeleteZip));
        thread.start();
        // true 在子线程中进行解压 , false主线程中解压
        zipFile.setRunInThread(true);
        try {
            zipFile.extractAll(targetFile);
        } catch (Exception e) {
            monitor.cancelAllTasks();
            listener.onError(e);
        }
    }
}
