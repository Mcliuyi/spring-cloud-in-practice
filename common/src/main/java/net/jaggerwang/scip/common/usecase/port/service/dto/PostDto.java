package net.jaggerwang.scip.common.usecase.port.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    private Long userId;

    private String type;

    private String text;

    @Builder.Default
    private List<Long> imageIds = List.of();

    private Long videoId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}