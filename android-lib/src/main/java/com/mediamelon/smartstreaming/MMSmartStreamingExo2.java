package com.mediamelon.smartstreaming;
import android.annotation.TargetApi;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Handler;
import android.media.MediaDrm;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.content.Context;
import android.util.SparseArray;
import android.view.Display;
import android.drm.DrmManagerClient;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.TracksInfo;

import com.mediamelon.qubit.ep.SDKExperienceProbe;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.ref.WeakReference;

import android.media.MediaCodecList;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * ExoPlayer interface for the MMSmartStreaming SDK. This class includes wrapper functions to 
 * simplify integration of the MediaMelon SDK with the ExoPlayer media player.
 */

public class MMSmartStreamingExo2 implements MMSmartStreamingObserver{
    private static String deviceMarketingName1;
    //private MMSmartStreamingNowtilusSSAIPlugin mmSmartStreamingNowtilusSSAIPlugin = null;
  //public static MMSmartStreamingNowtilusSSAIPlugin staticO = null;
  private boolean isSSAIAdPlaying = false;
  private static boolean isOnloadSent = false;
  private MMPlayerState mmPreviousPlayerState = null;
  private MMAdState mmPreviousAdState = null;

  private MMAnalyticsBridge analyticsBridge = null;
  private ExoPlayer playerExo = null;
  private boolean isLiveFilter;

  //private String deviceMarketingName1 = "Unknown";
 // private WeakReference<SimpleExoPlayer> playerSimple = new WeakReference<>(null);;
  private WeakReference<ExoPlayer> playerSimple = new WeakReference<>(null);

  public void sessionInitializationCompleted(Integer initCmdId, MMSmartStreamingInitializationStatus status, String description){
    if(obs != null){
      obs.get().sessionInitializationCompleted(initCmdId, status, description);
    }

    if (status == MMSmartStreamingInitializationStatus.Success){
      Integer interval = MMSmartStreaming.getInstance().getLocationUpdateInterval();
      MMNetworkInformationRetriever.instance().startRetriever(ctx.get(), interval);
    }
  }

  /**
   * Gets the SDK adapter instance
   * @return SDK adapter instance for Exoplayer
   */
  public static MMSmartStreamingExo2 getInstance(){
    if(myObj == null){
      myObj = new MMSmartStreamingExo2();
    }
    return myObj;
  }

  /**
   * Gets the SDK version
   * @return SDK version (major.minor.patch)
   */
  public static String getVersion(){
    return MMSmartStreaming.getVersion();
  }

  /**
   * Gets the registration status (done via registerMMSmartStreaming)
   * @return true if the SDK has successfully registered with the registerMMSmartStreaming method;
   * otherwise returns false.
   * @see registerMMSmartStreaming
   */
  public static boolean getRegistrationStatus(){
    return MMSmartStreaming.getRegistrationStatus();
  }

