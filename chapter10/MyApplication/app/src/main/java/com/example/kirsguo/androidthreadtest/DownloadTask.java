package com.example.kirsguo.androidthreadtest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Kirsguo on 2017/4/18.
 */

public class DownloadTask extends AsyncTask<Void,Integer,Boolean> {
    ProgressDialog progressDialog =new ProgressDialog(MainActivity.this);
    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        try {
            while (true){
                int downloadPercent = doDownload(); // 这是一个虚构的方法
                publishProgress(downloadPercent);
                if (downloadPercent >= 100)
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
//       在这里更新下载进度
        progressDialog.setMessage("Download" + values[0] + "%");
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        progressDialog.dismiss();//关闭进度对话框
//        在这里提示下载结果
        if (result){
            Toast.makeText(context,"succeed",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"failed",Toast.LENGTH_SHORT).show();
        }
    }
}
