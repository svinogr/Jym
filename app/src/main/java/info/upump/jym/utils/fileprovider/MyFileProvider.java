package info.upump.jym.utils.fileprovider;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

public class MyFileProvider extends android.support.v4.content.FileProvider {
    public Uri getDatabaseURI(Context c) {
        File data = Environment.getDataDirectory();
        String dbName = "jym.db";
        String currentDBPath = "//data//info.upump.jym//databases//" + dbName;

        File exportFile = new File(data, currentDBPath);

        return getFileUri(c, exportFile);
    }

    public Uri getFileUri(Context c, File f) {
        return getUriForFile(c, "info.upump.jym", f);
    }
}
