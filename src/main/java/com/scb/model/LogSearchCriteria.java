package com.scb.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@XmlRootElement
public class LogSearchCriteria {
	private long transactionId;
	private String transactionType;
	private String transactionSubType;
	private String messageType;
	private String fromDate;
	private String toDate;
	private Date internalFromDate;
	private Date internalToDate;

	public void setInternalFromDate(String date) {
		try {
			this.internalFromDate = (Date) new SimpleDateFormat("dd-MM-yyyy").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setInternalToDate(String date) {
		try {
			this.internalToDate = (Date) new SimpleDateFormat("dd-MM-yyyy").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
