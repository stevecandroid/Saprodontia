package com.example.saprodontia.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.saprodontia.Adapter.Adapter;
import com.example.saprodontia.Adapter.PhotoAdapter;
import com.example.saprodontia.Adapter.SendPagerAdapter;
import com.example.saprodontia.Application.App;
import com.example.saprodontia.Models.ContentModle;
import com.example.saprodontia.Models.FileInfo;
import com.example.saprodontia.Models.AppInfoModle;
import com.example.saprodontia.Models.WifiModle;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.ToastUtil;
import com.example.saprodontia.View.RecycleDecoration;

import java.util.ArrayList;
import java.util.List;

public class SendActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;

    private AppInfoModle mAppInfoModle;
    private ContentModle mContentModle;
    private WifiModle socketModle;

    private Adapter appAdapter;
    private Adapter docuAdapter;
    private Adapter videoAdapter;
    private Adapter musicAdapter;
    private PhotoAdapter photoAdapter;



    private ViewPager viewPager;
    List<View> views ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        socketModle = new WifiModle(this);

        mContentModle = new ContentModle(this);
        mAppInfoModle = new AppInfoModle(this);
        views = new ArrayList<>();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);


        FloatingActionButton bt_ensure = (FloatingActionButton) findViewById(R.id.bt_ensure);
        bt_ensure.setOnClickListener(this);


        tabLayout.setupWithViewPager(viewPager);

        initAdapter();
        initListener();

        tabLayout.getTabAt(0).setText("图片");
        tabLayout.getTabAt(1).setText("应用");
        tabLayout.getTabAt(2).setText("文档");
        tabLayout.getTabAt(3).setText("视频");
        tabLayout.getTabAt(4).setText("音乐");
//        AppInfoModle modleAt = new AppInfoModle();
//        List<ApplicationInfo> infos = AppInfoModle.get();
//        ApplicationInfo i = infos.get(1);
//
//        for(ApplicationInfo info : infos){
//            LogUtil.e(info.loadLabel(getPackageManager()));
//        }
//        List<FileInfo> t = AppInfoModle.getSimAppinfos();


//
//        final WifiP2pManager wm = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
//        final WifiP2pManager.Channel channel=  wm.initialize(this,getMainLooper(),null);
//        mWifiReceiver = new WifiReceiver(wm,channel,this);


