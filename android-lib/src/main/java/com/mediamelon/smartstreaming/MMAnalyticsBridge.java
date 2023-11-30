
package com.mediamelon.smartstreaming;
import static com.google.android.exoplayer2.C.DATA_TYPE_MEDIA;

//import com.google.ads.interactivemedia.v3.api.StreamManager;
import android.os.Build;
import com.google.android.exoplayer2.DeviceInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
//import com.google.android.exoplayer2.TracksInfo;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.analytics.AnalyticsListener;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManager.DisplayListener;
import android.util.SparseArray;
import android.view.Display;
import android.os.Handler;
import android.view.Display.HdrCapabilities;

import android.media.MediaCodecList;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.os.SystemClock;
//import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.CueGroup;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.BandwidthMeter.EventListener.EventDispatcher;

//import com.google.ads.interactivemedia.v3.api.Ad;
//import com.google.ads.interactivemedia.v3.api.AdErrorEvent;
//import com.google.ads.interactivemedia.v3.api.AdErrorEvent.AdErrorListener;
//import com.google.ads.interactivemedia.v3.api.AdEvent;
//import com.google.ads.interactivemedia.v3.api.AdEvent.AdEventListener;
//import com.google.ads.interactivemedia.v3.api.AdsLoader.AdsLoadedListener;
//import com.google.ads.interactivemedia.v3.api.AdsManagerLoadedEvent;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.collect.ImmutableList;
import com.mediamelon.qubit.ep.SDKExperienceProbe;

