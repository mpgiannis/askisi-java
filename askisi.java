import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class askisi {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File(args[0]);
        File inputFile1 = new File(args[1]);
        if(args.length == 3 && inputFile.exists() && inputFile1.exists()) {


            try {
                Scanner data = new Scanner(new FileReader(args[0]));
                data.useDelimiter("\\t");
                int nooflines = 0;
                Scanner data1 = new Scanner(new FileReader(args[1]));
                while (data1.hasNextLine()) {
                    data1.nextLine();
                    nooflines++;
                }
                data1.close();
                Scanner config = new Scanner(new FileReader(args[1]));
                int[] array = new int[nooflines];
                String line = data.nextLine();

                int k = 0;
                while (config.hasNextLine()) {
                    String confi = config.nextLine();
                    System.out.println(confi);
                    for (int i = 0; i < line.split("\t").length; i++) {
                        if (line.split("\t")[i].equals(confi)) {
                            array[k] = i;
                        }
                    }
                    k++;
                }

                FileWriter newfile = new FileWriter(args[2]);
                newfile.write(line + '\n');


                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                SecretKey skey = kgen.generateKey();
                Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
                ci.init(Cipher.ENCRYPT_MODE, skey);

                while (data.hasNextLine()) {
                    line = data.nextLine();

                    for (int i = 0; i < line.split("\t").length; i++) {
                        if (i == array[0] || i == array[1] || i == array[2]) {
                            byte[] input = line.split("\t")[i].getBytes("UTF-8");
                            byte[] encoded = ci.doFinal(input);
                            newfile.write(String.valueOf(encoded) + '\t');

                        } else newfile.write(line.split("\t")[i] + '\t');

                    }
                    newfile.write('\n');


                }

                newfile.close();


                data.close();
                config.close();

            }
            catch (IOException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
                e.printStackTrace();
            }
            catch (BadPaddingException e) {
                e.printStackTrace();
            }
            catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }

        else{
            System.out.println("give arguments");
            System.exit(0); }

        }


    }

