package com.jonservices.bookservice.proxy;

import com.jonservices.bookservice.response.Conversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "conversion-service"/*, url = "${feign.url}"*/)
public interface ConversionProxy {
    @GetMapping("/conversion/{value}/{from}/{to}")
    Conversion convert(@PathVariable BigDecimal value,
                       @PathVariable String from,
                       @PathVariable String to);
}
