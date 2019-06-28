package com.cisco.japan.as.uran.payloaddecoder.GlobalSat.lt501h;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.cisco.japan.as.uran.payloaddecoder.GlobalSat.lt501h.Lt501hPayloadDecoder;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/** テスト用クラス */
public class Lt501hPayloadDecoderTest extends TestCase {

	public Lt501hPayloadDecoderTest(String testName) {
		super(testName);
	}
	
    /**
     * @return the suite of tests being tested
     */
    public static TestSuite suite()
    {
        return new TestSuite( Lt501hPayloadDecoderTest.class );
    }

	public  void testMain()  throws Exception {
		Lt501hPayloadDecoder target = new Lt501hPayloadDecoder();

		// 現在日時を取得 リスト
		Date date = new Date();

		// 日時,デコード用文字列(payload_hex),DeviceIdentifyerを入力
		EncodedPayload test1 = new EncodedPayload(date, "0c1002dbc43d0763737d012264A4989659", "test1");
		EncodedPayload test2 = new EncodedPayload(date, "0c100295967e08ffb09a02025a5813825c", "test2");
//		EncodedPayload test3 = new EncodedPayload(date, "0c130272cb10575ab647568f3e113c29eda2de0000000022bfc55a",
//				"test3");
//		EncodedPayload test4 = new EncodedPayload(date, "0c070072cb10575ab647568f3e113c29eda2de0000000021b3c55f",
//				"test4");

		// リストに追加していく
		List<EncodedPayload> str = new ArrayList<EncodedPayload>();
		str.add(test1);
		str.add(test2);
//		str.add(test3);
//		str.add(test4);

		// encodeを呼び出す(Lt501hPayloadEncoderと同じ処理)
		List<DecodedPayload> decodeInfoList = target.decode(str);

		// 結果をリスト分出力
		for (DecodedPayload decodeInfo : decodeInfoList) {
			System.out.println("--------start--------");
			System.out.println("PayloadString:" + decodeInfo.getPayloadString());
			System.out.println("Time:" + decodeInfo.getTime());
			System.out.println("PayloadJson:" + decodeInfo.getPayloadJson());
			System.out.println("PayloadTime:" + decodeInfo.getPayloadTime());
			System.out.println("DeviceIdentifyer:" + decodeInfo.getDeviceIdentifiyer());
			System.out.println("--------end--------");
		}

	}
}
