package com.techkers.autoblackbox.utils;

public final class ForcaUtil {
	
	private static final int GRAVIDADE  = 9;
	
	private ForcaUtil() {
		//do nothing
	}

	public static int calcularForca(float x, float y, float z){
		int forca = (int)Math.sqrt((x*x + y*y + z*z));
		
		return forca - GRAVIDADE;
	}
}
