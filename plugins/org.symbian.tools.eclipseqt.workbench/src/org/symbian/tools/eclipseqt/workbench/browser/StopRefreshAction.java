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
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.symbian.tools.eclipseqt.qwebview.SWTQWebView;

/**
 * This action is "stop" when browser is loading the resource and "refresh"
 * when the page is loaded.
 * 
 * @author Eugene Ostroukhov
 */
public class StopRefreshAction extends Action {
	private final SWTQWebView webView;
	boolean loading = true;

	public StopRefreshAction(SWTQWebView webView) {
		this.webView = webView;
		webView.addProgressListener(new ProgressListener() {

			public void completed(ProgressEvent event) {
				refresh(false);
			}

			public void changed(ProgressEvent event) {
				refresh(true);
			}
		});
		refresh(false);
	}

	protected void refresh(boolean isLoading) {
		if (isLoading && !loading) {
			setText("Stop");
			setDescription("Stop");
			setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
					.getImageDescriptor(ISharedImages.IMG_ELCL_STOP));
		} else if (!isLoading && loading) {
			setText("Refresh");
			setDescription("Refresh");
			setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
					.getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));
		}
		loading = isLoading;
	}

	@Override
	public void run() {
		if (loading) {
			webView.stop();
		} else {
			webView.refresh();
		}
	}
}
