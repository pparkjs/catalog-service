package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *  모의 웹 환경에서 스프링 애플리케이션 콘텍스트를 로드하고 스프링 MVC 인프라를 설정하며
 * @RestControoler 및 @RestControllerAdvice와 같은 MVC 계층에서 사용되는 빈만 포함한다.
 * 또한 테스트 중인 특정 컨트롤러가 사용하는 빈만 포함하도록 콘텍스트를 제한하는 것이 좋다.
 */
@WebMvcTest(BookController.class)
class BookControllerMvcTests {

    @Autowired
    private MockMvc mockMvc; // 모의 환경에서 웹 계층을 테스트하기 위한 유틸리티 클래스

    @MockBean // 스프링 애플리케이션 콘텍스트에 BookService의 모의 객체를 추가한다.
    private BookService bookService;

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "73737313940";
        given(bookService.viewBookDetails(isbn))
            .willThrow(BookNotFoundException.class);  // 모의 빈이 어떻게 작동할 것인지 규정한다.
        mockMvc
            .perform(get("/books/" + isbn)) // MockMVC는 HTTP GET 요청을 수행하고 결과를 확인하기 위해 사용한다.
            .andExpect(status().isNotFound()); // 응답이 '404 발견되지 않음' 상태를 가질 것으로 예상한다.
    }


}