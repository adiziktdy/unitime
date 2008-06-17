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
package org.unitime.timetable.model;

import org.unitime.timetable.model.base.BaseJointEnrollment;



public class JointEnrollment extends BaseJointEnrollment {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public JointEnrollment () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public JointEnrollment (java.lang.Long uniqueId) {
		super(uniqueId);
	}

	/**
	 * Constructor for required fields
	 */
	public JointEnrollment (
		java.lang.Long uniqueId,
		org.unitime.timetable.model.Solution solution,
		org.unitime.timetable.model.Class_ class1,
		org.unitime.timetable.model.Class_ class2,
		java.lang.Double jenrl) {

		super (
			uniqueId,
			solution,
			class1,
			class2,
			jenrl);
	}

/*[CONSTRUCTOR MARKER END]*/


}
