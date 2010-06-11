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
package org.unitime.timetable.gwt.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.unitime.timetable.gwt.shared.CurriculumInterface;
import org.unitime.timetable.gwt.shared.CurriculumInterface.AcademicClassificationInterface;
import org.unitime.timetable.gwt.shared.CurriculumInterface.CurriculumClassificationInterface;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;

public class CurriculaClassifications extends Composite {
	
	private FlexTable iTable;

	private List<AcademicClassificationInterface> iClassifications = null;
	private List<ExpectedChangedHandler> iExpectedChangedHandlers = new ArrayList<ExpectedChangedHandler>();
	private List<NameChangedHandler> iNameChangedHandlers = new ArrayList<NameChangedHandler>();

	public CurriculaClassifications() {
		iTable = new FlexTable();
		iTable.setCellPadding(2);
		iTable.setCellSpacing(0);
		initWidget(iTable);
	}
	
	public List<AcademicClassificationInterface> getClassifications() {
		return iClassifications;
	}
	
	public void setup(List<AcademicClassificationInterface> classifications) {
		iTable.clear(true);
		iClassifications = classifications;
		iTable.setText(0, 0, "Name:");
		iTable.setText(1, 0, "Classification:");
		iTable.setText(2, 0, "Expected Students:");
		iTable.getCellFormatter().setWordWrap(2, 0, false);
		iTable.setText(3, 0, "Enrolled Students:");
		iTable.getCellFormatter().setWordWrap(3, 0, false);
		iTable.setText(4, 0, "Last-Like Students:");
		iTable.getCellFormatter().setWordWrap(4, 0, false);
		int col = 0;
		for (final AcademicClassificationInterface clasf: iClassifications) {
			col ++;
			final MyTextBox name = new MyTextBox(true);
			name.setText(clasf.getCode());
			name.setWidth("60px");
			name.setMaxLength(20);
			final int xcol = col - 1;
			name.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					NameChangedEvent e = new NameChangedEvent(clasf, xcol, name.getText());
					for (NameChangedHandler h: iNameChangedHandlers)
						h.nameChanged(e);
				}
			});
			iTable.setWidget(0, col, name);
			iTable.getCellFormatter().setHorizontalAlignment(0, col, HasHorizontalAlignment.ALIGN_CENTER);
			
			iTable.setWidget(1, col, new HTML(clasf.getName().replace(" ", "<br>")));
			iTable.getCellFormatter().setHorizontalAlignment(1, col, HasHorizontalAlignment.ALIGN_CENTER);
			
			final MyTextBox expected = new MyTextBox(true);
			iTable.setWidget(2, col, expected);
			iTable.getCellFormatter().setHorizontalAlignment(2, col, HasHorizontalAlignment.ALIGN_CENTER);
			expected.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					Integer exp = null;
					if (!expected.getText().isEmpty()) {
						try {
							exp = Integer.parseInt(expected.getText());
						} catch (Exception e) { }
					}
					ExpectedChangedEvent e = new ExpectedChangedEvent(clasf, xcol, exp);
					for (ExpectedChangedHandler h: iExpectedChangedHandlers)
						h.expectedChanged(e);
				}
			});
			MyTextBox enrl = new MyTextBox(false);
			iTable.setWidget(3, col, enrl);
			iTable.getCellFormatter().setHorizontalAlignment(3, col, HasHorizontalAlignment.ALIGN_CENTER);
			MyTextBox ll = new MyTextBox(false);
			iTable.setWidget(4, col, ll);
			iTable.getCellFormatter().setHorizontalAlignment(4, col, HasHorizontalAlignment.ALIGN_CENTER);
		}
	}
	
	public void populate(TreeSet<CurriculumClassificationInterface> classifications) {
		int col = 0;
		for (AcademicClassificationInterface clasf: iClassifications) {
			CurriculumClassificationInterface ci = null;
			if (classifications != null && !classifications.isEmpty())
				for (CurriculumClassificationInterface x: classifications) {
					if (x.getAcademicClassification().getId().equals(clasf.getId())) { ci = x; break; }
				}
			if (ci == null) {
				setName(col, clasf.getCode());
				setExpected(col, null);
				setEnrollment(col, null);
				setLastLike(col, null);
			} else {
				setName(col, ci.getName());
				setExpected(col, ci.getExpected());
				setEnrollment(col, ci.getEnrollment());
				setLastLike(col, ci.getLastLike());
			}
			col++;
		}
	}
	
	public boolean saveCurriculum(CurriculumInterface c) {
		if (c.hasClassifications()) c.getClassifications().clear();
		int col = 0;
		for (AcademicClassificationInterface ac: iClassifications) {
			Integer exp = getExpected(col);
			if (exp != null) {
				CurriculumClassificationInterface clasf = new CurriculumClassificationInterface();
				clasf.setAcademicClassification(ac);
				clasf.setCurriculumId(c.getId());
				clasf.setExpected(exp);
				clasf.setLastLike(getLastLike(col));
				clasf.setName(getName(col));
				c.addClassification(clasf);
			}
			col ++;
		}
		return true;
	}
	
	public void setEnabled(boolean enabled) {
		for (int i = 0; i < iClassifications.size(); i++) {
			((MyTextBox)iTable.getWidget(0, 1 + i)).setEnabled(enabled);
			((MyTextBox)iTable.getWidget(2, 1 + i)).setEnabled(enabled);
			boolean visible = enabled || getExpected(i) != null || getEnrollment(i) != null || getLastLike(i) != null;
			for (int j = 0; j < 5; j++)
				iTable.getWidget(j, 1 + i).setVisible(visible);
		}
	}
	
	public static class MyTextBox extends TextBox {
		public MyTextBox(boolean editable) {
			super();
			setWidth("60px");
			setStyleName("gwt-SuggestBox");
			setMaxLength(6);
			setTextAlignment(TextBox.ALIGN_RIGHT);
			if (!editable) {
				setEnabled(false);
				getElement().getStyle().setBorderColor("transparent");
				getElement().getStyle().setBackgroundColor("transparent");
			}
		}
		
		public void setEnabled(boolean enabled) {
			super.setEnabled(enabled);
			if (enabled) {
				getElement().getStyle().setBorderColor(null);
				getElement().getStyle().setBackgroundColor(null);
			} else {
				getElement().getStyle().setBorderColor("transparent");
				getElement().getStyle().setBackgroundColor("transparent");
			}
		}
	}
	
	public int getColumn(AcademicClassificationInterface classification) {
		for (int i = 0; i < iClassifications.size(); i++)
			if (classification.getId().equals(iClassifications.get(i).getId()))
				return i;
		return -1;
	}
	
	public String getName(int column) {
		return ((TextBox)iTable.getWidget(0, 1 + column)).getText();
	}
	
	public void setName(int column, String name) {
		((TextBox)iTable.getWidget(0, 1 + column)).setText(name);
	}

	public Integer getExpected(int column) {
		String text = ((TextBox)iTable.getWidget(2, 1 + column)).getText();
		if (text.isEmpty()) return null;
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void setExpected(int column, Integer expected) {
		((TextBox)iTable.getWidget(2, 1 + column)).setText(expected == null ? "" : expected.toString());
	}

	
	public Integer getEnrollment(int column) {
		String text = ((TextBox)iTable.getWidget(3, 1 + column)).getText();
		if (text.isEmpty()) return null;
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			return null;
		}
	}

	public void setEnrollment(int column, Integer enrollment) {
		((TextBox)iTable.getWidget(3, 1 + column)).setText(enrollment == null ? "" : enrollment.toString());
	}
	
	public Integer getLastLike(int column) {
		String text = ((TextBox)iTable.getWidget(4, 1 + column)).getText();
		if (text.isEmpty()) return null;
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			return null;
		}
	}

	public void setLastLike(int column, Integer lastLike) {
		((TextBox)iTable.getWidget(4, 1 + column)).setText(lastLike == null ? "" : lastLike.toString());
	}

	public static class ExpectedChangedEvent {
		private AcademicClassificationInterface iClassification;
		private int iColumn;
		private Integer iExpected;
		
		ExpectedChangedEvent(AcademicClassificationInterface classification, int column, Integer expected) {
			iClassification = classification;
			iColumn = column;
			iExpected = expected;
		}
		
		public AcademicClassificationInterface getClassification() { return iClassification; }
		public int getColumn() { return iColumn; }
		public Integer getExpected() { return iExpected; }
	}
	
	public static interface ExpectedChangedHandler {
		public void expectedChanged(ExpectedChangedEvent e);
	}
	
	public void addExpectedChangedHandler(ExpectedChangedHandler h) {
		iExpectedChangedHandlers.add(h);
	}
	
	public static class NameChangedEvent {
		private AcademicClassificationInterface iClassification;
		private int iColumn;
		private String iName;
		
		NameChangedEvent(AcademicClassificationInterface classification, int column, String name) {
			iClassification = classification;
			iColumn = column;
			iName = name;
		}
		
		public AcademicClassificationInterface getClassification() { return iClassification; }
		public int getColumn() { return iColumn; }
		public String getName() { return iName; }
	}
	
	public static interface NameChangedHandler {
		public void nameChanged(NameChangedEvent e);
	}
	
	public void addNameChangedHandler(NameChangedHandler h) {
		iNameChangedHandlers.add(h);
	}
}
