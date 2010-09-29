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
import org.eclipse.ui.browser.AbstractWorkbenchBrowserSupport;
import org.eclipse.ui.browser.IWebBrowser;

public class QtBasedBrowserSupport extends AbstractWorkbenchBrowserSupport {

	public IWebBrowser createBrowser(int style, String browserId, String name,
			String tooltip) throws PartInitException {
		final BrowserEditorInput input = new BrowserEditorInput(browserId, name, tooltip);
		final IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, "org.symbian.tools.eclipseqt.browsereditor", true);
		if (editor instanceof IWebBrowser) {
			return (IWebBrowser) editor;
		}
		return null;
	}

	public IWebBrowser createBrowser(String browserId) throws PartInitException {
		return createBrowser(LOCATION_BAR | AS_EDITOR | NAVIGATION_BAR | STATUS, browserId, "Qt WebKit", browserId);
	}

	@Override
	public boolean isInternalWebBrowserAvailable() {
		return true;
	}
}
