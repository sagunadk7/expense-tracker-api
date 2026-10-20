package com.sagun.expense_tracker_api.config;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

@Component
@EnableJpaAuditing
public class JpaAuditingConfig {
}
