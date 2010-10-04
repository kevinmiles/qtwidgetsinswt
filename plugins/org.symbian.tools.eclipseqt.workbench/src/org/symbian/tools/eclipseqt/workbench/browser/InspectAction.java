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

import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.symbian.tools.eclipseqt.qwebview.SWTQWebView;
import org.symbian.tools.eclipseqt.workbench.internal.Activator;

/**
 * This action opens inspector and attaches it to the browser
 * 
 * @author Eugene Ostroukhov
 */
public class InspectAction extends Action {
	private final SWTQWebView webView;

	public InspectAction(SWTQWebView view) {
		this.webView = view;
		setText("Inspect...");
		setDescription("Inspect");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_DEF_VIEW));
	}
	
	@Override
	public void run() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		try {
			IViewPart view = window.getActivePage().showView(WebInspectorView.VIEW_ID);
			if (view instanceof WebInspectorView) {
				((WebInspectorView) view).setBrowser(webView);
			}
		} catch (PartInitException e) {
			Activator.log(e);
		}
	}
}
