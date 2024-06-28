package xyz.felh.baidu.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHeaders implements Serializable {

    private String appId;

    /**
     * The maximum number of requests that are permitted before exhausting the rate limit.
     * UNIT: RPM
     */
    private Integer limitRequests;

    /**
     * The maximum number of tokens that are permitted before exhausting the rate limit.
     * UNIT: TPM
     */
    private Integer limitTokens;

    /**
     * The remaining number of requests that are permitted before exhausting the rate limit.
     */
    private Integer remainingRequests;

    /**
     * The remaining number of tokens that are permitted before exhausting the rate limit.
     */
    private Integer remainingTokens;

}
