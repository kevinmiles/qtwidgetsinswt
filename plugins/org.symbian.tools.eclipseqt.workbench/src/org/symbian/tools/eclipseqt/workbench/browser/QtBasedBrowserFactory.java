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

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.BrowserFactory;
import org.eclipse.ui.browser.IWebBrowser;

/**
 * Factory that creates browsers.
 * 
 * @author Eugene Ostroukhov
 */
public class QtBasedBrowserFactory extends BrowserFactory {

	@Override
	public IWebBrowser createBrowser(String id, String location,
			String parameters) {
		final BrowserEditorInput input = new BrowserEditorInput(id, location,
				location);
		IEditorPart editor;
		try {
			editor = PlatformUI
					.getWorkbench()
					.getActiveWorkbenchWindow()
					.getActivePage()
					.openEditor(input,
							"org.symbian.tools.eclipseqt.browsereditor", true);
			if (editor instanceof QtBasedBrowser) {
				QtBasedBrowser browser = (QtBasedBrowser) editor;
				browser.openUrl(location);
				return browser;
			}
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
