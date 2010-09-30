HEADERS = SWTQtContainer.h natives.h
SOURCES = SWTQtContainer.cpp natives.cpp
CONFIG += qt plugin
QT += core gui webkit
DESTDIR = ../../plugins/org.symbian.tools.eclipse.webkit.swt 
TEMPLATE = lib dll
OBJECTS_DIR = Build

macx {
    QMAKE_EXTENSION_SHLIB = jnilib
    INCLUDEPATH += /System/Library/Frameworks/JavaVM.framework/Headers
    OBJECTIVE_SOURCES += SWTQtContainer_cocoa.mm
    LIBS += -framework AppKit -framework Cocoa
}
win32 {
    JNI_INCLUDE = "$$(JAVA_HOME)/include"
    !exists( $$JNI_INCLUDE ) {
	error("JAVA_HOME should point to JDK installation")
    }
    INCLUDEPATH += $$JNI_INCLUDE $$JNI_INCLUDE/win32"
    SOURCES += SWTQtContainer_win32.cpp
    HEADERS += ../qtwinmigrate/QMfcApp ../qtwinmigrate/qmfcapp.h ../qtwinmigrate/QWinWidget ../qtwinmigrate/qwinwidget.h 
    SOURCES += ../qtwinmigrate/qmfcapp.cpp ../qtwinmigrate/qwinwidget.cpp 
    LIBS += -luser32
    QMAKE_LFLAGS += -Wl,--kill-at -D_JNI_IMPLEMENTATION_
}