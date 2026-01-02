package com.varol.WellPass_Mananagement_System.dtos.response.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> content;

    private Integer currentPage;

    private Integer pageSize;

    private Long totalElements;

    private Integer totalPages;

    private Boolean isFirst;

    private Boolean isLast;

    private Boolean hasNext;

    private Boolean hasPrevious;
}
