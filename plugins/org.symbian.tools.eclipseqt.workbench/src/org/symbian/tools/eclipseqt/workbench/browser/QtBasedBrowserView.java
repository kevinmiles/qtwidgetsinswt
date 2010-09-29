/**
 * Copyright (c) 2010 Symbian Foundation and/or its subsidiary(-ies).
 * All rights reserved.
 * This component and the accompanying materials are made available
 * under the terms of the GNU Lesser General Public License
 * which accompanies this distribution
 * 
 * Initial Contributors:
 * Symbian Foundation - initial contribution.
 * Contributors:
 * Description:
 * Overview:
 * Details:
 * Platforms/Drives/Compatibility:
 * Assumptions/Requirement/Pre-requisites:
 * Failures and causes:
 */
package org.symbian.tools.eclipseqt.workbench.browser;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * This opens the QWebView in a view.
 * 
 * @author Eugene Ostroukhov
 */
public class QtBasedBrowserView extends ViewPart {
	private QtBasedBrowserPane browserPane;

	public void createPartControl(Composite parent) {
		browserPane = new QtBasedBrowserPane() {
			@Override
			protected IStatusLineManager getStatusLineManager() {
				return getViewSite().getActionBars().getStatusLineManager();
			}
		};
		browserPane.createPartControl(parent);
	}

	@Override
	public void setFocus() {
		browserPane.setFocus();
	}
}