//        wm.discoverPeers(channel, new WifiP2pManager.ActionListener() {
//            @Override
//            public void onSuccess() {
//                LogUtil.e("搜索成功");
////                wm.requestPeers(channel, new WifiP2pManager.PeerListListener() {
////                    @Override
////                    public void onPeersAvailable(WifiP2pDeviceList peers) {
////                        LogUtil.e("AVAILABLLE");
////                        LogUtil.e(peers.getDeviceList().size());
////                    }
////                });
//            }
//
//            @Override
//            public void onFailure(int reason) {
//                LogUtil.e("搜索失败");
//            }
//        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                    byte[] buffer = new byte[1024*8];
//                    WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//                    WifiInfo wifiInfo = wm.getConnectionInfo();
//                    LogUtil.e(Formatter.formatIpAddress(wifiInfo.getIpAddress()));
//                    LogUtil.e(Formatter.formatIpAddress(wm.getDhcpInfo().serverAddress));
//                    Socket socket = new Socket(Formatter.formatIpAddress(wm.getDhcpInfo().serverAddress),65535);
////                    Socket socket = new Socket(InetAddress.getLocalHost(),65535);
//                    FileInputStream fis = new FileInputStream(new File(infos.get(15).getLocation()));
//                    OutputStream os =  socket.getOutputStream();
//                    int len = 0;
//                    while(( len =fis.read(buffer) ) !=-1){
//                        LogUtil.e("SENDING");
//                        os.write(buffer,0,len);
//                    }
//
//                    fis.close();
//                    os.close();
//                    socket.close();
//
//
//
//
//                } catch (IOException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }

    @Override
    protected void onResume() {

//        IntentFilter mIntentFilter = new IntentFilter();
//        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
//        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
//        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
//        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

//        registerReceiver(mWifiReceiver,mIntentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
//        unregisterReceiver(mWifiReceiver);
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ensure: {
                socketModle.shareFile(((App)(getApplicationContext())).getSenDatas());
                break;
            }
        }
    }

    private void initAdapter(){

        List<Adapter> listAdapter = new ArrayList<>();
        appAdapter = new Adapter( this,mAppInfoModle.initSimAppInfos());
        videoAdapter = new Adapter(this, mContentModle.getVideosFile());
        docuAdapter = new Adapter(this, mContentModle.getFileInfos());
        musicAdapter = new Adapter(this,mContentModle.getMusicInfos());
        photoAdapter = new PhotoAdapter(mContentModle.getImagesFolder(),this);

        listAdapter.add(appAdapter);
        listAdapter.add(docuAdapter);
        listAdapter.add(videoAdapter);
        listAdapter.add(musicAdapter);

        final RecyclerView recyphoto = new RecyclerView(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (recyphoto.getAdapter().getItemViewType(position)==1){
                    return 3;
                }else {
                    return 1;
                }
            }
        });

        photoAdapter.setOnSendDataChangeListener(new PhotoAdapter.OnSendDataChangeListener() {
            @Override
            public void onSendDataChange(List<FileInfo> list) {
                title.setText("当前已选择: " + list.size() +" 个");
            }
        });

        photoAdapter.setOnScrollToBottomListener(new PhotoAdapter.OnScrollToBottomListener() {
            @Override
            public void onBottom(int pos, int length) {
                int vPos = ((GridLayoutManager)(recyphoto.getLayoutManager())).findLastVisibleItemPosition();
               if(vPos == pos || pos == vPos-1){
                   recyphoto.smoothScrollToPosition(pos+1);
               }
            }
        });



        recyphoto.setAdapter(photoAdapter);
        recyphoto.setLayoutManager(gridLayoutManager);
        recyphoto.setItemAnimator(new DefaultItemAnimator());
        recyphoto.addItemDecoration(new RecycleDecoration());
        views.add(recyphoto);

        for(int i = 0 ; i < listAdapter.size() ; i++){
            RecyclerView recyclerView = new RecyclerView(this);
            recyclerView.setAdapter(listAdapter.get(i));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new RecycleDecoration());

            listAdapter.get(i).setmOnSenDatasChangedListener(new Adapter.onSenDatasChangedListener() {
                @Override
                public void onDataChanged(List<FileInfo> fileInfos) {
                    title.setText("当前已选择: " + fileInfos.size() +" 个");
                }
            });

            views.add(recyclerView);
        }


        SendPagerAdapter sendPagerAdapter = new SendPagerAdapter(views);
        viewPager.setAdapter(sendPagerAdapter);
    }

    private  void initListener(){

        mAppInfoModle.setmOnDataChangeListener(new AppInfoModle.OnDataChangeListener() {
            @Override
            public void onDataChange() {
                appAdapter.notifyDataSetChanged();
            }
        });

        mContentModle.setmOnVideoDataChangedListener(new ContentModle.OnVideoDataChangedListener() {
            @Override
            public void onDataChange() {
                videoAdapter.notifyDataSetChanged();
            }
        });

        mContentModle.setmOnFileDataChangedListener(new ContentModle.OnFileDataChangedListener() {
            @Override
            public void onDataChanged(int position) {
                docuAdapter.notifyItemInserted(position);
            }
        });

        mContentModle.setmOnImageDataChangedListener(new ContentModle.OnImageDataChangedListener() {
            @Override
            public void onDataChanged() {

            }
        });

        mContentModle.setmOnMusicDataChangedListener(new ContentModle.OnMusicDataChangedListener() {
            @Override
            public void onDataChanged() {
                musicAdapter.notifyDataSetChanged();
            }
        });
    }


//    class WifiReceiver extends BroadcastReceiver{
//        WifiP2pManager wm;
//        WifiP2pManager.Channel channel;
//        Context context;
//        List<WifiP2pDevice> peer = new ArrayList<>();
//
//        WifiP2pDevice device;
//        WifiP2pConfig config = new WifiP2pConfig();
//
//        final WifiP2pManager.PeerListListener listener = new WifiP2pManager.PeerListListener() {
//            @Override
//            public void onPeersAvailable(WifiP2pDeviceList peers) {
//                peer.clear();
//                peer.addAll(peers.getDeviceList());
//                LogUtil.e("PEER" + peers.getDeviceList().size());
//            }
//        };
//
//        public WifiReceiver(final WifiP2pManager wm, final WifiP2pManager.Channel channel, Context context) {
//            this.wm = wm;
//            this.channel = channel;
//            this.context = context;
//    }
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//
//            if(action.equals(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)){
//                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,-1);
//
//                if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED){
//                    LogUtil.e("ENABLE");
////                    sendBroadcast(new Intent(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION));
//                }else{
//                    LogUtil.e("UNABLE");
//                }
//            }
//
//            if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){
//                LogUtil.e("WIFI_P2P_PEERS_CHANGED_ACTION");
//                // TODO: 2017/7/17  这个分支中调用requestPeers()获取设备列表
//                wm.requestPeers(channel,listener);
//            }
//
//            if(action.equals(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)){
//                //// TODO: 2017/7/17  get self info.
//            }
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private long start = 0;
    @Override
    public void onBackPressed() {

        ToastUtil.showToast("再按一次退出程序");
        if(System.currentTimeMillis()-start > 1500 ){
            start = System.currentTimeMillis();
        }else{
            finish();
        }

    }
}
