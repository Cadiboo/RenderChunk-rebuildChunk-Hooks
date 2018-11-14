package optifine.xdelta;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Checksum {
   public static final int BASE = 65521;
   public static final int S = 16;
   public static boolean debug = false;
   protected int[] hashtable;
   protected long[] checksums;
   protected int prime;
   protected static final char[] single_hash = new char[]{'병', '뭥', '䋂', '\udffe', '陦', '䌛', '蔄', '\ueb46', '捹', '푠', '켔', '叏', '\udb51', '\udb08', 'ወ', '\uf602', '\ue766', '⎔', '┍', '\udcbb', 'ꙸ', 'ʯ', 'ꗆ', '约', '뙅', '쭍', '쑋', '\ue5dc', '\u9fe6', '孜', '㗵', '瀚', '∏', '永', 'ᩖ', '䲣', 'ￆ', '녒', '赡', '穘', '逥', '謽', '뼏', '閣', '\ue5f4', '섧', '㯭', '㈋', '럳', '恔', '㌼', '펃', '腔', '剂', '不', 'ઔ', '瀨', '蚉', '㨢', '\u0980', 'ᡇ', '냱', '魜', '䅶', '롘', '핂', 'Ὤ', '⒗', '橚', '龩', '豚', '睃', 'ꢩ', '騂', '䤘', '䎌', '쎈', '鸫', '䲭', 'ƶ', '\uab19', '\uf777', '㙟', 'Ẳ', 'ञ', '篸', '窎', '刧', '\ueab1', '⁴', '䔣', '\ue781', 'ƣ', 'ᘽ', '㬮', '⡽', '广', 'ꁣ', '넴', '辮', '庎', '랷', '䕈', '\u1f5a', '節', '稤', '透', '䋜', '챩', 'ʠ', 'ଢ', '\udb31', '燾', '౽', 'ᜲ', 'ᅙ', '쬉', '\ue1d2', 'ፑ', '勩', '\uf536', '婏', '쌖', '毹', '覔', '띴', '弾', '\uf6d6', '㩡', '\uf82c', '찢', '鴆', '⦜', '\u09e5', 'Ử', '兏', '赓', 'Ꙑ', '屮', '앷', '祘', '熬', '褖', '魏', 'Ⰹ', '刑', '\uf6d8', '쪪', '\uf7ef', '⡿', '窔', '\uab49', '館', '爢', '\ue457', '휚', 'Ã', '᩶', '\ue98c', '쀷', '興', '尭', '\udfda', '\ue5f5', '\u0b45', 'ᗎ', '詾', 'ﲭ', 'ꨭ', '䭜', '퐮', '뉑', '遾', '驇', '즦', '\ud93f', '࡞', '㗎', 'ꅓ', '繻', '鼋', '▪', '嶟', '쁍', '討', '⡵', '䨜', '⥟', '᎓', '\uf760', '酸', 'ཛ', '墳', '莴', '₂', '爝', '摢', 'ͨ', '柢', '蘤', '᥍', '⋶', '磻', '枑', '눸', '댲', '牶', '\uf272', '䟬', '䔄', 'ꥡ', '鿈', '㿜', '됓', 'z', 'ࠆ', '瑘', '闆', '첪', 'ᣖ', '\ue2ae', 'ᬆ', '\uf3f6', '偐', '죨', '\uf4ac', '쁌', '\uf41c', '餯', '깄', '弛', 'ᄓ', '\u1738', '\ud9a8', '᧪', 'ⴳ', '隘', '\u2fe9', '㈿', '췢', '浱', '\ue37d', '뚗', 'ⱏ', '䍳', '鄂', 'ݝ', '踥', 'ᙲ', '\uec28', '櫋', '蛌', 'ᡮ', '鐔', '홴', '톥'};

   public static long queryChecksum(byte[] buf, int len) {
      int high = 0;
      int low = 0;

      for(int i = 0; i < len; ++i) {
         low += single_hash[buf[i] + 128];
         high += low;
      }

      return (long)((high & '\uffff') << 16 | low & '\uffff');
   }

   public static long incrementChecksum(long checksum, byte out, byte in) {
      char old_c = single_hash[out + 128];
      char new_c = single_hash[in + 128];
      int low = (int)(checksum & 65535L) - old_c + new_c & '\uffff';
      int high = (int)(checksum >> 16) - old_c * 16 + low & '\uffff';
      return (long)(high << 16 | low & '\uffff');
   }

   public static int generateHash(long checksum) {
      long high = checksum >> 16 & 65535L;
      long low = checksum & 65535L;
      long it = (high >> 2) + (low << 3) + (high << 16);
      int hash = (int)(it ^ high ^ low);
      return hash > 0 ? hash : -hash;
   }

   public void generateChecksums(File sourceFile, int length) throws IOException {
      FileInputStream fis = new FileInputStream(sourceFile);

      try {
         this.generateChecksums((InputStream)fis, length);
      } catch (IOException var8) {
         throw var8;
      } finally {
         fis.close();
      }

   }

   public void generateChecksums(InputStream sis, int length) throws IOException {
      InputStream is = new BufferedInputStream(sis);
      int checksumcount = length / 16;
      if (debug) {
         System.out.println("generating checksum array of size " + checksumcount);
      }

      this.checksums = new long[checksumcount];
      this.hashtable = new int[checksumcount];
      this.prime = findClosestPrime(checksumcount);
      if (debug) {
         System.out.println("using prime " + this.prime);
      }

      int i;
      for(i = 0; i < checksumcount; ++i) {
         byte[] buf = new byte[16];
         is.read(buf, 0, 16);
         this.checksums[i] = queryChecksum(buf, 16);
      }

      for(i = 0; i < checksumcount; ++i) {
         this.hashtable[i] = -1;
      }

      for(i = 0; i < checksumcount; ++i) {
         int hash = generateHash(this.checksums[i]) % this.prime;
         if (debug) {
            System.out.println("checking with hash: " + hash);
         }

         if (this.hashtable[hash] != -1) {
            if (debug) {
               System.out.println("hash table collision for index " + i);
            }
         } else {
            this.hashtable[hash] = i;
         }
      }

   }

   public int findChecksumIndex(long checksum) {
      return this.hashtable[generateHash(checksum) % this.prime];
   }

   private static int findClosestPrime(int size) {
      int prime = (int)SimplePrime.belowOrEqual((long)(size - 1));
      return prime < 2 ? 1 : prime;
   }

   private String printIntArray(int[] a) {
      String result = "";
      result = result + "[";

      for(int i = 0; i < a.length; ++i) {
         result = result + a[i];
         if (i != a.length - 1) {
            result = result + ",";
         } else {
            result = result + "]";
         }
      }

      return result;
   }

   private String printLongArray(long[] a) {
      String result = "";
      result = result + "[";

      for(int i = 0; i < a.length; ++i) {
         result = result + a[i];
         if (i != a.length - 1) {
            result = result + ",";
         } else {
            result = result + "]";
         }
      }

      return result;
   }
}
