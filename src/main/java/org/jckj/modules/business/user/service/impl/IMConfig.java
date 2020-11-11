package org.jckj.modules.business.user.service.impl;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component("IMConfig")
public class IMConfig {
    @Value("${sdkAppId}")
    public static  long sdkAppId;
    @Value("${secretKey}")
    public static String secretKey;
    public static long expire= 60*60*24*7;
}
