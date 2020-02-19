package com.cisco.japan.as.uran.payloaddecoder.hitachi.axispcounter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cisco.japan.as.uran.payloaddecoder.DecodedPayload;
import com.cisco.japan.as.uran.payloaddecoder.EncodedPayload;
import com.cisco.japan.as.uran.payloaddecoder.PayloadDecoder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AxisPCounterPayloadDecoder implements PayloadDecoder {

	/**
	 * AXIS People counter のPayload変換処理
	 * 
	 * @param encodedPayloadList 変換対象のリスト
	 * @return decodeInfoList デコード情報のリスト
	 */
	public List<DecodedPayload> decode(List<EncodedPayload> encodedPayloadList) throws Exception {


		// ObjectMapperを作成
		ObjectMapper mapper = new ObjectMapper();

		List<DecodedPayload> decodeInfoList = new ArrayList<DecodedPayload>();
		EncodedPayload decodeInfo;

		for (EncodedPayload payload : encodedPayloadList) {

			// payloadStringからjsonStringを取得
			String jsonStr = payload.getPayloadString();
			
			// payload存在チェック
			if (jsonStr != null && !jsonStr.isEmpty()) {

				// TODO:  無変換で、JSONオブジェクトにする
				JsonNode liveSumJson = mapper.readTree(jsonStr);
				DecodedPayload ent = new DecodedPayload(new Date(), new Date(), jsonStr,  liveSumJson,
						"dev_id1");
				
				decodeInfoList.add(ent );
			}

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
	private static DecodedPayload makeDecodeInfo(ObjectNode payloadObject, EncodedPayload payload) {
//		DecodedPayload decodeInfo = new DecodedPayload(payload.getTime(), payload.getPayloadString(),
//		payloadObject, payload.getDeviceIdentifiyer());

		
		DecodedPayload decodeInfo = new DecodedPayload(payload.getTime(), payload.getTime(), payload.getPayloadString(),
				payloadObject, payload.getDeviceIdentifiyer());

		return decodeInfo;

	}
}
