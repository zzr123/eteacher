package com.turing.eteacher.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.TermDAO;
import com.turing.eteacher.model.Term;
import com.turing.eteacher.service.ITermService;
import com.turing.eteacher.util.DateUtil;

@Service
public class TermServiceImpl extends BaseService<Term> implements ITermService {

	@Autowired
	private TermDAO termDAO;
	
	@Override
	public BaseDAO<Term> getDAO() {
		return termDAO;
	}
	
	@Override
	public void saveTerm(Term term) {
		/*try{
			String year="";
			String termnum="";
			String termName = "";
			if(termnum.equals("1")){
				termName=year+"学年第一学期";
			}
			else if(termnum.equals("2")){
				termName=year+"学年第二学期";
			}
			else if(termnum.equals("3")){
				termName=year+"学年第三学期";
			}
			else{
				termName=year+"学年第四学期";
			}*/
		/*if(term.getStartDate().substring(0,4).equals(term.getEndDate().substring(0,4))){
			term.setYear((Integer.parseInt(term.getStartDate().substring(0,4))-1) + "-" + term.getStartDate().substring(0,4));
			term.setTerm("2");
		}
		else{
			term.setYear(term.getStartDate().substring(0,4) + "-" + term.getEndDate().substring(0,4));
			term.setTerm("1");
		}
		if(StringUtil.isNotEmpty(term.getTermId())){
			Term serverRecord = termDAO.get(term.getTermId());
			BeanUtils.copyToModel(term, serverRecord);
			termDAO.update(serverRecord);
		}
		else{
			termDAO.save(term);
		}*/
		/*String hql = "insert into TermPrivate (startDate,endDate,weekCount) values(?,?,?)";
		List<Map> list = termDAO.findMap(hql, term);
		return list;*/
	}
	
	

	

	@Override
	public List<Map> getListByGrade(int grade) {
		List<Map> list = new ArrayList();
		Map record = null;
		int year = Calendar.getInstance().get(Calendar.YEAR); 
		int month = Calendar.getInstance().get(Calendar.MONTH); 
		for(int i = grade;i<=year;i++){
			if(i<year||month>=8){
				record = new HashMap();
				record.put("year", i+ "-" + (i + 1));
				record.put("term", 1);
				record.put("termName", i+ "-" + (i + 1) + "年第一学期");
				list.add(record);
			}
			if(i<year){
				record = new HashMap();
				record.put("year", i+ "-" + (i + 1));
				record.put("term", 2);
				record.put("termName", i+ "-" + (i + 1) + "年第二学期");
				list.add(record);
			}
		}
		Collections.reverse(list);
		return list;
	}

	@Override
	public Term getByYearAndTerm(int year, int term) {
		String hql = "from Term t where t.year = ? and t.term = ?";
		List<Term> list = termDAO.find(hql, year, term);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	//教师端
	/**
	 * 获取学期公有数据列表
	 */
	@Override
	public List<Map> getListTerms() {
		String hql="from Term";
		List<Map> list=termDAO.getListTerms(hql);
		return list;
	}
	/**
	 * 获取学期私有数据列表
	 */
	@Override
	public List<Map> getListTermPrivates(String userId) {
		String hql="select tp.tpId as tpId,t.termName as termName,tp.startDate as startTime,"+
	               "tp.endDate as endTime from TermPrivate tp,Term t where tp.termId=t.termId "+
				   "and tp.userId=?";
		List<Map> list = termDAO.findMap(hql, userId);
		return list;
	}
	/**
	 * 获取当前学期
	 */
	@Override
	public Map getCurrentTerm(String userId) {
		String now = DateUtil.getCurrentDateStr(DateUtil.YYYYMMDD);
		String hql = "select tp.tpId as termId,"
				+ "t.termName as termName,"
				+ "tp.startDate as startDate,"
				+ "tp.endDate as endDate "
				+ "from TermPrivate tp ,Term t "
				+ "where tp.userId = ? "
				+ "and ? >= tp.startDate "
				+ "and ? <= tp.endDate order by tp.createTime desc";
		List<Map> list = termDAO.findMap(hql, userId, now, now);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		else{
			list = getTermList(userId);
			if(list!=null&&list.size()>0){
				return list.get(0);
			}
		}
		return null;
	}
	/**
	 * 获取最新的一个学期
	 */
	@Override
	public List<Map> getTermList(String userId) {
		String hql = "select tp.tpId as termId,"
				+ "t.termName as termName,"
				+ "tp.startDate as startDate,"
				+ "tp.endDate as endDate "
				+ "from TermPrivate tp,Term t "
				+ "where tp.termId = t.termId "
				+ "and tp.userId = ? "
				+ "order by tp.startDate desc";
		return termDAO.findMap(hql, userId);
	}

	@Override
	public List<Map> getListTermPrivatesName(String userId) {
		String hql = "SELECT t_term_private.TP_ID  AS id, "+
		"t_term.TERM_NAME AS content " +
		"FROM t_term_private LEFT JOIN t_term " +
		"ON t_term_private.TREM_ID =  t_term.TERM_ID "+
		"WHERE t_term_private.USER_ID = ?";
		List list = termDAO.findBySql(hql, userId);
		return list;
	}
}
