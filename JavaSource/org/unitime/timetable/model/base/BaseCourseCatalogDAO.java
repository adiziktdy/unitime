/*
 * UniTime 3.2 (University Timetabling Application)
 * Copyright (C) 2010, UniTime LLC, and individual contributors
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

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;

import org.unitime.timetable.model.CourseCatalog;
import org.unitime.timetable.model.dao._RootDAO;
import org.unitime.timetable.model.dao.CourseCatalogDAO;

public abstract class BaseCourseCatalogDAO extends _RootDAO {

	private static CourseCatalogDAO sInstance;

	public static CourseCatalogDAO getInstance () {
		if (sInstance == null) sInstance = new CourseCatalogDAO();
		return sInstance;
	}

	public Class getReferenceClass () {
		return CourseCatalog.class;
	}

	public Order getDefaultOrder () {
		return null;
	}

	public CourseCatalog get(Long uniqueId) {
		return (CourseCatalog) get(getReferenceClass(), uniqueId);
	}

	public CourseCatalog get(Long uniqueId, org.hibernate.Session hibSession) {
		return (CourseCatalog) get(getReferenceClass(), uniqueId, hibSession);
	}

	public CourseCatalog load(Long uniqueId) {
		return (CourseCatalog) load(getReferenceClass(), uniqueId);
	}

	public CourseCatalog load(Long uniqueId, org.hibernate.Session hibSession) {
		return (CourseCatalog) load(getReferenceClass(), uniqueId, hibSession);
	}

	public CourseCatalog loadInitialize(Long uniqueId, org.hibernate.Session hibSession) {
		CourseCatalog courseCatalog = load(uniqueId, hibSession);
		if (!Hibernate.isInitialized(courseCatalog)) Hibernate.initialize(courseCatalog);
		return courseCatalog;
	}

	public void save(CourseCatalog courseCatalog) {
		save((Object) courseCatalog);
	}

	public void save(CourseCatalog courseCatalog, org.hibernate.Session hibSession) {
		save((Object) courseCatalog, hibSession);
	}

	public void saveOrUpdate(CourseCatalog courseCatalog) {
		saveOrUpdate((Object) courseCatalog);
	}

	public void saveOrUpdate(CourseCatalog courseCatalog, org.hibernate.Session hibSession) {
		saveOrUpdate((Object) courseCatalog, hibSession);
	}


	public void update(CourseCatalog courseCatalog) {
		update((Object) courseCatalog);
	}

	public void update(CourseCatalog courseCatalog, org.hibernate.Session hibSession) {
		update((Object) courseCatalog, hibSession);
	}

	public void delete(Long uniqueId) {
		delete(load(uniqueId));
	}

	public void delete(Long uniqueId, org.hibernate.Session hibSession) {
		delete(load(uniqueId, hibSession), hibSession);
	}

	public void delete(CourseCatalog courseCatalog) {
		delete((Object) courseCatalog);
	}

	public void delete(CourseCatalog courseCatalog, org.hibernate.Session hibSession) {
		delete((Object) courseCatalog, hibSession);
	}

	public void refresh(CourseCatalog courseCatalog, org.hibernate.Session hibSession) {
		refresh((Object) courseCatalog, hibSession);
	}

	public List<CourseCatalog> findAll(org.hibernate.Session hibSession) {
		return hibSession.createQuery("from CourseCatalog").list();
	}

	public List<CourseCatalog> findBySession(org.hibernate.Session hibSession, Long sessionId) {
		return hibSession.createQuery("from CourseCatalog x where x.session.uniqueId = :sessionId").setLong("sessionId", sessionId).list();
	}
}
