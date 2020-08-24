package com.cos.opgg.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.cos.opgg.api.dto.InfoDto;
import com.cos.opgg.dto.RespDto;
import com.cos.opgg.dto.RespListDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestGson {
	
//	public static void main(String[] args) {
//		URL url;
//		try {
//			url = new URL("http://59.20.79.42:58002/test/info/name/hideonbush");
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
//
//			StringBuilder sb = new StringBuilder();
//
//			String input = "";
//			while ((input = br.readLine()) != null) {
//				sb.append(input);
//			}
//			
//			Gson gson = new Gson();
//			
//			Type type = new TypeToken<RespListDto<InfoDto>>() {}.getType();
//			
//			RespListDto<InfoDto> test = gson.fromJson(sb.toString(), type);
//			
//			System.out.println(test);
//			
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

}
