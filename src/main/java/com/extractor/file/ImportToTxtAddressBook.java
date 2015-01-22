package com.extractor.file;

import com.extractor.model.Company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

/**
 * Created by Padonag on 16.11.2014.
 */
public class ImportToTxtAddressBook {
    private static final String TXT_EXTENSION = "txt";

    public File createNewTxtAddressBook(String absolutePath, List<Company> companies){
        File outputFile = new File(absolutePath + "." + TXT_EXTENSION);
        writeCompaniesToTxtFile(companies, outputFile);
        return outputFile;
    }

    public void writeCompaniesToTxtFile(List<Company> companies, File file){
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));
            for(Company tempCompany : companies){
                StringBuilder toFileBuilder = new StringBuilder();
                toFileBuilder.append(tempCompany.getName().replaceAll(",", " "));
                toFileBuilder.append(" <");
                toFileBuilder.append(tempCompany.getEmail());
                toFileBuilder.append("> ");
                writer.append(toFileBuilder.toString()).append("\r\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
