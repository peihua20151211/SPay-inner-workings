package com.samsung.android.spayfw.payprovider.plcc.tzsvc;

import com.samsung.android.spaytzsvc.api.TAInfo;

public class PlccTAInfo extends TAInfo {
    public static final String CONFIG_LSI_PATH = "ffffffff000000000000000000000027.mp3";
    public static final String CONFIG_LSI_UUID = "ffffffff000000000000000000000027";
    public static final String CONFIG_QC_PATH = "plcc_pay.mp3";
    public static final String CONFIG_QC_PROCESS = "plcc_pay";
    public static final String CONFIG_QC_ROOT = "/firmware/image";
    public static final int SPAY_TA_TYPE_TEE_PLCC = 4;
    public static final boolean bUsesPinRandom = true;
    public static final PlccCommands mCommands;

    static {
        mCommands = new PlccCommands();
    }

    public PlccTAInfo() {
        super((int) SPAY_TA_TYPE_TEE_PLCC, TAInfo.SPAY_TA_TECH_TEE, PlccTAController.class, mCommands, CONFIG_LSI_UUID, CONFIG_LSI_PATH, CONFIG_QC_ROOT, CONFIG_QC_PROCESS, CONFIG_QC_PATH, (boolean) bUsesPinRandom);
    }
}