//import com.mediamelon.qubit.Log;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MMAnalyticsBridge implements AnalyticsListener {

  private static final String TAG = "MMAnalyticsBridge";
  private static final int MAX_TIMELINE_ITEM_LINES = 3;
  private static final NumberFormat TIME_FORMAT;

  @Override
  public void onPlayWhenReadyChanged(EventTime eventTime, boolean playWhenReady, int reason) {
  //  AnalyticsListener.super.onPlayWhenReadyChanged(eventTime, playWhenReady, reason);
  }


  @Override
  public void onPlaybackSuppressionReasonChanged(EventTime eventTime, int playbackSuppressionReason) {
  //  AnalyticsListener.super.onPlaybackSuppressionReasonChanged(eventTime, playbackSuppressionReason);
  }

  @Override
  public void onIsPlayingChanged(EventTime eventTime, boolean isPlaying) {
  //  AnalyticsListener.super.onIsPlayingChanged(eventTime, isPlaying);
    //Log.i("DDDD", String.valueOf(isPlaying));
  }

  @Override
  public void onPositionDiscontinuity(EventTime eventTime, int reason) {
  //  AnalyticsListener.super.onPositionDiscontinuity(eventTime, reason);
  }

  @Override
  public void onPositionDiscontinuity(EventTime eventTime, Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
  //  AnalyticsListener.super.onPositionDiscontinuity(eventTime, oldPosition, newPosition, reason);
  }

  @Override
  public void onSeekStarted(EventTime eventTime) {
  //  AnalyticsListener.super.onSeekStarted(eventTime);
  }

  @Override
  public void onPlaybackParametersChanged(EventTime eventTime, PlaybackParameters playbackParameters) {
  //  AnalyticsListener.super.onPlaybackParametersChanged(eventTime, playbackParameters);
  }

  @Override
  public void onSeekBackIncrementChanged(EventTime eventTime, long seekBackIncrementMs) {
   // AnalyticsListener.super.onSeekBackIncrementChanged(eventTime, seekBackIncrementMs);
  }

  @Override
  public void onSeekForwardIncrementChanged(EventTime eventTime, long seekForwardIncrementMs) {
   // AnalyticsListener.super.onSeekForwardIncrementChanged(eventTime, seekForwardIncrementMs);
  }


  @Override
  public void onMaxSeekToPreviousPositionChanged(EventTime eventTime, long maxSeekToPreviousPositionMs) {
   // AnalyticsListener.super.onMaxSeekToPreviousPositionChanged(eventTime, maxSeekToPreviousPositionMs);
  }

  @Override
  public void onRepeatModeChanged(EventTime eventTime, int repeatMode) {
   // AnalyticsListener.super.onRepeatModeChanged(eventTime, repeatMode);
  }

  @Override
  public void onShuffleModeChanged(EventTime eventTime, boolean shuffleModeEnabled) {
  //  AnalyticsListener.super.onShuffleModeChanged(eventTime, shuffleModeEnabled);
  }

  @Override
  public void onIsLoadingChanged(EventTime eventTime, boolean isLoading) {
   // AnalyticsListener.super.onIsLoadingChanged(eventTime, isLoading);
  }

  @Override
  public void onAvailableCommandsChanged(EventTime eventTime, Player.Commands availableCommands) {
    //AnalyticsListener.super.onAvailableCommandsChanged(eventTime, availableCommands);
  }

  @Override
  public void onPlayerError(EventTime eventTime, PlaybackException error) {
    //AnalyticsListener.super.onPlayerError(eventTime, error);
  }

  @Override
  public void onTrackSelectionParametersChanged(EventTime eventTime, TrackSelectionParameters trackSelectionParameters) {
    //AnalyticsListener.super.onTrackSelectionParametersChanged(eventTime, trackSelectionParameters);
  }

  @Override
  public void onMediaMetadataChanged(EventTime eventTime, MediaMetadata mediaMetadata) {
   // AnalyticsListener.super.onMediaMetadataChanged(eventTime, mediaMetadata);
  }

  @Override
  public void onPlaylistMetadataChanged(EventTime eventTime, MediaMetadata playlistMetadata) {
    //AnalyticsListener.super.onPlaylistMetadataChanged(eventTime, playlistMetadata);
  }

  @Override
  public void onCues(EventTime eventTime, List<Cue> cues) {
    //AnalyticsListener.super.onCues(eventTime, cues);
  }



  @Override
  public void onCues(EventTime eventTime, CueGroup cueGroup) {
    //AnalyticsListener.super.onCues(eventTime, cueGroup);
  }

  @Override
  public void onAudioEnabled(EventTime eventTime, DecoderCounters decoderCounters) {
    //AnalyticsListener.super.onAudioEnabled(eventTime, decoderCounters);
  }

  @Override
  public void onAudioDecoderInitialized(EventTime eventTime, String decoderName, long initializedTimestampMs, long initializationDurationMs) {
   // AnalyticsListener.super.onAudioDecoderInitialized(eventTime, decoderName, initializedTimestampMs, initializationDurationMs);
  }

  @Override
  public void onAudioDecoderInitialized(EventTime eventTime, String decoderName, long initializationDurationMs) {
   // AnalyticsListener.super.onAudioDecoderInitialized(eventTime, decoderName, initializationDurationMs);
  }

  @Override
  public void onAudioInputFormatChanged(EventTime eventTime, Format format) {
    //AnalyticsListener.super.onAudioInputFormatChanged(eventTime, format);
  }

  @Override
  public void onAudioInputFormatChanged(EventTime eventTime, Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
    //AnalyticsListener.super.onAudioInputFormatChanged(eventTime, format, decoderReuseEvaluation);
  }

  @Override
  public void onAudioPositionAdvancing(EventTime eventTime, long playoutStartSystemTimeMs) {
    //AnalyticsListener.super.onAudioPositionAdvancing(eventTime, playoutStartSystemTimeMs);
  }

  @Override
  public void onAudioDecoderReleased(EventTime eventTime, String decoderName) {
    //AnalyticsListener.super.onAudioDecoderReleased(eventTime, decoderName);
  }

  @Override
  public void onAudioDisabled(EventTime eventTime, DecoderCounters decoderCounters) {
    //AnalyticsListener.super.onAudioDisabled(eventTime, decoderCounters);
  }

  @Override
  public void onAudioSessionIdChanged(EventTime eventTime, int audioSessionId) {
    //AnalyticsListener.super.onAudioSessionIdChanged(eventTime, audioSessionId);
  }

  @Override
  public void onAudioAttributesChanged(EventTime eventTime, AudioAttributes audioAttributes) {
    //AnalyticsListener.super.onAudioAttributesChanged(eventTime, audioAttributes);
  }

  @Override
  public void onSkipSilenceEnabledChanged(EventTime eventTime, boolean skipSilenceEnabled) {
    //AnalyticsListener.super.onSkipSilenceEnabledChanged(eventTime, skipSilenceEnabled);
  }

  @Override
  public void onAudioSinkError(EventTime eventTime, Exception audioSinkError) {
   // AnalyticsListener.super.onAudioSinkError(eventTime, audioSinkError);
  }

  @Override
  public void onAudioCodecError(EventTime eventTime, Exception audioCodecError) {
    //AnalyticsListener.super.onAudioCodecError(eventTime, audioCodecError);
  }

  @Override
  public void onVolumeChanged(EventTime eventTime, float volume) {
    //AnalyticsListener.super.onVolumeChanged(eventTime, volume);
  }

  @Override
  public void onDeviceInfoChanged(EventTime eventTime, DeviceInfo deviceInfo) {
    //AnalyticsListener.super.onDeviceInfoChanged(eventTime, deviceInfo);
  }

  @Override
  public void onDeviceVolumeChanged(EventTime eventTime, int volume, boolean muted) {
    //AnalyticsListener.super.onDeviceVolumeChanged(eventTime, volume, muted);
    SDKExperienceProbe.getInstance().reportDeviceVolumeChanged("VOLUME_CHANGE","Volume: "+volume+"%");
  }

  @Override
  public void onVideoEnabled(EventTime eventTime, DecoderCounters decoderCounters) {
    //AnalyticsListener.super.onVideoEnabled(eventTime, decoderCounters);
  }

  @Override
  public void onVideoDecoderInitialized(EventTime eventTime, String decoderName, long initializedTimestampMs, long initializationDurationMs) {
    //AnalyticsListener.super.onVideoDecoderInitialized(eventTime, decoderName, initializedTimestampMs, initializationDurationMs);
  }

  @Override
  public void onVideoDecoderInitialized(EventTime eventTime, String decoderName, long initializationDurationMs) {
    //AnalyticsListener.super.onVideoDecoderInitialized(eventTime, decoderName, initializationDurationMs);
  }

  @Override
  public void onVideoInputFormatChanged(EventTime eventTime, Format format) {
    //AnalyticsListener.super.onVideoInputFormatChanged(eventTime, format);
  }

  @Override
  public void onVideoInputFormatChanged(EventTime eventTime, Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
    //AnalyticsListener.super.onVideoInputFormatChanged(eventTime, format, decoderReuseEvaluation);
  }

  @Override
  public void onVideoDecoderReleased(EventTime eventTime, String decoderName) {
    //AnalyticsListener.super.onVideoDecoderReleased(eventTime, decoderName);
  }

  @Override
  public void onVideoDisabled(EventTime eventTime, DecoderCounters decoderCounters) {
    //AnalyticsListener.super.onVideoDisabled(eventTime, decoderCounters);
  }

  @Override
  public void onVideoFrameProcessingOffset(EventTime eventTime, long totalProcessingOffsetUs, int frameCount) {
    //AnalyticsListener.super.onVideoFrameProcessingOffset(eventTime, totalProcessingOffsetUs, frameCount);
  }

  @Override
  public void onVideoCodecError(EventTime eventTime, Exception videoCodecError) {
    //AnalyticsListener.super.onVideoCodecError(eventTime, videoCodecError);
  }

  @Override
  public void onRenderedFirstFrame(EventTime eventTime, Object output, long renderTimeMs) {
    //AnalyticsListener.super.onRenderedFirstFrame(eventTime, output, renderTimeMs);
  }

  @Override
  public void onVideoSizeChanged(EventTime eventTime, VideoSize videoSize) {
    //AnalyticsListener.super.onVideoSizeChanged(eventTime, videoSize);
  }

  @Override
  public void onSurfaceSizeChanged(EventTime eventTime, int width, int height) {
  //  AnalyticsListener.super.onSurfaceSizeChanged(eventTime, width, height);
  }

  @Override
  public void onDrmSessionAcquired(EventTime eventTime) {
    //AnalyticsListener.super.onDrmSessionAcquired(eventTime);
  }

  @Override
  public void onDrmSessionAcquired(EventTime eventTime, int state) {
    //AnalyticsListener.super.onDrmSessionAcquired(eventTime, state);
  }

  @Override
  public void onDrmSessionReleased(EventTime eventTime) {
  //  AnalyticsListener.super.onDrmSessionReleased(eventTime);
  }

  @Override
  public void onPlayerReleased(EventTime eventTime) {
   // AnalyticsListener.super.onPlayerReleased(eventTime);
  }

  @Override
  public void onEvents(Player player, Events events) {
   // AnalyticsListener.super.onEvents(player, events);
  }

  static {
    TIME_FORMAT = NumberFormat.getInstance(Locale.US);
    TIME_FORMAT.setMinimumFractionDigits(2);
    TIME_FORMAT.setMaximumFractionDigits(2);
    TIME_FORMAT.setGroupingUsed(false);
  }
  private final Timeline.Window window;
  private final Timeline.Period period;
  private final long startTimeMs;
  private boolean isAdStreaming = false;
  private boolean isAdBuffering = false;
  //private WeakReference<StreamManager> imaAdsManager = null;
  private double pbTime = 0L;
  private double pbTimeBeforeAdStart =-1L;
  /**
   * Creates event logger.
   *
   */
  public MMAnalyticsBridge() {
    window = new Timeline.Window();
    period = new Timeline.Period();
    startTimeMs = SystemClock.elapsedRealtime();
  }

  public void reportPlaybackPosition(double curPbTime){
    if(curPbTime > 0) {
      pbTime = curPbTime;
    }
  }


  // AnalyticsListenerØ
 @Override
 public void onLoadingChanged(EventTime eventTime, boolean isLoading) {
    Log.d(TAG, "loading " + Boolean.toString(isLoading));
  }
