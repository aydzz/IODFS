package com.pup.cea.iodfs.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.cea.iodfs.ajax.response.DocumentActivityResponseBody;
import com.pup.cea.iodfs.dao.UserInfoRepository;
import com.pup.cea.iodfs.dao.UserLogsRepository;
import com.pup.cea.iodfs.dao.security.UserLoginRepository;
import com.pup.cea.iodfs.model.Document;
import com.pup.cea.iodfs.model.DocumentActivity;
import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.model.UserLogs;
import com.pup.cea.iodfs.model.security.UserLogin;


@Service
public class UserLogsService {
	
	@Autowired 
	private UserLogsRepository userLogsRepos;

	
	public List<UserLogs> findAll(){
		return userLogsRepos.findAll();
	}
	
	
	/*
	 * added for ops that is not defined in the SQL Script dito muna hanggat di pa
	 * nalalagayan sa SQL
	 */
	public UserLogs findByCts(String cts) {
		return userLogsRepos.findByCts(cts);
	}
	
	public UserLogs findById(long id) {
		return userLogsRepos.findById(id).get();
	}
	
	public void saveUserLog(UserLogs userLog) {
		userLogsRepos.save(userLog);
	}
	
	//for fetching chart data
	public List<UserLogs> findByActionWildCard(String action, String date){
		return userLogsRepos.findByActionWildCard(action, date);
	}
	public List<UserLogs> findByDateWildCard(String date){
		return userLogsRepos.findByDateWildCard(date);
	}
	
	public List<DocumentActivity> getChartData(int range) throws ParseException{
		
		List<DocumentActivity> list = new LinkedList<>();
		Calendar calendar = Calendar.getInstance();
		
		//PARSING OF STRING DATE
		SimpleDateFormat iniDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter = new SimpleDateFormat("MMMMM dd, yyyy"); 
		
		calendar.add(Calendar.DATE, -range);
		//DATE OPS
		
		for(int i=1;i<=range;i++) {
			DocumentActivity docActivity = new DocumentActivity();
			calendar.add(Calendar.DATE, 1);
			docActivity.setDateOfActivity(iniDateFormatter.format(calendar.getTime()).toString());
			docActivity.setNumberOfAction(findByDateWildCard(iniDateFormatter.format(calendar.getTime()).toString()+"%").size());
			docActivity.setCreation(findByActionWildCard("ADDED%",iniDateFormatter.format(calendar.getTime()).toString()+"%").size());
			docActivity.setSuccessfulTransfer(findByActionWildCard("RECEIVED%",iniDateFormatter.format(calendar.getTime()).toString()+"%").size());
			docActivity.setTransfer(findByActionWildCard("FORWARDED%",iniDateFormatter.format(calendar.getTime()).toString()+"%").size());
			
			list.add(docActivity);
		}
		return list;
	}
	


}
