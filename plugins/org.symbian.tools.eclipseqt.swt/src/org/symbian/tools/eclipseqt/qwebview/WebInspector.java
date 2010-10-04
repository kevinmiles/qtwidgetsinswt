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
package org.symbian.tools.eclipseqt.qwebview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.symbian.tools.eclipseqt.internal.SWT_Qt;

/**
 * This is a wrapper for <a href="http://doc.troll.no/4.7/qwebinspector.html">QWebInspector</a> class.
 * 
 * @author Eugene Ostroukhov
 */
public final class WebInspector extends QtControlWrapper {

	public WebInspector(Composite parent, int style) {
		super(parent, style | SWT.EMBEDDED);
		qtpointer = qt_createWebInspector(SWT_Qt.getNativeId(this), new QtDelegate(this));
	}

	public void setBrowser(SWTQWebView webView) {
		checkWidget();
		qt_setBrowser(qtpointer, webView.qtpointer);
	}
	
	private native void qt_setBrowser(int nativeId, int browserId);

	private native int qt_createWebInspector(int nativeId, QtDelegate qtDelegate);
	
}
