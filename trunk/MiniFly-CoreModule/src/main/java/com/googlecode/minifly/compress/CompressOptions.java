/*
 * Copyright 2011 Gerton ten Ham
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.googlecode.minifly.compress;

/**
 * @author gtenham
 *
 */
public class CompressOptions {
	
	private int lineBreakPos = -1;   //Insert a line break after the specified column number
    private boolean warn = false; //Display possible errors in the code
    private boolean munge = true; //Minify only, do not obfuscate
    private boolean preserveAllSemiColons = false; //Preserve unnecessary semicolons
    private boolean disableOptimizations = true; // Additional optimizations
    
	/**
	 * Setter lineBreakPos
	 * Insert a line break after the specified column number
	 * 
	 * @param lineBreakPos the lineBreakPos to set
	 */
	public void setLineBreakPos(int lineBreakPos) {
		this.lineBreakPos = lineBreakPos;
	}
	/**
	 * Getter lineBreakPos
	 * 
	 * @return the lineBreakPos
	 */
	public int getLineBreakPos() {
		return lineBreakPos;
	}
	/**
	 * Setter warn
	 * Display possible errors in the code
	 * 
	 * @param warn the warn to set
	 */
	public void setWarn(boolean warn) {
		this.warn = warn;
	}
	/**
	 * Getter warn
	 * 
	 * @return the warn
	 */
	public boolean isWarn() {
		return warn;
	}
	/**
	 * Setter munge
	 * Minify only, do not obfuscate
	 * 
	 * @param munge the munge to set
	 */
	public void setMunge(boolean munge) {
		this.munge = munge;
	}
	/**
	 * Getter munge
	 * 
	 * @return the munge
	 */
	public boolean isMunge() {
		return munge;
	}
	/**
	 * Setter preserveAllSemiColons
	 * Preserve unnecessary semicolons
	 * 
	 * @param preserveAllSemiColons the preserveAllSemiColons to set
	 */
	public void setPreserveAllSemiColons(boolean preserveAllSemiColons) {
		this.preserveAllSemiColons = preserveAllSemiColons;
	}
	/**
	 * Getter preserveAllSemiColons
	 * 
	 * @return the preserveAllSemiColons
	 */
	public boolean isPreserveAllSemiColons() {
		return preserveAllSemiColons;
	}
	/**
	 * Setter disableOptimizations
	 * Additional optimizations
	 * 
	 * @param disableOptimizations the disableOptimizations to set
	 */
	public void setDisableOptimizations(boolean disableOptimizations) {
		this.disableOptimizations = disableOptimizations;
	}
	/**
	 * Getter disableOptimizations
	 * 
	 * @return the disableOptimizations
	 */
	public boolean isDisableOptimizations() {
		return disableOptimizations;
	}
    
}
