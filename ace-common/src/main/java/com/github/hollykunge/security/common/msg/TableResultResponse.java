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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "TableResultResponse{" +
                "data=" + data +
                ", pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", totalPage=" + totalPage +
                ", totalCount=" + totalCount +
                '}';
    }
}
