package com.turing.eteacher.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDAO<T> {

	private Class<T> entityClass;

	@Autowired
	private SessionFactory sessionFactory;

	public BaseDAO() {
		/**
		 * 通过反射获取子类确定的范型类
		 */
		Type type = getClass().getGenericSuperclass();
		try {
			Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
			this.entityClass = (Class<T>) trueType;
		} catch (Exception e) {
		}
	}
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	public T get(Serializable id) {
		return (T) getSession().get(entityClass, id);
	}
	
	public Object get(Class clz, Serializable id) {
        return getSession().get(clz, id);
    }
	
	public Serializable save(Object entity) {
        return getSession().save(entity);
    }
	
	public void delete(Object entity) {
		getSession().delete(entity);
    }
	
	public void deleteById(Serializable id) {
		getSession().delete(get(id));
    }
	
	public void update(Object entity) {
		getSession().update(entity);
    }
	
	public void saveOrUpdate(Object entity) {
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }
	
	public List find(String hql, Object...params){
		Query query = getSession().createQuery(hql);
		setQueryParams(query, params);
		return query.list();
	}
	
	public List<Map> findMap(String hql, Object...params){
		if (getSession() == null) {
			System.out.println("session is null");
		}
		if (hql == null) {
			System.out.println("hql is null");
		}
		Query query = getSession().createQuery(hql);
		setQueryParams(query, params);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	

	/**
     * 根据sql语句查询返回MAP的列表
     * @param sql
     * @param params
     * @return
     */
    public List<Map> findBySql(String sql, Object... params) {
        Query query = getSession().createSQLQuery(sql);
        setQueryParams(query, params);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    /**
     * @author lifei 
     * @param sql
     * @param params
     * @return
     */
    public int executeBySql(String sql, Object... params) {
    	Query query = getSession().createSQLQuery(sql);
    	setQueryParams(query, params);
    	query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    	return query.executeUpdate();
    }
    
    public List<Map> findBySqlAndPage(String sql, int first, int max, Object... params) {
        Query query = getSession().createSQLQuery(sql);
        setQueryParams(query, params);
        query.setFirstResult(first);
        query.setMaxResults(max);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
	
	private void setQueryParams(Query query, Object...params){
		if(null != params){
			if (params.length == 1 && params[0] instanceof List<?>) {
            List list = (List) params[0];
            for (int i = 0; i < list.size(); i++) {
                query.setParameter(i, list.get(i));
            }
        }
        else {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
		}
		
	}
	
	private Query setMapQueryParams(Query query, Map<String, Object> map) {
        if (map != null) {
            Set<String> keySet = map.keySet();
            for (String string : keySet) {
                Object obj = map.get(string);
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同  
                if (obj instanceof Collection<?>) {
                    if (obj != null && ((Collection<?>) obj).size() > 0) {
                        query.setParameterList(string, (Collection<?>) obj);
                    }
                    else {
                        query.setParameter(string, null);
                    }
                }
                else if (obj instanceof Object[]) {
                    if (obj != null && ((Object[]) obj).length > 0) {
                        query.setParameterList(string, (Object[]) obj);
                    }
                    else {
                        query.setParameter(string, null);
                    }
                }
                else {
                    query.setParameter(string, obj);
                }
            }
        }
        return query;
    }
	
	public List<T> findAll(){
		return getSession().createCriteria(entityClass).list();
	}
	
	 /**
     * 根据HQL语句分页查询符合条件的实体类的持久化列表
     * @param hql
     * @param first 数据起始索引
     * @param max 取多少条数据
     * @param params
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List findByPage(String hql, int first, int max, Object... params) {
        Query query = getSession().createQuery(hql);
        setQueryParams(query, params);
        query.setFirstResult(first);
        query.setMaxResults(max);
        return query.list();
    }
    
    /**
     * 根据HQL语句分页查询符合条件的实体类的持久化列表
     * @param hql
     * @param first 数据起始索引
     * @param max 取多少条数据
     * @param params
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List findMapByPage(String hql, int first, int max, Object... params) {
        Query query = getSession().createQuery(hql);
        setQueryParams(query, params);
        query.setFirstResult(first);
        query.setMaxResults(max);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
	
	/**
     * 通过hql查询单个结果
     * @param hql 查询语句
     * @param params 查询参数
     * @return
     */
    public Object getUniqueResult(String hql, Object... params) {
        Query query = getSession().createQuery(hql);
        setQueryParams(query, params);
        return query.uniqueResult();
    }
    
    /**
     * 通过hql查询单个结果
     * @param hql 查询语句
     * @param params 查询参数
     * @return
     */
    public Object getUniqueResultBySql(String sql, Object... params) {
        Query query = getSession().createSQLQuery(sql);
        setQueryParams(query, params);
        return query.uniqueResult();
    }
	
	public int executeHql(String hql, Object...params){
		Query query = getSession().createQuery(hql);
		setQueryParams(query, params);
		return query.executeUpdate();
	}
	
	public int executeHqlByParams(String hql, Map params){
		Query query = getSession().createQuery(hql);
		setMapQueryParams(query, params);
		return query.executeUpdate();
	}
}
