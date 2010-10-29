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

SWTQWidget::SWTQWidget(int handle) {
	panel = create(handle);
}

void SWTQWidget::resize(int x, int y, int w, int h) {
	panel->resize(x, y, w, h);
}

SWTQWidget::~SWTQWidget() {}

SWTQWebView::SWTQWebView(int handle, QWebViewDelegate *delegate) : SWTQWidget(handle) {
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
	QObject::connect(webView, SIGNAL(titleChanged(const QString&)), this, SLOT(titleChanged(const QString&)));
	// void titleChanged(const QString & title)
}

unsigned char* SWTQWebView::getImageData(int* w, int* h, int* bpp, int* arrayLength) {
    const QIcon& icon = webView->icon();
    const QPixmap& pixmap = icon.pixmap(16, 16);
    if (pixmap.isNull()) {
        return NULL;
    } else {
        const QImage& image = pixmap.toImage();
        *w = image.width();
        *h = image.height();
        *bpp = image.depth();
        *arrayLength = *w * *h * *bpp / 8;
        unsigned char* buffer = (unsigned char*) malloc(*arrayLength);
        memcpy(buffer, image.constBits(), *arrayLength);
        return buffer;
    }
}

void SWTQWebView::titleChanged(const QString & title) {
    const std::string stdString = title.toStdString();
    delegate->titleChanged(this, stdString.c_str());
}

void SWTQWebView::openUrl(const char* url) {
	webView->load(QUrl(url));
}

SWTQWebView::~SWTQWebView() {
	delete webView;
	delete layout;
	delete panel;
}

void SWTQWebView::loadStarted() {
	delegate->loadStarted(this);
}

void SWTQWebView::loadProgress(int progress) {
	delegate->loadProgress(this, progress);
}

void SWTQWebView::loadFinished(bool ok) {
	delegate->loadFinished(this, ok);
}

void SWTQWebView::urlChanged(const QUrl& url) {
	QString sz = url.toString();
	delegate->urlChanged(this, sz.toStdString().c_str());
}

void SWTQWebView::back() {
	webView->history()->back();
}

void SWTQWebView::forward() {
	webView->history()->forward();
}

bool SWTQWebView::canGoBack() {
	return webView->history()->canGoBack();
}

bool SWTQWebView::canGoForward() {
	return webView->history()->canGoForward();
}

void SWTQWebView::refresh() {
	webView->reload();
}

void SWTQWebView::stop() {
	webView->stop();
}

void SWTQWebView::setIconsDbPath(const char* sz) {
	webView->settings()->setIconDatabasePath(sz);
}

QWebView *SWTQWebView::qWebView() {
	return webView;
}

QWebViewDelegate::~QWebViewDelegate() {
}

INativePanel::~INativePanel() {};

SWTQWebInspector::SWTQWebInspector(int parentHandle) : SWTQWidget(parentHandle) {
	layout = new QStackedLayout;
	QWidget *nativePane = panel->container();
	nativePane->setLayout(layout);
	webInspector = new QWebInspector();
	nativePane->move(0, 0);
	layout->addWidget(webInspector);
	layout->setCurrentWidget(webInspector);
	nativePane->show();
	webInspector->show();
}

void SWTQWebInspector::setBrowser(SWTQWebView *webViewWrapper) {
	QWebView *webView = webViewWrapper->qWebView();
	webView->settings()->setAttribute(QWebSettings::DeveloperExtrasEnabled, true);
	webInspector->setPage(webView->page());
// See https://bugs.webkit.org/show_bug.cgi?id=47203
//	QWebPage *page = webView->page();
//	page->inspect(page->mainFrame()->documentElement()); 
}

SWTQWebInspector::~SWTQWebInspector() {}