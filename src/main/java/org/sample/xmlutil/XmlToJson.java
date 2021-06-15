package org.sample.xmlutil;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;

import static java.lang.System.out;

public class XmlToJson {
    public static String xmlString= "<?xml version=\"1.0\" ?><test       attrib=\"jsontext1\">tutorialspoint</test><test attrib=\"jsontext2\">tutorix</test>";
    public static void main(String[] args) {
        if(args.length !=2){
            out.println("Usage : XmlToJson <file-name.xml> <file-name.json>" );
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
                String xmlString = sb.toString();

                JSONObject json = XML.toJSONObject(xmlString); // converts xml to json
                String jsonPrettyPrintString = json.toString(4); // json pretty print

                out.write(jsonPrettyPrintString);
            } catch (FileNotFoundException fe) {
                System.out.println(fe.toString());
            } catch (IOException ioe) {
                System.out.println(ioe.toString());
            } catch (JSONException je) {
                System.out.println(je.toString());
            }finally {
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
}
