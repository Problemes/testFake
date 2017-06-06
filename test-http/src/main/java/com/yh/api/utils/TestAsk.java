package com.yh.api.utils;

import com.yh.api.http.HttpUtil;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Admin on 2017/1/18.
 */
public class TestAsk {

    @Test
    public void testAskCardUpdate() throws IOException {

        String url = "http://api.730edu.net/restApi/v1/card/update";
        Long[] sids = {1000030l, 1000061l};
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cardType", "");
        jsonObject.put("linkedSids", sids);

        String result = HttpUtil.doPost(url, jsonObject);

        System.out.println(result);
    }
}

