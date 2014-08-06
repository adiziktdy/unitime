/*
 * UniTime 3.2 - 3.5 (University Timetabling Application)
 * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors
 * as indicated by the @authors tag.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
*/
package org.unitime.timetable.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.unitime.timetable.model.base.BaseCourseHistory;




/**
 * @author Tomas Muller
 */
public class CourseHistory extends BaseCourseHistory {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CourseHistory () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CourseHistory (java.lang.Long uniqueId) {
		super(uniqueId);
	}

/*[CONSTRUCTOR MARKER END]*/

	/**
	 * Retrieves all course history data for the academic session
	 * @param sessionId academic session
	 * @return List of CourseHistory objects
	 */
	public static List getCourseHistoryList(Long sessionId) 
			throws HibernateException {
	    
		return getHistoryList(sessionId, CourseHistory.class);
	}
	

}
