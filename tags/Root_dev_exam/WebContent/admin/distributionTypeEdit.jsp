<%-- 
 * UniTime 3.0 (University Course Timetabling & Student Sectioning Application)
 * Copyright (C) 2007, UniTime.org
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
 --%>
<%@ page import="org.unitime.timetable.util.Constants" %>
<%@ page import="org.unitime.timetable.model.Department" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/timetable.tld" prefix="tt" %>

<html:form action="/distributionTypeEdit">
	<html:hidden property="uniqueId"/>
	<table width="93%" border="0" cellspacing="0" cellpadding="3">	
		<TR>
			<TD colspan='2'>
			<tt:section-header>
				<tt:section-title>
					<bean:write name="distributionTypeEditForm" property="reference"/>
					<html:hidden property="reference"/>
				</tt:section-title>
				<html:submit property="op" value="Save"/>
				<html:submit property="op" value="Back"/>
			</tt:section-header>
			</TD>
		</TR>

		<tr>
			<td>Id:</td>
			<td>
				<bean:write name="distributionTypeEditForm" property="requirementId"/>
				<html:hidden property="requirementId"/>
				<html:errors property="requirementId"/>
			</td>
		</tr>
		<tr>
			<td>Abbreviation:</td>
			<td>
				<html:text property="abbreviation" size="60"/>
				<html:errors property="abbreviation"/>
			</td>
		</tr>
		<tr>
			<td>Name:</td>
			<td>
				<html:text property="label" size="60"/>
				<html:errors property="label"/>
			</td>
		</tr>
		<tr>
			<td>Allow Instructor Preference:</td>
			<td>
				<html:checkbox property="instructorPref"/>
				<html:errors property="instructorPref"/>
			</td>
		</tr>
		<tr>
			<td>Sequencing Required:</td>
			<td>
				<logic:equal name="distributionTypeEditForm" property="sequencingRequired" value="true">
					Yes
				</logic:equal>
				<logic:equal name="distributionTypeEditForm" property="sequencingRequired" value="false">
					No
				</logic:equal>
				<html:hidden property="sequencingRequired"/>
				<html:errors property="sequencingRequired"/>
			</td>
		</tr>
		<tr>
			<td>Allow Preferences:</td>
			<td>
				<html:text property="allowedPref" size="10"/>
				<html:errors property="allowedPref"/>
			</td>
		</tr>
		<tr>
			<td>Description:</td>
			<td>
				<html:textarea property="description" rows="4" cols="160"/>
				<html:errors property="description"/>
			</td>
		</tr>
		
		<TR>
			<TD colspan='2'>
				&nbsp;
			</TD>
		</TR>

		<TR>
			<TD colspan='2'>
			<tt:section-title>Restrict Access</tt:section-title>
			</TD>
		</TR>

		<TR>
			<TD valign="top">Departments:</TD>
			<TD>
				<logic:iterate name="distributionTypeEditForm" property="departmentIds" id="deptId">
					<logic:iterate scope="request" name="<%=Department.DEPT_ATTR_NAME%>" id="dept">
						<logic:equal name="dept" property="value" value="<%=deptId.toString()%>">
							<bean:write name="dept" property="label"/>
							<input type="hidden" name="depts" value="<%=deptId%>">
							<BR>
						</logic:equal>
					</logic:iterate>
				</logic:iterate>
				<html:select property="departmentId">
					<html:option value="<%=Constants.BLANK_OPTION_VALUE%>"><%=Constants.BLANK_OPTION_LABEL%></html:option>
					<html:options collection="<%=Department.DEPT_ATTR_NAME%>" property="value" labelProperty="label"/>
				</html:select>
				&nbsp;
				<html:submit property="op" value="Add Department"/>
				&nbsp;
				<html:submit property="op" value="Remove Department"/>
				&nbsp;
				<html:errors property="department"/>
			</TD>
		</TR>
		
		<tr>
			<td valign="middle" colspan="2">
				<tt:section-title/>
			</td>
		<tr>
		
		<TR>
			<TD colspan='2' align='right'>
				<html:submit property="op" value="Save"/>
				<html:submit property="op" value="Back"/>
			</TD>
		</TR>
	</table>	
</html:form>
