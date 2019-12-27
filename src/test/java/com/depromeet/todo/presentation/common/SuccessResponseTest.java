package com.depromeet.todo.presentation.common;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.SliceImpl;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class SuccessResponseTest {
    @Test
    void 한_개의_객체로_성공_응답하는_경우() {
        ApiResponse<String> apiResponse = ApiResponse.successFrom("This is a response object");

        assertThat(apiResponse).isInstanceOf(SuccessSimpleResponse.class);
        SuccessSimpleResponse<String> successSimpleResponse = (SuccessSimpleResponse<String>) apiResponse;
        assertThat(successSimpleResponse.getData()).isEqualTo("This is a response object");
    }

    @Test
    void 리스트_타입으로_성공_응답하는_경우() {
        ApiResponse<String> apiResponse = ApiResponse.successFrom(Arrays.asList("1", "2", "3"));

        assertThat(apiResponse).isInstanceOf(SuccessListResponse.class);
        SuccessListResponse<String> successListResponse = (SuccessListResponse<String>) apiResponse;
        assertThat(successListResponse.getData()).hasSize(3);
    }

    @Test
    void 슬라이스_타입으로_성공_응답하는_경우() {
        ApiResponse<String> apiResponse = ApiResponse.successFrom(new SliceImpl<>(Arrays.asList("1", "2", "3")));

        assertThat(apiResponse).isInstanceOf(SuccessSliceResponse.class);
        SuccessSliceResponse<String> successSliceResponse = (SuccessSliceResponse<String>) apiResponse;
        assertThat(successSliceResponse.getData()).hasSize(3);
        assertThat(successSliceResponse.getHasNext()).isFalse();
    }

    @Test
    void 페이지_타입으로_성공_응답하는_경우() {
        ApiResponse<String> apiResponse = ApiResponse.successFrom(new PageImpl<>(Arrays.asList("1", "2", "3")));

        assertThat(apiResponse).isInstanceOf(SuccessPageResponse.class);
        SuccessPageResponse<String> successPageResponse = (SuccessPageResponse<String>) apiResponse;
        assertThat(successPageResponse.getData()).hasSize(3);
        assertThat(successPageResponse.getTotalCount()).isEqualTo(3L);
    }
}