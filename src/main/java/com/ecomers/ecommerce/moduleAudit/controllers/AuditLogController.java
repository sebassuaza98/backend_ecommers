package com.ecomers.ecommerce.moduleAudit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecomers.ecommerce.moduleAudit.models.AuditLog;
import com.ecomers.ecommerce.moduleAudit.services.AuditLogService;

import java.util.List;

@RestController
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping("/audit-logs")
    public List<AuditLog> getAuditLogs() {
        return auditLogService.getAuditLogs();
    }
}
