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

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
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
			InputStream stream = SWT_Qt.class
					.getClassLoader()
					.getResourceAsStream(
							"/org/symbian/tools/eclipseqt/internal/platform.properties");
			if (stream != null) {
				try {
					Properties props = new Properties();
					props.load(stream);
					delegate = (IPlatformDelegate) Class.forName(
							props.getProperty("delegate.class")).newInstance();
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

	public static ImageData qtImageToSwt(final int w, final int h,
			final int bpp, final byte[] data) {
		if (data != null) {
			final ImageData imageData = new ImageData(w, h, bpp,
					new PaletteData(0xFF00, 0xFF0000, 0xFF000000), 8, data);
			if (bpp == 32) {
				byte[] alpha = new byte[w * h];
				for (int i = 0; i < alpha.length; i++) {
					alpha[i] = data[((i + 1) * 4) - 1];
				}
				imageData.setAlphas(0, 0, w * h, alpha, 0);
			}
			return imageData;
		} else {
			return null;
		}
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
