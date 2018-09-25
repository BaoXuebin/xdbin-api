package com.xdbin.web;

import com.xdbin.config.sso.SSOClientResources;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("sso/")
 @RestController
public class SSOController {

    @Resource
    OAuth2ClientContext oAuth2ClientContext;

    @Bean
    @ConfigurationProperties("github")
    public SSOClientResources github() {
        return new SSOClientResources();
    }

    @RequestMapping("github")
    public ResponseEntity ssoGithub() {
        SSOClientResources clientResources = github();

        OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationProcessingFilter = new OAuth2ClientAuthenticationProcessingFilter("/sso/github");
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(clientResources.getClient(), oAuth2ClientContext);
        oAuth2ClientAuthenticationProcessingFilter.setRestTemplate(oAuth2RestTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(clientResources.getResource().getUserInfoUri(), clientResources.getClient().getClientId());
        tokenServices.setRestTemplate(oAuth2RestTemplate);
        oAuth2ClientAuthenticationProcessingFilter.setTokenServices(tokenServices);


        return ResponseEntity.ok("Hello");
    }

}
