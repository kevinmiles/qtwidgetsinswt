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
#include "SWTQtContainer.h"
#include <stdio.h>

SWTQtContainer::SWTQtContainer(int handle, QWebViewDelegate *delegate) {
	panel = create(handle);
	layout = new QStackedLayout;
	QWidget *nativePane = panel->container();
	nativePane->setLayout(layout);
	webView = new QWebView;
	nativePane->move(0, 0);
	layout->addWidget(webView);
	layout->setCurrentWidget(webView);
	nativePane->show();
	webView->show();
	
	this->delegate = delegate;
	QObject::connect(webView, SIGNAL(loadStarted()), this, SLOT(loadStarted()));
	QObject::connect(webView, SIGNAL(loadProgress(int)), this, SLOT(loadProgress(int)));
	QObject::connect(webView, SIGNAL(loadFinished(bool)), this, SLOT(loadFinished(bool)));
	QObject::connect(webView, SIGNAL(urlChanged(const QUrl&)), this, SLOT(urlChanged(const QUrl&)));
}

void SWTQtContainer::resize(int x, int y, int w, int h) {
	panel->resize(x, y, w, h);
}

void SWTQtContainer::openUrl(const char* url) {
	webView->load(QUrl(url));
}

SWTQtContainer::~SWTQtContainer() {
	delete webView;
	delete layout;
	delete panel;
}

void SWTQtContainer::loadStarted() {
	delegate->loadStarted(this);
}

void SWTQtContainer::loadProgress(int progress) {
	delegate->loadProgress(this, progress);
}

void SWTQtContainer::loadFinished(bool ok) {
	delegate->loadFinished(this, ok);
}

void SWTQtContainer::urlChanged(const QUrl& url) {
	QString sz = url.toString();
	delegate->urlChanged(this, sz.toStdString().c_str());
}

void SWTQtContainer::back() {
	webView->history()->back();
}

void SWTQtContainer::forward() {
	webView->history()->forward();
}

bool SWTQtContainer::canGoBack() {
	return webView->history()->canGoBack();
}

bool SWTQtContainer::canGoForward() {
	return webView->history()->canGoForward();
}

void SWTQtContainer::refresh() {
	webView->reload();
}

void SWTQtContainer::stop() {
	webView->stop();
}

QWebViewDelegate::~QWebViewDelegate() {
}

INativePanel::~INativePanel() {};