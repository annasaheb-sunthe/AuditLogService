package com.scb.serviceImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scb.model.AuditLog;
import com.scb.model.LogData;
import com.scb.model.LogSearchCriteria;
import com.scb.repository.AuditLogRepository;
import com.scb.service.AuditLogService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AuditLogServiceImpl implements AuditLogService {

	@Autowired
	private AuditLogRepository logRepository;

	@Override
	public List<AuditLog> searchLog(LogSearchCriteria searchCriteria) {

		log.info("Log Search Criteria: " + searchCriteria);
		List<AuditLog> logList = null;

		// if all search criterias are present
		if (searchCriteria.getToDate() != null || !searchCriteria.getToDate().isEmpty()) {
			searchCriteria.setToDate(getFormattedDate());
		}
		
		if (searchCriteria.getFromDate() != null || !searchCriteria.getFromDate().isEmpty()) {
			searchCriteria.setFromDate(getOldDate());
		}
		
		searchCriteria.setInternalFromDate(searchCriteria.getFromDate());
		searchCriteria.setInternalToDate(searchCriteria.getToDate());

		// List<AuditLog> logList = getLog();
		if(searchCriteria.getTransactionType() != null && searchCriteria.getTransactionSubType() != null 
				&& searchCriteria.getMessageType() != null) {
			logList = logRepository.findBySearchCriteria1(searchCriteria.getTransactionType(), searchCriteria.getTransactionSubType(),
					searchCriteria.getMessageType(), searchCriteria.getInternalFromDate(),
					searchCriteria.getInternalToDate());
		} else if (searchCriteria.getTransactionType() != null && searchCriteria.getTransactionSubType() != null 
				&& searchCriteria.getMessageType() == null) {
			logList = logRepository.findBySearchCriteria2(searchCriteria.getTransactionType(), searchCriteria.getTransactionSubType(),
					searchCriteria.getInternalFromDate(), searchCriteria.getInternalToDate());
		} else if (searchCriteria.getTransactionType() != null && searchCriteria.getTransactionSubType() == null 
				&& searchCriteria.getMessageType() != null) {
			logList = logRepository.findBySearchCriteria3(searchCriteria.getTransactionType(), searchCriteria.getMessageType(),
					searchCriteria.getInternalFromDate(), searchCriteria.getInternalToDate());
		} else if (searchCriteria.getTransactionType() == null && searchCriteria.getTransactionSubType() != null 
				&& searchCriteria.getMessageType() != null) {
			logList = logRepository.findBySearchCriteria4(searchCriteria.getTransactionSubType(), searchCriteria.getMessageType(),
					searchCriteria.getInternalFromDate(), searchCriteria.getInternalToDate());
		} else if (searchCriteria.getTransactionType() != null && searchCriteria.getTransactionSubType() == null 
				&& searchCriteria.getMessageType() == null) {
			logList = logRepository.findBySearchCriteria5(searchCriteria.getTransactionType(),
					searchCriteria.getInternalFromDate(), searchCriteria.getInternalToDate());
		} else if (searchCriteria.getTransactionType() == null && searchCriteria.getTransactionSubType() == null 
				&& searchCriteria.getMessageType() != null) {
			logList = logRepository.findBySearchCriteria6(searchCriteria.getMessageType(),
					searchCriteria.getInternalFromDate(), searchCriteria.getInternalToDate());
		} else if (searchCriteria.getTransactionType() == null && searchCriteria.getTransactionSubType() != null 
				&& searchCriteria.getMessageType() == null) {
			logList = logRepository.findBySearchCriteria7(searchCriteria.getTransactionSubType(),
					searchCriteria.getInternalFromDate(), searchCriteria.getInternalToDate());
		} else {
			logList = logRepository.findBySearchCriteria8(searchCriteria.getInternalFromDate(), searchCriteria.getInternalToDate());
		}
		log.info("Log retrived from db : " + logList.size());
		return logList;
	}

	@Override
	public boolean saveLog(AuditLog auditLog) {
		log.info("Received AuditLog data: " + auditLog);
		if (auditLog.getTimestamp() == null) {
			auditLog.setTimestamp(new Date());
		}
		log.info("After setting date AuditLog data: " + auditLog);
		AuditLog savedLog = logRepository.save(auditLog);

		log.info("Saved log data: " + savedLog);
		return true;
	}

	@Override
	public LogData getLogById(long transactionId) {
		LogData obj = logRepository.findByTransactionId(transactionId).get();
		return obj;
	}

	@Override
	public List<LogData> getLogByType(String transactionType) {
		List<LogData> obj = logRepository.findByType(transactionType);
		return obj;
	}

	@Override
	public List<LogData> getLogByStatus(String status) {
		List<LogData> obj = logRepository.findByStatus(status);
		return obj;
	}

	@Override
	public List<LogData> getLogByDate(String timeStamp) {
		List<LogData> obj = logRepository.findByDate(timeStamp);
		return obj;
	}

	@Override
	public List<LogData> getLogByLogType(String logType) {
		List<LogData> obj = logRepository.findByLogType(logType);
		return obj;
	}

	public LocalDateTime getCurrentDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime;
	}

	public String getFormattedDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy"); 
		return format.format(new Date()); 
	}
	
	public String getOldDate() {
		return "01-01-1900";
//		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
//		try {
//			return dateFormat.parse("01-01-1900").toString();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private List<AuditLog> getLog() {
		List<AuditLog> logList = new ArrayList<AuditLog>();
		AuditLog log = null;
		String status[] = { "INITIATED", "PROCESSING", "STORING", "CONNECTED", "STORED", "POST PROCESSING", "LOGING",
				"COMPLETED", "FAILED", "SUCCESS", "FINISHED" };

		for (int i = 0; i < 10; i++) {
			log = new AuditLog();
			log.setAuditLogId(getTransmitterId());
			log.setTransactionId(getTransmitterId() + i);
			log.setTransactionType("OutwardCreditTransfer");
			log.setTransactionSubType("IN");
			log.setMessageType("XML");
			log.setServiceName("PersistenceService");
			log.setStatus(status[i]);
			log.setTimestamp(new Date());
			log.setMessage("Messages processed successfully");

			logList.add(log);
		}

		return logList;
	}

	public long getTransmitterId() {
		Random random = new Random(System.nanoTime() % 100000);
		long uniqueMetadataId = random.nextInt(1000000000);
		return uniqueMetadataId;
	}
}
