package com.company.infor.parser;

import com.company.infor.model.Movie;
import com.company.infor.model.Root;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Optional;

public class XmlParser{

    public Optional<Movie> parse(String xml) throws JAXBException {
        var is = new InputSource(new StringReader(xml));
        var jaxbContext = JAXBContext.newInstance(Root.class);
        var jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        var root = (Root) jaxbUnmarshaller.unmarshal(is);
        if (root == null)
            return Optional.empty();
        return root.getMovieInfo();
    }
}
