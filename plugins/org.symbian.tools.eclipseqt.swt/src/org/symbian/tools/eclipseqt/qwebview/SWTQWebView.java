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

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;
import org.symbian.tools.eclipseqt.internal.SWT_Qt;

/**
 * This class provides SWT interfaces for embedding Qt <a
 * href="http://doc.trolltech.com/main-snapshot/qwebview.html">QWebView</a>
 * class. It's API is intended to resemble SWT Browser class as much as
 * possible.
 * 
 * @author Eugene Ostroukhov
 */
public class SWTQWebView extends QtControlWrapper {
	private final Collection<LocationListener> locationListeners = new LinkedList<LocationListener>();
	private final Collection<ProgressListener> progressListeners = new LinkedList<ProgressListener>();
	private final Collection<TitleListener> titleListeners = new LinkedList<TitleListener>();
	private String url;
	private String title = "";

	public SWTQWebView(Composite parent, int style) {
		super(parent, style);
		qtpointer = qt_createWebView(SWT_Qt.getNativeId(this),
				new BrowserDelegate(this));
	}

	public void addLocationListener(LocationListener locationListener) {
		checkWidget();
		locationListeners.add(locationListener);
	}

	public synchronized void addProgressListener(ProgressListener listener) {
		checkWidget();
		progressListeners.add(listener);
	}

	public synchronized void addTitleListener(TitleListener listener) {
		checkWidget();
		titleListeners.add(listener);
	}

	public boolean back() {
		checkWidget();
		qt_back(qtpointer);
		return true;
	}

	public boolean forward() {
		checkWidget();
		qt_forward(qtpointer);
		return true;
	}

	public String getUrl() {
		checkWidget();
		return url;
	}

	/**
	 * This method will only be called by the delegate when event comes from
	 * native part
	 */
	void innerSetUrl(String url) {
		this.url = url;
	}

	void invokeLater(Runnable runnable) {
		// I don't feel comfortable with Qt enough to know for sure if notifying
		// widgets could be done directly from Qt call. I am not even sure if
		// we would necessarily be on the UI thread.
		getDisplay().asyncExec(runnable);
	}

	public boolean isBackEnabled() {
		checkWidget();
		return qt_canGoBack(qtpointer);
	}

	public boolean isForwardEnabled() {
		checkWidget();
		return qt_canGoForward(qtpointer);
	}

	protected void notifyLoadFinished(int browserId, boolean ok) {
		final ProgressEvent event = new ProgressEvent(this);
		event.current = 100;
		event.total = 100;

		for (ProgressListener listener : progressListeners) {
			listener.completed(event);
		}
	}

	protected void notifyLoadProgress(int browserId, int progress) {
		final ProgressEvent event = new ProgressEvent(this);
		event.current = progress;
		event.total = 100;

		for (ProgressListener listener : progressListeners) {
			listener.changed(event);
		}
	}

	protected void notifyLoadStarted(int browserId) {
		notifyLoadProgress(browserId, 0);
	}

	protected void notifyUrlChanged(int browserId, String url) {
		LocationEvent event = new LocationEvent(this);
		event.location = url;
		for (LocationListener listener : locationListeners) {
			listener.changed(event);
		}
	}

	private native void qt_back(int qtpointer);

	private native boolean qt_canGoBack(int qtpointer);

	private native boolean qt_canGoForward(int qtpointer);

	private native int qt_createWebView(int parentHandle,
			BrowserDelegate delegate);

	private native void qt_forward(int qtpointer);

	private native void qt_refresh(int qtpointer);

	private native void qt_setIconsDbPath(int qtpointer, String path);

	private native void qt_setUrl(int qtpointer, String url);

	private native void qt_stop(int qtpointer);

	private native byte[] qt_getIcon(int qtpointer, int[] whbp);

	public void refresh() {
		checkWidget();
		qt_refresh(qtpointer);
	}

	public void removeLocationListener(LocationListener locationListener) {
		checkWidget();
		locationListeners.remove(locationListener);
	}

	public synchronized void removeProgressListener(ProgressListener listener) {
		checkWidget();
		progressListeners.remove(listener);
	}

	/**
	 * Note that the browser will not download the icons until a valid path is
	 * supplied. This path should point to a writable directory (i.e. somewhere
	 * in the plug-in state location).
	 */
	public void setIconsDatabasePath(String path) {
		checkWidget();
		final File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		qt_setIconsDbPath(qtpointer, path);
	}

	public void setUrl(String url) {
		checkWidget();
		this.url = url;
		qt_setUrl(qtpointer, url);
	}

	public void stop() {
		checkWidget();
		qt_stop(qtpointer);
	}

	public synchronized void removeTitleListener(TitleListener listener) {
		checkWidget();
		titleListeners.remove(listener);
	}

	public String getTitle() {
		return title;
	}
	
	public ImageData getIcon() {
		final int[] whbp = new int[3];
		final byte[] data = qt_getIcon(qtpointer, whbp);
		if (data != null && data.length > 0) {
			return SWT_Qt.qtImageToSwt(whbp[0], whbp[1], whbp[2], data);
		} else {
			return null;
		}
	}

	public void innerSetTitle(String title) {
		this.title = title;
	}

	public void notifyTitleChanged(int browserId, String title) {
		final TitleEvent event = new TitleEvent(this);
		event.title = title;
		
		for (TitleListener listener : titleListeners) {
			listener.changed(event);
		}
	}

}
