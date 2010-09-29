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

#include <QWidget>
#include <QPushButton>
#include <QApplication>
#include <QWebView>
#include <QWebHistory>
#include <QStackedlayout>

class INativePanel {
public:
	virtual QWidget* container() = 0;
	virtual void resize(int x, int y, int w, int h) = 0;
	virtual ~INativePanel();
};

INativePanel* create(int parentHandle);
void startQt();
void tearDownQt();
class SWTQtContainer;

class QWebViewDelegate {
public:
	virtual ~QWebViewDelegate();
	virtual void loadStarted(SWTQtContainer*) = 0;
	virtual void loadProgress(SWTQtContainer*, int) = 0;
	virtual void loadFinished(SWTQtContainer*, bool) = 0;
	virtual void urlChanged(SWTQtContainer*, const char*) = 0;
};

class SWTQtContainer: public QObject {
	Q_OBJECT;
	INativePanel *panel;
	QWebView *webView;
	QStackedLayout *layout;
	QWebViewDelegate *delegate;
public:
	SWTQtContainer(int parentHandle, QWebViewDelegate*);
	void resize(int x, int y, int w, int h);
	void openUrl(const char*);
	void back();
	void forward();
	bool canGoBack();
	bool canGoForward();
	void stop();
	void refresh();
	virtual ~SWTQtContainer();
public slots:
	void loadStarted();
	void loadProgress(int);
	void loadFinished(bool);
	void urlChanged(const QUrl&);
};

#endif //SWT_QT_CONTAINER