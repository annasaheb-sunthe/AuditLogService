package com.scb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scb.model.AuditLog;
import com.scb.model.LogData;
import com.scb.model.LogSearchCriteria;

@Service
public interface AuditLogService {

	boolean saveLog(AuditLog auditLog);

	LogData getLogById(long transactionId);

	List<LogData> getLogByType(String transactionType);

	List<LogData> getLogByDate(String timestamp);

	List<LogData> getLogByStatus(String status);

	List<LogData> getLogByLogType(String logType);
	
	List<AuditLog> searchLog(LogSearchCriteria searchCriteria);
}
