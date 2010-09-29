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
package org.symbian.tools.eclipseqt.internal;

import org.eclipse.swt.widgets.Composite;

/**
 * Collection of Qt integration singletons and platform-specific methods.
 * 
 * Note: currently we are trying to avoid fragments so I will rely on reflection
 * and platform filtering.
 * 
 * @author Eugene Ostroukhov
 */
public class SWT_Qt {
	private static final String COCOA_NSVIEW = "org.eclipse.swt.internal.cocoa.NSView";

	private static Boolean isMac;

	public static synchronized void verifyRunning() {
		if (isMacCocoa32()) {
			System.loadLibrary("swtlibrary");
		} else {
			throw new IllegalStateException("Unsupported operating system");
		}
	}

	private static boolean isMacCocoa32() {
		// Note - we are really testing if there's proper SWT version. We can't
		// simply rely
		// it's there by testing os.name and other system props
		if (isMac == null) {
			try {
				SWT_Qt.class.getClassLoader().loadClass(COCOA_NSVIEW);
				isMac = Boolean.TRUE;
			} catch (ClassNotFoundException e) {
				// Quite expected - with the Mac market share less then 10%
				isMac = Boolean.FALSE;
			}
		}
		return isMac.booleanValue();
	}

	/**
	 * Returns integer that can be used by native library to build native container
	 * and Qt objects. This code relies on reflection as it needs to peek into SWT
	 * implementation details.
	 */
	public static int getNativeId(Composite control) {
		try {
			if (isMacCocoa32()) {
				Object nsView = control.getClass().getField("view")
						.get(control);
				return nsView.getClass().getField("id").getInt(nsView);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		throw new IllegalStateException("Unsuppoted SWT version");
	}

}
