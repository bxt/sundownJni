#include "jni.h"
#include <stdio.h>
#include "bxt_sundownJni_SundownJni.h"

#include "markdown.h"
#include "html.h"
#include "buffer.h"

#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "bxt_sundownJni_SundownJni_SundownJniOptions.h"

#define READ_UNIT 1024
#define OUTPUT_UNIT 64
#define SMARTYPANTS_UNIT 128

#define MIN(x,y) ((x)<(y)?(x):(y))

JNIEXPORT jbyteArray JNICALL 
Java_bxt_sundownJni_SundownJni_toHtml(JNIEnv *env, jobject obj, jbyteArray jbytes, jint markdown_render_flags, jint my_flags, jint html_render_flags)
{
  struct buf *ib, *ob;
  int ret,i;

  struct sd_callbacks callbacks;
  struct html_renderopt options;
  struct sd_markdown *markdown;
  
  /* reading everything from java byte[] */
  int jbyteslen = (*env)->GetArrayLength(env, jbytes);
  ib = bufnew(READ_UNIT);
  if(BUF_ENOMEM==bufgrow(ib,jbyteslen) );
  (*env)->GetByteArrayRegion(env, jbytes, 0, MIN(jbyteslen,ib->asize - ib->size), ib->data );
  ib->size+=MIN(jbyteslen,ib->asize - ib->size);
  
  /* performing markdown parsing */
  ob = bufnew(OUTPUT_UNIT);

  if( my_flags & bxt_sundownJni_SundownJni_SundownJniOptions_TOC ) {
    sdhtml_toc_renderer(&callbacks, &options);
  } else {
    sdhtml_renderer(&callbacks, &options, html_render_flags);
  }
  markdown = sd_markdown_new(markdown_render_flags, 16, &callbacks, &options);

  sd_markdown_render(ob, ib->data, MIN(ib->size,jbyteslen), markdown);
  sd_markdown_free(markdown);

  /* Smartypants actions */
  if( my_flags & bxt_sundownJni_SundownJni_SundownJniOptions_SMARTYPANTS ) {
    struct buf *sb = bufnew(SMARTYPANTS_UNIT);
    sdhtml_smartypants(sb, ob->data, ob->size);
    bufrelease(ob);
    ob = sb;
  }

  /* writing the result to java byte[] */
  jbyteArray jbytesRes = (*env)->NewByteArray(env, ob->size);
  int jbytesReslen = ob->size;
  //stelle dynmaisch Speicherplatz zur Verfügung
  jbyte *tmp;
  tmp = malloc(jbytesReslen * sizeof(jbyte));
  for(i=0;i<jbytesReslen; ++i){
    tmp[i] = (jbyte) ob->data[i];
    (*env)->SetByteArrayRegion(env, jbytesRes, 0,jbytesReslen, tmp);
  }
  free(tmp);

  /* cleanup */
  bufrelease(ib);
  bufrelease(ob);
  
  // might enable this for debugging:
  // printf("done...%d > %d\n",jbyteslen,jbytesReslen);
  return jbytesRes;
}
