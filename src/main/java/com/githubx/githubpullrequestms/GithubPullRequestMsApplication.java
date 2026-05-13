package com.githubx.githubpullrequestms;

import org.openapitools.configuration.SpringDocConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
    basePackages = {
        "com.githubx.githubpullrequestms",
        "com.smithy.g.pullrequest.server.pullrequest.api",
        "org.openapitools.configuration"
    },
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = SpringDocConfiguration.class
    )
)
public class GithubPullRequestMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubPullRequestMsApplication.class, args);
    }
}
