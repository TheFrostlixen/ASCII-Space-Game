package asciiSpace;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.Format;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class TextureLoader
{
	public TextureLoader(){}
	/*public int load(String filename)
	{
		int id = 0;
		int w = 0;
		int h = 0;
		ByteBuffer buff = null;
		try
		{
			InputStream in = new FileInputStream(filename);
			PNGDecoder d = new PNGDecoder(in);
			w = d.getWidth();
			h = d.getHeight();
			buff = ByteBuffer.allocateDirect(4*w*h);
			d.decode(buff, w*4, Format.RGBA);
			buff.flip();
			in.close();
		}
		catch(IOException e)
		{
			System.out.println("Could not load" + filename);
		}
		
		id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, w, h, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buff);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		return id;
	}*/

	public int load2d(String filename)
	{
		int id = 0;
		BufferedImage image;
		try { image = ImageIO.read(new File(filename)); }
		catch (IOException e) { return -1; }
		int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));		// Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));		// Green component
                buffer.put((byte) (pixel & 0xFF));				// Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));		// Alpha component
            }
        }

        buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

        id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        return id;
	}
}
