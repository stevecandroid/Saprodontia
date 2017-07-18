package com.example.saprodontia.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.example.saprodontia.Utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        private Socket socket;
        private byte[] data = new byte[1024];
        private byte[] name = new byte[1024];
        private FileOutputStream fos;
        private InputStream is;


        public ReceTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                is = socket.getInputStream();
                is.read(name);
                String str = new String(name).trim();
                LogUtil.e(str);
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + str + ".apk");
                if (!file.exists()) {
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                while ((len = is.read(data)) != -1) {
                    fos.write(data, 0, len);
                }
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
