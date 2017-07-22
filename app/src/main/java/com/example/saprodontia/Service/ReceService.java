package com.example.saprodontia.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.EmbossMaskFilter;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.example.saprodontia.Utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ReceService extends IntentService {

    private ServerSocket serverSocket;

    public ReceService() {
        super("ReceService");
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        while (true) {
            try {
               Socket socket = serverSocket.accept();
                new Thread (new ReceTask(socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ReceTask implements Runnable{

        private int len = 0;
        private long totallen = 0 ;
        private Socket socket;
        private byte[] data = new byte[1024];
        private byte[] msg = new byte[1024];
        private FileOutputStream fos;
        private OutputStream os;
        private InputStream is;
        private boolean done = false;


        public ReceTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {

                is = socket.getInputStream();
                os = socket.getOutputStream();
                is.read(msg);
                String str = new String(msg).trim();
                final String[] nameAndSize = str.split("=");

                os.write(str.getBytes()); //feedBack

                final Intent progress = new Intent("DOWNLOAD_TASK_PROGRESS");

                Thread p = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while(!done) {
                                    progress.putExtra("taskName", nameAndSize[0]);
                                    progress.putExtra("progress", totallen);
                                    sendBroadcast(progress);
                                    Thread.sleep(500);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });



                File folder = new File(Environment.getExternalStorageDirectory().getPath()+"/SaprodontiaDownload");

                if(!folder.exists()){
                    folder.mkdir();
                }

                File file = new File(Environment.getExternalStorageDirectory().getPath() +"/SaprodontiaDownload/" + nameAndSize[0] );


                if (!file.exists()) {
                    file.createNewFile();

                    Intent intent = new Intent();
                    intent.setAction("DOWNLOAD_TASK_INIT_DATA");
                    intent.putExtra("name",nameAndSize[0]);
                    intent.putExtra("max",nameAndSize[1]);
                    sendBroadcast(intent);
                    p.start();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(data)) != -1) {
                        fos.write(data, 0, len);
                        totallen += len;
                    }
                }

                done = true;

                fos.close();
                is.close();
                socket.close();

                progress.putExtra("taskName",nameAndSize[0]);
                progress.putExtra("progress",totallen);
                sendBroadcast(progress);

                progress.setAction("DOWNLOAD_TASK_DONE");
                sendBroadcast(progress);



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }


}
