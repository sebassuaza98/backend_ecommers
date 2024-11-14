package com.ecomers.ecommerce.moduleAudit.events;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecomers.ecommerce.Util.Constants;
import com.ecomers.ecommerce.login.services.UserContext;
import com.ecomers.ecommerce.moduleAudit.models.AuditLog;
import com.ecomers.ecommerce.moduleAudit.repositories.AuditLogRepository;
import com.ecomers.ecommerce.moduleProducts.models.Product;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditLogRepository auditLogRepository;
    @Pointcut("execution(* com.ecomers.ecommerce.moduleProducts.services.ProductService.createProduct(..))")
    public void createProductMethod() {}

    @AfterReturning(value = "createProductMethod()", returning = "result")
    public void logAuditAfterProductCreation(Object result) {
        if (result instanceof Product) {
            Product product = (Product) result;
            AuditLog auditLog = new AuditLog();
            auditLog.setAction(Constants.AUDIT_CREATE);
            auditLog.setEntityName("Product");
            Long userId = UserContext.getUserId();
            auditLog.setUser(userId != null ? String.valueOf(userId) : "system");
            auditLog.setTimestamp(LocalDateTime.now());
            auditLogRepository.save(auditLog);
        }
    }

    @Pointcut("execution(* com.ecomers.ecommerce.moduleProducts.services.ProductService.updateProduct(..))")
    public void updateProductMethod() {}

    @AfterReturning(value = "updateProductMethod()", returning = "result")
    public void logAuditAfterProductUpdate(Object result) {
        if (result instanceof Product) {
            Product product = (Product) result;
            AuditLog auditLog = new AuditLog();
            auditLog.setAction(Constants.AUDIT_UPDATE);
            auditLog.setEntityName("Product");
            Long userId = UserContext.getUserId();
            auditLog.setUser(userId != null ? String.valueOf(userId) : "system");
            auditLog.setTimestamp(LocalDateTime.now());
            auditLogRepository.save(auditLog);
        }
    }
}
