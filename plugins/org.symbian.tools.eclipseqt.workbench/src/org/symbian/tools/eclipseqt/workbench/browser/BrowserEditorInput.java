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
package org.symbian.tools.eclipseqt.workbench.browser;

import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * Editor input for the browsers opened in the workbench editor area.
 * 
 * @author Eugene Ostroukhov
 */
public final class BrowserEditorInput extends PlatformObject implements
		IEditorInput {
	private final String browserId;
	private final String name;
	private final String tooltip;

	public BrowserEditorInput(String browserId, String name, String tooltip) {
		this.browserId = browserId != null ? browserId : "default";
		this.name = name != null ? name : "default";
		this.tooltip = tooltip != null ? tooltip : "default";
	}

	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return name;
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return tooltip;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((browserId == null) ? 0 : browserId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BrowserEditorInput other = (BrowserEditorInput) obj;
		if (browserId == null) {
			if (other.browserId != null)
				return false;
		} else if (!browserId.equals(other.browserId))
			return false;
		return true;
	}

	public String getBrowserId() {
		return browserId;
	}

}
