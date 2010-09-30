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
 * Interface for platform-specific delegates.
 * 
 * @author Eugene Ostroukhov
 *
 */
public interface IPlatformDelegate {
	/**
	 * Makes sure Qt is loaded and properly initialized.
	 */
	void verifyRunning();

	/**
	 * Returns platform-specific integer that can be used by native library.
	 */
	int getNativeId(Composite control);

}
