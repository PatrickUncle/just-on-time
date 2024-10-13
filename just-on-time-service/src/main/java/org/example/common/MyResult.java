package org.example.common;

import java.util.HashMap;

public class MyResult extends HashMap<String, Object> {

    public static MyResult ok() {
        MyResult myResult = new MyResult();
        myResult.put("code", 200);
        myResult.put("message", "ok");
        return myResult;
    }

    public static MyResult error(int code, String message) {
        MyResult myResult = new MyResult();
        myResult.put("code", code);
        myResult.put("message", message);
        return myResult;
    }


    public static MyResult error(String message) {
        MyResult myResult = new MyResult();
        myResult.put("code", 500);
        myResult.put("message", message);
        return myResult;
    }


    public MyResult add(String key, Object o) {
        this.put(key, o);
        return this;
    }

    public MyResult data(Object data) {
        this.put("data", data);
        return this;
    }
}
