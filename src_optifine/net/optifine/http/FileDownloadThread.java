package net.optifine.http;

public class FileDownloadThread extends Thread {
   private String urlString = null;
   private IFileDownloadListener listener = null;

   public FileDownloadThread(String urlString, IFileDownloadListener listener) {
      this.urlString = urlString;
      this.listener = listener;
   }

   public void run() {
      try {
         byte[] bytes = HttpPipeline.get(this.urlString, bib.z().M());
         this.listener.fileDownloadFinished(this.urlString, bytes, (Throwable)null);
      } catch (Exception var2) {
         this.listener.fileDownloadFinished(this.urlString, (byte[])null, var2);
      }

   }

   public String getUrlString() {
      return this.urlString;
   }

   public IFileDownloadListener getListener() {
      return this.listener;
   }
}