  /**
   * Sets the activity context
   * @param aCtx Player context
   */
  public void setContext(Context aCtx){
    if(logStackTrace){
      Log.v(StackTraceLogTag, "setContext" + aCtx);
    }

    ctx = new WeakReference<Context>(aCtx);
    applicationContext=aCtx;
    DisplayMetrics dm = ctx.get().getResources().getDisplayMetrics();
    Integer height = dm.heightPixels;
    Integer width = dm.widthPixels;

    mainHandler = new Handler(ctx.get().getMainLooper());
    TelephonyManager tm = (TelephonyManager)ctx.get().getSystemService(Context.TELEPHONY_SERVICE);

    //For Device Display Capabilities
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
      DisplayManager dm1 = (DisplayManager)ctx.get().getSystemService(Context.DISPLAY_SERVICE);
      getDeviceCapibilities(dm1);
    }
    getWVDrmInfo();
    //codecList();
    MMSmartStreaming.reportDeviceInfo(Build.BRAND, Build.MODEL, "Android", Build.VERSION.RELEASE, (tm!=null? (tm.getNetworkOperatorName()):null), width, height, deviceMarketingName1);
    MMNetworkInformationRetriever.instance().initializeRetriever(ctx.get());
  }

  int displayId;
  public void getDeviceCapibilities(DisplayManager dm){


    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
      Display[] displays = dm.getDisplays();
      for (Display display : displays) {
        displayId = display.getDisplayId(); // Use the displayId as needed for specific display-related operations
      }
      checkDisplayCapabilities(displayId,dm);
    }

  }
  //getting the HDR qualities of a device
  private static final SparseArray<String> HDR_DESCRIPTIONS = new SparseArray<>();
  static {
    HDR_DESCRIPTIONS.put(Display.HdrCapabilities.HDR_TYPE_HDR10, "HDR10");
    HDR_DESCRIPTIONS.put(Display.HdrCapabilities.HDR_TYPE_DOLBY_VISION, "DolbyVision");
    HDR_DESCRIPTIONS.put(Display.HdrCapabilities.HDR_TYPE_HDR10_PLUS,"HDR10+");
    HDR_DESCRIPTIONS.put(Display.HdrCapabilities.HDR_TYPE_HLG,"HLG");
    // Add more mappings as needed
  }
  private void checkDisplayCapabilities(int displayId,DisplayManager dm) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      //DisplayManager displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
      Display display = dm.getDisplay(displayId);

      if (display != null) {
        Display.HdrCapabilities hdrCapabilities = display.getHdrCapabilities();
        String capbilities = "";
        if (hdrCapabilities != null) {
          // Display supports HDR
          int[] supportedHdrTypes = hdrCapabilities.getSupportedHdrTypes();
          for(int t:supportedHdrTypes){
            capbilities = capbilities + HDR_DESCRIPTIONS.get(t)+",";
          }
          if(capbilities!="") capbilities = capbilities.substring(0,capbilities.length()-1);
          float maxLuminance = hdrCapabilities.getDesiredMaxLuminance();
          float maxAverageLuminance = hdrCapabilities.getDesiredMaxAverageLuminance();
          // Process the HDR capabilities as needed
          // You can check the supported HDR types and luminance levels here
        } else {
          // HDR not supported on this display
          capbilities = "None";
        }
        //Generate Report Display Capibilities
        SDKExperienceProbe.getInstance().reportDisplayCapabilities(capbilities);
      }
    }
  }
  //  //getting the supported hardware codecs
  public void codecList() {
    int codecCount = MediaCodecList.getCodecCount();
    Log.i("CodecInfo", String.valueOf(codecCount));
    for (int i = 0; i < codecCount; i++) {
      MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);

      if (codecInfo.isEncoder()) {
        // Skip encoder codecs, we are interested in decoder codecs
        continue;
      }




      String codecName = codecInfo.getName();
      //Log.i("CodecInfo",codecName);


     


      String[] supportedTypes = codecInfo.getSupportedTypes();

      // Check if the codec supports any media types
      if (supportedTypes.length > 0) {
        // This codec supports one or more media types, print its details
        for (String type : supportedTypes) {
          // Print codec name and supported media type
          String codecDetails = "Codec: " + codecName + ", Supported Type: " + type;
          Log.d("CodecInfo", codecDetails);
        }
      }
    }
  }
