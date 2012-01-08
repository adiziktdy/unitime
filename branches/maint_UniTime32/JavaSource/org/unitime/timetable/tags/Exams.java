/*
 * UniTime 3.2 (University Timetabling Application)
 * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors
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
package org.unitime.timetable.tags;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.unitime.commons.Debug;
import org.unitime.commons.User;
import org.unitime.commons.web.Web;
import org.unitime.commons.web.WebTable;
import org.unitime.timetable.model.BuildingPref;
import org.unitime.timetable.model.Class_;
import org.unitime.timetable.model.CourseOffering;
import org.unitime.timetable.model.DepartmentalInstructor;
import org.unitime.timetable.model.DistributionPref;
import org.unitime.timetable.model.MidtermPeriodPreferenceModel;
import org.unitime.timetable.model.Exam;
import org.unitime.timetable.model.ExamPeriodPref;
import org.unitime.timetable.model.InstrOfferingConfig;
import org.unitime.timetable.model.InstructionalOffering;
import org.unitime.timetable.model.PeriodPreferenceModel;
import org.unitime.timetable.model.PreferenceLevel;
import org.unitime.timetable.model.RoomFeaturePref;
import org.unitime.timetable.model.RoomGroupPref;
import org.unitime.timetable.model.RoomPref;
import org.unitime.timetable.model.Session;
import org.unitime.timetable.model.Settings;
import org.unitime.timetable.model.TimetableManager;
import org.unitime.timetable.solver.WebSolver;
import org.unitime.timetable.solver.exam.ExamSolverProxy;
import org.unitime.timetable.solver.exam.ui.ExamAssignment;
import org.unitime.timetable.solver.exam.ui.ExamAssignmentInfo;
import org.unitime.timetable.util.Constants;
import org.unitime.timetable.webutil.RequiredTimeTable;


/**
 * @author Tomas Muller
 */
public class Exams extends BodyTagSupport {
	private static final long serialVersionUID = -666904499562226756L;
	private String iType = null;
    private boolean iAdd = true;
    private String iId = null;
    
    public Exams() {
        super();
    }

    public void setType(String type) {
        iType = type;
    }
    public String getType() {
        return iType;
    }

    public void setAdd(boolean add) {
        iAdd = add;
    }
    public boolean isAdd() {
        return iAdd;
    }

    public void setId(String id) {
        iId = id;
    }
    public String getId() {
        return iId;
    }

    public int doStartTag() throws JspException {
        return EVAL_BODY_BUFFERED;
    }
    
