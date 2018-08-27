package me.steffenjacobs.effectivemusic.frontend.client.ui.component.mediagrid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/** @author Steffen Jacobs */
public class Media {

	public static final RES INSTANCE = GWT.create(RES.class);

	public interface RES extends ClientBundle {
		@Source("grid.css")
		public MediaCss grid();
	}

	public interface MediaCss extends CssResource {

		String grid();

		@ClassName("loop-1")
		String loop1();

		@ClassName("loop-10")
		String loop10();

		@ClassName("loop-11")
		String loop11();

		@ClassName("loop-12")
		String loop12();

		@ClassName("loop-2")
		String loop2();

		@ClassName("loop-3")
		String loop3();

		@ClassName("loop-4")
		String loop4();

		@ClassName("loop-5")
		String loop5();

		@ClassName("loop-6")
		String loop6();

		@ClassName("loop-7")
		String loop7();

		@ClassName("loop-8")
		String loop8();

		@ClassName("loop-9")
		String loop9();

		@ClassName("loop1")
		String loop1_();

		@ClassName("loop10")
		String loop10_();

		@ClassName("loop11")
		String loop11_();

		@ClassName("loop12")
		String loop12_();

		@ClassName("loop2")
		String loop2_();

		@ClassName("loop3")
		String loop3_();

		@ClassName("loop4")
		String loop4_();

		@ClassName("loop5")
		String loop5_();

		@ClassName("loop6")
		String loop6_();

		@ClassName("loop7")
		String loop7_();

		@ClassName("loop8")
		String loop8_();

		@ClassName("loop9")
		String loop9_();
	}
}