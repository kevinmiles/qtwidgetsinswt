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
#include <jni.h>
#include "natives.h"
#include "SWTQtContainer.h"
#include <iostream>

static JavaVM* javaVM;

class JNIWebViewDelegate: public QWebViewDelegate {
	const jobject delegateRef;
public:
	JNIWebViewDelegate(jobject);
	virtual ~JNIWebViewDelegate();
	virtual void loadStarted(SWTQtContainer*);
	virtual void loadProgress(SWTQtContainer*, int);
	virtual void loadFinished(SWTQtContainer*, bool);
	virtual void urlChanged(SWTQtContainer*, const char*);
};

JNIWebViewDelegate::JNIWebViewDelegate(jobject delegate): delegateRef(delegate) {};

JNIWebViewDelegate::~JNIWebViewDelegate() {
	JNIEnv *env;
	javaVM->GetEnv((void**) &env, JNI_VERSION_1_2);
	env->DeleteGlobalRef(delegateRef);
};

jmethodID getMethod(JNIEnv *env, jobject delegate, char *name, char *signature) {
	jclass javaClass = env->GetObjectClass(delegate);
	jmethodID mID = env->GetMethodID(javaClass, name, signature);
	if (mID == NULL) {
		std::cerr << "Method " << name << " with signature " << signature << " was not found";
	}
	return mID;
}

void JNIWebViewDelegate::loadProgress(SWTQtContainer* container, int progress) {
	JNIEnv *env;
	javaVM->GetEnv((void**) &env, JNI_VERSION_1_2);  // Note - threading may get complicated!
	jmethodID method = getMethod(env, delegateRef, "browserLoadProgress", "(II)V");
	jint javaHandle = reinterpret_cast<jint> (container);
	env->CallVoidMethod(delegateRef, method, javaHandle, progress);
}

void JNIWebViewDelegate::loadFinished(SWTQtContainer* container, bool ok){
	JNIEnv *env;
	javaVM->GetEnv((void**) &env, JNI_VERSION_1_2);  // Note - threading may get complicated!
	jmethodID method = getMethod(env, delegateRef, "browserLoadFinished", "(IZ)V");
	jint javaHandle = reinterpret_cast<jint> (container);
	env->CallVoidMethod(delegateRef, method, javaHandle, ok ? JNI_TRUE : JNI_FALSE);
}

void JNIWebViewDelegate::urlChanged(SWTQtContainer* container, const char* url) {
	JNIEnv *env;
	javaVM->GetEnv((void**) &env, JNI_VERSION_1_2);  // Note - threading may get complicated!
	jmethodID method = getMethod(env, delegateRef, "browserUrlChanged", "(ILjava/lang/String;)V");
	jint javaHandle = reinterpret_cast<jint> (container);
	jstring str = env->NewStringUTF(url);
	env->CallVoidMethod(delegateRef, method, javaHandle, str);
}


JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void *)
{
	javaVM = jvm;
	startQt();
	return JNI_VERSION_1_2;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *, void *)
{
	tearDownQt();
}

void JNIWebViewDelegate::loadStarted(SWTQtContainer* container) {
	JNIEnv *env;
	javaVM->GetEnv((void**) &env, JNI_VERSION_1_2);  // Note - threading may get complicated!
	jmethodID method = getMethod(env, delegateRef, "browserLoadStarted", "(I)V");
	jint javaHandle = reinterpret_cast<jint> (container);
	env->CallVoidMethod(delegateRef, method, javaHandle);
}

JNIEXPORT void JNICALL Java_org_symbian_tools_eclipseqt_qwebview_SWTQWebView_qt_1resize
(JNIEnv * env, jobject obj, jint handle, jint x, jint y, jint w, jint h) {
	Q_UNUSED(env);
	Q_UNUSED(obj);
	SWTQtContainer *container = reinterpret_cast<SWTQtContainer*> (handle);
	container->resize(x, y, w, h);
}

