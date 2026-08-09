package com.dingtalk.spring.boot.bean;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JsapiTicketSignature}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class JsapiTicketSignatureTest {

    @Test
    void builder_shouldBuildCorrectly() {
        JsapiTicketSignature sig = JsapiTicketSignature.builder()
                .agentId("agent1")
                .url("http://example.com")
                .nonceStr("nonce")
                .timestamp(1234567890L)
                .corpId("corp1")
                .signature("sig1")
                .build();
        assertThat(sig.getAgentId()).isEqualTo("agent1");
        assertThat(sig.getUrl()).isEqualTo("http://example.com");
        assertThat(sig.getNonceStr()).isEqualTo("nonce");
        assertThat(sig.getTimestamp()).isEqualTo(1234567890L);
        assertThat(sig.getCorpId()).isEqualTo("corp1");
        assertThat(sig.getSignature()).isEqualTo("sig1");
    }

    @Test
    void noArgsConstructor_shouldCreateEmptyObject() {
        JsapiTicketSignature sig = new JsapiTicketSignature();
        assertThat(sig.getAgentId()).isNull();
        assertThat(sig.getUrl()).isNull();
        assertThat(sig.getNonceStr()).isNull();
        assertThat(sig.getTimestamp()).isEqualTo(0L);
        assertThat(sig.getCorpId()).isNull();
        assertThat(sig.getSignature()).isNull();
    }

    @Test
    void allArgsConstructor_shouldSetAllFields() {
        JsapiTicketSignature sig = new JsapiTicketSignature("a1", "http://u.com", "n1", 100L, "c1", "s1");
        assertThat(sig.getAgentId()).isEqualTo("a1");
        assertThat(sig.getUrl()).isEqualTo("http://u.com");
        assertThat(sig.getNonceStr()).isEqualTo("n1");
        assertThat(sig.getTimestamp()).isEqualTo(100L);
        assertThat(sig.getCorpId()).isEqualTo("c1");
        assertThat(sig.getSignature()).isEqualTo("s1");
    }

    @Test
    void dataAnnotation_shouldGenerateEqualsAndHashCode() {
        JsapiTicketSignature sig1 = new JsapiTicketSignature("a1", "u1", "n1", 100L, "c1", "s1");
        JsapiTicketSignature sig2 = new JsapiTicketSignature("a1", "u1", "n1", 100L, "c1", "s1");
        assertThat(sig1).isEqualTo(sig2);
        assertThat(sig1.hashCode()).isEqualTo(sig2.hashCode());
    }

    @Test
    void dataAnnotation_shouldGenerateToString() {
        JsapiTicketSignature sig = new JsapiTicketSignature("a1", "u1", "n1", 100L, "c1", "s1");
        String str = sig.toString();
        assertThat(str).contains("agentId=a1");
        assertThat(str).contains("signature=s1");
    }
}
