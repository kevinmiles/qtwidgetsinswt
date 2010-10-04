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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.symbian.tools.eclipseqt.qwebview.SWTQWebView;

/**
 * This is a web browser pane that may be embedded into editors, views, wizards,
 * etc.
 * 
 * @author Eugene Ostroukhov
 */
public abstract class QtBasedBrowserPane {

	/**
	 * The ID of the view as specified by the extension.
	 */
	private SWTQWebView browser;
	private Text location;
	private int previousProgress;
	private boolean trackingProgress = false;

	protected void browse() {
		browser.setUrl(location.getText());
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		Composite root = new Composite(parent, SWT.NONE);
		root.setLayoutData(new GridData(GridData.FILL_BOTH));
		Composite panel = new Composite(root, SWT.NONE);
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginHeight = 0;
		panel.setLayout(gridLayout);
		ToolBar bar = new ToolBar(panel, SWT.NONE);
		location = new Text(panel, SWT.BORDER);
		Button browse = new Button(panel, SWT.NONE);
		location.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		browse.setText("Go");
		browse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browse();
			}
		});
		browser = new SWTQWebView(root, SWT.NONE);

		ToolBarManager manager = new ToolBarManager(bar);
		manager.add(new BackAction(browser));
		manager.add(new ForwardAction(browser));
		manager.add(new Separator());
		manager.add(new StopRefreshAction(browser));
//		manager.add(new InspectAction(browser));
		manager.update(true);

		root.setLayout(new FormLayout());
		FormData locationData = new FormData();
		locationData.left = new FormAttachment(0, 0);
		locationData.top = new FormAttachment(0, 0);
		locationData.right = new FormAttachment(100, 0);
		panel.setLayoutData(locationData);

		FormData browserData = new FormData();
		browserData.left = new FormAttachment(0, 0);
		browserData.right = new FormAttachment(100, 0);
		browserData.bottom = new FormAttachment(100, 0);
		browserData.top = new FormAttachment(panel, 0);
		browser.setLayoutData(browserData);

		browser.addProgressListener(new ProgressListener() {
			public void changed(ProgressEvent event) {
				progressChange(event.current);
			}

			public void completed(ProgressEvent event) {
				downloadDone();
			}
		});
		browser.addLocationListener(new LocationListener() {
			public void changed(LocationEvent event) {
				location.setText(event.location);
			}

			public void changing(LocationEvent event) {
			}
		});
		browser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println(e.keyCode);
			}
		});
		browser.setUrl("http://symbian.org");
		location.setText(browser.getUrl());
	}

	protected void downloadDone() {
		if (trackingProgress) {
			getStatusLineManager().getProgressMonitor().done();
			trackingProgress = false;
		}
	}

	protected abstract IStatusLineManager getStatusLineManager();

	protected synchronized void progressChange(final int current) {
		IProgressMonitor progressMonitor = getStatusLineManager()
				.getProgressMonitor();
		if (trackingProgress) {
			if (current > previousProgress) {
				int worked = current - previousProgress;
				progressMonitor.worked(worked);
				previousProgress = current;
			}
		} else {
			trackingProgress = true;
			previousProgress = current;
			progressMonitor.beginTask("Downloading", 100);
			progressMonitor.worked(current);
		}
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		browser.setFocus();
	}

	public void openUrl(String url) {
		browser.setUrl(url);
	}

}
