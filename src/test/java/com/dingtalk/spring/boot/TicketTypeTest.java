package com.dingtalk.spring.boot;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TicketType}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class TicketTypeTest {

    @Test
    void jsapi_shouldHaveCorrectCode() {
        assertThat(TicketType.JSAPI.getCode()).isEqualTo("jsapi");
    }

    @Test
    void values_shouldContainOnlyJsapi() {
        assertThat(TicketType.values()).hasSize(1);
        assertThat(TicketType.values()).containsExactly(TicketType.JSAPI);
    }

    @Test
    void valueOf_shouldReturnJsapi() {
        assertThat(TicketType.valueOf("JSAPI")).isEqualTo(TicketType.JSAPI);
    }
}
