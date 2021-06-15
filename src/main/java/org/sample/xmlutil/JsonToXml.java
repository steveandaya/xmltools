package org.sample.xmlutil;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.InputSource;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

import static java.lang.System.out;

public class JsonToXml {
    public static void main(String[] args) throws JSONException {
        //Convert JSON to XML
        if (args.length != 2) {
            out.println("Usage : XmlToJson <file-name.xml> <file-name.json>");
        } else {
            BufferedReader br = null;
            BufferedWriter out = null;
            StringBuilder sb = null;
            try {
                br = new BufferedReader(new FileReader(args[0]));
                out = new BufferedWriter(new FileWriter(args[1]));

                sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String jsonString = sb.toString();

                String sourcexml = convert(jsonString, "root"); // This method converts json object to xml string
                String xml = prettyPrintXml(sourcexml);
                out.write(xml);

            } catch (FileNotFoundException fe) {
                System.out.println(fe.toString());
            } catch (IOException ioe) {
                System.out.println(ioe.toString());
            } catch (JSONException je) {
                System.out.println(je.toString());
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException ioe) {
                    System.out.println(ioe.toString());
                }
            }
        }
    }
    public static String convert(String json, String root) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n" + XML.toString(jsonObject) ;
        return xml;
    }
    public static String prettyPrintXml(String sourceXml) {
        try {
            Transformer serializer = SAXTransformerFactory.newInstance().newTransformer();

            serializer.setOutputProperty(OutputKeys.INDENT, "yes");

            // serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            // serializer.setOutputProperty("{http://xml.customer.org/xslt}indent-amount", "2");
            Source xmlSource = new SAXSource(new InputSource(new ByteArrayInputStream(sourceXml.getBytes())));
            StreamResult res = new StreamResult(new ByteArrayOutputStream());

            serializer.transform(xmlSource, res);

            return new String(((ByteArrayOutputStream) res.getOutputStream()).toByteArray());
        } catch (Exception e) {
            // TODO log error
            return sourceXml;
        }
    }
}
