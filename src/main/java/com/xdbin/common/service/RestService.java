package com.xdbin.common.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface RestService {

    ResponseEntity fetch(String url, HttpMethod method, MultiValueMap<String, String> params);

}
