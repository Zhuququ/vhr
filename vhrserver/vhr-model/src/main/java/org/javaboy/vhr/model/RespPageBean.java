package org.javaboy.vhr.model;

import java.util.List;

public class RespPageBean {

    private List<?> data;

    private Long total;

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
