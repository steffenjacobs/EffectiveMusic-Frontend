package me.steffenjacobs.effectivemusic.frontend.client.ui.component.browser;

import com.google.gwt.user.cellview.client.CellTable;

/** @author Steffen Jacobs */
public interface ImprovedCellTableResources extends CellTable.Resources {

	@Source({ CellTable.Style.DEFAULT_CSS, "ImprovedCellTable.css" })
	public CellTable.Style cellTableStyle();
}
