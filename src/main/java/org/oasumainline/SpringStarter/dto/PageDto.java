package org.oasumainline.SpringStarter.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageDto<T>{
    private List<T> data;
    private PaginationDto pagination;


    public PageDto(Page<T> page) {
        this.data = page.getContent();
        this.pagination = new PaginationDto(page.getNumber(), page.getTotalPages());
    }
}
