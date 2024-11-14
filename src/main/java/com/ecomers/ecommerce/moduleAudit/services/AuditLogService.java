package com.ecomers.ecommerce.moduleAudit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomers.ecommerce.moduleAudit.models.AuditLog;
import com.ecomers.ecommerce.moduleAudit.repositories.AuditLogRepository;

import java.util.List;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public List<AuditLog> getAuditLogs() {
        return auditLogRepository.findAll();
    }
}
