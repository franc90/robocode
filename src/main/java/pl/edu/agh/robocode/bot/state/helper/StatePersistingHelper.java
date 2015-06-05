package pl.edu.agh.robocode.bot.state.helper;

import robocode.RobocodeFileOutputStream;

import java.io.*;

public class StatePersistingHelper<T extends Serializable> {

    private File dataFile;

    public StatePersistingHelper(File dataFile) {
        this.dataFile = dataFile;
    }

    public void persist(T obj) {
        RobocodeFileOutputStream rfos = null;
        ObjectOutputStream oos = null;

        try {
            rfos = new RobocodeFileOutputStream(dataFile);
            oos = new ObjectOutputStream(rfos);

            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    System.err.println("Could not close oos " + dataFile.getName());
                }
            }
            if (rfos != null) {
                try {
                    rfos.close();
                } catch (IOException e) {
                    System.err.println("Could not close rfos " + dataFile.getName());
                }
            }
        }
    }

    public T load() {
        FileInputStream is = null;
        ObjectInputStream ois = null;
        try {
            is = new FileInputStream(dataFile);
            ois = new ObjectInputStream(is);

            T o =  (T) ois.readObject();
            if (o == null) {
                throw new NullPointerException("CHUJ DUPA I KAMIENI KUPA\n" + dataFile.getPath());
            }
            return o;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    System.err.println("Could not close ois " + dataFile.getName());
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.err.println("Could not close is " + dataFile.getName());
                }
            }
        }
        return null;
    }

}
