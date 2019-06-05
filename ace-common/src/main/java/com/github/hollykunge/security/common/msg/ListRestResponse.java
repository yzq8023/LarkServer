package com.github.hollykunge.security.common.msg;

/**
 * 列表返回类型
 *
 * @author 协同设计小组
 * @create 2017-06-09 7:32
 */
public class ListRestResponse<T> extends BaseResponse {
    String msg;
    T data;
    int count;



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ListRestResponse count(int count) {
        this.setCount(count);
        return this;
    }

    public ListRestResponse count(Long count) {
        this.setCount(count.intValue());
        return this;
    }

    public ListRestResponse msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public ListRestResponse result(T data) {
        this.setData(data);
        return this;
    }

    public ListRestResponse(String msg, int count,T data){
        this.count = count;
        this.msg = msg;
        this.data = data;
    }
}
