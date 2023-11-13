package com.moduinterview.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AwsS3Response {

    private String url;

    public AwsS3Response(String url) {
        this.url = url;
    }
}
