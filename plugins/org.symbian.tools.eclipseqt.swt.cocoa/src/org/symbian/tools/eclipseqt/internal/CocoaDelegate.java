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
public final class CocoaDelegate implements IPlatformDelegate {
	private static boolean librariesLoaded = false;

	public void verifyRunning() {
		if (!librariesLoaded) {
			System.loadLibrary("swtlibrary");
			librariesLoaded = true;
		}
	}

	/**
	 * Returns integer that can be used by native library to build native
	 * container and Qt objects. This code relies on reflection as it needs to
	 * peek into SWT implementation details.
	 */
	@SuppressWarnings("restriction")
	public int getNativeId(Composite control) {
		return control.view.id;
	}
}
