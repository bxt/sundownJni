SundownJni - Markdown bindings for Java
=======================================

Parse [Markdown][markdown] and compile to HTML in Java using the well-tested [sundown][sundown] C-library. 

Installing
----------

Download the source package, unzip and run `make` in the `sundownJni` directory. 
If the file `README.html` appears, everything works. 

Then, add the file `libSundownJni.so` to your `LD_LIBRARY_PATH` by unsing something
like `export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:.` or configuring in your IDE. 
Add the `*.class` files to your java class path. 

Usage
-----

This library includes a JNI adaptor for accessing the C methods from Java. 
The API is pretty simple: 

    String quote = new SundownJni().toHtml("This is *Sparta!*\n"));

The `quote` variable will contain `<p>This is <em>Sparta!</em></p>`. 

You can pass Options objects to the constructor like this:

    SundownJni parser = new SundownJni(
      new MarkdownOptions().setStrikethrough().setSuperscript().setAutolink(),
      new SundownJniOptions().setSmartypants(),
      new HtmlOptions().setToc()
    );

All options are optional and disabled by default. For example, if you want to 
remove all (other) HTML tags from the input, you could do the following: 

    SundownJni parser = new SundownJni(null,null,new HtmlOptions().setSkipHtml());

The SundownJniOptions are not passed to sundown but used by the C code of
the adaptor to enable Smartypants or TOC rendering. 

This will construct you a paser which will extact a TOC from your embedded headlines:

    SundownJni parser = new SundownJni(null,new SundownJniOptions().setToc(),null);

This will construct you a paser which will enable [Smartypants][smartypants]:

    SundownJni parser = new SundownJni(null,new SundownJniOptions().setSmartypants(),null);

API
---

For detailled descriptions of the sundown options see sundown's documenation. 
As a Java-API doc here is the trimmed java source: 

    package bxt.sundownJni;
    
    class SundownJni {
      
      public SundownJni() {}
      public SundownJni(MarkdownOptions markdownOptions, SundownJniOptions sundownJniOptions, HtmlOptions htmlOptions) {}
      
      public String toHtml(String input) {}
      
      public static class MarkdownOptions {
        public void setNoIntraEmphasis(boolean mkdextNoIntraEmphasis) {}
        public void setTables(boolean mkdextTables) {}
        public void setFencedCode(boolean mkdextFencedCode) {}
        public void setAutolink(boolean mkdextAutolink) {}
        public void setStrikethrough(boolean mkdextStrikethrough) {}
        public void setLaxHtmlBlocks(boolean mkdextLaxHtmlBlocks) {}
        public void setSpaceHeaders(boolean mkdextSpaceHeaders) {}
        public void setSuperscript(boolean mkdextSuperscript) {}
      }
      
      public static class SundownJniOptions {
        public void setToc(boolean toc) {}
        public void setSmartypants(boolean smartypants) {}
      }
      
      public static class HtmlOptions {
        public void setSkipHtml(boolean htmlSkipHtml) {}
        public void setSkipStyle(boolean htmlSkipStyle) {}
        public void setSkipImages(boolean htmlSkipImages) {}
        public void setSkipLinks(boolean htmlSkipLinks) {}
        public void setExpandTabs(boolean htmlExpandTabs) {}
        public void setSafelink(boolean htmlSafelink) {}
        public void setToc(boolean htmlToc) {}
        public void setHardWrap(boolean htmlHardWrap) {}
        public void setUseXhtml(boolean htmlUseXhtml) {}
      }
      
    }


Experimental
------------

This library is very experimental and currently only tested with the
following setup: 

    java version "1.6.0_20"
    OpenJDK Runtime Environment (IcedTea6 1.9.7) (suse-1.2.1-x86_64)
    OpenJDK 64-Bit Server VM (build 19.0-b09, mixed mode)
    
    gcc (SUSE Linux) 4.5.1 20101208 [gcc-4_5-branch revision 167585]
    
    Linux dhcppc1 2.6.37.1-1.2-default #1 SMP 2011-02-21 10:34:10 +0100 x86_64 x86_64 x86_64 GNU/Linux

[smartypants]: http://daringfireball.net/projects/smartypants/
  "SmartyPants Documentation"

[markdown]: http://daringfireball.net/projects/markdown/syntax
  "Markdown Syntax Guide"

[sundown]: https://github.com/tanoku/sundown
  "Sundown C library on gihub.com"

[jni]: http://en.wikipedia.org/wiki/Java_Native_Interface
  "Java Native Interface - Wikipedia"
