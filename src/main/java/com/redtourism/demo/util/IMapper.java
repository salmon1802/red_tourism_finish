package com.redtourism.demo.util;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
public interface IMapper<T> extends Mapper<T>, MySqlMapper<T> {
	//FIXME 特别注意，该接口不能被扫描到，否则会出错
}