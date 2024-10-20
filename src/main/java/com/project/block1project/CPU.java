package com.project.block1project;

import com.project.cs4421.project.cpuInfo;

public class CPU {

    public static void main(String[] args){

        //Initialise the CPU Info object
        cpuInfo cpuMethods = new cpuInfo();

        int cps = cpuMethods.coresPerSocket();
        System.out.printf("Number of cores per socket: %d", cps);
    }

}
