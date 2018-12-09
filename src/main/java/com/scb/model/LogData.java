package com.scb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.scb.model.LogData.LogDataBuilder;
import com.scb.model.compositekey.LogDataCompositeKey;
import com.scb.model.compositekey.LogDataCompositeKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

@Getter @Setter @Builder @Entity @Table(name="logdata") @NoArgsConstructor @AllArgsConstructor @ToString @XmlRootElement
public class LogData implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private  LogDataCompositeKey dataCompositeKey;
	
	private String transactionType;
	
	private String status;
	
	
	private String msComponent;

	private String logLevel;

	private String logMessage;
	
	private String payload;
	
	private String timeStamp;
	
	private String errorMessage;
	
	private String errorCode;
	
	private String logType;
	
	private String stackTrace;

	public LogDataCompositeKey getLogDataCompositeKey() {
		// TODO Auto-generated method stub
		return null;
	}

	
	}

	
