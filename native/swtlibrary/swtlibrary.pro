HEADERS += SWTQtContainer.h natives.h
SOURCES += SWTQtContainer.cpp natives.cpp
CONFIG += qt plugin
QT += core gui webkit
TEMPLATE = lib dll
OBJECTS_DIR += Build

macx {
    QMAKE_EXTENSION_SHLIB = jnilib
    INCLUDEPATH += /System/Library/Frameworks/JavaVM.framework/Headers
    OBJECTIVE_SOURCES += SWTQtContainer_cocoa.mm
    LIBS += -framework AppKit -framework Cocoa
    DESTDIR = ../../plugins/org.symbian.tools.eclipseqt.swt.cocoa
}
win32 {
    DESTDIR = ../../plugins/org.symbian.tools.eclipseqt.swt.win32
    JNI_INCLUDE = "$$(JAVA_HOME)/include"
    !exists( $$JNI_INCLUDE ) {
	error("JAVA_HOME should point to JDK installation")
    }
    INCLUDEPATH += $$JNI_INCLUDE $$JNI_INCLUDE/win32"
    SOURCES += SWTQtContainer_win32.cpp
    include("../qtwinmigrate/qtwinmigrate.pri")
    QMAKE_LFLAGS += -Wl,--kill-at -D_JNI_IMPLEMENTATION_
}
