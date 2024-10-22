/*
 *  Disk information class for JNI
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 *
 *  TODO not currently implemented
 */
package com.project.cs4421.project;


public class diskInfo 
{
     public native int diskCount ();
     public native String getModel ();
}

