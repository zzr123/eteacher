package com.turing.eteacher.service;

import java.util.List;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.CustomFile;

public interface IFileService extends IService<CustomFile> {

	public List<CustomFile> getListByDataId(String dataId);
}