/*
 * Class:     org_symbian_tools_jambiproofofconcept_control_EmbedderComposite
 * Method:    disposeQt
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_symbian_tools_eclipseqt_qwebview_SWTQWebView_qt_1dispose
(JNIEnv * env, jobject obj, jint handle) {
	Q_UNUSED(env);
	Q_UNUSED(obj);
	SWTQtContainer *container = reinterpret_cast<SWTQtContainer*> (handle);
	delete container;
}

JNIEXPORT void JNICALL Java_org_symbian_tools_eclipseqt_qwebview_SWTQWebView_qt_1setUrl
(JNIEnv *env, jobject obj, jint handle, jstring url) {
	Q_UNUSED(obj);
	const char* curl = env->GetStringUTFChars(url, NULL);
	SWTQtContainer *container = reinterpret_cast<SWTQtContainer*> (handle);
	container->openUrl(curl);
}

/*
 * Class:     org_symbian_tools_jambiproofofconcept_control_EmbedderComposite
 * Method:    createChild
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_symbian_tools_eclipseqt_qwebview_SWTQWebView_qt_1createChild
(JNIEnv * env, jobject obj, jint handle, jobject delegate) {
	Q_UNUSED(env);
	Q_UNUSED(obj);
	jobject delegateRef = env->NewGlobalRef(delegate);
	return reinterpret_cast<jint> (new SWTQtContainer(handle, new JNIWebViewDelegate(delegateRef)));
}

/*
 * Class:     org_symbian_tools_jambiproofofconcept_control_QWebView
 * Method:    backQt
 * Signature: (I)Z
 */
JNIEXPORT void JNICALL Java_org_symbian_tools_eclipseqt_qwebview_SWTQWebView_qt_1back
(JNIEnv*, jobject, jint handle) {
	SWTQtContainer *container = reinterpret_cast<SWTQtContainer*> (handle);
	container->back();
}

/*
 * Class:     org_symbian_tools_jambiproofofconcept_control_QWebView
 * Method:    canGoBackQt
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_org_symbian_tools_eclipseqt_qwebview_SWTQWebView_qt_1canGoBack
(JNIEnv*, jobject, jint handle) {
	SWTQtContainer *container = reinterpret_cast<SWTQtContainer*> (handle);
	return container->canGoBack() ? JNI_TRUE : JNI_FALSE;
}

/*
 * Class:     org_symbian_tools_jambiproofofconcept_control_QWebView
 * Method:    canGoForwardQt
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_org_symbian_tools_eclipseqt_qwebview_SWTQWebView_qt_1canGoForward
(JNIEnv*, jobject, jint handle) {
	SWTQtContainer *container = reinterpret_cast<SWTQtContainer*> (handle);
	return container->canGoForward() ? JNI_TRUE : JNI_FALSE;
}

/*
 * Class:     org_symbian_tools_jambiproofofconcept_control_QWebView
 * Method:    forwardQt
 * Signature: (I)Z
 */
JNIEXPORT void JNICALL Java_org_symbian_tools_eclipseqt_qwebview_SWTQWebView_qt_1forward
(JNIEnv*, jobject, jint handle) {
	SWTQtContainer *container = reinterpret_cast<SWTQtContainer*> (handle);
	container->forward();
}

/*
 * Class:     org_symbian_tools_jambiproofofconcept_control_QWebView
 * Method:    stopQt
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_symbian_tools_eclipseqt_qwebview_SWTQWebView_qt_1stop
(JNIEnv *, jobject, jint handle) {
	SWTQtContainer *container = reinterpret_cast<SWTQtContainer*> (handle);
	container->stop();
}

/*
 * Class:     org_symbian_tools_jambiproofofconcept_control_QWebView
 * Method:    refreshQt
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_symbian_tools_eclipseqt_qwebview_SWTQWebView_qt_1refresh
(JNIEnv *, jobject, jint handle) {
	SWTQtContainer *container = reinterpret_cast<SWTQtContainer*> (handle);
	container->refresh();
}
