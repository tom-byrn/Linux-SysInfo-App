/*package com.project.block1project;
import javax.bluetooth.*;

public class Bluetooth {

    public static void main(String[] args) {
        try {
            // Get the local Bluetooth device
            LocalDevice localDevice = LocalDevice.getLocalDevice();
            System.out.println("Local Device Address: " + localDevice.getBluetoothAddress());
            System.out.println("Local Device Name: " + localDevice.getFriendlyName());

            // Discover nearby devices using GIAC
            DiscoveryAgent agent = localDevice.getDiscoveryAgent();
            agent.startInquiry(DiscoveryAgent.GIAC, new DiscoveryListener() {
                public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                    try {
                        System.out.println("Device found: " + btDevice.getBluetoothAddress() + " (" + btDevice.getFriendlyName(false) + ")");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                public void inquiryCompleted(int discType) {
                    System.out.println("Inquiry completed.");
                }

                // Implementing required method from DiscoveryListener
                public void serviceSearchCompleted(int transID, int respCode) {
                    // No service search performed, so this can be empty
                }

                // Implementing another required method
                public void servicesDiscovered(int transID, ServiceRecord[] records) {
                    // No service discovery performed, so this can be empty
                }
            });

            // Wait for the inquiry to complete
            synchronized (Bluetooth.class) {
                Bluetooth.class.wait();
            }
        } catch (BluetoothStateException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

*/