//
    @Override
    public void onPlaybackStateChanged(EventTime eventTime, int state) {
        //AnalyticsListener.super.onPlaybackStateChanged(eventTime, state);
        //Log.i("Helo","helo");
      //Log.d("SSSS", getStateString(state));
    }

    @Override
  public void onPlayerStateChanged(EventTime eventTime, boolean playWhenReady, int state) {
//    if(isAdStreaming){
//      Log.d(TAG, "Ad state " +  playWhenReady + ", " + getStateString(state));
//      return;
//    }
      //Log.d("SSSS", "Ad state " +  playWhenReady + ", " + getStateString(state));
    MMSmartStreamingExo2.getInstance().reportPlayerState(playWhenReady, state);
    Log.d(TAG, "state " +  playWhenReady + ", " + getStateString(state));
    Timeline timeline = eventTime.currentTimeline;
    if(timeline!=null) {
      int t = timeline.getWindowCount();
      Timeline.Window w = new Timeline.Window();
      if(w!=null)
      {
      for (int i = 0; i < t; i++) {
        timeline.getWindow(i, w);
        if(w.mediaItem!=null && w.mediaItem.playbackProperties!=null && w.mediaItem.playbackProperties.uri!=null) {
          String url = w.mediaItem.playbackProperties.uri.toString();
          //Log.i("URL", url);
          SDKExperienceProbe.getInstance().reportMetricValue(MMOverridableMetric.ServerAddress, url);
          SDKExperienceProbe.getInstance().updateStreamURL(url);
        }
      }
      }
    }


  }


//  @Override
//  public void onRepeatModeChanged(EventTime eventTime, @Player.RepeatMode int repeatMode) {
//    Log.d(TAG, "repeatMode " +  getRepeatModeString(repeatMode));
//  }
//
//  @Override
//  public void onShuffleModeChanged(EventTime eventTime, boolean shuffleModeEnabled) {
//    Log.d(TAG, "shuffleModeEnabled " + Boolean.toString(shuffleModeEnabled));
//  }
//
//  @Override
//  public void onPositionDiscontinuity(EventTime eventTime, @Player.DiscontinuityReason int reason) {
//    Log.d(TAG, "positionDiscontinuity " + getDiscontinuityReasonString(reason));
//  }
////
//  @Override
//  public void onSeekStarted(EventTime eventTime) {
//    Log.d(TAG, "seekStarted");
//  }

