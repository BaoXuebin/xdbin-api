package com.xdbin.common.service.impl;

import com.xdbin.common.service.RestService;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RestServiceImpl implements RestService {

    @Override
    public ResponseEntity fetch(String url, HttpMethod method, MultiValueMap<String, String> params) {
        RestTemplate template = new RestTemplate();
        return template.getForEntity(url, String.class);
    }
}
