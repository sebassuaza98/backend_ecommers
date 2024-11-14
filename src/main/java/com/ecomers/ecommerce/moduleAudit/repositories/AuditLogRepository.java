package com.ecomers.ecommerce.moduleAudit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecomers.ecommerce.moduleAudit.models.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}