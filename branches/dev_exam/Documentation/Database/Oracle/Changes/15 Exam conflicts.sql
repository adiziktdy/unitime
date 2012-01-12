/*
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
*/


/**
 * Added exam-conflict table and its M:N relations to exams and students
 **/
 
create table xconflict (
	uniqueid number(20,0) constraint nn_xconflict_uniqueid not null,
	conflict_type number(10,0) constraint nn_xconflict_type not null,
	distance float
);

alter table xconflict
  add constraint pk_xconflict primary key (uniqueid);
  
create table xconflict_exam (
	conflict_id number(20,0) constraint nn_xconflict_ex_conf not null,
	exam_id number(20,0) constraint nn_xconflict_ex_exam not null
);

alter table xconflict_exam
  add constraint pk_xconflict_exam primary key (conflict_id, exam_id);

alter table xconflict_exam
  add constraint fk_xconflict_ex_conf foreign key (conflict_id)
  references xconflict (uniqueid) on delete cascade;

alter table xconflict_exam
  add constraint fk_xconflict_ex_exam  foreign key (exam_id)
  references exam (uniqueid) on delete cascade;

create table xconflict_student (
	conflict_id number(20,0) constraint nn_xconflict_st_conf  not null,
	student_id number(20,0) constraint nn_xconflict_st_student  not null
);

alter table xconflict_student
  add constraint pk_xconflict_student primary key (conflict_id, student_id);

alter table xconflict_student
  add constraint fk_xconflict_st_conf foreign key (conflict_id)
  references xconflict (uniqueid) on delete cascade;

alter table xconflict_student
  add constraint fk_xconflict_st_student foreign key (student_id)
  references student (uniqueid) on delete cascade;
  
create index idx_xconflict_exam on xconflict_exam(exam_id);
	
/*
 * Update database version
 */

update application_config set value='15' where name='tmtbl.db.version';

commit;