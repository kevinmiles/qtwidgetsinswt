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
#include "SWTQtContainer.h";
#include <QtCore>
#include <QMacNativeWidget>
#include <QStackedlayout>

#ifdef Q_OS_MACX

#define RANDOM_OFFSET_I_CANT_UNDERSTAND_WHERE_IT_COMES_FROM 22 // I love self-explanatory code!

extern void qt_mac_set_native_menubar(bool);

static QApplication* app;

class MacNativePanel: public INativePanel {
	QMacNativeWidget* nativePane;
public:
	MacNativePanel(const int parentHandle);
	virtual QWidget* container();
	virtual void resize(int x, int y, int w, int h);
	virtual ~MacNativePanel();
};

void startQt() {
	qt_mac_set_native_menubar(false);
	QApplication::setAttribute(Qt::AA_MacPluginApplication);
	int argc = 0;
	app = new QApplication(argc, 0);
}

void tearDownQt() {
	delete app;
}

MacNativePanel::MacNativePanel(const int parentHandle) {
	NSView *obj = reinterpret_cast<NSView*> (parentHandle);
	nativePane = new QMacNativeWidget();
	nativePane->setPalette(QPalette(Qt::red));
	nativePane->setAutoFillBackground(true);
	NSView *native = reinterpret_cast<NSView*> (nativePane->winId());
	[obj addSubview:native];
}

QWidget* MacNativePanel::container() {
	return nativePane;
}

void MacNativePanel::resize(int x, int y, int w, int h) {
	NSView *native = reinterpret_cast<NSView*> (nativePane->winId());
	[native setFrame: NSMakeRect(0, 0, w, h)];
	nativePane->move(x, y - RANDOM_OFFSET_I_CANT_UNDERSTAND_WHERE_IT_COMES_FROM);
	nativePane->resize(w, h);
}

MacNativePanel::~MacNativePanel() {
}

INativePanel* create(int parentHandle) {
	return new MacNativePanel(parentHandle);
}
#endif //Q_OS_MACX