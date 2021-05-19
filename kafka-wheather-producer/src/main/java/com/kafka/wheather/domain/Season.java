package com.kafka.wheather.domain;

public enum Season {
WINTER("WINTER"),SUMMER("SUMMER"),SPRING("SPRING");
	 private final String name;       

	    private Season(String s) {
	        name = s;
	    }

	   

	    public String toString() {
	       return this.name;
	    }
}
	
