package com.company.infor.writer;

import com.company.infor.model.ClientInfo;

import java.util.List;

public class OutputBuilder {

    private static final String START_END_ROW = "</th>\n<th>";

    public String toString(List<ClientInfo> clientInfoList) {
        var sb = new StringBuilder();
        sb.append("cust_no\tactivated\temail_optout\tphone_optout\tfax_optout\tdate_created\tdate_modified\n");
        clientInfoList.forEach( clientInfo -> sb.append(clientInfo.toString()).append("\n"));
        return sb.toString();
    }

    public String toHTML(List<ClientInfo> clientInfoList) {
            var sb = new StringBuilder();
            sb.append("<!DOCTYPE html>\n");
            sb.append("<html>\n");
            sb.append("<body>\n");
            sb.append("<table style=\"width:100%\">\n");
            sb.append("<tr>\n" +
                    "<th>cust_no</th>\n" +
                    "<th>activated</th>\n" +
                    "<th>email_optout</th>\n" +
                    "<th>phone_optout</th>\n" +
                    "<th>fax_optout</th>\n" +
                    "<th>date_created</th>\n" +
                    "<th>date_modified</th>\n" +
                    "<tr>\n");
            for (ClientInfo clientInfo : clientInfoList) {
                sb.append("<tr>\n<th>");
                sb.append(clientInfo.getClientID());
                sb.append(START_END_ROW);
                sb.append(clientInfo.getActivated());
                sb.append(START_END_ROW);
                sb.append(clientInfo.getEmail_optout());
                sb.append(START_END_ROW);
                sb.append(clientInfo.getPhone_optout());
                sb.append(START_END_ROW);
                sb.append(clientInfo.getFax_optout());
                sb.append(START_END_ROW);
                sb.append(clientInfo.getDataCreated());
                sb.append(START_END_ROW);
                sb.append(clientInfo.getDataModified());
                sb.append("</th>\n</tr>\n");
            }
            sb.append("<table>");
            sb.append("</body>\n");
            sb.append("</html>");
            return sb.toString();
    }
}
