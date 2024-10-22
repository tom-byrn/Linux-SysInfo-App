package com.project.block1project;

public class CPU {

    public static void main(String[] args){

        //Initialise the CPU Info object
        cpuInfo cpuMethods = new cpuInfo();

        int cps = cpuMethods.coresPerSocket();
        System.out.printf("Number of cores per socket: %d", cps);
    }

}
