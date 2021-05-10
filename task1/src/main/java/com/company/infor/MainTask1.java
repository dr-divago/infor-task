package com.company.infor;


import com.company.infor.model.ClientInfo;
import com.company.infor.parser.Parser;
import com.company.infor.writer.OutputBuilder;

import java.util.Collections;
import java.util.List;

public class MainTask1 {

    private static String path;
    private static boolean toHTML;

    public static void main(String...arg) {
        parseCommandLine(arg);

        var parser = new Parser();
        var clientInfoList = parser.parse(path);
        Collections.sort(clientInfoList);
        var outputBuilder = new OutputBuilder();
        if (toHTML) {
            System.out.println(outputBuilder.toHTML(clientInfoList));
        }
        else {
            System.out.println(outputBuilder.toString(clientInfoList));
        }
    }

    private static void parseCommandLine(String... arg) {
        if (arg.length == 0) {
            System.exit(-1);
        }
        if (arg.length == 2 && arg[1].equals("true")) {
            toHTML = true;
        }

        path = arg[0];
    }
}
