package other.sumur.java.test.thinking.nio;
// $Id$

import java.nio.*;

public class UseFloatBuffer
{
  static public void main( String args[] ) throws Exception {
    FloatBuffer buffer = FloatBuffer.allocate( 10 );
    System.out.println(buffer.limit()+":"+buffer.position()+":"+buffer.arrayOffset()+":"+buffer.capacity());
    for (int i=0; i<buffer.capacity()-1; ++i) {
      float f = (float)Math.sin( (((float)i)/10)*(2*Math.PI) );
      buffer.put( f );
      System.out.println(buffer.limit()+":"+buffer.position()+":"+buffer.arrayOffset()+":"+buffer.capacity());
    }

    buffer.flip();
   
    System.out.println(buffer.limit()+":"+buffer.position()+":"+buffer.arrayOffset()+":"+buffer.capacity());
    while (buffer.hasRemaining()) {
      float f = buffer.get();
      System.out.println( f );
      System.out.println(buffer.limit()+":"+buffer.position()+":"+buffer.arrayOffset()+":"+buffer.capacity());
    }
    System.out.println(buffer.limit()+":"+buffer.position()+":"+buffer.arrayOffset()+":"+buffer.capacity());
  }
}
