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

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Rectangle;
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
public class SWTQWebView extends Composite {
	static {
		SWT_Qt.verifyRunning();
	}

	private final Collection<LocationListener> locationListeners = new LinkedList<LocationListener>();
	private final Collection<ProgressListener> progressListeners = new LinkedList<ProgressListener>();
	private final int qtpointer;
	private String url;

	public SWTQWebView(Composite parent, int style) {
		super(parent, style | SWT.EMBEDDED);
		super.setBackground(getDisplay().getSystemColor(SWT.COLOR_GREEN));
		qtpointer = qt_createChild(SWT_Qt.getNativeId(this), new Delegate(this));
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				qt_dispose(qtpointer);
			}
		});
		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				resize(e);
			}
		});
	}

	public void addLocationListener(LocationListener locationListener) {
		checkWidget();
		locationListeners.add(locationListener);
	}

	public synchronized void addProgressListener(ProgressListener listener) {
		checkWidget();
		progressListeners.add(listener);
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

	private native int qt_createChild(int parentHandle, Delegate delegate);

	private native void qt_dispose(int qtpointer);

	private native void qt_forward(int qtpointer);

	private native void qt_refresh(int qtpointer);

	private native void qt_resize(int qtpointer, int x, int y, int width,
			int height);

	private final native void qt_setUrl(int qtpointer, String url);

	private native void qt_stop(int qtpointer);

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

	protected void resize(ControlEvent e) {
		checkWidget();
		final Rectangle clientArea = getBounds();// getClientArea();
		qt_resize(qtpointer, clientArea.x, clientArea.y, clientArea.width,
				clientArea.height);
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

}
