package com.ritiesh.aidocumentassitance.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ChunkService{
	
	public List<String> splitText(String text){
		
		int chunkSize = 500;
		
		List<String> chunk = new ArrayList<String>();
		
		for(int i=0;i< text.length();i+=chunkSize) {
			
			chunk.add(text.substring(i, Math.min(text.length(), i+chunkSize)));
		}
		
		return chunk;
	}
	
}