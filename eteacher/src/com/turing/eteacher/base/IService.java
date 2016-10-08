/**
 * 
 */
package com.turing.eteacher.base;

import java.io.Serializable;
import java.util.List;

/**
 * <li>项目名称: WebKeyCore 
 * <li>功能描述: WebKey底层业务service接口(泛型) 
 * <li>版权: Copyright (c) 2000-2007 UniWin Co. Ltd. 
 * <li>公司:
 * 中信联信息技术有限公司
 * 
 * @author caojian
 * @version 1.0
 */
public interface IService<T> {

    /**
     * 
     * <li>功能描述：根据指定的实体类型和持久化标识符，返回它的持久化实例,如果没有这样的持久化实例，则返回null。
     * 
     * @param id
     *            Serializable 主键
     * @return T 持久化实例
     * @author caojian
     */
    public T get(Serializable id);

    public Object get(Class clz, Serializable id);

    /**
     * 
     * <li>功能描述：新增一条实体。
     * 
     * @param entity
     *            T 实体
     * @return 主键
     * @author caojian
     */
    public Serializable add(T entity);

    /**
     * 
     * <li>功能描述：新增一条实体。
     * 
     * @param entity
     *            Object 实体
     * @return 主键
     * @author caojian
     */
    public Serializable save(Object entity);

    /**
     * 
     * <li>功能描述：修改一条数据。
     * 
     * @param entity
     *            T 实体
     * @author caojian
     */
    public void update(T entity);

    /**
     * 
     * <li>功能描述：删除一条记录。
     * 
     * @param entity
     *            T 实体
     * @author caojian
     */
    public void delete(T entity);

    /**
     * 
     * <li>功能描述：删除一条记录(根据主键)。
     * 
     * @param id
     *            Serializable 主键
     * @author caojian
     */
    public void deleteById(Serializable id);
   
    /**
     * 
     * <li>功能描述：保存或更新给定的实例。
     * 
     * @param entity
     *            T 实例
     * @author caojian
     */
    public void saveOrUpdate(T entity);
    
    /**
     * 
     * <li>功能描述：根据给定实体类型，返回该类型实体的所有持久化实例。
     * 
     * @return List<T> 结果集
     * @author caojian
     */
    public List<T> findAll();

}
