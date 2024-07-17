package com.polarbookshop.catalogservice.domain;

public record Book(
        String isbn,  // 책의 고유 식별자
        String title,
        String author,
        Double price
) {}
