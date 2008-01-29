/*
 * UniTime 3.0 (University Course Timetabling & Student Sectioning Application)
 * Copyright (C) 2007, UniTime.org, and individual contributors
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

import java.io.Serializable;


/**
 * This is an object that contains data related to the STUDENT_ACAD_AREA table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="STUDENT_ACAD_AREA"
 */

public abstract class BaseAcademicAreaClassification  implements Serializable {

	public static String REF = "AcademicAreaClassification";


	// constructors
	public BaseAcademicAreaClassification () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseAcademicAreaClassification (java.lang.Long uniqueId) {
		this.setUniqueId(uniqueId);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseAcademicAreaClassification (
		java.lang.Long uniqueId,
		org.unitime.timetable.model.Student student,
		org.unitime.timetable.model.AcademicClassification academicClassification,
		org.unitime.timetable.model.AcademicArea academicArea) {

		this.setUniqueId(uniqueId);
		this.setStudent(student);
		this.setAcademicClassification(academicClassification);
		this.setAcademicArea(academicArea);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Long uniqueId;

	// many to one
	private org.unitime.timetable.model.Student student;
	private org.unitime.timetable.model.AcademicClassification academicClassification;
	private org.unitime.timetable.model.AcademicArea academicArea;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="sequence"
     *  column="UNIQUEID"
     */
	public java.lang.Long getUniqueId () {
		return uniqueId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param uniqueId the new ID
	 */
	public void setUniqueId (java.lang.Long uniqueId) {
		this.uniqueId = uniqueId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: STUDENT_ID
	 */
	public org.unitime.timetable.model.Student getStudent () {
		return student;
	}

	/**
	 * Set the value related to the column: STUDENT_ID
	 * @param student the STUDENT_ID value
	 */
	public void setStudent (org.unitime.timetable.model.Student student) {
		this.student = student;
	}



	/**
	 * Return the value associated with the column: ACAD_CLASF_ID
	 */
	public org.unitime.timetable.model.AcademicClassification getAcademicClassification () {
		return academicClassification;
	}

	/**
	 * Set the value related to the column: ACAD_CLASF_ID
	 * @param academicClassification the ACAD_CLASF_ID value
	 */
	public void setAcademicClassification (org.unitime.timetable.model.AcademicClassification academicClassification) {
		this.academicClassification = academicClassification;
	}



	/**
	 * Return the value associated with the column: ACAD_AREA_ID
	 */
	public org.unitime.timetable.model.AcademicArea getAcademicArea () {
		return academicArea;
	}

	/**
	 * Set the value related to the column: ACAD_AREA_ID
	 * @param academicArea the ACAD_AREA_ID value
	 */
	public void setAcademicArea (org.unitime.timetable.model.AcademicArea academicArea) {
		this.academicArea = academicArea;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof org.unitime.timetable.model.AcademicAreaClassification)) return false;
		else {
			org.unitime.timetable.model.AcademicAreaClassification academicAreaClassification = (org.unitime.timetable.model.AcademicAreaClassification) obj;
			if (null == this.getUniqueId() || null == academicAreaClassification.getUniqueId()) return false;
			else return (this.getUniqueId().equals(academicAreaClassification.getUniqueId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getUniqueId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getUniqueId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}