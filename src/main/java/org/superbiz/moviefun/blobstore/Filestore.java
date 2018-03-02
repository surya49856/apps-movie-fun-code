package org.superbiz.moviefun.blobstore;

import org.apache.tika.Tika;
import org.apache.tika.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.util.Optional;

public class Filestore implements BlobStore {



    @Override
    public void put(Blob blob) throws IOException {
        File targetFile=new File(blob.contentType,blob.name);
        targetFile.delete();
        targetFile.getParentFile().mkdirs();
        targetFile.createNewFile();

        try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            IOUtils.copy(blob.inputStream,outputStream);
        }
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
            File targetFile=new File(name);
             Tika tika = new Tika();
        Blob blob = new Blob(name, new FileInputStream(targetFile), tika.detect(targetFile));

        return Optional.of(blob); }

    @Override
    public void deleteAll() {

    }
}
