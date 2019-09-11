package com.smrahmadi.cityguid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class NetworkUtils {

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);

        assert connMgr != null;
        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        boolean connectToNetwork = activeNetworkInfo != null && (activeNetworkInfo.getType() == ConnectivityManager
                .TYPE_WIFI ||
                activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE);

        if (!connectToNetwork)
            return false;
        return isInternetActive();
    }

    private static boolean isInternetActive() {
        try {
            int timeoutMs = 500;
            Socket sock = new Socket();
            SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);
            sock.connect(socketAddress, timeoutMs);
            sock.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
