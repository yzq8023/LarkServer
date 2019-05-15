package com.github.hollykunge.security.common.msg;

import java.util.List;

/**
 * 分页结果返回
 *
 * @author 协同设计小组
 * @create 2017-06-14 22:40
 */

public class TableResultResponse<T> extends BaseResponse {


     private T data;
     private int pageSize;
     private int pageNo;
     private int totalPage;
     private int totalCount;

    public TableResultResponse(int status, String message, T data, int pageSize, int pageNo, int totalPage, int totalCount) {
        super(status, message);
        this.data = data;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
    }

    public TableResultResponse(T data, int pageSize, int pageNo, int totalPage, int totalCount) {
        this.data = data;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
