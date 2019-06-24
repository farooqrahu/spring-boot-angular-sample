package me.vitblokhin.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PageDto<D extends AbstractDto> {

    @JsonProperty("items")
    private List<D> dtoList;

    @JsonProperty("pageNumber")
    private int pageNumber;

    @JsonProperty("totalPages")
    private int totalPages;

    public PageDto() {
    }

    public PageDto(Page<D> page) {
        this.dtoList = page.get().collect(Collectors.toList());

        this.pageNumber = page.getNumber();
        this.totalPages = page.getTotalPages();
    }
} // class PageDto
