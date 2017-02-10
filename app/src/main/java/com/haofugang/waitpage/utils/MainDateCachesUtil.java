package com.haofugang.waitpage.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.haofugang.waitpage.DownLoader.DownloadProgressListener;
import com.haofugang.waitpage.DownLoader.DownloaderConfig;
import com.haofugang.waitpage.DownLoader.NetWorkHelper;
import com.haofugang.waitpage.DownLoader.WolfDownloader;
import com.haofugang.waitpage.StringUtils;

import java.io.File;
/**
 * Created by POST@hfg on 2016/12/17.
 */

public class MainDateCachesUtil {
    private String userId = "";
    private String openKey = "";
    private Context context;
    private WolfDownloader wolfDownloader;
    public MainDateCachesUtil(String userId, String openKey, Context context) {
        this.userId = userId;
        this.openKey = openKey;
        this.context = context;
    }

    /**
     *
     * @param PathUrl        图片地址
     * @param app_boot_url   图片点击跳转的地址
     */
    public void DownLoaderImg(final String PathUrl, final String app_boot_url){

        /**
         * PathUrl 上个页面传过来的Url
         * 传入的pathurl为空的时候直接结束
         * 并且初始化存储单位
         */
        if(StringUtils.isEmpty(PathUrl))
        {
            String[] sp_body = {"","0","",""};//第一个参数是 启动图的绝对路径  第三个是启动图的网络的地址用于判断是不是下次还要不要下载
            String[] sp_variable = {"wait_icon_path","wait_icon_type","img_url","app_boot_url"};
            Mysharedpreference.mysharedpreference(context,"wait_icon",sp_body,sp_variable);
            return;
        }

        /**
         *  传入的PathUrl 的和wait_icon表中存的img_url 一致的时候 结束不进行下面的操作 避免多次下载
         */
        if(PathUrl.equals(Mysharedpreference.getstring(context,"wait_icon","img_url")))
        {
            return;
        }

        File saveDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//开始下载文件
            saveDir = new File(Environment.getExternalStorageDirectory()+ "/com.postapp.post/Wait_logo/");
        } else {
            Toast.makeText(context, "SDCard不存在或者写保护", Toast.LENGTH_SHORT).show();
        }

        wolfDownloader = new DownloaderConfig()
                .setThreadNum(3)
                .setDownloadUrl(PathUrl)
                .setSaveDir(saveDir)
                .setDownloadListener(new DownloadProgressListener() {
                    @Override
                    public void onDownloadTotalSize(int totalSize) {
                    }

                    @Override
                    public void updateDownloadProgress(int size, float percent, float speed) {
                    }

                    @Override
                    public void onDownloadSuccess(String apkPath) {
                        /**
                         * 下载完成后才进行更新数据库的文件
                         * 第一个参数是 启动图的绝对路径  第三个是启动图的网络的地址用于判断是不是下次还要不要下载
                         * 第四个参数跳转对应的url
                         */
                        /*Toast.makeText(context, "下载成功\n" + apkPath, Toast.LENGTH_SHORT).show();*/

                        String[] sp_body = {apkPath,"1",PathUrl,app_boot_url};
                        String[] sp_variable = {"wait_icon_path","wait_icon_type","img_url","app_boot_url"};
                        Mysharedpreference.mysharedpreference(context,"wait_icon",sp_body,sp_variable);
                    }
                    @Override
                    public void onDownloadFailed() {
                    }

                    @Override
                    public void onPauseDownload() {
                    }

                    @Override
                    public void onStopDownload() {
                    }
                })
                .buildWolf(context);

        if(NetWorkHelper.isNetworkAvailable(context))
        {
            wolfDownloader.startDownload();
        }
    }

}
