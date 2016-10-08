package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Score;

public interface IScoreService extends IService<Score> {

	public List<Map> getScoreList(String courseId);
	
	public void saveScore(List<Score> scoreList);
	
	public int[] getScoreStatisticsData(String courseId, String scoreType);
	
	public void importScore(String courseId, List<Map> datas);
}
