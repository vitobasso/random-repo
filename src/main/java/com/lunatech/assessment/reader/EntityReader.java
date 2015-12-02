package com.lunatech.assessment.reader;

import com.lunatech.assessment.util.Record;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

import static com.lunatech.assessment.util.Lang.stream;

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
        Reader in = new FileReader(path);
        Iterable<CSVRecord> records = format.parse(in);
        return stream(records)
                .map(Record::new)
                .map(this::read)
                .collect(Collectors.toList());
    }

    protected abstract T read(Record strings);

}
