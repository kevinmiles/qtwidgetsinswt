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

import java.net.URL;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.part.EditorPart;

/**
 * This is QWebView opened as editor
 * 
 * @author Eugene Ostroukhov
 */
public class QtBasedBrowser extends EditorPart implements IWebBrowser,
		IEditorPart {
	private QtBasedBrowserPane browserPane;
	private String initialUrl;

	public boolean close() {
		return getEditorSite().getPage().closeEditor(this, false);
	}

	@Override
	public void createPartControl(Composite parent) {
		browserPane = new QtBasedBrowserPane() {
			@Override
			protected IStatusLineManager getStatusLineManager() {
				return getEditorSite().getActionBars().getStatusLineManager();
			}

			protected void setImage(org.eclipse.swt.graphics.Image image) {
				setTitleImage(image);
				setTitleToolTip(getBrowser().getUrl());
				String title = getBrowser().getTitle();
				setPartName(title != null ? title : getBrowser().getUrl());
			}
		};
		browserPane.createPartControl(parent);
		if (initialUrl != null && initialUrl != null) {
			browserPane.openUrl(initialUrl);
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	public String getId() {
		IEditorInput input = getEditorInput();
		return input instanceof BrowserEditorInput ? ((BrowserEditorInput) input)
				.getBrowserId() : getEditorSite().getId();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.setInput(input);
		super.setSite(site);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public void openUrl(String location) {
		if (browserPane != null) {
			browserPane.openUrl(location);
		} else {
			initialUrl = location;
		}
	}

	public void openURL(URL url) throws PartInitException {
		if (url != null) {
			openUrl(url.toString());
		}
	}

	@Override
	public void setFocus() {
		browserPane.setFocus();
	}
}
