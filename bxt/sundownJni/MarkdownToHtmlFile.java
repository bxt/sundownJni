package bxt.sundownJni;

import java.io.*;
import bxt.sundownJni.SundownJni;
import bxt.sundownJni.SundownJni.MarkdownOptions;
import bxt.sundownJni.SundownJni.SundownJniOptions;
import bxt.sundownJni.SundownJni.HtmlOptions;

class MarkdownToHtmlFile {
  
  
  public static void main(String[] args)  throws java.io.IOException, java.io.UnsupportedEncodingException {
    if(args.length >= 1) {
      
      SundownJni parser = new SundownJni(
      new MarkdownOptions().setStrikethrough().setSuperscript().setAutolink(),
      new SundownJniOptions().setSmartypants(),
      new HtmlOptions().setToc());
      
      SundownJni parserToc = new SundownJni(null,new SundownJniOptions().setToc().setSmartypants(),null);
      
      String inputFilePath = args[0];
      
      byte[] buffer = new byte[(int) new File(inputFilePath).length()];
      BufferedInputStream f = null;
      try {
          f = new BufferedInputStream(new FileInputStream(inputFilePath));
          f.read(buffer);
      } finally {
          if (f != null) try { f.close(); } catch (IOException ignored) { }
      }
      String input = new String(buffer, "UTF-8");
      
      System.out.print("<!DOCTYPE html>\n<style>\nbody {\nfont-size:12px;\nfont-family:Arial,sans-serif;\nmax-width:400px;\nmargin:0 auto;\ncolor:#000;\n}\n</style>\n");
      System.out.print(parserToc.toHtml(input));
      System.out.print(parser.toHtml(input));
    } else {
      System.out.println("No input filename. ");
    }
  }
  

}
