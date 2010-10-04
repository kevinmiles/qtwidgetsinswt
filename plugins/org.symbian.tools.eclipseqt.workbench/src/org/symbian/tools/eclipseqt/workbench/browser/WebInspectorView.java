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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.symbian.tools.eclipseqt.qwebview.SWTQWebView;
import org.symbian.tools.eclipseqt.qwebview.WebInspector;

/**
 * View with embedded webkit inspector.
 * 
 * @author Eugene Ostroukhov
 */
public class WebInspectorView extends ViewPart {
	public static final String VIEW_ID = "org.symbian.tools.eclipseqt.workbench.inspector";
	
	private WebInspector webInspector;

	@Override
	public void createPartControl(Composite parent) {
		webInspector = new WebInspector(parent, SWT.NONE);
		webInspector.setLayoutData(GridData.FILL_BOTH);
	}

	@Override
	public void setFocus() {
		webInspector.setFocus();
	}

	public void setBrowser(SWTQWebView view) {
		webInspector.setBrowser(view);
	}
}
