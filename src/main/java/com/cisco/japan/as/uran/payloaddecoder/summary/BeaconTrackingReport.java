package com.cisco.japan.as.uran.payloaddecoder.summary;

import com.cisco.japan.as.uran.payloaddecoder.constants.BeaconId;
import com.cisco.japan.as.uran.payloaddecoder.constants.BeaconType;
import com.cisco.japan.as.uran.payloaddecoder.constants.NodeElements;
import com.cisco.japan.as.uran.payloaddecoder.constants.ProtocolSummary;
import com.cisco.japan.as.uran.payloaddecoder.constants.UnknownStatus;
import com.cisco.japan.as.uran.payloaddecoder.decoder.BatteryCapacityDecoder;
import com.cisco.japan.as.uran.payloaddecoder.decoder.BeaconIdDecoder;
import com.cisco.japan.as.uran.payloaddecoder.decoder.BeaconTypeDecoder;
import com.cisco.japan.as.uran.payloaddecoder.decoder.ReportTypeDecoder;
import com.cisco.japan.as.uran.payloaddecoder.decoder.RssiDecoder;
import com.cisco.japan.as.uran.payloaddecoder.decoder.TxPowerDecoder;
import com.cisco.japan.as.uran.payloaddecoder.util.CommonUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BeaconTrackingReport {

	/** protocol部のlength */
	private static final int PROTOCOL_LENGTH = 6;
	/** 最大長 */
	private static final int MAX_LENGTH = 54;

	/**
	 * BeaconReport：TrackingReportデコード処理
	 * 
	 * @param payloadObject JSONオブジェクト
	 * @param hexStr        デコード用文字列
	 */
	public static void decodeBeaconTrackingReportd(ObjectNode payloadObject, String hexStr) {

		// hexStrのlengthチェック
		if (CommonUtils.checkPayloadLength(hexStr, MAX_LENGTH)) {

			// protocol部以降を切りだし
			hexStr = hexStr.substring(PROTOCOL_LENGTH);

			// protocol詰め
			CommonUtils.packingJson(payloadObject, NodeElements.PROTOCOL.getCode(),
					ProtocolSummary.BEACON_REPORT_T.getCode());

			// BeaconType変換
			String beaconType = BeaconTypeDecoder.decodeBeaconType(hexStr.substring(40, 42));

			// BeaconID変換
			if (!BeaconType.BEACON_NOT_AVAILABLE.getTagName().equals(beaconType)
					&& !UnknownStatus.UNKNOWN_BEACON_TYPE.getCode().equals(beaconType)) {

				String[] beaconId = BeaconIdDecoder.decodeBeaconId(hexStr.substring(0, 40), beaconType);

				if (beaconType.equals(BeaconType.IBEACON.getTagName())) { // iBeacon
					CommonUtils.packingJson(payloadObject, BeaconId.IBEACON_UUID.getCode(), beaconId[0]);
					CommonUtils.packingJson(payloadObject, BeaconId.IBEACON_MAJOR_ID.getCode(), beaconId[1]);
					CommonUtils.packingJson(payloadObject, BeaconId.IBEACON_MINOR_ID.getCode(), beaconId[2]);
				} else if (beaconType.equals(BeaconType.EDDYSOTNE_BEACON.getTagName())) { // Eddystone Beacon
					CommonUtils.packingJson(payloadObject, BeaconId.EDDYSTONE_BEACON_ID.getCode(), beaconId[0]);
				} else { // ALTBeacon
					CommonUtils.packingJson(payloadObject, BeaconId.ALTBEACON_ID.getCode(), beaconId[0]);
				}
			}

			// BeaconType詰め込み
			CommonUtils.packingJson(payloadObject, NodeElements.BEACON_TYPE.getCode(), beaconType);

			// ReportType変換
			String reportType = ReportTypeDecoder.decodeReportType(hexStr.substring(40, 42));
			CommonUtils.packingJson(payloadObject, NodeElements.REPORT_TYPE.getCode(), reportType);

			// RSSI変換
			String rssi = RssiDecoder.decodeRssi(hexStr.substring(42, 44));
			CommonUtils.packingJson(payloadObject, NodeElements.RSSI.getCode(), rssi);

			// TxPower変換
			String txPower = TxPowerDecoder.decodeTxPower(hexStr.substring(44, 46));
			CommonUtils.packingJson(payloadObject, NodeElements.TXPOWER.getCode(), txPower);

			// BatteryCapacity変換
			String batteryCapacity = BatteryCapacityDecoder.decodeBatteryCapacity(hexStr.substring(46, 48));
			CommonUtils.packingJson(payloadObject, NodeElements.BATTERY_CAPACITY.getCode(), batteryCapacity);

		} else { // error

			CommonUtils.packingJson(payloadObject, NodeElements.ERROR.getCode(),
					UnknownStatus.UNKNOWN_FORMAT.getCode());

		}
	}
}
