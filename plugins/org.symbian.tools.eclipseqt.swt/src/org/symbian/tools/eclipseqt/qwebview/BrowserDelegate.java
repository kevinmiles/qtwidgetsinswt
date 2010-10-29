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
package org.symbian.tools.eclipseqt.qwebview;

/**
 * This object methods will be triggered on the native side to send events to
 * Java side.
 * 
 * @author Eugene Ostroukhov
 */
final class BrowserDelegate {
	private final SWTQWebView webView;

	BrowserDelegate(SWTQWebView swtqWebView) {
		webView = swtqWebView;
	}

	public void browserLoadFinished(final int browserId, final boolean ok) {
		webView.invokeLater(new Runnable() {
			public void run() {
				BrowserDelegate.this.webView.notifyLoadFinished(browserId, ok);
			}
		});
	}

	public void browserLoadProgress(final int browserId, final int progress) {
		webView.invokeLater(new Runnable() {
			public void run() {
				BrowserDelegate.this.webView.notifyLoadProgress(browserId,
						progress);
			}
		});
	}

	public void browserLoadStarted(final int browserId) {
		webView.invokeLater(new Runnable() {
			public void run() {
				BrowserDelegate.this.webView.notifyLoadStarted(browserId);
			}
		});
	}

	public void browserUrlChanged(final int browserId, final String browserUrl) {
		final String url;
		if (browserUrl != null && browserUrl.length() == 0) {
			url = null;
		} else {
			url = browserUrl;
		}
		webView.innerSetUrl(url);
		webView.invokeLater(new Runnable() {
			public void run() {
				BrowserDelegate.this.webView.notifyUrlChanged(browserId, url);
			}
		});
	}

	public void browserTitleChanged(final int browserId,
			final String browserTitle) {
		final String title;
		if (browserTitle != null && browserTitle.length() == 0) {
			title = null;
		} else {
			title = browserTitle;
		}
		webView.innerSetTitle(title);
		webView.invokeLater(new Runnable() {
			public void run() {
				BrowserDelegate.this.webView.notifyTitleChanged(browserId,
						title);
			}
		});
	}
}