//

  private static final UUID WIDEVINE_UUID = new UUID(0xEDEF8BA979D64ACEL, 0xA3C827DCD51D21EDL);

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
  @SuppressWarnings("ResourceType")
  private void getWVDrmInfo() {
    MediaDrm mediaDrm = null;
    try {
      mediaDrm = new MediaDrm(WIDEVINE_UUID);

      String vendor = mediaDrm.getPropertyString(MediaDrm.PROPERTY_VENDOR);
      String version = mediaDrm.getPropertyString(MediaDrm.PROPERTY_VERSION);
      String description = mediaDrm.getPropertyString(MediaDrm.PROPERTY_DESCRIPTION);
      String algorithms = mediaDrm.getPropertyString(MediaDrm.PROPERTY_ALGORITHMS);
      String securityLevel = mediaDrm.getPropertyString("securityLevel");
      String systemId = mediaDrm.getPropertyString("systemId");
      String hdcpLevel = mediaDrm.getPropertyString("hdcpLevel");
      String maxHdcpLevel = mediaDrm.getPropertyString("maxHdcpLevel");
      String usageReportingSupport = mediaDrm.getPropertyString("usageReportingSupport");
      String maxNumberOfSessions = mediaDrm.getPropertyString("maxNumberOfSessions");
      String numberOfOpenSessions = mediaDrm.getPropertyString("numberOfOpenSessions");
      SDKExperienceProbe.getInstance().reportDRM(securityLevel,version,systemId);
      mediaDrm.release();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }






  /**
   * Registers the QBR SmartStreaming engine and performs a license verification. This API should
   * be called once when player starts. The QBR SmartStreaming engine must be successfully
   * registered before initialization.
   * This is a synchronous call. Registration status can be checked at any time using the
   * getRegistrationStatus method.
   *
   * @param playerName Name of the player
   * @param customerID MediaMelon assigned customer ID
   * @param subscriberID Viewer's subscriber ID
   * @param domainName Content-owner domain name.
   *                   Some business organizations may would like to do analytics segmented
   *                   by group. For example, a Media House may have many divisions, and will like
   *                   to categorize their analysis based on division. Or a content owner has
   *                   distributed content to various resellers and would like to know the reseller
   *                   from whom the user is playing the content. In this case every reseller will
   *                   have separate application, and will configure the domain name.
   *
   * @note Please be aware that this API will be deprecated in the version 4.x.x. Integrators are
   * advised to use another overload of this API that accepts subscriberType as parameter as well
   * @see registerMMSmartStreaming
   * @see getRegistrationStatus
   * @see updateSubscriberID
   */
  public static void registerMMSmartStreaming(String playerName, String customerID, String subscriberID, String domainName){
    if(!MMSmartStreaming.getRegistrationStatus())MMSmartStreaming.registerMMSmartStreaming(playerName, customerID, "ANDROIDSDK", subscriberID, domainName);
  }

  /**
   * Registers the QBR SmartStreaming engine and performs a license verification. This API should
   * be called once when player starts. The QBR SmartStreaming engine must be successfully
   * registered before initialization.
   * This is a synchronous call. Registration status can be checked at any time using the
   * getRegistrationStatus method.
   *
   * @param playerName Name of the player
   * @param customerID MediaMelon assigned customer ID
   * @param subscriberID Viewer's subscriber ID
   * @param domainName Content-owner domain name.
   *                   Some business organizations may would like to do analytics segmented
   *                   by group. For example, a Media House may have many divisions, and will like
   *                   to categorize their analysis based on division. Or a content owner has
   *                   distributed content to various resellers and would like to know the reseller
   *                   from whom the user is playing the content. In this case every reseller will
   *                   have separate application, and will configure the domain name.
   * @param subscriberType Viewer's subscriber type such as "Free", "Basic" or "Premium" as
   *                         configured by the customer for the end user of the player.
   *
   * @see getRegistrationStatus
   * @see updateSubscriberID
   */
  public static void registerMMSmartStreaming(String playerName, String customerID, String subscriberID, String domainName, String subscriberType){
    if(!MMSmartStreaming.getRegistrationStatus()) MMSmartStreaming.registerMMSmartStreaming(playerName, customerID, "ANDROIDSDK", subscriberID, domainName, subscriberType);
  }

  /**
   * Registers the QBR SmartStreaming engine and performs a license verification. This API should
   * be called once when player starts. The QBR SmartStreaming engine must be successfully
   * registered before initialization.
   * This is a synchronous call. Registration status can be checked at any time using the
   * getRegistrationStatus method.
   *
   * @param playerName Name of the player
   * @param customerID MediaMelon assigned customer ID
   * @param subscriberID Viewer's subscriber ID
   * @param domainName Content-owner domain name.
   *                   Some business organizations may would like to do analytics segmented
   *                   by group. For example, a Media House may have many divisions, and will like
   *                   to categorize their analysis based on division. Or a content owner has
   *                   distributed content to various resellers and would like to know the reseller
   *                   from whom the user is playing the content. In this case every reseller will
   *                   have separate application, and will configure the domain name.
   * @param subscriberType Viewer's subscriber type such as "Free", "Basic" or "Premium" as
   *                         configured by the customer for the end user of the player.
   * @param subscriberTag Viewer's tag using which one can track their pattern

   *
   * @see getRegistrationStatus
   * @see updateSubscriberID
   */
  public static void registerMMSmartStreaming(String playerName, String customerID, String subscriberID, String domainName, String subscriberType, String subscriberTag,boolean hashSubscriberId){
    if(!MMSmartStreaming.getRegistrationStatus()) MMSmartStreaming.registerMMSmartStreaming(playerName, customerID, "ANDROIDSDK", subscriberID, domainName, subscriberType,subscriberTag,hashSubscriberId);
  }

  public ArrayList<String> getMissingPermissions(Context context){
    return MMNetworkInformationRetriever.getMissingPermissions(context);
  }

  /**
   * Disables the fetching of manifests by the SDK to determine the presentation information of the content.
   * SDK will rely completely on presentation information provided as part of setPresentationInformation.
   * @param disable Disables/Enables the manifest fetch by the SDK
   * @see setPresentationInformation
   */
  private static void disableManifestsFetch(boolean disable){
    try{
      Thread currentThread = Thread.currentThread();
      String threadName = currentThread.getName();
      Log.i("TTHH",threadName);
      MMSmartStreaming.disableManifestsFetch(disable);
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  /**
   * After the registration, user may will like to update the subscriber ID,
   * for example - user logged off from the Video service website, and logs in again with different
   * user.
   * @note This API will be deprecated for updateSubscriber from version 4.x.x
   *
   * @param subscriberID New Subscriber ID
   * @see registerMMSmartStreaming
   * @see updateSubscriber
   *
   */
  public static void updateSubscriberID(String subscriberID){
    MMSmartStreaming.updateSubscriberID(subscriberID);
  }

  /**
   * After the registration, user may will like to update the subscriber ID,
   * for example - user logged off from the Video service website, and logs in again with different
   * user.
   * @param subscriberID New Subscriber ID
   * @param subscriberID New Subscriber Type
   * @see registerMMSmartStreaming
   *
   */
  public static void updateSubscriber(String subscriberID, String subscriberType){
    MMSmartStreaming.updateSubscriber(subscriberID, subscriberType);
  }

  /**
   * Reports the media player characteristics to analytics.
   * Use a NULL pointer if the value is unknown or inapplicable.
   *
   * @param brand Brand of the player. For example - Brand could be Organisation Name.
   * @param model Model of the player. For example - This could be a variant of player.
   *              Say name of third party player used by organisation. Or any human readable name of
   *              the player.
   * @param version Version of the player.
   */
  public static void reportPlayerInfo(String brand, String model, String version){
    MMSmartStreaming.reportPlayerInfo(brand, model, version);
  }

    /**
     * @param appName Name of the application which the user using.
     * @param appVersion Version of the application.
     */

  public static void reportAppInfo(String appName, String appVersion){
      MMSmartStreaming.reportAppInfo(appName,appVersion);
  }

  public static void reportTrackInfo(String audioTrack, String subtitleTrack, boolean isSubtitleActive, boolean isVDSActive, String videoTrack){
      MMSmartStreaming.reportTrackInfo(audioTrack, subtitleTrack, isSubtitleActive, isVDSActive, videoTrack);
  }

  public static void setDeviceInfo(String deviceMarketingName){
      deviceMarketingName1 = deviceMarketingName;
  }
  public static void reportVideoQuality(String videoQuality){
    MMSmartStreaming.reportVideoQuality(videoQuality);
  }

  /**
   * Initializes the session for playback with QBR optimization. This API should be called once for
   * every media session and is asynchronous. Its completion is indicated via callback to
   * MMSmartStreamingObserver::sessionInitializationCompleted that user may choose to ignore.
   *
   * @param mode QBR operating mode.
   * @param manifestURL URL of the media manifest
   * @param metaURL URL of the media metadata. If it is null, and QBR operating mode is
   *                Bitsave, CostSave, or Quality, a metadata file with manifestUrl base name will
   *                be used. If the metadata cannot be retrieved, mode will default to Disabled.
   * @param assetID Content identifier
   * @param assetName Content name
   * @param observer MMSmartStreamingObserver that will receive the callback on initialization
   *                 completion.
   * @see MMQBRMode
   * @see MMSmartStreamingObserver
   */
  public void initializeSession(SimpleExoPlayer simplePlayer, MMQBRMode mode, String manifestURL, String metaURL, String assetID, String assetName, MMSmartStreamingObserver observer){

    reset();

    if(observer != null) {
      obs = new WeakReference<MMSmartStreamingObserver>(observer);
    }
   // playerSimple = new WeakReference<SimpleExoPlayer>(simplePlayer);
    playerSimple = new WeakReference<ExoPlayer>(simplePlayer);
    if(playerSimple != null && playerSimple.get() != null){
      if(analyticsBridge == null) {
        analyticsBridge = new MMAnalyticsBridge();
      }
      playerSimple.get().addAnalyticsListener(analyticsBridge);
    }
    //System.out.println(playerSimple.get().getCurrentTracksInfo());
    MMSmartStreaming.getInstance().initializeSession(mode, manifestURL, metaURL, assetID, assetName, this);
  }

  /**
   * Initializes the session for playback with QBR optimization. This API should be called once for
   * every media session and is asynchronous. Its completion is indicated via callback to
   * MMSmartStreamingObserver::sessionInitializationCompleted that user may choose to ignore.
   *
   * @param mode QBR operating mode.
   * @param manifestURL URL of the media manifest
   * @param metaURL URL of the media metadata. If it is null, and QBR operating mode is
   *                Bitsave, CostSave, or Quality, a metadata file with manifestUrl base name will
   *                be used. If the metadata cannot be retrieved, mode will default to Disabled.
   * @param assetID Content identifier
   * @param assetName Content name
   * @param videoId Video Id
   * @param observer MMSmartStreamingObserver that will receive the callback on initialization
   *                 completion.
   * @see MMQBRMode
   * @see MMSmartStreamingObserver
   */
  //public void initializeSession(SimpleExoPlayer simplePlayer, MMQBRMode mode, String manifestURL, String metaURL, String assetID, String assetName, String videoId, MMSmartStreamingObserver observer, JSONObject cm){
  public void initializeSession(ExoPlayer simplePlayer, MMQBRMode mode, String manifestURL, String metaURL, String assetID, String assetName, String videoId, MMSmartStreamingObserver observer, JSONObject cm,boolean isLive){
 // used currently
    reset();
    if(observer != null) {
      obs = new WeakReference<MMSmartStreamingObserver>(observer);
    }
    //playerSimple = new WeakReference<SimpleExoPlayer>(simplePlayer);
    playerSimple = new WeakReference<ExoPlayer>(simplePlayer);
    if(playerSimple != null && playerSimple.get() != null){
      if(analyticsBridge == null) {
        analyticsBridge = new MMAnalyticsBridge();
      }
      playerSimple.get().addAnalyticsListener(analyticsBridge);
    }
    isLiveFilter = isLive;
    MMSmartStreaming.getInstance().initializeSession(mode,manifestURL,metaURL,cm,this,isLive);
  }

  public void initializeSession(ExoPlayer simplePlayer, MMQBRMode mode, String manifestURL, String metaURL, String assetID, String assetName, String videoId, MMSmartStreamingObserver observer, JSONObject cm){
    // used currently
    reset();

    if(observer != null) {
      obs = new WeakReference<MMSmartStreamingObserver>(observer);
    }
    playerSimple = new WeakReference<ExoPlayer>(simplePlayer);
    if(playerSimple != null && playerSimple.get() != null){
      if(analyticsBridge == null) {
        analyticsBridge = new MMAnalyticsBridge();
      }
      playerSimple.get().addAnalyticsListener(analyticsBridge);
    }
    MMSmartStreaming.getInstance().initializeSession(mode,manifestURL,metaURL,cm,this,isLiveFilter);

  }

  public void setPlayer(SimpleExoPlayer simplePlayer){
    if(simplePlayer != null) {
      //playerSimple = new WeakReference<SimpleExoPlayer>(simplePlayer);
      playerSimple = new WeakReference<ExoPlayer>(simplePlayer);
      if (playerSimple != null && playerSimple.get() != null) {
        if (analyticsBridge == null) {
          analyticsBridge = new MMAnalyticsBridge();
        }
        playerSimple.get().addAnalyticsListener(analyticsBridge);
      }
    }
  }

  public MMAnalyticsBridge getAnalyticsBridge(){
    if(analyticsBridge == null) {
      analyticsBridge = new MMAnalyticsBridge();
    }
    return analyticsBridge;
  }

  /**
   * Reports that user initiated the playback session.
   * This should be called at different instants depending on the mode of operation of player.
   * In Auto Play Mode, should be the called when payer is fed with the manifest URL for playback
   * In non-Auto Play Mode, should be called when the user presses the play button on the
   * player
   */
  public void reportUserInitiatedPlayback(){
    MMSmartStreaming.getInstance().reportUserInitiatedPlayback();
    isOnloadSent=true;
  }

  /**
   * Tells the QBR SmartStreaming engine which representations that the player can present.
   * Representations that are not in this list will not be selected by the QBR SmartStreaming engine.
   * @param presentationInfo PresentationInformation specifying the representations selected by
   *                         the player for playback.
   * @see blacklistRepresentation
   * @see MMPresentationInfo
   */
  public void setPresentationInformation(MMPresentationInfo presentationInfo){
    MMSmartStreaming.getInstance().setPresentationInformation(presentationInfo);
    isLiveFilter = presentationInfo.isLive;
  }

  /**
   * Removes a representation from the list previously defined by setPresentationInformation. This
   * would typically be used to stop referring to a representation that is listed in the manifest
   * but not currently available.
   *
   * @param representationIdx Representation Index for the representation to be (un)blacklisted.
   * @param blacklistRepresentation True to blacklist the representation; False to un-blacklist
   *                                the representation.
   * @see setPresentationInformation
   */
  public void blacklistRepresentation(Integer representationIdx, boolean blacklistRepresentation){
    MMSmartStreaming.getInstance().blacklistRepresentation(representationIdx, blacklistRepresentation);
  }

  /**
   * Returns the bandwidth required for the QBR representation that delivers constant quality across
   * the session.
   *
   * @param representationTrackIdx Track Index of the representation whose corresponding
   *                               quality bitrate is to be evaluated.
   * @param defaultBitrate Bitrate of the CBR representation as advertised in the manifest (in
   *                         bits per second).
   * @param bufferLength Amount of media buffered in player ahead of current playback position (in
   *                    milliseconds).
   * @param playbackPosition Current playback position (in milliseconds).
   * @return Bandwidth of QBR representation (in bits per second).
   */
  public Integer getQBRBandwidth(Integer representationTrackIdx, Integer defaultBitrate, Long bufferLength, Long playbackPosition){
    return MMSmartStreaming.getInstance().getQBRBandwidth(representationTrackIdx, defaultBitrate, bufferLength, playbackPosition);
  }

  /**
   * During the playback session, player is expected to query the constant quality chunk that it
   * should request from server for the chunk selected based on ABR algorithm.
   * This API is used only if Qubitisation of content is to be achieved.
   * @param cbrChunk MMChunkInformation object identifying the chunk selected by ABR algorithm.
   * For referencing the chunk there are two option:
   * (a) Caller of API may specify resourceURL
   * (b) Caller of API may specify combination of sequence id and track id.
   * Using option b) may result in improved CPU performace of this API and is recommended.
   * @return The chunk selected by the QBR algorithm.
   * @see MMChunkInformation
   */
  public MMChunkInformation getQBRChunk(MMChunkInformation cbrChunk){
    return MMSmartStreaming.getInstance().getQBRChunk(cbrChunk);
  }

  /**
   * Reports the chunk request to analytics. This method is not used when QBR optimization is
   * enabled.
   * @param chunkInfo Chunk selected by the player.
   * @see MMChunkInformation
   */
  public void reportChunkRequest(MMChunkInformation chunkInfo){
    MMSmartStreaming.getInstance().reportChunkRequest(chunkInfo);
  }

  /**
   * Reports current download rate (rate at which chunk is downloaded) to analytics. This should be
   * reported for every chunk download (if possible). If this value is not available on every
   * chunk download, then last updated value with player should be reported every 2 seconds.
   *
   * @param downloadRate Download rate as measured by the player (in bits per second)
   */
  public void reportDownloadRate(Long downloadRate){
    MMSmartStreaming.getInstance().reportDownloadRate(downloadRate);
  }

  /**
   * Reports current download rate (rate at which chunk is downloaded) to analytics. This should be
   * reported for every chunk download (if possible). If this value is not available on every
   * chunk download, then last updated value with player should be reported every 2 seconds.
   *
   * @param bufferLength Download rate as measured by the player (in bits per second)
   */
  public void reportBufferLength(Long bufferLength){
    MMSmartStreaming.getInstance().reportBufferLength(bufferLength);
  }

  /**
   * Reports custom metadata, in the form of a key-value pair, to analytics.
   *
   * @param key Custom metadata key.
   * @param value Custom metadata value.
   */
  public void reportCustomMetadata(String key, String value){
    MMSmartStreaming.getInstance().reportCustomMetadata(key, value);
  }

  /**
   * Reports current playback position in media to analytics. This should be reported every two
   * seconds if possible.
   *
   * @param playbackPos Current playback position (in milliseconds).
   */
  public void reportPlaybackPosition(Long playbackPos){
    MMSmartStreaming.getInstance().reportPlaybackPosition(playbackPos);
  }

  /**
   * Override the SmartSight-calculated metric with a specific value.
   *
   * @param metric : Metric to be overridden.
   * @param value : New metric value. Even if the value of
   *   metric is numeric, int (for example in case of latency), user
   *   is expected to provide its string representation:
   * - For Latency, the latency in seconds, with with millisecond resolution (e.g., "1.236")
   * - For ServerAddress, the name of the cdn (e.g., "PrivateCDN")
   * - For DurationWatched, the duration watched in seconds, with millisecond resolution (e.g., "137.935")
   * @see MMOverridableMetric
   */
  public void reportMetricValue(MMOverridableMetric metric, String value){
    MMSmartStreaming.getInstance().reportMetricValue(metric, value);
  }

  void reset(){
    obs = null;
    cumulativeFramesDropped = 0;
    sendBufferingCompletionOnReady = false;
    playbackPollingStarted = false;
    playerSimple = null;
    if(timer != null) {
      timer.cancel();
      timer = null;
    }

    isSSAIAdPlaying=false;
    isOnloadSent=false;
  }

  void stopPlaybackPolling(){
    playbackPollingStarted = false;
    if(timer != null) {
      timer.cancel();
      timer = null;
    }
  }

  void startPlaybackPolling(){
    if(playbackPollingStarted == false){
      playbackPollingStarted = true;
      if(timer == null){
        timer = new Timer();
      }
      timer.scheduleAtFixedRate(new TimerTask() {
        synchronized public void run() {
          mainHandler.post(new Runnable() {
            public void run() {
              if(playerSimple != null && playerSimple.get() != null) {
                Long curPosition = playerSimple.get().getCurrentPosition();
                Long offset = playerSimple.get().getCurrentLiveOffset();
                Log.i("PPPP",String.valueOf(offset));
                MMSmartStreamingExo2.getInstance()
                    .reportPlaybackPosition(curPosition);
                if(analyticsBridge != null){
                  analyticsBridge.reportPlaybackPosition((double)curPosition);
                }
              }
            }
          });

        }
      }, 500, 500);
    }
  }
  /**
   * Reports the current player state to analytics.
   * @param playWhenReady Boolean indicating that the player should start playing media when it
   *                      is ready (has enough media to play)
   * @param exoPlayerState Target state to which player transitions to from current state
   * @see MMPlayerState
   */
  public void reportPlayerState(boolean playWhenReady, Integer exoPlayerState){
    if(logStackTrace){
      Log.v("SSSS", "reportPlayerState - < " + Boolean.toString(playWhenReady) + ", " + exoPlayerState + " >");
    }
    switch (exoPlayerState){
      case Player.STATE_IDLE:
        break;
      case Player.STATE_BUFFERING:{
        MMSmartStreaming.getInstance().reportBufferingStarted();
        sendBufferingCompletionOnReady = true;
      }
      break;
      case Player.STATE_READY:{
        startPlaybackPolling();
        if (sendBufferingCompletionOnReady){
          MMSmartStreaming.getInstance().reportBufferingCompleted();
          sendBufferingCompletionOnReady = false;
        }
        if (playWhenReady){
          MMSmartStreaming.getInstance().reportPlayerState(MMPlayerState.PLAYING);
        }else{
          MMSmartStreaming.getInstance().reportPlayerState(MMPlayerState.PAUSED);
        }
      }
      break;
      case Player.STATE_ENDED:{
        MMSmartStreaming.getInstance().reportPlayerState(MMPlayerState.STOPPED);
        stopPlaybackPolling();
      }
      break;
    }
  }

  /**
   * Reports the ABR bitrate changes to the analytics. This API should be called when neither
   * getQBRChunk nor reportChunkRequest is called by the player.
   * @param prevBitrate Previous ABR bitrate in bits per second.
   * @param newBitrate New ABR bitrate in pers per second.
   */
  public void reportABRSwitch(Integer prevBitrate, Integer newBitrate){
    MMSmartStreaming.getInstance().reportABRSwitch(prevBitrate, newBitrate);
  }

  /**
   * Reports cumulative frame loss count to analytics.
   * @param lossCnt Cumulative count of frames lost in playback session.
   */
  public void reportFrameLoss(Integer lossCnt){
    if(logStackTrace){
      Log.v(StackTraceLogTag, "reportFrameLoss - " + lossCnt);
    }
    cumulativeFramesDropped += lossCnt;
    MMSmartStreaming.getInstance().reportFrameLoss(cumulativeFramesDropped);
  }

  /**
   * Reports an error encountered during playback.
   * @param error Error encountered during playback session.
   * @param playbackPosMilliSec Playback position in millisec when error occurred.
   */
  public void reportError(String error, Long playbackPosMilliSec){
    MMSmartStreaming.getInstance().reportError(error, playbackPosMilliSec);
  }

  public void reportAppError(long errorCode,String errorMessage){

  }

  /**
   * Reports that a seek event is complete, with the new playback starting position.
   * @param seekEndPos Playback position(in milliseconds) when seek completed. This is point from
   *                   which playback will start after the seek.
   */
  public void reportPlayerSeekCompleted(Long seekEndPos){
    MMSmartStreaming.getInstance().reportPlayerSeekCompleted(seekEndPos);
  }

  /**
   * Reports the WiFi Service Set Identifier (SSID).
   * @param ssid WiFi Service Set Identifier (SSID).
   */
  public void reportWifiSSID(String ssid){
    MMSmartStreaming.getInstance().reportWifiSSID(ssid);
  }

  /**
   * Reports the WiFi signal strength. This may be useful, if someone is analyzing a
   * back playback session using smartsight's microscope feature, and wants to know if Wifi signal
   * strength is the cause fo poor performance of that session. This API is relevant if Wifi is used
   * for the playback session.
   *
   * @param strength Strength of Wifi signal in %
   */
  public void reportWifiSignalStrengthPercentage(Double strength){
    MMSmartStreaming.getInstance().reportWifiSignalStrengthPercentage(strength);
  }

  /**
   * Reports the WiFi maximum data rate.
   * @param dataRate WiFi data rate (in kbps)
   */
  public void reportWifiDataRate(Integer dataRate){
    MMSmartStreaming.getInstance().reportWifiDataRate(dataRate);
  }

  /**
   * Reports advertisement playback state
   * @param adState State of the advertisement
   * @see MMAdState
   */
  public void reportAdState(MMAdState adState){
    MMSmartStreaming.getInstance().reportAdState(adState);
  }

  /**
   * Reports advertisement-related information
   *
   * @param adClient Client used to play the ad, eg: VAST
   * @param adURL Tag represented by the ad (AD ID).
   * @param adDuration Length of the video ad (in milliseconds).
   * @param adPosition Position of the ad in the video  playback; one of "pre", "post" or "mid"
   *                   that represents that the ad played before, after or during playback respectively.
   * @param adType Type of the ad (linear, non-linear etc).
   * @param adCreativeType Ad MIME type
   * @param adServer Ad server (ex. DoubleClick, YuMe, AdTech, Brightroll, etc.)
   * @param adResolution Advertisement video resolution
   * @param adPodIndex Position of the ad within the ad group (Index should start from 1)
   * @param adPositionInPod Position of the ad within the pod
   * @param adPodLength Total number of ads in the ad group
   * @param isBumper True if bumper Ad else false
   * @param adScheduledTime The content time offset at which the current ad pod was scheduled
   */
  public void reportAdInfo(String adClient, String adURL, Long adDuration, String adPosition, MMAdType adType, String adCreativeType, String adServer,String adResolution, int adPodIndex, int adPositionInPod, int adPodLength, boolean isBumper, double adScheduledTime,String adCreativeId, String adUrl, String adTitle, int adBitrate,Long timestamp){
    MMSmartStreaming.getInstance().reportAdInfo(adClient, adURL, adDuration, adPosition, adType, adCreativeType, adServer, adResolution, adPodIndex,adPositionInPod, adPodLength, isBumper, adScheduledTime,adCreativeId,adURL,adTitle,adBitrate,timestamp);
  }

  /**
   * Reports advertisement-related information
   * @param adInfo
   */
  public void reportAdInfo(MMAdInfo adInfo){
    MMSmartStreaming.getInstance().reportAdInfo(adInfo);
  }

  /**
   * Reports current advertisement playback position
   * @param playbackPosition Current playback position in the Ad (in milliseconds)
   */
  public void reportAdPlaybackTime(Long playbackPosition){
    MMSmartStreaming.getInstance().reportAdPlaybackTime(playbackPosition);
  }

  /**
   * Reports error encountered during the advertisement playback
   * @param error Error encountered during advertisement playback
   * @param pos Playback position (in milliseconds) where error occurred
   */
  public void reportAdError(String error, Long pos){
    MMSmartStreaming.getInstance().reportAdError(error, pos);
  }
  /**
   * Reports Buffering start during the advertisement playback
   */
  public void reportAdBufferingStarted(){
    MMSmartStreaming.getInstance().reportAdBufferingStarted();
  }
  /**
   * Reports Buffering completion during the advertisement playback
   */
  public void reportAdBufferingCompleted(){
    MMSmartStreaming.getInstance().reportAdBufferingCompleted();
  }

  /**
   * Enables/Disables console logs for the SDK methods. This is to help in debugging and testing
   * of the player to SDK integration.
   * @param logStTrace True to enable console logs; false to disable console logs.
   */
  public static void enableLogTrace(boolean logStTrace){
    logStackTrace = logStTrace;
    MMSmartStreaming.enableLogTrace(logStTrace);
  }




  boolean playbackPollingStarted = false;
  private Timer timer;
  private Handler mainHandler;
  WeakReference<MMSmartStreamingObserver> obs = null;
  private boolean sendBufferingCompletionOnReady = false;
  private WeakReference<Context> ctx;
  public Context applicationContext;
  private int cumulativeFramesDropped = 0;
  private MMSmartStreamingExo2(){}
  private static boolean logStackTrace = false;
  private static String StackTraceLogTag = "MMSmartStreamingIntgr";
  private static MMSmartStreamingExo2 myObj;

}