//  @Override
//  public void onPlaybackParametersChanged(EventTime eventTime, PlaybackParameters playbackParameters) {
//    Log.d(TAG,"playbackParameters" + " speed=" + playbackParameters.speed + " pitch=" +playbackParameters.pitch + " skipSilence=" + playbackParameters.skipSilence);
//  }

  @Override
  public void onTimelineChanged(EventTime eventTime, @Player.TimelineChangeReason int reason) {
    int periodCount = eventTime.timeline.getPeriodCount();
    int windowCount = eventTime.timeline.getWindowCount();
    Log.d(TAG, "timelineChanged [" + getEventTimeString(eventTime) + ", periodCount=" + periodCount + ", windowCount=" + windowCount + ", reason=" + getTimelineChangeReasonString(reason));
    MMPresentationInfo info = new MMPresentationInfo();
    if(eventTime.timeline.getWindowCount() > 0){
      eventTime.timeline.getWindow(0, window);
      info.isLive = window.isDynamic;
      info.duration = window.getDurationMs();
      if(info.isLive == true) {
        info.duration = -1L;
      }
      MMSmartStreamingExo2.getInstance().setPresentationInformation(info);
    }
  }

  @Override
  public void onPlayerErrorChanged(EventTime eventTime, @Nullable PlaybackException e) {
    Log.d(TAG, "playerFailed " +  e);
    String errString = "Error Source - Unknown";
    reportPlaybackPosition(eventTime.eventPlaybackPositionMs);
    if(e!=null){
      Log.i("SSSS",e.getErrorCodeName() + "    " + e.getMessage());

    }
    if(e!=null) MMSmartStreamingExo2.getInstance().reportError(e.getErrorCodeName() == null?e.getMessage()!=null?e.getMessage():errString:e.errorCode+" - "+e.getErrorCodeName(), eventTime.currentPlaybackPositionMs);
    MMSmartStreamingExo2.getInstance().reportPlayerState(false, Player.STATE_ENDED);
  }


  String audioLang = "Unknown";
  String videoTrack = "Unknown";
  String subtitleTrack = "Unknown";
  boolean isVDSActive = false;
  boolean isSubtitleActive = false;
  String prevAudioLang = null;
  String prevSubtitle = null;


@Override
public void onTracksChanged(EventTime eventTime, Tracks tracks) {
  //AnalyticsListener.super.onTracksChanged(eventTime, tracks);
  ImmutableList<Tracks.Group> groups = tracks.getGroups();

  for(int i=0;i< groups.size();i++){

    TrackGroup cur_Group = groups.get(i).getMediaTrackGroup();
    int trackType = groups.get(i).getType();

    //For Audio tracks
    if(trackType == 1){
      for(int j=0;j<cur_Group.length;j++){
        if(groups.get(i).isTrackSelected(j)){
          audioLang = cur_Group.getFormat(j).language;
          String label = cur_Group.getFormat(j).label;
          if(label == "commentary"){
            isVDSActive = true;
          }
        }
      }
    }
    //For video Tracks
    if(trackType == 2){
//        for(int j=0;j<cur_Group.length;j++){
//          if(groups.get(i).isTrackSelected(j)){
//
//              isVideoCodecChanged = true;
//              vCodec = cur_Group.getFormat(j).codecs; //For getting Video Codec Info from Video Track group
//              bitrate = cur_Group.getFormat(j).bitrate;
//          }
//        }
    }
    //For Subtitle tracks
    if(trackType == 3){
      for(int j=0;j<cur_Group.length;j++){
        if(groups.get(i).isTrackSelected(j)){
            subtitleTrack = cur_Group.getFormat(j).language;
          isSubtitleActive = true;
        }
      }
    }
  }
  MMSmartStreamingExo2.reportTrackInfo(audioLang, subtitleTrack, isSubtitleActive, isVDSActive, videoTrack);
  if(prevAudioLang==null){
    SDKExperienceProbe.getInstance().reportAudioTrackChange();
  }
  if(prevAudioLang!=null && !prevAudioLang.equals(audioLang)){
    SDKExperienceProbe.getInstance().reportAudioTrackChange();
  }

  if(prevSubtitle==null){
    SDKExperienceProbe.getInstance().reportSubtitleTrackChange();
  }
  if(prevSubtitle!=null && !prevSubtitle.equals(subtitleTrack)){
    SDKExperienceProbe.getInstance().reportSubtitleTrackChange();
  }
  prevAudioLang = audioLang;
  prevSubtitle = subtitleTrack;

}
/*
  @Override
  public void onTracksChanged(EventTime eventTime, TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    //AnalyticsListener.super.onTracksChanged(eventTime, trackGroups, trackSelections);
  }
*/
  @Override
  public void onSeekProcessed(EventTime eventTime) {
    Log.d(TAG, "seekProcessed");
    reportPlaybackPosition(eventTime.eventPlaybackPositionMs);
    MMSmartStreamingExo2.getInstance().reportPlayerSeekCompleted(eventTime.eventPlaybackPositionMs);
  }

  @Override
  public void onMetadata(EventTime eventTime, Metadata metadata) {
    Log.d(TAG, "metadata [" + getEventTimeString(eventTime) + ", ");
    printMetadata(metadata, "  ");
    logd("]");
  }




  @Override
  public void onDecoderEnabled(EventTime eventTime, int trackType, DecoderCounters counters) {
    Log.d(TAG, "decoderEnabled " +  getTrackTypeString(trackType));
  }

