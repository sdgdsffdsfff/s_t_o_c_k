package other.sumur.java.test.thinking.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

// $Id$


public class CopyFile
{
  static public void main( String args[] ) throws Exception {

    String infile ="/home/samforit/srcFile";
    String outfile ="/home/samforit/destFile";

    FileInputStream fin = new FileInputStream( infile );
    FileOutputStream fout = new FileOutputStream( outfile );

    FileChannel fcin = fin.getChannel();
    FileChannel fcout = fout.getChannel();

    ByteBuffer buffer = ByteBuffer.allocate( 1024 );

    while (true) {
      buffer.clear();

      int r = fcin.read( buffer );
      System.out.println(r);
      if (r==-1) {
        break;
      }
      buffer.flip();
      fcout.write( buffer );
    }
    fcin.close();
    fcout.close();
  }
}
