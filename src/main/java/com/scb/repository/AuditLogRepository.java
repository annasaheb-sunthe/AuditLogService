package com.scb.repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scb.model.AuditLog;
import com.scb.model.LogData;

public interface AuditLogRepository extends JpaRepository<AuditLog, AuditLog> {
	@Query(value="SELECT * FROM AuditLog sd WHERE sd.transactionType = ?1",nativeQuery=true)
	List<LogData> findByType(String transactionType);
	
	@Query(value="SELECT * FROM AuditLog sd WHERE sd.transactionId = ?1",nativeQuery=true)
	Optional<LogData> findByTransactionId(long transactionId);
	
	@Query(value="SELECT * FROM AuditLog sd WHERE sd.status = ?1",nativeQuery=true)
	List<LogData> findByStatus(String status);
	
	@Query(value="SELECT * FROM AuditLog sd WHERE sd.timeStamp = ?1",nativeQuery=true)
	List<LogData> findByDate(String timeStamp);
	
	@Query(value="SELECT * FROM AuditLog sd WHERE sd.logType = ?1",nativeQuery=true)
	List<LogData> findByLogType(String logType);

	@Query(value="SELECT * FROM AuditLog sd WHERE sd.transactionType = ?1 AND sd.transactionSubType = ?2 "
			+ "AND sd.messageType = ?3 AND sd.timestamp >= ?4 AND sd.timestamp <= ?5",nativeQuery=true)
	List<AuditLog> findBySearchCriteria1(String transactionType, String transactionSubType, String messageType, 
			Date fromDate, Date toDate);
	
	@Query(value="SELECT * FROM AuditLog sd WHERE sd.transactionType = ?1 AND sd.transactionSubType = ?2 "
			+ "AND sd.timestamp >= ?3 AND sd.timestamp <= ?4",nativeQuery=true)
	List<AuditLog> findBySearchCriteria2(String transactionType, String transactionSubType, Date fromDate, Date toDate);
	
	@Query(value="SELECT * FROM AuditLog sd WHERE sd.transactionType = ?1 AND sd.messageType = ?2 "
			+ "AND sd.timestamp >= ?3 AND sd.timestamp <= ?4",nativeQuery=true)
	List<AuditLog> findBySearchCriteria3(String transactionType, String messageType, Date fromDate, Date toDate);

	@Query(value="SELECT * FROM AuditLog sd WHERE sd.transactionSubType = ?1 AND sd.messageType = ?2 "
			+ "AND sd.timestamp >= ?3 AND sd.timestamp <= ?4",nativeQuery=true)
	List<AuditLog> findBySearchCriteria4(String transactionSubType, String messageType, Date fromDate, Date toDate);

	@Query(value="SELECT * FROM AuditLog sd WHERE sd.transactionType = ?1 AND sd.timestamp >= ?2 AND sd.timestamp <= ?3",nativeQuery=true)
	List<AuditLog> findBySearchCriteria5(String transactionType, Date fromDate, Date toDate);
	
	@Query(value="SELECT * FROM AuditLog sd WHERE sd.messageType = ?1 AND sd.timestamp >= ?2 AND sd.timestamp <= ?3",nativeQuery=true)
	List<AuditLog> findBySearchCriteria6(String messageType, Date fromDate, Date toDate);

	
	@Query(value="SELECT * FROM AuditLog sd WHERE sd.transactionSubType = ?1 AND sd.timestamp >= ?2 AND sd.timestamp <= ?3",nativeQuery=true)
	List<AuditLog> findBySearchCriteria7(String transactionSubType, Date fromDate, Date toDate);

	@Query(value="SELECT * FROM AuditLog sd WHERE sd.timestamp >= ?1 AND sd.timestamp <= ?2",nativeQuery=true)
	List<AuditLog> findBySearchCriteria8( Date fromDate, Date toDate);
}
