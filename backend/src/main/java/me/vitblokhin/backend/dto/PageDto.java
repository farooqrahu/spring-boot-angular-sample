package me.vitblokhin.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import me.vitblokhin.backend.model.AbstractEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageDto<D extends AbstractDto, E extends AbstractEntity> {

    @JsonProperty("items")
    private List<D> dtoList;

    @JsonProperty("pageNumber")
    private int pageNumber;

    @JsonProperty("totalPages")
    private int totalPages;

    public PageDto() {
    }

    public PageDto(Page<E> page, Function<E, D> constructor) {
        this.dtoList = page.get().map(constructor).collect(Collectors.toList());

        this.pageNumber = page.getNumber();
        this.totalPages = page.getTotalPages();
    }
} // class PageDto
