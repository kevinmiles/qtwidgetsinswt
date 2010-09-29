HEADERS = SWTQtContainer.h natives.h
SOURCES = SWTQtContainer.cpp natives.cpp
CONFIG += qt plugin
QT += core gui webkit
DESTDIR = ../../plugins/org.symbian.tools.eclipse.webkit.swt 
TEMPLATE = lib dll
OBJECTS_DIR = Build

mac {
    QMAKE_EXTENSION_SHLIB = jnilib
    INCLUDEPATH += /System/Library/Frameworks/JavaVM.framework/Headers
    OBJECTIVE_SOURCES += SWTQtContainer_cocoa.mm
    LIBS += -framework AppKit -framework Cocoa
}
