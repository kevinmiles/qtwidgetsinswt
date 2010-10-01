/****************************************************************************
** Meta object code from reading C++ file 'SWTQtContainer.h'
**
** Created: Thu Sep 30 16:59:29 2010
**      by: The Qt Meta Object Compiler version 62 (Qt 4.7.0)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "SWTQtContainer.h"
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'SWTQtContainer.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 62
#error "This file was generated using the moc from 4.7.0. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
static const uint qt_meta_data_SWTQWidget[] = {

 // content:
       5,       // revision
       0,       // classname
       0,    0, // classinfo
       0,    0, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

       0        // eod
};

static const char qt_meta_stringdata_SWTQWidget[] = {
    "SWTQWidget\0"
};

const QMetaObject SWTQWidget::staticMetaObject = {
    { &QObject::staticMetaObject, qt_meta_stringdata_SWTQWidget,
      qt_meta_data_SWTQWidget, 0 }
};

#ifdef Q_NO_DATA_RELOCATION
const QMetaObject &SWTQWidget::getStaticMetaObject() { return staticMetaObject; }
#endif //Q_NO_DATA_RELOCATION

const QMetaObject *SWTQWidget::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->metaObject : &staticMetaObject;
}

void *SWTQWidget::qt_metacast(const char *_clname)
{
    if (!_clname) return 0;
    if (!strcmp(_clname, qt_meta_stringdata_SWTQWidget))
        return static_cast<void*>(const_cast< SWTQWidget*>(this));
    return QObject::qt_metacast(_clname);
}

int SWTQWidget::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    return _id;
}
static const uint qt_meta_data_SWTQWebView[] = {

 // content:
       5,       // revision
       0,       // classname
       0,    0, // classinfo
       4,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

 // slots: signature, parameters, type, tag, flags
      13,   12,   12,   12, 0x0a,
      27,   12,   12,   12, 0x0a,
      45,   12,   12,   12, 0x0a,
      64,   12,   12,   12, 0x0a,

       0        // eod
};

static const char qt_meta_stringdata_SWTQWebView[] = {
    "SWTQWebView\0\0loadStarted()\0loadProgress(int)\0"
    "loadFinished(bool)\0urlChanged(QUrl)\0"
};

const QMetaObject SWTQWebView::staticMetaObject = {
    { &SWTQWidget::staticMetaObject, qt_meta_stringdata_SWTQWebView,
      qt_meta_data_SWTQWebView, 0 }
};

#ifdef Q_NO_DATA_RELOCATION
const QMetaObject &SWTQWebView::getStaticMetaObject() { return staticMetaObject; }
#endif //Q_NO_DATA_RELOCATION

const QMetaObject *SWTQWebView::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->metaObject : &staticMetaObject;
}

void *SWTQWebView::qt_metacast(const char *_clname)
{
    if (!_clname) return 0;
    if (!strcmp(_clname, qt_meta_stringdata_SWTQWebView))
        return static_cast<void*>(const_cast< SWTQWebView*>(this));
    return SWTQWidget::qt_metacast(_clname);
}

int SWTQWebView::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = SWTQWidget::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        switch (_id) {
        case 0: loadStarted(); break;
        case 1: loadProgress((*reinterpret_cast< int(*)>(_a[1]))); break;
        case 2: loadFinished((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 3: urlChanged((*reinterpret_cast< const QUrl(*)>(_a[1]))); break;
        default: ;
        }
        _id -= 4;
    }
    return _id;
}
static const uint qt_meta_data_SWTQWebInspector[] = {

 // content:
       5,       // revision
       0,       // classname
       0,    0, // classinfo
       0,    0, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

       0        // eod
};

static const char qt_meta_stringdata_SWTQWebInspector[] = {
    "SWTQWebInspector\0"
};

const QMetaObject SWTQWebInspector::staticMetaObject = {
    { &SWTQWidget::staticMetaObject, qt_meta_stringdata_SWTQWebInspector,
      qt_meta_data_SWTQWebInspector, 0 }
};

#ifdef Q_NO_DATA_RELOCATION
const QMetaObject &SWTQWebInspector::getStaticMetaObject() { return staticMetaObject; }
#endif //Q_NO_DATA_RELOCATION

const QMetaObject *SWTQWebInspector::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->metaObject : &staticMetaObject;
}

void *SWTQWebInspector::qt_metacast(const char *_clname)
{
    if (!_clname) return 0;
    if (!strcmp(_clname, qt_meta_stringdata_SWTQWebInspector))
        return static_cast<void*>(const_cast< SWTQWebInspector*>(this));
    return SWTQWidget::qt_metacast(_clname);
}

int SWTQWebInspector::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = SWTQWidget::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    return _id;
}
QT_END_MOC_NAMESPACE
