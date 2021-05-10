package com.company.infor.parser;

import com.company.infor.model.ClientInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {

    public List<ClientInfo> parse(String fullpath) {
        var path = Paths.get(fullpath);

        try (Stream<String> stream = Files.lines(path)) {
            return stream
                    .map( line -> line.split("\t"))
                    .filter( l -> !l[0].equals("cust_no"))
                    .map(l -> new ClientInfo(l[0], l[1], l[2], l[3], l[4], l[5]))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
