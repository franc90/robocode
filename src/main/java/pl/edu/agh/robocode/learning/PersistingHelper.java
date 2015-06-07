package pl.edu.agh.robocode.learning;

import pl.edu.agh.robocode.exception.NullValueException;
import robocode.RobocodeFileOutputStream;

import java.io.*;

class PersistingHelper<T extends Serializable> {

    private File dataFile;

    public PersistingHelper(File dataFile) {
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
                throw new NullValueException(PersistingHelper.class + "no loaded object");
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

        throw new NullValueException(PersistingHelper.class + "no loaded object");
    }

}