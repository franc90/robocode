package pl.edu.agh.robocode.bot.statistics;

import robocode.RobocodeFileOutputStream;

import java.io.*;

public class Statistics {

    private final File dataFile;

    public Statistics(File dataFile) {
        this.dataFile = dataFile;
    }

    public void save(String stats) {
        BufferedWriter bw = null;
        RobocodeFileOutputStream rfos = null;
        try {
            rfos = new RobocodeFileOutputStream(dataFile.getPath(), true);
            OutputStreamWriter writer = new OutputStreamWriter(rfos, "UTF-8");
            bw = new BufferedWriter(writer);
            bw.write(String.valueOf(stats));
            bw.newLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (bw != null)
                try {
                    bw.close();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

            if (rfos != null)
                try {
                    rfos.close();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
        }
    }
}
