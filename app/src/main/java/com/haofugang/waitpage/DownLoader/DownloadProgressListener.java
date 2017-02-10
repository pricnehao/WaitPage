package com.haofugang.waitpage.DownLoader;

/**
 * Desc:
 * POST HFG
 * 2017年1月3日16:19:28
 */
public interface DownloadProgressListener {
    void onDownloadTotalSize(int totalSize);

    /**
     * Real-time update downloading progress
     *
     * @param size    downloading progress(Byte)
     * @param percent downloading percent(%)
     * @param speed   downloading speed(KB/S)
     */
    void updateDownloadProgress(int size, float percent, float speed);

    void onDownloadSuccess(String apkPath);

    void onDownloadFailed();

    void onPauseDownload();

    void onStopDownload();
}
