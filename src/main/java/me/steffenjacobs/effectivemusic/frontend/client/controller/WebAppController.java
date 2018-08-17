package me.steffenjacobs.effectivemusic.frontend.client.controller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;

import me.steffenjacobs.effectivemusic.frontend.client.event.NextEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.PauseMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.PreviousEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.ResumeMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.StartMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.StopMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.TrackPositionChangeEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.VolumeChangeEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.refresh.RefreshTrackInformationEvent;

public class WebAppController {

	private SimpleEventBus eventBus;

	private static final RequestCallback DEFAULT_CALLBACK = new RequestCallback() {
		public void onError(Request request, Throwable e) {
			Window.alert("error = " + e.getMessage());
		}

		public void onResponseReceived(Request request, Response response) {
			if (200 == response.getStatusCode()) {
			}
		}
	};

	@Inject
	public WebAppController(SimpleEventBus evtBus) {
		this.eventBus = evtBus;
	}

	public void bindHandlers() {
		eventBus.addHandler(StopMusicEvent.TYPE, (event) -> sendRequest("http://localhost:8080/music/stop", true, event.getCallback()));
		eventBus.addHandler(StartMusicEvent.TYPE, (event) -> sendRequest("http://localhost:8080/music/play?path=" + event.getPath(), true, event.getCallback()));
		eventBus.addHandler(PauseMusicEvent.TYPE, (event) -> sendRequest("http://localhost:8080/music/pause", true, event.getCallback()));
		eventBus.addHandler(ResumeMusicEvent.TYPE, (event) -> sendRequest("http://localhost:8080/music/resume", true, event.getCallback()));
		eventBus.addHandler(PreviousEvent.TYPE, () -> sendRequest("http://localhost:8080/music/playlist/previous", true));
		eventBus.addHandler(NextEvent.TYPE, () -> sendRequest("http://localhost:8080/music/playlist/next", true));
		eventBus.addHandler(RefreshTrackInformationEvent.TYPE, event -> sendRequest("http://localhost:8080/music/live_info", false, event.getCallback()));
		eventBus.addHandler(TrackPositionChangeEvent.TYPE,
				event -> sendRequest("http://localhost:8080/music/position?position=" + event.getPosition() / 100, true, event.getCallback()));
		eventBus.addHandler(VolumeChangeEvent.TYPE, event -> sendRequest("http://localhost:8080/music/gain?gain=" + event.getVolume(), true, event.getCallback()));
	}

	private void sendRequest(String uri, boolean post) {
		sendRequest(uri, post, null);
	}

	private void sendRequest(String uri, boolean post, RequestCallback callback) {
		String pageBaseUrl = GWT.getHostPageBaseURL();
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, pageBaseUrl + "/rest/" + (post ? "post" : "get") + "?uri=" + URL.encode(uri).replace("&", "%26"));
		builder.setHeader("Content-type", "application/x-www-form-urlencoded");
		if (callback == null) {

			callback = DEFAULT_CALLBACK;
		}
		builder.setCallback(callback);
		try {
			builder.send();
		} catch (RequestException e) {
			e.printStackTrace();
			Window.alert("error = " + e.getMessage());
		}
	}
}
