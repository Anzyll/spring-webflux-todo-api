package com.webflux.reactivetodoapi;
public record ResponseDto(
        Long id,
        String title,
        Boolean completed
) {}