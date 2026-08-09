package com.dingtalk.spring.boot.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RandomUtils}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class RandomUtilsTest {

    @Test
    void getRandomStr_shouldReturn16Characters() {
        String result = RandomUtils.getRandomStr();
        assertThat(result).hasSize(16);
    }

    @Test
    void getRandomStr_shouldContainOnlyAlphanumeric() {
        String result = RandomUtils.getRandomStr();
        assertThat(result).matches("[a-zA-Z0-9]{16}");
    }

    @Test
    void getRandomStr_shouldReturnDifferentValues() {
        String result1 = RandomUtils.getRandomStr();
        String result2 = RandomUtils.getRandomStr();
        // Very unlikely to be equal with 62^16 possibilities
        assertThat(result1).isNotEqualTo(result2);
    }
}
