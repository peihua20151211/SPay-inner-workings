package android.os;

import android.os.IBatteryPropertiesRegistrar.Stub;
import com.android.internal.app.IBatteryStats;

public class BatteryManager {
    public static final String ACTION_CHARGING = "android.os.action.CHARGING";
    public static final String ACTION_DISCHARGING = "android.os.action.DISCHARGING";
    public static final String ACTION_SEC_BATTERY_EVENT = "com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT";
    public static final String ACTION_SEC_BATTERY_WATER_IN_CONNECTOR = "com.samsung.server.BatteryService.action.SEC_BATTERY_WATER_IN_CONNECTOR";
    public static final int BATTERY_HEALTH_COLD = 7;
    public static final int BATTERY_HEALTH_DEAD = 4;
    public static final int BATTERY_HEALTH_GOOD = 2;
    public static final int BATTERY_HEALTH_OVERHEAT = 3;
    public static final int BATTERY_HEALTH_OVER_VOLTAGE = 5;
    public static final int BATTERY_HEALTH_UNKNOWN = 1;
    public static final int BATTERY_HEALTH_UNSPECIFIED_FAILURE = 6;
    public static final int BATTERY_ONLINE_FAST_WIRELESS_CHARGER = 100;
    public static final int BATTERY_ONLINE_INCOMPATIBLE_CHARGER = 0;
    public static final int BATTERY_ONLINE_NONE = 1;
    public static final int BATTERY_ONLINE_POGO = 23;
    public static final int BATTERY_ONLINE_TA = 3;
    public static final int BATTERY_ONLINE_USB = 4;
    public static final int BATTERY_ONLINE_WATER_IN_CONNECTOR = 101;
    public static final int BATTERY_ONLINE_WIRELESS_CHARGER = 10;
    public static final int BATTERY_PLUGGED_AC = 1;
    public static final int BATTERY_PLUGGED_ANY = 7;
    public static final int BATTERY_PLUGGED_USB = 2;
    public static final int BATTERY_PLUGGED_WIRELESS = 4;
    public static final int BATTERY_PROPERTY_CAPACITY = 4;
    public static final int BATTERY_PROPERTY_CHARGE_COUNTER = 1;
    public static final int BATTERY_PROPERTY_CURRENT_AVERAGE = 3;
    public static final int BATTERY_PROPERTY_CURRENT_NOW = 2;
    public static final int BATTERY_PROPERTY_ENERGY_COUNTER = 5;
    public static final int BATTERY_SEC_EVENT_WATER_IN_CONNECTOR = 1;
    public static final int BATTERY_STATUS_CHARGING = 2;
    public static final int BATTERY_STATUS_DISCHARGING = 3;
    public static final int BATTERY_STATUS_FULL = 5;
    public static final int BATTERY_STATUS_NOT_CHARGING = 4;
    public static final int BATTERY_STATUS_UNKNOWN = 1;
    public static final String EXTRA_CAPACITY = "capacity";
    public static final String EXTRA_CHARGE_TYPE = "charge_type";
    public static final String EXTRA_CURRENT_AVG = "current_avg";
    public static final String EXTRA_CURRENT_NOW = "current_now";
    public static final String EXTRA_HEALTH = "health";
    public static final String EXTRA_HIGHVOLTAGE_CHARGER = "hv_charger";
    public static final String EXTRA_ICON_SMALL = "icon-small";
    public static final String EXTRA_INVALID_CHARGER = "invalid_charger";
    public static final String EXTRA_LEVEL = "level";
    public static final String EXTRA_MAX_CHARGING_CURRENT = "max_charging_current";
    public static final String EXTRA_ONLINE = "online";
    public static final String EXTRA_OTG_CHARGE_BLOCK_ENABLE = "OTG_CHARGE_BLOCK";
    public static final String EXTRA_PLUGGED = "plugged";
    public static final String EXTRA_POGO_CONDITION = "pogo_plugged";
    public static final String EXTRA_POWER_SHARING = "power_sharing";
    public static final String EXTRA_POWER_SHARING_ENABLE = "power_sharing_enable";
    public static final String EXTRA_PRESENT = "present";
    public static final String EXTRA_SCALE = "scale";
    public static final String EXTRA_SEC_PLUG_TYPE_SUMMARY = "sec_plug_type";
    public static final String EXTRA_SELF_DISCHARGING = "self_discharging";
    public static final String EXTRA_STATUS = "status";
    public static final String EXTRA_TECHNOLOGY = "technology";
    public static final String EXTRA_TEMPERATURE = "temperature";
    public static final String EXTRA_VOLTAGE = "voltage";
    public static final String EXTRA_WATER = "water";
    private final IBatteryPropertiesRegistrar mBatteryPropertiesRegistrar = Stub.asInterface(ServiceManager.getService("batteryproperties"));
    private final IBatteryStats mBatteryStats = IBatteryStats.Stub.asInterface(ServiceManager.getService(BatteryStats.SERVICE_NAME));

    public boolean isCharging() {
        try {
            return this.mBatteryStats.isCharging();
        } catch (RemoteException e) {
            return true;
        }
    }

    private long queryProperty(int id) {
        if (this.mBatteryPropertiesRegistrar == null) {
            return Long.MIN_VALUE;
        }
        try {
            BatteryProperty prop = new BatteryProperty();
            if (this.mBatteryPropertiesRegistrar.getProperty(id, prop) == 0) {
                return prop.getLong();
            }
            return Long.MIN_VALUE;
        } catch (RemoteException e) {
            return Long.MIN_VALUE;
        }
    }

    public int getIntProperty(int id) {
        return (int) queryProperty(id);
    }

    public long getLongProperty(int id) {
        return queryProperty(id);
    }
}
