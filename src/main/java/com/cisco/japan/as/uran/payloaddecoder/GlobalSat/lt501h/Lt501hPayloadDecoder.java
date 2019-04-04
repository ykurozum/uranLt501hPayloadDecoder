package com.cisco.japan.as.uran.payloaddecoder.GlobalSat.lt501h;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cisco.japan.as.uran.payloaddecoder.bean.HelpRepaortBean;
import com.cisco.japan.as.uran.payloaddecoder.bean.TrackingReportBean;
import com.cisco.japan.as.uran.payloaddecoder.constants.NodeElements;
import com.cisco.japan.as.uran.payloaddecoder.constants.ProtocolSummary;
import com.cisco.japan.as.uran.payloaddecoder.constants.UnknownStatus;
import com.cisco.japan.as.uran.payloaddecoder.summary.BeaconHelpReport;
import com.cisco.japan.as.uran.payloaddecoder.summary.BeaconTrackingReport;
import com.cisco.japan.as.uran.payloaddecoder.summary.HelpReport;
import com.cisco.japan.as.uran.payloaddecoder.summary.HelpReportShort;
import com.cisco.japan.as.uran.payloaddecoder.summary.TrackingReport;
import com.cisco.japan.as.uran.payloaddecoder.summary.TrackingReportShort;
import com.cisco.japan.as.uran.payloaddecoder.util.CommonUtils;
import com.cisco.japan.as.uran.payloaddecoder.DecodedPayload;
import com.cisco.japan.as.uran.payloaddecoder.EncodedPayload;
import com.cisco.japan.as.uran.payloaddecoder.PayloadDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Lt501hPayloadDecoder implements PayloadDecoder {

	/**
	 * Lt501のPayload変換処理
	 * 
	 * @param encodedPayloadList 変換対象のリスト
	 * @return decodeInfoList デコード情報のリスト
	 */
	public List<DecodedPayload> encode(List<EncodedPayload> encodedPayloadList) throws Exception {

		// ObjectMapperを作成
		ObjectMapper mapper = new ObjectMapper();

		List<DecodedPayload> decodeInfoList = new ArrayList<DecodedPayload>();
		DecodedPayload decodeInfo;

		for (EncodedPayload payload : encodedPayloadList) {

			ObjectNode payloadObject = mapper.createObjectNode();

			// payloadStringからpayload_hexを取得
			String hexStr = payload.getPayloadString();

			// payload存在チェック
			if (hexStr != null && !hexStr.isEmpty()) {
				if (hexStr.startsWith(ProtocolSummary.TRACKING_REPORT.getCode())) { // protocol:0c1002

					// TrackingReport:peyload_hexを変換
					TrackingReportBean trBean = new TrackingReportBean();
					TrackingReport.decodeTrackingReport(payloadObject, hexStr, trBean);
					decodeInfo = makeDecodeInfo(payloadObject, payload, trBean.getDateTime());

				} else if (hexStr.startsWith(ProtocolSummary.TRACKING_REPORT_S.getCode())) { // protocol:8083

					// TrackingRport(short):peyload_hexを変換
					TrackingReportShort.decodeTrackingReportShort(payloadObject, hexStr);
					decodeInfo = makeDecodeInfo(payloadObject, payload, null);

				} else if (hexStr.startsWith(ProtocolSummary.HELP_REPORT.getCode())) { // protocol:0c0b00

					// HelpReport:peyload_hexを変換
					HelpRepaortBean hrBean = new HelpRepaortBean();
					HelpReport.decodeHelpReport(payloadObject, hexStr, hrBean);
					decodeInfo = makeDecodeInfo(payloadObject, payload, hrBean.getDateTime());

				} else if (hexStr.startsWith(ProtocolSummary.HELP_REPORT_S.getCode())) { // protocol:8001

					// HelpReport(short):peyload_hexを変換
					HelpReportShort.decodeHelpReportShort(payloadObject, hexStr);
					decodeInfo = makeDecodeInfo(payloadObject, payload, null);

				} else if (hexStr.startsWith(ProtocolSummary.BEACON_REPORT_T.getCode())) { // protocol:0c1302

					// BeaconTrackingReport:peyload_hexを変換
					BeaconTrackingReport.decodeBeaconTrackingReportd(payloadObject, hexStr);
					decodeInfo = makeDecodeInfo(payloadObject, payload, null);

				} else if (hexStr.startsWith(ProtocolSummary.BEACON_REPORT_H.getCode())) { // protocol:0c0700

					// BeaconHelpReport:peyload_hexを変換
					BeaconHelpReport.decodeBeaconHelpReport(payloadObject, hexStr);
					decodeInfo = makeDecodeInfo(payloadObject, payload, null);

				} else { // unknwonProtocol

					CommonUtils.packingJson(payloadObject, NodeElements.PROTOCOL.getCode(),
							UnknownStatus.UNKNOWN_PROTOCOL.getCode());
					decodeInfo = makeDecodeInfo(payloadObject, payload, null);
				}
			} else { // hexStr = null
				CommonUtils.packingJson(payloadObject, NodeElements.ERROR.getCode(),
						UnknownStatus.UNKNOWN_FORMAT.getCode());
				decodeInfo = makeDecodeInfo(payloadObject, payload, null);
			}
			decodeInfoList.add(decodeInfo);

		}

		return decodeInfoList;
	}

	/**
	 * デコード情報を詰め込む
	 * 
	 * @param payloadObject Jsonの中身
	 * @param payload       Link_upTime&Payload_Hex
	 * @param dateTime      Payload内の日時
	 * @return decodeInfo デコード情報
	 */
	private static DecodedPayload makeDecodeInfo(ObjectNode payloadObject, EncodedPayload payload,
			Date payloadDateTime) {

		DecodedPayload decodeInfo = new DecodedPayload(payload.getTime(), payloadDateTime, payload.getPayloadString(),
				payloadObject, payload.getDeviceIdentifiyer());

		return decodeInfo;

	}
}
