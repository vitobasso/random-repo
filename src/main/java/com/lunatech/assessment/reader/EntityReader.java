package com.lunatech.assessment.reader;

import com.google.common.base.Charsets;
import com.lunatech.assessment.util.Record;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import static com.lunatech.assessment.util.Lang.stream;
import static java.util.stream.Collectors.toList;

/**
 * Created by Victor on 01/12/2015.
 */
public abstract class EntityReader<T> {

    private static final CSVFormat format = CSVFormat.EXCEL.withHeader();
    public static final String ROOT_PATH = "src/main/resources/";

    private final String path;

    public EntityReader(String fileName) {
        this.path = ROOT_PATH + fileName;
    }

    public List<T> read() throws IOException {
        Reader in = new InputStreamReader(new FileInputStream(path), Charsets.UTF_8);
        Iterable<CSVRecord> records = format.parse(in);
        return stream(records)
                .map(Record::new)
                .map(this::read)
                .collect(toList());
    }

    protected abstract T read(Record strings);

}