//  @Override
//  public void onAudioSessionId(EventTime eventTime, int audioSessionId) {
//    Log.d(TAG, "audioSessionId" + Integer.toString(audioSessionId));
//  }

  @Override
  public void onDecoderInitialized(
          EventTime eventTime, int trackType, String decoderName, long initializationDurationMs) {
    Log.d(TAG, "decoderInitialized" + getTrackTypeString(trackType) + ", " + decoderName);
  }

  @Override
  public void onDecoderInputFormatChanged(EventTime eventTime, int trackType, Format format) {
    Log.d(TAG, "decoderInputFormatChanged" + getTrackTypeString(trackType) + ", " + Format.toLogString(format));
  }

  @Override
  public void onDecoderDisabled(EventTime eventTime, int trackType, DecoderCounters counters) {
    Log.d(TAG, "decoderDisabled" + getTrackTypeString(trackType));
  }

  @Override
  public void onAudioUnderrun(
          EventTime eventTime, int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
    Log.e(TAG, "audioTrackUnderrun" + bufferSize + ", " + bufferSizeMs + ", " + elapsedSinceLastFeedMs + "]");
    MMSmartStreamingExo2.getInstance().reportError("audioTrackUnderrun" + bufferSize + ", " + bufferSizeMs + ", " + elapsedSinceLastFeedMs + "]", eventTime.currentPlaybackPositionMs);
  }

  @Override
  public void onDroppedVideoFrames(EventTime eventTime, int count, long elapsedMs) {
    Log.d(TAG, "droppedFrames" + Integer.toString(count));
    MMSmartStreamingExo2.getInstance().reportFrameLoss(count);
  }

  @Override
  public void onMediaItemTransition(EventTime eventTime, @Nullable MediaItem mediaItem, int reason) {
    //System.out.println();
  }

  String prevResol = 0+"x"+0;
  @Override
  public void onVideoSizeChanged(
          EventTime eventTime,
          int width,
          int height,
          int unappliedRotationDegrees,
          float pixelWidthHeightRatio) {
    String newResol = width+"x"+height;
    SDKExperienceProbe.getInstance().reportResolutionShift(prevResol,newResol);
    prevResol = newResol;
  }

