package ru.practicum.myblog.dto.page;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Page<T> {

    private List<T> content;

    private long totalCount;

    private int pageSize;

    private int pageNumber;

    @SuppressWarnings("unused")
    public Page(List<T> content, Long totalCount, Integer pageSize, Integer pageNumber) {
        this.content = content == null ? new ArrayList<>() : content;
        this.totalCount = totalCount == null ? 0 : totalCount;
        this.pageSize = pageSize == null ? 0 : pageSize;
        this.pageNumber = pageNumber == null ? 0 : pageNumber;
    }

    @SuppressWarnings("unused")
    public Page() {
        this.pageSize = 5;
        this.pageNumber = 1;
    }

    @SuppressWarnings("unused")
    public boolean hasNext() {
        return pageNumber * pageSize < totalCount;
    }

    @SuppressWarnings("unused")
    public boolean hasPrevious() {
        return pageNumber > 1;
    }

    public int getOffset() {
        return pageSize * (pageNumber - 1);
    }

}
