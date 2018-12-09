package com.scb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.scb.auditlog.util.ReceiverConstants;
import com.scb.model.AuditLog;
import com.scb.model.LogData;
import com.scb.model.LogSearchCriteria;
import com.scb.service.AuditLogService;

import lombok.extern.log4j.Log4j2;

@Component
@RestController
@Log4j2
@RequestMapping(ReceiverConstants.AUDITLOG_URL)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuditLogServiceController {
	@Autowired
	private AuditLogService auditLogService;

	@PostMapping("/addLog")
	public ResponseEntity<Void> saveLog(@RequestBody AuditLog auditLog,
			UriComponentsBuilder builder) {
		log.info("Log data received: " + auditLog);
		boolean flag = auditLogService.saveLog(auditLog);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(builder.path("/ProcessFlowSequence/{id}").buildAndExpand(auditLog).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	//@RequestMapping("/searchLog")
	@RequestMapping(value = ReceiverConstants.SEARCH_LOG_URL, method = RequestMethod.POST, produces = { "application/json", "application/xml" })
	public ResponseEntity<List<AuditLog>> searchLog(@RequestBody LogSearchCriteria searchCriteria) {
		log.info("Log search criteria received: " + searchCriteria);
		List<AuditLog> logList = auditLogService.searchLog(searchCriteria);
		log.info("Log retrived from service : " + logList.size());
		return new ResponseEntity<List<AuditLog>>(logList, HttpStatus.OK);
	}
	
	@GetMapping("/getLogById/{transactionId}")
	public ResponseEntity<LogData> getLogById(@PathVariable("transactionId") long transactionId) {
		log.info(" Get Log By ID received: " + transactionId);
		LogData transactionById = auditLogService.getLogById(transactionId);
		log.info("Log Recieved With Id" + transactionId + " received: " + transactionById);
		return new ResponseEntity<LogData>(transactionById,HttpStatus.OK);
	}

	@GetMapping("/getLogByType/{transactionType}")
	public ResponseEntity<List<LogData>> getLogById(
			@PathVariable("transactionType") String transactionType) {
		log.info(" Get Log By ID received: " + transactionType);
		List<LogData> transactionByType = auditLogService.getLogByType(transactionType);
		log.info("Log Recieved With Type" + transactionType + " received: " + transactionByType);
		return new ResponseEntity<List<LogData>>(transactionByType, HttpStatus.OK);
	}
	
	@GetMapping("/getLogByStatus/{status}")
	public ResponseEntity<List<LogData>> getLogByStatus(
			@PathVariable("status") String status) {
		log.info(" Get Log By status received: " + status);
		List<LogData> transactionByStatus = auditLogService.getLogByStatus(status);
		log.info("Log Recieved With Status" + status + " received: " + transactionByStatus);
		return new ResponseEntity<List<LogData>>(transactionByStatus, HttpStatus.OK);
	}
	
	@GetMapping("/getLogByDate/{timeStamp}")
	public ResponseEntity<List<LogData>> getLogByDate(
			@PathVariable("timeStamp") String timeStamp) {
		log.info(" Get Log By date received: " + timeStamp);
		List<LogData> transactionByDate = auditLogService.getLogByDate(timeStamp);
		log.info("Log Recieved With date" + timeStamp + " received: " + transactionByDate);
		return new ResponseEntity<List<LogData>>(transactionByDate, HttpStatus.OK);
	}

	@GetMapping("/getLogByLogtype/{logType}")
	public ResponseEntity<List<LogData>> getLogByLogType(
			@PathVariable("logType") String logType) {
		log.info(" Get Log By logType received: " + logType);
		List<LogData> transactionByLogType = auditLogService.getLogByLogType(logType);
		log.info("Log Recieved With logType" + logType + " received: " + transactionByLogType);
		return new ResponseEntity<List<LogData>>(transactionByLogType, HttpStatus.OK);
	}
}
