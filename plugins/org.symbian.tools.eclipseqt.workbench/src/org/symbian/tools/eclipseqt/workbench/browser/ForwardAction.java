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
import org.eclipse.swt.browser.LocationAdapter;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.symbian.tools.eclipseqt.qwebview.SWTQWebView;

/**
 * This is "forward" action
 * 
 * @author Eugene Ostroukhov
 */
public class ForwardAction extends Action {
	private final SWTQWebView view;

	public ForwardAction(SWTQWebView view) {
		this.view = view;
		view.addLocationListener(new LocationAdapter() {
			@Override
			public void changed(LocationEvent event) {
				refreshEnabled();
			}
		});
		setText("Forward...");
		setDescription("Forward");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
		refreshEnabled();
	}

	protected void refreshEnabled() {
		setEnabled(view.isForwardEnabled());
	}

	@Override
	public void run() {
		view.forward();
	}
}
