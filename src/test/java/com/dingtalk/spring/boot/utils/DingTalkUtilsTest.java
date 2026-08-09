package com.dingtalk.spring.boot.utils;

import com.taobao.api.ApiException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link DingTalkUtils}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DingTalkUtilsTest {

    @Test
    void sign_shouldReturnNonNullSignature() throws ApiException {
        String result = DingTalkUtils.sign("testTicket", "nonce123", 1234567890L, "http://example.com");
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    void sign_shouldReturnConsistentResult() throws ApiException {
        String sig1 = DingTalkUtils.sign("ticket", "nonce", 1000L, "http://url.com");
        String sig2 = DingTalkUtils.sign("ticket", "nonce", 1000L, "http://url.com");
        assertThat(sig1).isEqualTo(sig2);
    }

    @Test
    void sign_shouldReturnDifferentResultsForDifferentInputs() throws ApiException {
        String sig1 = DingTalkUtils.sign("ticket1", "nonce", 1000L, "http://url.com");
        String sig2 = DingTalkUtils.sign("ticket2", "nonce", 1000L, "http://url.com");
        assertThat(sig1).isNotEqualTo(sig2);
    }

    @Test
    void sign_shouldReturnDifferentResultsForDifferentUrls() throws ApiException {
        String sig1 = DingTalkUtils.sign("ticket", "nonce", 1000L, "http://url1.com");
        String sig2 = DingTalkUtils.sign("ticket", "nonce", 1000L, "http://url2.com");
        assertThat(sig1).isNotEqualTo(sig2);
    }

    @Test
    void sign_shouldReturnDifferentResultsForDifferentTimestamps() throws ApiException {
        String sig1 = DingTalkUtils.sign("ticket", "nonce", 1000L, "http://url.com");
        String sig2 = DingTalkUtils.sign("ticket", "nonce", 2000L, "http://url.com");
        assertThat(sig1).isNotEqualTo(sig2);
    }
}
