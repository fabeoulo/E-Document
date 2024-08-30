/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.advantech.model.db2;

/**
 *
 * @author Justin.Yeh
 */
public interface IWorktimeForWebService {

    public String getModelName();

    public IUserM9 getUserBySpeOwnerId();

    public IUserM9 getUserByQcOwnerId();

    public IUserM9 getUserByEeOwnerId();

    public IUserM9 getUserByMpmOwnerId();

//    public void setUserBySpeOwnerId(IUserM9 userBySpeOwnerId);
//
//    public void setUserByEeOwnerId(IUserM9 userBySpeOwnerId);
//
//    public void setUserByQcOwnerId(IUserM9 userBySpeOwnerId);
//
//    public void setUserByMpmOwnerId(IUserM9 userBySpeOwnerId);
//    
//    public Object getDefaultByType(Class<?> type, String fieldName);
}
