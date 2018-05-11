package com.koolearn.klibrary.core.options;

import com.koolearn.android.util.LogUtils;

public final class ZLStringOption extends ZLOption {
	public ZLStringOption(String group, String optionName, String defaultValue) {
		super(group, optionName, defaultValue);
	}

	public String getValue() {
		if (mySpecialName != null && !Config.Instance().isInitialized()) {
			return Config.Instance().getSpecialStringValue(mySpecialName, myDefaultStringValue);
		} else {
			return getConfigValue();
		}
	}

	public void setValue(String value) {
		LogUtils.i6("setValue");

		if (value == null) {
			return;
		}
		if (mySpecialName != null) {
			LogUtils.i6("setValue");
			Config.Instance().setSpecialStringValue(mySpecialName, value);
		}
		LogUtils.i6("setValue");

		setConfigValue(value);
	}

	public void saveSpecialValue() {
		if (mySpecialName != null && Config.Instance().isInitialized()) {
			Config.Instance().setSpecialStringValue(mySpecialName, getValue());
		}
	}
}
