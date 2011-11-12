package bxt.sundownJni;

import java.util.Arrays;
import java.io.UnsupportedEncodingException;

class SundownJni {
  
  private MarkdownOptions markdownOptions = new MarkdownOptions();
  private SundownJniOptions sundownJniOptions = new SundownJniOptions();
  private HtmlOptions htmlOptions = new HtmlOptions();
  
  public SundownJni() {}
  
  public SundownJni(MarkdownOptions markdownOptions, SundownJniOptions sundownJniOptions, HtmlOptions htmlOptions) {
    if(markdownOptions!=null) this.markdownOptions=markdownOptions;
    if(sundownJniOptions!=null) this.sundownJniOptions=sundownJniOptions;
    if(htmlOptions!=null) this.htmlOptions=htmlOptions;
  }
  
  public String toHtml(String input) {
    String returnValue=null;
    try {
      returnValue=new String(
        toHtml(
          input.getBytes("UTF-8"),
          markdownOptions.getBitmask(),
          sundownJniOptions.getBitmask(),
          htmlOptions.getBitmask() )
        ,"UTF-8" );
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return returnValue;
  }
  
  public static void main(String[] args) {
    /*
    try {
      System.out.println(new String(new SundownJni().toHtml(new byte[]{42,67,68,-60,-99,42,10,0}),"UTF-8"));
    } catch (UnsupportedEncodingException e) { e.printStackTrace(); }
    */
    SundownJniOptions s = new SundownJniOptions()/*.setToc()*/.setSmartypants();
    HtmlOptions h = new HtmlOptions().setToc()/*.setSkipHtml()*/;
    MarkdownOptions m = new MarkdownOptions().setStrikethrough().setSuperscript().setAutolink();
    System.out.print(new SundownJni(m,s,h).toHtml(
      "*fett*\n\n------\n\n"+
      "Hallo\n-----\n\n"+
      "Noch'll ~~zwei~~ \"ein\" Absatz^2 <i>mit</i> 1/2 ßondährzeich€nĝ... -- http://1-co.de/ \n"
      ));
  }
  
  private native byte[] toHtml(byte[] input, int markdownOptions, int sundownJniOptions, int htmlOptions);
  static {
    System.loadLibrary("SundownJni");
  }
  
  // And for easy access to options:
  


  public static class MarkdownOptions {

    private static final int MKDEXT_NO_INTRA_EMPHASIS = 1 << 0;

    private static final int MKDEXT_TABLES = 1 << 1;

    private static final int MKDEXT_FENCED_CODE = 1 << 2;

    private static final int MKDEXT_AUTOLINK = 1 << 3;

    private static final int MKDEXT_STRIKETHROUGH = 1 << 4;

    private static final int MKDEXT_LAX_HTML_BLOCKS = 1 << 5;

    private static final int MKDEXT_SPACE_HEADERS = 1 << 6;

    private static final int MKDEXT_SUPERSCRIPT = 1 << 7;

    private int bitmask = 0;
    
    private int getBitmask() {
      return bitmask;
    }
    

    public MarkdownOptions setNoIntraEmphasis() {
      setNoIntraEmphasis(true);
      return this;
    }
    public void setNoIntraEmphasis(boolean mkdextNoIntraEmphasis) {
      bitmask = (mkdextNoIntraEmphasis? bitmask|MKDEXT_NO_INTRA_EMPHASIS : bitmask&~MKDEXT_NO_INTRA_EMPHASIS);
    }
    public boolean getNoIntraEmphasis() {
      return 0 != (bitmask & MKDEXT_NO_INTRA_EMPHASIS);
    }

    public MarkdownOptions setTables() {
      setTables(true);
      return this;
    }
    public void setTables(boolean mkdextTables) {
      bitmask = (mkdextTables? bitmask|MKDEXT_TABLES : bitmask&~MKDEXT_TABLES);
    }
    public boolean getTables() {
      return 0 != (bitmask & MKDEXT_TABLES);
    }

    public MarkdownOptions setFencedCode() {
      setFencedCode(true);
      return this;
    }
    public void setFencedCode(boolean mkdextFencedCode) {
      bitmask = (mkdextFencedCode? bitmask|MKDEXT_FENCED_CODE : bitmask&~MKDEXT_FENCED_CODE);
    }
    public boolean getFencedCode() {
      return 0 != (bitmask & MKDEXT_FENCED_CODE);
    }

    public MarkdownOptions setAutolink() {
      setAutolink(true);
      return this;
    }
    public void setAutolink(boolean mkdextAutolink) {
      bitmask = (mkdextAutolink? bitmask|MKDEXT_AUTOLINK : bitmask&~MKDEXT_AUTOLINK);
    }
    public boolean getAutolink() {
      return 0 != (bitmask & MKDEXT_AUTOLINK);
    }

    public MarkdownOptions setStrikethrough() {
      setStrikethrough(true);
      return this;
    }
    public void setStrikethrough(boolean mkdextStrikethrough) {
      bitmask = (mkdextStrikethrough? bitmask|MKDEXT_STRIKETHROUGH : bitmask&~MKDEXT_STRIKETHROUGH);
    }
    public boolean getStrikethrough() {
      return 0 != (bitmask & MKDEXT_STRIKETHROUGH);
    }

    public MarkdownOptions setLaxHtmlBlocks() {
      setLaxHtmlBlocks(true);
      return this;
    }
    public void setLaxHtmlBlocks(boolean mkdextLaxHtmlBlocks) {
      bitmask = (mkdextLaxHtmlBlocks? bitmask|MKDEXT_LAX_HTML_BLOCKS : bitmask&~MKDEXT_LAX_HTML_BLOCKS);
    }
    public boolean getLaxHtmlBlocks() {
      return 0 != (bitmask & MKDEXT_LAX_HTML_BLOCKS);
    }

    public MarkdownOptions setSpaceHeaders() {
      setSpaceHeaders(true);
      return this;
    }
    public void setSpaceHeaders(boolean mkdextSpaceHeaders) {
      bitmask = (mkdextSpaceHeaders? bitmask|MKDEXT_SPACE_HEADERS : bitmask&~MKDEXT_SPACE_HEADERS);
    }
    public boolean getSpaceHeaders() {
      return 0 != (bitmask & MKDEXT_SPACE_HEADERS);
    }

    public MarkdownOptions setSuperscript() {
      setSuperscript(true);
      return this;
    }
    public void setSuperscript(boolean mkdextSuperscript) {
      bitmask = (mkdextSuperscript? bitmask|MKDEXT_SUPERSCRIPT : bitmask&~MKDEXT_SUPERSCRIPT);
    }
    public boolean getSuperscript() {
      return 0 != (bitmask & MKDEXT_SUPERSCRIPT);
    }

  }
  
  

  public static class SundownJniOptions {

    private static final int TOC = 1 << 0;

    private static final int SMARTYPANTS = 1 << 1;

    private int bitmask = 0;
    
    private int getBitmask() {
      return bitmask;
    }
    

    public SundownJniOptions setToc() {
      setToc(true);
      return this;
    }
    public void setToc(boolean toc) {
      bitmask = (toc? bitmask|TOC : bitmask&~TOC);
    }
    public boolean getToc() {
      return 0 != (bitmask & TOC);
    }

    public SundownJniOptions setSmartypants() {
      setSmartypants(true);
      return this;
    }
    public void setSmartypants(boolean smartypants) {
      bitmask = (smartypants? bitmask|SMARTYPANTS : bitmask&~SMARTYPANTS);
    }
    public boolean getSmartypants() {
      return 0 != (bitmask & SMARTYPANTS);
    }

  }
  
  

  public static class HtmlOptions {

    private static final int HTML_SKIP_HTML = 1 << 0;

    private static final int HTML_SKIP_STYLE = 1 << 1;

    private static final int HTML_SKIP_IMAGES = 1 << 2;

    private static final int HTML_SKIP_LINKS = 1 << 3;

    private static final int HTML_EXPAND_TABS = 1 << 4;

    private static final int HTML_SAFELINK = 1 << 5;

    private static final int HTML_TOC = 1 << 6;

    private static final int HTML_HARD_WRAP = 1 << 7;

    private static final int HTML_USE_XHTML = 1 << 8;

    private int bitmask = 0;
    
    private int getBitmask() {
      return bitmask;
    }
    

    public HtmlOptions setSkipHtml() {
      setSkipHtml(true);
      return this;
    }
    public void setSkipHtml(boolean htmlSkipHtml) {
      bitmask = (htmlSkipHtml? bitmask|HTML_SKIP_HTML : bitmask&~HTML_SKIP_HTML);
    }
    public boolean getSkipHtml() {
      return 0 != (bitmask & HTML_SKIP_HTML);
    }

    public HtmlOptions setSkipStyle() {
      setSkipStyle(true);
      return this;
    }
    public void setSkipStyle(boolean htmlSkipStyle) {
      bitmask = (htmlSkipStyle? bitmask|HTML_SKIP_STYLE : bitmask&~HTML_SKIP_STYLE);
    }
    public boolean getSkipStyle() {
      return 0 != (bitmask & HTML_SKIP_STYLE);
    }

    public HtmlOptions setSkipImages() {
      setSkipImages(true);
      return this;
    }
    public void setSkipImages(boolean htmlSkipImages) {
      bitmask = (htmlSkipImages? bitmask|HTML_SKIP_IMAGES : bitmask&~HTML_SKIP_IMAGES);
    }
    public boolean getSkipImages() {
      return 0 != (bitmask & HTML_SKIP_IMAGES);
    }

    public HtmlOptions setSkipLinks() {
      setSkipLinks(true);
      return this;
    }
    public void setSkipLinks(boolean htmlSkipLinks) {
      bitmask = (htmlSkipLinks? bitmask|HTML_SKIP_LINKS : bitmask&~HTML_SKIP_LINKS);
    }
    public boolean getSkipLinks() {
      return 0 != (bitmask & HTML_SKIP_LINKS);
    }

    public HtmlOptions setExpandTabs() {
      setExpandTabs(true);
      return this;
    }
    public void setExpandTabs(boolean htmlExpandTabs) {
      bitmask = (htmlExpandTabs? bitmask|HTML_EXPAND_TABS : bitmask&~HTML_EXPAND_TABS);
    }
    public boolean getExpandTabs() {
      return 0 != (bitmask & HTML_EXPAND_TABS);
    }

    public HtmlOptions setSafelink() {
      setSafelink(true);
      return this;
    }
    public void setSafelink(boolean htmlSafelink) {
      bitmask = (htmlSafelink? bitmask|HTML_SAFELINK : bitmask&~HTML_SAFELINK);
    }
    public boolean getSafelink() {
      return 0 != (bitmask & HTML_SAFELINK);
    }

    public HtmlOptions setToc() {
      setToc(true);
      return this;
    }
    public void setToc(boolean htmlToc) {
      bitmask = (htmlToc? bitmask|HTML_TOC : bitmask&~HTML_TOC);
    }
    public boolean getToc() {
      return 0 != (bitmask & HTML_TOC);
    }

    public HtmlOptions setHardWrap() {
      setHardWrap(true);
      return this;
    }
    public void setHardWrap(boolean htmlHardWrap) {
      bitmask = (htmlHardWrap? bitmask|HTML_HARD_WRAP : bitmask&~HTML_HARD_WRAP);
    }
    public boolean getHardWrap() {
      return 0 != (bitmask & HTML_HARD_WRAP);
    }

    public HtmlOptions setUseXhtml() {
      setUseXhtml(true);
      return this;
    }
    public void setUseXhtml(boolean htmlUseXhtml) {
      bitmask = (htmlUseXhtml? bitmask|HTML_USE_XHTML : bitmask&~HTML_USE_XHTML);
    }
    public boolean getUseXhtml() {
      return 0 != (bitmask & HTML_USE_XHTML);
    }

  }
  
  
    

}
