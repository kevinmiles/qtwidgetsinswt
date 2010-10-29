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
#ifndef SWT_QT_CONTAINER
#define SWT_QT_CONTAINER

#include <QApplication>
#include <QPushButton>
#include <QStackedlayout>
#include <QWebInspector>
#include <QWebFrame>
#include <QWebElement>
#include <QWebHistory>
#include <QWebView>
#include <QWidget>

class INativePanel {
public:
	virtual QWidget* container() = 0;
	virtual void resize(int x, int y, int w, int h) = 0;
	virtual ~INativePanel();
};

INativePanel* create(int parentHandle);
void startQt();
void tearDownQt();

class SWTQWidget: public QObject {
	Q_OBJECT;
protected:
	INativePanel *panel;
public:
	SWTQWidget(int parentHandle);
	void resize(int x, int y, int w, int h);
	virtual ~SWTQWidget();
};

class SWTQWebView;

class QWebViewDelegate {
public:
	virtual ~QWebViewDelegate();
	virtual void loadStarted(SWTQWebView*) = 0;
	virtual void loadProgress(SWTQWebView*, int) = 0;
	virtual void loadFinished(SWTQWebView*, bool) = 0;
	virtual void urlChanged(SWTQWebView*, const char*) = 0;
    virtual void titleChanged(SWTQWidget*, const char*) = 0;
};

class SWTQWebView: public SWTQWidget {
	Q_OBJECT;
	QWebView *webView;
	QStackedLayout *layout;
	QWebViewDelegate *delegate;
public:
	SWTQWebView(int parentHandle, QWebViewDelegate*);
	void openUrl(const char*);
	void back();
	void forward();
	bool canGoBack();
	bool canGoForward();
	void stop();
	void refresh();
    unsigned char* getImageData(int* w, int* h, int* bpp, int* arrayLength);
	QWebView* qWebView();
	void setIconsDbPath(const char*);
	virtual ~SWTQWebView();
public slots:
	void loadStarted();
	void loadProgress(int);
	void loadFinished(bool);
	void urlChanged(const QUrl&);
	void titleChanged(const QString & title);
};

class SWTQWebInspector: public SWTQWidget {
	Q_OBJECT;
	QWebInspector *webInspector;
	QStackedLayout *layout;
public:
	SWTQWebInspector(int parentHandle);
	void setBrowser(SWTQWebView *webView);
	virtual ~SWTQWebInspector();
};
#endif //SWT_QT_CONTAINER