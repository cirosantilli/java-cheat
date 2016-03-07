/*
# InetAddress

# NetworkInterface

    http://stackoverflow.com/questions/494465/how-to-enumerate-ip-addresses-of-all-enabled-nic-cards-from-java?lq=1
    http://stackoverflow.com/questions/8083479/java-getting-my-ip-address
*/

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class GetIps {
    public static void main(String[] args) {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("InetAddress.getLocalHost().getHostAddress = " + localhost.getHostAddress());
            // Just in case this host has multiple IP addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 1) {
                System.out.println("InetAddress.getAllByName(localhost.getCanonicalHostName())");
                for (InetAddress inetAddress : allMyIps) {
                    System.out.println("  " + inetAddress);
                }
            }
        } catch (UnknownHostException e) {
            System.out.println(e);
        }

        try {
            System.out.println("NetworkInterface.getNetworkInterfaces()");
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = en.nextElement();
                System.out.println("  getName() = " + intf.getName() + ", getDisplayName() = " + intf.getDisplayName());
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    System.out.println("    " + enumIpAddr.nextElement().toString());
                }
            }
        } catch (SocketException e) {
            System.out.println(e);
        }
    }
}
