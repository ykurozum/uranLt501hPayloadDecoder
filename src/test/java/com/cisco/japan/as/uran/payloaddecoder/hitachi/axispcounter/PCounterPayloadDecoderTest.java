package com.cisco.japan.as.uran.payloaddecoder.hitachi.axispcounter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cisco.japan.as.uran.payloaddecoder.hitachi.axispcounter.AxisPCounterPayloadDecoder;
import com.cisco.japan.as.uran.payloaddecoder.DecodedPayload;
import com.cisco.japan.as.uran.payloaddecoder.EncodedPayload;

/** テスト用クラス */
public class PCounterPayloadDecoderTest extends TestCase {

	public PCounterPayloadDecoderTest(String testName) {
		super(testName);
	}
	
    /**
     * @return the suite of tests being tested
     */
    public static TestSuite suite()
    {
        return new TestSuite( PCounterPayloadDecoderTest.class );
    }

	public  void testMain()  throws Exception {
		AxisPCounterPayloadDecoder target = new AxisPCounterPayloadDecoder();

		// 現在日時を取得 リスト
		Date date = new Date();
		
		String data = "{"+
				  "\"serial\":\"ACCC8ED87AC2\","+
				  "\"name\":\"Axis-ACCC8ED87AC2\","+
				  "\"timestamp\":\"20200219105050\","+
				  "\"in\":2,"+
				  "\"out\":1"+
				"}";

		// 日時,デコード用文字列(payload_hex),DeviceIdentifyerを入力
		EncodedPayload test1 = new EncodedPayload(date, data, "test1");
		System.out.println( data );
//		EncodedPayload test2 = new EncodedPayload(date, "0c100295967e08ffb09a02025a5813825c", "test2");
//		EncodedPayload test3 = new EncodedPayload(date, "0c130272cb10575ab647568f3e113c29eda2de0000000022bfc55a",
//				"test3");
//		EncodedPayload test4 = new EncodedPayload(date, "0c070072cb10575ab647568f3e113c29eda2de0000000021b3c55f",
//				"test4");

		// リストに追加していく
		List<EncodedPayload> list = new ArrayList<EncodedPayload>();
		list.add(test1);
//		str.add(test2);
//		str.add(test3);
//		str.add(test4);

		// encodeを呼び出す(Lt501hPayloadEncoderと同じ処理)
		List<DecodedPayload> decodeInfoList = target.decode(list);


		// 結果をリスト分出力
		for (DecodedPayload decodeInfo : decodeInfoList) {
			System.out.println("--------start--------");
			System.out.println( decodeInfo.getPayloadJson() );
			System.out.println("--------end--------");
		}

	}
}
