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
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.symbian.tools.eclipseqt.internal.SWT_Qt;

/**
 * This is a base class for Qt object wrappers.
 * 
 * @author Eugene Ostroukhov
 */
public abstract class QtControlWrapper extends Composite {
	static {
		SWT_Qt.verifyRunning();
	}
	protected int qtpointer;
	
	public QtControlWrapper(Composite parent, int style) {
		super(parent, style | SWT.EMBEDDED);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				qt_dispose(qtpointer);
			}
		});
		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				resize(e);
			}
		});
	}

	protected void resize(ControlEvent e) {
		checkWidget();
		final Rectangle clientArea = getBounds();// getClientArea();
		qt_resize(qtpointer, clientArea.x, clientArea.y, clientArea.width,
				clientArea.height);
	}

	private native void qt_dispose(int qtpointer);
	private native void qt_resize(int qtpointer, int x, int y, int width,
			int height);
}
