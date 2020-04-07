package com.brick.buster.main.response;

import com.brick.buster.main.response.interfaces.Response;

public class ObjectResponse extends Response {
    private Object item = null;

    public ObjectResponse(Object item, String message) {
        super(message);
        this.item = item;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
}
