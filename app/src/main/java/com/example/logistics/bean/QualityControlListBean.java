package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :21.1.22
 */
public class QualityControlListBean implements Serializable {
    private List<ControlListBean> records;
    private String total;
    private String size;
    private String current;
    private List<?> orders;
    private Boolean optimizeCountSql;
    private Boolean hitCount;
    private String countId;
    private String maxLimit;
    private Boolean searchCount;
    private String pages;

    public List<ControlListBean> getRecords() {
        return records;
    }

    public void setRecords(List<ControlListBean> records) {
        this.records = records;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
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

    public String getCountId() {
        return countId;
    }

    public void setCountId(String countId) {
        this.countId = countId;
    }

    public String getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(String maxLimit) {
        this.maxLimit = maxLimit;
    }

    public Boolean getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Boolean searchCount) {
        this.searchCount = searchCount;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }
}
