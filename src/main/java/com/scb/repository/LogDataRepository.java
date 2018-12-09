package com.scb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scb.model.LogData;
import com.scb.model.compositekey.LogDataCompositeKey;


public interface LogDataRepository extends JpaRepository<LogData, LogDataCompositeKey> {
	@Query(value="SELECT * FROM logdata sd WHERE sd.transactionType = ?1",nativeQuery=true)
	List<LogData> findByType(String transactionType);
	
	@Query(value="SELECT * FROM logdata sd WHERE sd.transactionId = ?1",nativeQuery=true)
	Optional<LogData> findByTransactionId(long transactionId);
	
	@Query(value="SELECT * FROM logdata sd WHERE sd.status = ?1",nativeQuery=true)
	List<LogData> findByStatus(String status);
	
	@Query(value="SELECT * FROM logdata sd WHERE sd.timeStamp = ?1",nativeQuery=true)
	List<LogData> findByDate(String timeStamp);
	
	@Query(value="SELECT * FROM logdata sd WHERE sd.logType = ?1",nativeQuery=true)
	List<LogData> findByLogType(String logType);


}
