package com.dddqmmx.surf.socket.connect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectList {
    private static final Map<String,Map<String,Connect>> connectMapList = new HashMap<>();

    public static boolean addHost(String host){
        if (!connectMapList.containsKey(host)){
            Map<String,Connect> connectMap = new HashMap<>();
            connectMapList.put(host,connectMap);
        }
        return true;
    }

    public static boolean setPost(String host,String session,int port){
        Map<String, Connect> stringConnectMap = connectMapList.get(host);
        Connect connect = new Connect();
        connect.setPort(port);
        stringConnectMap.put(session,connect);
        return true;
    }

    public static boolean hasSession(String host,String session){
        Map<String, Connect> stringConnectMap = connectMapList.get(host);
        if (stringConnectMap == null){
            return false;
        }
        return stringConnectMap.containsKey(session);
    }
}
