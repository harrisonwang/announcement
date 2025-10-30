package com.talkweb.announcement.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CustomAuditorAware implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        // TODO: 获取当前用户
        return Optional.of("admin");
    }
}