    public int doEndTag() throws JspException {
        try {
            User user = Web.getUser(pageContext.getSession());
            if (user==null)return EVAL_PAGE; 
            TimetableManager manager = TimetableManager.getManager(user);
            Session session = Session.getCurrentAcadSession(user);
            
            String objectIdStr = (getBodyContent()==null?null:getBodyContent().getString().trim());
            if (objectIdStr==null || objectIdStr.length()==0) objectIdStr = (getId()==null?null:getId().trim());
            if (objectIdStr==null || objectIdStr.length()==0) return EVAL_PAGE;
            Long objectId = Long.parseLong(objectIdStr);
            
            boolean edit = (manager==null || session==null? false : manager.canEditExams(session, user));
            boolean view = (manager==null || session==null? false : manager.canSeeExams(session, user));
            
            List exams = Exam.findAllRelated(getType(),objectId);
            if (exams==null || exams.isEmpty()) {
                if (!edit || !iAdd) return EVAL_PAGE;
            }
            
            String title = (exams.size()==1?"Examination":"Examinations");
            if (edit && iAdd) 
                title = "<table width='100%'><tr><td width='100%'>" + 
                    "<DIV class=\"WelcomeRowHeadNoLine\">Examinations</DIV>"+
                    "</td><td style='padding-bottom: 2px'>"+
                    "<input type=\"button\" onclick=\"document.location='examEdit.do?firstType="+getType()+"&firstId="+objectId+"';\" class=\"btn\" accesskey='X' title='Add Examination (Alt+X)' value=\"Add Examination\">"+
                    "</td></tr></table>";
            
            WebTable table = new WebTable(10, title,
                    new String[] { "Classes / Courses", "Type", "Length", "Seating<br>Type", "Size", "Max<br>Rooms", 
                        "Instructor", "Period<br>Preferences", "Room<br>Preferences", "Distribution<br>Preferences"},
                    new String[] {"left", "left", "right", "center", "right", "right", "left", 
                        "left", "left", "left"},
                        new boolean[] {true, true, true, true, true, true, true, true, true}
                    );

            boolean timeVertical = RequiredTimeTable.getTimeGridVertical(user);
            boolean timeText = RequiredTimeTable.getTimeGridAsText(user);
            String instructorNameFormat = Settings.getSettingValue(user, Constants.SETTINGS_INSTRUCTOR_NAME_FORMAT);
            
            String backId = null;
            if ("PreferenceGroup".equals(pageContext.getRequest().getParameter("backType")))
                backId = pageContext.getRequest().getParameter("backId");
            if (pageContext.getRequest().getAttribute("examId")!=null)
                backId = pageContext.getRequest().getAttribute("examId").toString(); 
            boolean hasExamHash = false;
            
            if (exams!=null)  {
                ExamSolverProxy solver = WebSolver.getExamSolver(pageContext.getSession());
                int solverType = (solver==null?-1:solver.getExamType());
                
                boolean hasSolution = false;
                for (Iterator i=new TreeSet(exams).iterator();i.hasNext();) {
                    Exam exam = (Exam)i.next();
                    ExamAssignment assignment = null;
                    if (solver!=null && solverType==exam.getExamType())
                        assignment = solver.getAssignment(exam.getUniqueId());
                    else if (exam.getAssignedPeriod()!=null)
                        assignment = new ExamAssignment(exam);
                    if (assignment!=null && assignment.getPeriodId()!=null) {
                        hasSolution = true; break;
                    }
                }
                if (view) {
                    if (hasSolution)
                        table = new WebTable(10, title,
                                new String[] { "Classes / Courses", "Type", "Length", "Seating<br>Type", "Size", "Max<br>Rooms", 
                                "Instructor", "Assigned<br>Period", "Assigned<br>Room", "Student<br>Conflicts"},
                            new String[] {"left", "left", "right", "center", "right", "right", "left", 
                                "left", "left", "left"},
                                new boolean[] {true, true, true, true, true, true, true, true, true}
                            );
                
                    for (Iterator i=new TreeSet(exams).iterator();i.hasNext();) {
                        Exam exam = (Exam)i.next();
                    
                        String objects = "", instructors = "", perPref = "", roomPref = "", distPref = "";
                        
                        ExamAssignmentInfo assignment = null;
                        if (solver!=null && solverType==exam.getExamType())
                            assignment = solver.getAssignmentInfo(exam.getUniqueId());
                        else if (exam.getAssignedPeriod()!=null)
                            assignment = new ExamAssignmentInfo(exam);
                        
                        for (Enumeration e=exam.getOwnerObjects().elements();e.hasMoreElements();) {
                            Object object = e.nextElement();
                            if (objects.length()>0) objects+="<br>";
                            if (object instanceof Class_)
                                objects += ((Class_)object).getClassLabel();
                            else if (object instanceof InstrOfferingConfig)
                                objects += ((InstrOfferingConfig)object).toString();
                            else if (object instanceof InstructionalOffering)
                                objects += ((InstructionalOffering)object).getCourseName();
                            else if (object instanceof CourseOffering)
                                objects += ((CourseOffering)object).getCourseName();
                            else
                                objects += object.toString();
                        }
                        
                        if (!hasSolution || assignment==null || assignment.getPeriodId()==null) {
                            roomPref += exam.getEffectivePrefHtmlForPrefType(RoomPref.class);
                            if (roomPref.length()>0 && !roomPref.endsWith("<br>")) roomPref+="<br>";
                            roomPref += exam.getEffectivePrefHtmlForPrefType(BuildingPref.class);
                            if (roomPref.length()>0 && !roomPref.endsWith("<br>")) roomPref+="<br>";
                            roomPref += exam.getEffectivePrefHtmlForPrefType(RoomFeaturePref.class);
                            if (roomPref.length()>0 && !roomPref.endsWith("<br>")) roomPref+="<br>";
                            roomPref += exam.getEffectivePrefHtmlForPrefType(RoomGroupPref.class);
                            if (roomPref.endsWith("<br>")) roomPref = roomPref.substring(0, roomPref.length()-"<br>".length());
                            if (timeText || Exam.sExamTypeMidterm==exam.getExamType()) {
                                if (Exam.sExamTypeMidterm==exam.getExamType()) {
                                    MidtermPeriodPreferenceModel epx = new MidtermPeriodPreferenceModel(exam.getSession(), null);
                                    epx.load(exam);
                                    perPref+=epx.toString();
                                } else {
                                    perPref += exam.getEffectivePrefHtmlForPrefType(ExamPeriodPref.class);
                                }
                            } else {
                                PeriodPreferenceModel px = new PeriodPreferenceModel(exam.getSession(), null, exam.getExamType());
                                px.load(exam);
                                RequiredTimeTable rtt = new RequiredTimeTable(px);
                                File imageFileName = null;
                                try {
                                    imageFileName = rtt.createImage(timeVertical);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                String rttTitle = rtt.getModel().toString();
                                if (imageFileName!=null)
                                    perPref = "<img border='0' src='temp/"+(imageFileName.getName())+"' title='"+rttTitle+"'>";
                                else
                                    perPref += exam.getEffectivePrefHtmlForPrefType(ExamPeriodPref.class);
                            }
                            if (!hasSolution)
                                distPref += exam.getEffectivePrefHtmlForPrefType(DistributionPref.class);
                            else
                                distPref = "<i>Not Assigned</i>";
                        } else {
                            perPref = (view?assignment.getPeriodAbbreviationWithPref():assignment.getPeriodAbbreviation());
                            roomPref = (view?assignment.getRoomsNameWithPref("<br>"):assignment.getRoomsName("<br>")); 
                            int dc = assignment.getNrDirectConflicts();
                            String dcStr = (dc<=0?"<font color='gray'>0</font>":"<font color='"+PreferenceLevel.prolog2color("P")+"'>"+dc+"</font>");
                            int m2d = assignment.getNrMoreThanTwoConflicts();
                            String m2dStr = (m2d<=0?"<font color='gray'>0</font>":"<font color='"+PreferenceLevel.prolog2color("2")+"'>"+m2d+"</font>");
                            int btb = assignment.getNrBackToBackConflicts();
                            int dbtb = assignment.getNrDistanceBackToBackConflicts();
                            String btbStr = (btb<=0 && dbtb<=0?"<font color='gray'>0</font>":"<font color='"+PreferenceLevel.prolog2color("1")+"'>"+btb+(dbtb>0?" (d:"+dbtb+")":"")+"</font>");
                            distPref = (view?dcStr+", "+m2dStr+", "+btbStr:"<i>N/A</i>"); 
                        }
                        
                        for (Iterator j=new TreeSet(exam.getInstructors()).iterator();j.hasNext();) {
                            DepartmentalInstructor instructor = (DepartmentalInstructor)j.next();
                            if (instructors.length()>0) instructors+="<br>";
                            instructors += instructor.getName(instructorNameFormat);
                        }
                        
                        int nrStudents = exam.getSize();
                        
                        if (exam.getUniqueId().toString().equals(backId)) {
                            objects = "<A name='examHash'>"+objects+"</A>";
                            hasExamHash = true;
                        }
                        
                        table.addLine(
                                (view?"onClick=\"document.location='examDetail.do?examId="+exam.getUniqueId()+"';\"":null),
                                new String[] {
                                    objects,
                                    Exam.sExamTypes[exam.getExamType()],
                                    exam.getLength().toString(),
                                    (Exam.sSeatingTypeNormal==exam.getSeatingType()?"Normal":"Exam"),
                                    String.valueOf(nrStudents),
                                    exam.getMaxNbrRooms().toString(),
                                    instructors,
                                    perPref,
                                    roomPref,
                                    distPref
                                },
                                null,
                                exam.getUniqueId().toString());
                    }
                } else {
                    if (!hasSolution) return EVAL_PAGE;
                    table = new WebTable(5, title,
                            new String[] { "Classes / Courses", "Type", "Instructor", "Period", "Room"},
                            new String[] {"left", "left", "left", "left", "left"},
                            new boolean[] {true, true, true, true, true}
                        );
                    
                    for (Iterator i=new TreeSet(exams).iterator();i.hasNext();) {
                        Exam exam = (Exam)i.next();
                    
                        String objects = "", instructors = "", perPref = "", roomPref = "";
                        
                        ExamAssignmentInfo assignment = null;
                        if (solver!=null && solverType==exam.getExamType())
                            assignment = solver.getAssignmentInfo(exam.getUniqueId());
                        else if (exam.getAssignedPeriod()!=null)
                            assignment = new ExamAssignmentInfo(exam);
                        
                        for (Enumeration e=exam.getOwnerObjects().elements();e.hasMoreElements();) {
                            Object object = e.nextElement();
                            if (objects.length()>0) objects+="<br>";
                            if (object instanceof Class_)
                                objects += ((Class_)object).getClassLabel();
                            else if (object instanceof InstrOfferingConfig)
                                objects += ((InstrOfferingConfig)object).toString();
                            else if (object instanceof InstructionalOffering)
                                objects += ((InstructionalOffering)object).getCourseName();
                            else if (object instanceof CourseOffering)
                                objects += ((CourseOffering)object).getCourseName();
                            else
                                objects += object.toString();
                        }
                        
                        if (assignment==null || assignment.getPeriodId()==null) continue;

                        perPref = assignment.getPeriodName();
                        roomPref = assignment.getRoomsName("<br>"); 
                        
                        for (Iterator j=new TreeSet(exam.getInstructors()).iterator();j.hasNext();) {
                            DepartmentalInstructor instructor = (DepartmentalInstructor)j.next();
                            if (instructors.length()>0) instructors+="<br>";
                            instructors += instructor.getName(instructorNameFormat);
                        }
                        
                        if (exam.getUniqueId().toString().equals(backId)) {
                            objects = "<A name='examHash'>"+objects+"</A>";
                            hasExamHash = true;
                        }
                        
                        table.addLine(
                                null,
                                new String[] {
                                    objects,
                                    Exam.sExamTypes[exam.getExamType()],
                                    instructors,
                                    perPref,
                                    roomPref},
                                null,
                                exam.getUniqueId().toString());
                    }
                }
            }
            
            pageContext.getOut().println("<table width='100%' border='0' cellspacing='0' cellpadding='3'>");
            if ("Exam".equals(pageContext.getRequest().getParameter("backType"))) {
                pageContext.getOut().println("<tr><td colpan='9'><A name='examHash'>&nbsp;</A></td></tr>");
                hasExamHash = true;
            } else 
                pageContext.getOut().println("<tr><td colpan='9'>&nbsp;</td></tr>");
            pageContext.getOut().println(table.printTable());
            pageContext.getOut().println("</table>");
            if (hasExamHash)
                pageContext.getOut().println("<SCRIPT type='text/javascript' language='javascript'>location.hash = 'examHash';</SCRIPT>");
        } catch (Exception e) {
            Debug.error(e);
            try {
                pageContext.getOut().print("<font color='red'>ERROR: "+e.getMessage()+"</font>");
            } catch (IOException io) {}
        }
        
        return EVAL_PAGE;
    }
    
}