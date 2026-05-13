package com.githubx.githubpullrequestms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.main.lazy-initialization=true",
        "app.security.oauth2.enabled=false"
})
class GithubPullRequestMsApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the Spring application context loads successfully.
        // It passes if no exception is thrown during context initialization.
    }
}
