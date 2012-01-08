/*
 * UniTime 3.1 (University Timetabling Application)
 * Copyright (C) 2008, UniTime LLC, and individual contributors
 * as indicated by the @authors tag.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/
package org.unitime.timetable.model.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.unitime.timetable.model.dao.ExamPeriodPrefDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseExamPeriodPrefDAO extends org.unitime.timetable.model.dao._RootDAO {

	// query name references


	public static ExamPeriodPrefDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static ExamPeriodPrefDAO getInstance () {
		if (null == instance) instance = new ExamPeriodPrefDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return org.unitime.timetable.model.ExamPeriodPref.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a org.unitime.timetable.model.ExamPeriodPref
	 */
	public org.unitime.timetable.model.ExamPeriodPref cast (Object object) {
		return (org.unitime.timetable.model.ExamPeriodPref) object;
	}

	public org.unitime.timetable.model.ExamPeriodPref get(java.lang.Long key)
	{
		return (org.unitime.timetable.model.ExamPeriodPref) get(getReferenceClass(), key);
	}

	public org.unitime.timetable.model.ExamPeriodPref get(java.lang.Long key, Session s)
	{
		return (org.unitime.timetable.model.ExamPeriodPref) get(getReferenceClass(), key, s);
	}

	public org.unitime.timetable.model.ExamPeriodPref load(java.lang.Long key)
	{
		return (org.unitime.timetable.model.ExamPeriodPref) load(getReferenceClass(), key);
	}

	public org.unitime.timetable.model.ExamPeriodPref load(java.lang.Long key, Session s)
	{
		return (org.unitime.timetable.model.ExamPeriodPref) load(getReferenceClass(), key, s);
	}

	public org.unitime.timetable.model.ExamPeriodPref loadInitialize(java.lang.Long key, Session s) 
	{ 
		org.unitime.timetable.model.ExamPeriodPref obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param ExamPeriodPref a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Long save(org.unitime.timetable.model.ExamPeriodPref ExamPeriodPref)
	{
		return (java.lang.Long) super.save(ExamPeriodPref);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param ExamPeriodPref a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.Long save(org.unitime.timetable.model.ExamPeriodPref ExamPeriodPref, Session s)
	{
		return (java.lang.Long) save((Object) ExamPeriodPref, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param ExamPeriodPref a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(org.unitime.timetable.model.ExamPeriodPref ExamPeriodPref)
	{
		saveOrUpdate((Object) ExamPeriodPref);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param ExamPeriodPref a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(org.unitime.timetable.model.ExamPeriodPref ExamPeriodPref, Session s)
	{
		saveOrUpdate((Object) ExamPeriodPref, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param ExamPeriodPref a transient instance containing updated state
	 */
	public void update(org.unitime.timetable.model.ExamPeriodPref ExamPeriodPref) 
	{
		update((Object) ExamPeriodPref);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param ExamPeriodPref a transient instance containing updated state
	 * @param the Session
	 */
	public void update(org.unitime.timetable.model.ExamPeriodPref ExamPeriodPref, Session s)
	{
		update((Object) ExamPeriodPref, s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Long id)
	{
		delete((Object) load(id));
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param id the instance ID to be removed
	 * @param s the Session
	 */
	public void delete(java.lang.Long id, Session s)
	{
		delete((Object) load(id, s), s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param ExamPeriodPref the instance to be removed
	 */
	public void delete(org.unitime.timetable.model.ExamPeriodPref ExamPeriodPref)
	{
		delete((Object) ExamPeriodPref);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param ExamPeriodPref the instance to be removed
	 * @param s the Session
	 */
	public void delete(org.unitime.timetable.model.ExamPeriodPref ExamPeriodPref, Session s)
	{
		delete((Object) ExamPeriodPref, s);
	}
	
	/**
	 * Re-read the state of the given instance from the underlying database. It is inadvisable to use this to implement
	 * long-running sessions that span many business tasks. This method is, however, useful in certain special circumstances.
	 * For example 
	 * <ul> 
	 * <li>where a database trigger alters the object state upon insert or update</li>
	 * <li>after executing direct SQL (eg. a mass update) in the same session</li>
	 * <li>after inserting a Blob or Clob</li>
	 * </ul>
	 */
	public void refresh (org.unitime.timetable.model.ExamPeriodPref ExamPeriodPref, Session s)
	{
		refresh((Object) ExamPeriodPref, s);
	}


}