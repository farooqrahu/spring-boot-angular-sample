package me.vitblokhin.backend.dto.filter;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class AbstractFilter {
    @Min(0)
    private int page = 0;
    @Min(1)
    private int size = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
} // class ObjectFilter



