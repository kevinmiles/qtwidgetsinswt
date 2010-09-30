/****************************************************************************
** Meta object code from reading C++ file 'SWTQtContainer.h'
**
** Created: Thu Sep 30 10:49:12 2010
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
static const uint qt_meta_data_SWTQtContainer[] = {

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
      16,   15,   15,   15, 0x0a,
      30,   15,   15,   15, 0x0a,
      48,   15,   15,   15, 0x0a,
      67,   15,   15,   15, 0x0a,

       0        // eod
};

static const char qt_meta_stringdata_SWTQtContainer[] = {
    "SWTQtContainer\0\0loadStarted()\0"
    "loadProgress(int)\0loadFinished(bool)\0"
    "urlChanged(QUrl)\0"
};

const QMetaObject SWTQtContainer::staticMetaObject = {
    { &QObject::staticMetaObject, qt_meta_stringdata_SWTQtContainer,
      qt_meta_data_SWTQtContainer, 0 }
};

#ifdef Q_NO_DATA_RELOCATION
const QMetaObject &SWTQtContainer::getStaticMetaObject() { return staticMetaObject; }
#endif //Q_NO_DATA_RELOCATION

const QMetaObject *SWTQtContainer::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->metaObject : &staticMetaObject;
}

void *SWTQtContainer::qt_metacast(const char *_clname)
{
    if (!_clname) return 0;
    if (!strcmp(_clname, qt_meta_stringdata_SWTQtContainer))
        return static_cast<void*>(const_cast< SWTQtContainer*>(this));
    return QObject::qt_metacast(_clname);
}

int SWTQtContainer::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
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
QT_END_MOC_NAMESPACE