//  @Override
//  public void onRenderedFirstFrame(EventTime eventTime, Surface surface) {
//    Log.d(TAG, "renderedFirstFrame" + surface.toString());
//  }
//
//  @Override
//  public void onMediaPeriodCreated(EventTime eventTime) {
//    Log.d(TAG, "mediaPeriodCreated");
//  }
//
//  @Override
//  public void onMediaPeriodReleased(EventTime eventTime) {
//    Log.d(TAG, "mediaPeriodReleased");
//  }

  @Override
  public void onLoadStarted(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {

  }

  @Override
  public void onLoadError(
          EventTime eventTime,
          LoadEventInfo loadEventInfo,
          MediaLoadData mediaLoadData,
          IOException error,
          boolean wasCanceled) {
    printInternalError(eventTime, "loadError ", error);
    MMSmartStreamingExo2.getInstance().reportError("loadError " + error.getMessage(), eventTime.currentPlaybackPositionMs);
  }

  @Override
  public void onLoadCanceled(
          EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    // Do nothing.
  }

  int prevBitrate = 0;
  @Override
  public void onLoadCompleted(
          EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    //Log.d("codec-d",mediaLoadData.trackFormat.codecs);
    if(mediaLoadData != null &&
            mediaLoadData.dataType == DATA_TYPE_MEDIA &&
            mediaLoadData.trackFormat!= null &&
            ((mediaLoadData.trackFormat.codecs != null && mediaLoadData.trackFormat.codecs.toLowerCase().contains("avc")) ||
                    (mediaLoadData.trackFormat.containerMimeType != null && mediaLoadData.trackFormat.containerMimeType.toLowerCase().contains("mpegurl")))){
      MMChunkInformation info = new MMChunkInformation();
      info.bitrate = mediaLoadData.trackFormat.bitrate;
      info.startTime = mediaLoadData.mediaStartTimeMs;
      info.duration = mediaLoadData.mediaEndTimeMs - mediaLoadData.mediaStartTimeMs;
      if(info.bitrate>0) {
        MMSmartStreamingExo2.getInstance().reportChunkRequest(info);
        if(prevBitrate==0){
          MMSmartStreamingExo2.getInstance().reportABRSwitch(prevBitrate, info.bitrate);
        }
        if(prevBitrate!=0 && prevBitrate!=info.bitrate){
          MMSmartStreamingExo2.getInstance().reportABRSwitch(prevBitrate, info.bitrate);
        }
        prevBitrate = info.bitrate;
      }
      // Do nothing.
      long downloadRate = (loadEventInfo.bytesLoaded * 8000 / loadEventInfo.loadDurationMs);
      MMSmartStreamingExo2.getInstance().reportDownloadRate(downloadRate);
    }

  }

//  @Override
//  public void onReadingStarted(EventTime eventTime) {
//    Log.d(TAG, "mediaPeriodReadingStarted");
//  }

  @Override
  public void onBandwidthEstimate(EventTime eventTime, int totalLoadTimeMs, long totalBytesLoaded, long bitrateEstimate) {
      Log.i("BBBB","Total bytes loadesd  "+totalBytesLoaded);
  }

  //@Override
  public void onViewportSizeChange(EventTime eventTime, int width, int height) {
    Log.d(TAG, "viewportSizeChanged" + width + ", " + height);
  }

  //@Override
//  public void onNetworkTypeChanged(EventTime eventTime, @Nullable NetworkInfo networkInfo) {
//    Log.d(TAG, "networkTypeChanged " + networkInfo == null ? "none" : networkInfo.toString());
//  }

  @Override
  public void onUpstreamDiscarded(EventTime eventTime, MediaLoadData mediaLoadData) {
    Log.d(TAG, "upstreamDiscarded " + Format.toLogString(mediaLoadData.trackFormat));
  }

  @Override
  public void onDownstreamFormatChanged(EventTime eventTime, MediaLoadData mediaLoadData) {
    Log.d(TAG, "downstreamFormatChanged " + Format.toLogString(mediaLoadData.trackFormat));
  }

  @Override
  public void onDrmSessionManagerError(EventTime eventTime, Exception e) {
    printInternalError(eventTime, "drmSessionManagerError", e);
    MMSmartStreamingExo2.getInstance().reportError("drmSessionManagerError " + e.getMessage(), eventTime.currentPlaybackPositionMs);
  }

  @Override
  public void onDrmKeysRestored(EventTime eventTime) {
    Log.d(TAG, "drmKeysRestored");
  }

  @Override
  public void onDrmKeysRemoved(EventTime eventTime) {
    Log.d(TAG, "drmKeysRemoved");
  }

  @Override
  public void onDrmKeysLoaded(EventTime eventTime) {
    Log.d(TAG, "drmKeysLoaded");
  }

  /**
   * IMA ADS LOADER
   * QBR AD ANALYTICS
   */
  /*
  @Override
  public void onAdEvent(AdEvent adEvent) {
    //Log.i("Check","***************MM AD EVENT LOADED CALLED**********************");
   // HandleAdEvent(adEvent);
  }

  @Override
  public void onAdError(AdErrorEvent adErrorEvent) {
   // Log.i("Check","***************MM AD ERROR LOADED CALLED**********************");
    String errorMessage = "Unknown Ad Error";
    if(adErrorEvent != null && adErrorEvent.getError()!= null) {
      errorMessage = adErrorEvent.getError().getMessage();
    }
    Log.e(TAG, "Ad Error Message: " + errorMessage);
    long currentTime =0L;
    if(imaAdsManager != null && imaAdsManager.get() != null){
      currentTime = (long)imaAdsManager.get().getAdProgress().getCurrentTimeMs();
      if(currentTime >= 0) {
        currentTime = currentTime ;
      }
    }
    MMSmartStreamingExo2.getInstance().reportAdError(errorMessage,currentTime);
  }

  @Override
  public void onAdsManagerLoaded(AdsManagerLoadedEvent event) {

    try{
      imaAdsManager = new WeakReference<StreamManager>(event.getStreamManager());

      if(imaAdsManager != null && imaAdsManager.get() != null) {

        MMSmartStreamingExo2.getInstance().reportAdState(MMAdState.AD_REQUEST);
        imaAdsManager.get().addAdEventListener(new AdEvent.AdEventListener() {
          @Override
          public void onAdEvent(AdEvent adEvent) {
            HandleAdEvent(adEvent);
          }
        });
      }
    }catch(Exception e){
      Log.d(TAG, "Exception: " + e.getMessage());
    }
  }

  private void fetchAndReportAdInfo (AdEvent adEvent){
    Map<String,String> adData = adEvent.getAdData();
    Ad adInfo = adEvent.getAd();
    if(adInfo != null) {
      MMAdInfo mmAdInfo = new MMAdInfo();
      if (adInfo.getWidth() > 0) {
        mmAdInfo.adResolution = String.format("%sx%s", adInfo.getWidth(), adInfo.getHeight());
      } else if (adInfo.getVastMediaWidth() > 0) {
        mmAdInfo.adResolution = String
                .format("%sx%s", adInfo.getVastMediaWidth(), adInfo.getVastMediaHeight());
      }

      mmAdInfo.adClient = "IMA";
      mmAdInfo.adType = ((adInfo.isLinear() == true) ? MMAdType.AD_LINEAR : MMAdType.AD_UNKNOWN);
      mmAdInfo.adID = adInfo.getAdId();
      mmAdInfo.adDuration = (long) (adInfo.getDuration() * 1000);
      mmAdInfo.adCreativeType = adInfo.getContentType();
      mmAdInfo.adServer = adInfo.getAdSystem();
      mmAdInfo.adCreativeId =adInfo.getCreativeId();
      mmAdInfo.adPositionInPod = adInfo.getAdPodInfo().getAdPosition();
      mmAdInfo.adPodIndex = adInfo.getAdPodInfo().getPodIndex();
      mmAdInfo.adPodLength = adInfo.getAdPodInfo().getTotalAds();
      mmAdInfo.isBumper = adInfo.getAdPodInfo().isBumper();
      mmAdInfo.adScheduledTime = adInfo.getAdPodInfo().getTimeOffset();
      mmAdInfo.timestamp =System.currentTimeMillis();
      if(mmAdInfo.adPodIndex == 0){
        mmAdInfo.adPosition = "pre";
      }
      else if (mmAdInfo.adPodIndex == -1){
        mmAdInfo.adPosition = "post";
      }
      else {
        mmAdInfo.adPosition = "mid";
      }


      MMSmartStreamingExo2.getInstance().reportAdInfo(mmAdInfo);
    }
  }

  private void HandleAdEvent(AdEvent adEvent){
    Log.v("Ad Event: ", adEvent.getType().toString());

    fetchAndReportAdInfo(adEvent);
    //MMSmartStreamingExo2.getInstance().reportAdPlaybackTime();
    switch (adEvent.getType()) {
      case LOADED:
        isAdBuffering = false;
        isAdStreaming = true;
        if(pbTimeBeforeAdStart < 0) {
          pbTimeBeforeAdStart = pbTime;
        }
        MMSmartStreamingExo2.getInstance().reportPlaybackPosition((long)pbTimeBeforeAdStart);
        fetchAndReportAdInfo(adEvent);
        MMSmartStreamingExo2.getInstance().reportAdState(MMAdState.AD_IMPRESSION);
        break;
      case CONTENT_PAUSE_REQUESTED:
        isAdStreaming = true;
        if(pbTimeBeforeAdStart >= 0) {
          MMSmartStreamingExo2.getInstance().reportPlaybackPosition((long) pbTimeBeforeAdStart);
        }
        MMSmartStreamingExo2.getInstance().reportPlayerState(false, Player.STATE_READY);
        break;
      case CONTENT_RESUME_REQUESTED:
        if(pbTimeBeforeAdStart >= 0) {
          MMSmartStreamingExo2.getInstance().reportPlaybackPosition((long) pbTimeBeforeAdStart);
        }
        MMSmartStreamingExo2.getInstance().reportPlayerState(true, Player.STATE_READY);
        isAdStreaming = false;
        pbTimeBeforeAdStart =-1L;
        break;
      case STARTED:
        isAdStreaming = true;
        MMSmartStreamingExo2.getInstance().reportAdPlaybackTime(0L);
        if(isAdBuffering){
          isAdBuffering = false;
          MMSmartStreamingExo2.getInstance().reportAdBufferingCompleted();
        }
        MMSmartStreamingExo2.getInstance().reportAdState(MMAdState.AD_STARTED);
        break;
      case AD_PROGRESS:
        isAdStreaming = true;
        if(imaAdsManager != null && imaAdsManager.get() != null){
          float currentTime = imaAdsManager.get().getAdProgress().getCurrentTimeMs();
          if(currentTime >= 1) {
            MMSmartStreamingExo2.getInstance().reportAdPlaybackTime((long) currentTime);
          }else if (pbTime > 1000){
            MMSmartStreamingExo2.getInstance().reportAdPlaybackTime((long) pbTime);
          }
        }
        //MMSmartStreamingExo2.getInstance().reportAdState(MMAdState.AD_PLAYING);
        break;
      case PAUSED:
        isAdStreaming = true;
        MMSmartStreamingExo2.getInstance().reportAdState(MMAdState.AD_PAUSED);
        break;
      case RESUMED:
        MMSmartStreamingExo2.getInstance().reportAdState(MMAdState.AD_RESUMED);
        isAdStreaming = true;
        break;
      case AD_BUFFERING:
        isAdStreaming = true;
        if(isAdBuffering == false){
          isAdBuffering = true;
          MMSmartStreamingExo2.getInstance().reportAdBufferingStarted();
        }
        break;
      case MIDPOINT:
        isAdStreaming = true;
        MMSmartStreamingExo2.getInstance().reportAdState(MMAdState.AD_MIDPOINT);
        break;
      case FIRST_QUARTILE:
        isAdStreaming = true;
        MMSmartStreamingExo2.getInstance().reportAdState(MMAdState.AD_FIRST_QUARTILE);
        break;
      case THIRD_QUARTILE:
        isAdStreaming = true;
        MMSmartStreamingExo2.getInstance().reportAdState(MMAdState.AD_THIRD_QUARTILE);
        break;
      case AD_BREAK_STARTED:
        break;

      case ICON_TAPPED:
      case TAPPED:
        break;

      case CLICKED:
        MMSmartStreamingExo2.getInstance().reportAdState(MMAdState.AD_CLICKED);
        isAdStreaming = false;
        break;
      case SKIPPED:
        MMSmartStreamingExo2.getInstance().reportAdState(MMAdState.AD_SKIPPED);
        isAdStreaming = false;
        break;

      case COMPLETED:
        if(imaAdsManager != null && imaAdsManager.get() != null){
          float currentTime = imaAdsManager.get().getAdProgress().getCurrentTimeMs();
          if(currentTime > 1000) {
            MMSmartStreamingExo2.getInstance().reportAdPlaybackTime((long) currentTime);
          }else if (pbTime > 1000){
            MMSmartStreamingExo2.getInstance().reportAdPlaybackTime((long) pbTime);
          }
        }
        MMSmartStreamingExo2.getInstance().reportAdState(MMAdState.AD_COMPLETED);
        isAdStreaming = false;
        break;
      case ALL_ADS_COMPLETED:
        isAdStreaming = false;
        break;
      case AD_BREAK_READY:
        break;
      default:
        break;
    }
  }
*/
  /**
   * Logs a debug message.
   *
   * @param msg The message to log.
   */
  protected void logd(String msg) {
    Log.d(TAG, msg);
  }

//  /**
//   * Logs an error message and exception.
//   *
//   * @param msg The message to log.
//   * @param tr The exception to log.
//   */
//  protected void loge(String msg, Throwable tr) {
//    Log.e(TAG, msg, tr);
//  }

  // Internal methods

  private void logd(EventTime eventTime, String eventName) {
    Log.d(TAG, getEventString(eventTime, eventName));
  }

  private void logd(EventTime eventTime, String eventName, String eventDescription) {
    Log.d(TAG, getEventString(eventTime, eventName, eventDescription));
  }

  private void loge(EventTime eventTime, String eventName, Throwable throwable) {
    Log.e(TAG, getEventString(eventTime, eventName));
  }

  private void loge(
          EventTime eventTime, String eventName, String eventDescription, Throwable throwable) {
    Log.e(TAG, getEventString(eventTime, eventName, eventDescription));
  }

  private void printInternalError(EventTime eventTime, String type, Exception e) {
    Log.e(TAG, "internalError " + type);
    e.printStackTrace();
  }

  private void printMetadata(Metadata metadata, String prefix) {
    for (int i = 0; i < metadata.length(); i++) {
      logd(prefix + metadata.get(i));
    }
  }

  private String getEventString(EventTime eventTime, String eventName) {
    return eventName + " [" + getEventTimeString(eventTime) + "]";
  }

  private String getEventString(EventTime eventTime, String eventName, String eventDescription) {
    return eventName + " [" + getEventTimeString(eventTime) + ", " + eventDescription + "]";
  }

  private String getEventTimeString(EventTime eventTime) {
    String windowPeriodString = "window=" + eventTime.windowIndex;
    if (eventTime.mediaPeriodId != null) {
      // 2.9.0 does has periodIdx, but unique period Idx. So, let us remove this logging of period to have single adaptor across 2.8.x
      //windowPeriodString += ", period=" + eventTime.mediaPeriodId.periodIndex;
      windowPeriodString = "";
      if (eventTime.mediaPeriodId.isAd()) {
        windowPeriodString += ", adGroup=" + eventTime.mediaPeriodId.adGroupIndex;
        windowPeriodString += ", ad=" + eventTime.mediaPeriodId.adIndexInAdGroup;
      }
    }
    return getTimeString(eventTime.realtimeMs - startTimeMs)
            + ", "
            + getTimeString(eventTime.currentPlaybackPositionMs)
            + ", "
            + windowPeriodString;
  }

  private static String getTimeString(long timeMs) {
    return timeMs == C.TIME_UNSET ? "?" : TIME_FORMAT.format((timeMs) / 1000f);
  }

  private static String getStateString(int state) {
    switch (state) {
      case Player.STATE_BUFFERING:
        return "BUFFERING";
      case Player.STATE_ENDED:
        return "ENDED";
      case Player.STATE_IDLE:
        return "IDLE";
      case Player.STATE_READY:
        return "READY";
      default:
        return "?";
    }
  }

  private static String getFormatSupportString(int formatSupport) {
    switch (formatSupport) {
      case RendererCapabilities.FORMAT_HANDLED:
        return "YES";
      case RendererCapabilities.FORMAT_EXCEEDS_CAPABILITIES:
        return "NO_EXCEEDS_CAPABILITIES";
      case RendererCapabilities.FORMAT_UNSUPPORTED_DRM:
        return "NO_UNSUPPORTED_DRM";
      case RendererCapabilities.FORMAT_UNSUPPORTED_SUBTYPE:
        return "NO_UNSUPPORTED_TYPE";
      case RendererCapabilities.FORMAT_UNSUPPORTED_TYPE:
        return "NO";
      default:
        return "?";
    }
  }

  private static String getAdaptiveSupportString(int trackCount, int adaptiveSupport) {
    if (trackCount < 2) {
      return "N/A";
    }
    switch (adaptiveSupport) {
      case RendererCapabilities.ADAPTIVE_SEAMLESS:
        return "YES";
      case RendererCapabilities.ADAPTIVE_NOT_SEAMLESS:
        return "YES_NOT_SEAMLESS";
      case RendererCapabilities.ADAPTIVE_NOT_SUPPORTED:
        return "NO";
      default:
        return "?";
    }
  }

  // Suppressing reference equality warning because the track group stored in the track selection
  // must point to the exact track group object to be considered part of it.
  @SuppressWarnings("ReferenceEquality")
  private static String getTrackStatusString(TrackSelection selection, TrackGroup group,
                                             int trackIndex) {
    return getTrackStatusString(selection != null && selection.getTrackGroup() == group
            && selection.indexOf(trackIndex) != C.INDEX_UNSET);
  }

  private static String getTrackStatusString(boolean enabled) {
    return enabled ? "[X]" : "[ ]";
  }

  private static String getRepeatModeString(@Player.RepeatMode int repeatMode) {
    switch (repeatMode) {
      case Player.REPEAT_MODE_OFF:
        return "OFF";
      case Player.REPEAT_MODE_ONE:
        return "ONE";
      case Player.REPEAT_MODE_ALL:
        return "ALL";
      default:
        return "?";
    }
  }

  private static String getDiscontinuityReasonString(@Player.DiscontinuityReason int reason) {
    switch (reason) {
//      case Player.DISCONTINUITY_REASON_PERIOD_TRANSITION:
//        return "PERIOD_TRANSITION";
      case Player.DISCONTINUITY_REASON_SEEK:
        return "SEEK";
      case Player.DISCONTINUITY_REASON_SEEK_ADJUSTMENT:
        return "SEEK_ADJUSTMENT";
//      case Player.DISCONTINUITY_REASON_AD_INSERTION:
//        return "AD_INSERTION";
      case Player.DISCONTINUITY_REASON_INTERNAL:
        return "INTERNAL";
      default:
        return "?";
    }
  }

  private static String getTimelineChangeReasonString(@Player.TimelineChangeReason int reason) {
    switch (reason) {
//      case Player.TIMELINE_CHANGE_REASON_PREPARED:
//        return "PREPARED";
//      case Player.TIMELINE_CHANGE_REASON_RESET:
//        return "RESET";
//      case Player.TIMELINE_CHANGE_REASON_DYNAMIC:
//        return "DYNAMIC";
      default:
        return "?";
    }
  }

  private static String getTrackTypeString(int trackType) {
    switch (trackType) {
      case C.TRACK_TYPE_AUDIO:
        return "audio";
      case C.TRACK_TYPE_DEFAULT:
        return "default";
      case C.TRACK_TYPE_METADATA:
        return "metadata";
      case C.TRACK_TYPE_NONE:
        return "none";
      case C.TRACK_TYPE_TEXT:
        return "text";
      case C.TRACK_TYPE_VIDEO:
        return "video";
      default:
        return trackType >= C.TRACK_TYPE_CUSTOM_BASE ? "custom (" + trackType + ")" : "?";
    }
  }
}
