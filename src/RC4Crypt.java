import java.util.Arrays;

/**
 * Created by Vladimir Rodin.
 * Built on: Thinkpad Workstation W540
 * Date: 26.01.2016
 * Twitter: @heyhihellobro
 */
public class RC4Crypt {
    byte S[] = new byte[128];

    int x = 0;
    int y = 0;

    public RC4Crypt(byte[] key) {
        init(key);

    }

    private void init(byte[] key) {
        int keyLength = key.length;

        for (int i = 0; i < 128; i++) {
            S[i] = (byte) i;
        }

        int j = 0;
        for (int i = 0; i < 128; i++) {
            j = (j + S[i] + key[i % keyLength]) % 128;
            swap(i, j);
        }
    }

    public byte[] Encode(byte[] dataB) {

        byte[] cipher = new byte[dataB.length];

        for (int m = 0; m < dataB.length; m++) {
            cipher[m] = (byte) (dataB[m] ^ keyItem());
        }

        return cipher;
    }

    public byte[] Decode(byte[] dataB) {
        return Encode(dataB);
    }

    // Pseudo-Random Generation Algorithm
    private byte keyItem() {
        x = (x + 1) % 128;
        y = (y + S[x]) % 128;

        swap(x, y);

        return S[(S[x] + S[y]) % 128];
    }

    private void swap(int i, int j) {
        byte temp = S[i];
        S[i] = S[j];
        S[j] = temp;
    }

    public static void main(String args[]) {


        String str = "myKey";
        String testString = "Wordpress";
        System.out.println("Оригинал сообщения: " + testString);
        byte[] key = str.getBytes();

        RC4Crypt rc = new RC4Crypt(key);
        byte[] testBytes = testString.getBytes();
        byte[] result = rc.Encode(testBytes);
        System.out.println("Зашифрованное сообщение: " + result);
        //System.out.println("Зашифрованное сообщение (Массив): " + Arrays.toString(result));

        RC4Crypt decoder = new RC4Crypt(key);
        byte[] decryptedBytes = decoder.Decode(result);
        System.out.println("Дешифрованное сообщение: " + new String(decryptedBytes));
    }

}
