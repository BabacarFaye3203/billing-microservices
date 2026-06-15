package com.babacar.app.dto;

import java.util.List;

public record ListResponse<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean hasNext
) {

}
