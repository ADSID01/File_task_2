import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        GameProgress gamePr_1 = new GameProgress(100, 50, 80, 9.9);
        GameProgress gamePr_2 = new GameProgress(200, 80, 93, 17.1);
        GameProgress gamePr_3 = new GameProgress(300, 30, 22, 1.0);

        String pathSave1 = "F://Java//Games//savegames//save1.dat";
        String pathSave2 = "F://Java//Games//savegames//save2.dat";
        String pathSave3 = "F://Java//Games//savegames//save3.dat";

        saveGame(pathSave1, gamePr_1);
        saveGame(pathSave2, gamePr_2);
        saveGame(pathSave3, gamePr_3);

        String pathZip = "F://Java//Games//savegames//newZip.zip";
        ArrayList<String> listPath = new ArrayList<>();
        listPath.add(pathSave1);
        listPath.add(pathSave2);
        listPath.add(pathSave3);

        zipFiles(pathZip, listPath);
        deleteFile(listPath);

        System.out.println("\nРабота программы завершена.");
    }

    public static void saveGame(String pathSave, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(pathSave);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String pathZip, ArrayList<String> listPathFilesToZip) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(pathZip))) {
            for (String pathFile : listPathFilesToZip) {
                FileInputStream fis = new FileInputStream(pathFile);
                String[] nameFile = pathFile.split("//");
                ZipEntry entry = new ZipEntry(nameFile[nameFile.length - 1]);
                zos.putNextEntry(entry);

                byte[] buf = new byte[fis.available()];
                fis.read(buf);
                zos.write(buf);

                fis.close();
            }
            zos.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteFile(ArrayList<String> listPath) {
        for (String path : listPath) {
            File file = new File(path);

            try {
                if (file.delete()) {
                    System.out.println("Файл успешно удален: " + path);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            file = null;
        }
    }
}
