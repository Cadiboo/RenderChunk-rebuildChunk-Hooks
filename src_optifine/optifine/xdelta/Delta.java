package optifine.xdelta;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;

public class Delta {
   public static final int S = 16;
   public static final boolean debug = false;
   public static final int buff_size = 1024;

   public static void computeDelta(SeekableSource source, InputStream targetIS, int targetLength, DiffWriter output) throws IOException, DeltaException {
      int sourceLength = (int)source.length();
      Checksum checksum = new Checksum();
      checksum.generateChecksums((InputStream)(new SeekableSourceInputStream(source)), sourceLength);
      source.seek(0L);
      PushbackInputStream target = new PushbackInputStream(new BufferedInputStream(targetIS), 1024);
      boolean done = false;
      byte[] buf = new byte[16];
      long hashf = 0L;
      byte[] b = new byte[1];
      byte[] sourcebyte = new byte[16];
      int readBytes;
      int targetidx;
      if (targetLength > 16 && sourceLength > 16) {
         readBytes = target.read(buf, 0, 16);
         targetidx = readBytes;
         hashf = Checksum.queryChecksum(buf, 16);
         long alternativehashf = hashf;
         boolean sourceOutofBytes = false;

         while(true) {
            while(true) {
               while(!done) {
                  int index = checksum.findChecksumIndex(hashf);
                  if (index != -1) {
                     boolean match = true;
                     int offset = index * 16;
                     int length = 15;
                     source.seek((long)offset);
                     if (!sourceOutofBytes && source.read(sourcebyte, 0, 16) != -1) {
                        for(int ix = 0; ix < 16; ++ix) {
                           if (sourcebyte[ix] != buf[ix]) {
                              match = false;
                           }
                        }
                     } else {
                        sourceOutofBytes = true;
                     }

                     if (match & !sourceOutofBytes) {
                        long start = System.currentTimeMillis();
                        boolean ok = true;
                        byte[] sourceBuff = new byte[1024];
                        byte[] targetBuff = new byte[1024];
                        int source_idx = false;
                        int target_idx = false;
                        boolean var29 = false;

                        int read_idx;
                        do {
                           int source_idx = source.read(sourceBuff, 0, 1024);
                           if (source_idx == -1) {
                              sourceOutofBytes = true;
                              break;
                           }

                           int target_idx = target.read(targetBuff, 0, source_idx);
                           if (target_idx == -1) {
                              break;
                           }

                           read_idx = Math.min(source_idx, target_idx);
                           int i = 0;

                           do {
                              ++targetidx;
                              ++length;
                              ok = sourceBuff[i] == targetBuff[i];
                              ++i;
                              if (!ok) {
                                 b[0] = targetBuff[i - 1];
                                 if (target_idx != -1) {
                                    target.unread(targetBuff, i, target_idx - i);
                                 }
                              }
                           } while(i < read_idx && ok);

                           b[0] = targetBuff[i - 1];
                        } while(ok && targetLength - targetidx > 0);

                        output.addCopy(offset, length);
                        if (targetLength - targetidx <= 15) {
                           buf[0] = b[0];
                           read_idx = targetLength - targetidx;
                           target.read(buf, 1, read_idx);
                           targetidx += read_idx;

                           for(int ix = 0; ix < read_idx + 1; ++ix) {
                              output.addData(buf[ix]);
                           }

                           done = true;
                           continue;
                        }

                        buf[0] = b[0];
                        target.read(buf, 1, 15);
                        targetidx += 15;
                        alternativehashf = hashf = Checksum.queryChecksum(buf, 16);
                        continue;
                     }
                  }

                  int j;
                  if (targetLength - targetidx > 0) {
                     target.read(b, 0, 1);
                     ++targetidx;
                     output.addData(buf[0]);
                     alternativehashf = Checksum.incrementChecksum(alternativehashf, buf[0], b[0]);

                     for(j = 0; j < 15; ++j) {
                        buf[j] = buf[j + 1];
                     }

                     buf[15] = b[0];
                     hashf = Checksum.queryChecksum(buf, 16);
                  } else {
                     for(j = 0; j < 16; ++j) {
                        output.addData(buf[j]);
                     }

                     done = true;
                  }
               }

               return;
            }
         }
      } else {
         while((readBytes = target.read(buf)) >= 0) {
            for(targetidx = 0; targetidx < readBytes; ++targetidx) {
               output.addData(buf[targetidx]);
            }
         }

      }
   }

   public static void computeDelta(byte[] source, InputStream targetIS, int targetLength, DiffWriter output) throws IOException, DeltaException {
      computeDelta((SeekableSource)(new ByteArraySeekableSource(source)), targetIS, targetLength, output);
   }

   public static void computeDelta(File sourceFile, File targetFile, DiffWriter output) throws IOException, DeltaException {
      int targetLength = (int)targetFile.length();
      SeekableSource source = new RandomAccessFileSeekableSource(new RandomAccessFile(sourceFile, "r"));
      FileInputStream targetIS = new FileInputStream(targetFile);

      try {
         computeDelta((SeekableSource)source, targetIS, targetLength, output);
      } catch (IOException var11) {
         throw var11;
      } catch (DeltaException var12) {
         throw var12;
      } finally {
         output.flush();
         source.close();
         targetIS.close();
         output.close();
      }

   }

   public static void main(String[] argv) {
      new Delta();
      if (argv.length != 3) {
         System.err.println("usage Delta [-d] source target [output]");
         System.err.println("either -d or an output filename must be specified.");
         System.err.println("aborting..");
      } else {
         try {
            DiffWriter output = null;
            File sourceFile = null;
            File targetFile = null;
            if (argv[0].equals("-d")) {
               sourceFile = new File(argv[1]);
               targetFile = new File(argv[2]);
               output = new DebugDiffWriter();
            } else {
               sourceFile = new File(argv[0]);
               targetFile = new File(argv[1]);
               output = new GDiffWriter(new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(argv[2])))));
            }

            if (sourceFile.length() > 2147483647L || targetFile.length() > 2147483647L) {
               System.err.println("source or target is too large, max length is 2147483647");
               System.err.println("aborting..");
               return;
            }

            computeDelta(sourceFile, targetFile, (DiffWriter)output);
            ((DiffWriter)output).flush();
            ((DiffWriter)output).close();
         } catch (Exception var5) {
            System.err.println("error while generating delta: " + var5);
         }

      }
   }
}
