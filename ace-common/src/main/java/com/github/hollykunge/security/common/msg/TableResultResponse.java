package com.github.hollykunge.security.common.msg;

import java.util.List;

/**
 * 分页结果返回
 *
 * @author 协同设计小组
 * @create 2017-06-14 22:40
 */

public class TableResultResponse<T> extends BaseResponse {

    private int pageSize;
    private int pageNo;
    private int totalPage;
    private long totalCount;
    TableData<T> data;

    public TableResultResponse(int pageSize, int pageNo, int totalPage, long totalCount, List<T> rows) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.data = new TableData<T>(rows);
    }

    public TableResultResponse() {
        this.data = new TableData<T>();
    }

    TableResultResponse<T> total(long total) {
        this.setTotalCount(total);
        return this;
    }

    TableResultResponse<T> rows(List<T> rows) {
        this.data.setRows(rows);
        return this;
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

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public TableData<T> getData() {
        return data;
    }

    public void setData(TableData<T> data) {
        this.data = data;
    }

    class TableData<T> {

        private List<T> rows;

        public TableData(List<T> rows) {
            this.rows = rows;
        }

        public TableData() {
        }

        public List<T> getRows() {
            return rows;
        }

        public void setRows(List<T> rows) {
            this.rows = rows;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "data=" + data +
                "}";
    }
}
