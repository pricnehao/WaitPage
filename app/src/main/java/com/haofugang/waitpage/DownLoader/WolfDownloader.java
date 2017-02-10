package com.haofugang.waitpage.DownLoader;

import android.content.Context;

/**
 * Desc: Wolf File Downloader [Simple]
 * Tips: Support multithreading breakpoint continuingly
 * POST HFG
 * 2017年1月3日16:19:28
 */
public class WolfDownloader {
    FileDownloader fileDownloader;

    public WolfDownloader(Context mContext, DownloaderConfig config) {
        this.fileDownloader = new FileDownloader(mContext);
        this.fileDownloader.setConfig(config);
    }

    public void readHistory(HistoryCallback historyCallback){
        this.fileDownloader.readHistory(historyCallback);
    }

    public void startDownload() {
        fileDownloader.start();
    }

    public void pauseDownload() {
        fileDownloader.pause();
    }

    public void restartDownload() {
        fileDownloader.restart();
    }

    public void stopDownload() {
        fileDownloader.stop();
    }

}
