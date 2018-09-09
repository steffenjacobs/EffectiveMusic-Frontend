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

import me.steffenjacobs.effectivemusic.frontend.client.event.MuteEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.NextEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.PauseMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.PreviousEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.ResumeMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.StartMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.StopMusicEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.TrackPositionChangeEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.VolumeChangeEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.io.BrowseFileEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.libraryimport.GetLibraryImportStatusEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.libraryimport.StartLibraryImportEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.AddToPlaylistEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.GotoPlaylistPositionEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.playlist.PlaylistLoopRepeatEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.refresh.RefreshPlayerInformationEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.refresh.RefreshPlaylistInformationEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.refresh.RefreshTrackInformationEvent;
import me.steffenjacobs.effectivemusic.frontend.client.event.search.SearchEvent;

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
		eventBus.addHandler(RefreshPlaylistInformationEvent.TYPE, event -> sendRequest("http://localhost:8080/music/playlist", false, event.getCallback()));
		eventBus.addHandler(RefreshPlayerInformationEvent.TYPE, event -> sendRequest("http://localhost:8080/music/player", false, event.getCallback()));
		eventBus.addHandler(BrowseFileEvent.TYPE, event -> sendRequest("http://localhost:8081/files/browse?path=" + event.getPath(), true, event.getCallback()));
		eventBus.addHandler(SearchEvent.TYPE, event -> sendRequest("http://localhost:8081/files/search/weighted?search=" + event.getSearchText(), false, event.getCallback()));
		eventBus.addHandler(AddToPlaylistEvent.TYPE, event -> sendRequest("http://localhost:8080/music/playlist/enquene?path=" + event.getPath(), true, event.getCallback()));
		eventBus.addHandler(GotoPlaylistPositionEvent.TYPE, event -> sendRequest("http://localhost:8080/music/playlist/position?position=" + event.getPosition(), true));
		eventBus.addHandler(PlaylistLoopRepeatEvent.TYPE, event -> sendRequest("http://localhost:8080/music/playlist/loop?value=" + event.getLoopRepeatStatus(), true));
		eventBus.addHandler(MuteEvent.TYPE, event -> sendRequest("http://localhost:8080/music/mute?mute=" + event.isMute(), true));
		eventBus.addHandler(GetLibraryImportStatusEvent.TYPE, event -> sendRequest("http://localhost:8081/files/index/status", false, event.getCallback()));
		eventBus.addHandler(StartLibraryImportEvent.TYPE, event -> sendRequest("http://localhost:8081/files/index?path=" + event.getPath(), true));
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
