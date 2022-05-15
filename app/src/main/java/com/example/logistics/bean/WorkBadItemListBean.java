package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :10.2.22
 */
public class WorkBadItemListBean implements Serializable {
    private List<RecordsBadItemListBean> records;
    private Integer total;
    private Integer size;
    private Integer current;
    private List<?> orders;
    private Boolean optimizeCountSql;
    private Boolean hitCount;
    private Object countId;
    private Object maxLimit;
    private Boolean searchCount;
    private Integer pages;

    public List<RecordsBadItemListBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBadItemListBean> records) {
        this.records = records;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public List<?> getOrders() {
        return orders;
    }

    public void setOrders(List<?> orders) {
        this.orders = orders;
    }

    public Boolean getOptimizeCountSql() {
        return optimizeCountSql;
    }

    public void setOptimizeCountSql(Boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
    }

    public Boolean getHitCount() {
        return hitCount;
    }

    public void setHitCount(Boolean hitCount) {
        this.hitCount = hitCount;
    }

    public Object getCountId() {
        return countId;
    }

    public void setCountId(Object countId) {
        this.countId = countId;
    }

    public Object getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(Object maxLimit) {
        this.maxLimit = maxLimit;
    }

    public Boolean getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Boolean searchCount) {
        this.searchCount = searchCount;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
