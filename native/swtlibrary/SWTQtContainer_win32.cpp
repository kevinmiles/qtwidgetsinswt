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
#include "../qtwinmigrate/QWinWidget"
#include <windows.h>

static QApplication* app;

class WindowsNativePanel: public INativePanel {
private:
    QWinWidget *winWidget;
public:
    WindowsNativePanel(HWND);
    virtual QWidget* container();
    virtual void resize(int x, int y, int w, int h);
    virtual ~WindowsNativePanel();
};

INativePanel* create(int parentHandle) {
    return new WindowsNativePanel(reinterpret_cast<HWND> (parentHandle));
}

void startQt() {
    int argc = 0;
    app = new QApplication(argc, 0);
}

void tearDownQt() {
    delete app;
}

WindowsNativePanel::WindowsNativePanel(HWND parentWindow) {
    winWidget = new QWinWidget(parentWindow);
}

void WindowsNativePanel::resize(int x, int y, int w, int h) {
    HWND hWnd = winWidget->winId();
    UINT flags = SWP_NOZORDER | SWP_DRAWFRAME | SWP_NOACTIVATE;
    SetWindowPos(hWnd, (HWND) 0, x, 0, w, h, flags);
    winWidget->move(x, 0);
    winWidget->resize(w, h);
}


QWidget* WindowsNativePanel::container() {
    return winWidget;
}

WindowsNativePanel::~WindowsNativePanel() {

}
