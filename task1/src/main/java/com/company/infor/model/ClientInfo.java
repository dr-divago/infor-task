package com.company.infor.model;

import com.company.infor.util.DateUtils;

public class ClientInfo implements Comparable<ClientInfo>{

    private String clientID;
    private String activated;
    private String email_optout;
    private String phone_optout;
    private String fax_optout;
    private String dataCreated;
    private String dataModified;

    public ClientInfo(String clientID,
                      String activated,
                      String email_optout,
                      String phone_optout,
                      String fax_optout,
                      String dataCreated) {

        this.clientID = replace(clientID);
        this.activated = activated;
        this.email_optout = email_optout;
        this.phone_optout = phone_optout;
        this.fax_optout = fax_optout;
        this.dataCreated = DateUtils.updateToUTC(dataCreated);
        this.dataModified = DateUtils.updateToCurrentDate();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append(clientID)
                .append("\t")
                .append(activated)
                .append("\t")
                .append(email_optout)
                .append("\t")
                .append(phone_optout)
                .append("\t")
                .append(fax_optout)
                .append("\t")
                .append(dataCreated)
                .append("\t")
                .append(dataModified);

        return sb.toString();
    }


    public String getClientID() {
        return clientID;
    }

    public String getActivated() {
        return activated;
    }

    public String getEmail_optout() {
        return email_optout;
    }

    public String getPhone_optout() {
        return phone_optout;
    }

    public String getFax_optout() {
        return fax_optout;
    }

    public String getDataCreated() {
        return dataCreated;
    }

    public String getDataModified() {
        return dataModified;
    }

    private String replace(String s) {
        if (s.startsWith("\""))
            return s.substring(1);
        if (!s.startsWith("C"))
            return s.replaceFirst(String.valueOf(s.charAt(0)), "C");

        return s;
    }

    @Override
    public int compareTo(ClientInfo o) {
        return Integer.compare(extractID(), o.extractID());
    }

    public int extractID() {
        char[] c = clientID.toCharArray();
        var i = 0;
        while (!Character.isDigit(c[i])) { i++;}
         try {
            return Integer.parseInt(clientID.substring(i));
        } catch (NumberFormatException e) {
             System.out.println(clientID.substring(i));
         }
         return -1;
    }
}
