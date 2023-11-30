/**
 * Created by shri on 20/5/15.
 */
package com.mediamelon.qubit.ep;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class StreamInfo implements JSONAble {
    public String maxRes;
    public String minRes;
    public Double maxFps;
    public Double minFps;
    public Integer numOfProfile;
    public Double totalDuration;
    public String streamFormat;
    public String audioTrack;
    public  String subtitleTrack;
    public boolean isSubtitleActive;
    public boolean isVDSActive;
    public String videoTrack;

    @Override
    public String toString() {
        return "StreamInfo: { maxRes: "+maxRes+", minRes: "+minRes+", maxFps: "+maxFps+", minFps: "+
                minFps+", numOfProfile: "+numOfProfile+", totalDuration: "+totalDuration + "streamFormat:" +  streamFormat + "}";
    }
    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("maxRes", maxRes);
            jsonObject.put("minRes", minRes);
            jsonObject.put("maxFps", maxFps);
            jsonObject.put("minFps", minFps);
            jsonObject.put("numOfProfile", numOfProfile);
            jsonObject.put("totalDuration", totalDuration);
            jsonObject.put("streamFormat", streamFormat);
            jsonObject.put("audioTrack", audioTrack);
            jsonObject.put("subtitleTrack", subtitleTrack);
            jsonObject.put("videoTrack", videoTrack);

            jsonObject.put("isSubtitleActive", isSubtitleActive);
            jsonObject.put("isVDSActive", isVDSActive);


        } catch (JSONException e) {

        }
        return jsonObject;
    }
}
