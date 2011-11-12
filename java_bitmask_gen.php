<?php

$bitmasks=array(

array(
'name'=>'MarkdownOptions',
'opts'=>'
MKDEXT_NO_INTRA_EMPHASIS
MKDEXT_TABLES
MKDEXT_FENCED_CODE
MKDEXT_AUTOLINK
MKDEXT_STRIKETHROUGH
MKDEXT_LAX_HTML_BLOCKS
MKDEXT_SPACE_HEADERS
MKDEXT_SUPERSCRIPT
',
),

array(
'name'=>'SundownJniOptions',
'opts'=>'
TOC
SMARTYPANTS
',
),

array(
'name'=>'HtmlOptions',
'opts'=>'
HTML_SKIP_HTML
HTML_SKIP_STYLE
HTML_SKIP_IMAGES
HTML_SKIP_LINKS
HTML_EXPAND_TABS
HTML_SAFELINK
HTML_TOC
HTML_HARD_WRAP
HTML_USE_XHTML
',
),

); // end $bitmasks=array(

foreach($bitmasks as $bitmask):


$name=$bitmask['name'];
$opts=explode("\n",$bitmask['opts']);

//var_dump($opts);

echo '
  public static class '.$name.' {
';

$oc=0;
foreach($opts as $opt) {
if($opt) {
echo '
    private static final int '.$opt.' = 1 << '.$oc.';
';
$oc++;
}
}

echo '
    private int bitmask = 0;
    
    private int getBitmask() {
      return bitmask;
    }
    
';

$oc=0;
foreach($opts as $opt) {
if($opt) {
$optV=camelcase(strtolower($opt));
$optF=ucfirst(camelcase(strtolower(removefirst($opt))));
echo '
    public '.$name.' set'.$optF.'() {
      set'.$optF.'(true);
      return this;
    }
    public void set'.$optF.'(boolean '.$optV.') {
      bitmask = ('.$optV.'? bitmask|'.$opt.' : bitmask&~'.$opt.');
    }
    public boolean get'.$optF.'() {
      return 0 != (bitmask & '.$opt.');
    }
';
$oc++;
}
}

echo '
  }
  
  
';

endforeach;

function removefirst($str) {
  $p=strpos($str,'_');
  if($p!==false) {
    $str=substr($str,$p+1);
  }
  return $str;
}

function camelcase($str) {
  $cb=create_function('$c', 'return strtoupper($c[1]);');
  return preg_replace_callback('/_([a-z])/', $cb, $str);
}