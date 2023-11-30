/**
 * Created by shri on 20/5/15.
 */
package com.mediamelon.qubit.ep;

import com.mediamelon.smartstreaming.MMCellInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ClientInfo implements JSONAble {
    public String location;
    public Double latitude;
    public Double longitude;
    public MMCellInfo cellInfo;
    public String operator;
    public String cdn;
    public String nwType;
    public String platform;// Operating System name
    public String model;// model of the device
    public String brand;// brand of the device
    public String version;// Operating System version
    public String scrnRes;
    public String device; // possible values are "Personal computer", "Smartphone", "STB", "Smart TV", "Tablet" or "unknown"
    public String userAgent;
    public String wifissid;
    public String wifidatarate;
    public String signalstrength;
    public String appName;
    public String appVersion;
    public String deviceMarketingName;
    public String videoQuality;
    public String HdrCapbilities;
    //public String supportedAcodecs;
    //public String supportedVcodecs;
    public String widevine;
    public String drmVersion;
    public String systemId;

    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("location",location);
            jsonObject.put("latitude",latitude);
            jsonObject.put("longitude",longitude);
            jsonObject.put("cellInfo",cellInfo);
            jsonObject.put("operator",operator);
            jsonObject.put("cdn",cdn);
            jsonObject.put("nwType",nwType);
            jsonObject.put("platform",platform);
            jsonObject.put("model",model);
            jsonObject.put("brand",brand);
            jsonObject.put("version",version);
            jsonObject.put("scrnRes",scrnRes);
            jsonObject.put("device",device);
            jsonObject.put("userAgent",userAgent);
            jsonObject.put("wifissid",wifissid);
            jsonObject.put("wifidatarate",wifidatarate);
            jsonObject.put("signalstrength",signalstrength);
            jsonObject.put("appName",appName);
            jsonObject.put("appVersion",appVersion);
            jsonObject.put("deviceMarketingName",deviceMarketingName);
            jsonObject.put("videoQuality",videoQuality);
            jsonObject.put("hdrCapabilities",HdrCapbilities);
            //jsonObject.put("supportedHardwareAcodecs",supportedAcodecs);
            //jsonObject.put("supportedHardwareVcodecs",supportedVcodecs);
            jsonObject.put("widevine",widevine);
            jsonObject.put("drmVersion",drmVersion);
            jsonObject.put("systemId",systemId);

        } catch (JSONException e) {

        }
        return jsonObject;
    }
}





