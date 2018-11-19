package com.xdbin.comment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Data
@Component
@ConfigurationProperties(prefix = "author.sign")
public class AuthorSign {

    private String email;

    private String username;

    private String website;

    public boolean verity(String email, String username, String website) {
        if (this.username.equals(username)) {
            return !StringUtils.isEmpty(this.email)
                    && !StringUtils.isEmpty(this.website)
                    && this.email.equals(email)
                    && this.website.equals(website);
        }
        return true;
    }

}
