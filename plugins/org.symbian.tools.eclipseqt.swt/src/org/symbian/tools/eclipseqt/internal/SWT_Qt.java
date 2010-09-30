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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
	private static IPlatformDelegate delegate;

	private static synchronized IPlatformDelegate getDelegate() {
		if (delegate == null) {
			InputStream stream = SWT_Qt.class.getClassLoader()
					.getResourceAsStream("/org/symbian/tools/eclipseqt/internal/platform.properties");
			if (stream != null) {
				try {
					Properties props = new Properties();
					props.load(stream);
					delegate = (IPlatformDelegate) Class.forName(props.getProperty("delegate.class")).newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (delegate == null) {
				delegate = new IPlatformDelegate() {
					public void verifyRunning() {
						throw new IllegalArgumentException(
								"Missing platform-specific fragment. Either your installation is broken or your SWT version is not supported");
					}

					public int getNativeId(Composite control) {
						throw new IllegalArgumentException(
								"Missing platform-specific fragment. Either your installation is broken or your SWT version is not supported");
					}
				};
			}
		}
		return delegate;
	}

	public static synchronized void verifyRunning() {
		getDelegate().verifyRunning();
	}

	/**
	 * Returns integer that can be used by native library to build native
	 * container and Qt objects. This code relies on reflection as it needs to
	 * peek into SWT implementation details.
	 */
	public static int getNativeId(Composite control) {
		return getDelegate().getNativeId(control);
	}

}
